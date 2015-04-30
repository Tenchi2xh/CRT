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
package net.team2xh.crt.gui.beans.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import net.team2xh.crt.raytracer.Material;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MaterialEditor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    private final Editor editor = new Editor();
    private final Renderer renderer = new Renderer();

    @Override
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    @Override
    public boolean isPaintable() {
        return true;
    }

    @Override
    public void paintValue(Graphics g, Rectangle box) {
        
        renderer.setMaterial((Material) getValue());
        renderer.setPreferredSize(box.getSize());
        renderer.doLayout();
        
        // Hack: panel components not laid out if not in a window
        JFrame tempFrame = new JFrame();
        tempFrame.add(renderer);
        tempFrame.pack();

        g.translate(box.x, box.y);
        // paintall() of JPanel doesn't work so we draw each component separately
        JComponent[] cs = new JComponent[]{renderer.colorSquare, renderer.description, renderer.more};
        for (Component c : cs) {
            int ofs = c == renderer.more ? 0 : - 1;
            g.translate(c.getX() + ofs, c.getY());
            c.paint(g);
            g.translate(-c.getX() - ofs, -c.getY());
        }
        g.translate(-box.x, -box.y);
        
        tempFrame.dispose();
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        return editor;
    }

    private class Renderer extends JPanel {

        private final JButton more = new JButton("\u2026");
        private final JPanel view = new JPanel();
        private final JLabel description = new JLabel();
        private Color color = Color.GRAY;
        private final JLabel colorSquare = new JLabel() {

            private final static int W = 15;

            @Override
            public void paintComponent(Graphics g) {
                g.setColor(color);
                g.fillRect(0, 0, W, W);
                g.setColor(color.brighter());
                g.drawRect(0, 0, W - 1, W - 1);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(W, W);
            }
        };

        {
            setLayout(new BorderLayout());
            view.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 1));
            view.add(colorSquare);
            view.add(description);
            int h = more.getPreferredSize().height;
            more.setPreferredSize(new Dimension(h, h));
            add(view, BorderLayout.LINE_START);
            add(more, BorderLayout.LINE_END);
        }

        private void setMaterial(Material m) {
            color = m.color.getColor();
            colorSquare.repaint();
            description.setText(getDescription(m));
            validate();
            repaint();
        }

        private String getDescription(Material m) {
            return String.format("d: %d%% / r: %d%%", (int) m.diffuse * 100, (int) m.reflectivity * 100);
        }
        
    }

    private class Editor implements InplaceEditor {

        private final Renderer panel = new Renderer();
        
        private PropertyEditor editor = null;
        private PropertyModel model;
        
        @Override
        public void connect(PropertyEditor propertyEditor, PropertyEnv env) {
            editor = propertyEditor;
            reset();
        }

        @Override
        public JComponent getComponent() {
            return panel;
        }

        @Override
        public void clear() {
            editor = null;
            model = null;
        }

        @Override
        public Object getValue() {
            // TODO: return correct material once material editing window is done
            return editor.getValue();
        }

        @Override
        public void setValue(Object o) {
            Material m = (Material) o;
            panel.setMaterial(m);
        }

        @Override
        public boolean supportsTextEntry() {
            return false;
        }

        @Override
        public void reset() {
            Material m = (Material) editor.getValue();
            if (m != null) {
                panel.setMaterial(m);
            }
        }

        @Override
        public void addActionListener(ActionListener al) {
        }

        @Override
        public void removeActionListener(ActionListener al) {
        }

        @Override
        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[0];
        }

        @Override
        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        @Override
        public PropertyModel getPropertyModel() {
            return model;
        }

        @Override
        public void setPropertyModel(PropertyModel model) {
            this.model = model;
        }

        @Override
        public boolean isKnownComponent(Component component) {
            return component == panel;
        }

    }
}
