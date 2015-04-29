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
public class ParallelLight extends Light {

    private Vector3 direction;
    
    public ParallelLight(Vector3 from, Vector3 pointingTo, Pigment pigment) {
        super(pigment);
        init(from, pointingTo);
    }
    
    public ParallelLight(Vector3 from, Vector3 pointingTo, Pigment pigment, double ambient) {
        super(pigment, ambient);
        init(from, pointingTo);
    }

    private void init(Vector3 from, Vector3 pointingTo) {
        direction = from.subtract(pointingTo).normalize();
    }
    
    @Override
    public Vector3 getDirection(Vector3 from) {
        return direction;
    }
    
    @Override
    public double getFalloff() {
        return -1;
    }

    @Override
    public double getDistance(Vector3 from) {
        return 10000.0;
    }
    
}
