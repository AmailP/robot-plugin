package amailp.parser;

import amailp.psi.RobotASTTypes;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.TokenSet;

import static amailp.psi.RobotTokenTypes.*;

public class PhraseParser {
    public static void parsePhrase(PsiBuilder builder) {
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
