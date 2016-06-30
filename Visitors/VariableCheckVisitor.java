package Visitors;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import CompileUtils.FunctionNode;
import CompileUtils.Scope;
import CompileUtils.SemanticError;
import CompileUtils.ValueTable;
import antlr.WACCParser;
import antlr.WACCParser.CallContext;
import antlr.WACCParser.ParamContext;
import antlr.WACCParser.Param_listContext;

public class VariableCheckVisitor extends WACCVisitor {

    @Override
    public Void visitBegin(@NotNull WACCParser.BeginContext ctx) {
        Scope<String> newScope =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitFunc(@NotNull WACCParser.FuncContext ctx) {
        String returnType = ctx.type().getText();
        String functionName = ctx.IDENT().getText();
        Scope<String> newScope =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        Param_listContext parameters = ctx.param_list();
        List<String> paramTypes = new ArrayList<>();

        // the following null check accounts for functions with no arguments
        if (parameters != null) {
            List<ParamContext> params = parameters.param();

            for (ParamContext param : params) {
                addIdentToScope(param.IDENT().getText(), param.type(),
                        ctx.start.getLine(), ctx.start.getCharPositionInLine());

                paramTypes.add(param.type().getText());
            }
        }

        if (paramTypes.isEmpty()) {
            paramTypes = null;
        }

        FunctionNode function = new FunctionNode(returnType, paramTypes);
        funcTable.addType(functionName, function, ctx.start.getLine(),
                ctx.start.getCharPositionInLine());

        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitWhile(@NotNull WACCParser.WhileContext ctx) {
        Scope<String> newScope =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }    
    
    @Override
    public Void visitDo_while(@NotNull WACCParser.Do_whileContext ctx) {
        Scope<String> newScope =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitFor(@NotNull WACCParser.ForContext ctx) {
        Scope<String> newScope =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        visitChildren(ctx);
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitInit(@NotNull WACCParser.InitContext ctx) {
        String varName = ctx.IDENT().getText();
        String lhsType = ctx.type().getText();

        if (ctx.assign_rhs() instanceof CallContext) {
            addIdentToScope(varName, ctx.type(), ctx.start.getLine(), ctx.start
                    .getCharPositionInLine());
            return visitChildren(ctx);
        }

        String rhsType = getAssign_rhsType(ctx.assign_rhs());

        if (rhsType.equals("int")) {
            varStack.push(varName);
        }

        if ((ctx.type().array_type() != null) && rhsType.equals("emptyList")) {
            addIdentToScope(varName, ctx.type(), ctx.start.getLine(), ctx.start
                    .getCharPositionInLine());
            return visitChildren(ctx);
        }

        if (rhsType.equals("pair") && lhsType.startsWith("pair") && !lhsType.endsWith("]")) {
            addIdentToScope(varName, ctx.type(), ctx.start.getLine(), ctx.start
                    .getCharPositionInLine());
            return visitChildren(ctx);
        }

        if (lhsType.startsWith("pair") && lhsType.endsWith("]")) {
            if (rhsType.equals("pair[]")) {
                addIdentToScope(varName, ctx.type(), ctx.start.getLine(), ctx.start
                        .getCharPositionInLine());
                return visitChildren(ctx);
            }
        }

        if (!lhsType.equals(rhsType)) {
            errorReporter.addSemantic(new SemanticError(ctx.start.getLine(),
                    ctx.start.getCharPositionInLine(),
                    "Value type does not match target type."));
        }

        addIdentToScope(varName, ctx.type(), ctx.start.getLine(), ctx.start
                .getCharPositionInLine());

        return visitChildren(ctx);
    }

    @Override
    public Void visitReassign(@NotNull WACCParser.ReassignContext ctx) {
        String lhsType = null;

        if (ctx.assign_lhs().IDENT() != null) {
            lhsType =
                    currentScope.getTypeGlobal(ctx.assign_lhs().IDENT()
                                    .getText(), ctx.start.getLine(),
                            ctx.start.getCharPositionInLine());
        }

        if (ctx.assign_lhs().pair_elem() != null) {
            return visitChildren(ctx);
        }

        if (ctx.assign_lhs().array_elem() != null) {
            lhsType =
                    currentScope.getTypeGlobal(ctx.assign_lhs().array_elem()
                                    .IDENT().getText(), ctx.start.getLine(),
                            ctx.start.getCharPositionInLine());
            lhsType = lhsType.replaceAll("\\[", "");
            lhsType = lhsType.replaceAll("\\]", "");
        }

        String rhsType = null;
        if (!(ctx.assign_rhs() instanceof CallContext)) {
            rhsType = getAssign_rhsType(ctx.assign_rhs());
        } else {
            return visitChildren(ctx);
        }

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
    public Void visitIf_else(@NotNull WACCParser.If_elseContext ctx) {
        Scope<String> newScopeThen =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        Scope<String> newScopeElse =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScopeThen);
        currentScope = newScopeThen;
        visit(ctx.getChild(3));
        currentScope = currentScope.getScopeAbove();

        currentScope.addScopeBelow(newScopeElse);
        currentScope = newScopeElse;
        visit(ctx.getChild(5));
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitIf(@NotNull WACCParser.IfContext ctx) {
        Scope<String> newScopeThen =
                new Scope<>(currentScope, errorReporter,
                        new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScopeThen);
        currentScope = newScopeThen;
        visit(ctx.stat());
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitInt_liter(@NotNull WACCParser.Int_literContext ctx) {
        long value = Long.parseLong(ctx.getText());

        if (!varStack.isEmpty()) {
            String ident = varStack.pop();
            currentScope.valueTable.add(ident, value, ctx.start.getLine(),
                    ctx.start.getCharPositionInLine());
        }
        return null;
    }

}
