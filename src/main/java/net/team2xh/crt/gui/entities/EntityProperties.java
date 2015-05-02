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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableColumnModel;
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
        
        // Master hacker - You can't hide by not being public!
        JSplitPane splitpane = (JSplitPane) ((JPanel) getComponent(0)).getComponent(0);
        JScrollPane scrollpane = (JScrollPane) splitpane.getTopComponent();
        JViewport viewport = scrollpane.getViewport();
        JTable table = (JTable) viewport.getView();
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setWidth(120);
        tcm.getColumn(0).setMinWidth(120);
        tcm.getColumn(0).setMaxWidth(120);
    }

    public void viewProperties(Object object) {
        try {
            setNodes(new Node[]{new BeanNode(object)});
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
