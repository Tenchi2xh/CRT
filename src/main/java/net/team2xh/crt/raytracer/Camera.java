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

import net.team2xh.crt.raytracer.math.Matrix4;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 * NOT WORKING CORRECTLY
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Camera {

    private Vector3 position, direction;

    private double roll = 0.0;

    private double verticalFov;
    private double focalDistance = 0.5;
    private double aperture = 3;
    private ApertureShape shape = ApertureShape.UNIFORM;

    private Matrix4 viewMatrix;

    public Camera() {
        this(new Vector3(0.0, 0.0, 0.0), new Vector3(0.0, 0.0, -1.0), 70);
    }

    public Camera(Vector3 position, Vector3 pointing, double verticalFov) {
        this.position = position;
        setVerticalFov(verticalFov);

        /**
         * Adapted from http://www.cubic.org/docs/camera.htm until it worked
         */
        Vector3 d = new Vector3(-pointing.x, -pointing.y, pointing.z);

        Vector3 up    = new Vector3(Math.sin(roll), Math.cos(roll), 0.0);
        Vector3 back  = position.subtract(pointing).normalize();
        Vector3 right = up.cross(back).normalize();
        up = back.cross(right);

        Matrix4 rotation = new Matrix4(right, up, back);
        Matrix4 translation = new Matrix4().setColumn(3, position.invert());

        viewMatrix = rotation.multiply(translation);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Matrix4 getMatrix() {
        return viewMatrix;
    }


    public double getVerticalFov() {
        return verticalFov;
    }

    public void setVerticalFov(double verticalFov) {
        this.verticalFov = Math.toRadians(verticalFov);
    }

    public double getFocalDistance() {
        return focalDistance;
    }

    public void setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
    }

    public double getAperture() {
        return aperture;
    }

    public void setAperture(double aperture) {
        this.aperture = aperture;
    }

    public ApertureShape getShape() {
        return shape;
    }

    public void setShape(ApertureShape shape) {
        this.shape = shape;
    }

    public static void main(String[] args) {
        // Camera cam = new Camera(new Vector3(5, -5, 8), new Vector3(3, 4, 0));
        Camera cam = new Camera(new Vector3(0, 0, 1), new Vector3(0, 0, 0), 70.0);
        // cam.viewMatrix.print();
        System.out.println(cam.viewMatrix.rotate(new Vector3(-0.001, 0.001, -1))); // Dir
        System.out.println(cam.viewMatrix.translate(new Vector3(0, 0, 1))); // Pos
    }


    public enum ApertureShape {
        CIRCLE("Circle"),
        HEXAGON("Hexagon"),
        PENTAGON("Pentagon"),
        SQUARE("Square"),
        TRIANGLE("Triangle"),
        LINE("Line"),
        UNIFORM("Uniform"),
        GAUSSIAN("Gaussian");

        String definition;

        ApertureShape(String definition) {
            this.definition = definition;
        }

        @Override
        public String toString() {
            return definition;
        }
    }
}
