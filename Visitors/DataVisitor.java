package Visitors;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import antlr.WACCParser;
import antlr.WACCParser.Array_elemContext;
import antlr.WACCParser.ExprContext;
import antlr.WACCParser.NegateContext;
import antlr.WACCParser.Pair_elemContext;

public class DataVisitor extends WACCVisitor {

    private boolean[] endMessages = {false, false, false, false};

    private boolean addedBools = endMessages[0];
    private boolean addedOverflow = false;
    private boolean addedDivideByZero = false;
    private boolean addedPrintln = endMessages[3];
    private boolean addedPointer = endMessages[1];
    private boolean addedString = endMessages[2];
    private boolean addedIntPrint = false;
    private boolean addedIntRead = false;
    private boolean addedChar = false;
    private boolean addedOutOfBounds = false;
    private boolean addedPairElemNullRef = false;
    private boolean addedFreeNullRef = false;
    private boolean empty = true;

    private class StringLiterVisitor extends WACCVisitor {

        @Override
        public Void visitStringLiterExpr(
                @NotNull WACCParser.StringLiterExprContext ctx) {
            String message = ctx.STRING_LITER().getText();
            int backslashCount = 0;
            for (int i = 0; i < message.length(); i++) {
                if (message.charAt(i) == '\\') {
                    if (message.charAt(i + 1) != '\\') {
                        backslashCount++;
                    }
                }
            }

            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word " + (message.length() - 2 - backslashCount));
            appendDataLine(".ascii " + message);
            msgHashMap.put(message, "msg_" + messageCounter);
            empty = false;
            messageCounter++;
            return null;
        }
    }

    @Override
    public Void visitProgram(@NotNull WACCParser.ProgramContext ctx) {
        StringLiterVisitor sLV = new StringLiterVisitor();

        appendDataLabel(".data");
        appendDataLabel("");

        sLV.visitProgram(ctx);
        WACCVisitor.currentScope = WACCVisitor.globalTable;
        WACCVisitor.currentScope.resetTableCounter();

        visitChildren(ctx);

        generateEndMessages();
        appendDataLabel("");

        if (empty) {
            removeData();
        }

        return null;
    }

    @Override
    public Void visitArrayElemExpr(@NotNull WACCParser.ArrayElemExprContext ctx) {
        generateOutOfBounds();
        generateStringExpr();
        return null;
    }

    @Override
    public Void visitAssign_rhs_pair(
            @NotNull WACCParser.Assign_rhs_pairContext ctx) {
        generatePairElemNullRef();
        generateStringExpr();
        return null;
    }

