/* The following code was generated by JFlex 1.4.3 on 08/02/14 22:28 */

package amailp.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import amailp.elements.RobotTokenTypes;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 08/02/14 22:28 from the specification file
 * <tt>/Users/angelini/tesi/src/RobotPlugin/robot-plugin/src/main/resources/amailp/lexer/Robot.flex</tt>
 */
public class _RobotLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int LINE = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\4\1\1\1\51\1\52\1\2\22\0\1\3\2\0\1\5"+
    "\1\45\5\0\1\7\3\0\1\6\21\0\1\50\1\43\1\0\1\20"+
    "\1\34\6\0\1\22\4\0\1\42\1\0\1\44\1\10\1\17\1\0"+
    "\1\30\4\0\1\33\1\0\1\40\3\0\1\21\1\31\1\35\1\27"+
    "\1\11\1\0\1\15\1\0\1\13\2\0\1\32\1\37\1\14\1\25"+
    "\1\41\1\0\1\26\1\16\1\12\1\36\1\0\1\24\1\0\1\23"+
    "\1\0\1\46\1\0\1\47\7\0\1\51\u1fa2\0\2\51\udfd6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\4\0\1\1\2\2\1\3\1\4\1\5\5\1\2\6"+
    "\1\3\1\4\1\5\2\0\1\7\2\10\2\11\12\1"+
    "\1\10\1\7\1\10\1\11\1\12\14\1\1\13\1\0"+
    "\12\1\1\14\1\15\4\0\11\1\4\0\3\1\1\16"+
    "\4\1\4\0\6\1\4\0\4\1\4\0\2\1\4\0"+
    "\1\1\4\0\1\1\20\0\1\17\1\0\1\20\2\0"+
    "\1\21\1\22";

  private static int [] zzUnpackAction() {
    int [] result = new int[150];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\53\0\126\0\201\0\254\0\327\0\u0102\0\u012d"+
    "\0\u012d\0\u0158\0\u0183\0\u01ae\0\u01d9\0\u0204\0\u022f\0\327"+
    "\0\u025a\0\u0285\0\u0285\0\u02b0\0\126\0\201\0\u02db\0\327"+
    "\0\u012d\0\327\0\u0158\0\u0306\0\u0331\0\u035c\0\u0387\0\u03b2"+
    "\0\u03dd\0\u0408\0\u0433\0\u045e\0\u0489\0\u04b4\0\u04df\0\u0285"+
    "\0\u02db\0\254\0\u050a\0\u0535\0\u0560\0\u058b\0\u05b6\0\u05e1"+
    "\0\u060c\0\u0637\0\u0662\0\u068d\0\u06b8\0\u06e3\0\327\0\u070e"+
    "\0\u0739\0\u0764\0\u078f\0\u07ba\0\u07e5\0\u0810\0\u083b\0\u0866"+
    "\0\u0891\0\u08bc\0\u06b8\0\u06e3\0\u08e7\0\u0912\0\u093d\0\u0968"+
    "\0\u0993\0\u09be\0\u09e9\0\u0a14\0\u0a3f\0\u0a6a\0\u0a95\0\u0ac0"+
    "\0\u0aeb\0\u0b16\0\u0b41\0\u0b6c\0\u0b97\0\u0bc2\0\u0bed\0\u0c18"+
    "\0\254\0\u0c43\0\u0c6e\0\u0c99\0\u0cc4\0\u0cef\0\u0d1a\0\u0d45"+
    "\0\u0d70\0\u0d9b\0\u0dc6\0\u0df1\0\u0e1c\0\u0e47\0\u0e72\0\u0e9d"+
    "\0\u0ec8\0\u0ef3\0\u0f1e\0\u0f49\0\u0f74\0\u0f9f\0\u0fca\0\u0ff5"+
    "\0\u1020\0\u104b\0\u1076\0\u10a1\0\u10cc\0\u10f7\0\u1122\0\u114d"+
    "\0\u1178\0\u11a3\0\u11ce\0\u11f9\0\u1224\0\u124f\0\u127a\0\u12a5"+
    "\0\u12d0\0\u12fb\0\u1326\0\u1351\0\u137c\0\u13a7\0\u13d2\0\u13fd"+
    "\0\u1428\0\u1453\0\u147e\0\u14a9\0\u14d4\0\u14ff\0\u152a\0\327"+
    "\0\u1555\0\327\0\u1580\0\u15ab\0\327\0\327";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[150];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"+
    "\23\5\1\15\11\5\1\16\2\5\1\17\1\5\1\11"+
    "\1\5\1\20\1\21\1\22\1\23\1\24\1\13\1\14"+
    "\23\5\1\15\11\5\1\16\2\5\1\17\1\5\1\23"+
    "\3\0\2\25\45\0\1\25\1\4\1\0\3\4\1\26"+
    "\45\4\1\5\4\0\45\5\55\0\1\6\52\0\1\6"+
    "\1\7\2\11\1\27\43\0\1\30\1\31\1\12\1\32"+
    "\1\33\50\12\1\5\4\0\1\5\1\34\43\5\1\0"+
    "\1\5\4\0\2\5\1\35\42\5\1\0\1\5\4\0"+
    "\3\5\1\36\6\5\1\37\14\5\1\40\5\5\1\41"+
    "\1\42\1\43\5\5\1\0\1\5\4\0\41\5\1\44"+
    "\3\5\1\0\1\5\4\0\41\5\1\45\3\5\2\0"+
    "\1\20\52\0\1\30\1\46\2\23\1\47\43\0\1\30"+
    "\1\50\1\24\1\0\51\24\1\27\1\32\1\51\50\27"+
    "\1\5\4\0\1\5\1\52\43\5\1\0\1\5\4\0"+
    "\2\5\1\53\42\5\1\0\1\5\4\0\4\5\1\54"+
    "\40\5\1\0\1\5\4\0\4\5\1\55\1\5\1\56"+
    "\5\5\1\57\30\5\1\0\1\5\4\0\20\5\1\60"+
    "\24\5\1\0\1\5\4\0\20\5\1\61\1\62\23\5"+
    "\1\0\1\5\4\0\21\5\1\63\23\5\1\0\1\5"+
    "\4\0\4\5\1\64\40\5\1\0\1\65\4\0\45\65"+
    "\1\0\1\66\4\0\45\66\2\0\1\67\51\0\1\47"+
    "\1\0\51\47\1\5\2\0\1\70\1\0\45\5\1\0"+
    "\1\5\4\0\5\5\1\71\37\5\1\0\1\5\4\0"+
    "\14\5\1\72\15\5\1\73\12\5\1\0\1\5\4\0"+
    "\32\5\1\74\12\5\1\0\1\5\4\0\10\5\1\75"+
    "\34\5\1\0\1\5\4\0\30\5\1\76\14\5\1\0"+
    "\1\5\4\0\11\5\1\77\33\5\1\0\1\5\4\0"+
    "\4\5\1\100\40\5\1\0\1\5\4\0\10\5\1\101"+
    "\34\5\1\0\1\5\4\0\5\5\1\102\37\5\1\0"+
    "\1\65\4\0\42\65\1\103\2\65\1\0\1\66\4\0"+
    "\42\66\1\104\2\66\11\0\1\105\6\0\1\106\2\0"+
    "\1\107\5\0\1\110\22\0\1\5\4\0\31\5\1\111"+
    "\13\5\1\0\1\5\4\0\21\5\1\112\23\5\1\0"+
    "\1\5\4\0\34\5\1\113\10\5\1\0\1\5\4\0"+
    "\4\5\1\114\40\5\1\0\1\5\4\0\11\5\1\115"+
    "\33\5\1\0\1\5\4\0\31\5\1\116\13\5\1\0"+
    "\1\5\4\0\5\5\1\100\37\5\1\0\1\5\4\0"+
    "\30\5\1\117\14\5\1\0\1\5\4\0\31\5\1\120"+
    "\13\5\1\0\1\5\4\0\31\5\1\121\13\5\12\0"+
    "\1\122\52\0\1\123\52\0\1\124\62\0\1\125\31\0"+
    "\1\5\4\0\34\5\1\115\10\5\1\0\1\5\4\0"+
    "\22\5\1\126\22\5\1\0\1\5\4\0\25\5\1\127"+
    "\17\5\1\0\1\5\4\0\20\5\1\130\24\5\1\0"+
    "\1\5\4\0\33\5\1\131\11\5\1\0\1\5\4\0"+
    "\32\5\1\132\12\5\1\0\1\5\4\0\20\5\1\133"+
    "\24\5\1\0\1\5\4\0\32\5\1\134\12\5\1\0"+
    "\1\5\4\0\21\5\1\135\23\5\13\0\1\136\56\0"+
    "\1\137\57\0\1\140\55\0\1\141\24\0\1\5\4\0"+
    "\20\5\1\142\24\5\1\0\1\5\4\0\14\5\1\143"+
    "\30\5\1\0\1\5\4\0\31\5\1\144\13\5\1\0"+
    "\1\5\4\0\4\5\1\145\40\5\1\0\1\5\4\0"+
    "\7\5\1\146\35\5\1\0\1\5\4\0\4\5\1\147"+
    "\40\5\1\0\1\5\4\0\7\5\1\115\35\5\13\0"+
    "\1\150\52\0\1\151\64\0\1\152\41\0\1\153\37\0"+
    "\1\5\4\0\17\5\1\135\25\5\1\0\1\5\4\0"+
    "\5\5\1\154\37\5\1\0\1\5\4\0\5\5\1\115"+
    "\37\5\1\0\1\5\4\0\7\5\1\155\35\5\1\0"+
    "\1\5\4\0\22\5\1\156\22\5\1\0\1\5\4\0"+
    "\7\5\1\157\35\5\14\0\1\160\42\0\1\161\74\0"+
    "\1\162\46\0\1\163\31\0\1\5\4\0\4\5\1\115"+
    "\40\5\1\0\1\5\4\0\5\5\1\164\37\5\1\0"+
    "\1\5\4\0\6\5\1\165\36\5\1\0\1\5\4\0"+
    "\5\5\1\75\37\5\15\0\1\166\56\0\1\167\60\0"+
    "\1\170\55\0\1\171\21\0\1\5\4\0\14\5\1\165"+
    "\30\5\1\0\1\5\4\0\5\5\1\172\37\5\16\0"+
    "\1\173\56\0\1\174\60\0\1\175\55\0\1\176\20\0"+
    "\1\5\4\0\6\5\1\177\36\5\4\0\1\200\12\0"+
    "\1\201\52\0\1\202\37\0\1\203\12\0\1\204\45\0"+
    "\1\205\41\0\1\5\4\0\20\5\1\135\24\5\10\0"+
    "\1\206\46\0\1\200\60\0\1\207\50\0\1\210\46\0"+
    "\1\203\52\0\1\211\12\0\1\212\43\0\1\213\46\0"+
    "\1\214\12\0\1\215\43\0\1\216\52\0\1\217\46\0"+
    "\1\211\56\0\1\220\52\0\1\221\46\0\1\214\56\0"+
    "\1\222\52\0\1\223\52\0\1\224\52\0\1\225\52\0"+
    "\1\226\43\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5590];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\4\0\1\1\1\11\11\1\1\11\7\1\1\11\1\1"+
    "\1\11\34\1\1\11\1\0\14\1\4\0\11\1\4\0"+
    "\10\1\4\0\6\1\4\0\4\1\4\0\2\1\4\0"+
    "\1\1\4\0\1\1\20\0\1\11\1\0\1\11\2\0"+
    "\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[150];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** For the backwards DFA of general lookahead statements */
  private boolean [] zzFin = new boolean [ZZ_BUFFERSIZE+1];

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;


  public _RobotLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public _RobotLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 138) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    return;

    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL.charAt(zzCurrentPosL++);
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL.charAt(zzCurrentPosL++);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 1: 
          { yybegin(LINE); return RobotTokenTypes.Word;
          }
        case 19: break;
        case 3: 
          { yybegin(LINE); return RobotTokenTypes.Space;
          }
        case 20: break;
        case 5: 
          { yybegin(LINE); return RobotTokenTypes.Comment;
          }
        case 21: break;
        case 2: 
          { return RobotTokenTypes.BlankLine;
          }
        case 22: break;
        case 18: 
          { yybegin(LINE); return RobotTokenTypes.TestCasesHeader;
          }
        case 23: break;
        case 9: 
          { return RobotTokenTypes.Comment;
          }
        case 24: break;
        case 17: 
          { yybegin(LINE); return RobotTokenTypes.VariablesHeader;
          }
        case 25: break;
        case 14: 
          { yybegin(LINE); return RobotTokenTypes.TestCaseSetting;
          }
        case 26: break;
        case 8: 
          // lookahead expression with fixed lookahead length
          yypushback(1);
          { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces;
          }
        case 27: break;
        case 7: 
          // general lookahead, find correct zzMarkedPos
          { int zzFState = 2;
            int zzFPos = zzStartRead;
            if (zzFin.length <= zzBufferL.length()) { zzFin = new boolean[zzBufferL.length()+1]; }
            boolean zzFinL[] = zzFin;
            while (zzFState != -1 && zzFPos < zzMarkedPos) {
              if ((zzAttrL[zzFState] & 1) == 1) { zzFinL[zzFPos] = true; } 
              zzInput = zzBufferL.charAt(zzFPos++);
              zzFState = zzTransL[ zzRowMapL[zzFState] + zzCMapL[zzInput] ];
            }
            if (zzFState != -1 && (zzAttrL[zzFState] & 1) == 1) { zzFinL[zzFPos] = true; } 

            zzFState = 3;
            zzFPos = zzMarkedPos;
            while (!zzFinL[zzFPos] || (zzAttrL[zzFState] & 1) != 1) {
              zzInput = zzBufferL.charAt(--zzFPos);
              zzFState = zzTransL[ zzRowMapL[zzFState] + zzCMapL[zzInput] ];
            };
            zzMarkedPos = zzFPos;
          }
          { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces;
          }
        case 28: break;
        case 11: 
          // lookahead expression with fixed lookahead length
          yypushback(2);
          { yybegin(LINE); return RobotTokenTypes.IrrelevantSpaces;
          }
        case 29: break;
        case 15: 
          { yybegin(LINE); return RobotTokenTypes.SettingsHeader;
          }
        case 30: break;
        case 4: 
          { yybegin(LINE); return RobotTokenTypes.Separator;
          }
        case 31: break;
        case 6: 
          { yybegin(YYINITIAL); return RobotTokenTypes.LineTerminator;
          }
        case 32: break;
        case 16: 
          { yybegin(LINE); return RobotTokenTypes.KeywordsHeader;
          }
        case 33: break;
        case 12: 
          { yybegin(LINE); return RobotTokenTypes.Variable;
          }
        case 34: break;
        case 10: 
          { yybegin(LINE); return RobotTokenTypes.Ellipsis;
          }
        case 35: break;
        case 13: 
          { yybegin(LINE); return RobotTokenTypes.ListVariable;
          }
        case 36: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}