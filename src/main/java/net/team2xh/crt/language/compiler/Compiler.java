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

import net.team2xh.crt.language.parser.CRTBaseVisitor;
import net.team2xh.crt.language.parser.CRTLexer;
import net.team2xh.crt.language.parser.CRTParser;
import net.team2xh.crt.language.parser.CRTParser.AssignmentContext;
import net.team2xh.crt.language.parser.CRTParser.LiteralContext;
import net.team2xh.crt.language.parser.CRTParser.PrimaryContext;
import net.team2xh.crt.language.parser.CRTParser.ScriptContext;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Settings;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
final public class Compiler extends CRTBaseVisitor {

    private Script script;
    private Scope scope;

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
                throw new CompilerException("Top-level statements must be either an assignment or settings/scene block");
            }

        }

//        if (!hasSettings)
//            throw new CompilerException("Script must define settings block");
//        if (!hasScene)
//            throw new CompilerException("Script must define a scene");

        System.out.println(scope.getVariables());

        return script;
    }

    @Override
    public Variable visitAssignment(AssignmentContext ctx) {
        Object left = ctx.expression(0).accept(this);
        Object right = ctx.expression(1).accept(this);

        String name = "";
        Object value = right;

        if (left.getClass() != TerminalNodeImpl.class) {
            throw new CompilerException("Left-hand side of assignment must be an identifier");
        }

        TerminalNode tn = (TerminalNode) left;

        if (tn.getSymbol().getType() != CRTLexer.IDENTIFIER) {
            throw new CompilerException("Left-hand side of assignment must be an identifier");
        }

        name = tn.getText();

        System.out.println(name);
        System.out.println(name.getClass());
        System.out.println(value);
        System.out.println(value.getClass());

        return new Variable(name, value);
    }

    @Override
    public Object visitPrimary(PrimaryContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            return ctx.IDENTIFIER();
        } else return visitChildren(ctx);
    }

    @Override
    public Object visitLiteral(LiteralContext ctx) {
        ParseTree node = ctx.getChild(0);
        if (node.getClass()!= TerminalNodeImpl.class) {
            return Boolean.parseBoolean(node.getText());
        }
        TerminalNode lit = (TerminalNode) node;
        switch (lit.getSymbol().getType()) {
            case CRTLexer.INTEGER:
                return Integer.parseInt(lit.getText());
            case CRTLexer.FLOAT:
                return Double.parseDouble(lit.getText());
            case CRTLexer.STRING:
                // TODO: parse better
                String str = lit.getText();
                return str.subSequence(1, str.length() - 1);
            default:
                return null;
        }
    }
}
