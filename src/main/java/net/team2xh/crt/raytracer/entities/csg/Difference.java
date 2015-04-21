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
package net.team2xh.crt.raytracer.entities.csg;

import net.team2xh.crt.raytracer.Hit;
import net.team2xh.crt.raytracer.Ray;
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.entities.Entity;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Difference extends CSG {

    // A - B
    private final Entity a, b;

    public Difference(Entity a, Entity b) {
        super(a.material);
        this.a = a;
        this.b = b;
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
            
            // else, must determine better
            if (a1 >= b0 && a1 <= b1 || b1 >= a0 && b1 <= a1) {
                if (b0 > a0)
                    return hitA;
                // We are in intersection, that could be void
                // send a new ray from *JUST* after b0, so we are inside of B
                Ray newRay = new Ray(ray.direction, ray.origin.add(ray.direction.multiply(b0 + Tracer.E)));
                Hit newHit = b.intersect(newRay);
                // Everything should be relative to initial ray, not inside ray
                newHit.setPoint(ray.origin.add(ray.direction.multiply(b0 + newHit.entry() - Tracer.E)));
                newHit.setEntry(b0 + newHit.entry() - Tracer.E);
                newHit.setExit(b0 + newHit.exit() - Tracer.E);
                newHit.setEntity(b);
                if (b1 < a1 && newHit.intersects())
                    return newHit;
                else
                    return Hit.miss;
            }
        }

        return hitA;
    }

}