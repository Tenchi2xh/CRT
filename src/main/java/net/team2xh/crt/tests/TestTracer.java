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
package net.team2xh.crt.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.team2xh.crt.raytracer.Background;
import net.team2xh.crt.raytracer.Camera;
import net.team2xh.crt.raytracer.Light;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.entities.csg.*;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class TestTracer {

    public BufferedImage bi;
    public int w, h;
    public JLabel image;

    public TestTracer() {

        Camera camera = new Camera(new Vector3(0, 0.5, -1), new Vector3(0.0, 0.0, 0.0), 50);
        Scene scene = Scene.createScene(1280, 720, camera);

        Light lightR = new Light(new Vector3(0.3, 0.3, 0), new Pigment(0.75, 0.2, 0.2));
        Light lightB = new Light(new Vector3(-0.3, 0.3, 0), new Pigment(0.2, 0.2, 0.75));
        Light lightF1 = new Light(new Vector3(-0.3, 0.3, -0.8), new Pigment(0.75, 0.0, 0.75));
        Light lightF2 = new Light(new Vector3(0.3, 0.3, -0.8), new Pigment(0.75, 0.75, 0.0));
        Light sun = new Light(new Vector3(-600, 1000, -400), new Pigment(0.8));
        Light center = new Light(Vector3.O, new Pigment(0.75, 0.2, 0.2));

        lightR.setAmbient(0.1);
        lightB.setAmbient(0.1);
        lightR.setFalloff(1.7);
        lightB.setFalloff(1.7);
        lightF1.setFalloff(0.5);
        lightF2.setFalloff(0.5);
        center.setFalloff(10.5);
        sun.setAmbient(0.1);

//        scene.addLight(lightR);
//        scene.addLight(lightB);
//        scene.addLight(lightF1);
//        scene.addLight(lightF2);
        scene.addLight(sun);
//        scene.addLight(center);
        scene.setBackground(new Background(new Pigment(0,0,1), new Pigment(0,1,1)));

        scene.getSettings().setRecursionDepth(2);

        Material sphereMat = new Material(new Pigment(0.2), 0.3);
        sphereMat.setSpecular(1.0);
        sphereMat.setShininess(50.0);

        Material dieMat = new Material(new Pigment(0.6), 0.0);
        dieMat.setSpecular(1.0);
        dieMat.setShininess(50.0);

        scene.add(new Plane(new Vector3(0, 1, 0), new Vector3(0.0, -0.3, 0.0), new Material(new Pigment(1.0))));

        //scene.add(new Box(new Vector3(0.4, -.25, 1.0), new Vector3(0.6, 0.4, -0.1), sphereMat));

        for (int i = 0; i < 15; ++i) {
            //scene.add(new Sphere(new Vector3(-0.5, -0.125, 0.0 + 0.3*i), 0.125, sphereMat));
        }

        Box box = new Box(new Vector3(-0.2, -0.2, -0.2), new Vector3(0.2, 0.2, 0.2), dieMat);
        Sphere sphere = new Sphere(new Vector3(0.0, 0.0, 0.0), 0.275, sphereMat);

        Entity test = new Union(box, sphere);


        List<Entity> dice = new LinkedList<>();
        dice.add(test);
        for (int i = 0; i < 3; ++i) {
            dice.add(new Sphere(new Vector3((1./2.5)*-0.2, (1./2)*-0.2 + i*(1./2)*0.2, -0.2), 0.03, sphereMat));
        }
        for (int i = 0; i < 3; ++i) {
            dice.add(new Sphere(new Vector3((1./2.5)*0.2, (1./2)*-0.2 + i*(1./2)*0.2, -0.2), 0.03, sphereMat));
        }
        for (int i = 0; i < 2; ++i) {
            dice.add(new Sphere(new Vector3((1./2.5)*0.2, 0.2, (1./2)*-0.2 + i*(1.)*0.2), 0.03, sphereMat));
        }
        for (int i = 0; i < 2; ++i) {
            dice.add(new Sphere(new Vector3((1./2.5)*-0.2, 0.2, (1./2)*-0.2 + i*(1.)*0.2), 0.03, sphereMat));
        }

        //test = CSG.subtract(dice);

        scene.add(test);
//        scene.add(sphere3);

        //scene.add(new Plane(new Vector3(-1, 0, 0), new Vector3(0.25, 0, 0.0), new Material(new Pigment(1.0))));
//        scene.add(new Sphere(new Vector3(0.0,  0.125, -0.3), 0.065, sphereMat));
//        scene.add(new Sphere(new Vector3(0.0, -0.125, -0.3), 0.065, sphereMat));
        // Spiral
//        for (int i = 0; i < 50; ++i) {
//            double x = Math.cos(i / 3.0) * 0.2;
//            double y = 0.125 - (i * 0.005);
//            double z = Math.sin(i / 3.0) * 0.2 - 0.3;
//            scene.add(new Sphere(new Vector3(x, y, z), 0.01, sphereMat));
//        }

        scene.getSettings().setSupersampling(2);
        Tracer tracer = Tracer.getInstance();

        w = 1280;
        h = 720;

        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        image = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                //super.paintComponent(g);
                g.drawImage(bi, 0, 0, null);
            }
        };
        image.setPreferredSize(new Dimension(w, h));

        JFrame f = new JFrame("Tracer Test");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(image, BorderLayout.CENTER);
        panel.add(new JLabel("OKADOKADS"), BorderLayout.PAGE_END);
        f.getContentPane().add(panel);
        f.pack();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        ForkJoinPool pool = new ForkJoinPool(4);
        pool.execute(() -> tracer.render(3, (int[][] p, Integer i) -> draw(p, i, w, h), scene));

    }

    public void draw(int[][] picture, int pass, int w, int h) {
        int x0 = w < image.getWidth() ? (image.getWidth() / 2) - (w/2) : 0;
        int y0 = h < image.getHeight() ? (image.getHeight() / 2) - (h/2) : 0;
        int step = (int) Math.pow(2, pass);
        for (int x = 0; x < w; x += step) {
            for (int y = 0; y < h; y += step) {
                if (pass == 0)
                    bi.setRGB(x+x0, y+y0, picture[x][y]);
                else
                    for (int i = 0; i < step; ++i)
                        for (int j = 0; j < step; ++j)
                            bi.setRGB(x + i + x0, y + j + y0, picture[x][y]);
            }
        }
        image.repaint();
    }

    public static void main(String[] args) {
        new TestTracer();
    }
}
