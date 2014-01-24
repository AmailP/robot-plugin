package amailp.elements;

public interface RobotASTTypes {
    final static RobotElementType TableList = new RobotElementType("TableList");
    final static RobotElementType SettingsTable = new RobotElementType("SettingsTable");
    final static RobotElementType TestCasesTable = new RobotElementType("TestCasesTable");
    final static RobotElementType KeywordsTable = new RobotElementType("KeywordsTable");
    final static RobotElementType VariablesTable = new RobotElementType("VariablesTable");
    final static RobotElementType HeaderRow = new RobotElementType("HeaderRow");
    final static RobotElementType TableRow = new RobotElementType("TableRow");
    final static RobotElementType Phrase = new RobotElementType("Phrase");
    final static RobotElementType Sentence = new RobotElementType("Sentence");
    final static RobotElementType EmptyCell = new RobotElementType("EmptyCell");
    final static RobotElementType NonEmptyCell = new RobotElementType("NonEmptyCell");
}