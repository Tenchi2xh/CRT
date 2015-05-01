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

import java.awt.BorderLayout;
import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextSupport;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Entity;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanChildren;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class EntityTree extends JPanel implements ExplorerManager.Provider {

    private final ExplorerManager manager = new ExplorerManager();
    private final BeanTreeView tree = new BeanTreeView();
    private final EntityProperties properties = new EntityProperties();

    public EntityTree(Scene scene) {
        setLayout(new BorderLayout());
        add(tree, BorderLayout.CENTER);
        add(properties, BorderLayout.PAGE_END);

        tree.getVerticalScrollBar().setUnitIncrement(16);
        tree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        BeanContext beans = new BeanContextSupport();

        for (Entity e : scene.getEntities()) {
            beans.add(e);
        }

        Children children = new BeanChildren(beans);

        Node root = new AbstractNode(children);
        root.setDisplayName("Scene");
        root.setShortDescription("The current scene after execution of the script.");

        manager.setRootContext(root);

    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }
}
