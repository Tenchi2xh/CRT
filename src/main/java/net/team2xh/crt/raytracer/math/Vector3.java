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

/**
 * Class for representing three dimensional vectors, along with
 * commonly needed operators and factories.
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Vector3 {

    /** Origin vector */ final public static Vector3 O = new Vector3(0.0, 0.0, 0.0);
    /** Unit X vector */ final public static Vector3 X = new Vector3(1.0, 0.0, 0.0);
    /** Unit Y vector */ final public static Vector3 Y = new Vector3(0.0, 1.0, 0.0);
    /** Unit Z vector */ final public static Vector3 Z = new Vector3(0.0, 0.0, 1.0);
    /** Unit X vector */ final public static Vector3 Xm = new Vector3(-1.0, 0.0, 0.0);
    /** Unit Y vector */ final public static Vector3 Ym = new Vector3(0.0, -1.0, 0.0);
    /** Unit Z vector */ final public static Vector3 Zm = new Vector3(0.0, 0.0, -1.0);

    /** X component */ public double x;
    /** Y component */ public double y;
    /** Z component */ public double z;

    /**
     * Initializes a three-dimensional vector.
     *
     * @param  x X component
     * @param  y Y component
     * @param  z Z component
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the result of the subtraction
     * between the current and another vector.
     *
     * @param  other Vector to be subtracted.
     * @return       Result of the subtraction.
     */
    public Vector3 subtract(Vector3 other) {
        double x = this.x - other.x;
        double y = this.y - other.y;
        double z = this.z - other.z;
        return new Vector3(x, y, z);
    }

    /**
     * Returns the result of the addition
     * between the current and another vector.
     *
     * @param  other Vector to be added.
     * @return       Result of the addition.
     */
    public Vector3 add(Vector3 other) {
        double x = this.x + other.x;
        double y = this.y + other.y;
        double z = this.z + other.z;
        return new Vector3(x, y, z);
    }

    /**
     * Returns the result of the scalar multiplication
     * between the current vector and constant.
     *
     * @param d Scalar multiplication constant.
     * @return  Result of the scalar multiplication.
     */
    public Vector3 multiply(double d) {
        return new Vector3(x*d, y*d, z*d);
    }

    /**
     * Returns the magnitude (length) of the current vector.
     *
     * @return Magnitude of the vector.
     */
    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Returns a normalized version of the current vector.
     * The magnitude of a normalized vector is always 1.
     *
     * @return Normal vector.
     */
    public Vector3 normalize() {
        double m = magnitude();
        return new Vector3(x/m, y/m, z/m);
    }

    /**
     * Returns the distance between the current vector
     * (treated as a point) and another vector (ditto).
     *
     * @param  other Point to compute the distance to
     * @return       Distance to the other point.
     */
    public double distanceTo(Vector3 other) {
        return other.subtract(this).magnitude();
    }

    /**
     * Returns the dot product between
     * the current vector and another.
     * The dot product can be used as a shortcut
     * for computing the cosine of the angle
     * between the two vectors.
     *
     * @param  other Second operand of the dot product.
     * @return       Result of the dot product.
     */
    public double dot(Vector3 other) {
        return this.x*other.x + this.y*other.y + this.z*other.z;
    }

    /**
     * Returns the cross product between
     * the current vector and another.
     * The returned vector is perpendicular
     * to the plane formed by the two operands.
     *
     * @param  other Second operand of the cross product.
     * @return       Result of the cross product.
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(y*other.z - z*other.y, z*other.x - x*other.z, x*other.y - y*other.x);
    }

    /**
     * Returns the direction vector of the reflection
     * of the current vector given a normal vector.
     * The result is normalized by design.
     *
     * @param  normal Normal vector of the surface to be reflected on.
     * @return        Direction of the reflected ray.
     */
    public Vector3 reflect(Vector3 normal) {
        // r = v - 2(v·n)n
        double dot = this.dot(normal);
        return this.subtract(normal.multiply(2*dot));
    }

    /**
     * Returns the current vector in the reverse direction.
     *
     * @return Inverted vector.
     */
    public Vector3 invert() {
        return new Vector3(-x, -y, -z);
    }

    /**
     * Returns an array with the three vector components.
     *
     * @return Components array
     */
    public double[] components() {
        return new double[] {x, y, z};
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ", " + z + ">";
    }

}
