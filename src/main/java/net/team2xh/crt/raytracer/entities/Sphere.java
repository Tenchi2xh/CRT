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

import net.team2xh.crt.raytracer.Hit;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Ray;
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
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
        // http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
        
        // sphere: | x - c |^2 = r^2
        // ray: x = o + dl
        
        // inject: | o + dl - c |^2 = r^2
        // (o + dl - c) . (o + dl - c) = r^2
        
        //      a            b                   c
        // d^2(l.l) + 2d(l.(o - c)) + (o - c).(o - c) - r^2 = 0
        // a = l.l
        // b = 2(l.(o - c))
        // c = (o - c).(o - c) - r^2
        Vector3 co = ray.origin.subtract(center);
        double a = ray.direction.dot(ray.direction);
        double b = 2 * (ray.direction.dot(co));
        double c = co.dot(co) - radius*radius;
        double delta = b*b - 4*a*c;
        
        // No solution
        if (delta < 0)
            return Hit.miss;
        
        double d1 = (-b + Math.sqrt(delta)) / (2*a);
        double d2 = (-b - Math.sqrt(delta)) / (2*a);
        
        // They're both behind
        if (d1 < 0.00001 && d2 < Tracer.E)
            return Hit.miss;
        
        double entry = 0;
        double exit  = 0;
        boolean inside = true;
        
        // If one is negative, we are inside
        if (d2 < 0 && d1 > 0) {
            entry = d1;
            exit  = d2;
        }
        else if (d1 < 0 && d2 > 0) {
            entry = d2;
            exit  = d1;
        }
        else {
            entry = Math.min(d1, d2);
            exit  = Math.max(d1, d2);
            inside = false;
        }
        
        Vector3 point = ray.origin.add(ray.direction.multiply(entry));
        Vector3 normal = inside ? center.subtract(point) : point.subtract(center);
        normal = normal.normalize();
        
        Entity test = this;
        
        if (inside) {
//            point = ray.origin.add(ray.direction.multiply(entry));
        }
                
        return new Hit(test, true, point, entry, exit, normal); 
    }
    
    // @Override
    // public Vector3 normal(Vector3 point) {
    //     if (point == null)
    //         return null;
    //     return point.subtract(center).normalize();
    // }

    @Override
    public Vector3 getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
