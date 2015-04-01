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
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Ray;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public abstract class Entity {

    public Material material;

    public Entity(Entity entity) {
        this.material = entity.material;
    }

    public Entity(Material material) {
        this.material = material;
    }

    public Entity(Pigment pigment) {
        this.material = new Material(pigment);
    }

    public abstract Hit intersect(Ray ray);

    // public abstract Vector3 normal(Vector3 point);
}
