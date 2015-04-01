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

import java.util.ArrayList;
import java.util.List;
import net.team2xh.crt.raytracer.entities.Entity;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Scene {

    private List<Entity> entities = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();
    private Settings settings;
    private Background background = new Background();
    private Camera camera;

    private Scene() { }

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
        settings.updateFov();
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

}
