package Visitors;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import CompileUtils.*;
import StdLib.StandardLibrary;
import org.antlr.v4.runtime.misc.NotNull;

import antlr.WACCParser;
import antlr.WACCParser.*;

public class WACCVisitor extends antlr.WACCParserBaseVisitor<Void> {
    public static ErrorReporter errorReporter = new ErrorReporter();

    public static Scope<String> globalTable = new Scope<>(
            null, errorReporter, new ValueTable(null, errorReporter));
    public static Scope<String> currentScope = globalTable;
    protected static final Scope<FunctionNode> funcTable =
            new Scope<>(null, errorReporter, null);

    public static StandardLibrary stdLib = new StandardLibrary();

    protected static final Stack<String> returnTypeStack = new Stack<>();
    protected static final Stack<String> varStack = new Stack<>();

    protected static String operType;
    protected static boolean visitingFunctionBody = false;

    protected static Integer returnCheckStatus = VisitResult.RETURN_MISSING
            .ordinal();

    protected static final HashMap<String, String> msgHashMap =
            new LinkedHashMap<>();

    public static StringBuilder dataProgramBuilder = new StringBuilder();
    public static StringBuilder textProgramBuilder = new StringBuilder();
    public static StringBuilder sysFuncProgramBuilder = new StringBuilder();
    protected static int messageCounter = 0;

    protected enum VisitResult {
        RETURN_CORRECT, RETURN_INCORRECT, RETURN_MISSING
    }

    public static ReturnCheckVisitor returnCheck() {
        return new ReturnCheckVisitor();
    }

    public static SemanticCheckVisitor semanticCheck() {
        return new SemanticCheckVisitor();
    }

    public static VariableCheckVisitor variableCheck() {
        return new VariableCheckVisitor();
    }

    public static DataVisitor dataVisitor() {
        return new DataVisitor();
    }

    public static TextVisitor textVisitor() {
        return new TextVisitor();
    }

