// Generated from CRT.g4 by ANTLR 4.3
package net.team2xh.crt.language.parser;
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
		SETTINGS=1, SCENE=2, SCALE=3, TRANSLATE=4, ROTATE=5, TRUE=6, FALSE=7, 
		MACRO=8, IDENTIFIER=9, NAME=10, STRING=11, SKIP=12, INTEGER=13, FLOAT=14, 
		LPAREN=15, RPAREN=16, LBRACE=17, RBRACE=18, LBRACK=19, RBRACK=20, COMMA=21, 
		ASSIGN=22, ATTRIBUTE=23, ADD=24, SUBTRACT=25, INTERSECTION=26, MULTIPLY=27, 
		DIVIDE=28, MODULO=29, NOT=30, LESS=31, GREATER=32, LESS_EQUAL=33, GREATER_EQUAL=34, 
		EQUAL=35, NOT_EQUAL=36, AND=37, OR=38, QUESTION=39, COLON=40, INVALID=41;
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
		"SETTINGS", "SCENE", "SCALE", "TRANSLATE", "ROTATE", "TRUE", "FALSE", 
		"MACRO", "IDENTIFIER", "NAME", "STRING", "SKIP", "INTEGER", "FLOAT", "LPAREN", 
		"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COMMA", "ASSIGN", "ATTRIBUTE", 
		"ADD", "SUBTRACT", "INTERSECTION", "MULTIPLY", "DIVIDE", "MODULO", "NOT", 
		"LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "EQUAL", "NOT_EQUAL", 
		"AND", "OR", "QUESTION", "COLON", "DIGITS", "SPACES", "COMMENT", "LINE_JOINING", 
		"LOWER", "UPPER", "NUMBER", "ESCAPED_QUOTE", "INVALID"
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
		"\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\7\n\u00a1\n\n\f\n\16\n\u00a4\13\n\3\13\3\13\3\13\3\13\7\13\u00aa"+
		"\n\13\f\13\16\13\u00ad\13\13\3\f\3\f\3\f\7\f\u00b2\n\f\f\f\16\f\u00b5"+
		"\13\f\3\f\3\f\3\r\3\r\3\r\5\r\u00bc\n\r\3\r\3\r\3\16\3\16\3\17\3\17\3"+
		"\17\5\17\u00c5\n\17\3\17\3\17\5\17\u00c9\n\17\3\20\3\20\3\21\3\21\3\22"+
		"\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37"+
		"\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3"+
		"\'\3(\3(\3)\3)\3*\3*\7*\u0108\n*\f*\16*\u010b\13*\3+\6+\u010e\n+\r+\16"+
		"+\u010f\3,\3,\3,\3,\7,\u0116\n,\f,\16,\u0119\13,\3-\3-\3.\3.\3/\3/\3\60"+
		"\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3\u00b3\2\63\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K"+
		"\'M(O)Q*S\2U\2W\2Y\2[\2]\2_\2a\2c+\3\2\4\4\2\f\f\17\17\4\2\13\13\"\"\u012f"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2c\3\2\2\2\3"+
		"e\3\2\2\2\5n\3\2\2\2\7t\3\2\2\2\tz\3\2\2\2\13\u0084\3\2\2\2\r\u008b\3"+
		"\2\2\2\17\u0090\3\2\2\2\21\u0096\3\2\2\2\23\u009c\3\2\2\2\25\u00a5\3\2"+
		"\2\2\27\u00ae\3\2\2\2\31\u00bb\3\2\2\2\33\u00bf\3\2\2\2\35\u00c8\3\2\2"+
		"\2\37\u00ca\3\2\2\2!\u00cc\3\2\2\2#\u00ce\3\2\2\2%\u00d0\3\2\2\2\'\u00d2"+
		"\3\2\2\2)\u00d4\3\2\2\2+\u00d6\3\2\2\2-\u00d8\3\2\2\2/\u00da\3\2\2\2\61"+
		"\u00dd\3\2\2\2\63\u00df\3\2\2\2\65\u00e1\3\2\2\2\67\u00e3\3\2\2\29\u00e5"+
		"\3\2\2\2;\u00e7\3\2\2\2=\u00e9\3\2\2\2?\u00eb\3\2\2\2A\u00ed\3\2\2\2C"+
		"\u00ef\3\2\2\2E\u00f2\3\2\2\2G\u00f5\3\2\2\2I\u00f8\3\2\2\2K\u00fb\3\2"+
		"\2\2M\u00fe\3\2\2\2O\u0101\3\2\2\2Q\u0103\3\2\2\2S\u0105\3\2\2\2U\u010d"+
		"\3\2\2\2W\u0111\3\2\2\2Y\u011a\3\2\2\2[\u011c\3\2\2\2]\u011e\3\2\2\2_"+
		"\u0120\3\2\2\2a\u0122\3\2\2\2c\u0125\3\2\2\2ef\7U\2\2fg\7g\2\2gh\7v\2"+
		"\2hi\7v\2\2ij\7k\2\2jk\7p\2\2kl\7i\2\2lm\7u\2\2m\4\3\2\2\2no\7U\2\2op"+
		"\7e\2\2pq\7g\2\2qr\7p\2\2rs\7g\2\2s\6\3\2\2\2tu\7u\2\2uv\7e\2\2vw\7c\2"+
		"\2wx\7n\2\2xy\7g\2\2y\b\3\2\2\2z{\7v\2\2{|\7t\2\2|}\7c\2\2}~\7p\2\2~\177"+
		"\7u\2\2\177\u0080\7n\2\2\u0080\u0081\7c\2\2\u0081\u0082\7v\2\2\u0082\u0083"+
		"\7g\2\2\u0083\n\3\2\2\2\u0084\u0085\7t\2\2\u0085\u0086\7q\2\2\u0086\u0087"+
		"\7v\2\2\u0087\u0088\7c\2\2\u0088\u0089\7v\2\2\u0089\u008a\7g\2\2\u008a"+
		"\f\3\2\2\2\u008b\u008c\7v\2\2\u008c\u008d\7t\2\2\u008d\u008e\7w\2\2\u008e"+
		"\u008f\7g\2\2\u008f\16\3\2\2\2\u0090\u0091\7h\2\2\u0091\u0092\7c\2\2\u0092"+
		"\u0093\7n\2\2\u0093\u0094\7u\2\2\u0094\u0095\7g\2\2\u0095\20\3\2\2\2\u0096"+
		"\u0097\7O\2\2\u0097\u0098\7c\2\2\u0098\u0099\7e\2\2\u0099\u009a\7t\2\2"+
		"\u009a\u009b\7q\2\2\u009b\22\3\2\2\2\u009c\u00a2\5[.\2\u009d\u00a1\5["+
		".\2\u009e\u00a1\5]/\2\u009f\u00a1\5_\60\2\u00a0\u009d\3\2\2\2\u00a0\u009e"+
		"\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\24\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\u00ab\5]/\2"+
		"\u00a6\u00aa\5[.\2\u00a7\u00aa\5]/\2\u00a8\u00aa\5_\60\2\u00a9\u00a6\3"+
		"\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\26\3\2\2\2\u00ad\u00ab\3\2\2"+
		"\2\u00ae\u00b3\7$\2\2\u00af\u00b2\5a\61\2\u00b0\u00b2\n\2\2\2\u00b1\u00af"+
		"\3\2\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b3"+
		"\u00b1\3\2\2\2\u00b4\u00b6\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b7\7$"+
		"\2\2\u00b7\30\3\2\2\2\u00b8\u00bc\5U+\2\u00b9\u00bc\5W,\2\u00ba\u00bc"+
		"\5Y-\2\u00bb\u00b8\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00ba\3\2\2\2\u00bc"+
		"\u00bd\3\2\2\2\u00bd\u00be\b\r\2\2\u00be\32\3\2\2\2\u00bf\u00c0\5S*\2"+
		"\u00c0\34\3\2\2\2\u00c1\u00c2\5S*\2\u00c2\u00c4\7\60\2\2\u00c3\u00c5\5"+
		"S*\2\u00c4\u00c3\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c9\3\2\2\2\u00c6"+
		"\u00c7\7\60\2\2\u00c7\u00c9\5S*\2\u00c8\u00c1\3\2\2\2\u00c8\u00c6\3\2"+
		"\2\2\u00c9\36\3\2\2\2\u00ca\u00cb\7*\2\2\u00cb \3\2\2\2\u00cc\u00cd\7"+
		"+\2\2\u00cd\"\3\2\2\2\u00ce\u00cf\7}\2\2\u00cf$\3\2\2\2\u00d0\u00d1\7"+
		"\177\2\2\u00d1&\3\2\2\2\u00d2\u00d3\7]\2\2\u00d3(\3\2\2\2\u00d4\u00d5"+
		"\7_\2\2\u00d5*\3\2\2\2\u00d6\u00d7\7.\2\2\u00d7,\3\2\2\2\u00d8\u00d9\7"+
		"?\2\2\u00d9.\3\2\2\2\u00da\u00db\7/\2\2\u00db\u00dc\7@\2\2\u00dc\60\3"+
		"\2\2\2\u00dd\u00de\7-\2\2\u00de\62\3\2\2\2\u00df\u00e0\7/\2\2\u00e0\64"+
		"\3\2\2\2\u00e1\u00e2\7`\2\2\u00e2\66\3\2\2\2\u00e3\u00e4\7,\2\2\u00e4"+
		"8\3\2\2\2\u00e5\u00e6\7\61\2\2\u00e6:\3\2\2\2\u00e7\u00e8\7\'\2\2\u00e8"+
		"<\3\2\2\2\u00e9\u00ea\7#\2\2\u00ea>\3\2\2\2\u00eb\u00ec\7>\2\2\u00ec@"+
		"\3\2\2\2\u00ed\u00ee\7@\2\2\u00eeB\3\2\2\2\u00ef\u00f0\7>\2\2\u00f0\u00f1"+
		"\7?\2\2\u00f1D\3\2\2\2\u00f2\u00f3\7@\2\2\u00f3\u00f4\7?\2\2\u00f4F\3"+
		"\2\2\2\u00f5\u00f6\7?\2\2\u00f6\u00f7\7?\2\2\u00f7H\3\2\2\2\u00f8\u00f9"+
		"\7#\2\2\u00f9\u00fa\7?\2\2\u00faJ\3\2\2\2\u00fb\u00fc\7(\2\2\u00fc\u00fd"+
		"\7(\2\2\u00fdL\3\2\2\2\u00fe\u00ff\7~\2\2\u00ff\u0100\7~\2\2\u0100N\3"+
		"\2\2\2\u0101\u0102\7A\2\2\u0102P\3\2\2\2\u0103\u0104\7<\2\2\u0104R\3\2"+
		"\2\2\u0105\u0109\5_\60\2\u0106\u0108\5_\60\2\u0107\u0106\3\2\2\2\u0108"+
		"\u010b\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010aT\3\2\2\2"+
		"\u010b\u0109\3\2\2\2\u010c\u010e\t\3\2\2\u010d\u010c\3\2\2\2\u010e\u010f"+
		"\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110V\3\2\2\2\u0111"+
		"\u0112\7/\2\2\u0112\u0113\7/\2\2\u0113\u0117\3\2\2\2\u0114\u0116\n\2\2"+
		"\2\u0115\u0114\3\2\2\2\u0116\u0119\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0118"+
		"\3\2\2\2\u0118X\3\2\2\2\u0119\u0117\3\2\2\2\u011a\u011b\t\2\2\2\u011b"+
		"Z\3\2\2\2\u011c\u011d\4c|\2\u011d\\\3\2\2\2\u011e\u011f\4C\\\2\u011f^"+
		"\3\2\2\2\u0120\u0121\4\62;\2\u0121`\3\2\2\2\u0122\u0123\7^\2\2\u0123\u0124"+
		"\7$\2\2\u0124b\3\2\2\2\u0125\u0126\13\2\2\2\u0126\u0127\3\2\2\2\u0127"+
		"\u0128\b\62\3\2\u0128d\3\2\2\2\17\2\u00a0\u00a2\u00a9\u00ab\u00b1\u00b3"+
		"\u00bb\u00c4\u00c8\u0109\u010f\u0117\4\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}