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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.team2xh.crt.gui.util.GUIToolkit;
import static net.team2xh.crt.raytracer.math.UniformDistributions.randomPolygonPoint;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class TestDistributions {

    public static void main(String[] args) {
        JFrame f = new JFrame("Bokeh tester");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIToolkit.centerFrame(f);

        JPanel t = new JPanel() {
            @Override
            public Dimension getPreferredSize() { return new Dimension(450, 450); }
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = Math.min(getWidth(), getHeight());
                int o = (getWidth() / 2) - (w / 2);
                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.WHITE);
                g.fillRect(o, 0, w, w);
                g.setColor(Color.GRAY);
                g.drawOval(o, 0, w, w);
                g.setColor(Color.BLACK);
                long start = System.currentTimeMillis();
                for (int i = 0; i < 8000; ++i) {
                    double[] p = randomPolygonPoint(6);
                    // double[] p = randomCirclePoint();
                    g.fillOval(o+(int)(p[0]*(w/2) - 2 + (w/2)), (int)(p[1]*(w/2) - 2 + (w/2)), 4, 4);
                }
                long time = System.currentTimeMillis() - start;
                g.drawString(time + "ms", 0, 10);
            }
        };

        f.add(t);
        f.pack();
        f.setVisible(true);
    }

}

