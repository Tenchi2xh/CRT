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
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Difference extends CSG {

    public Difference(Entity a, Entity b) {
        super(a.material, a, b);
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

            // if both exits are behind the ray's direction, we're in void
            if (a1 < a0 && b1 < b0) {
                if (a0 < b0) {
                    return Hit.miss;
                }
            }
            
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
            
            // else, must determine better
            if (a0 <= b1 && b0 <= a1) {

                // We are in intersection, that could be void
                // send a new ray from *JUST* after b0, so we are inside of B
                Ray newRay = new Ray(ray.direction, ray.origin.add(ray.direction.multiply(b0 + Tracer.E)));
                Hit newHit = b.intersect(newRay);
                // Everything should be relative to initial ray, not inside ray
                newHit.setPoint(ray.origin.add(ray.direction.multiply(b0 + newHit.entry() - Tracer.E)));
                newHit.setEntry(b0 + newHit.entry() - Tracer.E);
                newHit.setExit(b0 + newHit.exit() - Tracer.E);
                newHit.setEntity(b);
                if (b0 > a0) {
                    hitA.setExit(newHit.entry());
                    return hitA;
                }
                if (b1 < a1 && newHit.intersects())
                    return newHit;
                
                return Hit.miss;
            }
        }

        return hitA;
    }

}