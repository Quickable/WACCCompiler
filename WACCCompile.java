import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import CompileUtils.SyntaxListener;
import Visitors.DataVisitor;
import Visitors.ReturnCheckVisitor;
import Visitors.SemanticCheckVisitor;
import Visitors.TextVisitor;
import Visitors.VariableCheckVisitor;
import Visitors.WACCVisitor;
import antlr.WACCLexer;
import antlr.WACCParser;

public class WACCCompile {

    public static void main(String[] args) throws Exception {

        if (args.length >= 1) {
            if (args[0].equals("--browse_lib")) {
                System.out.println("Standard library functions:");
                WACCVisitor.stdLib.printStdLibFuncs();
                System.exit(0);
            } else {
                System.out.println("Invalid flags!, use \"--browse_lib\" to view the standard" +
                        " library functions or give a .wacc file to compile.");
                System.exit(1);
            }
        }

        ANTLRInputStream input = new ANTLRInputStream(System.in);

        WACCLexer lexer = new WACCLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        WACCParser parser = new WACCParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new SyntaxListener(WACCVisitor.errorReporter));
        // addType ours
       
        ParseTree tree = parser.program();
        WACCVisitor.errorReporter.print();

        VariableCheckVisitor vCV = WACCVisitor.variableCheck();
        vCV.visit(tree);
        WACCVisitor.errorReporter.print();

        WACCVisitor.currentScope = WACCVisitor.globalTable;
        WACCVisitor.currentScope.resetTableCounter();

        ReturnCheckVisitor rCV = WACCVisitor.returnCheck();
        rCV.visit(tree);
        WACCVisitor.errorReporter.print();
        
        WACCVisitor.currentScope = WACCVisitor.globalTable;
        WACCVisitor.currentScope.resetTableCounter();
        
        SemanticCheckVisitor sC = WACCVisitor.semanticCheck();
        sC.visit(tree);
        WACCVisitor.errorReporter.print();

        WACCVisitor.currentScope = WACCVisitor.globalTable;
        WACCVisitor.currentScope.resetTableCounter();

        DataVisitor dV = WACCVisitor.dataVisitor();
        dV.visit(tree);

        WACCVisitor.currentScope = WACCVisitor.globalTable;
        WACCVisitor.currentScope.resetTableCounter();

        TextVisitor tV = WACCVisitor.textVisitor();
        tV.visit(tree);

        System.out.println(WACCVisitor.dataProgramBuilder.toString());
        System.out.println(WACCVisitor.textProgramBuilder.toString());

        System.exit(0);
    }
}
