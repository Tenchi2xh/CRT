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

import java.awt.Color;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 * Class for representing colors. Supports several formats
 * and provides multiple methods and utilitary static methods.
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Pigment {

    // Red, Green, Blue and Alpha components.
    public double r, g, b, a;

    /**
     * Standard constructor.
     * Takes the R, G and B components and defaults the alpha to 1.0.
     *
     * @param  r Red component.
     * @param  g Green component.
     * @param  b Blue component.
     */
    public Pigment(double r, double g, double b) {
        init(r, g, b, 1.0);
    }

    /**
     * RGBA constructor.
     * Takes the R, G, B and A components.
     *
     * @param  r Red component.
     * @param  g Green component.
     * @param  b Blue component.
     * @param  a Alpha component.
     */
    public Pigment(double r, double g, double b, double a) {
        init(r, g, b, a);
    }

    /**
     * Gray constructor.
     * Constructs a gray Pigment with an alpha component of 1.0.
     *
     * @param  i Intensity of the gray.
     */
    public Pigment(double i) {
        init (i, i, i, 1.0);
    }

    /**
     * Java Color constructor.
     * Initializes the Pigment using a java.awt.Color object.
     *
     * @param  color Color to copy.
     */
    public Pigment(Color color) {
        init(color);
    }

    /**
     * Hex format constructor.
     * Initializes the Pigment by parsing a hex formatted String.
     * <p>
     * Format: "#RRGGBB"
     *
     * @param  hex Hex formatted String.
     */
    public Pigment(String hex) {
        Color color = Color.decode(hex);
        init(color);
    }

    /**
     * RGB int constructor.
     * Initializes the Pigment by deconstructing a
     * RGB formatted integer.
     * <p>
     * Format: 0xRRGGBB
     *
     * @param  i RGB formatted integer
     */
    public Pigment(int i) {
        r = ((i>>16)&0xFF) / 255.0;
        g = ((i>>8)&0xFF) / 255.0;
        b = (i&0xFF) / 255.0;
    }

    /**
     * Helper method for initializing colors
     *
     * @param r Red component.
     * @param g Green component.
     * @param b Blue component.
     * @param a Alpha component.
     */
    private void init(double r, double g, double b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Helper method for initializing java Colors.
     *
     * @param color Color to copy.
     */
    private void init(Color color) {
        float[] c = new float[3];
        color.getRGBColorComponents(c); // Writes to c
        init(c[0], c[1], c[2], 1.0);
    }

    /**
     * Adds the given pigment to the current pigment
     *
     * @param other Pigment to add.
     */
    public void addSelf(Pigment other) {
        r = r+other.r;
        g = g+other.g;
        b = b+other.b;
    }

    /**
     * Returns a new Pigment from the sum of
     * the current and another Pigment.
     * Does not modify the current Pigment.
     *
     * @param  other Pigment to add.
     * @return       Blended Pigment.
     */
    public Pigment add(Pigment other) {
        return new Pigment(r+other.r, g+other.g, b+other.b);
    }

    /**
     * Returns a shade of the current Pigment.
     * Does not modify the current Pigment.
     *
     * @param  f Intensity of desired Pigment.
     * @return   Shaded Pigment.
     */
    public Pigment mul(double f) {
        return new Pigment(r*f, g*f, b*f);
    }

    /**
     * Returns a blend of the current and another Pigment.
     * Does not modify the current Pigment.
     *
     * @param  other Pigment to be blended with.
     * @return       Blended Pigment.
     */
    public Pigment mul(Pigment other) {
        return new Pigment(r*other.r, g*other.g, b*other.b);
    }

    /**
     * Returns a RGB formatted integer of the current Pigment.
     *
     * @param  showClip if activated, clipping will be displayed as red
     * @return RGB      formatted integer.
     */
    public int rgb(boolean showClip) {
        int rr = (int)(r * 255);
        int gg = (int)(g * 255);
        int bb = (int)(b * 255);
        if ((rr > 255 || gg > 255 ||  bb > 255)) {
            if (showClip) {
                rr = 255; bb = gg = 0;
            } else {
                rr = rr > 255 ? 255 : rr;
                gg = gg > 255 ? 255 : gg;
                bb = bb > 255 ? 255 : bb;
            }
        }
        return (rr << 16) | (gg << 8) | bb;
    }

    /**
     * Utilitary static method for averaging a
     * two-dimensional array of pigments.
     *
     * @param  samples 2D Pigment array to average.
     * @return         Average color.
     */
    public static Pigment getAverage(Pigment[][] samples) {
        int l = samples.length;
        double f = 1.0 / (l*l);
        double r = 0, g = 0, b = 0;
        for (int i = 0; i < l; ++i) {
            for (int j = 0; j < l; ++j) {
                r += samples[i][j].r * f;
                g += samples[i][j].g * f;
                b += samples[i][j].b * f;
            }
        }
        return new Pigment(r, g, b);
    }

    /**
     * Utilitary static method for averaging a
     * one-dimensional array of pigments.
     *
     * @param  samples Pigment array to average.
     * @return         Average color.
     */
    public static Pigment getAverage(Pigment[] samples) {
        int l = samples.length;
        double f = 1.0 / l;
        double r = 0, g = 0, b = 0;
        for (int i = 0; i < l; ++i) {
            r += samples[i].r * f;
            g += samples[i].g * f;
            b += samples[i].b * f;
        }
        return new Pigment(r, g, b);
    }

    /**
     * Returns a java.awt.Color instance of the current pigment.
     *
     * @return Color
     */
    public Color getColor() {
        return new Color((float) r, (float) g, (float) b, (float) a);
    }

    /**
     * Returns the hue of the current pigment.
     *
     * @return Hue of pigment
     */
    public float getHue() {
        return Color.RGBtoHSB((int)(r*255), (int)(g*255),(int)(b*255), null)[0];
    }

    /**
     * Returns a vector represenation of the current pigment.
     * 
     * @return Vector represenation of the current pigment
     */
    public Vector3 getVector() {
        return new Vector3(r, g, b);
    }
    
    @Override
    public String toString() {
        return String.format("#%02x%02x%02x %d%%", (int)(r*255), (int)(g*255), (int)(b*255), (int)(a*100));
    }

}