    @Override
    public Void visitFree(@NotNull WACCParser.FreeContext ctx) {
        String type = getExprType(ctx.expr());

        if (type.startsWith("pair") && !type.endsWith("[]")) {
            generateFreeNullRef();
            generateStringExpr();
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitPrint(@NotNull WACCParser.PrintContext ctx) {
        generatePrint(ctx.expr());
        return visitChildren(ctx);
    }

    @Override
    public Void visitPrintln(@NotNull WACCParser.PrintlnContext ctx) {
        generatePrint(ctx.expr());

        if (!addedPrintln) {
            addedPrintln = true;
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitRead(@NotNull WACCParser.ReadContext ctx) {
        TerminalNode ident = ctx.assign_lhs().IDENT();
        Array_elemContext array_elem = ctx.assign_lhs().array_elem();
        Pair_elemContext pair_elem = ctx.assign_lhs().pair_elem();

        if (ident != null) {
            String type = currentScope.getTypeGlobal(ident.getText(), 0, 0);

            if (type.equals("char")) {
                generateCharExpr();
            } else if (type.equals("int")) {
                generateReadIntLiterExpr();
            }
        } else if (array_elem != null) {
            String type = currentScope.getTypeGlobal(array_elem.getText(), 0, 0);
            type = type.replaceAll("\\[", "");
            type = type.replaceAll("\\]", "");

            if (type.equals("char")) {
                generateCharExpr();
            } else if (type.equals("int")) {
                generateReadIntLiterExpr();
            }
        } else if (pair_elem != null) {
            String type = null;

            if (ctx.assign_lhs().pair_elem().FST() != null) {
                type = currentScope.getTypeGlobal(pair_elem.expr().getText()
                        + ".fst", 0, 0);
            } else {
                type = currentScope.getTypeGlobal(pair_elem.expr().getText()
                        + ".fst", 0, 0);
            }

            if (type.equals("char")) {
                generateCharExpr();
            } else if (type.equals("int")) {
                generateReadIntLiterExpr();
            }
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitAssign_lhs(@NotNull WACCParser.Assign_lhsContext ctx) {
        if (ctx.array_elem() != null) {
            generateOutOfBounds();
            generateStringExpr();
        } else if (ctx.pair_elem() != null) {
            generatePairElemNullRef();
            generateStringExpr();
        }

        return visitChildren(ctx);
    }

    @Override
    public Void visitBinaryMultDivModExpr(
            @NotNull WACCParser.BinaryMultDivModExprContext ctx) {
        String operator = ctx.getText();

        if (operator.equals("*")) {
            generateOverflow();
        } else {
            generateDivideByZero();
        }

        generateStringExpr();
        return visitChildren(ctx);
    }

    @Override
    public Void visitBinaryAddMinusExpr(
            @NotNull WACCParser.BinaryAddMinusExprContext ctx) {
        generateOverflow();
        generateStringExpr();
        return visitChildren(ctx);
    }

    @Override
    public Void visitUnaryOperExpr(@NotNull WACCParser.UnaryOperExprContext ctx) {
        if (ctx.unary_oper() instanceof NegateContext) {
            generateOverflow();
            generateStringExpr();
        }
        return visitChildren(ctx);
    }

    @Override
    public Void visitPostUnaryOperExpr(@NotNull WACCParser.PostUnaryOperExprContext ctx) {
        generateOverflow();
        generatePrintIntLiterExpr();
        generateStringExpr();
        return visitChildren(ctx);
    }
    
    @Override
    public Void visitInc_dec(@NotNull WACCParser.Inc_decContext ctx) {
    	generateOverflow();
        generateStringExpr();
        return visitChildren(ctx);
    }

    private void generatePrint(ExprContext expr) {
        String type = getExprType(expr);

        if (type.equals("char[]") || type.equals("string")) {
            generateStringExpr();
        } else if (type.endsWith("[]") || type.startsWith("pair")) {
            generatePointer();
        } else if (type.equals("bool")) {
            generateBoolLiterExpr();
        } else if (type.equals("int")) {
            generatePrintIntLiterExpr();
        }
    }

    private void generatePairElemNullRef() {
        empty = false;

        if (!addedPairElemNullRef) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 50");
            appendDataLine(".ascii \"NullReferenceError: dereference a "
                    + "null reference\\n\\0\"");
            msgHashMap.put("\"NullReferenceError: dereference a "
                    + "null reference\\n\\0\"", "msg_" + messageCounter);
            messageCounter++;
            addedPairElemNullRef = true;
        }
    }

    public void generateFreeNullRef() {
        empty = false;

        if (!addedFreeNullRef) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 50");
            appendDataLine(".ascii \"NullReferenceError: dereference a "
                    + "null reference\\n\\0\"");
            msgHashMap.put("\"NullReferenceError: dereference a "
                    + "null reference\\n\\0\"", "msg_" + messageCounter);
            messageCounter++;
            addedFreeNullRef = true;
        }
    }

    public void generateDivideByZero() {
        empty = false;

        if (!addedDivideByZero) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 45");
            appendDataLine(".ascii  \"DivideByZeroError: divide or modulo"
                    + " by zero\\n\\0\"");
            msgHashMap.put("\"DivideByZeroError: divide or modulo"
                    + " by zero\\n\\0\"", "msg_" + messageCounter);
            messageCounter++;
            addedDivideByZero = true;
        }
    }

    private void generateOverflow() {
        empty = false;

        if (!addedOverflow) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 82");
            appendDataLine(".ascii \"OverflowError: the result is too "
                    + "small/large to store in a 4-byte signed-integer.\\n\"");
            msgHashMap.put("\"OverflowError: the result is too "
                            + "small/large to store in a 4-byte signed-integer.\\n\"",
                    "msg_" + messageCounter);
            messageCounter++;
            addedOverflow = true;
        }
    }

    public void generateOutOfBounds() {
        empty = false;

        if (!addedOutOfBounds) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 44");
            appendDataLine(".ascii \"ArrayIndexOutOfBoundsError: "
                    + "negative index\\n\\0\"");
            msgHashMap.put("\"ArrayIndexOutOfBoundsError: "
                    + "negative index\\n\\0\"", "msg_" + messageCounter);
            messageCounter++;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 45");
            appendDataLine(".ascii \"ArrayIndexOutOfBoundsError: "
                    + "index too large\\n\\0\"");
            msgHashMap.put("\"ArrayIndexOutOfBoundsError: "
                    + "index too large\\n\\0\"", "msg_" + messageCounter);
            messageCounter++;
            addedOutOfBounds = true;
        }
    }

    private void generateStringExpr() {
        empty = false;

        if (!addedString) {
            addedString = true;
        }
    }

    private void generatePointer() {
        empty = false;

        if (!addedPointer) {
            addedPointer = true;
        }
    }

    public void generateReadIntLiterExpr() {
        empty = false;

        if (!addedIntRead) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 3");
            appendDataLine(".ascii \"%d\\0\"");
            msgHashMap.put("\"%d\\0\"", "msg_" + messageCounter);
            addedIntRead = true;
            messageCounter++;
        }
    }

