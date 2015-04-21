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
package net.team2xh.crt.raytracer.math;

import java.util.Random;

/**
 * Static methods for generating uniform distributions of points
 * inside particular shapes, destined for aperture shapes.
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
final public class UniformDistributions {

    final public static Random rand = new Random();

    /**
     * Returns the coordinates of a random point in the unit circle.
     *
     * Source: http://stackoverflow.com/a/5838991
     *
     * @return Random point in a unit circle
     */
    public static double[] randomCirclePoint() {

        double a = rand.nextDouble();
        double b = rand.nextDouble();

        if (b < a) {
            double tmp = a;
            a = b;
            b = tmp;
        }

        double c = 2*Math.PI*a/b;
        return new double[] {b*Math.cos(c), b*Math.sin(c)};
    }
    
    /**
     * Generates a cosine weighted random vector in a unit hemisphere.
     * 
     * Source: http://www.rorydriscoll.com/2009/01/07/better-sampling/
     * 
     * @return 
     */
    public static Vector3 randomVectorInHemisphere() {
        
        double u1 = rand.nextDouble();
        double u2 = rand.nextDouble();
        
        double r = Math.sqrt(u1);
        double theta = 2 * Math.PI * u2;
        
        double x = r * Math.cos(theta);
        double y = r * Math.sin(theta);
        
        return new Vector3(x, y, Math.sqrt(Math.max(0, 1 - u1)));
    }
    
    public static Vector3 orientedRandomVectorInHemisphere(Vector3 direction) {
        
        Vector3 vec = randomVectorInHemisphere();
        // http://stackoverflow.com/a/20925348
        if (direction.x == 0 && direction.z == 0) {
            if (direction.y < 0) { // rotate 180 degrees
                vec = new Vector3(-vec.x, -vec.y, vec.z);
            }
        } else {
            Vector3 new_y = direction;
            Vector3 new_z = new_y.cross(Vector3.Y);
            Vector3 new_x = new_y.cross(new_z);
            Matrix3 basisChange = new Matrix3(new_x, new_y, new_z);
            vec = basisChange.multiply(vec);
        }
        return vec;
    }

    public static double[] randomPolygonPoint2(int degree) {
        double x, y;
        do {
            x = rand.nextDouble()*2 - 1;
            y = rand.nextDouble()*2 - 1;
        } while(!isInPolygon(degree, x, y));
        return new double[] {x, y};
    }

    private static boolean isInPolygon(int degree, double x, double y) {
        double[][] positions = new double[degree][2];
        double angle = (2*Math.PI) / degree;
        for (int i = 0; i < degree; ++i) {
            positions[i][0] = Math.cos(i*angle);
            positions[i][1] = Math.sin(i*angle);
        }

        /**
         * Source: http://everything2.com/title/Determining+if+a+point+is+inside+a+regular+polygon
         */
        double distToCenter = x*x + y*y;
        for (int i = 0; i < degree; ++i) {
            // Because it's a unit circle, the mean of two coordinates * 2
            // is the center reflected on the edge described by the two points
            double qx = positions[i][0] + positions[(i+1)%degree][0];
            double qy = positions[i][1] + positions[(i+1)%degree][1];
            double distToQ = (x-qx)*(x-qx) + (y-qy)*(y-qy);
            if (distToQ < distToCenter)
                return false;
        }

        return true;
    }

    public static double[] randomPolygonPoint(int degree) {
        /**
         * Source: Prof. J.-F. Hêche
         */

        // First, pick a random point in a triangle
        double a = rand.nextDouble();
        double b = rand.nextDouble();
        // If it's not in the unit square (sum > 1) mirror it
        if (a+b > 1.0) {
            a = 1 - a;
            b = 1 - b;
        }
        // Pick a random triangle in the poly
        int t = rand.nextInt(degree);
        // Calculate the triangle points, the third being (0,0)
        double angle = (2*Math.PI) / degree;
        double[] p0 = {Math.cos((t+0)*angle), Math.sin((t+0)*angle)};
        double[] p1 = {Math.cos((t+1)*angle), Math.sin((t+1)*angle)};
        // Rotate and skew the point to fit the triangle
        // P = a (p0x, p0y) + b (p1x, p1y)
        return new double[] {a*p0[0] + b*p1[0], a*p0[1] + b*p1[1]};
    }

}