// Generated from CRT.g4 by ANTLR 4.3
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
	 * Visit a parse tree produced by {@link CRTParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(@NotNull CRTParser.ExpressionListContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull CRTParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModifier(@NotNull CRTParser.ModifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull CRTParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(@NotNull CRTParser.AttributeContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#config}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConfig(@NotNull CRTParser.ConfigContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#script}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScript(@NotNull CRTParser.ScriptContext ctx);

	/**
	 * Visit a parse tree produced by {@link CRTParser#scene}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScene(@NotNull CRTParser.SceneContext ctx);

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

	/**
	 * Visit a parse tree produced by {@link CRTParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull CRTParser.LiteralContext ctx);
}