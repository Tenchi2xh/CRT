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
public class Sphere extends Entity {

    private Vector3 center;
    private double radius;

    public Sphere(Vector3 center, double radius, Material material) {
        super(material);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public Hit intersect(Ray ray) {
        boolean intersects = true;
        Vector3 point = null;
        double entry = 0;
        double exit = 0;
        Vector3 normal = null;
        Vector3 l = center.subtract(ray.origin);
        double tca = l.dot(ray.direction);
        if (tca < 0)
            intersects = false;
        else {
            double d2 = l.dot(l) - tca*tca;
            if (d2 > radius*radius)
                intersects = false;
            else {
                // System.out.println("OKDAOKDSAOKSODK");
                double thc = Math.sqrt(radius*radius - d2);

                // Have to figure out which way is in the ray's direction

                double t0 = tca - thc;
                double t1 = tca + thc;

                // http://www.scratchapixel.com/old/assets/Uploads/Lesson007/l007-rayspherecases.png
                if (t0 > 0 && t1 > 0) {
                    entry = t0;
                    exit = t1;
                } else if (t0 == t1) {
                    entry = t0;
                    exit = t1;
                } else if (t0 < 0 && t1 > 0) {
                    entry = t1;
                    exit = t0;
                }

                point = ray.origin.add(ray.direction.multiply(entry));
                normal = point.subtract(center).normalize();

            }
        }
        return new Hit(this, intersects, point, entry, exit, normal);
    }

    // @Override
    // public Vector3 normal(Vector3 point) {
    //     if (point == null)
    //         return null;
    //     return point.subtract(center).normalize();
    // }
}
