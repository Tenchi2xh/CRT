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
 * Class representing a variable scope.
 *
 * When assigning an identifier to a variable,
 * dynamic pointing tries to resolve the value
 * bound to the identifier.
 *
 * When trying to get the value bound to an identifier,
 * dynamic scoping tries to resolve the value
 * by recursively looking up parent scopes.
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Scope {

    private final List<Variable> variables;
    private Scope parent = null;

    public Scope(Scope parent) {
        this();
        this.parent = parent;
    }

    public Scope() {
        variables = new ArrayList<>();
    }

    /**
     * Adds a variable to the scope.
     *
     * Applies dynamic pointing if the value of the variable
     * is an identifier. If the variable name already exists,
     * the value bound to the name gets updated.
     *
     * @param v Variable to add to the scope
     */
    public void add(Variable v) {
        for (int i = 0; i < variables.size(); ++i) {
            if (variables.get(i).getName().equals(v.getName())) {
                variables.get(i).setValue(v.getValue());
                point(v);
                return;
            }
        }
        variables.add(v);
        point(v);
    }

    /**
     * Applies dynamic pointing to a variable.
     *
     * When a variable name is assigned to an identifier,
     * it should point to the variable bound to that identifier.
     *
     * @param v Variable to apply dynamic pointing to
     */
    private void point(Variable v) {
        if (v.getValue().getClass() == Identifier.class)
            v.setValue(get((Identifier) v.getValue()));
    }

    /**
     * Gets a variable from the current scope hierarchy.
     *
     * Tries to fetch the variable from the current scope,
     * and if it fails, calls recursively on the parents.
     *
     * @param name Name of the variable to get
     * @return Requested variable
     */
    public Variable get(Identifier name) {
        for (Variable v: variables) {
            if (v.getName().equals(name))
                return v;
        }
        if (parent != null)
            return parent.get(name);

        throw new CompilerException("Variable \"" + name + "\" is not defined");
    }

    /**
     * Returns a list of all current variables.
     *
     * @return List of variables
     */
    public List<Variable> getVariables() {
        return variables;
    }

}
