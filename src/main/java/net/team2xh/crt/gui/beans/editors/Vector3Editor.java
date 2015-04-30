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
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.util.Enumeration;
import javax.swing.JComponent;
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

    @Override
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    private InplaceEditor ed = null;

    @Override
    public InplaceEditor getInplaceEditor() {
        if (ed == null) {
            ed = new Inplace();
        }
        return ed;
    }

    private static class Inplace implements InplaceEditor {

        private final JPanel panel = new JPanel();
        private final JSpinner x = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));
        private final JSpinner y = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));
        private final JSpinner z = new JSpinner(new SpinnerNumberModel((Double) 0.0, null, null, 0.01));
        
        {
            panel.setOpaque(false);
            panel.setBorder(null);
            x.setPreferredSize(new Dimension(50, 15));
            y.setPreferredSize(new Dimension(50, 15));
            z.setPreferredSize(new Dimension(50, 15));
            panel.add(x);
            panel.add(y);
            panel.add(z);
        }
        
        private PropertyEditor editor = null;
        private PropertyModel model;

        private void setVector(Vector3 v) {
            x.getModel().setValue(v.x);
            y.getModel().setValue(v.y);
            z.getModel().setValue(v.z);
        }

        @Override
        public void connect(PropertyEditor propertyEditor, PropertyEnv env) {
            editor = propertyEditor;
            System.out.println(env);
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
            return new Vector3((double) x.getValue(), (double) y.getValue(), (double) z.getValue());
        }

        @Override
        public void setValue(Object o) {
            Vector3 v = (Vector3) o;
            setVector(v);
        }

        @Override
        public boolean supportsTextEntry() {
            return true;
        }

        @Override
        public void reset() {
            Vector3 v = (Vector3) editor.getValue();
            if (v != null) {
                setVector(v);
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
