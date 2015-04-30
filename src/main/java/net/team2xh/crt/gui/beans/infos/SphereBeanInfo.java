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
package net.team2xh.crt.gui.beans.infos;

import java.beans.*;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class SphereBeanInfo extends SimpleBeanInfo {

    // Bean descriptor
    private static BeanDescriptor getBdescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(net.team2xh.crt.raytracer.entities.Sphere.class, null); // NOI18N
        beanDescriptor.setDisplayName("Sphere");
        beanDescriptor.setShortDescription("Mathematical primitive representing a sphere.");
        return beanDescriptor;
    }

    // Property identifiers
    private static final int PROPERTY_center = 0;
    private static final int PROPERTY_material = 1;
    private static final int PROPERTY_radius = 2;

    // Property array
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];

        try {
            properties[PROPERTY_center] = new PropertyDescriptor("center", net.team2xh.crt.raytracer.entities.Sphere.class, "getCenter", "setCenter"); // NOI18N
            properties[PROPERTY_center].setDisplayName("Center");
            properties[PROPERTY_center].setShortDescription("Coordinates for the center of the sphere.");
            properties[PROPERTY_material] = new PropertyDescriptor("material", net.team2xh.crt.raytracer.entities.Sphere.class, "getMaterial", "setMaterial"); // NOI18N
            properties[PROPERTY_material].setDisplayName("Material");
            properties[PROPERTY_material].setShortDescription("The sphere's material.");
            properties[PROPERTY_material].setValue("i", false);
            properties[PROPERTY_radius] = new PropertyDescriptor("radius", net.team2xh.crt.raytracer.entities.Sphere.class, "getRadius", "setRadius"); // NOI18N
            properties[PROPERTY_radius].setDisplayName("Radius");
            properties[PROPERTY_radius].setShortDescription("The sphere's radius.");
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        // Here you can add code for customizing the properties array.

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
