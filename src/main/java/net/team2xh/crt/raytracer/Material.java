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

/**
 * Class for describing entity materials
 * and how light will interact with them.
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Material {

    public Pigment color = new Pigment(0.5); // Base diffuse color

    public double reflectivity = 0.0; // How much light is reflected
    public double transparency = 0.0; // How much light is refracted
    public double refractIndex = 0.0; // Refractive index
    public double diffuse      = 1.0; // How much light bounces diffusely
    public double specular     = 0.0; // Specular highlight: NOT physically acurate
    public double shininess    = 3.0; // Shininess of highlight : NOT physicall acurate

    /**
     * Default constructor.
     * Creates a completely diffuse white material.
     */
    public Material() { }

    /**
     * Diffuse constructor.
     * Creates a completely diffuse material with the given color.
     *
     * @param  color Diffuse color.
     */
    public Material(Pigment color) {
        this.color = color;
    }

    /**
     * Metallic constructor.
     * Creates a material partially reflective and partially diffuse.
     *
     * @param  color        Diffuse color.
     * @param  reflectivity Percentage of reflectivity
     */
    public Material(Pigment color, double reflectivity) {
        this.color = color;
        this.reflectivity = reflectivity;
        this.diffuse = 1.0 - reflectivity;
    }

    /**
     * Glass constructor.
     * Creates a material partially reflective, partially refractive,
     * and partially diffuse.
     *
     * TODO: Both factors should be resolved with the Fresnel model
     *
     * @param  color        Diffuse color.
     * @param  reflectivity Percentage of reflectivity.
     * @param  transparency Percentage of transparency.
     * @param  refractIndex Refractive index
     */
    public Material(Pigment color, double reflectivity, double transparency, double refractIndex) {
        this.color = color;
        this.reflectivity = reflectivity;
        this.transparency = transparency;
        this.diffuse = 1.0 - reflectivity - transparency;
        this.refractIndex = refractIndex;
    }

    /**
     * Changes the specularity of the material.
     * Specularity is considered a hack and does not represent
     * how physical light works.
     *
     * @param specular Percentage of specularity.
     */
    public void setSpecular(double specular) {
        this.specular = specular;
    }

    /**
     * Changes the power of the specular highlight.
     * The bigger the number, the tiner / sharper will
     * the highlight edge be.
     *
     * @param shininess Power of shininess.
     */
    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

}