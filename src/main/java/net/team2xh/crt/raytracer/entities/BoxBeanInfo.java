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

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class BoxBeanInfo extends SimpleBeanInfo {
    
    // TODO: Icons
    
    // Bean descriptor
    private static BeanDescriptor getBdescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(net.team2xh.crt.raytracer.entities.Box.class, null); // NOI18N
        beanDescriptor.setDisplayName("Box");
        beanDescriptor.setShortDescription("Mathematical primitive representing a box.");
        return beanDescriptor;
    }

    // Property identifiers
    private static final int PROPERTY_cornerA = 0;
    private static final int PROPERTY_cornerB = 1;
    private static final int PROPERTY_material = 2;

    // Property array
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];

        try {
            properties[PROPERTY_cornerA] = new PropertyDescriptor("cornerA", net.team2xh.crt.raytracer.entities.Box.class, "getCornerA", "setCornerA"); // NOI18N
            properties[PROPERTY_cornerA].setDisplayName("Corner A");
            properties[PROPERTY_cornerA].setShortDescription("One of the two corner defining the box.");
            properties[PROPERTY_cornerB] = new PropertyDescriptor("cornerB", net.team2xh.crt.raytracer.entities.Box.class, "getCornerB", "setCornerB"); // NOI18N
            properties[PROPERTY_cornerB].setDisplayName("Corner B");
            properties[PROPERTY_cornerB].setShortDescription("One of the two corner defining the box.");
            properties[PROPERTY_material] = new PropertyDescriptor("material", net.team2xh.crt.raytracer.entities.Box.class, "getMaterial", "setMaterial"); // NOI18N
            properties[PROPERTY_material].setDisplayName("Material");
            properties[PROPERTY_material].setShortDescription("The box's material.");
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
