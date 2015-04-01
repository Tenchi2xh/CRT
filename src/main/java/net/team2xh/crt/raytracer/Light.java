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

import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Light {

    public Vector3 origin;
    public double falloff = -1;
    public double ambient = 0.0;
    public Pigment color;

    public Light(Vector3 origin, Pigment pigment) {
        this.color  = pigment;
        this.origin = origin;
    }

    public Light(Vector3 origin, Pigment pigment, double ambient) {
        this.origin  = origin;
        this.color   = pigment;
        this.ambient = ambient;
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public void setFalloff(double falloff) {
        this.falloff = falloff;
    }

}
