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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Scope {

    private final List<Variable> variables;
    private Scope parent = null;
    private List<Scope> subscopes;

    public Scope(Scope parent) {
        this();
        this.parent = parent;
    }

    public Scope() {
        variables = new ArrayList<>();
        subscopes = new ArrayList<>();
    }

    public void add(Variable v) {
        for (int i = 0; i < variables.size(); ++i) {
            if (variables.get(i).getName().equals(v.getName())) {
                variables.set(i, v);
                return;
            }
        }
        variables.add(v);
    }

    public Variable get(String name) {
        Variable v0 = null;
        for (Variable v1: variables) {
            if (v1.getName().equals(name))
                return v1;
        }
        if (parent != null)
            return parent.get(name);
        return v0;
    }

    public List<Variable> getVariables() {
        return variables;
    }

}
