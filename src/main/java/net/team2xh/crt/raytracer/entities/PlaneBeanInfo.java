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
package net.team2xh.crt.raytracer.entities;

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
public class PlaneBeanInfo extends SimpleBeanInfo {

    private final static Image ICON = GUIToolkit.getIcon("/icons/plane.png");
    
    @Override
    public Image getIcon(int iconKind) {
        return ICON;
    }    
    // Bean descriptor
    private static BeanDescriptor getBdescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(net.team2xh.crt.raytracer.entities.Plane.class, null); // NOI18N
        beanDescriptor.setDisplayName("Plane");
        beanDescriptor.setShortDescription("Mathematical primitive representing an infinite plane.");
        return beanDescriptor;
    }

    // Property identifiers
    private static final int PROPERTY_normal = 0;
    private static final int PROPERTY_position = 1;
    private static final int PROPERTY_material = 2;

    // Property array
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];

        try {
            properties[PROPERTY_normal] = new PropertyDescriptor("normal", net.team2xh.crt.raytracer.entities.Plane.class, "getNormal", "setNormal"); // NOI18N
            properties[PROPERTY_normal].setDisplayName("Normal");
            properties[PROPERTY_normal].setShortDescription("The plane's normal vector.");
            properties[PROPERTY_position] = new PropertyDescriptor("position", net.team2xh.crt.raytracer.entities.Plane.class, "getPosition", "setPosition"); // NOI18N
            properties[PROPERTY_position].setDisplayName("Position");
            properties[PROPERTY_position].setShortDescription("Position of the plane.");
            properties[PROPERTY_material] = new PropertyDescriptor("material", net.team2xh.crt.raytracer.entities.Plane.class, "getMaterial", "setMaterial"); // NOI18N
            properties[PROPERTY_material].setDisplayName("Material");
            properties[PROPERTY_material].setShortDescription("The plane's material.");
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
