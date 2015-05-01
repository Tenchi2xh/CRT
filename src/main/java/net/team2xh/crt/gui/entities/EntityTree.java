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
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeSelectionModel;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.lights.Light;
import net.team2xh.crt.raytracer.lights.PointLight;
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
        tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setPopupAllowed(false);

        // Entities
        BeanContext entities = new BeanContextSupport();
        for (Entity e : scene.getEntities()) {
            // TODO: Some way of differentiating similar objects in a scene
            // Counter ? maybe keep track of named variables
            entities.add(e);
        }
        Children entitiesChildren = new BeanChildren(entities);
        Node entitiesNode = new AbstractNode(entitiesChildren);
        entitiesNode.setDisplayName("Entities");
        entitiesNode.setShortDescription("The entities present in the compiled scene.");

        // Lights
        BeanContext lights = new BeanContextSupport();
        for (Light l : scene.getLights()) {
            lights.add(l);
        }
        Children lightsChildren = new BeanChildren(lights);
        Node lightsNode = new AbstractNode(lightsChildren);
        lightsNode.setDisplayName("Lights");
        lightsNode.setShortDescription("The lights present in the compiled scene.");

        // Scene
        Children sceneChildren = new Children.Array();
        sceneChildren.add(new Node[]{lightsNode, entitiesNode});

        Node root = new AbstractNode(sceneChildren);
        root.setDisplayName("Scene");
        root.setShortDescription("The current scene after execution of the script.");

        manager.setRootContext(root);
        
        SwingUtilities.invokeLater(() -> {
           tree.expandAll(); 
        });

    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }
}
