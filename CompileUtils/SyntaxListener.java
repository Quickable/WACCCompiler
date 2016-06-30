package CompileUtils;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SyntaxListener extends BaseErrorListener {

    private ErrorReporter errorReporter;

    public SyntaxListener(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg,
                            RecognitionException e) {

        errorReporter.addSyntax(new SyntaxError(line, charPositionInLine, msg));
    }
}
