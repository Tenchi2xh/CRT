package net.team2xh.crt.language.parser;

// Generated from CRT.g4 by ANTLR 4.3
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CRTLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, SETTINGS=2, SCENE=3, SCALE=4, TRANSLATE=5, ROTATE=6, IDENTIFIER=7, 
		NAME=8, STRING=9, SKIP=10, INTEGER=11, FLOAT=12, BOOLEAN=13, LPAREN=14, 
		RPAREN=15, LBRACE=16, RBRACE=17, LBRACK=18, RBRACK=19, LCHEVR=20, RCHEVR=21, 
		COMMA=22, ASSIGN=23, ATTRIBUTE=24, ADD=25, SUBTRACT=26, INTERSECTION=27, 
		MULTIPLY=28, DIVIDE=29, MODULO=30, NOT=31, LESS=32, GREATER=33, LESS_EQUAL=34, 
		GREATER_EQUAL=35, EQUAL=36, NOT_EQUAL=37, AND=38, OR=39, QUESTION=40, 
		COLON=41;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'"
	};
	public static final String[] ruleNames = {
		"T__0", "SETTINGS", "SCENE", "SCALE", "TRANSLATE", "ROTATE", "IDENTIFIER", 
		"NAME", "STRING", "SKIP", "INTEGER", "FLOAT", "BOOLEAN", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "LCHEVR", "RCHEVR", "COMMA", "ASSIGN", 
		"ATTRIBUTE", "ADD", "SUBTRACT", "INTERSECTION", "MULTIPLY", "DIVIDE", 
		"MODULO", "NOT", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "EQUAL", 
		"NOT_EQUAL", "AND", "OR", "QUESTION", "COLON", "DIGITS", "SPACES", "COMMENT", 
		"LINE_JOINING", "LOWER", "UPPER", "NUMBER", "ESCAPED_QUOTE"
	};


	public CRTLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CRT.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2+\u0129\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\7\b\u0096\n\b\f\b\16\b\u0099\13\b\3"+
		"\t\3\t\3\t\3\t\7\t\u009f\n\t\f\t\16\t\u00a2\13\t\3\n\3\n\3\n\7\n\u00a7"+
		"\n\n\f\n\16\n\u00aa\13\n\3\n\3\n\3\13\3\13\3\13\5\13\u00b1\n\13\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\r\5\r\u00ba\n\r\3\r\3\r\5\r\u00be\n\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00c9\n\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3"+
		"&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3*\3*\3+\3+\7+\u010c\n+\f+\16+\u010f\13+"+
		"\3,\6,\u0112\n,\r,\16,\u0113\3-\3-\3-\3-\7-\u011a\n-\f-\16-\u011d\13-"+
		"\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\62\3\u00a8\2\63\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U\2W\2Y\2[\2]\2_\2a\2c\2\3\2\4\4\2\f\f\17\17\4\2\13"+
		"\13\"\"\u0130\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2"+
		"\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2"+
		"S\3\2\2\2\3e\3\2\2\2\5k\3\2\2\2\7t\3\2\2\2\tz\3\2\2\2\13\u0080\3\2\2\2"+
		"\r\u008a\3\2\2\2\17\u0091\3\2\2\2\21\u009a\3\2\2\2\23\u00a3\3\2\2\2\25"+
		"\u00b0\3\2\2\2\27\u00b4\3\2\2\2\31\u00bd\3\2\2\2\33\u00c8\3\2\2\2\35\u00ca"+
		"\3\2\2\2\37\u00cc\3\2\2\2!\u00ce\3\2\2\2#\u00d0\3\2\2\2%\u00d2\3\2\2\2"+
		"\'\u00d4\3\2\2\2)\u00d6\3\2\2\2+\u00d8\3\2\2\2-\u00da\3\2\2\2/\u00dc\3"+
		"\2\2\2\61\u00de\3\2\2\2\63\u00e1\3\2\2\2\65\u00e3\3\2\2\2\67\u00e5\3\2"+
		"\2\29\u00e7\3\2\2\2;\u00e9\3\2\2\2=\u00eb\3\2\2\2?\u00ed\3\2\2\2A\u00ef"+
		"\3\2\2\2C\u00f1\3\2\2\2E\u00f3\3\2\2\2G\u00f6\3\2\2\2I\u00f9\3\2\2\2K"+
		"\u00fc\3\2\2\2M\u00ff\3\2\2\2O\u0102\3\2\2\2Q\u0105\3\2\2\2S\u0107\3\2"+
		"\2\2U\u0109\3\2\2\2W\u0111\3\2\2\2Y\u0115\3\2\2\2[\u011e\3\2\2\2]\u0120"+
		"\3\2\2\2_\u0122\3\2\2\2a\u0124\3\2\2\2c\u0126\3\2\2\2ef\7O\2\2fg\7c\2"+
		"\2gh\7e\2\2hi\7t\2\2ij\7q\2\2j\4\3\2\2\2kl\7U\2\2lm\7g\2\2mn\7v\2\2no"+
		"\7v\2\2op\7k\2\2pq\7p\2\2qr\7i\2\2rs\7u\2\2s\6\3\2\2\2tu\7U\2\2uv\7e\2"+
		"\2vw\7g\2\2wx\7p\2\2xy\7g\2\2y\b\3\2\2\2z{\7u\2\2{|\7e\2\2|}\7c\2\2}~"+
		"\7n\2\2~\177\7g\2\2\177\n\3\2\2\2\u0080\u0081\7v\2\2\u0081\u0082\7t\2"+
		"\2\u0082\u0083\7c\2\2\u0083\u0084\7p\2\2\u0084\u0085\7u\2\2\u0085\u0086"+
		"\7n\2\2\u0086\u0087\7c\2\2\u0087\u0088\7v\2\2\u0088\u0089\7g\2\2\u0089"+
		"\f\3\2\2\2\u008a\u008b\7t\2\2\u008b\u008c\7q\2\2\u008c\u008d\7v\2\2\u008d"+
		"\u008e\7c\2\2\u008e\u008f\7v\2\2\u008f\u0090\7g\2\2\u0090\16\3\2\2\2\u0091"+
		"\u0097\5]/\2\u0092\u0096\5]/\2\u0093\u0096\5_\60\2\u0094\u0096\5a\61\2"+
		"\u0095\u0092\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0094\3\2\2\2\u0096\u0099"+
		"\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\20\3\2\2\2\u0099"+
		"\u0097\3\2\2\2\u009a\u00a0\5_\60\2\u009b\u009f\5]/\2\u009c\u009f\5_\60"+
		"\2\u009d\u009f\5a\61\2\u009e\u009b\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009d"+
		"\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1"+
		"\22\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a8\7$\2\2\u00a4\u00a7\5c\62\2"+
		"\u00a5\u00a7\n\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00aa"+
		"\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ab\3\2\2\2\u00aa"+
		"\u00a8\3\2\2\2\u00ab\u00ac\7$\2\2\u00ac\24\3\2\2\2\u00ad\u00b1\5W,\2\u00ae"+
		"\u00b1\5Y-\2\u00af\u00b1\5[.\2\u00b0\u00ad\3\2\2\2\u00b0\u00ae\3\2\2\2"+
		"\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\b\13\2\2\u00b3\26"+
		"\3\2\2\2\u00b4\u00b5\5U+\2\u00b5\30\3\2\2\2\u00b6\u00b7\5U+\2\u00b7\u00b9"+
		"\7\60\2\2\u00b8\u00ba\5U+\2\u00b9\u00b8\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba"+
		"\u00be\3\2\2\2\u00bb\u00bc\7\60\2\2\u00bc\u00be\5U+\2\u00bd\u00b6\3\2"+
		"\2\2\u00bd\u00bb\3\2\2\2\u00be\32\3\2\2\2\u00bf\u00c0\7v\2\2\u00c0\u00c1"+
		"\7t\2\2\u00c1\u00c2\7w\2\2\u00c2\u00c9\7g\2\2\u00c3\u00c4\7h\2\2\u00c4"+
		"\u00c5\7c\2\2\u00c5\u00c6\7n\2\2\u00c6\u00c7\7u\2\2\u00c7\u00c9\7g\2\2"+
		"\u00c8\u00bf\3\2\2\2\u00c8\u00c3\3\2\2\2\u00c9\34\3\2\2\2\u00ca\u00cb"+
		"\7*\2\2\u00cb\36\3\2\2\2\u00cc\u00cd\7+\2\2\u00cd \3\2\2\2\u00ce\u00cf"+
		"\7}\2\2\u00cf\"\3\2\2\2\u00d0\u00d1\7\177\2\2\u00d1$\3\2\2\2\u00d2\u00d3"+
		"\7]\2\2\u00d3&\3\2\2\2\u00d4\u00d5\7_\2\2\u00d5(\3\2\2\2\u00d6\u00d7\7"+
		">\2\2\u00d7*\3\2\2\2\u00d8\u00d9\7@\2\2\u00d9,\3\2\2\2\u00da\u00db\7."+
		"\2\2\u00db.\3\2\2\2\u00dc\u00dd\7?\2\2\u00dd\60\3\2\2\2\u00de\u00df\7"+
		"/\2\2\u00df\u00e0\7@\2\2\u00e0\62\3\2\2\2\u00e1\u00e2\7-\2\2\u00e2\64"+
		"\3\2\2\2\u00e3\u00e4\7/\2\2\u00e4\66\3\2\2\2\u00e5\u00e6\7`\2\2\u00e6"+
		"8\3\2\2\2\u00e7\u00e8\7,\2\2\u00e8:\3\2\2\2\u00e9\u00ea\7\61\2\2\u00ea"+
		"<\3\2\2\2\u00eb\u00ec\7\'\2\2\u00ec>\3\2\2\2\u00ed\u00ee\7#\2\2\u00ee"+
		"@\3\2\2\2\u00ef\u00f0\7>\2\2\u00f0B\3\2\2\2\u00f1\u00f2\7@\2\2\u00f2D"+
		"\3\2\2\2\u00f3\u00f4\7>\2\2\u00f4\u00f5\7?\2\2\u00f5F\3\2\2\2\u00f6\u00f7"+
		"\7@\2\2\u00f7\u00f8\7?\2\2\u00f8H\3\2\2\2\u00f9\u00fa\7?\2\2\u00fa\u00fb"+
		"\7?\2\2\u00fbJ\3\2\2\2\u00fc\u00fd\7#\2\2\u00fd\u00fe\7?\2\2\u00feL\3"+
		"\2\2\2\u00ff\u0100\7(\2\2\u0100\u0101\7(\2\2\u0101N\3\2\2\2\u0102\u0103"+
		"\7~\2\2\u0103\u0104\7~\2\2\u0104P\3\2\2\2\u0105\u0106\7A\2\2\u0106R\3"+
		"\2\2\2\u0107\u0108\7<\2\2\u0108T\3\2\2\2\u0109\u010d\5a\61\2\u010a\u010c"+
		"\5a\61\2\u010b\u010a\3\2\2\2\u010c\u010f\3\2\2\2\u010d\u010b\3\2\2\2\u010d"+
		"\u010e\3\2\2\2\u010eV\3\2\2\2\u010f\u010d\3\2\2\2\u0110\u0112\t\3\2\2"+
		"\u0111\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114"+
		"\3\2\2\2\u0114X\3\2\2\2\u0115\u0116\7/\2\2\u0116\u0117\7/\2\2\u0117\u011b"+
		"\3\2\2\2\u0118\u011a\n\2\2\2\u0119\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011cZ\3\2\2\2\u011d\u011b\3\2\2\2"+
		"\u011e\u011f\t\2\2\2\u011f\\\3\2\2\2\u0120\u0121\4c|\2\u0121^\3\2\2\2"+
		"\u0122\u0123\4C\\\2\u0123`\3\2\2\2\u0124\u0125\4\62;\2\u0125b\3\2\2\2"+
		"\u0126\u0127\7^\2\2\u0127\u0128\7$\2\2\u0128d\3\2\2\2\20\2\u0095\u0097"+
		"\u009e\u00a0\u00a6\u00a8\u00b0\u00b9\u00bd\u00c8\u010d\u0113\u011b\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}