    @Override
    public Void visitFunc(@NotNull WACCParser.FuncContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitBegin(@NotNull WACCParser.BeginContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitWhile(@NotNull WACCParser.WhileContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override public Void visitDo_while(
    		@NotNull WACCParser.Do_whileContext ctx) {
    	currentScope = currentScope.getScopeBelow();
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitFor(@NotNull WACCParser.ForContext ctx) {
    	currentScope = currentScope.getScopeBelow();
    	visitChildren(ctx);
    	currentScope = currentScope.getScopeAbove();
    	return null;
    }

    @Override
    public Void visitIf_else(@NotNull WACCParser.If_elseContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visit(ctx.getChild(3));
        currentScope = currentScope.getScopeAbove();

        currentScope = currentScope.getScopeBelow();
        visit(ctx.getChild(5));
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitIf(@NotNull WACCParser.IfContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visit(ctx.stat());
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    protected String getExprType(ExprContext e) {

        if (e instanceof IntLiterExprContext) {
            return "int";
        }

        if (e instanceof UnaryOperExprContext
                && ((UnaryOperExprContext) e).unary_oper()
                instanceof NegateContext) {
            return "int";
        }

        if (e instanceof UnaryOperExprContext
                && ((UnaryOperExprContext) e).unary_oper()
                instanceof LenContext) {
            return "int";
        }

        if (e instanceof UnaryOperExprContext
                && ((UnaryOperExprContext) e).unary_oper()
                instanceof OrdContext) {
            return "int";
        }

        if (e instanceof UnaryOperExprContext
                && ((UnaryOperExprContext) e).unary_oper()
                instanceof ChrContext) {
            return "char";
        }

        if (e instanceof UnaryOperExprContext
                && ((UnaryOperExprContext) e).unary_oper()
                instanceof ExclContext) {
            return "bool";
        }

        if (e instanceof CharLiterExprContext) {
            return "char";
        }

        if (e instanceof StringLiterExprContext) {
            return "string";
        }

        if (e instanceof BoolLiterExprContext) {
            return "bool";
        }

        if (e instanceof IdentExprContext) {
            IdentExprContext ctx = (IdentExprContext) e;
            return currentScope.getTypeGlobal(ctx.IDENT().getText(),
                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }

        if (e instanceof PairLiterExprContext) {
            return "pair";
        }

        if (e instanceof BracketedExprContext) {
            return getExprType(((BracketedExprContext) e).expr());
        }

        if (e instanceof ArrayElemExprContext) {
            String ident = e.getText();
            String[] identTokens = ident.split("\\[");
            ArrayElemExprContext ctx = (ArrayElemExprContext) e;

            String type =
                    currentScope.getTypeGlobal(identTokens[0], ctx.start.getLine(),
                            ctx.start.getCharPositionInLine());
            return type.split("\\[")[0];
        }

        if (e instanceof BinaryMultDivModExprContext) {
            visitBinaryMultDivModExpr((BinaryMultDivModExprContext) e);
        }

        if (e instanceof BinaryAddMinusExprContext) {
            visitBinaryAddMinusExpr((BinaryAddMinusExprContext) e);
        }

        if (e instanceof BinaryCompExprContext) {
            visitBinaryCompExpr((BinaryCompExprContext) e);
        }

        if (e instanceof BinaryEqExprContext) {
            visitBinaryEqExpr((BinaryEqExprContext) e);
        }

        if (e instanceof BinaryBoolExprContext) {
            visitBinaryBoolExpr((BinaryBoolExprContext) e);
        }

        if (e instanceof UnaryOperExprContext) {
            visitUnaryOperExpr((UnaryOperExprContext) e);
        }

        if (e instanceof WACCParser.PostUnaryOperExprContext) {
            visitPostUnaryOperExpr((WACCParser.PostUnaryOperExprContext) e);
        }

        String temp = operType;
        operType = null;
        return temp;
    }

    protected String getAssign_rhsType(Assign_rhsContext a) {
        if (a instanceof CallContext) {
            CallContext ctx = (CallContext) a;
            if (((CallContext) a).STD() == null) {
                return funcTable.getTypeGlobal(ctx.IDENT().getText(),
                        ctx.start.getLine(),
                        ctx.start.getCharPositionInLine()).returnType;
            }
            FunctionNode functionNode = stdLib.getFunction(ctx.IDENT().getText());
            if (functionNode == null) {
                errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                        ctx.start.getCharPositionInLine(),
                        "Invalid library function: function \"" + ctx.IDENT().getText() + "\" does not exist."));
                errorReporter.print();
            }
            return functionNode.returnType;
        }

        if (a instanceof Array_literContext) {
            List<ExprContext> list = ((Array_literContext) a).expr();
            if (!list.isEmpty()) {
                // may need to visit Array_literContext to check all expr match
                return getExprType(list.get(0)) + "[]";
            } else {
                return "emptyList";
            }
        }

        if (a instanceof Assign_rhs_pairContext) {
            Assign_rhs_pairContext ctx = (Assign_rhs_pairContext) a;
            if (ctx.pair_elem().FST() != null) {
                String pairIdent = ctx.pair_elem().expr().getText();
                return currentScope.getTypeGlobal(pairIdent + ".fst",
                        ctx.start.getLine(), ctx.start.getCharPositionInLine());
            } else {
                String pairIdent = ctx.pair_elem().expr().getText();
                return currentScope.getTypeGlobal(pairIdent + ".snd",
                        ctx.start.getLine(), ctx.start.getCharPositionInLine());
            }
        }

        if (a instanceof New_pairContext) {
            New_pairContext npc = (New_pairContext) a;
            String fstType = getExprType(npc.expr(0));
            String sndType = getExprType(npc.expr(1));

            if (fstType.startsWith("pair")) {
                fstType = "pair";
            }

            if (sndType.startsWith("pair")) {
                sndType = "pair";
            }

            return "pair(" + fstType + "," + sndType + ")";
        }

        if (a instanceof Assign_exprContext) {
            return getExprType(((Assign_exprContext) a).expr());
        }

        return null;
    }

    protected void appendDataLabel(String label) {
        dataProgramBuilder.append('\t' + label + '\n');
    }

    protected void appendTextLabel(String label) {
        textProgramBuilder.append('\t' + label + '\n');
    }

    protected void appendDataLine(String lineOfCode) {
        dataProgramBuilder.append("\t\t" + lineOfCode + '\n');
    }

    protected void appendTextLine(String lineOfCode) {
        textProgramBuilder.append("\t\t" + lineOfCode + '\n');
    }

    protected void removeData() {
        dataProgramBuilder = new StringBuilder();
    }

    @Override
    public Void visitBinaryMultDivModExpr(
            @NotNull WACCParser.BinaryMultDivModExprContext ctx) {
        String type1 = getExprType(ctx.expr(0));
        String type2 = getExprType(ctx.expr(1));

        if (!type1.equals(type2)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must give operator matching types."));
        }

        if (!type1.equals("int")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Operator of type int, must provide ints."));
        }

        operType = "int";
        return null;
    }

    @Override
    public Void visitBinaryAddMinusExpr(
            @NotNull WACCParser.BinaryAddMinusExprContext ctx) {
        String type1 = getExprType(ctx.expr(0));
        String type2 = getExprType(ctx.expr(1));

        if (!type1.equals(type2)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must give operator matching types."));
        }

        if (!type1.equals("int")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Operator of type int, must provide ints."));
        }

        operType = "int";
        return null;
    }

    @Override
    public Void visitBinaryCompExpr(
            @NotNull WACCParser.BinaryCompExprContext ctx) {
        String type1 = getExprType(ctx.expr(0));
        String type2 = getExprType(ctx.expr(1));

        if (!type1.equals(type2)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must give operator matching types."));
        }

        if (type1.equals("int") || type1.equals("char")) {
            operType = "bool";
        } else {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Operator of type int/char, must provide ints or chars."));
        }

        return null;
    }

    @Override
    public Void visitBinaryEqExpr(@NotNull WACCParser.BinaryEqExprContext ctx) {
        String type1 = getExprType(ctx.expr(0));
        String type2 = getExprType(ctx.expr(1));

        if (type1.startsWith("pair") && type2.equals("pair")) {
            operType = "bool";
            return null;
        }

        if (!type1.equals(type2)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must give operator matching types."));
        }

        operType = "bool";
        return null;
    }

    @Override
    public Void visitBinaryBoolExpr(
            @NotNull WACCParser.BinaryBoolExprContext ctx) {
        String type1 = getExprType(ctx.expr(0));
        String type2 = getExprType(ctx.expr(1));

        if (!type1.equals(type2)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must give operator matching types."));
        }

        if (!type1.equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Operator of type bool, must provide bools."));
        }

        operType = "bool";
        return null;
    }

    @Override
    public Void
    visitUnaryOperExpr(@NotNull WACCParser.UnaryOperExprContext ctx) {
        String type = getExprType(ctx.expr());
        Unary_operContext unOP = ctx.unary_oper();
        
        if (unOP instanceof ExclContext) {
            if (!type.equals("bool")) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Operator of type bool, provided type " + type 
                        + ", must provide bool."));
            }

            operType = "bool";
        }

        if (unOP instanceof NegateContext || unOP instanceof ChrContext) {
            if (!type.equals("int")) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Operator of type int, provided type " + type 
                        + ", must provide int."));
            }

            operType = "int";
        }

