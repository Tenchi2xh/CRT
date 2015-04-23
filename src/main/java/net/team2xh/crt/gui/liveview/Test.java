/*
 * Copyright (C) 2015 Tenchi
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
package net.team2xh.crt.gui.liveview;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Matrix;
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
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.team2xh.crt.gui.util.GUIToolkit;
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
 * @author Tenchi
 */
public class Test {

    private World world;
    private FrameBuffer buffer;
    private ShadowHelper sh;
    private Projector projector;

    private JFrame frame;
    private Canvas canvas;

    SimpleVector sun = null;

    private Scene scene = new TestScene();

    public Test() {
        frame = new JFrame("Hello world");
        buffer = new FrameBuffer(scene.getSettings().getWidth(), scene.getSettings().getHeight(), FrameBuffer.SAMPLINGMODE_NORMAL);

        projector = new Projector();
        projector.setFOV(1.5f);
        projector.setYFOV(1.5f);

        canvas = buffer.enableGLCanvasRenderer();
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        frame.getContentPane().add(canvas, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton camUp = new JButton("^");
        JButton camDn = new JButton("v");
        JButton camLt = new JButton("<");
        JButton camRt = new JButton(">");
        camUp.addActionListener((ActionEvent e) -> {
            moveCamera(0, 1);
        });
        camDn.addActionListener((ActionEvent e) -> {
            moveCamera(0, -1);
        });
        camLt.addActionListener((ActionEvent e) -> {
            moveCamera(-1, 0);
        });
        camRt.addActionListener((ActionEvent e) -> {
            moveCamera(1, 0);
        });
        buttons.add(camUp);
        buttons.add(camDn);
        buttons.add(camLt);
        buttons.add(camRt);
        frame.add(buttons, BorderLayout.PAGE_END);
        
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIToolkit.centerFrame(frame);


        world = new World();
        world.setAmbientLight(0, 0, 0);
        InputStream unknown = getClass().getResourceAsStream("/resources/images/unknown.png");
        TextureManager.getInstance().addTexture("unknown", new Texture(unknown, true));

        sh = new ShadowHelper(world, buffer, projector, 2048);
        sh.setCullingMode(false);
        sh.setAmbientLight(new Color(30, 30, 30));
        sh.setLightMode(true);
        sh.setBorder(1);

        for (Entity e : scene.getEntities()) {
            Object3D obj;
            Class type = e.getClass();

            if (type == Box.class) {
                Box box = (Box) e;
                SimpleVector dimensions = SimpleVector.create((float) box.getWidth(),
                        (float) box.getHeight(),
                        (float) box.getDepth());
                obj = ExtendedPrimitives.createBox(dimensions);
                obj.translate(box.getMinCorner().getRightHanded().simpleVector());
                obj.setShadingMode(Object3D.SHADING_FAKED_FLAT);
            } else if (type == Sphere.class) {
                Sphere sphere = (Sphere) e;
                obj = Primitives.getSphere((float) sphere.getRadius());
            } else if (type == Plane.class) {
                obj = ExtendedPrimitives.createPlane(100);
            } else {
                obj = ExtendedPrimitives.createSprite(0.2f);
                obj.setTexture("unknown");
                obj.setTransparency(20);
            }

            sh.addCaster(obj);
            sh.addReceiver(obj);

            obj.translate(e.getCenter().getRightHanded().simpleVector());

            obj.setAdditionalColor(e.getMaterial().color.getColor());
            obj.build();
            world.addObject(obj);
        }

        for (net.team2xh.crt.raytracer.lights.Light l0 : scene.getLights()) {
            Light l1 = new Light(world);
            Class type = l0.getClass();

            if (type == PointLight.class) {
                PointLight pl = (PointLight) l0;
                l1.setPosition(pl.getOrigin().getRightHanded().invert().simpleVector());
            } else if (type == ParallelLight.class) {
                ParallelLight pl = (ParallelLight) l0;
                SimpleVector pos = pl.getDirection(Vector3.O).multiply(20).getRightHanded().invert().simpleVector();
                l1.setPosition(pos);
                if (sun == null) {
                    sun = pos;
                    System.out.println(pos);
                }
            }

            SimpleVector intensity = l0.getColor().getVector().simpleVector();
            intensity.scalarMul(255f * (float) l0.getFalloff());
            l1.setIntensity(intensity);
//            l1.setAttenuation((float) l0.getFalloff());
        }

        float vfov = (float) scene.getCamera().getVerticalFov();
        float hfov = 2 * (float) Math.atan(Math.tan(vfov / 2) * scene.getSettings().getWidth() / scene.getSettings().getHeight());

        world.getCamera().setFovAngle(hfov);
        world.getCamera().setPosition(scene.getCamera().getPosition().getRightHanded().simpleVector());
        world.getCamera().lookAt(scene.getCamera().getPointing().getRightHanded().simpleVector());

    }

    private void moveCamera(int x, int y) {
        world.getCamera().rotateCameraY(x * 0.05f);
//        Matrix rot = world.getCamera().getBack();

//        rot.rotateX(x * 0.1f);
//        rot.rotateY(y * 0.1f);

//        world.getCamera().setBack(rot.cloneMatrix());
    }

    private void loop() throws InterruptedException {

        while (frame.isShowing()) {

            if (sun != null) {
                projector.setPosition(sun);
                projector.lookAt(SimpleVector.ORIGIN);
                sh.updateShadowMap();
            }

            buffer.clear(java.awt.Color.GRAY);
            world.renderScene(buffer);
            world.draw(buffer);
            if (sun != null) {
                sh.drawScene();
            }
            buffer.update();
            buffer.displayGLOnly();
            canvas.repaint();
            Thread.sleep(10);
        }
        buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer.dispose();
    }

    public static void main(String[] args) {
        try {
            new Test().loop();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
