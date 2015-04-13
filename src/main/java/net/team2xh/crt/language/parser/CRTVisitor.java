// Generated from CRT.g4 by ANTLR 4.3
package net.team2xh.crt.language.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CRTParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CRTVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code objectExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectExpr(@NotNull CRTParser.ObjectExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(@NotNull CRTParser.ModifierContext ctx);

	/**
	 * Visit a parse tree produced by the {@code modifiers}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifiers(@NotNull CRTParser.ModifiersContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#scene}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScene(@NotNull CRTParser.SceneContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull CRTParser.LiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code unarySign}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnarySign(@NotNull CRTParser.UnarySignContext ctx);

	/**
	 * Visit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpr(@NotNull CRTParser.PrimaryExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull CRTParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(@NotNull CRTParser.FloatLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code multiplication}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplication(@NotNull CRTParser.MultiplicationContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(@NotNull CRTParser.AttributeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull CRTParser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code addition}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(@NotNull CRTParser.AdditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#settings}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSettings(@NotNull CRTParser.SettingsContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#macro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro(@NotNull CRTParser.MacroContext ctx);

	/**
	 * Visit a parse tree produced by the {@code comparison}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(@NotNull CRTParser.ComparisonContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#identifierPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierPrimary(@NotNull CRTParser.IdentifierPrimaryContext ctx);

	/**
	 * Visit a parse tree produced by the {@code macroExpr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacroExpr(@NotNull CRTParser.MacroExprContext ctx);

	/**
	 * Visit a parse tree produced by the {@code assignment}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull CRTParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by the {@code binaryOr}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryOr(@NotNull CRTParser.BinaryOrContext ctx);

	/**
	 * Visit a parse tree produced by the {@code binaryAnd}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryAnd(@NotNull CRTParser.BinaryAndContext ctx);

	/**
	 * Visit a parse tree produced by the {@code list}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList(@NotNull CRTParser.ListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(@NotNull CRTParser.ScriptContext ctx);

	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull CRTParser.CallContext ctx);

	/**
	 * Visit a parse tree produced by the {@code listAccess}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAccess(@NotNull CRTParser.ListAccessContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(@NotNull CRTParser.ExpressionListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull CRTParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(@NotNull CRTParser.ParamListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(@NotNull CRTParser.IntegerLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ternary}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTernary(@NotNull CRTParser.TernaryContext ctx);

	/**
	 * Visit a parse tree produced by the {@code unaryNot}
	 * labeled alternative in {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryNot(@NotNull CRTParser.UnaryNotContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(@NotNull CRTParser.PrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(@NotNull CRTParser.ObjectContext ctx);
}