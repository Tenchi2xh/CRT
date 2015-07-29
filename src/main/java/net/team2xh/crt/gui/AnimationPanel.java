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
package net.team2xh.crt.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import net.team2xh.crt.gui.editor.EditorTextPane;
import net.team2xh.crt.gui.util.AnimatedGifEncoder;
import net.team2xh.crt.gui.util.GifSequenceWriter;
import net.team2xh.crt.language.compiler.Compiler;
import net.team2xh.crt.language.compiler.Identifier;
import net.team2xh.crt.language.compiler.Script;
import net.team2xh.crt.language.compiler.Variable;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Tracer;
import org.openide.util.Exceptions;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class AnimationPanel extends JPanel {
    

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

    private BufferedImage[] frames = new BufferedImage[1];
    private int currentFrame = 0;
    private final JLabel image = new JLabel() {
        {
            setOpaque(false);
        }

        @Override
        public void paintComponent(Graphics g) {
            if (currentFrame < frames.length && frames[currentFrame] != null) {
                g.drawImage(frames[currentFrame], 0, 0, null);
            }
        }
    };

    private JSpinner spinner;
    private JSlider slider;
    private JButton render;
    
    public AnimationPanel(EditorTextPane editor) {
        setLayout(new BorderLayout());
        add(image, BorderLayout.CENTER);
        
        JPanel controls = new JPanel(new BorderLayout());
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        slider = new JSlider(JSlider.HORIZONTAL, 1, 1, 1);
        render = new JButton("Render");
        
        spinner.setPreferredSize(new Dimension((int)(spinner.getPreferredSize().width * 1.5), spinner.getPreferredSize().height));
        spinner.addChangeListener((ChangeEvent e) -> {
            int n = (int) spinner.getValue();
            slider.setMaximum(n);
            frames = new BufferedImage[n];
            image.repaint();
        });
        
        slider.addChangeListener((ChangeEvent e) -> {
            currentFrame = slider.getValue() - 1;
            image.repaint();
        });
        
        render.addActionListener((ActionEvent ae) -> {
            renderScene(editor.getText(), () -> { });
        });
        
        controls.add(spinner, BorderLayout.LINE_START);
        controls.add(slider, BorderLayout.CENTER);
        controls.add(render, BorderLayout.LINE_END);
        
        add(controls, BorderLayout.PAGE_END);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(texture);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    

    public void renderScene(String code, Runnable endAction) {
             
        int w = image.getWidth();
        int h = image.getHeight();
        image.setPreferredSize(new Dimension(w, h));
        slider.setEnabled(false);
        render.setEnabled(false);
        
        for (int i = 0; i < frames.length; ++i)
            frames[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        (new Thread(() -> {
            String f = frames.length > 1 ? "frames" : "frame";
            System.out.println("Rendering " + frames.length + " " + f + "...");
            if (frames.length == 1)
                System.err.println("Warning: you may want to render more than 1 frame for an animation");
            for (int i = 0; i < frames.length; ++i) {

                currentFrame = i;
                slider.setValue(i + 1);
                System.out.println("Rendering frame " + (currentFrame + 1) + " / " + frames.length);
                
                Script script = Compiler.compile(code, new Variable(new Identifier("t"), i));
                Scene scene = script.getScene();

                scene.getSettings().setResolution(w, h);
                scene.getSettings().updateFov();

                Tracer tracer = Tracer.getInstance();

                ForkJoinPool pool = tracer.parallelRender(4, (int[][] p, Integer pass) -> draw(this, p, pass, w, h), endAction, scene, true);
                pool.awaitQuiescence(365, TimeUnit.DAYS);
                
                image.repaint();
            }
            slider.setEnabled(true);
            render.setEnabled(true);
            
            // This writer has worse quality
//            try {
//                // Save gif
//                ImageOutputStream output = new FileImageOutputStream(new File("./animationA_" + (System.currentTimeMillis() / 1000) + ".gif"));
//                GifSequenceWriter writer = new GifSequenceWriter(output, frames[0].getType(), 16, true);
//                for (int i = 0; i < frames.length; ++i) {
//                    writer.writeToSequence(frames[i]);
//                }
//                writer.close();
//                output.close();
//            } catch (IOException ex) {
//                Exceptions.printStackTrace(ex);
//            }
            
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.start("./animation_" + (System.currentTimeMillis() / 1000) + ".gif");
            e.setDelay(10);
            for (int i = 0; i < frames.length; ++i) {
                e.addFrame(frames[i]);
            }
            e.finish();

            
            
        })).start();

    }

    public void draw(AnimationPanel panel, int[][] picture, int pass, int w, int h) {
        int step = (int) Math.pow(2, pass);
        for (int x = 0; x < w - (w % step); x += step) {
            for (int y = 0; y < h - (h % step); y += step) {
                if (pass == 0) {
                    frames[currentFrame].setRGB(x, y, picture[x][y]);
                } else {
                    for (int i = 0; i < step; ++i) {
                        for (int j = 0; j < step; ++j) {
                            frames[currentFrame].setRGB(x + i, y + j, picture[x][y]);
                        }
                    }
                }
            }
        }
        panel.image.repaint();
    }
   
}