    private void generatePrintIntLiterExpr() {
        empty = false;

        if (!addedIntPrint) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 3");
            appendDataLine(".ascii \"%d\\0\"");
            msgHashMap.put("\"%d\\0\"", "msg_" + messageCounter);
            addedIntPrint = true;
            messageCounter++;
        }
    }

    public void generateCharExpr() {
        empty = false;

        if (!addedChar) {
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 3");
            appendDataLine(".ascii \" %c\\0\"");
            msgHashMap.put("\"%c\\0\"", "msg_" + messageCounter);
            addedChar = true;
            messageCounter++;
        }
    }

    private void generateBoolLiterExpr() {
        empty = false;

        if (!addedBools) {
            addedBools = true;
        }
    }

    private void generateEndMessages() {
        if (addedBools) {
            empty = false;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 5");
            appendDataLine(".ascii \"true\\0\"");
            msgHashMap.put("\"true\\0\"", "msg_" + messageCounter);
            messageCounter++;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 6");
            appendDataLine(".ascii \"false\\0\"");
            msgHashMap.put("\"false\\0\"", "msg_" + messageCounter);
            messageCounter++;
        }

        if (addedPointer) {
            empty = false;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 3");
            appendDataLine(".ascii \"%p\\0\"");
            msgHashMap.put("\"%p\\0\"", "msg_" + messageCounter);
            messageCounter++;
        }

        if (addedString) {
            empty = false;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 5");
            appendDataLine(".ascii \"%.*s\\0\"");
            msgHashMap.put("\"%.*s\\0\"", "msg_" + messageCounter);
            messageCounter++;
        }

        if (addedPrintln) {
            empty = false;
            appendDataLabel("msg_" + messageCounter + ":");
            appendDataLine(".word 1");
            appendDataLine(".ascii \"\\0\"");
            msgHashMap.put("\"\\0\"", "msg_" + messageCounter);
            messageCounter++;
        }
    }

    public void generateStringMessage() {
        empty = false;
        appendDataLabel("msg_" + messageCounter + ":");
        appendDataLine(".word 5");
        appendDataLine(".ascii \"%.*s\\0\"");
        msgHashMap.put("\"%.*s\\0\"", "msg_" + messageCounter);
        messageCounter++;
    }

    public void generateBoolMessages() {
        empty = false;
        appendDataLabel("msg_" + messageCounter + ":");
        appendDataLine(".word 5");
        appendDataLine(".ascii \"true\\0\"");
        msgHashMap.put("\"true\\0\"", "msg_" + messageCounter);
        messageCounter++;
        appendDataLabel("msg_" + messageCounter + ":");
        appendDataLine(".word 6");
        appendDataLine(".ascii \"false\\0\"");
        msgHashMap.put("\"false\\0\"", "msg_" + messageCounter);
        messageCounter++;
    }


    public void generatePrintReferenceMessage() {
        empty = false;
        appendDataLabel("msg_" + messageCounter + ":");
        appendDataLine(".word 3");
        appendDataLine(".ascii \"%p\\0\"");
        msgHashMap.put("\"%p\\0\"", "msg_" + messageCounter);
        messageCounter++;
    }

    public void generatePrintLnMessage() {
        empty = false;
        appendDataLabel("msg_" + messageCounter + ":");
        appendDataLine(".word 1");
        appendDataLine(".ascii \"\\0\"");
        msgHashMap.put("\"\\0\"", "msg_" + messageCounter);
        messageCounter++;
    }
}
