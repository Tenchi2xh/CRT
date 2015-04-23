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
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.ExtendedPrimitives;
import java.awt.Canvas;
import java.io.InputStream;
import java.util.logging.Level;
import javax.swing.JFrame;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;

/**
 *
 * @author Tenchi
 */
public class Test {

    private World world;
    private FrameBuffer buffer;
    private JFrame frame;

    private Scene scene = new TestScene();

    public Test() {
        frame = new JFrame("Hello world");
        frame.setSize(scene.getSettings().getWidth(), scene.getSettings().getHeight());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIToolkit.centerFrame(frame);

        world = new World();
        world.setAmbientLight(0, 0, 0);
        InputStream unknown = getClass().getResourceAsStream("/resources/images/unknown.png");
        TextureManager.getInstance().addTexture("unknown", new Texture(unknown, true));

        for (Entity e : scene.getEntities()) {
            Object3D obj;
            Class type = e.getClass();

            if (type == Box.class) {
                Box box = (Box) e;
                SimpleVector dimensions = SimpleVector.create((float) box.getWidth(),
                        (float) box.getHeight(),
                        (float) box.getDepth());
                //obj.rotateY(0.01f);
                obj = ExtendedPrimitives.createBox(dimensions);
            } else if (type == Sphere.class) {
                Sphere sphere = (Sphere) e;
                obj = Primitives.getSphere((float) sphere.getRadius());
            } else {
                obj = ExtendedPrimitives.createSprite(0.2f);
                obj.setTexture("unknown");
                obj.setTransparency(20);
            }

            obj.translate(e.getCenter().simpleVector());

            obj.setAdditionalColor(e.getMaterial().color.getColor());
            obj.build();
            world.addObject(obj);
        }

        world.getCamera().setPosition(scene.getCamera().getPosition().simpleVector());
        world.getCamera().lookAt(scene.getCamera().getPointing().simpleVector());

    }

    private void loop() throws InterruptedException {
        buffer = new FrameBuffer(scene.getSettings().getWidth(), scene.getSettings().getHeight(), FrameBuffer.SAMPLINGMODE_NORMAL);
        Canvas canvas = buffer.enableGLCanvasRenderer();
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        frame.add(canvas);

        while (frame.isShowing()) {
            buffer.clear(java.awt.Color.GRAY);
            world.renderScene(buffer);
            world.draw(buffer);
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
