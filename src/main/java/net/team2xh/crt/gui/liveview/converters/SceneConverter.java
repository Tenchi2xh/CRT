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
package net.team2xh.crt.gui.liveview.converters;

import com.threed.jpct.Camera;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Projector;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.ExtendedPrimitives;
import com.threed.jpct.util.Light;
import com.threed.jpct.util.ShadowHelper;
import java.awt.Color;
import java.io.InputStream;
import net.team2xh.crt.gui.liveview.LiveViewPanel;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.lights.ParallelLight;
import net.team2xh.crt.raytracer.lights.PointLight;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
final public class SceneConverter {

    private SceneConverter() {
    }

    public static World convertScene(Scene scene, LiveViewPanel p) {

        Class c = p.getClass();
        
        World world = new World();
        world.setAmbientLight(0, 0, 0);

        InputStream unknown = c.getResourceAsStream("/images/unknown.png");
        TextureManager.getInstance().addTexture("unknown", new Texture(unknown, true));

        String vertex = Loader.loadTextFile(c.getResourceAsStream("shaders/vertex.glsl"));
        String fragment = Loader.loadTextFile(c.getResourceAsStream("shaders/fragment.glsl"));

        for (net.team2xh.crt.raytracer.lights.Light l0 : scene.getLights()) {
            Light l1 = new Light(world);
            Class type = l0.getClass();

            if (type == PointLight.class) {
                PointLight pl = (PointLight) l0;
                l1.setPosition(pl.getOrigin().multiply(p.distMult).getRightHanded().simpleVector());

            } else if (type == ParallelLight.class) {
                ParallelLight pl = (ParallelLight) l0;
                SimpleVector pos = pl.getDirection(Vector3.O).multiply(20 * p.distMult).getRightHanded().simpleVector();
                l1.setPosition(pos);
                // Shadows buggy with multiple viewports
//                if (sun == null) {
//                    sun = pos;
//                    System.out.println(pos);
//                }
            }

            SimpleVector intensity = l0.getPigment().getVector().simpleVector();
            intensity.scalarMul(255f);
            l1.setIntensity(intensity);
            l1.setAttenuation((float) l0.getFalloff() * p.distMult);

            if (type == ParallelLight.class) {
                intensity = l0.getPigment().getVector().simpleVector();
                intensity.scalarMul(255f);
                l1.setIntensity(intensity);
            }
        }

        p.camOrigPos = scene.getCamera().getPosition().multiply(p.distMult).getRightHanded().simpleVector();
        p.camLookAt = scene.getCamera().getPointing().multiply(p.distMult).getRightHanded().simpleVector();
        world.getCamera().setPosition(p.camOrigPos);
        world.getCamera().lookAt(p.camLookAt);
        world.getCamera().rotateCameraAxis(world.getCamera().getDirection(), (float) -scene.getCamera().getRoll());
        
        float vfov = (float) scene.getCamera().getVerticalFov();
        float hfov = 2 * (float) Math.atan(Math.tan(vfov / 2) * scene.getSettings().getWidth() / scene.getSettings().getHeight());
        
        world.getCamera().setFOVLimits(0, (float) (2 * Math.PI));
        world.getCamera().setFOV(2f * (float) Math.tan(hfov / 2));
        
        p.cameraY = new Camera();
        p.cameraZ = new Camera();
        p.cameraY.setPosition(0, -5 * p.distMult, 0);
        p.cameraZ.setPosition(0, 0, -5 * p.distMult);
        p.cameraY.lookAt(SimpleVector.ORIGIN);
        p.cameraZ.lookAt(SimpleVector.ORIGIN);

        if (p.sun != null) {

            p.projector = new Projector();
            p.projector.setFOV(1.5f);
            p.projector.setYFOV(1.5f);

            for (int i = 0; i < 3; ++i) {
                ShadowHelper sh = new ShadowHelper(world, p.buffers[i].getBuffer(), p.projector, 4096);

                sh.setCullingMode(false);
                sh.setAmbientLight(new Color(0, 0, 0));
                sh.setLightMode(true);
                sh.setBorder(1);

                p.shs[i] = sh;
            }
        }

        for (Entity e : scene.getEntities()) {
            Object3D obj;
            Class type = e.getClass();

            boolean sprite = false;

            if (type == Box.class) {
                Box box = (Box) e;
                SimpleVector dimensions = SimpleVector.create((float) box.getWidth() * p.distMult,
                        (float) box.getHeight() * p.distMult,
                        (float) box.getDepth() * p.distMult);
                obj = ExtendedPrimitives.createBox(dimensions);
                obj.translate(box.getMinCorner().multiply(p.distMult).getRightHanded().simpleVector());
//                obj.setShadingMode(Object3D.SHADING_FAKED_FLAT);
            } else if (type == Sphere.class) {
                Sphere sphere = (Sphere) e;
                obj = Primitives.getSphere((float) sphere.getRadius() * p.distMult);
            } else if (type == Plane.class) {
                obj = ExtendedPrimitives.createPlane(p.distMult, 100);
                // From http://stackoverflow.com/a/13199273
                SimpleVector n0 = SimpleVector.create(0.0f, 1.0f, 0.0f);
                SimpleVector n1 = ((Plane) e).getNormal().getRightHanded().simpleVector();
                if (n1.equals(SimpleVector.create(0.0f, 1.0f, 0.0f))) {
                    obj.rotateX((float) Math.PI);
                } else if (!n1.equals(SimpleVector.create(0.0f, -1.0f, 0.0f))) {
                    SimpleVector axis = n0.calcCross(n1);
                    float angle = (float) Math.acos(n0.calcDot(n1));
                    obj.rotateAxis(axis, angle);
                }
            } else {
                obj = ExtendedPrimitives.createSprite(0.2f * p.distMult);
                obj.setTexture("unknown");
                obj.setTransparency(20);
                sprite = true;
            }

            if (p.sun != null && !sprite) {
                for (int i = 0; i < 3; ++i) {
                    p.shs[i].addCaster(obj);
                    p.shs[i].addReceiver(obj);
                }
            }

            obj.translate(e.getCenter().multiply(p.distMult).getRightHanded().simpleVector());
            obj.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);

            Material m = e.getMaterial();

            obj.setSpecularLighting(true);
            obj.setAdditionalColor(m.color.getColor().darker().darker().darker());

            GLSLShader shader = new GLSLShader(vertex, fragment);
            shader.setUniform("isHighlighted", 0);
            shader.setUniform("matColor", m.color.getColor().getColorComponents(null));
            obj.setUserObject(shader);
            if (p.useShaders) {
                obj.setRenderHook(shader);
            }

            // Compile has to be dynamic or else it crashes when having multiple viewports
            obj.compile(true);
            obj.build();

            world.addObject(obj);
        }

        if (p.sun != null) {
            p.projector.setPosition(p.sun);
            p.projector.lookAt(SimpleVector.ORIGIN);

            for (int i = 0; i < 3; ++i) {
                p.shs[i].updateShadowMap();
            }
        }

        p.skybox = BackgroundConverter.convertBackground(scene.getBackground());

        return world;
    }
}
