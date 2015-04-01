/*
 * Copyright (C) 2015 Hamza Haiken (hamza.haiken@heig-vd.ch)
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

import net.team2xh.crt.raytracer.Hit;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Ray;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Plane extends Entity {

    private Vector3 normal, position;

    public Plane(Vector3 normal, Vector3 position, Material material) {
        super(material);
        this.normal = normal.normalize();
        this.position = position;
    }

    @Override
    public Hit intersect(Ray ray) {

        boolean intersects = false;

        double angle = normal.dot(ray.direction);
        double distance = -1;
        Vector3 point = null;

        if (angle < 0) {

            Vector3 v = position.subtract(ray.origin);
            distance = v.dot(normal) / angle;
            // System.out.println(distance);
            if (distance >= 0) {
                intersects = true;
                point = ray.origin.add(ray.direction.multiply(distance));
            }
        }

        return new Hit(this, intersects, point, distance, normal);
    }
}
