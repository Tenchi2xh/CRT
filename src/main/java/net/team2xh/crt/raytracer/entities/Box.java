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
public class Box extends Entity {

    private Vector3 cornerA;
    private Vector3 cornerB;

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
        Vector3 normal = Vector3.O;

        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        double dx = 1.0 / ray.direction.x;
        double dy = 1.0 / ray.direction.y;
        double dz = 1.0 / ray.direction.z;

        if (dx >= 0) {
            tmin = (min(0) - ray.origin.x) * dx;
            tmax = (max(0) - ray.origin.x) * dx;
            normal = Vector3.Xm;
        } else {
            tmin = (max(0) - ray.origin.x) * dx;
            tmax = (min(0) - ray.origin.x) * dx;
            normal = Vector3.X;
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
                normal = (dy >= 0) ? Vector3.Ym : Vector3.Y;
            }
            if (tymax < tmax) {
                tmax = tymax;
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
                    normal = (dz >= 0) ? Vector3.Zm : Vector3.Z;
                }
                if (tzmax < tmax) {
                    tmax = tzmax;
                }
                if (tmax > 0.0001) {
                    entry = tmin;
                    exit = tmax;
                    hits = true;
                    point = ray.origin.add(ray.direction.multiply(entry));
                }
            }
        }

        return new Hit(this, hits, point, entry, exit, normal);

    }

}
