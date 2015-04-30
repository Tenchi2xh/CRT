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
import net.team2xh.crt.gui.entities.EntityProperties;
import net.team2xh.crt.gui.themes.DarkTheme;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Pigment;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class TestEntityProperties {

    public static void main(String[] args) {

        Theme theme = new DarkTheme();

        GUIToolkit.initGUI(theme, theme.LAF);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Entity properties test");

            EntityProperties ep = new EntityProperties(theme);
            ep.viewProperties(new Sphere(Vector3.X, 0.15, new Material(new Pigment(0.1))));
            
            frame.getContentPane().add(ep, BorderLayout.CENTER);
            frame.setPreferredSize(new Dimension(300, 500));
            frame.pack();

            GUIToolkit.centerFrame(frame);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

    }
}
