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

import java.util.List;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.entities.Entity;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public abstract class CSG extends Entity {

    public CSG(Material material) {
        super(material);
    }

    public static Difference subtract(List<Entity> entities) {
        if (entities.size() < 2)
            throw new RuntimeException("List too small");

        Entity accu = entities.get(0);

        for (int i = 1; i < entities.size(); ++i) {
            accu = new Difference(accu, entities.get(i));
        }

        return (Difference) accu;
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

    public static Union join(List<Entity> entities) {
        if (entities.size() < 2)
            throw new RuntimeException("List too small");

        Entity accu = entities.get(0);

        for (int i = 1; i < entities.size(); ++i) {
            accu = new Union(accu, entities.get(i));
        }

        return (Union) accu;
    }

}
