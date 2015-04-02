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
package net.team2xh.crt.raytracer;

import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Hit {

    final public static Hit miss = new Hit(null, false, null, 0, 0, null);

    private Entity entity;
    private boolean intersects;
    private Vector3 point;
    private double entry;
    private double exit;
    private Vector3 normal;

    public Hit(Entity entity, boolean intersects, Vector3 point, double entry, double exit, Vector3 normal) {
        this.entity = entity;
        this.intersects = intersects;
        this.point = point;
        this.entry = entry;
        this.exit = exit;
        this.normal = normal;
    }

    public boolean intersects() { return intersects; }

    public Entity entity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Vector3 point() {
        if (!intersects)
            throw new RuntimeException("Ray does not intersect.");
        return point;
    }

    public double entry() {
        if (!intersects)
            throw new RuntimeException("Ray does not intersect.");
        return entry;
    }

    public double exit() {
        if (!intersects)
            throw new RuntimeException("Ray does not intersect.");
        return exit;
    }

    public Vector3 normal() {
        if (!intersects)
            throw new RuntimeException("Ray does not intersect.");
        return normal;
    }
}
