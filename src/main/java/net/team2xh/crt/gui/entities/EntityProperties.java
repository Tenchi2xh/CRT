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
package net.team2xh.crt.gui.entities;

import java.beans.IntrospectionException;
import org.openide.explorer.propertysheet.PropertySheetView;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class EntityProperties extends PropertySheetView {
    
    public EntityProperties() {
        setPopupEnabled(false);
    }
    
    public void viewProperties(Object object) {
        try {
            setNodes(new Node[]{new BeanNode(object)});
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
