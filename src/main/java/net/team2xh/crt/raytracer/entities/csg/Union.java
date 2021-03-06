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
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Union extends CSG {

    public Union(Entity a, Entity b) {
        super(a.material, a, b);
    }
    
    @Override
    public Vector3 getCenter() {
        return a.getCenter().add(b.getCenter()).multiply(0.5);
    }

    // TODO: constructor with material

    @Override
    public Hit intersect(Ray ray) {

        Hit hitA = a.intersect(ray);
        Hit hitB = b.intersect(ray);

        if (hitA.intersects()) {
            if (hitB.intersects())
                return (hitA.entry() < hitB.entry()) ? hitA : hitB;
            return hitA;
        } else if (hitB.intersects()) {
            return hitB;
        }
        return new Hit(null, false, null, 0, 0, null);
    }

}