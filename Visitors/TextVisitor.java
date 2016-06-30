package Visitors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import CompileUtils.Scope;
import CompileUtils.ValueTable;
import antlr.WACCParser;
import antlr.WACCParser.*;

public class TextVisitor extends WACCVisitor {

    private int labelCount = 0;
    private int initCount = 0;
    private int messageCount = 0;
    private int stringLiterCount = 0;
    private List<String> addedSysFuncs = new ArrayList<>();
    private int scopeStackOffset = 0;
    private int offsetRequired = 0;
    private List<Integer> freeRegisters;
    private int pushCount = 0;
    private boolean forceOffset = false;
    private static final int BYTE = 1;
    private static final int WORD = 4;
    private static final int MAX_IMMEDIATE = 1024;
    private boolean loadNegInt = false;

    /*
    * visitProgram starts this the TextVisitor by visiting all the functions
    * and then creating main.
    * The print functions must go last because we must be able to branch to
    * them.
    * */
    @Override
    public Void visitProgram(@NotNull WACCParser.ProgramContext ctx) {
        resetFreeRegisters();
        appendTextLabel(".text");
        appendTextLabel("");
        appendTextLabel(".global main");
        for (int i = 0; i < ctx.func().size(); i++) {
            visit(ctx.func(i));
            appendTextLine(".ltorg");
        }

        appendTextLabel("main:");
        appendTextLine("PUSH {lr}");

        initCount = countInits(ctx.stat());
        currentScope.setMemSize(initCount);
        int tempcount = initCount;

        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }

        visit(ctx.stat());

        if (tempcount != 0) {
            int temp = tempcount;
            incrementStackPointer(temp);
        }

        appendTextLine("LDR r0, =0");
        appendTextLine("POP {pc}");
        appendTextLine(".ltorg");

        textProgramBuilder.append(sysFuncProgramBuilder);

