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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.team2xh.crt.gui.util.GUIToolkit;
import static net.team2xh.crt.raytracer.math.PoissonDiskDistributions.cR;
import static net.team2xh.crt.raytracer.math.PoissonDiskDistributions.randomCirclePoints;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class TestPoisson {
    public static void main(String[] args) {

        JFrame f = new JFrame("Bokeh tester");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
                long start = System.currentTimeMillis();

                List<double[]> points = randomCirclePoints(100);
                System.out.println(points.size());

                int rr = (int)(cR * w / 2.0);
                int rr2 = rr/2;
                g.drawOval(o - rr2, 0 - rr2, w + rr, w + rr);
                g.setColor(Color.BLACK);
                for (double[] p : points) {
                    g.drawOval(o+(int)(p[0]*(w/2) - rr2 + (w/2)), (int)(p[1]*(w/2) - rr2 + (w/2)),rr,rr);

                }
                long time = System.currentTimeMillis() - start;
                g.drawString(time + "ms", 0, 10);
            }
        };

        f.add(t);
        f.pack();
        GUIToolkit.centerFrame(f);
        f.setVisible(true);
    }
}
