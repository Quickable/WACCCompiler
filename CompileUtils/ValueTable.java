package CompileUtils;

import java.util.HashMap;

public class ValueTable {

    private final HashMap<String, Long> table = new HashMap<>();
    public final ValueTable enclosingScope;
    private ErrorReporter errorReporter;

    public ValueTable(ValueTable vT, ErrorReporter errorReporter) {
        this.enclosingScope = vT;
        this.errorReporter = errorReporter;
    }

    public void add(String ident, Long value, int lineNum, int charNum) {
        if (table.containsKey(ident)) {
            errorReporter.addSemantic(new SemanticError(lineNum, charNum, ident
                    + " already defined"));
        }

        table.put(ident, value);
    }

    private Long exists(String ident) {
        Long value = table.get(ident);

        if (value != null) {
            return value;
        }

        return null;
    }

    public Long lookupGlobal(String ident, int lineNum, int charNum) {
        ValueTable currentScope = this;

        while (currentScope != null) {
            Long value = currentScope.exists(ident);

            if (value != null) {
                return value;
            }

            currentScope = currentScope.enclosingScope;
        }

        errorReporter.addSemantic(new SemanticError(lineNum, charNum, ident
                + " not initialised"));
        errorReporter.print();
        return null;
    }
}
