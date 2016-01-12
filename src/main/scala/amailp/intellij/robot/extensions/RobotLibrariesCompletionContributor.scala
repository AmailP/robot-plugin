package amailp.intellij.robot.extensions

import com.intellij.codeInsight.completion._
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.{Processor, ProcessingContext}
import com.intellij.codeInsight.lookup.{LookupElement, AutoCompletionPolicy, LookupElementBuilder}
import amailp.intellij.robot.elements.RobotTokenTypes
import amailp.intellij.robot.psi._
import com.jetbrains.python.psi.stubs.{PyModuleNameIndex, PyClassNameIndex}
import scala.collection.JavaConversions._
import amailp.intellij.robot.file.Icons
import icons.PythonIcons.Python.Python
import amailp.intellij.robot.psi.utils.ExtRobotPsiUtils
import com.intellij.psi.PsiElement
import com.jetbrains.python.psi._
import javax.swing.Icon
import com.jetbrains.python.{PyNames, PythonFileType}
import com.jetbrains.python.psi.impl.PyPsiUtils._

import scala.collection.mutable

class RobotLibrariesCompletionContributor extends CompletionContributor {

  extend(CompletionType.BASIC,
  PlatformPatterns.psiElement(RobotTokenTypes.Word),
  new CompletionProvider[CompletionParameters]() {
    override def addCompletions(
                                 completionParameters: CompletionParameters,
                                 processingContext: ProcessingContext,
                                 completionResultSet:  CompletionResultSet) = {
      val currentPsiElem = completionParameters.getPosition
      val psiUtils: ExtRobotPsiUtils = new ExtRobotPsiUtils {
        def utilsPsiElement: PsiElement = completionParameters.getOriginalPosition
      }
      //TODO rethink filtering, this maybe does not work well enough
      currentPsiElem.getParent.getParent.getParent match {
        case _: TestCaseDefinition | _: KeywordDefinition =>
          for {
            library: Library <- librariesInScope
            lookupElements = lookupElementsForLibrary(library)
          } completionResultSet.addAllElements(lookupElements)

        case _ =>
      }

      def librariesInScope = psiUtils.originalRobotFile.getImportedLibraries

      def lookupElementsForLibrary(library: Library): Seq[LookupElement] = {
        val libraryName = library.getText

        object WithSameNameClass {
          def unapply(pyFile: PyFile): Option[PyClass] =
            Option(pyFile.findTopLevelClass(pyFile.getVirtualFile.getNameWithoutExtension))
        }

        object PythonClassWithExactQName {
          def unapply(name: String): Option[PyClass] = searchForClass(s"$name")
        }

        object PythonClassFromRobotLib {
          def unapply(name: String): Option[PyClass] = searchForClass(s"robot.libraries.$name.$name")
        }

        object PythonClassSameNameAsModule {
          def unapply(name: String): Option[PyClass] = {
            val qNameComponents = name.split('.')
            val className = (qNameComponents :+ qNameComponents.last).mkString(".")
            searchForClass(className)
          }
        }

        object LocalPythonFile {
          def unapply(library: LibraryValue): Option[PyFile] = {
            for {
              virtualFile <- Option(library.currentDirectory.findFileByRelativePath(library.getText))
              psiFile <- Option(psiUtils.psiManager.findFile(virtualFile))
              if psiFile.getFileType == PythonFileType.INSTANCE
            } yield psiFile.asInstanceOf[PyFile]
          }
        }

        object InPathPythonFile {
          def unapply(library: LibraryValue): Option[PyFile] =
            PyModuleNameIndex.find(library.getText, currentPsiElem.getProject, true).headOption
        }

        object ClassName {
          def unapply(library: LibraryValue): Option[String] =
            Option(library.getText)
        }

        def searchForClass(qName: String) =
          Option(PyPsiFacade.getInstance(currentPsiElem.getProject).createClassByQName(qName, currentPsiElem))

        library match {
          case LocalPythonFile(WithSameNameClass(pyClass)) => lookupElementsFromMethods(libraryName, pyClass, Python)
          case LocalPythonFile(pyFile) => lookupElementsFrom__all__(libraryName, pyFile, Python)
          case ClassName(PythonClassWithExactQName(pyClass)) => lookupElementsFromMethods(libraryName, pyClass, Python)
          case ClassName(PythonClassFromRobotLib(pyClass)) => lookupElementsFromMethods(libraryName, pyClass, Icons.robot)
          case ClassName(PythonClassSameNameAsModule(pyClass)) => lookupElementsFromMethods(libraryName, pyClass, Python)
          case InPathPythonFile(pyFile) => lookupElementsFrom__all__(libraryName, pyFile, Python)
          case _ => Nil
        }
      }

      def formatParameterName(parameter: PyParameter) = parameter match {
        case p if p.hasDefaultValue => s"${p.getName}=${p.getDefaultValue.getText}"
        case p => p.getName
      }

      def lookupElementsFromMethods(libName: String, baseClass: PyClass, icon: Icon): Seq[LookupElement] = {
        class CollectLookupElementsIfPublic extends Processor[PyFunction] {
          val lookupElements: mutable.MutableList[LookupElement] = mutable.MutableList()
          override def process(method: PyFunction): Boolean = {
            if(!method.getName.startsWith("_"))
              lookupElements += createLookupElement(method, libName, drop = 1, icon)
            true
          }
        }
        val processor = new CollectLookupElementsIfPublic()
        baseClass.visitMethods(processor, true, null)
        processor.lookupElements
      }

      def lookupElementsFrom__all__(libName: String, pyFile: PyFile, icon: Icon): Seq[LookupElement] =
        for {
          function <- getFunctionsFrom__all__(pyFile)
          if !function.getName.startsWith("_")
        } yield createLookupElement(function, libName, drop = 0, icon)

      def getFunctionsFrom__all__(pyFile: PyFile): Seq[PyFunction] = {
        val attributesNamedAll = getAttributeValuesFromFile(pyFile, PyNames.ALL).toArray(Array[PyExpression]())
        for {
          functionName <- getStringValues(attributesNamedAll).toIndexedSeq
        } yield Option(pyFile.findTopLevelFunction(functionName))
      }.flatten

      def createLookupElement(function: PyFunction, libName: String, drop: Int, icon: Icon) = {
        val paramList = function.getParameterList
        LookupElementBuilder.create(function.getName.replace('_', ' '))
          .withCaseSensitivity(false)
          .withIcon(icon)
          .withTypeText(libName, true)
          .withTailText(formatMethodParameters(paramList.getParameters.drop(drop), paramList.hasPositionalContainer, paramList.hasKeywordContainer))
          .withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE)
      }

      def formatMethodParameters(parameters: Array[PyParameter],
                                 hasPositionalContainer: Boolean,
                                 hasKeywordContainer: Boolean) = {
        val params = parameters.reverseIterator
        var paramNames: List[String] = Nil

        if(params.hasNext && hasKeywordContainer)
          paramNames = s"**${params.next().getName}" :: paramNames

        if(params.hasNext && hasPositionalContainer)
          paramNames = s"*${params.next().getName}" :: paramNames

        for (parameter <- params)
          paramNames = formatParameterName(parameter) :: paramNames
        paramNames
      }.mkString(" (", ", ", ")")

    }
  })
}
