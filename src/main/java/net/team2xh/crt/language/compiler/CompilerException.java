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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import static org.apache.commons.lang3.StringUtils.repeat;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class CompilerException extends RuntimeException {

    public CompilerException(String s) {
        super(s);
        System.err.println();
        System.err.println("Compiler error:\n    " + s + ".");
        System.err.println();
    }

    public CompilerException(ParserRuleContext ctx, String code, String s) {
        super(s);

        Token firstToken = ctx.getStart();
        Integer line = firstToken.getLine();
        Integer column = firstToken.getCharPositionInLine();
        String codeLine = code.split("\n")[line-1];

        int a = ctx.start.getStartIndex();
        int b = ctx.stop.getStopIndex();
        Interval interval = new Interval(a, b);
        String codePart = ctx.start.getInputStream().getText(interval);

        int length = codePart.length();
        int offset = 4 + column;
        String underline = repeat(' ', offset) + repeat('¯', length);

        System.err.println();
        System.err.println("Compiler error:");
        System.err.println("    " + s + ".");
        System.err.println("    At line " + line + ", column " + column + ":");
        System.err.println();
        System.err.println("    " + codeLine);
        System.err.println(underline);
        System.err.println();

    }

}
