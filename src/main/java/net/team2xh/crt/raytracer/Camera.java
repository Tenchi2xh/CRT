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
 * NOT WORKING CORRECTLY
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Camera {

    private Vector3 position, pointing, direction, up, right;

    private double roll = 0;

    private double verticalFov;
    private double focalDistance = 0.5;
    private double aperture = 3;
    private ApertureShape shape = ApertureShape.UNIFORM;

    public Camera() {
        this(new Vector3(0.0, 0.0, -1.0), new Vector3(0.0, 0.0, 0.0), 60);
    }

    public Camera(Vector3 position, Vector3 pointing, double verticalFov) {
        this.position = position;
        this.pointing = pointing;
        setVerticalFov(verticalFov);

        direction = pointing.subtract(position).normalize();
        up        = new Vector3(Math.sin(roll), Math.cos(roll), 0.0);
        if (direction.x == 0 && direction.z == 0) {
            if (direction.y > 0) {
                up = Vector3.X;
            } else {
                up = Vector3.Xm;
            }
        }
        // TODO: if looking straight down, up fails
        right     = direction.cross(up).normalize().invert();
        up        = direction.cross(right);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getPointing() {
        return pointing;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 getUp() {
        return up;
    }

    public Vector3 getRight() {
        return right;
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
