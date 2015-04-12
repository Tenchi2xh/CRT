// Generated from CRT.g4 by ANTLR 4.3
package net.team2xh.crt.language.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CRTParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__2=1, T__1=2, T__0=3, SETTINGS=4, SCENE=5, SCALE=6, TRANSLATE=7, ROTATE=8, 
		IDENTIFIER=9, NAME=10, STRING=11, SKIP=12, INTEGER=13, FLOAT=14, LPAREN=15, 
		RPAREN=16, LBRACE=17, RBRACE=18, LBRACK=19, RBRACK=20, LCHEVR=21, RCHEVR=22, 
		COMMA=23, ASSIGN=24, ATTRIBUTE=25, ADD=26, SUBTRACT=27, INTERSECTION=28, 
		MULTIPLY=29, DIVIDE=30, MODULO=31, NOT=32, LESS=33, GREATER=34, LESS_EQUAL=35, 
		GREATER_EQUAL=36, EQUAL=37, NOT_EQUAL=38, AND=39, OR=40, QUESTION=41, 
		COLON=42;
	public static final String[] tokenNames = {
		"<INVALID>", "'true'", "'false'", "'Macro'", "'Settings'", "'Scene'", 
		"'scale'", "'translate'", "'rotate'", "IDENTIFIER", "NAME", "STRING", 
		"SKIP", "INTEGER", "FLOAT", "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"LCHEVR", "RCHEVR", "','", "'='", "'->'", "'+'", "'-'", "'^'", "'*'", 
		"'/'", "'%'", "'!'", "LESS", "GREATER", "'<='", "'>='", "'=='", "'!='", 
		"'&&'", "'||'", "'?'", "':'"
	};
	public static final int
		RULE_script = 0, RULE_statement = 1, RULE_settings = 2, RULE_scene = 3, 
		RULE_expression = 4, RULE_expressionList = 5, RULE_primary = 6, RULE_object = 7, 
		RULE_macro = 8, RULE_literal = 9, RULE_attribute = 10, RULE_modifier = 11, 
		RULE_bool = 12;
	public static final String[] ruleNames = {
		"script", "statement", "settings", "scene", "expression", "expressionList", 
		"primary", "object", "macro", "literal", "attribute", "modifier", "bool"
	};

	@Override
	public String getGrammarFileName() { return "CRT.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CRTParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ScriptContext extends ParserRuleContext {
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public ScriptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_script; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterScript(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitScript(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitScript(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__1) | (1L << T__0) | (1L << SETTINGS) | (1L << SCENE) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(26); statement();
				}
				}
				setState(31);
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

	public static class StatementContext extends ParserRuleContext {
		public SettingsContext settings() {
			return getRuleContext(SettingsContext.class,0);
		}
		public SceneContext scene() {
			return getRuleContext(SceneContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(35);
			switch (_input.LA(1)) {
			case SETTINGS:
				enterOuterAlt(_localctx, 1);
				{
				setState(32); settings();
				}
				break;
			case SCENE:
				enterOuterAlt(_localctx, 2);
				{
				setState(33); scene();
				}
				break;
			case T__2:
			case T__1:
			case T__0:
			case IDENTIFIER:
			case NAME:
			case STRING:
			case INTEGER:
			case FLOAT:
			case LPAREN:
			case LBRACK:
			case ADD:
			case SUBTRACT:
			case NOT:
				enterOuterAlt(_localctx, 3);
				{
				setState(34); expression(0);
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

	public static class SettingsContext extends ParserRuleContext {
		public SettingsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_settings; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterSettings(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitSettings(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitSettings(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SettingsContext settings() throws RecognitionException {
		SettingsContext _localctx = new SettingsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_settings);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37); match(SETTINGS);
			setState(38); match(LBRACE);
			setState(39); match(RBRACE);
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

	public static class SceneContext extends ParserRuleContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public SceneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scene; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterScene(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitScene(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitScene(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SceneContext scene() throws RecognitionException {
		SceneContext _localctx = new SceneContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_scene);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41); match(SCENE);
			setState(42); match(LBRACE);
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__1) | (1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(43); expression(0);
				}
				}
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(49); match(RBRACE);
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ComparisonContext extends ExpressionContext {
		public TerminalNode LESS() { return getToken(CRTParser.LESS, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public TerminalNode GREATER() { return getToken(CRTParser.GREATER, 0); }
		public ComparisonContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ObjectExprContext extends ExpressionContext {
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public ObjectExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterObjectExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitObjectExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitObjectExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MacroExprContext extends ExpressionContext {
		public MacroContext macro() {
			return getRuleContext(MacroContext.class,0);
		}
		public MacroExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterMacroExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitMacroExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitMacroExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public AssignmentContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryOrContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public BinaryOrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterBinaryOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitBinaryOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitBinaryOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryAndContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public BinaryAndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterBinaryAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitBinaryAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitBinaryAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListContext extends ExpressionContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ListContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModifiersContext extends ExpressionContext {
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public TerminalNode RCHEVR() { return getToken(CRTParser.RCHEVR, 0); }
		public TerminalNode LCHEVR() { return getToken(CRTParser.LCHEVR, 0); }
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ModifiersContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterModifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitModifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitModifiers(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallContext extends ExpressionContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnarySignContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnarySignContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterUnarySign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitUnarySign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitUnarySign(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListAccessContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ListAccessContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterListAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitListAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitListAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrimaryExprContext extends ExpressionContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public PrimaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterPrimaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitPrimaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitPrimaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiplicationContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public MultiplicationContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterMultiplication(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitMultiplication(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitMultiplication(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TernaryContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public TernaryContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterTernary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitTernary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitTernary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryNotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryNotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterUnaryNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitUnaryNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitUnaryNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AdditionContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public AdditionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterAddition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitAddition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitAddition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			switch (_input.LA(1)) {
			case ADD:
			case SUBTRACT:
				{
				_localctx = new UnarySignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(52);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUBTRACT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(53); expression(9);
				}
				break;
			case NOT:
				{
				_localctx = new UnaryNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54); match(NOT);
				setState(55); expression(8);
				}
				break;
			case T__2:
			case T__1:
			case IDENTIFIER:
			case STRING:
			case INTEGER:
			case FLOAT:
			case LPAREN:
				{
				_localctx = new PrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56); primary();
				}
				break;
			case NAME:
				{
				_localctx = new ObjectExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(57); object();
				}
				break;
			case T__0:
				{
				_localctx = new MacroExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(58); macro();
				}
				break;
			case LBRACK:
				{
				_localctx = new ListContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(59); match(LBRACK);
				setState(61);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__1) | (1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
					{
					setState(60); expressionList();
					}
				}

				setState(63); match(RBRACK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(115);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(113);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(66);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(67);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULTIPLY) | (1L << DIVIDE) | (1L << MODULO))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(68); expression(8);
						}
						break;

					case 2:
						{
						_localctx = new AdditionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(69);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(70);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUBTRACT) | (1L << INTERSECTION))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(71); expression(7);
						}
						break;

					case 3:
						{
						_localctx = new ComparisonContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(72);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(73);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LESS) | (1L << GREATER) | (1L << LESS_EQUAL) | (1L << GREATER_EQUAL) | (1L << EQUAL) | (1L << NOT_EQUAL))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(74); expression(6);
						}
						break;

					case 4:
						{
						_localctx = new BinaryAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(75);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(76); match(AND);
						setState(77); expression(5);
						}
						break;

					case 5:
						{
						_localctx = new BinaryOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(78);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(79); match(OR);
						setState(80); expression(4);
						}
						break;

					case 6:
						{
						_localctx = new TernaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(81);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(82); match(QUESTION);
						setState(83); expression(0);
						setState(84); match(COLON);
						setState(85); expression(3);
						}
						break;

					case 7:
						{
						_localctx = new AssignmentContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(87);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(88); match(ASSIGN);
						setState(89); expression(1);
						}
						break;

					case 8:
						{
						_localctx = new ListAccessContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(90);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(91); match(LBRACK);
						setState(92); expression(0);
						setState(93); match(RBRACK);
						}
						break;

					case 9:
						{
						_localctx = new CallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(95);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(96); match(LPAREN);
						setState(98);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__1) | (1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
							{
							setState(97); expressionList();
							}
						}

						setState(100); match(RPAREN);
						}
						break;

					case 10:
						{
						_localctx = new ModifiersContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(101);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(102); match(LCHEVR);
						setState(103); modifier();
						setState(108);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(104); match(COMMA);
							setState(105); modifier();
							}
							}
							setState(110);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(111); match(RCHEVR);
						}
						break;
					}
					} 
				}
				setState(117);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	public static class ExpressionListContext extends ParserRuleContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitExpressionList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118); expression(0);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(119); match(COMMA);
				setState(120); expression(0);
				}
				}
				setState(125);
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

	public static class PrimaryContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CRTParser.IDENTIFIER, 0); }
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primary);
		try {
			setState(132);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(126); match(LPAREN);
				setState(127); expression(0);
				setState(128); match(RPAREN);
				}
				break;
			case T__2:
			case T__1:
			case STRING:
			case INTEGER:
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(130); literal();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(131); match(IDENTIFIER);
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

	public static class ObjectContext extends ParserRuleContext {
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public TerminalNode NAME() { return getToken(CRTParser.NAME, 0); }
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_object);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134); match(NAME);
			setState(135); match(LBRACE);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(136); attribute();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142); match(RBRACE);
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

	public static class MacroContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER(int i) {
			return getToken(CRTParser.IDENTIFIER, i);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(CRTParser.IDENTIFIER); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public MacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroContext macro() throws RecognitionException {
		MacroContext _localctx = new MacroContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_macro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); match(T__0);
			setState(145); match(LPAREN);
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(146); match(IDENTIFIER);
				}
				}
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(152); match(RPAREN);
			setState(153); match(LBRACE);
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__1) | (1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(154); expression(0);
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(160); match(RBRACE);
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(CRTParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(CRTParser.STRING, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode FLOAT() { return getToken(CRTParser.FLOAT, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		try {
			setState(166);
			switch (_input.LA(1)) {
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(162); match(INTEGER);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(163); match(FLOAT);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(164); match(STRING);
				}
				break;
			case T__2:
			case T__1:
				enterOuterAlt(_localctx, 4);
				{
				setState(165); bool();
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

	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CRTParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); match(IDENTIFIER);
			setState(169); match(ATTRIBUTE);
			setState(170); expression(0);
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

	public static class ModifierContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_modifier);
		try {
			setState(178);
			switch (_input.LA(1)) {
			case SCALE:
				enterOuterAlt(_localctx, 1);
				{
				setState(172); match(SCALE);
				setState(173); expression(0);
				}
				break;
			case TRANSLATE:
				enterOuterAlt(_localctx, 2);
				{
				setState(174); match(TRANSLATE);
				setState(175); expression(0);
				}
				break;
			case ROTATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(176); match(ROTATE);
				setState(177); expression(0);
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

	public static class BoolContext extends ParserRuleContext {
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CRTVisitor ) return ((CRTVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_bool);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_la = _input.LA(1);
			if ( !(_la==T__2 || _la==T__1) ) {
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4: return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 7);

		case 1: return precpred(_ctx, 6);

		case 2: return precpred(_ctx, 5);

		case 3: return precpred(_ctx, 4);

		case 4: return precpred(_ctx, 3);

		case 5: return precpred(_ctx, 2);

		case 6: return precpred(_ctx, 1);

		case 7: return precpred(_ctx, 12);

		case 8: return precpred(_ctx, 11);

		case 9: return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u00b9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\3\3\3\3"+
		"\3\5\3&\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5/\n\5\f\5\16\5\62\13\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6@\n\6\3\6\5\6C\n\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6e\n\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\7\6m\n\6\f\6\16\6p\13\6\3\6\3\6\7\6t\n\6\f\6\16\6w"+
		"\13\6\3\7\3\7\3\7\7\7|\n\7\f\7\16\7\177\13\7\3\b\3\b\3\b\3\b\3\b\3\b\5"+
		"\b\u0087\n\b\3\t\3\t\3\t\7\t\u008c\n\t\f\t\16\t\u008f\13\t\3\t\3\t\3\n"+
		"\3\n\3\n\7\n\u0096\n\n\f\n\16\n\u0099\13\n\3\n\3\n\3\n\7\n\u009e\n\n\f"+
		"\n\16\n\u00a1\13\n\3\n\3\n\3\13\3\13\3\13\3\13\5\13\u00a9\n\13\3\f\3\f"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00b5\n\r\3\16\3\16\3\16\2\3\n\17"+
		"\2\4\6\b\n\f\16\20\22\24\26\30\32\2\7\3\2\34\35\3\2\37!\3\2\34\36\3\2"+
		"#(\3\2\3\4\u00cc\2\37\3\2\2\2\4%\3\2\2\2\6\'\3\2\2\2\b+\3\2\2\2\nB\3\2"+
		"\2\2\fx\3\2\2\2\16\u0086\3\2\2\2\20\u0088\3\2\2\2\22\u0092\3\2\2\2\24"+
		"\u00a8\3\2\2\2\26\u00aa\3\2\2\2\30\u00b4\3\2\2\2\32\u00b6\3\2\2\2\34\36"+
		"\5\4\3\2\35\34\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \3\3\2\2"+
		"\2!\37\3\2\2\2\"&\5\6\4\2#&\5\b\5\2$&\5\n\6\2%\"\3\2\2\2%#\3\2\2\2%$\3"+
		"\2\2\2&\5\3\2\2\2\'(\7\6\2\2()\7\23\2\2)*\7\24\2\2*\7\3\2\2\2+,\7\7\2"+
		"\2,\60\7\23\2\2-/\5\n\6\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2"+
		"\2\2\61\63\3\2\2\2\62\60\3\2\2\2\63\64\7\24\2\2\64\t\3\2\2\2\65\66\b\6"+
		"\1\2\66\67\t\2\2\2\67C\5\n\6\1389\7\"\2\29C\5\n\6\n:C\5\16\b\2;C\5\20"+
		"\t\2<C\5\22\n\2=?\7\25\2\2>@\5\f\7\2?>\3\2\2\2?@\3\2\2\2@A\3\2\2\2AC\7"+
		"\26\2\2B\65\3\2\2\2B8\3\2\2\2B:\3\2\2\2B;\3\2\2\2B<\3\2\2\2B=\3\2\2\2"+
		"Cu\3\2\2\2DE\f\t\2\2EF\t\3\2\2Ft\5\n\6\nGH\f\b\2\2HI\t\4\2\2It\5\n\6\t"+
		"JK\f\7\2\2KL\t\5\2\2Lt\5\n\6\bMN\f\6\2\2NO\7)\2\2Ot\5\n\6\7PQ\f\5\2\2"+
		"QR\7*\2\2Rt\5\n\6\6ST\f\4\2\2TU\7+\2\2UV\5\n\6\2VW\7,\2\2WX\5\n\6\5Xt"+
		"\3\2\2\2YZ\f\3\2\2Z[\7\32\2\2[t\5\n\6\3\\]\f\16\2\2]^\7\25\2\2^_\5\n\6"+
		"\2_`\7\26\2\2`t\3\2\2\2ab\f\r\2\2bd\7\21\2\2ce\5\f\7\2dc\3\2\2\2de\3\2"+
		"\2\2ef\3\2\2\2ft\7\22\2\2gh\f\f\2\2hi\7\27\2\2in\5\30\r\2jk\7\31\2\2k"+
		"m\5\30\r\2lj\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2\2oq\3\2\2\2pn\3\2\2\2"+
		"qr\7\30\2\2rt\3\2\2\2sD\3\2\2\2sG\3\2\2\2sJ\3\2\2\2sM\3\2\2\2sP\3\2\2"+
		"\2sS\3\2\2\2sY\3\2\2\2s\\\3\2\2\2sa\3\2\2\2sg\3\2\2\2tw\3\2\2\2us\3\2"+
		"\2\2uv\3\2\2\2v\13\3\2\2\2wu\3\2\2\2x}\5\n\6\2yz\7\31\2\2z|\5\n\6\2{y"+
		"\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\r\3\2\2\2\177}\3\2\2\2\u0080"+
		"\u0081\7\21\2\2\u0081\u0082\5\n\6\2\u0082\u0083\7\22\2\2\u0083\u0087\3"+
		"\2\2\2\u0084\u0087\5\24\13\2\u0085\u0087\7\13\2\2\u0086\u0080\3\2\2\2"+
		"\u0086\u0084\3\2\2\2\u0086\u0085\3\2\2\2\u0087\17\3\2\2\2\u0088\u0089"+
		"\7\f\2\2\u0089\u008d\7\23\2\2\u008a\u008c\5\26\f\2\u008b\u008a\3\2\2\2"+
		"\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090"+
		"\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7\24\2\2\u0091\21\3\2\2\2\u0092"+
		"\u0093\7\5\2\2\u0093\u0097\7\21\2\2\u0094\u0096\7\13\2\2\u0095\u0094\3"+
		"\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u009a\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7\22\2\2\u009b\u009f\7"+
		"\23\2\2\u009c\u009e\5\n\6\2\u009d\u009c\3\2\2\2\u009e\u00a1\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1\u009f\3\2"+
		"\2\2\u00a2\u00a3\7\24\2\2\u00a3\23\3\2\2\2\u00a4\u00a9\7\17\2\2\u00a5"+
		"\u00a9\7\20\2\2\u00a6\u00a9\7\r\2\2\u00a7\u00a9\5\32\16\2\u00a8\u00a4"+
		"\3\2\2\2\u00a8\u00a5\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a7\3\2\2\2\u00a9"+
		"\25\3\2\2\2\u00aa\u00ab\7\13\2\2\u00ab\u00ac\7\33\2\2\u00ac\u00ad\5\n"+
		"\6\2\u00ad\27\3\2\2\2\u00ae\u00af\7\b\2\2\u00af\u00b5\5\n\6\2\u00b0\u00b1"+
		"\7\t\2\2\u00b1\u00b5\5\n\6\2\u00b2\u00b3\7\n\2\2\u00b3\u00b5\5\n\6\2\u00b4"+
		"\u00ae\3\2\2\2\u00b4\u00b0\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5\31\3\2\2"+
		"\2\u00b6\u00b7\t\6\2\2\u00b7\33\3\2\2\2\22\37%\60?Bdnsu}\u0086\u008d\u0097"+
		"\u009f\u00a8\u00b4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}