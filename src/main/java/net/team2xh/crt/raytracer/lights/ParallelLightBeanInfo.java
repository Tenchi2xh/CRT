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
package net.team2xh.crt.raytracer.lights;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import net.team2xh.crt.gui.util.GUIToolkit;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class ParallelLightBeanInfo extends SimpleBeanInfo {
    
    private final static Image ICON = GUIToolkit.getIcon("/icons/parallellight.png");
    
    @Override
    public Image getIcon(int iconKind) {
        return ICON;
    }
    
    // Bean descriptor
    private static BeanDescriptor getBdescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(net.team2xh.crt.raytracer.lights.ParallelLight.class); // NOI18N
        beanDescriptor.setDisplayName("Parallel light");
        beanDescriptor.setShortDescription("A light source which rays are parallel, like the sun.");
        return beanDescriptor;
    }

    // Property identifiers
    private static final int PROPERTY_ambient = 0;
    private static final int PROPERTY_color = 1;
    private static final int PROPERTY_direction = 2;

    // Property array
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];

        try {
            properties[PROPERTY_ambient] = new PropertyDescriptor("ambient", net.team2xh.crt.raytracer.lights.ParallelLight.class, "getAmbient", "setAmbient"); // NOI18N
            properties[PROPERTY_ambient].setDisplayName("Ambient value");
            properties[PROPERTY_ambient].setShortDescription("Amount of light spread evenly everywhere in the scene.");
            properties[PROPERTY_color] = new PropertyDescriptor("color", net.team2xh.crt.raytracer.lights.ParallelLight.class, "getColor", "setColor"); // NOI18N
            properties[PROPERTY_color].setDisplayName("Color");
            properties[PROPERTY_color].setShortDescription("Color of the light source.");
            properties[PROPERTY_color].setPropertyEditorClass(org.netbeans.beaninfo.editors.ColorEditor.class);
            properties[PROPERTY_direction] = new PropertyDescriptor("direction", net.team2xh.crt.raytracer.lights.ParallelLight.class, "getDirection", "setDirection"); // NOI18N
            properties[PROPERTY_direction].setDisplayName("Direction");
            properties[PROPERTY_direction].setShortDescription("Direction of the incoming light.");
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return properties;
    }

    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

}
