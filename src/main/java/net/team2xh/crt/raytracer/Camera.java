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
package net.team2xh.crt.raytracer;

import net.team2xh.crt.raytracer.math.Vector3;

/**
 * Represents a camera watching the scene.
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public final class Camera {

    private Vector3 position;           // Camera's position in space
    private Vector3 pointing;           // Point the camera is pointing towards
    private Vector3 direction;          // Direction vector
    private Vector3 up;                 // Points up compared to where the camera is pointing to
    private Vector3 right;              // Points right compared to where the camera is pointing to

    private double roll = 0;            // Angle of rotation around direction vector

    private double verticalFov;         // Vertical field of view angle in radians
    private double focalDistance = 0.5; // Radius around the camera's position that is in focus
    private double aperture = 3;        // Size of the aperture hole -- amount of "blurriness"

    private ApertureShape shape = ApertureShape.CIRCLE; // Shape of the aperture

    /**
     * Constructs a camera placed at z = -1, 
     * pointing towards the origin, with 60° of vertical field of view.
     */
    public Camera() {
        this(Vector3.Zm, Vector3.O, 60);
    }

    /**
     * Constructs a camera in a given position and "look at" point.
     * 
     * @param position    Position of the camera
     * @param pointing    Point that the camera is pointing towards
     * @param verticalFov Vertical field of view angle in <b>degrees</b>
     */
    public Camera(Vector3 position, Vector3 pointing, double verticalFov) {
        this.position = position;
        this.pointing = pointing;
        
        setVerticalFov(verticalFov);
        resetVectors();
    }
    
    /**
     * Computes the up and right vectors.
     */
    private void resetVectors() {
        direction = pointing.subtract(position).normalize();
        // The up vector can be approximated by taking the coordinates of a point
        // on the unit circle rotated by the roll angle
        up = new Vector3(Math.sin(roll), Math.cos(roll), 0.0);
        // If the direction is pointing straight up or straight down,
        // the up approximation won't work since it is parallel with the direction
        if (direction.x == 0 && direction.z == 0) {
            if (direction.y > 0) {
                up = Vector3.X;
            } else {
                up = Vector3.Xm;
            }
        }
        // We can obtain the right vector with "direction x up"
        // The result is inverted because of left-handedness
        right = direction.cross(up).normalize().invert();
        // The approximated up direction can now be correctly computed with "direction x right"
        up = direction.cross(right);
    }

    /**
     * Returns the camera's roll angle
     * 
     * @return Rotation angle around the direction vector
     */
    public double getRoll() {
        return roll;
    }

    /**
     * Sets the camera's roll angle
     * 
     * @param roll Desired rotation angle around the direciton vector
     */
    public void setRoll(double roll) {
        this.roll = roll;
        // Since we roll, the up vector should be re-calculated
        resetVectors();
    }

    /**
     * Returns the camera's position in the scene
     * 
     * @return The camera's position
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Returns the point that the camera is pointing towards
     * 
     * @return "Look at" point
     */
    public Vector3 getPointing() {
        return pointing;
    }

    /**
     * Returns the camera's direction vector
     * 
     * @return Direction vector
     */
    public Vector3 getDirection() {
        return direction;
    }

    /**
     * Returns the camera's up vector
     * 
     * @return Up vector
     */
    public Vector3 getUp() {
        return up;
    }

    /**
     * Returns the camera's right vector
     * 
     * @return Right vector
     */
    public Vector3 getRight() {
        return right;
    }

    /**
     * Returns the camera's vertical field of view angle in <b>radians</b>
     * 
     * @return Vertical field of view angle in <b>radians</b>
     */
    public double getVerticalFov() {
        return verticalFov;
    }

    /**
     * Sets the camera's vertical field of view in <b>degrees</b>
     * 
     * @param verticalFov Desired vertical field of view in <b>degrees</b>
     */
    public void setVerticalFov(double verticalFov) {
        this.verticalFov = Math.toRadians(verticalFov);
    }

    /**
     * Returns the camera's focal distance
     * 
     * @return Focal distance, radius around the camera that is in focus
     */
    public double getFocalDistance() {
        return focalDistance;
    }

    /**
     * Sets the camera's focal distance
     * 
     * @param focalDistance Desired focal distance, radius around the camera that is in focus
     */
    public void setFocalDistance(double focalDistance) {
        this.focalDistance = focalDistance;
    }

    /**
     * Returns the camera's aperture size
     * 
     * The aperture size defines the amount of blurriness
     * for out-of-focus objects
     * 
     * @return Size of the aperture hole
     */
    public double getAperture() {
        return aperture;
    }

    /**
     * Sets the camera's aperture size
     * 
     * The aperture size defines the amount of blurriness
     * for out-of-focus objects
     * 
     * @param aperture Desired size of the aperture hole
     */
    public void setAperture(double aperture) {
        this.aperture = aperture;
    }

    /**
     * Returns the shape of the camera's aperture
     * 
     * The shape of the aperture defines the shape
     * and feel of the out-of-focus blurriness (bokeh)
     * 
     * @return Shape of the camera's aperture
     */
    public ApertureShape getShape() {
        return shape;
    }

    /**
     * Sets the shape of the camera's apertue
     * 
     * The shape of the aperture defines the shape
     * and feel of the out-of-focus blurriness (bokeh)
     * 
     * @param shape 
     */
    public void setShape(ApertureShape shape) {
        this.shape = shape;
    }
    
    /**
     * Represents the shape of the camera's aperture
     */
    public enum ApertureShape {
        CIRCLE("Circle"),
        HEXAGON("Hexagon"),
        PENTAGON("Pentagon"),
        SQUARE("Square"),
        TRIANGLE("Triangle"),
        LINE("Line"),
        UNIFORM("Uniform"),
        GAUSSIAN("Gaussian");

        String name;

        ApertureShape(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
