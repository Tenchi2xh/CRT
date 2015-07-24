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
package net.team2xh.crt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Tracer;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
 public class RenderPanel extends JPanel {

    private final static BufferedImage pattern;
    private final static Rectangle rectangle;
    private final static TexturePaint texture;
    private final static int W = 20;

    static {
        pattern = new BufferedImage(W, W, BufferedImage.TYPE_INT_RGB);
        rectangle = new Rectangle(W, W);

        Graphics g = pattern.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, W, W);
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, W / 2, W / 2);
        g.fillRect(W / 2, W / 2, W / 2, W / 2);
        g.dispose();

        texture = new TexturePaint(pattern, rectangle);
    }

    private BufferedImage bi;
    private final JLabel image = new JLabel() {
        {
            setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g) {
            if (bi != null) {
                g.drawImage(bi, 0, 0, null);
            }
        }
    };

    public RenderPanel() {
        setLayout(new BorderLayout());
        add(image, BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(texture);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    

    public static void renderScene(Scene scene, Runnable endAction, RenderPanel panel) {
        scene.getSettings().setResolution(panel.getWidth(), panel.getHeight());
        
        int w = scene.getSettings().getWidth();
        int h = scene.getSettings().getHeight();

        panel.image.setPreferredSize(new Dimension(w, h));
        panel.bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        scene.getSettings().updateFov();
        
        Tracer tracer = Tracer.getInstance();
        
        tracer.parallelRender(4, (int[][] p, Integer i) -> draw(panel, p, i, w, h), endAction, scene);
    }

    public static void draw(RenderPanel panel, int[][] picture, int pass, int w, int h) {
        int step = (int) Math.pow(2, pass);
        for (int x = 0; x < w - (w % step); x += step) {
            for (int y = 0; y < h - (h % step); y += step) {
                if (pass == 0) {
                    panel.bi.setRGB(x, y, picture[x][y]);
                } else {
                    for (int i = 0; i < step; ++i) {
                        for (int j = 0; j < step; ++j) {
                            panel.bi.setRGB(x + i, y + j, picture[x][y]);
                        }
                    }
                }
            }
        }
        panel.image.repaint();
    }
    
    public BufferedImage getImage() {
        return bi;
    }
}
