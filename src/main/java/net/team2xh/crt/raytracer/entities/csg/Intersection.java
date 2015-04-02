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

import java.util.List;
import net.team2xh.crt.raytracer.Hit;
import net.team2xh.crt.raytracer.Ray;
import net.team2xh.crt.raytracer.entities.Entity;

/**
 * NOT WORKING
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Intersection extends Entity {

    // A ^ B
    private Entity a, b;

    public Intersection(Entity a, Entity b) {
        super(a.material);
        this.a = a;
        this.b = b;
    }

    @Override
    public Hit intersect(Ray ray) {

        Hit hitA = a.intersect(ray);
        Hit hitB = b.intersect(ray);
        hitA.setEntity(this);
        hitB.setEntity(this);

        if (hitA.intersects() && hitB.intersects()) {
            double a0 = hitA.entry();
            double a1 = hitA.exit();
            double b0 = hitB.entry();
            double b1 = hitB.exit();
            // Both are hit, but are they spacially sharing the same space..
            // Entry and exit points must overlap
            // A: -----a0=====a1--------
            // B: --------b0=====b1-----
            //             or
            // A: --------a0=====a1-----
            // B: -----b0=====b1--------
            if (a1 >= b0 && a1 <= b1 || b1 >= a0 && b1 <= a1) {
                if (a0 > b0)
                    return hitA;
                return hitB;
            }
        }

        return new Hit(null, false, null, 0, 0, null);
    }

    public static Intersection intersect(List<Entity> entities) {
        if (entities.size() < 2)
            throw new RuntimeException("List too small");

        Entity accu = entities.get(0);

        for (int i = 1; i < entities.size(); ++i) {
            accu = new Intersection(accu, entities.get(i));
        }

        return (Intersection) accu;
    }

}