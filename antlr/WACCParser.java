// Generated from ./WACCParser.g4 by ANTLR 4.4
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WACCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DEC=8, LT=16, FST=59, WHILE=52, DOUBLE_QUOTE=62, MOD=11, GTE=15, CHAR=27, 
		DO=54, FOR=53, EXCL=3, NOT_NEW_LINE=69, AND=20, LTE=17, ORD=5, IF=48, 
		FREE=43, SINGLE_QUOTE=61, INC=7, CLOSE_PARENTHESES=31, THEN=49, ESCAPE=24, 
		COMMA=39, DONE=55, IS=38, PRINTLN=47, BEGIN=36, RETURN=44, CHAR_LITER=63, 
		IDENT=66, STD=2, PLUS=12, PAIR=29, EQ=18, COMMENT=68, NEWPAIR=57, INTEGER=35, 
		EXIT=45, STRING_LITER=64, SND=60, ELSE=50, BOOL=26, HASH=67, WHITESPACE=1, 
		PAIR_LITER=65, SEMI_COLON=56, INT=25, MINUS=13, MULT=9, OPEN_ARRAY=32, 
		TRUE=22, PRINT=46, CHR=6, FI=51, SKIP=40, NEQ=19, READ=42, CLOSE_ARRAY=33, 
		OR=21, ASSIGN=41, OPEN_PARENTHESES=30, GT=14, LEN=4, CALL=58, DIV=10, 
		END=37, FALSE=23, NEW_LINE=70, STRING=28, INT_SIGN=34;
	public static final String[] tokenNames = {
		"<INVALID>", "WHITESPACE", "'std.'", "'!'", "'len'", "'ord'", "'chr'", 
		"'++'", "'--'", "'*'", "'/'", "'%'", "'+'", "'-'", "'>'", "'>='", "'<'", 
		"'<='", "'=='", "'!='", "'&&'", "'||'", "'true'", "'false'", "'\\'", "'int'", 
		"'bool'", "'char'", "'string'", "'pair'", "'('", "')'", "'['", "']'", 
		"INT_SIGN", "INTEGER", "'begin'", "'end'", "'is'", "','", "'skip'", "'='", 
		"'read'", "'free'", "'return'", "'exit'", "'print'", "'println'", "'if'", 
		"'then'", "'else'", "'fi'", "'while'", "'for'", "'do'", "'done'", "';'", 
		"'newpair'", "'call'", "'fst'", "'snd'", "'''", "'\"'", "CHAR_LITER", 
		"STRING_LITER", "'null'", "IDENT", "'#'", "COMMENT", "NOT_NEW_LINE", "'\n'"
	};
	public static final int
		RULE_program = 0, RULE_func = 1, RULE_param_list = 2, RULE_param = 3, 
		RULE_stat = 4, RULE_assign_lhs = 5, RULE_assign_rhs = 6, RULE_arg_list = 7, 
		RULE_pair_elem = 8, RULE_type = 9, RULE_base_type = 10, RULE_array_type = 11, 
		RULE_pair_type = 12, RULE_pair_elem_type = 13, RULE_expr = 14, RULE_int_liter = 15, 
		RULE_bool_liter = 16, RULE_unary_oper = 17, RULE_post_unary_oper = 18, 
		RULE_array_elem = 19;
	public static final String[] ruleNames = {
		"program", "func", "param_list", "param", "stat", "assign_lhs", "assign_rhs", 
		"arg_list", "pair_elem", "type", "base_type", "array_type", "pair_type", 
		"pair_elem_type", "expr", "int_liter", "bool_liter", "unary_oper", "post_unary_oper", 
		"array_elem"
	};

	@Override
	public String getGrammarFileName() { return "WACCParser.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WACCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(WACCParser.EOF, 0); }
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public TerminalNode BEGIN() { return getToken(WACCParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WACCParser.END, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(40); match(BEGIN);
			setState(44);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(41); func();
					}
					} 
				}
				setState(46);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(47); stat(0);
			setState(48); match(END);
			setState(49); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncContext extends ParserRuleContext {
		public Param_listContext param_list() {
			return getRuleContext(Param_listContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WACCParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WACCParser.OPEN_PARENTHESES, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode IS() { return getToken(WACCParser.IS, 0); }
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public TerminalNode END() { return getToken(WACCParser.END, 0); }
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51); type();
			setState(52); match(IDENT);
			setState(53); match(OPEN_PARENTHESES);
			setState(55);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING) | (1L << PAIR))) != 0)) {
				{
				setState(54); param_list();
				}
			}

			setState(57); match(CLOSE_PARENTHESES);
			setState(58); match(IS);
			setState(59); stat(0);
			setState(60); match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Param_listContext extends ParserRuleContext {
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WACCParser.COMMA); }
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WACCParser.COMMA, i);
		}
		public Param_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitParam_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Param_listContext param_list() throws RecognitionException {
		Param_listContext _localctx = new Param_listContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_param_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62); param();
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(63); match(COMMA);
				setState(64); param();
				}
				}
				setState(69);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70); type();
			setState(71); match(IDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReassignContext extends StatContext {
		public Assign_rhsContext assign_rhs() {
			return getRuleContext(Assign_rhsContext.class,0);
		}
		public Assign_lhsContext assign_lhs() {
			return getRuleContext(Assign_lhsContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(WACCParser.ASSIGN, 0); }
		public ReassignContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitReassign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForContext extends StatContext {
		public TerminalNode SEMI_COLON(int i) {
			return getToken(WACCParser.SEMI_COLON, i);
		}
		public List<TerminalNode> SEMI_COLON() { return getTokens(WACCParser.SEMI_COLON); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode DO() { return getToken(WACCParser.DO, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode FOR() { return getToken(WACCParser.FOR, 0); }
		public TerminalNode DONE() { return getToken(WACCParser.DONE, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public ForContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitFor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(WACCParser.RETURN, 0); }
		public ReturnContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitReturn(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InitContext extends StatContext {
		public Assign_rhsContext assign_rhs() {
			return getRuleContext(Assign_rhsContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(WACCParser.ASSIGN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public InitContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitInit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SkipContext extends StatContext {
		public TerminalNode SKIP() { return getToken(WACCParser.SKIP, 0); }
		public SkipContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitSkip(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExitContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EXIT() { return getToken(WACCParser.EXIT, 0); }
		public ExitContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitExit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends StatContext {
		public TerminalNode PRINT() { return getToken(WACCParser.PRINT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BeginContext extends StatContext {
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public TerminalNode BEGIN() { return getToken(WACCParser.BEGIN, 0); }
		public TerminalNode END() { return getToken(WACCParser.END, 0); }
		public BeginContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBegin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileContext extends StatContext {
		public TerminalNode WHILE() { return getToken(WACCParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(WACCParser.DO, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DONE() { return getToken(WACCParser.DONE, 0); }
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public WhileContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class If_elseContext extends StatContext {
		public TerminalNode THEN() { return getToken(WACCParser.THEN, 0); }
		public TerminalNode IF() { return getToken(WACCParser.IF, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public TerminalNode FI() { return getToken(WACCParser.FI, 0); }
		public TerminalNode ELSE() { return getToken(WACCParser.ELSE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public If_elseContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitIf_else(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FreeContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FREE() { return getToken(WACCParser.FREE, 0); }
		public FreeContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitFree(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Inc_decContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Post_unary_operContext post_unary_oper() {
			return getRuleContext(Post_unary_operContext.class,0);
		}
		public Inc_decContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitInc_dec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Stat_listContext extends StatContext {
		public TerminalNode SEMI_COLON() { return getToken(WACCParser.SEMI_COLON, 0); }
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public Stat_listContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitStat_list(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Do_whileContext extends StatContext {
		public TerminalNode WHILE() { return getToken(WACCParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(WACCParser.DO, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public Do_whileContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitDo_while(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintlnContext extends StatContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode PRINTLN() { return getToken(WACCParser.PRINTLN, 0); }
		public PrintlnContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPrintln(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfContext extends StatContext {
		public TerminalNode THEN() { return getToken(WACCParser.THEN, 0); }
		public TerminalNode IF() { return getToken(WACCParser.IF, 0); }
		public TerminalNode FI() { return getToken(WACCParser.FI, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public IfContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReadContext extends StatContext {
		public Assign_lhsContext assign_lhs() {
			return getRuleContext(Assign_lhsContext.class,0);
		}
		public TerminalNode READ() { return getToken(WACCParser.READ, 0); }
		public ReadContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		return stat(0);
	}

	private StatContext stat(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatContext _localctx = new StatContext(_ctx, _parentState);
		StatContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_stat, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				_localctx = new SkipContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(74); match(SKIP);
				}
				break;
			case 2:
				{
				_localctx = new Inc_decContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(75); expr(0);
				setState(76); post_unary_oper();
				}
				break;
			case 3:
				{
				_localctx = new InitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(78); type();
				setState(79); match(IDENT);
				setState(80); match(ASSIGN);
				setState(81); assign_rhs();
				}
				break;
			case 4:
				{
				_localctx = new ReassignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(83); assign_lhs();
				setState(84); match(ASSIGN);
				setState(85); assign_rhs();
				}
				break;
			case 5:
				{
				_localctx = new ReadContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(87); match(READ);
				setState(88); assign_lhs();
				}
				break;
			case 6:
				{
				_localctx = new FreeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(89); match(FREE);
				setState(90); expr(0);
				}
				break;
			case 7:
				{
				_localctx = new ReturnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91); match(RETURN);
				setState(92); expr(0);
				}
				break;
			case 8:
				{
				_localctx = new ExitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93); match(EXIT);
				setState(94); expr(0);
				}
				break;
			case 9:
				{
				_localctx = new PrintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(95); match(PRINT);
				setState(96); expr(0);
				}
				break;
			case 10:
				{
				_localctx = new PrintlnContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(97); match(PRINTLN);
				setState(98); expr(0);
				}
				break;
			case 11:
				{
				_localctx = new If_elseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99); match(IF);
				setState(100); expr(0);
				setState(101); match(THEN);
				setState(102); stat(0);
				setState(103); match(ELSE);
				setState(104); stat(0);
				setState(105); match(FI);
				}
				break;
			case 12:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(107); match(IF);
				setState(108); expr(0);
				setState(109); match(THEN);
				setState(110); stat(0);
				setState(111); match(FI);
				}
				break;
			case 13:
				{
				_localctx = new WhileContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113); match(WHILE);
				setState(114); expr(0);
				setState(115); match(DO);
				setState(116); stat(0);
				setState(117); match(DONE);
				}
				break;
			case 14:
				{
				_localctx = new Do_whileContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(119); match(DO);
				setState(120); stat(0);
				setState(121); match(WHILE);
				setState(122); expr(0);
				}
				break;
			case 15:
				{
				_localctx = new ForContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(124); match(FOR);
				setState(125); expr(0);
				setState(126); match(SEMI_COLON);
				setState(127); expr(0);
				setState(128); match(SEMI_COLON);
				setState(129); stat(0);
				setState(130); match(DO);
				setState(131); stat(0);
				setState(132); match(DONE);
				}
				break;
			case 16:
				{
				_localctx = new BeginContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134); match(BEGIN);
				setState(135); stat(0);
				setState(136); match(END);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(145);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Stat_listContext(new StatContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_stat);
					setState(140);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(141); match(SEMI_COLON);
					setState(142); stat(2);
					}
					} 
				}
				setState(147);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Assign_lhsContext extends ParserRuleContext {
		public Pair_elemContext pair_elem() {
			return getRuleContext(Pair_elemContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public Array_elemContext array_elem() {
			return getRuleContext(Array_elemContext.class,0);
		}
		public Assign_lhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_lhs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitAssign_lhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_lhsContext assign_lhs() throws RecognitionException {
		Assign_lhsContext _localctx = new Assign_lhsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assign_lhs);
		try {
			setState(151);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148); match(IDENT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(149); array_elem();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(150); pair_elem();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_rhsContext extends ParserRuleContext {
		public Assign_rhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_rhs; }
	 
		public Assign_rhsContext() { }
		public void copyFrom(Assign_rhsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallContext extends Assign_rhsContext {
		public TerminalNode CALL() { return getToken(WACCParser.CALL, 0); }
		public Arg_listContext arg_list() {
			return getRuleContext(Arg_listContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WACCParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WACCParser.OPEN_PARENTHESES, 0); }
		public TerminalNode STD() { return getToken(WACCParser.STD, 0); }
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public CallContext(Assign_rhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Array_literContext extends Assign_rhsContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WACCParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPEN_ARRAY() { return getToken(WACCParser.OPEN_ARRAY, 0); }
		public TerminalNode CLOSE_ARRAY() { return getToken(WACCParser.CLOSE_ARRAY, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(WACCParser.COMMA, i);
		}
		public Array_literContext(Assign_rhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitArray_liter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Assign_rhs_pairContext extends Assign_rhsContext {
		public Pair_elemContext pair_elem() {
			return getRuleContext(Pair_elemContext.class,0);
		}
		public Assign_rhs_pairContext(Assign_rhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitAssign_rhs_pair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class New_pairContext extends Assign_rhsContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WACCParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WACCParser.COMMA, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode OPEN_PARENTHESES() { return getToken(WACCParser.OPEN_PARENTHESES, 0); }
		public TerminalNode NEWPAIR() { return getToken(WACCParser.NEWPAIR, 0); }
		public New_pairContext(Assign_rhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitNew_pair(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Assign_exprContext extends Assign_rhsContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Assign_exprContext(Assign_rhsContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitAssign_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_rhsContext assign_rhs() throws RecognitionException {
		Assign_rhsContext _localctx = new Assign_rhsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assign_rhs);
		int _la;
		try {
			setState(184);
			switch (_input.LA(1)) {
			case EXCL:
			case LEN:
			case ORD:
			case CHR:
			case MINUS:
			case TRUE:
			case FALSE:
			case OPEN_PARENTHESES:
			case INT_SIGN:
			case INTEGER:
			case CHAR_LITER:
			case STRING_LITER:
			case PAIR_LITER:
			case IDENT:
				_localctx = new Assign_exprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(153); expr(0);
				}
				break;
			case OPEN_ARRAY:
				_localctx = new Array_literContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(154); match(OPEN_ARRAY);
				setState(163);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (EXCL - 3)) | (1L << (LEN - 3)) | (1L << (ORD - 3)) | (1L << (CHR - 3)) | (1L << (MINUS - 3)) | (1L << (TRUE - 3)) | (1L << (FALSE - 3)) | (1L << (OPEN_PARENTHESES - 3)) | (1L << (INT_SIGN - 3)) | (1L << (INTEGER - 3)) | (1L << (CHAR_LITER - 3)) | (1L << (STRING_LITER - 3)) | (1L << (PAIR_LITER - 3)) | (1L << (IDENT - 3)))) != 0)) {
					{
					setState(155); expr(0);
					setState(160);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(156); match(COMMA);
						setState(157); expr(0);
						}
						}
						setState(162);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(165); match(CLOSE_ARRAY);
				}
				break;
			case NEWPAIR:
				_localctx = new New_pairContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(166); match(NEWPAIR);
				setState(167); match(OPEN_PARENTHESES);
				setState(168); expr(0);
				setState(169); match(COMMA);
				setState(170); expr(0);
				setState(171); match(CLOSE_PARENTHESES);
				}
				break;
			case FST:
			case SND:
				_localctx = new Assign_rhs_pairContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(173); pair_elem();
				}
				break;
			case CALL:
				_localctx = new CallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(174); match(CALL);
				setState(176);
				_la = _input.LA(1);
				if (_la==STD) {
					{
					setState(175); match(STD);
					}
				}

				setState(178); match(IDENT);
				setState(179); match(OPEN_PARENTHESES);
				setState(181);
				_la = _input.LA(1);
				if (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & ((1L << (EXCL - 3)) | (1L << (LEN - 3)) | (1L << (ORD - 3)) | (1L << (CHR - 3)) | (1L << (MINUS - 3)) | (1L << (TRUE - 3)) | (1L << (FALSE - 3)) | (1L << (OPEN_PARENTHESES - 3)) | (1L << (INT_SIGN - 3)) | (1L << (INTEGER - 3)) | (1L << (CHAR_LITER - 3)) | (1L << (STRING_LITER - 3)) | (1L << (PAIR_LITER - 3)) | (1L << (IDENT - 3)))) != 0)) {
					{
					setState(180); arg_list();
					}
				}

				setState(183); match(CLOSE_PARENTHESES);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_listContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(WACCParser.COMMA); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(WACCParser.COMMA, i);
		}
		public Arg_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_list; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitArg_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arg_listContext arg_list() throws RecognitionException {
		Arg_listContext _localctx = new Arg_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_arg_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186); expr(0);
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(187); match(COMMA);
				setState(188); expr(0);
				}
				}
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pair_elemContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode FST() { return getToken(WACCParser.FST, 0); }
		public TerminalNode SND() { return getToken(WACCParser.SND, 0); }
		public Pair_elemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair_elem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPair_elem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pair_elemContext pair_elem() throws RecognitionException {
		Pair_elemContext _localctx = new Pair_elemContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_pair_elem);
		try {
			setState(198);
			switch (_input.LA(1)) {
			case FST:
				enterOuterAlt(_localctx, 1);
				{
				setState(194); match(FST);
				setState(195); expr(0);
				}
				break;
			case SND:
				enterOuterAlt(_localctx, 2);
				{
				setState(196); match(SND);
				setState(197); expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public Array_typeContext array_type() {
			return getRuleContext(Array_typeContext.class,0);
		}
		public Base_typeContext base_type() {
			return getRuleContext(Base_typeContext.class,0);
		}
		public Pair_typeContext pair_type() {
			return getRuleContext(Pair_typeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type);
		try {
			setState(203);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(200); base_type();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(201); array_type(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(202); pair_type();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Base_typeContext extends ParserRuleContext {
		public TerminalNode BOOL() { return getToken(WACCParser.BOOL, 0); }
		public TerminalNode INT() { return getToken(WACCParser.INT, 0); }
		public TerminalNode STRING() { return getToken(WACCParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(WACCParser.CHAR, 0); }
		public Base_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_base_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBase_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Base_typeContext base_type() throws RecognitionException {
		Base_typeContext _localctx = new Base_typeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_base_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << CHAR) | (1L << STRING))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Array_typeContext extends ParserRuleContext {
		public TerminalNode OPEN_ARRAY() { return getToken(WACCParser.OPEN_ARRAY, 0); }
		public Array_typeContext array_type() {
			return getRuleContext(Array_typeContext.class,0);
		}
		public Base_typeContext base_type() {
			return getRuleContext(Base_typeContext.class,0);
		}
		public Pair_typeContext pair_type() {
			return getRuleContext(Pair_typeContext.class,0);
		}
		public TerminalNode CLOSE_ARRAY() { return getToken(WACCParser.CLOSE_ARRAY, 0); }
		public Array_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitArray_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_typeContext array_type() throws RecognitionException {
		return array_type(0);
	}

	private Array_typeContext array_type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Array_typeContext _localctx = new Array_typeContext(_ctx, _parentState);
		Array_typeContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_array_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			switch (_input.LA(1)) {
			case INT:
			case BOOL:
			case CHAR:
			case STRING:
				{
				setState(208); base_type();
				setState(209); match(OPEN_ARRAY);
				setState(210); match(CLOSE_ARRAY);
				}
				break;
			case PAIR:
				{
				setState(212); pair_type();
				setState(213); match(OPEN_ARRAY);
				setState(214); match(CLOSE_ARRAY);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(223);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Array_typeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_array_type);
					setState(218);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(219); match(OPEN_ARRAY);
					setState(220); match(CLOSE_ARRAY);
					}
					} 
				}
				setState(225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Pair_typeContext extends ParserRuleContext {
		public List<Pair_elem_typeContext> pair_elem_type() {
			return getRuleContexts(Pair_elem_typeContext.class);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WACCParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode COMMA() { return getToken(WACCParser.COMMA, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WACCParser.OPEN_PARENTHESES, 0); }
		public TerminalNode PAIR() { return getToken(WACCParser.PAIR, 0); }
		public Pair_elem_typeContext pair_elem_type(int i) {
			return getRuleContext(Pair_elem_typeContext.class,i);
		}
		public Pair_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPair_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pair_typeContext pair_type() throws RecognitionException {
		Pair_typeContext _localctx = new Pair_typeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_pair_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226); match(PAIR);
			setState(227); match(OPEN_PARENTHESES);
			setState(228); pair_elem_type();
			setState(229); match(COMMA);
			setState(230); pair_elem_type();
			setState(231); match(CLOSE_PARENTHESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Pair_elem_typeContext extends ParserRuleContext {
		public Array_typeContext array_type() {
			return getRuleContext(Array_typeContext.class,0);
		}
		public Base_typeContext base_type() {
			return getRuleContext(Base_typeContext.class,0);
		}
		public TerminalNode PAIR() { return getToken(WACCParser.PAIR, 0); }
		public Pair_elem_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair_elem_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPair_elem_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pair_elem_typeContext pair_elem_type() throws RecognitionException {
		Pair_elem_typeContext _localctx = new Pair_elem_typeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pair_elem_type);
		try {
			setState(236);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(233); base_type();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(234); array_type(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(235); match(PAIR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntLiterExprContext extends ExprContext {
		public Int_literContext int_liter() {
			return getRuleContext(Int_literContext.class,0);
		}
		public IntLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitIntLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryCompExprContext extends ExprContext {
		public Token binary_oper;
		public TerminalNode GTE() { return getToken(WACCParser.GTE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode LT() { return getToken(WACCParser.LT, 0); }
		public TerminalNode LTE() { return getToken(WACCParser.LTE, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode GT() { return getToken(WACCParser.GT, 0); }
		public BinaryCompExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBinaryCompExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryBoolExprContext extends ExprContext {
		public Token binary_oper;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode AND() { return getToken(WACCParser.AND, 0); }
		public TerminalNode OR() { return getToken(WACCParser.OR, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryBoolExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBinaryBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CharLiterExprContext extends ExprContext {
		public TerminalNode CHAR_LITER() { return getToken(WACCParser.CHAR_LITER, 0); }
		public CharLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitCharLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentExprContext extends ExprContext {
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public IdentExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitIdentExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryEqExprContext extends ExprContext {
		public Token binary_oper;
		public TerminalNode NEQ() { return getToken(WACCParser.NEQ, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(WACCParser.EQ, 0); }
		public BinaryEqExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBinaryEqExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringLiterExprContext extends ExprContext {
		public TerminalNode STRING_LITER() { return getToken(WACCParser.STRING_LITER, 0); }
		public StringLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitStringLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryAddMinusExprContext extends ExprContext {
		public Token binary_oper;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode MINUS() { return getToken(WACCParser.MINUS, 0); }
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(WACCParser.PLUS, 0); }
		public BinaryAddMinusExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBinaryAddMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryMultDivModExprContext extends ExprContext {
		public Token binary_oper;
		public TerminalNode DIV() { return getToken(WACCParser.DIV, 0); }
		public TerminalNode MULT() { return getToken(WACCParser.MULT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MOD() { return getToken(WACCParser.MOD, 0); }
		public BinaryMultDivModExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBinaryMultDivModExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PostUnaryOperExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Post_unary_operContext post_unary_oper() {
			return getRuleContext(Post_unary_operContext.class,0);
		}
		public PostUnaryOperExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPostUnaryOperExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolLiterExprContext extends ExprContext {
		public Bool_literContext bool_liter() {
			return getRuleContext(Bool_literContext.class,0);
		}
		public BoolLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBoolLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryOperExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Unary_operContext unary_oper() {
			return getRuleContext(Unary_operContext.class,0);
		}
		public UnaryOperExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitUnaryOperExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PairLiterExprContext extends ExprContext {
		public TerminalNode PAIR_LITER() { return getToken(WACCParser.PAIR_LITER, 0); }
		public PairLiterExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitPairLiterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayElemExprContext extends ExprContext {
		public Array_elemContext array_elem() {
			return getRuleContext(Array_elemContext.class,0);
		}
		public ArrayElemExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitArrayElemExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BracketedExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode CLOSE_PARENTHESES() { return getToken(WACCParser.CLOSE_PARENTHESES, 0); }
		public TerminalNode OPEN_PARENTHESES() { return getToken(WACCParser.OPEN_PARENTHESES, 0); }
		public BracketedExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBracketedExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				_localctx = new UnaryOperExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(239); unary_oper();
				setState(240); expr(8);
				}
				break;
			case 2:
				{
				_localctx = new IntLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(242); int_liter();
				}
				break;
			case 3:
				{
				_localctx = new BoolLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(243); bool_liter();
				}
				break;
			case 4:
				{
				_localctx = new CharLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(244); match(CHAR_LITER);
				}
				break;
			case 5:
				{
				_localctx = new StringLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(245); match(STRING_LITER);
				}
				break;
			case 6:
				{
				_localctx = new PairLiterExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(246); match(PAIR_LITER);
				}
				break;
			case 7:
				{
				_localctx = new IdentExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(247); match(IDENT);
				}
				break;
			case 8:
				{
				_localctx = new ArrayElemExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(248); array_elem();
				}
				break;
			case 9:
				{
				_localctx = new BracketedExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(249); match(OPEN_PARENTHESES);
				setState(250); expr(0);
				setState(251); match(CLOSE_PARENTHESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(274);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(272);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryMultDivModExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(255);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(256);
						((BinaryMultDivModExprContext)_localctx).binary_oper = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((BinaryMultDivModExprContext)_localctx).binary_oper = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(257); expr(7);
						}
						break;
					case 2:
						{
						_localctx = new BinaryAddMinusExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(258);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(259);
						((BinaryAddMinusExprContext)_localctx).binary_oper = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((BinaryAddMinusExprContext)_localctx).binary_oper = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(260); expr(6);
						}
						break;
					case 3:
						{
						_localctx = new BinaryCompExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(261);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(262);
						((BinaryCompExprContext)_localctx).binary_oper = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GTE) | (1L << LT) | (1L << LTE))) != 0)) ) {
							((BinaryCompExprContext)_localctx).binary_oper = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(263); expr(5);
						}
						break;
					case 4:
						{
						_localctx = new BinaryEqExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(264);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(265);
						((BinaryEqExprContext)_localctx).binary_oper = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NEQ) ) {
							((BinaryEqExprContext)_localctx).binary_oper = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(266); expr(4);
						}
						break;
					case 5:
						{
						_localctx = new BinaryBoolExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(267);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(268);
						((BinaryBoolExprContext)_localctx).binary_oper = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((BinaryBoolExprContext)_localctx).binary_oper = (Token)_errHandler.recoverInline(this);
						}
						consume();
						setState(269); expr(3);
						}
						break;
					case 6:
						{
						_localctx = new PostUnaryOperExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(270);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(271); post_unary_oper();
						}
						break;
					}
					} 
				}
				setState(276);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Int_literContext extends ParserRuleContext {
		public TerminalNode INT_SIGN() { return getToken(WACCParser.INT_SIGN, 0); }
		public TerminalNode INTEGER() { return getToken(WACCParser.INTEGER, 0); }
		public Int_literContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int_liter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitInt_liter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Int_literContext int_liter() throws RecognitionException {
		Int_literContext _localctx = new Int_literContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_int_liter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			_la = _input.LA(1);
			if (_la==INT_SIGN) {
				{
				setState(277); match(INT_SIGN);
				}
			}

			setState(280); match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bool_literContext extends ParserRuleContext {
		public TerminalNode TRUE() { return getToken(WACCParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(WACCParser.FALSE, 0); }
		public Bool_literContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool_liter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitBool_liter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bool_literContext bool_liter() throws RecognitionException {
		Bool_literContext _localctx = new Bool_literContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_bool_liter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			_la = _input.LA(1);
			if ( !(_la==TRUE || _la==FALSE) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Unary_operContext extends ParserRuleContext {
		public Unary_operContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_oper; }
	 
		public Unary_operContext() { }
		public void copyFrom(Unary_operContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NegateContext extends Unary_operContext {
		public TerminalNode MINUS() { return getToken(WACCParser.MINUS, 0); }
		public NegateContext(Unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitNegate(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrdContext extends Unary_operContext {
		public TerminalNode ORD() { return getToken(WACCParser.ORD, 0); }
		public OrdContext(Unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitOrd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExclContext extends Unary_operContext {
		public TerminalNode EXCL() { return getToken(WACCParser.EXCL, 0); }
		public ExclContext(Unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitExcl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LenContext extends Unary_operContext {
		public TerminalNode LEN() { return getToken(WACCParser.LEN, 0); }
		public LenContext(Unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitLen(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChrContext extends Unary_operContext {
		public TerminalNode CHR() { return getToken(WACCParser.CHR, 0); }
		public ChrContext(Unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitChr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unary_operContext unary_oper() throws RecognitionException {
		Unary_operContext _localctx = new Unary_operContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unary_oper);
		try {
			setState(289);
			switch (_input.LA(1)) {
			case EXCL:
				_localctx = new ExclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(284); match(EXCL);
				}
				break;
			case MINUS:
				_localctx = new NegateContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(285); match(MINUS);
				}
				break;
			case LEN:
				_localctx = new LenContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(286); match(LEN);
				}
				break;
			case ORD:
				_localctx = new OrdContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(287); match(ORD);
				}
				break;
			case CHR:
				_localctx = new ChrContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(288); match(CHR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Post_unary_operContext extends ParserRuleContext {
		public Post_unary_operContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_post_unary_oper; }
	 
		public Post_unary_operContext() { }
		public void copyFrom(Post_unary_operContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DecContext extends Post_unary_operContext {
		public TerminalNode DEC() { return getToken(WACCParser.DEC, 0); }
		public DecContext(Post_unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitDec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IncContext extends Post_unary_operContext {
		public TerminalNode INC() { return getToken(WACCParser.INC, 0); }
		public IncContext(Post_unary_operContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitInc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Post_unary_operContext post_unary_oper() throws RecognitionException {
		Post_unary_operContext _localctx = new Post_unary_operContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_post_unary_oper);
		try {
			setState(293);
			switch (_input.LA(1)) {
			case INC:
				_localctx = new IncContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(291); match(INC);
				}
				break;
			case DEC:
				_localctx = new DecContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(292); match(DEC);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Array_elemContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public TerminalNode OPEN_ARRAY(int i) {
			return getToken(WACCParser.OPEN_ARRAY, i);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> OPEN_ARRAY() { return getTokens(WACCParser.OPEN_ARRAY); }
		public TerminalNode CLOSE_ARRAY(int i) {
			return getToken(WACCParser.CLOSE_ARRAY, i);
		}
		public List<TerminalNode> CLOSE_ARRAY() { return getTokens(WACCParser.CLOSE_ARRAY); }
		public TerminalNode IDENT() { return getToken(WACCParser.IDENT, 0); }
		public Array_elemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_elem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WACCParserVisitor ) return ((WACCParserVisitor<? extends T>)visitor).visitArray_elem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Array_elemContext array_elem() throws RecognitionException {
		Array_elemContext _localctx = new Array_elemContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_array_elem);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(295); match(IDENT);
			setState(300); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(296); match(OPEN_ARRAY);
					setState(297); expr(0);
					setState(298); match(CLOSE_ARRAY);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(302); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return stat_sempred((StatContext)_localctx, predIndex);
		case 11: return array_type_sempred((Array_typeContext)_localctx, predIndex);
		case 14: return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 6);
		case 3: return precpred(_ctx, 5);
		case 4: return precpred(_ctx, 4);
		case 5: return precpred(_ctx, 3);
		case 6: return precpred(_ctx, 2);
		case 7: return precpred(_ctx, 7);
		}
		return true;
	}
	private boolean array_type_sempred(Array_typeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean stat_sempred(StatContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3H\u0133\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\7\2-\n\2\f\2\16\2\60\13\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3:\n\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\7"+
		"\4D\n\4\f\4\16\4G\13\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6\u008d\n\6\3\6\3\6\3\6\7\6\u0092\n\6\f\6\16\6\u0095"+
		"\13\6\3\7\3\7\3\7\5\7\u009a\n\7\3\b\3\b\3\b\3\b\3\b\7\b\u00a1\n\b\f\b"+
		"\16\b\u00a4\13\b\5\b\u00a6\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\5\b\u00b3\n\b\3\b\3\b\3\b\5\b\u00b8\n\b\3\b\5\b\u00bb\n\b\3\t\3\t"+
		"\3\t\7\t\u00c0\n\t\f\t\16\t\u00c3\13\t\3\n\3\n\3\n\3\n\5\n\u00c9\n\n\3"+
		"\13\3\13\3\13\5\13\u00ce\n\13\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\5\r\u00db\n\r\3\r\3\r\3\r\7\r\u00e0\n\r\f\r\16\r\u00e3\13\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\5\17\u00ef\n\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20"+
		"\u0100\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\7\20\u0113\n\20\f\20\16\20\u0116\13\20\3\21"+
		"\5\21\u0119\n\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\5\23\u0124"+
		"\n\23\3\24\3\24\5\24\u0128\n\24\3\25\3\25\3\25\3\25\3\25\6\25\u012f\n"+
		"\25\r\25\16\25\u0130\3\25\2\5\n\30\36\26\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(\2\t\3\2\33\36\3\2\13\r\3\2\16\17\3\2\20\23\3\2\24\25\3"+
		"\2\26\27\3\2\30\31\u0158\2*\3\2\2\2\4\65\3\2\2\2\6@\3\2\2\2\bH\3\2\2\2"+
		"\n\u008c\3\2\2\2\f\u0099\3\2\2\2\16\u00ba\3\2\2\2\20\u00bc\3\2\2\2\22"+
		"\u00c8\3\2\2\2\24\u00cd\3\2\2\2\26\u00cf\3\2\2\2\30\u00da\3\2\2\2\32\u00e4"+
		"\3\2\2\2\34\u00ee\3\2\2\2\36\u00ff\3\2\2\2 \u0118\3\2\2\2\"\u011c\3\2"+
		"\2\2$\u0123\3\2\2\2&\u0127\3\2\2\2(\u0129\3\2\2\2*.\7&\2\2+-\5\4\3\2,"+
		"+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61\62"+
		"\5\n\6\2\62\63\7\'\2\2\63\64\7\2\2\3\64\3\3\2\2\2\65\66\5\24\13\2\66\67"+
		"\7D\2\2\679\7 \2\28:\5\6\4\298\3\2\2\29:\3\2\2\2:;\3\2\2\2;<\7!\2\2<="+
		"\7(\2\2=>\5\n\6\2>?\7\'\2\2?\5\3\2\2\2@E\5\b\5\2AB\7)\2\2BD\5\b\5\2CA"+
		"\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2F\7\3\2\2\2GE\3\2\2\2HI\5\24\13"+
		"\2IJ\7D\2\2J\t\3\2\2\2KL\b\6\1\2L\u008d\7*\2\2MN\5\36\20\2NO\5&\24\2O"+
		"\u008d\3\2\2\2PQ\5\24\13\2QR\7D\2\2RS\7+\2\2ST\5\16\b\2T\u008d\3\2\2\2"+
		"UV\5\f\7\2VW\7+\2\2WX\5\16\b\2X\u008d\3\2\2\2YZ\7,\2\2Z\u008d\5\f\7\2"+
		"[\\\7-\2\2\\\u008d\5\36\20\2]^\7.\2\2^\u008d\5\36\20\2_`\7/\2\2`\u008d"+
		"\5\36\20\2ab\7\60\2\2b\u008d\5\36\20\2cd\7\61\2\2d\u008d\5\36\20\2ef\7"+
		"\62\2\2fg\5\36\20\2gh\7\63\2\2hi\5\n\6\2ij\7\64\2\2jk\5\n\6\2kl\7\65\2"+
		"\2l\u008d\3\2\2\2mn\7\62\2\2no\5\36\20\2op\7\63\2\2pq\5\n\6\2qr\7\65\2"+
		"\2r\u008d\3\2\2\2st\7\66\2\2tu\5\36\20\2uv\78\2\2vw\5\n\6\2wx\79\2\2x"+
		"\u008d\3\2\2\2yz\78\2\2z{\5\n\6\2{|\7\66\2\2|}\5\36\20\2}\u008d\3\2\2"+
		"\2~\177\7\67\2\2\177\u0080\5\36\20\2\u0080\u0081\7:\2\2\u0081\u0082\5"+
		"\36\20\2\u0082\u0083\7:\2\2\u0083\u0084\5\n\6\2\u0084\u0085\78\2\2\u0085"+
		"\u0086\5\n\6\2\u0086\u0087\79\2\2\u0087\u008d\3\2\2\2\u0088\u0089\7&\2"+
		"\2\u0089\u008a\5\n\6\2\u008a\u008b\7\'\2\2\u008b\u008d\3\2\2\2\u008cK"+
		"\3\2\2\2\u008cM\3\2\2\2\u008cP\3\2\2\2\u008cU\3\2\2\2\u008cY\3\2\2\2\u008c"+
		"[\3\2\2\2\u008c]\3\2\2\2\u008c_\3\2\2\2\u008ca\3\2\2\2\u008cc\3\2\2\2"+
		"\u008ce\3\2\2\2\u008cm\3\2\2\2\u008cs\3\2\2\2\u008cy\3\2\2\2\u008c~\3"+
		"\2\2\2\u008c\u0088\3\2\2\2\u008d\u0093\3\2\2\2\u008e\u008f\f\3\2\2\u008f"+
		"\u0090\7:\2\2\u0090\u0092\5\n\6\4\u0091\u008e\3\2\2\2\u0092\u0095\3\2"+
		"\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\13\3\2\2\2\u0095\u0093"+
		"\3\2\2\2\u0096\u009a\7D\2\2\u0097\u009a\5(\25\2\u0098\u009a\5\22\n\2\u0099"+
		"\u0096\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u0098\3\2\2\2\u009a\r\3\2\2\2"+
		"\u009b\u00bb\5\36\20\2\u009c\u00a5\7\"\2\2\u009d\u00a2\5\36\20\2\u009e"+
		"\u009f\7)\2\2\u009f\u00a1\5\36\20\2\u00a0\u009e\3\2\2\2\u00a1\u00a4\3"+
		"\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a5\u009d\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\u00bb\7#\2\2\u00a8\u00a9\7;\2\2\u00a9\u00aa\7 \2\2\u00aa\u00ab"+
		"\5\36\20\2\u00ab\u00ac\7)\2\2\u00ac\u00ad\5\36\20\2\u00ad\u00ae\7!\2\2"+
		"\u00ae\u00bb\3\2\2\2\u00af\u00bb\5\22\n\2\u00b0\u00b2\7<\2\2\u00b1\u00b3"+
		"\7\4\2\2\u00b2\u00b1\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\u00b5\7D\2\2\u00b5\u00b7\7 \2\2\u00b6\u00b8\5\20\t\2\u00b7\u00b6\3\2"+
		"\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\7!\2\2\u00ba"+
		"\u009b\3\2\2\2\u00ba\u009c\3\2\2\2\u00ba\u00a8\3\2\2\2\u00ba\u00af\3\2"+
		"\2\2\u00ba\u00b0\3\2\2\2\u00bb\17\3\2\2\2\u00bc\u00c1\5\36\20\2\u00bd"+
		"\u00be\7)\2\2\u00be\u00c0\5\36\20\2\u00bf\u00bd\3\2\2\2\u00c0\u00c3\3"+
		"\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\21\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c4\u00c5\7=\2\2\u00c5\u00c9\5\36\20\2\u00c6\u00c7\7"+
		">\2\2\u00c7\u00c9\5\36\20\2\u00c8\u00c4\3\2\2\2\u00c8\u00c6\3\2\2\2\u00c9"+
		"\23\3\2\2\2\u00ca\u00ce\5\26\f\2\u00cb\u00ce\5\30\r\2\u00cc\u00ce\5\32"+
		"\16\2\u00cd\u00ca\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce"+
		"\25\3\2\2\2\u00cf\u00d0\t\2\2\2\u00d0\27\3\2\2\2\u00d1\u00d2\b\r\1\2\u00d2"+
		"\u00d3\5\26\f\2\u00d3\u00d4\7\"\2\2\u00d4\u00d5\7#\2\2\u00d5\u00db\3\2"+
		"\2\2\u00d6\u00d7\5\32\16\2\u00d7\u00d8\7\"\2\2\u00d8\u00d9\7#\2\2\u00d9"+
		"\u00db\3\2\2\2\u00da\u00d1\3\2\2\2\u00da\u00d6\3\2\2\2\u00db\u00e1\3\2"+
		"\2\2\u00dc\u00dd\f\3\2\2\u00dd\u00de\7\"\2\2\u00de\u00e0\7#\2\2\u00df"+
		"\u00dc\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2"+
		"\2\2\u00e2\31\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\7\37\2\2\u00e5\u00e6"+
		"\7 \2\2\u00e6\u00e7\5\34\17\2\u00e7\u00e8\7)\2\2\u00e8\u00e9\5\34\17\2"+
		"\u00e9\u00ea\7!\2\2\u00ea\33\3\2\2\2\u00eb\u00ef\5\26\f\2\u00ec\u00ef"+
		"\5\30\r\2\u00ed\u00ef\7\37\2\2\u00ee\u00eb\3\2\2\2\u00ee\u00ec\3\2\2\2"+
		"\u00ee\u00ed\3\2\2\2\u00ef\35\3\2\2\2\u00f0\u00f1\b\20\1\2\u00f1\u00f2"+
		"\5$\23\2\u00f2\u00f3\5\36\20\n\u00f3\u0100\3\2\2\2\u00f4\u0100\5 \21\2"+
		"\u00f5\u0100\5\"\22\2\u00f6\u0100\7A\2\2\u00f7\u0100\7B\2\2\u00f8\u0100"+
		"\7C\2\2\u00f9\u0100\7D\2\2\u00fa\u0100\5(\25\2\u00fb\u00fc\7 \2\2\u00fc"+
		"\u00fd\5\36\20\2\u00fd\u00fe\7!\2\2\u00fe\u0100\3\2\2\2\u00ff\u00f0\3"+
		"\2\2\2\u00ff\u00f4\3\2\2\2\u00ff\u00f5\3\2\2\2\u00ff\u00f6\3\2\2\2\u00ff"+
		"\u00f7\3\2\2\2\u00ff\u00f8\3\2\2\2\u00ff\u00f9\3\2\2\2\u00ff\u00fa\3\2"+
		"\2\2\u00ff\u00fb\3\2\2\2\u0100\u0114\3\2\2\2\u0101\u0102\f\b\2\2\u0102"+
		"\u0103\t\3\2\2\u0103\u0113\5\36\20\t\u0104\u0105\f\7\2\2\u0105\u0106\t"+
		"\4\2\2\u0106\u0113\5\36\20\b\u0107\u0108\f\6\2\2\u0108\u0109\t\5\2\2\u0109"+
		"\u0113\5\36\20\7\u010a\u010b\f\5\2\2\u010b\u010c\t\6\2\2\u010c\u0113\5"+
		"\36\20\6\u010d\u010e\f\4\2\2\u010e\u010f\t\7\2\2\u010f\u0113\5\36\20\5"+
		"\u0110\u0111\f\t\2\2\u0111\u0113\5&\24\2\u0112\u0101\3\2\2\2\u0112\u0104"+
		"\3\2\2\2\u0112\u0107\3\2\2\2\u0112\u010a\3\2\2\2\u0112\u010d\3\2\2\2\u0112"+
		"\u0110\3\2\2\2\u0113\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114\u0115\3\2"+
		"\2\2\u0115\37\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u0119\7$\2\2\u0118\u0117"+
		"\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\7%\2\2\u011b"+
		"!\3\2\2\2\u011c\u011d\t\b\2\2\u011d#\3\2\2\2\u011e\u0124\7\5\2\2\u011f"+
		"\u0124\7\17\2\2\u0120\u0124\7\6\2\2\u0121\u0124\7\7\2\2\u0122\u0124\7"+
		"\b\2\2\u0123\u011e\3\2\2\2\u0123\u011f\3\2\2\2\u0123\u0120\3\2\2\2\u0123"+
		"\u0121\3\2\2\2\u0123\u0122\3\2\2\2\u0124%\3\2\2\2\u0125\u0128\7\t\2\2"+
		"\u0126\u0128\7\n\2\2\u0127\u0125\3\2\2\2\u0127\u0126\3\2\2\2\u0128\'\3"+
		"\2\2\2\u0129\u012e\7D\2\2\u012a\u012b\7\"\2\2\u012b\u012c\5\36\20\2\u012c"+
		"\u012d\7#\2\2\u012d\u012f\3\2\2\2\u012e\u012a\3\2\2\2\u012f\u0130\3\2"+
		"\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131)\3\2\2\2\32.9E\u008c"+
		"\u0093\u0099\u00a2\u00a5\u00b2\u00b7\u00ba\u00c1\u00c8\u00cd\u00da\u00e1"+
		"\u00ee\u00ff\u0112\u0114\u0118\u0123\u0127\u0130";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}