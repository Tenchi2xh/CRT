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
package net.team2xh.crt.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.team2xh.crt.gui.entities.EntityTree;
import net.team2xh.crt.gui.themes.DarkTheme;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.lights.ParallelLight;
import net.team2xh.crt.raytracer.lights.PointLight;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class TestEntityTree {

    public static void main(String[] args) {
        Theme theme = new DarkTheme();

        GUIToolkit.initGUI(theme, theme.LAF);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Entity properties test");

            GUIToolkit.enableHeapStatus(frame);
        
            Sphere s = new Sphere(Vector3.X, 0.15, new Material(new Pigment(0.9, 0.5, 0.0)));
            Box b = new Box(Vector3.X, Vector3.Y, new Material(new Pigment(0.3, 0.5, 0.8)));
            Plane p = new Plane(Vector3.X, Vector3.X, new Material(new Pigment(0.4, 0.8, 0.3)));

            PointLight l1 = new PointLight(new Vector3(0.3, 0.3, 0), new Pigment(0.75, 0.2, 0.2));
            PointLight l2 = new PointLight(new Vector3(-0.3, 0.3, 0), new Pigment(0.2, 0.2, 0.75));
            ParallelLight l3 = new ParallelLight(new Vector3(-0.5, 1, -0.5), new Vector3(0, 0, 0), new Pigment(0.8));

            l1.setAmbient(0.1);
            l2.setAmbient(0.1);
            l1.setFalloff(1.7);
            l2.setFalloff(1.7);

            Scene sc = new Scene();

            sc.add(s);
            sc.add(b);
            sc.add(p);

            sc.addLight(l1);
            sc.addLight(l2);
            sc.addLight(l3);

            EntityTree et = new EntityTree(sc);

            frame.getContentPane().add(et, BorderLayout.CENTER);
            frame.setPreferredSize(new Dimension(400, 700));
            frame.pack();

            GUIToolkit.centerFrame(frame);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//            new Thread(() -> {
//                for (;;) {
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println(s.getCenter());
//                    } catch (InterruptedException ex) {
//                        Exceptions.printStackTrace(ex);
//                    }
//                }
//            }).start();
        });
    }
}
