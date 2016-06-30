package CompileUtils;

public class SyntaxError extends Error {

    public SyntaxError(int lineNumber, int charNumber, String message) {
        super(lineNumber, charNumber, message);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("#syntax_error#" + '\n');
        sb.append("line " + lineNumber);
        sb.append(':');
        sb.append(charNumber);
        sb.append(" " + message);

        return sb.toString();
    }
}
