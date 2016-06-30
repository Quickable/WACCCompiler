package CompileUtils;

public abstract class Error {

    protected String message;
    protected int lineNumber;
    protected int charNumber;

    public Error(int lineNumber, int charNumber, String message) {
        this.lineNumber = lineNumber;
        this.charNumber = charNumber;
        this.message = message;
    }

    public abstract String toString();
}
