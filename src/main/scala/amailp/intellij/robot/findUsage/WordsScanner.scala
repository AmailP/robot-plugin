package amailp.intellij.robot.findUsage

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import amailp.intellij.robot.lexer.RobotLexer
import com.intellij.psi.tree.TokenSet
import amailp.intellij.robot.elements.RobotTokenTypes

class WordsScanner extends DefaultWordsScanner(new RobotLexer,
  TokenSet.create(RobotTokenTypes.Word, RobotTokenTypes.Space, RobotTokenTypes.Variable),
  RobotTokenTypes.CommentsTokens, TokenSet.EMPTY )
