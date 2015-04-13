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

/**
 * Scope variables which can point to other variables.
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Variable {

    private Identifier name;
    private Object value;

    public Variable(Identifier name, Object value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name associated with the variable.
     *
     * @return Variable name
     */
    public Identifier getName() {
        return name;
    }

    /**
     * Returns the value bound to the variable.
     *
     * If the stored value is another variable,
     * recursively call to return a non-variable value.
     *
     * @return Bound value
     */
    public Object getValue() {
        if (value instanceof Variable) {
            return ((Variable) value).getValue();
        }
        return value;
    }

    /**
     * Sets the value bound to the variable.
     *
     * @param value New value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        String sep = (value instanceof Variable) ? " -> " : " = ";
        String type = (value instanceof Variable) ? "" : " (" + value.getClass().getSimpleName() + ")";
        return name + sep + value + type;
    }

}
