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
import net.team2xh.crt.raytracer.entities.Entity;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Difference extends Entity {

    // A - B
    private Entity a, b;

    public Difference(Entity a, Entity b) {
        super(a.material);
        this.a = a;
        this.b = b;
    }

    @Override
    public Hit intersect(Ray ray) {

        Hit hitA = a.intersect(ray);

        if (hitA.intersects()) {
            Hit hitB = b.intersect(ray);

            if (hitB.intersects() && hitB.distance() < hitA.distance()) {
                Ray insideB = new Ray(ray.direction, hitA.point());
                return b.intersect(insideB);
            }
        }
        return hitA;
    }

}