/*
 * Copyright (C) 2015 Hamza Haiken (hamza.haiken@heig-vd.ch)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.team2xh.crt.language.compiler;

import java.util.LinkedList;
import java.util.List;
import net.team2xh.crt.language.parser.CRTBaseVisitor;
import net.team2xh.crt.language.parser.CRTLexer;
import net.team2xh.crt.language.parser.CRTParser;
import net.team2xh.crt.language.parser.CRTParser.AssignmentContext;
import net.team2xh.crt.language.parser.CRTParser.BooleanLiteralContext;
import net.team2xh.crt.language.parser.CRTParser.CallContext;
import net.team2xh.crt.language.parser.CRTParser.ExpressionContext;
import net.team2xh.crt.language.parser.CRTParser.ExpressionListContext;
import net.team2xh.crt.language.parser.CRTParser.FloatLiteralContext;
import net.team2xh.crt.language.parser.CRTParser.IdentifierPrimaryContext;
import net.team2xh.crt.language.parser.CRTParser.IntegerLiteralContext;
import net.team2xh.crt.language.parser.CRTParser.ListAccessContext;
import net.team2xh.crt.language.parser.CRTParser.ListContext;
import net.team2xh.crt.language.parser.CRTParser.ScriptContext;
import net.team2xh.crt.language.parser.CRTParser.StringLiteralContext;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Settings;
import net.team2xh.crt.raytracer.math.Vector3;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
final public class Compiler extends CRTBaseVisitor {

    private Script script;
    private Scope scope;

    private final static String RGB  = "rgb";
    private final static String RGBA = "rgba";
    private final static String VEC3 = "vec3";

    private Compiler() {
        script = new Script();
        scope = new Scope();
    }

    public static Script compile(String code) {

        CRTLexer lexer = new CRTLexer(new ANTLRInputStream(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CRTParser parser = new CRTParser(tokens);
        ParseTree tree = parser.script();

        Compiler compiler = new Compiler();
        compiler.visit(tree);

        return compiler.script;
    }

    @Override
    public Script visitScript(ScriptContext ctx) {
        boolean hasSettings = false;
        boolean hasScene = false;
        for (ParseTree t: ctx.children) {
            Object o = t.accept(this);
            Class c = o.getClass();

            if (c == Variable.class) {
                scope.add((Variable) o);
            } else if (c == Settings.class) {
                hasSettings = true;
                script.setSettings((Settings) o);
            } else if (c == Scene.class) {
                hasScene = true;
                script.setScene((Scene) o);
            } else {
                throw new CompilerException(ctx, "Top-level statements must be either an assignment or settings/scene block");
            }

        }

//        if (!hasSettings)
//            throw new CompilerException(ctx, "Script must define settings block");
//        if (!hasScene)
//            throw new CompilerException(ctx, "Script must define a scene");

        System.out.println(scope.getVariables());

        return script;
    }

    @Override
    public Variable visitAssignment(AssignmentContext ctx) {
        Object left = ctx.expression(0).accept(this);
        Object right = ctx.expression(1).accept(this);

        if (left.getClass() != Identifier.class) {
            throw new CompilerException(ctx, "Left-hand side of assignment must be an identifier");
        }

        Identifier name = (Identifier) left;

        return new Variable(name, right);
    }

    @Override
    public Identifier visitIdentifierPrimary(IdentifierPrimaryContext ctx) {
        return new Identifier(ctx.getText());
    }

    @Override
    public Integer visitIntegerLiteral(IntegerLiteralContext ctx) {
        return Integer.parseInt(ctx.getText());
    }

    @Override
    public Double visitFloatLiteral(FloatLiteralContext ctx) {
        return Double.parseDouble(ctx.getText());

    }

    @Override
    public String visitStringLiteral(StringLiteralContext ctx) {
        // TODO: parse escaped characters
        String str = ctx.getText();
        return str.substring(1, str.length() - 1);
    }

    @Override
    public Boolean visitBooleanLiteral(BooleanLiteralContext ctx) {
        return Boolean.parseBoolean(ctx.getText());
    }

    @Override
    public List<Object> visitList(ListContext ctx) {
        return visitExpressionList(ctx.expressionList());
    }

    @Override
    public List<Object> visitExpressionList(ExpressionListContext ctx) {
        List<ExpressionContext> exprs = new LinkedList<>();
        if (ctx != null)
            exprs = ctx.expression();
        List<Object> result = new LinkedList<>();
        for (int i = 0; i < exprs.size(); ++i) {
            result.add(exprs.get(i).accept(this));
        }
        return result;
    }

    @Override
    public Object visitCall(CallContext ctx) {
        Object left = ctx.expression().accept(this);
        List<Object> arguments = visitExpressionList(ctx.expressionList());

        if (left.getClass() != Identifier.class)
            throw new CompilerException(ctx, "\"" + left + "\" muts be an identifier");

        Identifier name = (Identifier) left;
        double[] args;
        switch (name.getName()) {
            case RGB:
                args = checkArguments(arguments, RGB, 3, ctx);
                return new Pigment(args[0], args[1], args[2]);
            case RGBA:
                args = checkArguments(arguments, RGBA, 4, ctx);
                return new Pigment(args[0], args[1], args[2], args[3]);
            case VEC3:
                args = checkArguments(arguments, VEC3, 3, ctx);
                return new Vector3(args[0], args[1], args[2]);
            default:
                // macro call
                return null;
        }
    }

    private double[] checkArguments(List<Object> arguments, String name, int n, ParserRuleContext ctx) {
        String ex = "\"" + name + "\" takes " + n + " float arguments";
        if (arguments.size() != n)
            throw new CompilerException(ctx, ex);

        double[] args = new double[n];
        for (int i = 0; i < arguments.size(); ++i) {
            if (arguments.get(i).getClass() != Double.class)
                throw new CompilerException(ctx, ex);
            args[i] = (Double) arguments.get(i);
        }

        return args;
    }

    @Override
    public Object visitListAccess(ListAccessContext ctx) {
        Object left = ctx.expression(0).accept(this);
        Object right = ctx.expression(1).accept(this);

        Object listObject = resolve(left);
        Object indexObject = resolve(right);

        if (listObject.getClass() != LinkedList.class)
            throw new CompilerException(ctx, "\"" + left + "\" is not a list");

        if (indexObject.getClass() != Integer.class)
            throw new CompilerException(ctx, "List index must be an integer");

        List<Object> list = (LinkedList) listObject;
        Integer index = (Integer) indexObject;

        if (index >= list.size())
            throw new CompilerException(ctx, "List index out of range");

        return list.get(index);
    }

    /**
     * Returns a value if "thing" is a variable identifier, else the thing itself.
     *
     * @param thing Object to resolve
     * @return Value of variable "thing" or "thing" itself
     */
    private Object resolve(Object thing) {
        Object result = thing;
        if (thing.getClass() == Identifier.class) {
            result = scope.get((Identifier) thing).getValue();
        }
        return result;
    }

}
