package amailp.parser;

import amailp.elements.RobotASTTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static amailp.elements.RobotTokenTypes.Space;
import static amailp.elements.RobotTokenTypes.Variable;
import static amailp.elements.RobotTokenTypes.Word;

public class SentenceParser {
    public static void parseSentence(PsiBuilder builder) {
        if(!phraseComponent.contains(builder.getTokenType())) {
            builder.error("Phrase expected. Found instead: " + builder.getTokenType());
            return;
        }
        PsiBuilder.Marker phrase = builder.mark();
        while (phraseComponent.contains(builder.getTokenType())){
            builder.advanceLexer();
        }
        phrase.done(RobotASTTypes.Phrase);
    }

    final static TokenSet phraseComponent = TokenSet.create(Word, Space, Variable);
}
