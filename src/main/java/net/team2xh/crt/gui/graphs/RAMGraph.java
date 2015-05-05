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
package net.team2xh.crt.gui.graphs;

import javax.swing.*;
import java.awt.*;
import net.team2xh.crt.gui.themes.Theme;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */

public class RAMGraph extends JLabel {

    final private static int HISTOGRAM_SIZE = 35;
    final private Font font = new Font("Envy Code R", Font.PLAIN, 12);
    final private Color DARKRED  = Theme.mix(Theme.getTheme().COLOR_10, Theme.getTheme().COLOR_04, 0.25);
    final private Color DARKBLUE = Theme.mix(Theme.getTheme().COLOR_09, Theme.getTheme().COLOR_04, 0.25);
    final private Color DARKGREEN = Theme.mix(Theme.getTheme().COLOR_07, Theme.getTheme().COLOR_04, 0.25);

    private int position = HISTOGRAM_SIZE - 1;
    private double[] system = new double[HISTOGRAM_SIZE];
    private double[] heap = new double[HISTOGRAM_SIZE];
    private double[] used = new double[HISTOGRAM_SIZE];

    private long totalRam;

    public RAMGraph(long totalRam) {
        this.totalRam = totalRam;
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        FontMetrics fm = g.getFontMetrics(font);
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth() - 1;
        int h = getHeight() - 1 - 15;
        int cw = fm.charWidth(' ');
        int m = cw*7; // Left margin for legends
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);

        int[][] sysPoly = generatePoly(w, h, m, system);
        int[][] heapPoly = generatePoly(w, h, m, heap);
        int[][] usedPoly = generatePoly(w, h, m, used);

        // Legends
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawString(String.format("%3.1fGB", (totalRam/(1024*1024*1024.0))), 0, 9);
        g2d.drawString(String.format("%3.1fGB", (totalRam/(2*1024*1024*1024.0))), 0, h/2 + 5);
        g2d.drawString("0.0GB", 0, h);
        // Middle axis is behind
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawLine(m, h/2, w, h/2);
        // Histogram backgrounds
        g2d.setColor(Theme.mix(Theme.getTheme().COLOR_10, Theme.getTheme().COLOR_04, 0.25));
        g2d.fillPolygon(sysPoly[0], sysPoly[1], HISTOGRAM_SIZE + 2);
        g2d.setColor(Theme.mix(Theme.getTheme().COLOR_09, Theme.getTheme().COLOR_04, 0.25));
        g2d.fillPolygon(heapPoly[0], heapPoly[1], HISTOGRAM_SIZE + 2);
        g2d.setColor(Theme.mix(Theme.getTheme().COLOR_07, Theme.getTheme().COLOR_04, 0.25));
        g2d.fillPolygon(usedPoly[0], usedPoly[1], HISTOGRAM_SIZE + 2);
        // System cpu histogram
        g2d.setColor(Theme.getTheme().COLOR_10);
        g2d.drawPolyline(sysPoly[0], sysPoly[1], HISTOGRAM_SIZE);
        g2d.setColor(Theme.getTheme().COLOR_09);
        g2d.drawPolyline(heapPoly[0], heapPoly[1], HISTOGRAM_SIZE);
        g2d.setColor(Theme.getTheme().COLOR_07);
        g2d.drawPolyline(usedPoly[0], usedPoly[1], HISTOGRAM_SIZE);
        // Axis
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawLine(m, 0, m, h);
        g2d.drawLine(m, h, w, h);
        // Legends
        // XX RAM load YY JVM heap ZZ CRT load -> 35*cw
        g2d.setColor(DARKRED);
        g2d.fillRect(w-(cw*35), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_10);
        g2d.drawRect(w-(cw*35), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawString("RAM load", w-(cw*32), h+15);
        g2d.setColor(DARKBLUE);
        g2d.fillRect(w-(cw*23), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_09);
        g2d.drawRect(w-(cw*23), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawString("JVM heap", w-(cw*20), h+15);
        g2d.setColor(DARKGREEN);
        g2d.fillRect(w-(cw*11), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_07);
        g2d.drawRect(w-(cw*11), h+5, cw*2, 10);
        g2d.setColor(Theme.getTheme().COLOR_11);
        g2d.drawString("CRT load", w-(cw*8), h+15);
    }

    private int[][] generatePoly(int w, int h, int m, double[] histogram) {
        double wstep = (1.0*w-m) / (HISTOGRAM_SIZE - 1);
        int[][] polygon = new int[2][HISTOGRAM_SIZE + 2];
        for (int i = 0; i < HISTOGRAM_SIZE; ++i) {
            polygon[0][i] = m + (int)(wstep*i);                                // xs
            polygon[1][i] = h - (int)(h*histogram[(position+i)%HISTOGRAM_SIZE]);  // ys
        }
        // Polygon end points
        polygon[0][HISTOGRAM_SIZE] = m + (int)(wstep*(HISTOGRAM_SIZE - 1));
        polygon[1][HISTOGRAM_SIZE] = h;
        polygon[0][HISTOGRAM_SIZE + 1] = m;
        polygon[1][HISTOGRAM_SIZE + 1] = h;
        return polygon;
    }

    public void update(long u, long h, long s) {
        used[position] = 1.0*u / totalRam;
        heap[position]  = 1.0*h / totalRam;
        system[position]  = 1.0*s / totalRam;
        position = (position + 1) % HISTOGRAM_SIZE;
        repaint();
    }
}