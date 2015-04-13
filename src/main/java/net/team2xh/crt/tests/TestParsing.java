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
package net.team2xh.crt.tests;

import net.team2xh.crt.language.compiler.Compiler;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
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

        String code =
                  "col1 = rgb(1.0, 0.3, 0.2)\n"
                + "col2 = rgba(0.2, 0.2, 0.2, 0.5)\n"
                + "x = vec3(1.0, 0.0, 0.0)\n";

        Compiler.compile(code);
    }
}
