package amailp.intellij.robot.psi.reference

import com.intellij.util.Processor
import com.jetbrains.python.psi.PyFunction

import scala.collection.mutable.ListBuffer

/**
 * @author Sergey Khomutinin skhomuti@gmail.com
 *         Date: 30.08.2019
 */
class MethodFinderWithoutUnderscoresAndSpaces(reference: String) extends Processor[PyFunction] {

  import MethodFinderWithoutUnderscoresAndSpaces._

  val result: ListBuffer[PyFunction] = ListBuffer[PyFunction]()

  override def process(currentElement: PyFunction): Boolean = {
    val matches: Boolean = pyElementMatches(currentElement, reference)
    if (matches)
      result += currentElement
    !matches
  }

  def getResult: List[PyFunction] = {
    result.toList
  }
}

object MethodFinderWithoutUnderscoresAndSpaces {
  private def prepareMethodName(name: String): String =
    name.replace(" ", "").replace("_", "").toLowerCase

  private def isPrivate(name: String): Boolean =
    name.startsWith("_")

  def pyElementMatches(pyElement: PyFunction, reference: String): Boolean = {
    if (!isPrivate(pyElement.getName)) {
      val methodNamePrepared = prepareMethodName(reference)
      val currentElementPrepared = prepareMethodName(pyElement.getName)
      methodNamePrepared == currentElementPrepared
    } else false
  }

}
