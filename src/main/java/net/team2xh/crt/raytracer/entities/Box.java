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
public class Box extends Entity {

    private final Vector3 cornerA;
    private final Vector3 cornerB;

    public Box(Vector3 cornerA, Vector3 cornerB, Material material) {
        super(material);
        this.cornerA = cornerA;
        this.cornerB = cornerB;
    }

    private double min(int i) {
        return Math.min(cornerA.components()[i], cornerB.components()[i]);
    }

    private double max(int i) {
        return Math.max(cornerA.components()[i], cornerB.components()[i]);
    }
    
    public double getWidth() {
        return max(0) - min(0);
    }
    
    public double getHeight() {
        return max(1) - min(1);
    }
    
    public double getDepth() {
        return max(2) - min(2);
    }

    public Vector3 getMinCorner() {
        return new Vector3(min(0), min(1), min(2));
    }
    
    @Override
    public Vector3 getCenter() {
        return new Vector3((max(0) - min(0)) / 2, (max(1) - min(1)) / 2, (max(2) - min(2)) / 2);
    }

    private boolean contains(Vector3 point) {
        return point.x >= min(0) && point.x <= max(0)
                && point.y >= min(1) && point.y <= max(1)
                && point.z >= min(2) && point.z <= max(2);
    }

    @Override
    public Hit intersect(Ray ray) {

        // http://people.csail.mit.edu/amy/papers/box-jgt.pdf

        boolean hits = false;
        Vector3 point = Vector3.O;
        double entry = 0;
        double exit = 0;
        Vector3 normal = null;
        String planeNear = "x";
        String planeFar = "x";

        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        double dx = 1.0 / ray.direction.x;
        double dy = 1.0 / ray.direction.y;
        double dz = 1.0 / ray.direction.z;

        if (dx >= 0) {
            tmin = (min(0) - ray.origin.x) * dx;
            tmax = (max(0) - ray.origin.x) * dx;
        } else {
            tmin = (max(0) - ray.origin.x) * dx;
            tmax = (min(0) - ray.origin.x) * dx;
        }

        if (dy >= 0) {
            tymin = (min(1) - ray.origin.y) * dy;
            tymax = (max(1) - ray.origin.y) * dy;
        } else {
            tymin = (max(1) - ray.origin.y) * dy;
            tymax = (min(1) - ray.origin.y) * dy;
        }

        if (!((tmin > tymax) || (tymin > tmax))) {

            if (tymin > tmin) {
                tmin = tymin;
                planeNear = "y";
            }
            if (tymax < tmax) {
                tmax = tymax;
                planeFar = "y";
            }
            if (dz >= 0) {
                tzmin = (min(2) - ray.origin.z) * dz;
                tzmax = (max(2) - ray.origin.z) * dz;
            } else {
                tzmin = (max(2) - ray.origin.z) * dz;
                tzmax = (min(2) - ray.origin.z) * dz;
            }
            if (!((tmin > tzmax) || (tzmin > tmax))) {
                if (tzmin > tmin) {
                    tmin = tzmin;
                    planeNear = "z";
                }
                if (tzmax < tmax) {
                    tmax = tzmax;
                    planeFar = "z";
                }
                if (tmax > Tracer.E) {
                    entry = tmin;
                    exit = tmax;
                    hits = true;
                    
                    String plane = planeNear;
                    
                    
                    if (contains(ray.origin)) {
                        plane = planeFar;
                        entry = tmax;
                        exit = tmin;
                    }
                    
                    switch (plane) {
                        case "x":
                            normal = new Vector3(-ray.direction.x, 0, 0).normalize();
                            break;
                        case "y":
                            normal = new Vector3(0, -ray.direction.y, 0).normalize();
                            break;
                        case "z":
                            normal = new Vector3(0, 0, -ray.direction.z).normalize();
                            break;
                    }
                    
                    point = ray.origin.add(ray.direction.multiply(entry));
                }
            }
        }

        return new Hit(this, hits, point, entry, exit, normal);

    }

}
