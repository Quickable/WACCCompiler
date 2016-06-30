package CompileUtils;

import java.util.LinkedList;
import java.util.List;

public class ErrorReporter {

    private List<SemanticError> semanticErrorList = new LinkedList<>();
    private List<SyntaxError> syntaxErrorList = new LinkedList<>();

    public void addSyntax(SyntaxError e) {
        syntaxErrorList.add(e);
    }

    public void addSemantic(SemanticError e) {
        semanticErrorList.add(e);
        // print();
    }

    public void print() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (SyntaxError e : syntaxErrorList) {
            sb1.append(e.toString());
            sb1.append('\n');
        }

        if (!syntaxErrorList.isEmpty()) {
            System.out.print(sb1.toString());
            System.exit(100);
        }

        for (SemanticError e : semanticErrorList) {
            sb2.append(e.toString());
            sb2.append('\n');
        }

        if (!semanticErrorList.isEmpty()) {
            System.out.print(sb2.toString());
            System.exit(200);
        }
    }
}
