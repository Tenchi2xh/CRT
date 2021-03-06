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

import net.team2xh.crt.raytracer.lights.Light;
import java.util.ArrayList;
import java.util.List;
import net.team2xh.crt.raytracer.entities.Entity;

/**
 * Represents a scene that can be traced.
 * 
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Scene {

    private final List<Entity> entities = new ArrayList<>(); // List of entities in the scene
    private final List<Light> lights = new ArrayList<>();    // List of lights lighting the scene
    
    private Settings settings;
    
    private Background background = new Background();
    private Camera camera;

    // Must set settings correctly, and assign a camera
    public Scene() { }

    public static Scene createScene(int width, int height, Camera camera) {
        Scene s = new Scene();
        s.camera = camera;
        s.settings = Settings.createSettings(s, width, height);
        return s;
    }

    public void add(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        if (settings != null) settings.updateFov();
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public List<Light> getLights() {
        return lights;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    
    @Override
    public String toString() {
        return String.format("%d entities, %d lights", entities.size(), lights.size());
    }

}
