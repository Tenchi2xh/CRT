// Generated from CRT.g4 by ANTLR 4.3
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
		T__0=1, CONFIG=2, SCENE=3, SCALE=4, TRANSLATE=5, ROTATE=6, IDENTIFIER=7, 
		NAME=8, STRING=9, SKIP=10, INTEGER=11, FLOAT=12, BOOLEAN=13, LPAREN=14, 
		RPAREN=15, LBRACE=16, RBRACE=17, LBRACK=18, RBRACK=19, LCHEVR=20, RCHEVR=21, 
		COMMA=22, ASSIGN=23, ATTRIBUTE=24, ADD=25, SUBTRACT=26, INTERSECTION=27, 
		MULTIPLY=28, DIVIDE=29, MODULO=30, NOT=31, LESS=32, GREATER=33, LESS_EQUAL=34, 
		GREATER_EQUAL=35, EQUAL=36, NOT_EQUAL=37, AND=38, OR=39, QUESTION=40, 
		COLON=41;
	public static final String[] tokenNames = {
		"<INVALID>", "'Macro'", "'Config'", "'Scene'", "'scale'", "'translate'", 
		"'rotate'", "IDENTIFIER", "NAME", "STRING", "SKIP", "INTEGER", "FLOAT", 
		"BOOLEAN", "'('", "')'", "'{'", "'}'", "'['", "']'", "LCHEVR", "RCHEVR", 
		"','", "'='", "'->'", "'+'", "'-'", "'^'", "'*'", "'/'", "'%'", "'!'", 
		"LESS", "GREATER", "'<='", "'>='", "'=='", "'!='", "'&&'", "'||'", "'?'", 
		"':'"
	};
	public static final int
		RULE_script = 0, RULE_statement = 1, RULE_config = 2, RULE_scene = 3, 
		RULE_expression = 4, RULE_expressionList = 5, RULE_primary = 6, RULE_object = 7, 
		RULE_macro = 8, RULE_literal = 9, RULE_attribute = 10, RULE_modifier = 11;
	public static final String[] ruleNames = {
		"script", "statement", "config", "scene", "expression", "expressionList", 
		"primary", "object", "macro", "literal", "attribute", "modifier"
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
	}

	public final ScriptContext script() throws RecognitionException {
		ScriptContext _localctx = new ScriptContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_script);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << CONFIG) | (1L << SCENE) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(24); statement();
				}
				}
				setState(29);
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
		public SceneContext scene() {
			return getRuleContext(SceneContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConfigContext config() {
			return getRuleContext(ConfigContext.class,0);
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
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(33);
			switch (_input.LA(1)) {
			case CONFIG:
				enterOuterAlt(_localctx, 1);
				{
				setState(30); config();
				}
				break;
			case SCENE:
				enterOuterAlt(_localctx, 2);
				{
				setState(31); scene();
				}
				break;
			case T__0:
			case IDENTIFIER:
			case NAME:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case LPAREN:
			case LBRACK:
			case ADD:
			case SUBTRACT:
			case NOT:
				enterOuterAlt(_localctx, 3);
				{
				setState(32); expression(0);
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

	public static class ConfigContext extends ParserRuleContext {
		public ConfigContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_config; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).enterConfig(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CRTListener ) ((CRTListener)listener).exitConfig(this);
		}
	}

	public final ConfigContext config() throws RecognitionException {
		ConfigContext _localctx = new ConfigContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_config);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35); match(CONFIG);
			setState(36); match(LBRACE);
			setState(37); match(RBRACE);
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
	}

	public final SceneContext scene() throws RecognitionException {
		SceneContext _localctx = new SceneContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_scene);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39); match(SCENE);
			setState(40); match(LBRACE);
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(41); expression(0);
				}
				}
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(47); match(RBRACE);
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
			setState(62);
			switch (_input.LA(1)) {
			case ADD:
			case SUBTRACT:
				{
				_localctx = new UnarySignContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(50);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUBTRACT) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(51); expression(9);
				}
				break;
			case NOT:
				{
				_localctx = new UnaryNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(52); match(NOT);
				setState(53); expression(8);
				}
				break;
			case IDENTIFIER:
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
			case LPAREN:
				{
				_localctx = new PrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(54); primary();
				}
				break;
			case NAME:
				{
				_localctx = new ObjectExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(55); object();
				}
				break;
			case T__0:
				{
				_localctx = new MacroExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56); macro();
				}
				break;
			case LBRACK:
				{
				_localctx = new ListContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(57); match(LBRACK);
				setState(59);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
					{
					setState(58); expressionList();
					}
				}

				setState(61); match(RBRACK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(113);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(111);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(64);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(65);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULTIPLY) | (1L << DIVIDE) | (1L << MODULO))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(66); expression(8);
						}
						break;

					case 2:
						{
						_localctx = new AdditionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(67);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(68);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUBTRACT) | (1L << INTERSECTION))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(69); expression(7);
						}
						break;

					case 3:
						{
						_localctx = new ComparisonContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(70);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(71);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LESS) | (1L << GREATER) | (1L << LESS_EQUAL) | (1L << GREATER_EQUAL) | (1L << EQUAL) | (1L << NOT_EQUAL))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(72); expression(6);
						}
						break;

					case 4:
						{
						_localctx = new BinaryAndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(73);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(74); match(AND);
						setState(75); expression(5);
						}
						break;

					case 5:
						{
						_localctx = new BinaryOrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(76);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(77); match(OR);
						setState(78); expression(4);
						}
						break;

					case 6:
						{
						_localctx = new TernaryContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(79);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(80); match(QUESTION);
						setState(81); expression(0);
						setState(82); match(COLON);
						setState(83); expression(3);
						}
						break;

					case 7:
						{
						_localctx = new AssignmentContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(85);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(86); match(ASSIGN);
						setState(87); expression(1);
						}
						break;

					case 8:
						{
						_localctx = new ListAccessContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(88);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(89); match(LBRACK);
						setState(90); expression(0);
						setState(91); match(RBRACK);
						}
						break;

					case 9:
						{
						_localctx = new CallContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(93);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(94); match(LPAREN);
						setState(96);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
							{
							setState(95); expressionList();
							}
						}

						setState(98); match(RPAREN);
						}
						break;

					case 10:
						{
						_localctx = new ModifiersContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(99);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(100); match(LCHEVR);
						setState(101); modifier();
						setState(106);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(102); match(COMMA);
							setState(103); modifier();
							}
							}
							setState(108);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(109); match(RCHEVR);
						}
						break;
					}
					} 
				}
				setState(115);
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
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116); expression(0);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(117); match(COMMA);
				setState(118); expression(0);
				}
				}
				setState(123);
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
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_primary);
		try {
			setState(130);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(124); match(LPAREN);
				setState(125); expression(0);
				setState(126); match(RPAREN);
				}
				break;
			case STRING:
			case INTEGER:
			case FLOAT:
			case BOOLEAN:
				enterOuterAlt(_localctx, 2);
				{
				setState(128); literal();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(129); match(IDENTIFIER);
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
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_object);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132); match(NAME);
			setState(133); match(LBRACE);
			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(134); attribute();
				}
				}
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(140); match(RBRACE);
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
	}

	public final MacroContext macro() throws RecognitionException {
		MacroContext _localctx = new MacroContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_macro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142); match(T__0);
			setState(143); match(LPAREN);
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IDENTIFIER) {
				{
				{
				setState(144); match(IDENTIFIER);
				}
				}
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(150); match(RPAREN);
			setState(151); match(LBRACE);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << IDENTIFIER) | (1L << NAME) | (1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN) | (1L << LPAREN) | (1L << LBRACK) | (1L << ADD) | (1L << SUBTRACT) | (1L << NOT))) != 0)) {
				{
				{
				setState(152); expression(0);
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158); match(RBRACE);
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
		public TerminalNode BOOLEAN() { return getToken(CRTParser.BOOLEAN, 0); }
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
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << INTEGER) | (1L << FLOAT) | (1L << BOOLEAN))) != 0)) ) {
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
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attribute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162); match(IDENTIFIER);
			setState(163); match(ATTRIBUTE);
			setState(164); expression(0);
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
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_modifier);
		try {
			setState(172);
			switch (_input.LA(1)) {
			case SCALE:
				enterOuterAlt(_localctx, 1);
				{
				setState(166); match(SCALE);
				setState(167); expression(0);
				}
				break;
			case TRANSLATE:
				enterOuterAlt(_localctx, 2);
				{
				setState(168); match(TRANSLATE);
				setState(169); expression(0);
				}
				break;
			case ROTATE:
				enterOuterAlt(_localctx, 3);
				{
				setState(170); match(ROTATE);
				setState(171); expression(0);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u00b1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\3\3\3\3\3\5\3$\n"+
		"\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5-\n\5\f\5\16\5\60\13\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6>\n\6\3\6\5\6A\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6c\n\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\7\6k\n\6\f\6\16\6n\13\6\3\6\3\6\7\6r\n\6\f\6\16\6u\13\6\3\7\3"+
		"\7\3\7\7\7z\n\7\f\7\16\7}\13\7\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0085\n\b\3"+
		"\t\3\t\3\t\7\t\u008a\n\t\f\t\16\t\u008d\13\t\3\t\3\t\3\n\3\n\3\n\7\n\u0094"+
		"\n\n\f\n\16\n\u0097\13\n\3\n\3\n\3\n\7\n\u009c\n\n\f\n\16\n\u009f\13\n"+
		"\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00af\n"+
		"\r\3\r\2\3\n\16\2\4\6\b\n\f\16\20\22\24\26\30\2\7\3\2\33\34\3\2\36 \3"+
		"\2\33\35\3\2\"\'\4\2\13\13\r\17\u00c2\2\35\3\2\2\2\4#\3\2\2\2\6%\3\2\2"+
		"\2\b)\3\2\2\2\n@\3\2\2\2\fv\3\2\2\2\16\u0084\3\2\2\2\20\u0086\3\2\2\2"+
		"\22\u0090\3\2\2\2\24\u00a2\3\2\2\2\26\u00a4\3\2\2\2\30\u00ae\3\2\2\2\32"+
		"\34\5\4\3\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2\36"+
		"\3\3\2\2\2\37\35\3\2\2\2 $\5\6\4\2!$\5\b\5\2\"$\5\n\6\2# \3\2\2\2#!\3"+
		"\2\2\2#\"\3\2\2\2$\5\3\2\2\2%&\7\4\2\2&\'\7\22\2\2\'(\7\23\2\2(\7\3\2"+
		"\2\2)*\7\5\2\2*.\7\22\2\2+-\5\n\6\2,+\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./"+
		"\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61\62\7\23\2\2\62\t\3\2\2\2\63\64\b\6"+
		"\1\2\64\65\t\2\2\2\65A\5\n\6\13\66\67\7!\2\2\67A\5\n\6\n8A\5\16\b\29A"+
		"\5\20\t\2:A\5\22\n\2;=\7\24\2\2<>\5\f\7\2=<\3\2\2\2=>\3\2\2\2>?\3\2\2"+
		"\2?A\7\25\2\2@\63\3\2\2\2@\66\3\2\2\2@8\3\2\2\2@9\3\2\2\2@:\3\2\2\2@;"+
		"\3\2\2\2As\3\2\2\2BC\f\t\2\2CD\t\3\2\2Dr\5\n\6\nEF\f\b\2\2FG\t\4\2\2G"+
		"r\5\n\6\tHI\f\7\2\2IJ\t\5\2\2Jr\5\n\6\bKL\f\6\2\2LM\7(\2\2Mr\5\n\6\7N"+
		"O\f\5\2\2OP\7)\2\2Pr\5\n\6\6QR\f\4\2\2RS\7*\2\2ST\5\n\6\2TU\7+\2\2UV\5"+
		"\n\6\5Vr\3\2\2\2WX\f\3\2\2XY\7\31\2\2Yr\5\n\6\3Z[\f\16\2\2[\\\7\24\2\2"+
		"\\]\5\n\6\2]^\7\25\2\2^r\3\2\2\2_`\f\r\2\2`b\7\20\2\2ac\5\f\7\2ba\3\2"+
		"\2\2bc\3\2\2\2cd\3\2\2\2dr\7\21\2\2ef\f\f\2\2fg\7\26\2\2gl\5\30\r\2hi"+
		"\7\30\2\2ik\5\30\r\2jh\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2mo\3\2\2\2"+
		"nl\3\2\2\2op\7\27\2\2pr\3\2\2\2qB\3\2\2\2qE\3\2\2\2qH\3\2\2\2qK\3\2\2"+
		"\2qN\3\2\2\2qQ\3\2\2\2qW\3\2\2\2qZ\3\2\2\2q_\3\2\2\2qe\3\2\2\2ru\3\2\2"+
		"\2sq\3\2\2\2st\3\2\2\2t\13\3\2\2\2us\3\2\2\2v{\5\n\6\2wx\7\30\2\2xz\5"+
		"\n\6\2yw\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\r\3\2\2\2}{\3\2\2\2~\177"+
		"\7\20\2\2\177\u0080\5\n\6\2\u0080\u0081\7\21\2\2\u0081\u0085\3\2\2\2\u0082"+
		"\u0085\5\24\13\2\u0083\u0085\7\t\2\2\u0084~\3\2\2\2\u0084\u0082\3\2\2"+
		"\2\u0084\u0083\3\2\2\2\u0085\17\3\2\2\2\u0086\u0087\7\n\2\2\u0087\u008b"+
		"\7\22\2\2\u0088\u008a\5\26\f\2\u0089\u0088\3\2\2\2\u008a\u008d\3\2\2\2"+
		"\u008b\u0089\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008b"+
		"\3\2\2\2\u008e\u008f\7\23\2\2\u008f\21\3\2\2\2\u0090\u0091\7\3\2\2\u0091"+
		"\u0095\7\20\2\2\u0092\u0094\7\t\2\2\u0093\u0092\3\2\2\2\u0094\u0097\3"+
		"\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097"+
		"\u0095\3\2\2\2\u0098\u0099\7\21\2\2\u0099\u009d\7\22\2\2\u009a\u009c\5"+
		"\n\6\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7\23"+
		"\2\2\u00a1\23\3\2\2\2\u00a2\u00a3\t\6\2\2\u00a3\25\3\2\2\2\u00a4\u00a5"+
		"\7\t\2\2\u00a5\u00a6\7\32\2\2\u00a6\u00a7\5\n\6\2\u00a7\27\3\2\2\2\u00a8"+
		"\u00a9\7\6\2\2\u00a9\u00af\5\n\6\2\u00aa\u00ab\7\7\2\2\u00ab\u00af\5\n"+
		"\6\2\u00ac\u00ad\7\b\2\2\u00ad\u00af\5\n\6\2\u00ae\u00a8\3\2\2\2\u00ae"+
		"\u00aa\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\31\3\2\2\2\21\35#.=@blqs{\u0084"+
		"\u008b\u0095\u009d\u00ae";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}