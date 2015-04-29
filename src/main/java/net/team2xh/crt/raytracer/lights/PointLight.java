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

import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class PointLight extends Light {

    private Vector3 origin;
    
    public PointLight(Vector3 origin, Pigment pigment) {
        super(pigment);
        init(origin);
    }
    
    public PointLight(Vector3 origin, Pigment pigment, double ambient) {
        super(pigment, ambient);
        init(origin);
    }
    
    private void init(Vector3 origin) {
        this.origin = origin;
    }

    @Override
    public Vector3 getDirection(Vector3 from) {
        return origin.subtract(from).normalize();
    }

    @Override
    public double getDistance(Vector3 from) {
        return from.distanceTo(origin);
    }
    
    public Vector3 getOrigin() {
        return origin;
    }
    
}
