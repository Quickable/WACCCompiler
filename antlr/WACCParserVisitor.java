// Generated from ./WACCParser.g4 by ANTLR 4.4
package antlr;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WACCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WACCParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code binaryCompExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryCompExpr(@NotNull WACCParser.BinaryCompExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentExpr(@NotNull WACCParser.IdentExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code for}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(@NotNull WACCParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_rhs_pair}
	 * labeled alternative in {@link WACCParser#assign_rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_rhs_pair(@NotNull WACCParser.Assign_rhs_pairContext ctx);
	/**
	 * Visit a parse tree produced by the {@code skip}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkip(@NotNull WACCParser.SkipContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull WACCParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assign_expr}
	 * labeled alternative in {@link WACCParser#assign_rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_expr(@NotNull WACCParser.Assign_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code free}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFree(@NotNull WACCParser.FreeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inc}
	 * labeled alternative in {@link WACCParser#post_unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInc(@NotNull WACCParser.IncContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryMultDivModExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryMultDivModExpr(@NotNull WACCParser.BinaryMultDivModExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code println}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintln(@NotNull WACCParser.PrintlnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code postUnaryOperExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostUnaryOperExpr(@NotNull WACCParser.PostUnaryOperExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(@NotNull WACCParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#pair_elem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_elem(@NotNull WACCParser.Pair_elemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code reassign}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReassign(@NotNull WACCParser.ReassignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code init}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInit(@NotNull WACCParser.InitContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#base_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBase_type(@NotNull WACCParser.Base_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#assign_lhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_lhs(@NotNull WACCParser.Assign_lhsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exit}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExit(@NotNull WACCParser.ExitContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(@NotNull WACCParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#array_elem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_elem(@NotNull WACCParser.Array_elemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code do_while}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_while(@NotNull WACCParser.Do_whileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stat_list}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat_list(@NotNull WACCParser.Stat_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull WACCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#pair_elem_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_elem_type(@NotNull WACCParser.Pair_elem_typeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryOperExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryOperExpr(@NotNull WACCParser.UnaryOperExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#int_liter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_liter(@NotNull WACCParser.Int_literContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pairLiterExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairLiterExpr(@NotNull WACCParser.PairLiterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code chr}
	 * labeled alternative in {@link WACCParser#unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChr(@NotNull WACCParser.ChrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link WACCParser#assign_rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull WACCParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryBoolExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryBoolExpr(@NotNull WACCParser.BinaryBoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code charLiterExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharLiterExpr(@NotNull WACCParser.CharLiterExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(@NotNull WACCParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiterExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiterExpr(@NotNull WACCParser.StringLiterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ord}
	 * labeled alternative in {@link WACCParser#unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrd(@NotNull WACCParser.OrdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code while}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile(@NotNull WACCParser.WhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code dec}
	 * labeled alternative in {@link WACCParser#post_unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(@NotNull WACCParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(@NotNull WACCParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolLiterExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiterExpr(@NotNull WACCParser.BoolLiterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code read}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(@NotNull WACCParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#array_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_type(@NotNull WACCParser.Array_typeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code new_pair}
	 * labeled alternative in {@link WACCParser#assign_rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNew_pair(@NotNull WACCParser.New_pairContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayElemExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElemExpr(@NotNull WACCParser.ArrayElemExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLiterExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiterExpr(@NotNull WACCParser.IntLiterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negate}
	 * labeled alternative in {@link WACCParser#unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegate(@NotNull WACCParser.NegateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryEqExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryEqExpr(@NotNull WACCParser.BinaryEqExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code return}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn(@NotNull WACCParser.ReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code len}
	 * labeled alternative in {@link WACCParser#unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLen(@NotNull WACCParser.LenContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#pair_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair_type(@NotNull WACCParser.Pair_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#bool_liter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_liter(@NotNull WACCParser.Bool_literContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(@NotNull WACCParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if_else}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_else(@NotNull WACCParser.If_elseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code begin}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBegin(@NotNull WACCParser.BeginContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryAddMinusExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryAddMinusExpr(@NotNull WACCParser.BinaryAddMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WACCParser#arg_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_list(@NotNull WACCParser.Arg_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code inc_dec}
	 * labeled alternative in {@link WACCParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInc_dec(@NotNull WACCParser.Inc_decContext ctx);
	/**
	 * Visit a parse tree produced by the {@code array_liter}
	 * labeled alternative in {@link WACCParser#assign_rhs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_liter(@NotNull WACCParser.Array_literContext ctx);
	/**
	 * Visit a parse tree produced by the {@code excl}
	 * labeled alternative in {@link WACCParser#unary_oper}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExcl(@NotNull WACCParser.ExclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketedExpr}
	 * labeled alternative in {@link WACCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedExpr(@NotNull WACCParser.BracketedExprContext ctx);
}