package Visitors;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import CompileUtils.FunctionNode;
import CompileUtils.SemanticError;
import CompileUtils.SyntaxError;
import antlr.WACCParser;
import antlr.WACCParser.BinaryCompExprContext;
import antlr.WACCParser.BinaryEqExprContext;
import antlr.WACCParser.CallContext;
import antlr.WACCParser.ExprContext;
import antlr.WACCParser.IdentExprContext;
import antlr.WACCParser.Inc_decContext;
import antlr.WACCParser.ReassignContext;

public class SemanticCheckVisitor extends WACCVisitor {
    @Override
    public Void visitBegin(@NotNull WACCParser.BeginContext ctx) {
        currentScope = currentScope.getScopeBelow();
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitIf_else(@NotNull WACCParser.If_elseContext ctx) {
        if (!getExprType(ctx.expr()).equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "If condition must be of type bool"));
        }

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
        if (!getExprType(ctx.expr()).equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "If condition must be of type bool"));
        }

        currentScope = currentScope.getScopeBelow();
        visit(ctx.stat());
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitCall(@NotNull WACCParser.CallContext ctx) {
        String functionName = ctx.IDENT().getText();
        FunctionNode function = null;
        List<String> paramTypes = null;

        if (ctx.STD() != null) {
            function = stdLib.getFunction(functionName);
            if (function == null) {
                errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                        ctx.start.getCharPositionInLine(),
                        "Invalid library function: Does not exist."));
            }
        } else {
            function = funcTable.getTypeGlobal(functionName,
                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
        paramTypes = function.paramTypes;

        // no arguments in call and function expects no arguments
        if (ctx.arg_list() == null && paramTypes == null) {
            return null;
        } else if (ctx.arg_list() == null || paramTypes == null) {
            // arguments in call and function expects no arguments or
            // no arguments in call and function expects arguments
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Incorrect number of arguments."));
        }

        List<ExprContext> args = ctx.arg_list().expr();
        List<String> argTypes = new ArrayList<>();

        for (ExprContext e : args) {
            argTypes.add(getExprType(e));
        }

        if (argTypes.size() != paramTypes.size()) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Incorrect number of arguments."));
            return null;
        }

        for (int i = 0; i < argTypes.size(); i++) {
            String argType = argTypes.get(i);
            String paramType = paramTypes.get(i);

            if (argType.equals("pair") && paramType.startsWith("pair")) {
                continue;
            }

            if ((argType.equals("string") && paramType.equals("char[]"))
                    || (argType.equals("char[]") && paramType.equals("string"))) {
                continue;
            }

            if (!argType.equals(paramType)) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Incorrect types of arguments."));
            }
        }

        return null;
    }

    @Override
    public Void visitInit(@NotNull WACCParser.InitContext ctx) {
        String varName = ctx.IDENT().getText();
        String lhsType = ctx.type().getText();

        if (!(ctx.assign_rhs() instanceof CallContext)) {
            return visitChildren(ctx);
        }

        String rhsType = getAssign_rhsType(ctx.assign_rhs());

        if (rhsType.equals("int")) {
            varStack.push(varName);
        }

        if (rhsType.equals("pair") && lhsType.startsWith("pair")
                && !lhsType.endsWith("]")) {
            return visitChildren(ctx);
        }

        if (!lhsType.equals(rhsType)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Value type does not match target type."));
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitReassign(@NotNull WACCParser.ReassignContext ctx) {
        String lhsType = null;

        if (!(ctx.assign_rhs() instanceof CallContext)) {
            return visitChildren(ctx);
        }

        if (ctx.assign_lhs().IDENT() != null) {
            lhsType = currentScope.getTypeGlobal(ctx.assign_lhs().IDENT()
                            .getText(), ctx.start.getLine(),
                    ctx.start.getCharPositionInLine());
        }

        if (ctx.assign_lhs().pair_elem() != null) {
            if (ctx.assign_lhs().pair_elem().FST() != null) {
                lhsType = currentScope.getTypeGlobal(ctx.assign_lhs()
                                .pair_elem().expr().getText()
                                + ".fst", ctx.start.getLine(),
                        ctx.start.getCharPositionInLine());
            } else {
                lhsType = currentScope.getTypeGlobal(ctx.assign_lhs()
                                .pair_elem().expr().getText()
                                + ".snd", ctx.start.getLine(),
                        ctx.start.getCharPositionInLine());
            }

        }

        if (ctx.assign_lhs().array_elem() != null) {
            lhsType = currentScope.getTypeGlobal(ctx.assign_lhs().array_elem()
                            .IDENT().getText(), ctx.start.getLine(),
                    ctx.start.getCharPositionInLine());
            lhsType = lhsType.replaceAll("\\[", "");
            lhsType = lhsType.replaceAll("\\]", "");
        }

        String rhsType = getAssign_rhsType(ctx.assign_rhs());

        if (lhsType == null) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Target not initialised."));
        }

        if (rhsType.equals("pair") && lhsType.startsWith("pair")) {
            return visitChildren(ctx);
        }

        if (lhsType.equals("pair") && rhsType.startsWith("pair")) {
            return visitChildren(ctx);
        }

        if (rhsType.equals("any")) {
            return visitChildren(ctx);
        }

        if (rhsType.equals("char") && lhsType.equals("string")) {
            return visitChildren(ctx);
        }

        if (!lhsType.equals(rhsType)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Value type does not match target type."));
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitArray_liter(@NotNull WACCParser.Array_literContext ctx) {
        List<ExprContext> list = ctx.expr();

        if (list.isEmpty()) {
            return null;
        }

        String firstType = getExprType(list.get(0));

        for (ExprContext ec : list) {
            if (!getExprType(ec).equals(firstType)) {
                errorReporter.addSemantic(new SemanticError(
                        ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                        "Not every item in list same type."));
            }
        }

        return null;
    }

    @Override
    public Void visitInc_dec(@NotNull WACCParser.Inc_decContext ctx) {
        String type = getExprType(ctx.expr());
        WACCParser.Post_unary_operContext unOP = ctx.post_unary_oper();

        if (unOP instanceof WACCParser.IncContext
        		|| unOP instanceof WACCParser.DecContext) {
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

    @Override
    public Void visitWhile(@NotNull WACCParser.WhileContext ctx) {
        currentScope = currentScope.getScopeBelow();
        String cond = getExprType(ctx.expr());

        if (!cond.equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Condition must be of type bool."));
        }

        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }
    
    @Override
    public Void visitDo_while(@NotNull WACCParser.Do_whileContext ctx) {
        currentScope = currentScope.getScopeBelow();
        String cond = getExprType(ctx.expr());

        if (!cond.equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Condition must be of type bool."));
        }

        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }
    
    @Override
    public Void visitFor(@NotNull WACCParser.ForContext ctx) {
        currentScope = currentScope.getScopeBelow();
        
        if (!(ctx.expr(0) instanceof IdentExprContext)) {
        	errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "First expression in for loop must be an int variable."));
        }
        
        String ident = getExprType(ctx.expr(0));
        String cond = getExprType(ctx.expr(1));
        
        if (!ident.equals("int")) {
        	errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "First expression in for loop must be an int variable."));
        }

        if (!cond.equals("bool")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Condition must be of type bool."));
        }
        
        String loopVarIdent = ctx.expr(0).getText();
		if (ctx.expr(1) instanceof BinaryCompExprContext) {
			if (!(loopVarIdent.equals(
        				((BinaryCompExprContext) ctx.expr(1)).expr().get(0).getText())
        		|| loopVarIdent.equals(
        				((BinaryCompExprContext) ctx.expr(1)).expr().get(1).getText()))) {
				errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
	                    ctx.start.getCharPositionInLine(),
	                    "Second expression in for loop must be a boolean" +
	                    " comparison using the original variable."));
			}
		} else if (ctx.expr(1) instanceof BinaryEqExprContext) {
			if (!(loopVarIdent.equals(
        				((BinaryEqExprContext) ctx.expr(1)).expr().get(0).getText())
        		|| loopVarIdent.equals(
        				((BinaryCompExprContext) ctx.expr(1)).expr().get(1).getText()))) {
				errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
	                    ctx.start.getCharPositionInLine(),
	                    "Second expression in for loop must be a boolean" +
	                    " comparison using the original variable."));
			}
		}
        
        if (!((ctx.stat(0) instanceof WACCParser.Inc_decContext))
        		|| ctx.stat(0) instanceof WACCParser.ReassignContext) {
        	errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Loop variable must be changed using increment, " +
                    "decrement or a reassignment statement."));
        }
        
        if (ctx.stat(0) instanceof WACCParser.Inc_decContext) {
        	Inc_decContext inc_dec = (Inc_decContext) ctx.stat(0);
        	if (!(loopVarIdent.equals(inc_dec.expr().getText()))) {
        		errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                        ctx.start.getCharPositionInLine(),
                        "Loop variable reassignment statement must use" +
                        "the original variable."));
        	}
        } else if (ctx.stat(0) instanceof WACCParser.ReassignContext) {
        	ReassignContext reassign = (ReassignContext) ctx.stat(0);
        	if (!(loopVarIdent.equals(
        			reassign.assign_lhs().IDENT().getText()))) {
        		errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                        ctx.start.getCharPositionInLine(),
                        "Loop variable reassignment statement must use" +
                        "the original variable."));
        	}
        }

        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitRead(@NotNull WACCParser.ReadContext ctx) {
        String variable = null;
        if (ctx.assign_lhs().IDENT() != null) {
            variable = ctx.assign_lhs().IDENT().getText();
        }
        if (ctx.assign_lhs().pair_elem() != null) {
            if (ctx.assign_lhs().pair_elem().FST() != null) {
                variable = ctx.assign_lhs().pair_elem().expr().getText()
                        + ".fst";
            } else {
                variable = ctx.assign_lhs().pair_elem().expr().getText()
                        + ".snd";
            }
        }
        if (ctx.assign_lhs().array_elem() != null) {
            variable = ctx.assign_lhs().array_elem().IDENT().getText();
        }

        String type = currentScope.getTypeGlobal(variable, ctx.start.getLine(),
                ctx.start.getCharPositionInLine());

        if (type.endsWith("]")) {
            type = type.replaceAll("\\[", "");
            type = type.replaceAll("\\]", "");
        }

        if (!(type.equals("char") || type.equals("int"))) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Input must be of types char or int."));
        }

        return null;
    }

    @Override
    public Void visitFree(@NotNull WACCParser.FreeContext ctx) {
        String type = getExprType(ctx.expr());

        if (!type.contains("pair") && !type.equals("array")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must free pairs or array elements."));
        }

        return null;
    }

    @Override
    public Void visitExit(@NotNull WACCParser.ExitContext ctx) {
        String type = getExprType(ctx.expr());

        if (!type.equals("int")) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Must exit with type int."));
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitInt_liter(@NotNull WACCParser.Int_literContext ctx) {
        long value = Long.parseLong(ctx.getText());

        if (value >= Math.pow(2, 31) || value < -Math.pow(2, 31)) {
            errorReporter.addSyntax(new SyntaxError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Integer over/underflow."));
        }
        return null;
    }

    @Override
    public Void visitPrintln(@NotNull WACCParser.PrintlnContext ctx) {

        if (ctx.expr() instanceof IdentExprContext) {
            currentScope.getTypeGlobal(ctx.expr().getText(),
                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitPrint(@NotNull WACCParser.PrintContext ctx) {

        if (ctx.expr() instanceof IdentExprContext) {
            currentScope.getTypeGlobal(ctx.expr().getText(),
                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }

        return visitChildren(ctx);
    }

}
