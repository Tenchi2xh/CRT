/*
 * Copyright (C) 2015 Hamza Haiken <tenchi@team2xh.net>
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
package net.team2xh.crt.tests;

import net.team2xh.crt.language.compiler.Compiler;
import net.team2xh.crt.language.compiler.CompilerException;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class TestParsing {

    public static void main(String[] args) {
        String code1 =
                  "foo = \"bar\"\n"
                + "foo = \"baz\"\n"
                + "int = 42\n"
                + "flt = 13.37\n"
                + "boo = true\n"
                + "test = foo\n"
                + "foo = \"barbar\"";

        String code2 =
                  "foo = [1, 2.0, \"trois\"]\n"
                + "bar = []\n"
                + "ll = [[1,2,3], [4,5,6], [7,8,9]]\n"
                + "baz = foo[2]\n"
                + "f00 = ll[0][1]";

        String code3 =
                  "n = 3.5\n"
                + "col1 = rgb(1.0, 0.3, 0.2)\n"
                + "col2 = rgba(0.2, 0.2, 0.2, 0.5)\n"
                + "x = vec3(n, 0.0, 0.0)\n";

        String code4 =
                  "a = +1\n"
                + "b = -1\n"
                + "c = -a\n"
                + "d = false\n"
                + "e = !d\n"
                + "f = \"bla\" * 5\n"
                + "g = 1.91 * 6 + 1.91\n"
                + "h = (19 + 923) % 60";

        String code5 =
                  "a = true == true\n"
                + "b = true == false\n"
                + "c = true != false\n"
                + "d = 3 >= 2\n"
                + "e = 2.1 < 4\n"
                + "f = \"test\" == \"test\"\n"
                + "g = true || false\n"
                + "h = (3 < 5) || (5 <= 3.8)\n"
                + "i = true && false\n"
                + "j = (3 < 5) ? \"smaller\" : \"bigger\"";

        String code =
                  "Settings {\n" +
                  "    title      -> \"Example 01\"\n" +
                  "    author     -> \"Tenchi (tenchi@team2xh.net)\"\n" +
                  "    date       -> \"08.06.2014\"\n" +
                  "    notes      -> \"Sample CRT scene in TRC language\"\n" +
                  "    gamma      -> 1.0\n" +
                  "}";

        try {
            Compiler.compile(code);
        } catch (CompilerException ex) {

        }
    }
}
