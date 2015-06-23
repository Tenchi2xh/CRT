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
package net.team2xh.crt.raytracer.lights;

import java.awt.Color;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public abstract class Light {
    
    private double falloff = -1;
    private double ambient = 0.0;
    private Pigment pigment;
    // For property sheet editor
    private Color color;

    public Light(Pigment pigment) {
        setPigment(pigment);
    }

    public Light(Pigment pigment, double ambient) {
        setPigment(pigment);
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

    public Pigment getPigment() {
        return pigment;
    }

    public void setPigment(Pigment pigment) {
        this.pigment = pigment;
        this.color = pigment.getColor();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        setPigment(new Pigment(color));
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + ", faloff=" + falloff;
    }
    
}
