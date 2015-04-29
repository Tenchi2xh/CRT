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
package net.team2xh.crt.raytracer;

import net.team2xh.crt.raytracer.math.Vector3;

/**
 * Class for representing a Ray.
 * A Ray is a Vector3 that has an origin point.
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Ray {

    public Vector3 origin, direction;

    /**
     * Initializes a Ray given its direction and origin point.
     * The direction vector is automatically normalized.
     *
     * @param  direction Direction vector.
     * @param  origin    Origin point.
     */
    public Ray(Vector3 direction, Vector3 origin) {
        this.direction = direction.normalize();
        this.origin = origin;
    }

    /**
     * Ray factory method that generates a Ray
     * that starts at pointA and points to pointB.
     *
     * @param  pointA Origin of the vector.
     * @param  pointB Point being pointed at.
     * @return        Ray from pointA to pointB.
     */
    public static Ray between(Vector3 pointA, Vector3 pointB) {
        Vector3 direction = pointB.subtract(pointA).normalize();
        Vector3 origin = pointA;
        return new Ray(direction, origin);
    }

}

