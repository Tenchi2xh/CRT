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

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class PointLightBeanInfo extends SimpleBeanInfo {
    
    // TODO: Icons
    
    // Bean descriptor
    private static BeanDescriptor getBdescriptor() {
        BeanDescriptor beanDescriptor = new BeanDescriptor(net.team2xh.crt.raytracer.lights.PointLight.class); // NOI18N
        beanDescriptor.setDisplayName("Point light");
        beanDescriptor.setShortDescription("A punctual light source illuminating the scene.");
        return beanDescriptor;
    }

    // Property identifiers
    private static final int PROPERTY_falloff = 0;
    private static final int PROPERTY_ambient = 1;
    private static final int PROPERTY_color = 2;
    private static final int PROPERTY_origin = 3;

    // Property array
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[4];

        try {
            properties[PROPERTY_falloff] = new PropertyDescriptor("falloff", net.team2xh.crt.raytracer.lights.PointLight.class, "getFalloff", "setFalloff"); // NOI18N
            properties[PROPERTY_falloff].setDisplayName("Falloff");
            properties[PROPERTY_falloff].setShortDescription("Intensity of the light.");
            properties[PROPERTY_ambient] = new PropertyDescriptor("ambient", net.team2xh.crt.raytracer.lights.PointLight.class, "getAmbient", "setAmbient"); // NOI18N
            properties[PROPERTY_ambient].setDisplayName("Ambient value");
            properties[PROPERTY_ambient].setShortDescription("Amount of light spread evenly everywhere in the scene.");
            properties[PROPERTY_color] = new PropertyDescriptor("color", net.team2xh.crt.raytracer.lights.PointLight.class, "getColor", "setColor"); // NOI18N
            properties[PROPERTY_color].setDisplayName("Color");
            properties[PROPERTY_color].setShortDescription("Color of the light source.");
            properties[PROPERTY_color].setPropertyEditorClass(org.netbeans.beaninfo.editors.ColorEditor.class);
            properties[PROPERTY_origin] = new PropertyDescriptor("origin", net.team2xh.crt.raytracer.lights.PointLight.class, "getOrigin", "setOrigin"); // NOI18N
            properties[PROPERTY_origin].setDisplayName("Origin");
            properties[PROPERTY_origin].setShortDescription("Coordinates of the origin of the light source.");
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
