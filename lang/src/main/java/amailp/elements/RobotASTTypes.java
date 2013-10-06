package amailp.elements;

import amailp.psi.impl.RobotPhraseImpl;
import amailp.psi.impl.RobotTableImpl;
import amailp.psi.impl.RobotTableListImpl;
import com.intellij.psi.PsiElement;

import java.util.HashMap;

public interface RobotASTTypes {
    final static RobotElementType TableList = new RobotElementType("TableList");
    final static RobotElementType Table = new RobotElementType("Table");
    final static RobotElementType HeaderRow = new RobotElementType("HeaderRow");
    final static RobotElementType TableRow = new RobotElementType("TableRow");
    final static RobotElementType Phrase = new RobotElementType("Phrase");
    final static RobotElementType Sentence = new RobotElementType("Sentence");
    final static RobotElementType EmptyCell = new RobotElementType("EmptyCell");
    final static RobotElementType NonEmptyCell = new RobotElementType("NonEmptyCell");
}