        return null;
    }

    @Override
    public Void visitReturn(@NotNull WACCParser.ReturnContext ctx) {
        loadOperands(ctx.expr());
        resetFreeRegisters();
        appendTextLine("MOV r0, r4");
        
        if (offsetRequired != 0) {
            int temp = offsetRequired;
            incrementStackPointer(temp);
        }
        
        appendTextLine("POP {pc}");
        return null;
    }

    @Override
    public Void visitBegin(@NotNull WACCParser.BeginContext ctx) {
        Scope<String> newScope = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        int oldInitCount = initCount;
        initCount = countInits(ctx.stat());
        currentScope.setMemSize(initCount);
        int tempCount = initCount;
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        visitChildren(ctx);
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        initCount = oldInitCount;
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitFunc(@NotNull WACCParser.FuncContext ctx) {
        Scope<String> newScope = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        appendTextLabel("f_" + ctx.IDENT().getText() + ":");
        appendTextLine("PUSH {lr}");
        int initCount = countInits(ctx.stat());
        currentScope.setMemSize(initCount);
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        offsetRequired = initCount;
        int count = WORD + initCount;
        
        if (ctx.param_list() != null) {
            for (WACCParser.ParamContext param : ctx.param_list().param()) {
            	currentScope.addAddress(param.IDENT().getText(), count);
                addIdentToScope(param.IDENT().getText(), param.type(), 0, 0);
                String type = param.type().getText();
                if (hasMemorySizeFour(type)) {
                    count += WORD;
                } else {
                    count++;
                }
            }
        }
        
        visitChildren(ctx);
        appendTextLine("POP {pc}");
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitIf_else(@NotNull WACCParser.If_elseContext ctx) {
        Scope<String> newScopeThen = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        Scope<String> newScopeElse = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        
        String label1 = getNextLabel();
        String label2 = getNextLabel();
        
        loadOperands(ctx.expr());
        resetFreeRegisters();
        
        appendTextLine("CMP r4, #0");
        appendTextLine("BEQ " + label1);
        
        int oldInitCount = initCount;
        initCount = countInits(ctx.stat(0));
        int tempCount = initCount;
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        currentScope.addScopeBelow(newScopeThen);
        currentScope = newScopeThen;
        currentScope.setMemSize(initCount);
        
        visit(ctx.stat(0));
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        appendTextLine("B " + label2);
        appendTextLabel(label1 + ":");
        
        currentScope = currentScope.getScopeAbove();
        currentScope.addScopeBelow(newScopeElse);
        currentScope = newScopeElse;
        
        initCount = countInits(ctx.stat(1));
        currentScope.setMemSize(initCount);
        tempCount = initCount;
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        visit(ctx.stat(1));
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        appendTextLabel(label2 + ":");
        initCount = oldInitCount;
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitIf(@NotNull WACCParser.IfContext ctx) {
        Scope<String> newScopeThen = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        String label1 = getNextLabel();
        
        loadOperands(ctx.expr());
        resetFreeRegisters();
        
        appendTextLine("CMP r4, #0");
        appendTextLine("BEQ " + label1);
        
        int oldInitCount = initCount;
        initCount = countInits(ctx.stat());
        int tempCount = initCount;
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        currentScope.addScopeBelow(newScopeThen);
        currentScope = newScopeThen;
        currentScope.setMemSize(initCount);
        
        visit(ctx.stat());
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        appendTextLabel(label1 + ":");
        
        initCount = oldInitCount;
        currentScope = currentScope.getScopeAbove();
        return null;
    }

    @Override
    public Void visitWhile(@NotNull WACCParser.WhileContext ctx) {
        Scope<String> newScope = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        
        String label1 = getNextLabel();
        String label2 = getNextLabel();
        
        int oldInitCount = initCount;
        initCount = countInits(ctx.stat());
        currentScope.setMemSize(initCount);
        int tempCount = initCount;
        appendTextLine("B " + label1);
        appendTextLabel(label2 + ":");
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        visit(ctx.stat());
        initCount = oldInitCount;
        appendTextLabel(label1 + ":");
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        int reg = loadOperands(ctx.expr());
        resetFreeRegisters();
        
        appendTextLine("CMP r" + reg + ", #1");
        appendTextLine("BEQ " + label2);
        currentScope = currentScope.getScopeAbove();
        return null;
    }
    
    @Override
    public Void visitDo_while(@NotNull WACCParser.Do_whileContext ctx) {
        Scope<String> newScope = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        
        String label1 = getNextLabel();
        
        int oldInitCount = initCount;
        initCount = countInits(ctx.stat());
        currentScope.setMemSize(initCount);
        int tempCount = initCount;
        appendTextLabel(label1 + ":");
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        visit(ctx.stat());
        initCount = oldInitCount;
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        int reg = loadOperands(ctx.expr());
        resetFreeRegisters();
        
        appendTextLine("CMP r" + reg + ", #1");
        appendTextLine("BEQ " + label1);
        currentScope = currentScope.getScopeAbove();
        return null;
    }
    
    @Override
    public Void visitFor(@NotNull WACCParser.ForContext ctx) {
        
        String label1 = getNextLabel();
        String label2 = getNextLabel();
        
        appendTextLabel(label1 + ":");
        
        int reg = loadOperands(ctx.expr(1));
        resetFreeRegisters();
        appendTextLine("CMP r" + reg + ", #0");
        appendTextLine("BEQ " + label2);
        
        int oldInitCount = initCount;
        
        initCount = countInits(ctx.stat(1));
        int tempCount = initCount;
        
        if (initCount != 0) {
            int temp = initCount;
            decrementStackPointer(temp);
        }
        
        Scope<String> newScope = new Scope<>(currentScope, errorReporter, new ValueTable(null, errorReporter));
        currentScope.addScopeBelow(newScope);
        currentScope = newScope;
        currentScope.setMemSize(initCount);
        scopeStackOffset = initCount;
        
        visit(ctx.stat(1));

        currentScope = currentScope.getScopeAbove();
        
        
        if (tempCount != 0) {
            int temp = tempCount;
            incrementStackPointer(temp);
        }
        
        initCount = oldInitCount;
                
        visit(ctx.stat(0));
        
        appendTextLine("B " + label1);
        appendTextLabel(label2 + ":");
           
    	return null;
    }

    @Override public Void visitInc_dec(@NotNull WACCParser.Inc_decContext ctx) {
//        List<Integer> tempFreeRegisters = new LinkedList<>();
//        tempFreeRegisters.addAll(freeRegisters);
        int reg = loadOperands(ctx.expr());
        resetFreeRegisters();
//        tempFreeRegisters.remove(new Integer(reg));
//        freeRegisters = tempFreeRegisters;
        Integer address = currentScope.getAddressGlobal(
        		ctx.expr().getText(), scopeStackOffset, forceOffset);
        
        if (reg == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg = 11;
        }
        
        if (ctx.post_unary_oper().getText().equals("++")) {
            appendTextLine("ADDS r" + reg + ", r" + reg + ", #1");
            appendTextLine("BLVS p_throw_overflow_error");
            addStr(reg, address, "int");
        }
        
        if (ctx.post_unary_oper().getText().equals("--")) {
            appendTextLine("SUBS r" + reg + ", r" + reg + ", #1");
            appendTextLine("BLVS p_throw_overflow_error");
            addStr(reg, address, "int");
        }
        
        AddTOE();
        return null;
    }

    /*
    * Puts all the idents in the stackTable and allocates memory based on
    * their types. It then generates code for the initialisation steps.
    * */
    @Override
    public Void visitInit(@NotNull WACCParser.InitContext ctx) {
        String type = ctx.type().getText();
        addIdentToScope(ctx.IDENT().getText(), ctx.type(), 0, 0);
        if (ctx.assign_rhs() instanceof WACCParser.Assign_exprContext) {
            WACCParser.ExprContext expr = ((WACCParser.Assign_exprContext) ctx.assign_rhs()).expr();
            if (expr instanceof WACCParser.IdentExprContext) {
                loadOperands(expr);
                resetFreeRegisters();
                addToStackTable(ctx.IDENT().getText(), type);
                addStr(4, initCount, type);
            }

            if (expr instanceof WACCParser.IntLiterExprContext) {
                long value = Long.parseLong(ctx.assign_rhs().getText());
                appendTextLine("LDR r4, =" + value);
                addToStackTable(ctx.IDENT().getText(), "int");
                addStr(4, initCount, type);
            }

            if (expr instanceof WACCParser.CharLiterExprContext) {
                String chr = ctx.assign_rhs().getText();
                int ord = escapeCharToInt(chr);
                if (ord != -1) {
                    appendTextLine("MOV r4, #" + ord);
                } else {
                    appendTextLine("MOV r4, #" + ctx.assign_rhs().getText());
                }
                addStr(4, initCount - BYTE, "char");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.BoolLiterExprContext) {
                if (((WACCParser.BoolLiterExprContext) expr).bool_liter().getText().equals("true")) {
                    appendTextLine("MOV r4, #1");
                } else {
                    appendTextLine("MOV r4, #0");
                }
                addStr(4, initCount - BYTE, "bool");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.PairLiterExprContext) {
                appendTextLine("LDR r4, =0");
                addStr(4, initCount - WORD, "pair");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.StringLiterExprContext) {
                appendTextLine("LDR r4, =" + "msg_" + stringLiterCount++);
                addStr(4, initCount - WORD, "string");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.BinaryAddMinusExprContext
                    || expr instanceof WACCParser.BinaryMultDivModExprContext) {
                visitChildren(ctx);
                resetFreeRegisters();
                addStr(4, initCount - WORD, "int");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.BinaryBoolExprContext
                    || expr instanceof WACCParser.BinaryCompExprContext
                    || expr instanceof WACCParser.BinaryEqExprContext) {
                visitChildren(ctx);
                resetFreeRegisters();
                addStr(4, initCount - BYTE, "bool");
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.UnaryOperExprContext) {
                WACCParser.Unary_operContext unaryOp = ((WACCParser.UnaryOperExprContext) expr).unary_oper();
                if (unaryOp instanceof WACCParser.NegateContext) {
                    long value = Long.parseLong(expr.getText());
                    appendTextLine("LDR r4, =" + value);
                    addStr(4, initCount - WORD, "int");
                    addToStackTable(ctx.IDENT().getText(), type);
                } else if (unaryOp instanceof WACCParser.OrdContext
                        || unaryOp instanceof WACCParser.LenContext) {
                    visitChildren(ctx);
                    resetFreeRegisters();
                    addStr(4, initCount - WORD, "int");
                    addToStackTable(ctx.IDENT().getText(), type);
                } else {
                    visitChildren(ctx);
                    resetFreeRegisters();
                    addStr(4, initCount - BYTE, type);
                    addToStackTable(ctx.IDENT().getText(), type);
                }
            }

            if (expr instanceof WACCParser.PostUnaryOperExprContext) {
                visitChildren(ctx);
                resetFreeRegisters();
                addStr(4, initCount - BYTE, type);
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.BracketedExprContext) {
                visit(((WACCParser.BracketedExprContext) expr).expr());
                addStr(4, initCount - WORD, type);
                addToStackTable(ctx.IDENT().getText(), type);
            }

            if (expr instanceof WACCParser.ArrayElemExprContext) {
                ArrayElemExprContext arrayElem = (ArrayElemExprContext) expr;
                String ident = arrayElem.array_elem().IDENT().getText();
                Integer arrayAddress = currentScope.getAddressGlobal(ident, scopeStackOffset, forceOffset);
                List<ExprContext> indexes = arrayElem.array_elem().expr();
                int nesting = indexes.size();
                String identType = currentScope.getTypeGlobal(ident, 0, 0);
                String innerArrayType = identType.substring(0, identType.length() - 2 * nesting);
                appendTextLine("ADD r4, sp, #" + arrayAddress);

                for (int i = 0; i < nesting - 1; i++) {
                    if (indexes.get(i) instanceof IntLiterExprContext) {
                        appendTextLine("LDR r5, =" + indexes.get(i).getText());
                    } else {
                        int indexAddress = currentScope.getAddressGlobal(indexes.get(i).getText(),
                                scopeStackOffset, forceOffset);
                        addLdr(5, indexAddress, "]");
                    }
                    appendTextLine("LDR r4, [r4]");
                    appendTextLine("MOV r0, r5");
                    appendTextLine("MOV r1, r4");
                    appendTextLine("BL p_check_array_bounds");
                    appendTextLine("ADD r4, r4, #4");
                    appendTextLine("ADD r4, r4, r5, LSL #2");
                }

                String lastIndex = indexes.get(nesting - 1).getText();
                if (indexes.get(nesting - 1) instanceof IntLiterExprContext) {
                    appendTextLine("LDR r5, =" + lastIndex);
                } else {
                    int indexAddress = currentScope.getAddressGlobal(lastIndex, scopeStackOffset, forceOffset);
                    addLdr(5, indexAddress, "]");
                }

                appendTextLine("LDR r4, [r4]");
                appendTextLine("MOV r0, r5");
                appendTextLine("MOV r1, r4");
                appendTextLine("BL p_check_array_bounds");
                addCAB();
                appendTextLine("ADD r4, r4, #4");

                if (hasMemorySizeFour(innerArrayType)) {
                    appendTextLine("ADD r4, r4, r5, LSL #2");
                    appendTextLine("LDR r4, [r4]");
                } else {
                    appendTextLine("ADD r4, r4, r5");
                    appendTextLine("LDRSB r4, [r4]");
                }

                if (hasMemorySizeFour(type)) {
                    addStr(4, initCount - WORD, type);
                    addToStackTable(ctx.IDENT().getText(), type);
                } else {
                    addStr(4, initCount - BYTE, type);
                    addToStackTable(ctx.IDENT().getText(), type);
                }
            }
        }

        if (ctx.assign_rhs() instanceof WACCParser.Array_literContext) {
            List<WACCParser.ExprContext> elemList = ((WACCParser
                    .Array_literContext) ctx.assign_rhs()).expr();
            int elemSize;
            String innerArrayType = type.substring(0, type.length() - 2);

            if (hasMemorySizeFour(innerArrayType)) {
                int memNeeded = WORD * (elemList.size() + 1);
                elemSize = WORD;
                appendTextLine("LDR r0, =" + memNeeded);
            } else {
                int memNeeded = elemList.size() + WORD;
                elemSize = BYTE;
                appendTextLine("LDR r0, =" + memNeeded);
            }

            appendTextLine("BL malloc");
            appendTextLine("MOV r4, r0");

            int memCount = WORD;

            if (innerArrayType.equals("int")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("LDR r5, =" + elem.getText());
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.endsWith("]")
                    || innerArrayType.startsWith("pair")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("LDR r5, [sp, #" +
                            currentScope.getAddressGlobal(((IdentExprContext) elem)
                                    .IDENT().getText(), scopeStackOffset, forceOffset) + "]");
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.equals("string")) {
                for (int i = 0; i < elemList.size(); i++) {
                    appendTextLine("LDR r5, =" + "msg_" + stringLiterCount++);
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.equals("char")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("MOV r5, #" + elem.getText());
                    appendTextLine("STRB r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else {
                for (WACCParser.ExprContext elem : elemList) {
                    if (elem.getText().equals("true")) {
                        appendTextLine("MOV r5, #1");
                    } else {
                        appendTextLine("MOV r5, #0");
                    }
                    appendTextLine("STRB r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            }

            appendTextLine("LDR r5, =" + elemList.size());
            appendTextLine("STR r5, [r4]");
            addStr(4, initCount - WORD, "]");
            addToStackTable(ctx.IDENT().getText(), type);
        }

        if (ctx.assign_rhs() instanceof WACCParser.New_pairContext) {
            appendTextLine("LDR r0, =8");
            appendTextLine("BL malloc");
            appendTextLine("MOV r4, r0");

            freeRegisters.remove(0);
            loadOperands(((WACCParser.New_pairContext) ctx.assign_rhs()).expr(0));
            resetFreeRegisters();
            String type1 = currentScope.getTypeGlobal(ctx.IDENT().getText() + ".fst", 0, 0);

            if (hasMemorySizeFour(type1)) {
                appendTextLine("LDR r0, =4");
            } else {
                appendTextLine("LDR r0, =1");
            }

            appendTextLine("BL malloc");

            if (hasMemorySizeFour(type1)) {
                appendTextLine("STR r5, [r0]");
            } else {
                appendTextLine("STRB r5, [r0]");
            }

            appendTextLine("STR r0, [r4]");

            freeRegisters.remove(0);
            loadOperands(((WACCParser.New_pairContext) ctx.assign_rhs()).expr(1));
            resetFreeRegisters();
            String type2 = currentScope.getTypeGlobal(ctx.IDENT().getText() + ".snd", 0, 0);

            if (hasMemorySizeFour(type2)) {
                appendTextLine("LDR r0, =4");
            } else {
                appendTextLine("LDR r0, =1");
            }

            appendTextLine("BL malloc");

            if (hasMemorySizeFour(type2)) {
                appendTextLine("STR r5, [r0]");
            } else {
                appendTextLine("STRB r5, [r0]");
            }

            appendTextLine("STR r0, [r4, #4]");
            addStr(4, initCount - WORD, "pair");
            addToStackTable(ctx.IDENT().getText(), type);
        }

        if (ctx.assign_rhs() instanceof WACCParser.Assign_rhs_pairContext) {
            loadOperands(((WACCParser.Assign_rhs_pairContext) ctx.assign_rhs()).pair_elem().expr());
            resetFreeRegisters();
            appendTextLine("MOV r0, r4");
            appendTextLine("BL p_check_null_pointer");

            if (((WACCParser.Assign_rhs_pairContext) ctx.assign_rhs()).pair_elem().FST() != null) {
                appendTextLine("LDR r4, [r4]");
            } else {
                appendTextLine("LDR r4, [r4, #4]");
            }

            if (hasMemorySizeFour(type)) {
                appendTextLine("LDR r4, [r4]");
            } else {
                appendTextLine("LDRSB r4, [r4]");
            }

            int temp;
            if (hasMemorySizeFour(type)) {
                temp = initCount - WORD;
            } else {
                temp = initCount - BYTE;
            }

            addStr(4, temp, type);
            addToStackTable(ctx.IDENT().getText(), type);
            AddCNPCheck();
        }

        if (ctx.assign_rhs() instanceof WACCParser.CallContext) {
            addToStackTable(ctx.IDENT().getText(), type);
            visitChildren(ctx);
            int address = currentScope.getAddressGlobal(ctx.IDENT().getText(), scopeStackOffset, forceOffset);
            addStr(4, address, type);
        }

        return null;
    }

    @Override
    public Void visitReassign(@NotNull WACCParser.ReassignContext ctx) {
        Integer address = null;
        String type = null;

        if (ctx.assign_lhs().IDENT() != null) {
            address = currentScope.getAddressGlobal(
            		ctx.assign_lhs().IDENT().getText(),
            		scopeStackOffset,forceOffset);
            type = currentScope.getTypeGlobal(
            		ctx.assign_lhs().IDENT().getText(), 0, 0);
        } else if (ctx.assign_lhs().array_elem() != null) {
            address = currentScope.getAddressGlobal(
            		ctx.assign_lhs().array_elem().IDENT().getText(),
            		scopeStackOffset, forceOffset);
            type = currentScope.getTypeGlobal(
            		ctx.assign_lhs().array_elem().IDENT().getText(), 0, 0);
        } else {
            address = currentScope.getAddressGlobal(
            		ctx.assign_lhs().pair_elem().expr().getText(),
            		scopeStackOffset, forceOffset);
            type = currentScope.getTypeGlobal(
            		ctx.assign_lhs().pair_elem().expr().getText(), 0, 0);
        }
            
        if (ctx.assign_rhs() instanceof WACCParser.Assign_exprContext) {
            WACCParser.Assign_exprContext expr = (WACCParser.Assign_exprContext) ctx.assign_rhs();
            int reg = loadOperands(expr.expr());
            if (expr.expr() instanceof WACCParser.ArrayElemExprContext) {
                loadOperands(expr.expr());
            }
            if (ctx.assign_lhs().pair_elem() != null) {
                int reg2 = loadOperands(ctx.assign_lhs().pair_elem().expr());
                String pairElemType = null;
                appendTextLine("MOV r0, r" + reg2);
                resetFreeRegisters();
                appendTextLine("BL p_check_null_pointer");
                if (ctx.assign_lhs().pair_elem().FST() != null) {
                    pairElemType = currentScope.getTypeGlobal(ctx.assign_lhs().pair_elem().expr().getText()
                            + ".fst", 0, 0);
                    appendTextLine("LDR r" + reg2 + ", [r" + reg2 + "]");
                } else if (ctx.assign_lhs().pair_elem().SND() != null) {
                    pairElemType = currentScope.getTypeGlobal(ctx.assign_lhs().pair_elem().expr().getText()
                            + ".snd", 0, 0);
                    appendTextLine("LDR r" + reg2 + ", [r" + reg2 + ", #4]");
                }
                if (pairElemType != null && (pairElemType.equals("bool") || pairElemType.equals("char"))) {
                    appendTextLine("STRB r" + reg + ", [r" + reg2 + "]");
                } else {
                    appendTextLine("STR r" + reg + ", [r" + reg2 + "]");
                }
                AddCNPCheck();
            } else if (ctx.assign_lhs().array_elem() == null) {
                addStr(4, address, type);
            }
            resetFreeRegisters();
        }
        resetFreeRegisters();
        if (ctx.assign_rhs() instanceof WACCParser.CallContext) {
            visitChildren(ctx);
            addStr(4, address, type);
        }

        if (ctx.assign_rhs() instanceof WACCParser.Array_literContext) {
            List<WACCParser.ExprContext> elemList = ((WACCParser.Array_literContext) ctx.assign_rhs()).expr();
            int elemSize;

            String innerArrayType = type.substring(0, type.length() - 2);

            if (hasMemorySizeFour(innerArrayType)) {
                int memNeeded = WORD * (elemList.size() + 1);
                elemSize = WORD;
                appendTextLine("LDR r0, =" + memNeeded);
            } else {
                int memNeeded = elemList.size() + BYTE;
                elemSize = BYTE;
                appendTextLine("LDR r0, =" + memNeeded);
            }

            appendTextLine("BL malloc");
            appendTextLine("MOV r4, r0");

            int memCount = WORD;

            if (innerArrayType.equals("int")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("LDR r5, =" + elem.getText());
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.endsWith("]")
                    || innerArrayType.equals("pair")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("LDR r5, [sp, #" +
                            currentScope.getAddressGlobal(((IdentExprContext) elem)
                                    .IDENT().getText(), scopeStackOffset, forceOffset) + "]");
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.equals("string")) {
                for (int i = 0; i < elemList.size(); i++) {
                    appendTextLine("LDR r5, =" + "msg_" + stringLiterCount++);
                    appendTextLine("STR r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else if (innerArrayType.equals("char")) {
                for (WACCParser.ExprContext elem : elemList) {
                    appendTextLine("MOV r5, #" + elem.getText());
                    appendTextLine("STRB r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            } else {
                for (WACCParser.ExprContext elem : elemList) {
                    if (elem.getText().equals("true")) {
                        appendTextLine("MOV r5, #1");
                    } else {
                        appendTextLine("MOV r5, #0");
                    }

                    appendTextLine("STRB r5, [r4, #" + memCount + "]");
                    memCount += elemSize;
                }
            }

            appendTextLine("LDR r5, =" + elemList.size());
            appendTextLine("STR r5, [r4]");
            appendTextLine("STR r4, [sp]");
        }

        if (ctx.assign_rhs() instanceof WACCParser.New_pairContext) {
            appendTextLine("LDR r0, =8");
            appendTextLine("BL malloc");
            appendTextLine("MOV r4, r0");


            loadOperands(((WACCParser.New_pairContext) ctx.assign_rhs()).expr(0));
            resetFreeRegisters();
            String type1 = currentScope.getTypeGlobal(ctx.assign_lhs().IDENT().getText() + ".fst", 0, 0);

            if (hasMemorySizeFour(type1)) {
                appendTextLine("LDR r0, =4");
            } else {
                appendTextLine("LDR r0, =1");
            }

            appendTextLine("BL malloc");
            appendTextLine("STR r5, [r0]");
            appendTextLine("STR r0, [r4]");

            loadOperands(((WACCParser.New_pairContext) ctx.assign_rhs()).expr(1));
            resetFreeRegisters();
            String type2 = currentScope.getTypeGlobal(ctx.assign_lhs().IDENT().getText() + ".snd", 0, 0);

            if (hasMemorySizeFour(type2)) {
                appendTextLine("LDR r0, =4");
            } else {
                appendTextLine("LDR r0, =1");
            }

            appendTextLine("BL malloc");
            appendTextLine("STR r5, [r0]");
            appendTextLine("STR r0, [r4, #4]");

            addStr(4, address, "pair");
        }

        if (ctx.assign_rhs() instanceof WACCParser.Assign_rhs_pairContext) {
            WACCParser.Pair_elemContext pairElem = ((WACCParser.Assign_rhs_pairContext) ctx.assign_rhs()).pair_elem();
            loadOperands(pairElem.expr());
            resetFreeRegisters();
            appendTextLine("MOV r0, r4");
            appendTextLine("BL p_check_null_pointer");

            String pairElemType;

            if (pairElem.FST() != null) {
                pairElemType = currentScope.getTypeGlobal(pairElem.expr().getText() + ".fst", 0, 0);
                appendTextLine("LDR r4, [r4]");
            } else {
                pairElemType = currentScope.getTypeGlobal(pairElem.expr().getText() + ".snd", 0, 0);
                appendTextLine("LDR r4, [r4, #4]");
            }

            if (!hasMemorySizeFour(pairElemType)) {
                appendTextLine("LDRSB r4, [r4]");
            } else {
                appendTextLine("LDR r4, [r4]");
            }

            addStr(4, address, pairElemType);
        }

        if (ctx.assign_lhs().array_elem() != null) {

            String ident = ctx.assign_lhs().array_elem().IDENT().getText();
            Integer arrayAddress = address;
            List<ExprContext> indexes = ctx.assign_lhs().array_elem().expr();
            int nesting = indexes.size();
            String identType = currentScope.getTypeGlobal(ident, 0, 0);
            String innerArrayType = identType.substring(0, identType.length() - 2 * nesting);
            appendTextLine("ADD r5, sp, #" + arrayAddress);

            for (int i = 0; i < nesting - 1; i++) {
                if (indexes.get(i) instanceof IdentExprContext) {
                    int indexAddress = currentScope.getAddressGlobal(indexes.get(i).getText(),
                            scopeStackOffset, forceOffset);
                    if (indexAddress != 0) {
                        appendTextLine("LDR r6 + , [sp, #" + indexAddress + "]");
                    } else {
                        appendTextLine("LDR r6, [sp]");
                    }
                } else {
                    appendTextLine("LDR r6, =" + indexes.get(i).getText());
                }
                appendTextLine("LDR r5, [r5]");
                appendTextLine("MOV r0, r6");
                appendTextLine("MOV r1, r5");
                appendTextLine("BL p_check_array_bounds");
                appendTextLine("ADD r5, r5, #4");
                appendTextLine("ADD r5, r5, r6"
                        + ", LSL #2");
            }

            String lastIndex = indexes.get(nesting - 1).getText();

            if (indexes.get(nesting - 1) instanceof IntLiterExprContext) {
                appendTextLine("LDR r6" + ", =" + lastIndex);
            } else {
                int indexAddress = currentScope.getAddressGlobal(lastIndex, scopeStackOffset, forceOffset);
                addLdr(6, indexAddress, "]");
            }

            appendTextLine("LDR r5, [r5]");
            appendTextLine("MOV r0, r6");
            appendTextLine("MOV r1, r5");
            appendTextLine("BL p_check_array_bounds");
            addCAB();
            appendTextLine("ADD r5, r5, #4");

            if (hasMemorySizeFour(innerArrayType)) {
                appendTextLine("ADD r5, r5, r6, LSL #2");
                appendTextLine("STR r4, [r5]");
            } else {
                appendTextLine("ADD r5, r5, r6");
                appendTextLine("STRB r4, [r5]");
            }
        }
        return null;
    }

    @Override
    public Void visitBinaryAddMinusExpr(
            @NotNull WACCParser.BinaryAddMinusExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg1 = loadOperands(ctx.expr(0));
        tempFreeRegisters.remove(new Integer(reg1));
        freeRegisters = tempFreeRegisters;

        int reg2 = loadOperands(ctx.expr(1));
        freeRegisters = tempFreeRegisters;
        if (reg2 == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg2 = 11;
        }
        if (ctx.binary_oper.getText().equals("+")) {
            appendTextLine("ADDS r" + reg1 + ", r" + reg1 + ", r" + reg2);
        }
        if (ctx.binary_oper.getText().equals("-")) {
            appendTextLine("SUBS r" + reg1 + ", r" + reg1 + ", r" + reg2);
        }
        appendTextLine("BLVS p_throw_overflow_error");
        AddTOE();
        return null;
    }

    @Override
    public Void visitBinaryMultDivModExpr(
            @NotNull WACCParser.BinaryMultDivModExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg1 = loadOperands(ctx.expr(0));
        tempFreeRegisters.remove(new Integer(reg1));
        freeRegisters = tempFreeRegisters;

        int reg2 = loadOperands(ctx.expr(1));
        freeRegisters = tempFreeRegisters;
        if (reg2 == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg2 = 11;
        }
        if (ctx.binary_oper.getText().equals("*")) {
            appendTextLine("SMULL r" + reg1 + ", r" + reg2 + ", r" + reg1 + ", r"
                    + reg2);
            appendTextLine("CMP r" + reg2 + ", r" + reg1 + ", ASR #31");
            appendTextLine("BLNE p_throw_overflow_error");
            AddTOE();
            return null;
        }
        appendTextLine("MOV r0, r" + reg1);
        appendTextLine("MOV r1, r" + reg2);
        appendTextLine("BL p_check_divide_by_zero");
        AddDBZCheck();
        if (ctx.binary_oper.getText().equals("/")) {
            appendTextLine("BL __aeabi_idiv");
            appendTextLine("MOV r" + reg1 + ", r0");
        }
        if (ctx.binary_oper.getText().equals("%")) {
            appendTextLine("BL __aeabi_idivmod");
            appendTextLine("MOV r" + reg1 + ", r1");
        }
        return null;
    }

    @Override
    public Void visitBinaryCompExpr(
            @NotNull WACCParser.BinaryCompExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg1 = loadOperands(ctx.expr(0));
        tempFreeRegisters.remove(new Integer(reg1));
        freeRegisters = tempFreeRegisters;

        int reg2 = loadOperands(ctx.expr(1));
        freeRegisters = tempFreeRegisters;
        if (reg2 == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg2 = 11;
        }
        appendTextLine("CMP r" + reg1 + ", r" + reg2);
        if (ctx.binary_oper.getText().equals(">")) {
            appendTextLine("MOVGT r" + reg1 + ", #1");
            appendTextLine("MOVLE r" + reg1 + ", #0");
        }
        if (ctx.binary_oper.getText().equals(">=")) {
            appendTextLine("MOVGE r" + reg1 + ", #1");
            appendTextLine("MOVLT r" + reg1 + ", #0");
        }
        if (ctx.binary_oper.getText().equals("<")) {
            appendTextLine("MOVLT r" + reg1 + ", #1");
            appendTextLine("MOVGE r" + reg1 + ", #0");
        }
        if (ctx.binary_oper.getText().equals("<=")) {
            appendTextLine("MOVLE r" + reg1 + ", #1");
            appendTextLine("MOVGT r" + reg1 + ", #0");
        }
        return null;
    }

    @Override
    public Void visitBinaryEqExpr(
            @NotNull WACCParser.BinaryEqExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg1 = loadOperands(ctx.expr(0));
        tempFreeRegisters.remove(new Integer(reg1));
        freeRegisters = tempFreeRegisters;

        int reg2 = loadOperands(ctx.expr(1));
        freeRegisters = tempFreeRegisters;
        if (reg2 == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg2 = 11;
        }
        appendTextLine("CMP r" + reg1 + ", r" + reg2);
        if (ctx.binary_oper.getText().equals("==")) {
            appendTextLine("MOVEQ r" + reg1 + ", #1");
            appendTextLine("MOVNE r" + reg1 + ", #0");
        }
        if (ctx.binary_oper.getText().equals("!=")) {
            appendTextLine("MOVNE r" + reg1 + ", #1");
            appendTextLine("MOVEQ r" + reg1 + ", #0");
        }
        return null;
    }

    @Override
    public Void visitBinaryBoolExpr(
            @NotNull WACCParser.BinaryBoolExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg1 = loadOperands(ctx.expr(0));
        tempFreeRegisters.remove(new Integer(reg1));
        freeRegisters = tempFreeRegisters;

        int reg2 = loadOperands(ctx.expr(1));
        freeRegisters = tempFreeRegisters;
        if (reg2 == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg2 = 11;
        }
        if (ctx.binary_oper.getText().equals("&&")) {
            appendTextLine("AND r" + reg1 + ", r" + reg1 + ", r" + reg2);
        }
        if (ctx.binary_oper.getText().equals("||")) {
            appendTextLine("ORR r" + reg1 + ", r" + reg1 + ", r" + reg2);
        }
        return null;
    }

    @Override
    public Void visitUnaryOperExpr(
            @NotNull WACCParser.UnaryOperExprContext ctx) {

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        if (ctx.unary_oper().getText().equals("-")) {
            loadNegInt = true;
        }
        int reg = loadOperands(ctx.expr());
        loadNegInt = false;
        tempFreeRegisters.remove(new Integer(reg));
        freeRegisters = tempFreeRegisters;
        if (reg == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg = 11;
        }
        if (ctx.unary_oper().getText().equals("!")) {
            appendTextLine("EOR r" + reg + ", r" + reg + ", #1");
        }
        if (ctx.unary_oper().getText().equals("-")) {
            appendTextLine("RSBS r" + reg + ", r" + reg + ", #0");
            appendTextLine("BLVS p_throw_overflow_error");
            AddTOE();
        }
        if (ctx.unary_oper().getText().equals("len")) {
            appendTextLine("LDR r" + reg + ", [r" + reg + "]");
        }
        return null;
    }

    @Override
    public Void visitPostUnaryOperExpr(@NotNull WACCParser.PostUnaryOperExprContext ctx) {
        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        int reg = loadOperands(ctx.expr());
        tempFreeRegisters.remove(new Integer(reg));
        freeRegisters = tempFreeRegisters;
        Integer address = currentScope.getAddressGlobal(
        		ctx.expr().getText(), scopeStackOffset, forceOffset);
        
        if (reg == 10 && pushCount > 0) {
            appendTextLine("POP {r11}");
            pushCount--;
            reg = 11;
        }
        
        if (ctx.post_unary_oper().getText().equals("++")) {
            appendTextLine("PUSH {r" + reg + "}");
            appendTextLine("ADDS r" + reg + ", r" + reg + ", #1");
            appendTextLine("BLVS p_throw_overflow_error");
            addStr(reg, address + WORD, "int");
            appendTextLine("POP {r" + reg + "}");
        }
        
        if (ctx.post_unary_oper().getText().equals("--")) {
            appendTextLine("PUSH {r" + reg + "}");
            appendTextLine("SUBS r" + reg + ", r" + reg + ", #1");
            appendTextLine("BLVS p_throw_overflow_error");
            addStr(reg, address + WORD, "int");
            appendTextLine("POP {r" + reg + "}");
        }
        
        AddTOE();
        return null;
    }

    @Override
    public Void visitCall(@NotNull WACCParser.CallContext ctx) {
        int totalMemory = 0;

        if (ctx.STD() != null) {
            List<StringBuilder> libFunc = stdLib.add(ctx.IDENT().getText());
            if (libFunc != null) {
                dataProgramBuilder.append(libFunc.get(0));
                sysFuncProgramBuilder.append(libFunc.get(1));
                for (int i = 2; i < libFunc.size(); i++) {
                    addSysFunc(libFunc.get(i).toString());
                }
            }
        }

        if (ctx.arg_list() != null) {
            int oldSOffset = scopeStackOffset;
            int previousArgSize = 0;
            for (int i = ctx.arg_list().expr().size() - 1; i >= 0; i--) {
                WACCParser.ExprContext arg = ctx.arg_list().expr(i);
                int address = -calculateMemSize(arg);
                scopeStackOffset = previousArgSize + oldSOffset;
                forceOffset = true;
                loadOperands(arg);
                forceOffset = false;
                resetFreeRegisters();

                if (calculateMemSize(arg) == WORD) {
                    if (address != 0) {
                        appendTextLine("STR r4, [sp, #" + address + "]!");
                    } else {
                        appendTextLine("STR r4, [sp]!");
                    }
                } else {
                    if (address != 0) {
                        appendTextLine("STRB r4, [sp, #" + address + "]!");
                    } else {
                        appendTextLine("STRB r4, [sp]!");
                    }
                }
                
                totalMemory += calculateMemSize(arg);
                previousArgSize = -address;
                scopeStackOffset = oldSOffset;
            }
        }

        if (ctx.STD() == null) {
            appendTextLine("BL " + "f_" + ctx.IDENT().getText());
        } else {
            appendTextLine("BL " + "f_std_" + ctx.IDENT().getText());
        }
        
        if (totalMemory != 0) {
            int temp = totalMemory;
            incrementStackPointer(temp);
        }
        
        appendTextLine("MOV r4, r0");
        return null;
    }

    @Override
    public Void visitExit(@NotNull WACCParser.ExitContext ctx) {
        if (ctx.expr() instanceof WACCParser.UnaryOperExprContext) {
            ExprContext expr = ctx.expr();
            if (((WACCParser.UnaryOperExprContext) expr).unary_oper() instanceof WACCParser.NegateContext) {
                int reg = loadOperands(ctx.expr());
                if (expr.getChild(1) instanceof IntLiterExprContext) {
                    long value = Long.parseLong(expr.getText());
                    appendTextLine("LDR r" + reg + ", =" + value);
                    appendTextLine("MOV r0, r" + reg);
                    appendTextLine("BL exit");
                } else {
                    visit(ctx.expr());
                    appendTextLine("MOV r0, r" + reg);
                    appendTextLine("BL exit");
                }
                resetFreeRegisters();
            }
            return null;
        } else {
            int reg = loadOperands(ctx.expr());
            visit(ctx.expr());
            appendTextLine("MOV r0, r" + reg);
            appendTextLine("BL exit");
            resetFreeRegisters();
            return visitChildren(ctx);
        }
    }

    @Override
    public Void visitRead(@NotNull WACCParser.ReadContext ctx) {
        if (ctx.assign_lhs().pair_elem() != null) {
            loadOperands((ctx.assign_lhs()).pair_elem().expr());
            resetFreeRegisters();
            appendTextLine("MOV r0, r4");
            appendTextLine("BL p_check_null_pointer");
            AddCNPCheck();

            Integer address = currentScope.getAddressGlobal(ctx.assign_lhs().pair_elem().expr().getText(),
                    scopeStackOffset, forceOffset);

            if (ctx.assign_lhs().pair_elem().SND() != null) {
                address += WORD;
            }
            addLdr(4, address, "pair");
        } else {
            Integer address = currentScope.getAddressGlobal(ctx.assign_lhs().IDENT().getText(),
                    scopeStackOffset, forceOffset);
            appendTextLine("ADD r4, sp, #" + address);
        }
        appendTextLine("MOV r0, r4");
        String type;

        if (ctx.assign_lhs().IDENT() != null) {
            type = currentScope.getTypeGlobal(ctx.assign_lhs().IDENT().getText(), 0, 0);
        } else if (ctx.assign_lhs().array_elem() != null) {
            type = currentScope.getTypeGlobal(ctx.assign_lhs().array_elem().IDENT().getText(), 0, 0);
        } else {
            if (ctx.assign_lhs().pair_elem().FST() != null) {
                type = currentScope.getTypeGlobal(ctx.assign_lhs().pair_elem().expr().getText() + ".fst", 0, 0);
            } else {
                type = currentScope.getTypeGlobal(ctx.assign_lhs().pair_elem().expr().getText() + ".snd", 0, 0);
            }
        }

        if (type.equals("int")) {
            appendTextLine("BL p_read_int");
            AddRead("int");
        } else {
            appendTextLine("BL p_read_char");
            AddRead("char");
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitFree(@NotNull WACCParser.FreeContext ctx) {
        loadOperands(ctx.expr());
        resetFreeRegisters();
        appendTextLine("MOV r0, r4");
        appendTextLine("BL p_free_pair");
        AddFP();
        return visitChildren(ctx);
    }

    @Override
    public Void visitPrint(@NotNull WACCParser.PrintContext ctx) {
        ExprContext expr = ctx.expr();
        printHelper(expr);
        return null;
    }

    @Override
    public Void visitPrintln(@NotNull WACCParser.PrintlnContext ctx) {
        ExprContext expr = ctx.expr();
        printHelper(expr);
        appendTextLine("BL p_print_ln");
        AddPrintLn();
        return null;
    }

    private void printHelper(ExprContext expr) {
        loadOperands(expr);
        resetFreeRegisters();
        appendTextLine("MOV r0, r4");
        if (expr instanceof IdentExprContext) {
            String type = currentScope.getTypeGlobal(((IdentExprContext) expr).IDENT().getText(), 0, 0);
            switch (type) {
                case "int":
                    appendTextLine("BL p_print_int");
                    AddPrintInt();
                    break;
                case "bool":
                    appendTextLine("BL p_print_bool");
                    AddPrintBool();
                    break;
                case "char":
                    appendTextLine("BL putchar");
                    break;
                case "string":
                case "char[]":
                    appendTextLine("BL p_print_string");
                    AddPrintString();
                    break;
                default:
                    appendTextLine("BL p_print_reference");
                    AddPrintReference();
                    break;
            }
        }
        if (expr instanceof IntLiterExprContext) {
            appendTextLine("BL p_print_int");
            AddPrintInt();
        } else if (expr instanceof WACCParser.BoolLiterExprContext) {
            appendTextLine("BL p_print_bool");
            AddPrintBool();
        } else if (expr instanceof WACCParser.CharLiterExprContext) {
            appendTextLine("BL putchar");
        } else if (expr instanceof WACCParser.StringLiterExprContext) {
            appendTextLine("BL p_print_string");
            AddPrintString();
        } else if (expr instanceof WACCParser.ArrayElemExprContext) {
            ArrayElemExprContext arrayElem = (ArrayElemExprContext) expr;
            String ident = arrayElem.array_elem().IDENT().getText();
            List<ExprContext> indexes = arrayElem.array_elem().expr();
            int nesting = indexes.size();
            String identType = currentScope.getTypeGlobal(ident, 0, 0);
            String printType = identType.substring(0, identType.length() - 2 * nesting);

            switch (printType) {
                case "int":
                    appendTextLine("BL p_print_int");
                    AddPrintInt();
                    break;
                case "bool":
                    appendTextLine("BL p_print_bool");
                    AddPrintBool();
                    break;
                case "char":
                    appendTextLine("BL putchar");
                    break;
                case "string":
                case "char[]":
                    appendTextLine("BL p_print_string");
                    AddPrintString();
                    break;
                default:
                    appendTextLine("BL p_print_reference");
                    AddPrintReference();
                    break;
            }
        } else if (expr instanceof WACCParser.PairLiterExprContext) {
            appendTextLine("BL p_print_reference");
            AddPrintReference();
        } else if (expr instanceof WACCParser.BinaryAddMinusExprContext
                || expr instanceof WACCParser.BinaryMultDivModExprContext) {
            appendTextLine("BL p_print_int");
            AddPrintInt();
        } else if (expr instanceof WACCParser.BinaryBoolExprContext
                || expr instanceof WACCParser.BinaryCompExprContext
                || expr instanceof WACCParser.BinaryEqExprContext) {
            appendTextLine("BL p_print_bool");
            AddPrintBool();
        } else if (expr instanceof WACCParser.PostUnaryOperExprContext) {
            appendTextLine("BL p_print_int");
            AddPrintInt();
        }
        else if (expr instanceof WACCParser.UnaryOperExprContext) {
            WACCParser.Unary_operContext unaryOper = ((WACCParser.UnaryOperExprContext) expr).unary_oper();
            if (unaryOper instanceof WACCParser.NegateContext
                    || unaryOper instanceof WACCParser.OrdContext
                    || unaryOper instanceof WACCParser.LenContext) {
                appendTextLine("BL p_print_int");
                AddPrintInt();
            } else if (unaryOper instanceof WACCParser.ExclContext) {
                appendTextLine("BL p_print_bool");
                AddPrintBool();
            } else {
                appendTextLine("BL putchar");
            }
        }
    }

    private void addToStackTable(String ident, String type) {
        if (hasMemorySizeFour(type)) {
            initCount -= WORD;
            currentScope.addAddress(ident, initCount);
        } else {
            initCount--;
            currentScope.addAddress(ident, initCount);
        }
    }

    private int loadOperands(WACCParser.ExprContext expr) {
        int reg;
        reg = getFreeReg(expr);

        List<Integer> tempFreeRegisters = new LinkedList<>();
        tempFreeRegisters.addAll(freeRegisters);
        tempFreeRegisters.remove(new Integer(reg));

        if (isOperation(expr)) {
            visit(expr);
            return reg;
        }

        if (expr instanceof WACCParser.IdentExprContext) {
            String ident = ((IdentExprContext) expr).IDENT().getText();
            String type = currentScope.getTypeGlobal(ident, 0, 0);
            Integer address = currentScope.getAddressGlobal(ident, scopeStackOffset, forceOffset);
            addLdr(reg, address, type);
        } else if (expr instanceof WACCParser.IntLiterExprContext) {
            long value = Long.parseLong(expr.getText());
            if (!loadNegInt) {
                appendTextLine("LDR r" + reg + ", =" + value);
            } else {
                appendTextLine("LDR r" + reg + ", =-" + value);
            }
        } else if (expr instanceof WACCParser.BoolLiterExprContext) {
            if (expr.getText().equals("true")) {
                appendTextLine("MOV r" + reg + ", #1");
            } else {
                appendTextLine("MOV r" + reg + ", #0");
            }
        } else if (expr instanceof WACCParser.StringLiterExprContext) {
            appendTextLine("LDR r4, =" + "msg_" + stringLiterCount++);
        } else if (expr instanceof WACCParser.CharLiterExprContext) {
            String chr = ((WACCParser.CharLiterExprContext) expr).CHAR_LITER().getText();
            int ord = escapeCharToInt(chr);
            if (ord != -1) {
                appendTextLine("MOV r" + reg + ", #" + ord);
            } else {
                appendTextLine("MOV r" + reg + ", #" + chr);
            }
        } else if (expr instanceof WACCParser.PairLiterExprContext) {
            appendTextLine("LDR r" + reg + ", =0");
        } else if (expr instanceof WACCParser.ArrayElemExprContext) {
            ArrayElemExprContext arrayElem = (ArrayElemExprContext) expr;
            String ident = arrayElem.array_elem().IDENT().getText();
            Integer arrayAddress = currentScope.getAddressGlobal(
            		ident, scopeStackOffset, forceOffset);
            List<ExprContext> indexes = arrayElem.array_elem().expr();
            int nesting = indexes.size();
            String identType = currentScope.getTypeGlobal(ident, 0, 0);
            String innerArrayType =
                    identType.substring(0, identType.length() - 2 * nesting);
            appendTextLine("ADD r" + reg + ", sp, #" + arrayAddress);

            for (int i = 0; i < nesting - 1; i++) {
                if (indexes.get(i) instanceof IntLiterExprContext) {
                    appendTextLine("LDR r" + (reg + 1) + ", =" +
                    		indexes.get(i).getText());

                } else {
                    int indexAddress = currentScope.getAddressGlobal(
                    		indexes.get(i).getText(),
                    		scopeStackOffset, forceOffset);
                    addLdr(reg + 1, indexAddress, "]");
                }
                appendTextLine("LDR r" + reg + ", [r" + reg + "]");
                appendTextLine("MOV r0, r" + (reg + 1));
                appendTextLine("MOV r1, r" + reg);
                appendTextLine("BL p_check_array_bounds");
                appendTextLine("ADD r" + reg + ", r" + reg + ", #4");
                appendTextLine("ADD r" + reg + ", r" + reg + ", r" + (reg + 1) + ", LSL #2");

            }

            String lastIndex = indexes.get(nesting - 1).getText();
            if (indexes.get(nesting - 1) instanceof IntLiterExprContext) {
                appendTextLine("LDR r" + (reg + 1) + ", =" + lastIndex);
            } else if (lastIndex.charAt(0) == '-') {
                appendTextLine("LDR r" + (reg + 1) + ", =" + lastIndex);
            } else {
                int indexAddress = currentScope.getAddressGlobal(
                		lastIndex, scopeStackOffset, forceOffset);
                addLdr(reg + 1, indexAddress, "]");
            }

            appendTextLine("LDR r" + reg + ", [r" + reg+ "]");
            appendTextLine("MOV r0, r" + (reg + 1));
            appendTextLine("MOV r1, r" + reg);
            appendTextLine("BL p_check_array_bounds");

            addCAB();
            appendTextLine("ADD r" + reg + ", r" + reg + ", #4");

            if (hasMemorySizeFour(innerArrayType)) {
                appendTextLine("ADD r" + reg + ", r" + reg + ", r" + (reg + 1) + ", LSL #2");
                appendTextLine("LDR r" + reg + ", [r" + reg + "]");
            } else {
                appendTextLine("ADD r" + reg + ", r" + reg + ", r" + (reg + 1));
                appendTextLine("LDRSB r" + reg + ", [r" + reg + "]");
            }
        } else {
            visit(expr);
        }
        freeRegisters = tempFreeRegisters;
        return reg;
    }

    private int getFreeReg(ExprContext expr) {
        int reg;
        if (freeRegisters.isEmpty()) {
            reg = 10;
            if (!isOperation(expr)) {
                appendTextLine("PUSH {r10}");
                pushCount++;
            }
        } else {
            reg = freeRegisters.get(0);
        }
        return reg;
    }

    private void incrementStackPointer(int temp) {
        while (true) {
            if (temp > MAX_IMMEDIATE) {
                appendTextLine("ADD sp, sp, #" + MAX_IMMEDIATE);
                temp -= MAX_IMMEDIATE;
            } else {
                appendTextLine("ADD sp, sp, #" + temp);
                break;
            }
        }
    }

    private void decrementStackPointer(int temp) {
        while (true) {
            if (temp > MAX_IMMEDIATE) {
                appendTextLine("SUB sp, sp, #" + MAX_IMMEDIATE);
                temp -= MAX_IMMEDIATE;
            } else {
                appendTextLine("SUB sp, sp, #" + temp);
                break;
            }
        }
    }

    private boolean isOperation(ExprContext expr) {
        if (expr instanceof WACCParser.BracketedExprContext) {
            return isOperation(((WACCParser.BracketedExprContext) expr).expr());
        }
        return expr instanceof WACCParser.BinaryAddMinusExprContext
                || expr instanceof WACCParser.BinaryCompExprContext
                || expr instanceof WACCParser.BinaryEqExprContext
                || expr instanceof WACCParser.BinaryMultDivModExprContext
                || expr instanceof WACCParser.BinaryBoolExprContext
                || expr instanceof WACCParser.UnaryOperExprContext;
    }

    private void addStr(int reg, int stackAddr, String type) {
        if (hasMemorySizeFour(type)) {
            if (stackAddr != 0) {
                appendTextLine("STR r" + reg + ", [sp, #" + stackAddr + "]");
            } else {
                appendTextLine("STR r" + reg + ", [sp]");
            }
        } else {
            if (stackAddr != 0) {
                appendTextLine("STRB r" + reg + ", [sp, #" + stackAddr + "]");
            } else {
                appendTextLine("STRB r" + reg + ", [sp]");
            }
        }
    }

    private void addLdr(int reg, int stackAddr, String type) {
        if (hasMemorySizeFour(type)) {
            if (stackAddr != 0) {
                appendTextLine("LDR r" + reg + ", [sp, #" + stackAddr + "]");
            } else {
                appendTextLine("LDR r" + reg + ", [sp]");
            }
        } else {
            if (stackAddr != 0) {
                appendTextLine("LDRSB r" + reg + ", [sp, #" + stackAddr + "]");
            } else {
                appendTextLine("LDRSB r" + reg + ", [sp]");
            }
        }
    }

    private int calculateMemSize(WACCParser.ExprContext expr) {
        if (expr instanceof WACCParser.IdentExprContext) {
            String type = getExprType(expr);
            if (hasMemorySizeFour(type)) {
                return WORD;
            }
            return BYTE;
        }
        if (expr instanceof WACCParser.StringLiterExprContext
                || expr instanceof WACCParser.PairLiterExprContext
                || expr instanceof WACCParser.ArrayElemExprContext
                || expr instanceof WACCParser.IntLiterExprContext) {
            return WORD;
        }
        if (expr instanceof WACCParser.BinaryAddMinusExprContext
                || expr instanceof WACCParser.BinaryMultDivModExprContext) {
            return WORD;
        }
        if (expr instanceof UnaryOperExprContext) {
            if (((UnaryOperExprContext) expr).unary_oper() instanceof NegateContext
                    || ((UnaryOperExprContext) expr).unary_oper() instanceof LenContext
                    || ((UnaryOperExprContext) expr).unary_oper() instanceof OrdContext)
            return WORD;
        }
        if (expr instanceof PostUnaryOperExprContext) {
            return WORD;
        }
        if (expr instanceof BracketedExprContext) return calculateMemSize(((BracketedExprContext) expr).expr());
        return BYTE;
    }

    private boolean hasMemorySizeFour(String type) {
        return type.equals("int") || type.endsWith("]") || type.startsWith("pair") || type.equals("string");
    }

    private String getNextLabel() {
        return "L" + labelCount++;
    }

    private int countInits(WACCParser.StatContext stat) {
        int count = 0;

        if (stat instanceof WACCParser.InitContext) {
            String type = ((WACCParser.InitContext) stat).type().getText();
            WACCParser.Assign_rhsContext assign_rhsContext = ((WACCParser.InitContext) stat).assign_rhs();

            if (assign_rhsContext instanceof WACCParser.Assign_exprContext) {
                WACCParser.ExprContext expr = ((WACCParser.Assign_exprContext) assign_rhsContext).expr();
                if (expr instanceof WACCParser.IdentExprContext) {
                    if (hasMemorySizeFour(type)) {
                        count += WORD;
                    } else {
                        count += BYTE;
                    }
                }
                if (expr instanceof WACCParser.BracketedExprContext) {
                    if (hasMemorySizeFour(type)) {
                        count += WORD;
                    } else {
                        count++;
                    }
                }
                if (expr instanceof WACCParser.IntLiterExprContext
                        || expr instanceof WACCParser.PairLiterExprContext
                        || expr instanceof WACCParser.StringLiterExprContext) {
                    count += WORD;
                }
                if (expr instanceof WACCParser.CharLiterExprContext
                        || expr instanceof WACCParser.BoolLiterExprContext) {
                    count++;
                }
                if (expr instanceof WACCParser.BinaryAddMinusExprContext
                        || expr instanceof WACCParser.BinaryMultDivModExprContext) {
                    count += WORD;
                }
                if (expr instanceof WACCParser.BinaryBoolExprContext
                        || expr instanceof WACCParser.BinaryCompExprContext
                        || expr instanceof WACCParser.BinaryEqExprContext) {
                    count++;
                }
                if (expr instanceof WACCParser.UnaryOperExprContext) {
                    if (((WACCParser.UnaryOperExprContext) expr).unary_oper() instanceof WACCParser.NegateContext
                            || ((WACCParser.UnaryOperExprContext) expr).unary_oper() instanceof WACCParser.OrdContext
                            || ((WACCParser.UnaryOperExprContext) expr).unary_oper() instanceof WACCParser.LenContext) {
                        count += WORD;
                    } else {
                        count++;
                    }

                }
                if (expr instanceof WACCParser.ArrayElemExprContext) {
                    if (hasMemorySizeFour(type)) {
                        count += WORD;
                    } else {
                        count++;
                    }
                }
            }
            if (assign_rhsContext instanceof WACCParser.Array_literContext) {
                count += WORD;
            }
            if (assign_rhsContext instanceof WACCParser.New_pairContext) {
                count += WORD;
            }
            if (assign_rhsContext instanceof WACCParser.Assign_rhs_pairContext) {
                if (hasMemorySizeFour(type)) {
                    count += WORD;
                } else {
                    count++;
                }
            }
            if (assign_rhsContext instanceof WACCParser.CallContext) {
                if (hasMemorySizeFour(type)) {
                    count += WORD;
                } else {
                    count++;
                }
            }
        }
        if (stat instanceof WACCParser.Stat_listContext) {
            count += countInits(((WACCParser.Stat_listContext) stat).stat(0));
            count += countInits(((WACCParser.Stat_listContext) stat).stat(1));
        }
        initCount = count;
        return count;
    }

    private int escapeCharToInt(String chr) {
        chr = chr.substring(1, chr.length() - 1);
        if (chr.length() > 1) {
            switch (chr.charAt(1)) {
                case 'n':
                    return 10;
                case '0':
                    return 0;
                case 't':
                    return 9;
                case 'r':
                    return 13;
                case 'b':
                    return 8;
                case 'f':
                    return 12;
                case '\\':
                    return 92;
                case '\'':
                    return 39;
                case '\"':
                    return 34;
            }
        }
        return -1;
    }

    private void resetFreeRegisters() {
        freeRegisters = new LinkedList<>();
        int k = 0;
        for (int i = 4; i < 11; i++) {
            freeRegisters.add(k, i);
            k++;
        }
    }

    private void addSysFunc(String ident) {
        switch (ident) {
            case "p_check_divide_by_zero" :
                AddDBZCheck(); break;
            case "p_throw_runtime_error:" :
                AddTRE(); break;
            case "p_throw_overflow_error" :
                AddTOE(); break;
            case "p_check_null_pointer" :
                AddCNPCheck(); break;
            case "p_print_string" :
                AddPrintString(); break;
            case "p_check_array_bounds" :
                addCAB(); break;
            case "p_read_int" :
                AddRead("int"); break;
            case "p_read_char" :
                AddRead("char"); break;
            case "p_free_pair" :
                AddFP(); break;
            case "p_print_int" :
                AddPrintInt(); break;
            case "p_print_bool" :
                AddPrintBool(); break;
            case "p_print_reference" :
                AddPrintReference(); break;
            case "p_print_ln" :
                AddPrintLn();
        }
    }

    private void AddDBZCheck() {

        if (!addedSysFuncs.contains("p_check_divide_by_zero")) {
            if (WACCVisitor.msgHashMap.get("\"DivideByZeroError:"
                    + " divide or modulo by zero\\n\\0\"") == null) {
                WACCVisitor.dataVisitor().generateDivideByZero();
            }
            sysFuncProgramBuilder.append("\tp_check_divide_by_zero:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n"
                    + "\t\tCMP r1, #0\n"
                    + "\t\tLDREQ r0, ="
                    + WACCVisitor.msgHashMap.get("\"DivideByZeroError:"
                    + " divide or modulo by zero\\n\\0\"") + "\n"
                    + "\t\tBLEQ p_throw_runtime_error\n" + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_check_divide_by_zero");
            AddTRE();
        }
    }

    private void AddTRE() {
        if (!addedSysFuncs.contains("p_throw_runtime_error")) {
            sysFuncProgramBuilder.append("\tp_throw_runtime_error:\n");
            sysFuncProgramBuilder.append("\t\tBL p_print_string\n" + "\t\tMOV r0, #-1\n"
                    + "\t\tBL exit\n");
            addedSysFuncs.add("p_throw_runtime_error");
            AddPrintString();
        }
    }

    private void AddTOE() {
        if (!addedSysFuncs.contains("p_throw_overflow_error")) {
            sysFuncProgramBuilder.append("\tp_throw_overflow_error:\n");
            sysFuncProgramBuilder.append("\t\tLDR r0, =msg_" + messageCount + "\n"
                    + "\t\tBL p_throw_runtime_error\n");
            addedSysFuncs.add("p_throw_overflow_error");
            messageCount++;
            AddTRE();
        }
    }

    private void AddCNPCheck() {
        if (!addedSysFuncs.contains("p_check_null_pointer")) {
            if (WACCVisitor.msgHashMap.get("\"NullReferenceError: "
                    + "dereference a null reference\\n\\0\"") == null) {
                WACCVisitor.dataVisitor().generateFreeNullRef();
            }
            sysFuncProgramBuilder.append("\tp_check_null_pointer:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n"
                    + "\t\tCMP r0, #0\n"
                    + "\t\tLDREQ r0, ="
                    + WACCVisitor.msgHashMap.get("\"NullReferenceError: "
                    + "dereference a null reference\\n\\0\"") + "\n"
                    + "\t\tBLEQ p_throw_runtime_error\n" + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_check_null_pointer");
            AddTRE();
        }
    }

    private void addCAB() {
        if (!addedSysFuncs.contains("p_check_array_bounds")) {
            if (WACCVisitor.msgHashMap.get("\"ArrayIndexOutOfBoundsError: " +
                    "negative index\\n\\0\"") == null) {
                WACCVisitor.dataVisitor().generateOutOfBounds();
            }
            sysFuncProgramBuilder.append("\tp_check_array_bounds:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n"
                    + "\t\tCMP r0, #0\n"
                    + "\t\tLDRLT r0, ="
                    + WACCVisitor.msgHashMap.get
                    ("\"ArrayIndexOutOfBoundsError: " +
                            "negative index\\n\\0\"") + "\n"
                    + "\t\tBLLT p_throw_runtime_error\n"
                    + "\t\tLDR r1, [r1]\n"
                    + "\t\tCMP r0, r1\n"
                    + "\t\tLDRCS r0, ="
                    + WACCVisitor.msgHashMap.get
                    ("\"ArrayIndexOutOfBoundsError: index" +
                            " too large\\n\\0\"") + "\n"
                    + "\t\tBLCS p_throw_runtime_error\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_check_array_bounds");
            AddTRE();
        }
    }

    private void AddRead(String type) {
        String messageIndex;
        if (type.equals("int")) {
            messageIndex = "\"%d\\0\"";
            if (!addedSysFuncs.contains("p_read_int")) {
                if (WACCVisitor.msgHashMap.get(messageIndex) == null) {
                    WACCVisitor.dataVisitor().generateReadIntLiterExpr();
                }
                sysFuncProgramBuilder.append("\tp_read_int:\n");
                sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tMOV r1, r0\n"
                        + "\t\tLDR r0, ="
                        + WACCVisitor.msgHashMap.get(messageIndex) + "\n"
                        + "\t\tADD r0, r0, #4\n" + "\t\tBL scanf\n"
                        + "\t\tPOP {pc}\n");
                addedSysFuncs.add("p_read_int");
            }
        }
        if (type.equals("char")) {
            messageIndex = "\"%c\\0\"";
            if (!addedSysFuncs.contains("p_read_char")) {
                if (WACCVisitor.msgHashMap.get(messageIndex) == null) {
                    WACCVisitor.dataVisitor().generateCharExpr();
                }
                sysFuncProgramBuilder.append("\t\tp_read_char:\n");
                sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tMOV r1, r0\n"
                        + "\t\tLDR r0, ="
                        + WACCVisitor.msgHashMap.get(messageIndex) + "\n"
                        + "\t\tADD r0, r0, #4\n" + "\t\tBL scanf\n"
                        + "\t\tPOP {pc}\n");
                addedSysFuncs.add("p_read_char");
            }
        }
    }

    private void AddFP() {
        if (!addedSysFuncs.contains("p_free_pair")) {
            if (WACCVisitor.msgHashMap.get("\"NullReferenceError: "
                    + "dereference a null reference\\n\\0\"") == null) {
                WACCVisitor.dataVisitor().generateFreeNullRef();
            }
            sysFuncProgramBuilder.append("\tp_free_pair:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n"
                    + "\t\tCMP r0, #0\n"
                    + "\t\tLDREQ r0, ="
                    + WACCVisitor.msgHashMap.get("\"NullReferenceError: "
                    + "dereference a null reference\\n\\0\"") + "\n"
                    + "\t\tBEQ p_throw_runtime_error\n" + "\t\tPUSH {r0}\n"
                    + "\t\tLDR r0, [r0]\n" + "\t\tBL free\n"
                    + "\t\tLDR r0, [sp]\n" + "\t\tLDR r0, [r0, #4]\n"
                    + "\t\tBL free\n" + "\t\tPOP {r0}\n" + "\t\tBL free\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_free_pair");
            AddTRE();
        }
    }

    private void AddPrintInt() {
        if (!addedSysFuncs.contains("p_print_int")) {
            if (WACCVisitor.msgHashMap.get("\"%d\\0\"") == null) {
                WACCVisitor.dataVisitor().generateReadIntLiterExpr();
            }
            sysFuncProgramBuilder.append("\tp_print_int:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tMOV r1, r0\n"
                    + "\t\tLDR r0, ="
                    + WACCVisitor.msgHashMap.get("\"%d\\0\"") + "\n"
                    + "\t\tADD r0, r0, #4\n" + "\t\tBL printf\n"
                    + "\t\tMOV r0, #0\n" + "\t\tBL fflush\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_print_int");
        }
    }

    private void AddPrintBool() {
        if (!addedSysFuncs.contains("p_print_bool")) {
            if (WACCVisitor.msgHashMap.get("\"true\\0\"") == null) {
                WACCVisitor.dataVisitor().generateBoolMessages();
            }
            sysFuncProgramBuilder.append("\tp_print_bool:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tCMP r0, #0\n"
                    + "\t\tLDRNE r0, ="
                    + WACCVisitor.msgHashMap.get("\"true\\0\"") + "\n"
                    + "\t\tLDREQ r0, ="
                    + WACCVisitor.msgHashMap.get("\"false\\0\"") + "\n"
                    + "\t\tADD r0, r0, #4\n" + "\t\tBL printf\n"
                    + "\t\tMOV r0, #0\n" + "\t\tBL fflush\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_print_bool");
        }
    }

    private void AddPrintString() {
        if (!addedSysFuncs.contains("p_print_string")) {
            if (WACCVisitor.msgHashMap.get("\"%" + ".*s\\0\"") == null) {
                WACCVisitor.dataVisitor().generateStringMessage();
            }
            sysFuncProgramBuilder.append("\tp_print_string:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tLDR r1, [r0]\n"
                    + "\t\tADD r2, r0, #4\n" + "\t\tLDR r0, ="
                    + WACCVisitor.msgHashMap.get("\"%" + ".*s\\0\"")
                    + "\n" + "\t\tADD r0, r0, #4\n" + "\t\tBL printf\n"
                    + "\t\tMOV r0, #0\n" + "\t\tBL fflush\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_print_string");
        }
    }

    private void AddPrintReference() {
        if (!addedSysFuncs.contains("p_print_reference")) {
            if (WACCVisitor.msgHashMap.get("\"%p\\0\"") == null) {
                WACCVisitor.dataVisitor().generatePrintReferenceMessage();
            }
            sysFuncProgramBuilder.append("\tp_print_reference:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tMOV r1, r0\n"
                    + "\t\tLDR r0, ="
                    + WACCVisitor.msgHashMap.get("\"%p\\0\"") + "\n"
                    + "\t\tADD r0, r0, #4\n" + "\t\tBL printf\n"
                    + "\t\tMOV r0, #0\n" + "\t\tBL fflush\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_print_reference");
        }
    }

    private void AddPrintLn() {
        if (!addedSysFuncs.contains("p_print_ln")) {
            if (WACCVisitor.msgHashMap.get("\"\\0\"") == null) {
                WACCVisitor.dataVisitor().generatePrintLnMessage();
            }
            sysFuncProgramBuilder.append("\tp_print_ln:\n");
            sysFuncProgramBuilder.append("\t\tPUSH {lr}\n" + "\t\tLDR r0, ="
                    + WACCVisitor.msgHashMap.get("\"\\0\"") + "\n"
                    + "\t\tADD r0, r0, #4\n" + "\t\tBL puts\n"
                    + "\t\tMOV r0, #0\n" + "\t\tBL fflush\n"
                    + "\t\tPOP {pc}\n");
            addedSysFuncs.add("p_print_ln");
        }
    }
}