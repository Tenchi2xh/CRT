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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import net.team2xh.crt.raytracer.math.Vector3;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Vector3Editor extends PropertyEditorSupport implements ExPropertyEditor, InplaceEditor.Factory {

    private final Inplace ed = new Inplace();
    private final Renderer re = new Renderer();
    
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
        
        re.setVector((Vector3) getValue());
        re.setPreferredSize(new Dimension(box.width + 2, box.height));
        re.doLayout();
        
        // Hack: panel components not laid out if not in a window
        JFrame tempFrame = new JFrame();
        tempFrame.add(re);
        tempFrame.pack();

        g.translate(box.x, box.y);
        // paintall() of JPanel doesn't work so we draw each component separately
        JComponent[] cs = new JComponent[]{re.x, re.y, re.z};
        for (JComponent c : cs) {
            g.translate(c.getX() - 2, c.getY());
            c.print(g);
            g.translate(-c.getX() + 2, -c.getY());
        }
        g.translate(-box.x, -box.y);
        
        tempFrame.dispose();
    }
    @Override
    public InplaceEditor getInplaceEditor() {
        return ed;
    }

    private static class Renderer extends JPanel {

        private final JSpinner x = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));
        private final JSpinner y = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));
        private final JSpinner z = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));

        {
            x.setBorder(null);
            y.setBorder(null);
            z.setBorder(null);
            setLayout(new GridLayout(1, 0));
            add(x);
            add(y);
            add(z);
        }

        private void setVector(Vector3 v) {
            x.getModel().setValue(v.x);
            y.getModel().setValue(v.y);
            z.getModel().setValue(v.z);
        }

    }

    private static class Inplace implements InplaceEditor {

        private PropertyEditor editor = null;
        private PropertyModel model;

        private final Renderer panel = new Renderer();

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
            return new Vector3((double) panel.x.getValue(), (double) panel.y.getValue(), (double) panel.z.getValue());
        }

        @Override
        public void setValue(Object o) {
            Vector3 v = (Vector3) o;
            panel.setVector(v);
        }

        @Override
        public boolean supportsTextEntry() {
            return true;
        }

        @Override
        public void reset() {
            Vector3 v = (Vector3) editor.getValue();
            if (v != null) {
                panel.setVector(v);
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
