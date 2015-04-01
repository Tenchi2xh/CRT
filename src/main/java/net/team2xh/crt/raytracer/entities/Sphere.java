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

    // @Override
    public Hit intersectOld(Ray ray) {

        /**
         * http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
         */

        boolean intersects = false;
        Vector3 point = null;
        double distance = 0;
        Vector3 normal = null;

        // delta = v^2 - w^2 + r^2
        Vector3 co = ray.origin.subtract(center);
        double v = ray.direction.dot(co);
        double w = co.magnitude();
        double delta = v*v - w*w + radius*radius;

        if (delta == 0) {
            System.out.println("ONLY ONE");
            distance = -v;
            intersects = true;
        } else if (delta > 0) {
            // 2 solutions, return the nearest for now
            double d1 = -v - Math.sqrt(delta);
            double d2 = -v + Math.sqrt(delta);
            distance = Math.min(d1, d2);
            intersects = true;
        }

        if (intersects) {
            point = ray.origin.add(ray.direction.multiply(distance));
            normal = point.subtract(center).normalize();
            // Invert normal if ray originates from inside the sphere
            // if (co.magnitude() < radius) {
            //     System.out.println("INSIDE");
            //     normal = normal.invert();
            // }
        }

        return new Hit(this, intersects, point, distance, normal);
    }

    public Hit intersect(Ray ray) {
        boolean intersects = true;
        Vector3 point = null;
        double distance = -1;
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
                distance = (tca - thc < 0) ? tca + thc : tca - thc;
                point = ray.origin.add(ray.direction.multiply(distance));
                normal = point.subtract(center).normalize();
                if (l.magnitude() < radius)
                    normal = normal.invert();
            }
        }
        return new Hit(this, intersects, point, distance, normal);
    }

    // @Override
    // public Vector3 normal(Vector3 point) {
    //     if (point == null)
    //         return null;
    //     return point.subtract(center).normalize();
    // }
}
