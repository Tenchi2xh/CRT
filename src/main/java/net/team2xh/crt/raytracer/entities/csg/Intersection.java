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
package net.team2xh.crt.raytracer.entities.csg;

import net.team2xh.crt.raytracer.Hit;
import net.team2xh.crt.raytracer.Ray;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 * NOT WORKING
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Intersection extends CSG {

    // A ^ B
    private final Entity a, b;

    public Intersection(Entity a, Entity b) {
        super(a.material);
        this.a = a;
        this.b = b;
    }
    
    @Override
    public Vector3 getCenter() {
        return a.getCenter().add(b.getCenter()).multiply(0.5);
    }

    @Override
    public Hit intersect(Ray ray) {

        Hit hitA = a.intersect(ray);
        Hit hitB = b.intersect(ray);

        if (hitA.intersects() && hitB.intersects()) {
            double a0 = hitA.entry();
            double a1 = hitA.exit();
            double b0 = hitB.entry();
            double b1 = hitB.exit();
            
            if (a0 > a1) {
                double temp = a1;
                a1 = a0;
                a0 = temp;
            }
            if (b0 > b1) {
                double temp = b1;
                b1 = b0;
                b0 = temp;
            }
            
            // Both are hit, but are they spacially sharing the same space?
            // Entry and exit points must overlap
            if (a0 <= b1 && b0 <= a1) {
                if (a0 > b0) {
                    hitA.setExit(hitB.exit());
                    return hitA;
                }
                hitB.setExit(hitA.exit());
                return hitB;
            }
            
            
        }

        return new Hit(null, false, null, 0, 0, null);
    }

}