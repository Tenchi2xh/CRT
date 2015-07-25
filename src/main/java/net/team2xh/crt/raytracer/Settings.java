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
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public final class Settings {

    Scene parent;

    double gamma = 1.0;
    int width, height;
    double ratio;
    double fovFactor;
    int supersampling = 1;
    int dofSamples = 1;
    int aoSamples = 16;
    int recurDepth = 3;
    boolean showClip = false;
    Projection projection = Projection.PINHOLE;

    private String title;
    private String author;
    private String date;
    private String notes;

    private Settings(Scene parent, int width, int height) {
        this.parent = parent;
        setResolution(width, height);
    }

    public static Settings createSettings(Scene parent, int width, int height) {
        return new Settings(parent, width, height);
    }

    // Must manually set parent, then call setResolution
    public Settings() { }

    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        ratio = 1.0*width / height;
        updateFov();
    }

    public void updateFov() {
        fovFactor = Math.tan(parent.getCamera().getVerticalFov() / 2);
    }

    public void setRecursionDepth(int depth) {
        this.recurDepth = depth;
    }

    public void setSupersampling(int n) {
        this.supersampling = n;
    }

    public void setDOFSamples(int n) {
        this.dofSamples = n;
    }

    public void showClip(boolean b) {
        this.showClip = b;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public void setParent(Scene scene) {
        this.parent = scene;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getDOFSamples() {
        return dofSamples;
    }

    public int getSupersampling() {
        return supersampling;
    }

    // TODO: own class with methods
    public enum Projection {
        PINHOLE("Pinhole"),
        SPHERICAL("Spherical"),
        CYLINDRICAL("Cylindrical");

        String definition;

        Projection(String definition) {
            this.definition = definition;
        }

        @Override
        public String toString() {
            return definition;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}
