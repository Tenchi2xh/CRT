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
import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.beancontext.BeanContext;
import java.beans.beancontext.BeanContextSupport;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeSelectionModel;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.lights.Light;
import org.apache.commons.lang3.StringEscapeUtils;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanChildren;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class EntityTree extends JPanel implements ExplorerManager.Provider {

    private final ExplorerManager manager = new ExplorerManager();
    private final BeanTreeView tree = new BeanTreeView();
    private final EntityProperties properties = new EntityProperties();

    private final static Image ICON_SCENE = GUIToolkit.getIcon("/icons/scene.png");;
    private final static Image ICON_ENTITIES = GUIToolkit.getIcon("/icons/entities.png");
    private final static Image ICON_LIGHTS = GUIToolkit.getIcon("/icons/lights.png");;
    
    public EntityTree(Scene scene) {
        this();
        loadScene(scene);
    }
    
    public EntityTree() {
        setLayout(new BorderLayout());
        add(tree, BorderLayout.CENTER);
        add(properties, BorderLayout.PAGE_END);

        tree.getVerticalScrollBar().setUnitIncrement(16);
        tree.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setPopupAllowed(false);
        
//        ((JViewport) tree.getComponent(0)).getComponent(0).setForeground(Color.white);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    public void loadScene(Scene scene) {
        // Entities
        List<Node> entities = new LinkedList<>();
        for (Entity e : scene.getEntities()) {
            try {
                // TODO: Some way of differentiating similar objects in a scene
                // Counter ? maybe keep track of named variables
                entities.add(new DynamicNode(e) {
                    @Override
                    public String getHtmlDisplayName() {
                        return getDisplayName()
                               + " <font color='!controlShadow'><i>"
                               + StringEscapeUtils.escapeHtml3(e.getCenter().toString()) + "</i></font>";
                    }
                });
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        Children entitiesChildren = new Children.Array();
        entitiesChildren.add(entities.toArray(new Node[0]));
        for (Node n : entitiesChildren.getNodes()) {
            
        }
        Node entitiesNode = createNode(entitiesChildren, ICON_ENTITIES);
        entitiesNode.setDisplayName("Entities");
        entitiesNode.setShortDescription("The entities present in the compiled scene.");

        // Lights
        BeanContext lights = new BeanContextSupport();
        for (Light l : scene.getLights()) {
            lights.add(l);
        }
        Children lightsChildren = new BeanChildren(lights);
        Node lightsNode = createNode(lightsChildren, ICON_LIGHTS);
        lightsNode.setDisplayName("Lights");
        lightsNode.setShortDescription("The lights present in the compiled scene.");

        // Scene
        Children sceneChildren = new Children.Array();
        sceneChildren.add(new Node[]{lightsNode, entitiesNode});

        Node root = createNode(sceneChildren, ICON_SCENE);
        root.setDisplayName("Scene");
        root.setShortDescription("The current scene after execution of the script.");

        manager.setRootContext(root);

        SwingUtilities.invokeLater(() -> {
            tree.expandAll();
        });
    }
    
    private Node createNode(Children children, Image icon) {
        return new AbstractNode(children) {
            @Override
            public Image getIcon(int iconKind) {
                return icon;
            }
            @Override
            public Image getOpenedIcon(int iconKind) {
                return icon;
            }
        };
    }
    
    private static class DynamicNode extends BeanNode implements PropertyChangeListener {

        // TODO: NOT WORKING
        
        public DynamicNode(Object o) throws IntrospectionException {
            super(o);
            addPropertyChangeListener(this);
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            System.out.println("!!! " + evt.getPropertyName());
            fireDisplayNameChange(null, getDisplayName());
        }
        
    }
}
