// Generated from CRT.g4 by ANTLR 4.3
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CRTParser}.
 */
public interface CRTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code objectExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterObjectExpr(@NotNull CRTParser.ObjectExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitObjectExpr(@NotNull CRTParser.ObjectExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(@NotNull CRTParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(@NotNull CRTParser.ModifierContext ctx);

	/**
	 * Enter a parse tree produced by the {@code modifiers}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterModifiers(@NotNull CRTParser.ModifiersContext ctx);
	/**
	 * Exit a parse tree produced by the {@code modifiers}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitModifiers(@NotNull CRTParser.ModifiersContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#scene}.
	 * @param ctx the parse tree
	 */
	void enterScene(@NotNull CRTParser.SceneContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#scene}.
	 * @param ctx the parse tree
	 */
	void exitScene(@NotNull CRTParser.SceneContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull CRTParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(@NotNull CRTParser.LiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code unarySign}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnarySign(@NotNull CRTParser.UnarySignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unarySign}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnarySign(@NotNull CRTParser.UnarySignContext ctx);

	/**
	 * Enter a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(@NotNull CRTParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(@NotNull CRTParser.PrimaryExprContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull CRTParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull CRTParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(@NotNull CRTParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(@NotNull CRTParser.MultiplicationContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(@NotNull CRTParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(@NotNull CRTParser.AttributeContext ctx);

	/**
	 * Enter a parse tree produced by the {@code addition}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddition(@NotNull CRTParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addition}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddition(@NotNull CRTParser.AdditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#macro}.
	 * @param ctx the parse tree
	 */
	void enterMacro(@NotNull CRTParser.MacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#macro}.
	 * @param ctx the parse tree
	 */
	void exitMacro(@NotNull CRTParser.MacroContext ctx);

	/**
	 * Enter a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComparison(@NotNull CRTParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComparison(@NotNull CRTParser.ComparisonContext ctx);

	/**
	 * Enter a parse tree produced by the {@code macroExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMacroExpr(@NotNull CRTParser.MacroExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code macroExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMacroExpr(@NotNull CRTParser.MacroExprContext ctx);

	/**
	 * Enter a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(@NotNull CRTParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(@NotNull CRTParser.AssignmentContext ctx);

	/**
	 * Enter a parse tree produced by the {@code binaryOr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOr(@NotNull CRTParser.BinaryOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryOr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOr(@NotNull CRTParser.BinaryOrContext ctx);

	/**
	 * Enter a parse tree produced by the {@code binaryAnd}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryAnd(@NotNull CRTParser.BinaryAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryAnd}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryAnd(@NotNull CRTParser.BinaryAndContext ctx);

	/**
	 * Enter a parse tree produced by the {@code list}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterList(@NotNull CRTParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code list}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitList(@NotNull CRTParser.ListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#script}.
	 * @param ctx the parse tree
	 */
	void enterScript(@NotNull CRTParser.ScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#script}.
	 * @param ctx the parse tree
	 */
	void exitScript(@NotNull CRTParser.ScriptContext ctx);

	/**
	 * Enter a parse tree produced by the {@code call}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCall(@NotNull CRTParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code call}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCall(@NotNull CRTParser.CallContext ctx);

	/**
	 * Enter a parse tree produced by the {@code listAccess}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterListAccess(@NotNull CRTParser.ListAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listAccess}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitListAccess(@NotNull CRTParser.ListAccessContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(@NotNull CRTParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(@NotNull CRTParser.ExpressionListContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#config}.
	 * @param ctx the parse tree
	 */
	void enterConfig(@NotNull CRTParser.ConfigContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#config}.
	 * @param ctx the parse tree
	 */
	void exitConfig(@NotNull CRTParser.ConfigContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTernary(@NotNull CRTParser.TernaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTernary(@NotNull CRTParser.TernaryContext ctx);

	/**
	 * Enter a parse tree produced by the {@code unaryNot}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryNot(@NotNull CRTParser.UnaryNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryNot}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryNot(@NotNull CRTParser.UnaryNotContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(@NotNull CRTParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(@NotNull CRTParser.PrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link CRTParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(@NotNull CRTParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link CRTParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(@NotNull CRTParser.ObjectContext ctx);
}