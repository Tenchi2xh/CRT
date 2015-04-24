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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.team2xh.crt.raytracer.Background;
import net.team2xh.crt.raytracer.Camera;
import net.team2xh.crt.raytracer.lights.Light;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Settings;
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.entities.csg.*;
import net.team2xh.crt.raytracer.lights.ParallelLight;
import net.team2xh.crt.raytracer.lights.PointLight;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class TestTracer {

    public BufferedImage bi;
    public int w, h;
    public JLabel image;

    private boolean show = true;
    
    final private Object sync = new Object();

    public TestTracer() {
        
        w = 1280;
        h = 720;
        
//        for (int t = 0; t < 60; ++t) {
//            double d = 0.9 + 0.001*t*t;
        double d = 1;
        Camera camera = new Camera(new Vector3(d * 0.6, d * 0.05, d * -0.9), new Vector3(0.0, 0.0, 0.0), 70 / d);
//        Camera camera = new Camera(new Vector3(d * -0.3, d * 0.55, d * -1), new Vector3(0.0, 0.0, 0.0), 40 / d);
//        Camera camera = new Camera(new Vector3(d * 1.4, d * 1.45, d * -1.9), new Vector3(0.0, 0.0, 0.0), 40 / d);

        Scene scene = Scene.createScene(w, h, camera);

        Light lightR = new PointLight(new Vector3(0.3, 0.3, 0), new Pigment(0.75, 0.2, 0.2));
        Light lightB = new PointLight(new Vector3(-0.3, 0.3, 0), new Pigment(0.2, 0.2, 0.75));
        Light lightF1 = new PointLight(new Vector3(-0.3, 0.3, -0.8), new Pigment(0.75, 0.0, 0.75));
        Light lightF2 = new PointLight(new Vector3(0.3, 0.3, -0.8), new Pigment(0.75, 0.75, 0.0));
        Light sun = new ParallelLight(new Vector3(-0.5, 1, -0.5), new Vector3(0, 0, 0), new Pigment(0.8));
        Light center = new PointLight(Vector3.O, new Pigment(0.75, 0.2, 0.2));

        lightR.setAmbient(0.1);
        lightB.setAmbient(0.1);
        lightR.setFalloff(1.7);
        lightB.setFalloff(1.7);
        lightF1.setFalloff(1.5);
        lightF2.setFalloff(1.5);
        center.setFalloff(10.5);
        sun.setAmbient(0.35);

        boolean day = true;
        double b = 1.0;

        if (day) {
            scene.addLight(sun);
        } else {
            scene.addLight(lightR);
            scene.addLight(lightB);
            scene.addLight(lightF1);
            scene.addLight(lightF2);
            scene.addLight(center);
            b = 0.2;
        }

//        scene.setBackground(new Background(new Pigment(b * 147 / 255., b * 195 / 255., b * 209 / 255.), new Pigment(0, b * 88 / 255., b * 151 / 255.)));
        scene.setBackground(new Background("/resources/images/panorama/italy.jpg", 0.0));

        scene.getSettings().setRecursionDepth(2);
        scene.getSettings().setProjection(Settings.Projection.PINHOLE);

        Material gridMat = new Material(new Pigment(1, 0, 0), 0);
        boolean grid = false;

        if (grid) {
            for (int x = -10; x <= 10; ++x) {
                scene.add(new Box(new Vector3(x - 0.01, -0.01, -10), new Vector3(x + 0.01, 0.01, 10), gridMat));
                scene.add(new Box(new Vector3(-10, -0.01, x - 0.01), new Vector3(10, 0.01, x + 0.01), gridMat));
            }
            for (int x = -10; x <= 10; ++x) {
                scene.add(new Box(new Vector3(x - 0.01, -10, 0 - 0.01), new Vector3(x + 0.01, 10, 0.01), gridMat));
                scene.add(new Box(new Vector3(-10, x - 0.01, 0 - 0.01), new Vector3(10, x + 0.01, 0.01), gridMat));
            }
        }

        Material sphereMat1 = new Material(new Pigment(0.2, 0.2, 0.2), 0.3);
        sphereMat1.setSpecular(4.0);
        sphereMat1.setShininess(50.0);
        Material sphereMat2 = new Material(new Pigment(0.2, 0.9, 0.2), 0.3);
        sphereMat2.setSpecular(4.0);
        sphereMat2.setShininess(50.0);
        Material sphereMat3 = new Material(new Pigment(0.2, 0.2, 0.9), 0.3);
        sphereMat3.setSpecular(4.0);
        sphereMat3.setShininess(50.0);
        
        Material[] sphereMats = new Material[] { sphereMat1, sphereMat2, sphereMat3 };

        Material dieMat = new Material(new Pigment(0.9), 0.1);
        dieMat.setSpecular(0.3);
        dieMat.setShininess(50.0);

        scene.add(new Plane(new Vector3(0, 1, 0), new Vector3(0.0, -0.3, 0.0), new Material(new Pigment(1.0))));

        scene.add(new Box(new Vector3(0.4, -.25, 1.0), new Vector3(0.6, 0.4, -0.1), sphereMat1));

        for (int i = -8; i < 15; ++i) {
            scene.add(new Sphere(new Vector3(-0.5, -0.125, 0.0 + 0.3 * i), 0.125, sphereMats[(i+30) % 3]));
        }

        Box box = new Box(new Vector3(-0.2, -0.2, -0.2), new Vector3(0.2, 0.2, 0.2), dieMat);
        Sphere sphere = new Sphere(new Vector3(0.0, 0.0, 0.0), 0.265, dieMat);

        Entity diceBody = new Difference(box, sphere);

        List<Entity> diceElements = new LinkedList<>();
        diceElements.add(diceBody);
        for (int i = 0; i < 3; ++i) {
            diceElements.add(new Sphere(new Vector3((1. / 2.5) * -0.2, (1. / 2) * -0.2 + i * (1. / 2) * 0.2, -0.2), 0.03, dieMat));
        }
        for (int i = 0; i < 3; ++i) {
            diceElements.add(new Sphere(new Vector3((1. / 2.5) * 0.2, (1. / 2) * -0.2 + i * (1. / 2) * 0.2, -0.2), 0.03, dieMat));
        }
        for (int i = 0; i < 2; ++i) {
            diceElements.add(new Sphere(new Vector3((1. / 2.5) * 0.2, 0.2, (1. / 2) * -0.2 + i * (1.) * 0.2), 0.03, dieMat));
        }
        for (int i = 0; i < 2; ++i) {
            diceElements.add(new Sphere(new Vector3((1. / 2.5) * -0.2, 0.2, (1. / 2) * -0.2 + i * (1.) * 0.2), 0.03, dieMat));
        }

        Entity dice = CSG.subtract(diceElements);
        scene.add(diceBody);

//        scene.add(new Sphere(new Vector3(0.0,  0.125, -0.3), 0.065, sphereMat));
//        scene.add(new Sphere(new Vector3(0.0, -0.125, -0.3), 0.065, sphereMat));
//
//        for (int i = 0; i < 50; ++i) {
//            double x = Math.cos(i / 3.0) * 0.2;
//            double y = 0.125 - (i * 0.005);
//            double z = Math.sin(i / 3.0) * 0.2 - 0.3;
//            scene.add(new Sphere(new Vector3(x, y, z), 0.01, sphereMat));
//        }
        scene.getSettings().setSupersampling(2);
//        scene.getSettings().setDOFSamples(64);
        camera.setAperture(50);
        camera.setFocalDistance(6.55);
        Tracer tracer = Tracer.getInstance();

        bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        image = new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
                //super.paintComponent(g);
                g.drawImage(bi, 0, 0, null);
            }
        };
        image.setPreferredSize(new Dimension(w, h));

        if (show) {
            JFrame f = new JFrame("CRT");
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(image, BorderLayout.CENTER);
            panel.add(new JLabel("OKADOKADS"), BorderLayout.PAGE_END);
            f.getContentPane().add(panel);
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        }
        
        tracer.parallelRender(4, (int[][] p, Integer i) -> draw(p, i, w, h), scene);
        
        try {
            synchronized (sync) {
                sync.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TestTracer.class.getName()).log(Level.SEVERE, null, ex);
        }
//        }
    }

    public void draw(int[][] picture, int pass, int w, int h) {
        // TODO: Compare with length of picture not w and h
        int x0 = w < image.getWidth() ? (image.getWidth() / 2) - (w / 2) : 0;
        int y0 = h < image.getHeight() ? (image.getHeight() / 2) - (h / 2) : 0;
        int step = (int) Math.pow(2, pass);
        for (int x = 0; x < w; x += step) {
            for (int y = 0; y < h; y += step) {
                if (pass == 0) {
                    bi.setRGB(x + x0, y + y0, picture[x][y]);
                } else {
                    for (int i = 0; i < step; ++i) {
                        for (int j = 0; j < step; ++j) {
                            bi.setRGB(x + i + x0, y + j + y0, picture[x][y]);
                        }
                    }
                }
            }
        }
        image.repaint();

        if (pass == 0) {
            try {
                ImageIO.write(bi, "PNG", new File("Z:\\New Pictures\\CRT\\" + (System.currentTimeMillis() / 1000) + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(TestTracer.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized (sync) {
                sync.notify();
            }
        }
    }

    public static void main(String[] args) {
        new TestTracer();
    }
}
