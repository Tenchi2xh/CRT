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

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 * Class that represents a scene's background.
 * 
 * Rays that hit nothing end up at "infinity" and their color
 * is given by the Background class.
 * 
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Background {

    private BufferedImage image = null; // Sky image
    
    private Pigment horizon = null;     // Horizon color
    private Pigment sky     = null;     // Zenith color
    private Pigment color   = null;     // Global color

    private int width  = 0;
    private int height = 0;
    
    private double initialAngle = 0.0;  // Rotation angle around Y-axis for sky images

    /**
     * Produces a pure white background
     */
    public Background() {
        this.color = new Pigment(1.0);
    }

    /**
     * Produces a background filled with the given color
     * 
     * @param color Background color
     */
    public Background(Pigment color) {
        this.color = color;
    }

    /**
     * Produces a background from a spherical mapped 360° panorama picture 
     * 
     * @param imagePath Background picture, mapping must be spherical
     */
    public Background(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            width  = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            // In case the file does not exist, make a white background
            color = new Pigment(1.0);
        }
    }

    /**
     * Produces a background from a spherical mapped 360° panorama picture with initial angle
     * 
     * @param imagePath Background picture, mapping must be spherical
     * @param a0        Initial rotation angle around Y-axis
     */
    public Background(String imagePath, double a0) {
        this(imagePath);
        initialAngle = a0;
    }

    /**
     * Produces a background that has a linearly mapped gradient
     * from the horizon towards the zenith (and its opposite)
     * 
     * @param horizon Color for the horizon
     * @param sky     Color for the zenith
     */
    public Background(Pigment horizon, Pigment sky) {
        this.horizon = horizon;
        this.sky     = sky;
    }

    /**
     * Returns the background color for a given ray.
     * 
     * @param ray Ray to "hit" the background
     * @return    Color of that point
     */
    public Pigment getPoint(Ray ray) {
        // Normalize just in case
        Vector3 direction = ray.direction.normalize();

        // Simple colored backgrounds don't depend on the direction
        if (color != null) {
            return color;
        }
        // Straight forward linear interpolation
        else if (horizon != null) {
            double r = Math.abs(direction.y);
            return horizon.mul(1-r).add(sky.mul(r));
        }
        // Project the ray onto the spherical mapped panorama
        else {
            // Angle of the direction projected on the XZ plane
            // x is inverted because of left-handedness
            double a = (initialAngle + Math.atan2(-direction.x, direction.z) + Math.PI) % (2*Math.PI);
            // Normalize angles from 0 to 1
            double ax = (0.5*a/Math.PI);
            double ay = ((direction.y/2) + 0.5);
            // Compute coordinates on the picture
            double x = (1-ax) * width;
            double y = (1-ay) * height;
            // Whole part
            int xx = (int)x;
            int yy = (int)y;
            // Fractional part, used for bilinear interpolation
            double fx = x - xx;
            double fy = y - yy;
            // Get four samples from the background image in a 2x2 kernel
            int c1 = image.getRGB((xx  ) % width, (yy  ) % height);
            int c2 = image.getRGB((xx+1) % width, (yy  ) % height);
            int c3 = image.getRGB((xx  ) % width, (yy+1) % height);
            int c4 = image.getRGB((xx+1) % width, (yy+1) % height);
            // Interpolate the two upper colors. Fractional part is the interpolation weight
            Pigment p1 = new Pigment(c1).mul(1-fx).add(new Pigment(c2).mul(fx));
            // Interpolate the two lower colors
            Pigment p2 = new Pigment(c3).mul(1-fx).add(new Pigment(c4).mul(fx));
            // Interpolate the two interpolated colors
            Pigment p0  = p1.mul(1-fy).add(p2.mul(fy));
            return p0;

            // Binary interpolation reference :
            // http://supercomputingblog.com/graphics/coding-bilinear-interpolation/
        }
    }
}