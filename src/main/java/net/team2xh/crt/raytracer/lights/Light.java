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
package net.team2xh.crt.raytracer.lights;

import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public abstract class Light {
    
    private double falloff = -1;
    private double ambient = 0.0;
    private Pigment color;

    public Light(Pigment pigment) {
        this.color  = pigment;
    }

    public Light(Pigment pigment, double ambient) {
        this.color   = pigment;
        this.ambient = ambient;
    }

    abstract public Vector3 getDirection(Vector3 from);
    
    abstract public double getDistance(Vector3 from);

    public double getFalloff() {
        return falloff;
    }

    public void setFalloff(double falloff) {
        this.falloff = falloff;
    }

    public double getAmbient() {
        return ambient;
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public Pigment getColor() {
        return color;
    }

    public void setColor(Pigment color) {
        this.color = color;
    }

}
