package Visitors;

import org.antlr.v4.runtime.misc.NotNull;

import CompileUtils.SemanticError;
import CompileUtils.SyntaxError;
import antlr.WACCParser;

public class ReturnCheckVisitor extends WACCVisitor {

    @Override
    public Void visitFunc(@NotNull WACCParser.FuncContext ctx) {
        visitingFunctionBody = true;
        currentScope = currentScope.getScopeBelow();
        String returnType = ctx.type().getText();
        returnTypeStack.push(returnType);
        visitChildren(ctx);

        if (returnCheckStatus == VisitResult.RETURN_MISSING.ordinal()) {
            // missing return statement
            errorReporter.addSyntax(new SyntaxError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Missing function return."));
            errorReporter.print();
        }

        returnTypeStack.pop();
        returnCheckStatus = VisitResult.RETURN_MISSING.ordinal();
        visitingFunctionBody = false;
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitReturn(@NotNull WACCParser.ReturnContext ctx) {
        if (!visitingFunctionBody) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Cannot return outside function scope."));
            errorReporter.print();
        }

        String funcType = returnTypeStack.peek();
        String returnExprType = getExprType(ctx.expr());

        if (funcType.equals(returnExprType)) {
            returnCheckStatus = VisitResult.RETURN_CORRECT.ordinal();
        } else if (funcType.startsWith("pair") && !funcType.endsWith("]")
                && returnExprType.equals("pair")) {
            returnCheckStatus = VisitResult.RETURN_CORRECT.ordinal();
        } else {
            // incorrect return expression type
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Incorrect return expression type."));
            errorReporter.print();
        }

        return null;
    }

    @Override
    public Void visitExit(@NotNull WACCParser.ExitContext ctx) {
        returnCheckStatus = VisitResult.RETURN_CORRECT.ordinal();
        return null;
    }

    @Override
    public Void visitIf_else(@NotNull WACCParser.If_elseContext ctx) {
        if (visitingFunctionBody) {
            currentScope = currentScope.getScopeBelow();
            visit(ctx.getChild(3));
            currentScope = currentScope.getScopeAbove();

            if (returnCheckStatus == VisitResult.RETURN_INCORRECT.ordinal()) {
                // badly formed return in branch of if else statement
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Incorrect return expression type."));
            }

            if (returnCheckStatus == VisitResult.RETURN_MISSING.ordinal()) {
                currentScope = currentScope.getScopeBelow();
                currentScope = currentScope.getScopeAbove();
                return null;
            }

            currentScope = currentScope.getScopeBelow();
            visit(ctx.getChild(5));
            currentScope = currentScope.getScopeAbove();

            if (returnCheckStatus == VisitResult.RETURN_INCORRECT.ordinal()) {
                // badly formed return in branch of if else statement
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Incorrect return expression type."));
            }

            if (returnCheckStatus == VisitResult.RETURN_MISSING.ordinal()) {
                return null;
            }
        }

        returnCheckStatus = VisitResult.RETURN_CORRECT.ordinal();
        return null;
    }

    @Override
    public Void visitIf(@NotNull WACCParser.IfContext ctx) {
        if (visitingFunctionBody) {
            currentScope = currentScope.getScopeBelow();
            visit(ctx.stat());
            currentScope = currentScope.getScopeAbove();

            if (returnCheckStatus == VisitResult.RETURN_INCORRECT.ordinal()) {
                // badly formed return in branch of if else statement
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Incorrect return expression type."));
            }

            if (returnCheckStatus == VisitResult.RETURN_MISSING.ordinal()) {
                return null;
            }
        }

        returnCheckStatus = VisitResult.RETURN_CORRECT.ordinal();
        return null;
    }

    @Override
    public Void visitInt_liter(@NotNull WACCParser.Int_literContext ctx) {
        long value = Long.parseLong(ctx.getText());

        if (value >= Math.pow(2, 31)) {
            errorReporter.addSyntax(new SyntaxError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Positive integer overflow."));
        } else if (value < -Math.pow(2, 31)) {
            errorReporter.addSyntax(new SyntaxError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Negative integer overflow."));
        }

        return null;
    }

}