        if (unOP instanceof OrdContext) {
            if (!type.equals("char")) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Operator of type char, provided type " + type 
                        + ", must provide char."));
            }

            operType = "int";
        }

        if (unOP instanceof LenContext) {
            if (!type.endsWith("]")) {
                errorReporter
                        .addSemantic(new SemanticError(ctx.start.getLine(),
                                ctx.start.getCharPositionInLine(),
                                "Operator of type array element, provided type "
                                + type + ", must provide array element."));
            }

            operType = "int";
        }

        return null;
    }

    @Override
    public Void visitPostUnaryOperExpr(@NotNull WACCParser.PostUnaryOperExprContext ctx) {
        String type = getExprType(ctx.expr());
        WACCParser.Post_unary_operContext unOP = ctx.post_unary_oper();

        if (unOP instanceof IncContext || unOP instanceof DecContext) {
            if(!(ctx.expr() instanceof IdentExprContext)) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Operator requires variable referring to int."));
            }

            if (!type.equals("int")) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Operator of type int, provided type " + type
                                + ", must provide int."));
            }

            operType = "int";
        }
        return null;
    }

    protected void addIdentToScope(String ident, TypeContext type, int lineNum, int charNum) {
        if (type.pair_type() != null) {
            Pair_typeContext pair_type = type.pair_type();
            String first = ident + ".fst";
            String second = ident + ".snd";
            currentScope.addType(first, pair_type.pair_elem_type(0).getText(), lineNum, charNum);
            currentScope.addType(second, pair_type.pair_elem_type(1).getText(), lineNum, charNum);
            currentScope.addType(ident, type.getText(), lineNum, charNum);
        } else {
            currentScope.addType(ident, type.getText(), lineNum, charNum);
        }
    }
}