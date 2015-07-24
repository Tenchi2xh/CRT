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
package net.team2xh.crt.gui.liveview;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Object3D;
import com.threed.jpct.Projector;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.ShadowHelper;
import com.threed.jpct.util.SkyBox;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import net.team2xh.crt.gui.RenderPanel;
import net.team2xh.crt.gui.liveview.converters.SceneConverter;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Scene;
import org.openide.util.Exceptions;

/**
 * TODO: set focal distance function by clicking a point then trace a ray to it
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class LiveViewPanel extends JPanel {

    private World world;
    public SkyBox skybox;
    public BufferPanel buffers[];

    public ShadowHelper[] shs = new ShadowHelper[3];
    public Projector projector;

    private JFrame parent;
    private RenderPanel renderPanel;

    public int distMult = 20;

    public SimpleVector sun = null;
    
    private JButton camLt;
    private JButton camRt;
    private JButton camUp;
    private JButton camDn;
    private JButton renderButton;
    public JToggleButton toggleShdr;

    private MouseListener ml;

    private Object3D lastHighlight = null;
    private Color lastColor = null;

    public boolean useShaders = false;
    public Camera cameraY;
    public Camera cameraZ;
    private boolean isRunning = true;

    public LiveViewPanel(Scene scene, JFrame parent) {
        
        this.parent = parent;
        
        SwingUtilities.invokeLater(() -> {
            setLayout(new BorderLayout());
            JPanel panel = new JPanel(new GridLayout(2, 2));

            TextureManager.getInstance().flush();
            
            buffers = new BufferPanel[]{new BufferPanel(), new BufferPanel(), new BufferPanel()};

            renderPanel = new RenderPanel();

            panel.add(buffers[1]);
            panel.add(buffers[2]);
            panel.add(renderPanel);
            panel.add(buffers[0]);

            // TODO: resize listener to resize buffers
            add(panel, BorderLayout.CENTER);

            ml = new MouseListener();
            buffers[0].getCanvas().addMouseListener(ml);
            buffers[0].getCanvas().addMouseMotionListener(ml);

            JPanel buttons = new JPanel();
            camUp = new JButton("^");
            camDn = new JButton("v");
            camLt = new JButton("<");
            camRt = new JButton(">");
            renderButton = new JButton("Render");
            toggleShdr = new JToggleButton("Shaders");
            buttons.add(camUp);
            buttons.add(camDn);
            buttons.add(camLt);
            buttons.add(camRt);
            buttons.add(renderButton);
            buttons.add(toggleShdr);
            add(buttons, BorderLayout.PAGE_END);

            renderButton.addActionListener((ActionEvent e) -> {
                RenderPanel.renderScene(scene, () -> {}, renderPanel);
            });

            Config.lightMul = 1;
            Config.specPow = 100;
            Config.specTerm = 15;
            Config.glShadowZBias = 0.03f * distMult;
            
            world = SceneConverter.convertScene(scene, this);
            
            toggleShdr.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent ev) {
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        for (Enumeration<Object3D> en = world.getObjects(); en.hasMoreElements();) {
                            Object3D obj = en.nextElement();
                            GLSLShader shader = (GLSLShader) obj.getUserObject();
                            obj.setRenderHook(shader);
                        }
                        useShaders = true;
                    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                        for (Enumeration<Object3D> en = world.getObjects(); en.hasMoreElements();) {
                            Object3D obj = en.nextElement();
                            obj.setRenderHook(null);
                        }
                        useShaders = false;
                    }
                }
            });
            
            (new Thread(() -> {
                try {
                    loop();
                } catch (InterruptedException ex) {
                    Exceptions.printStackTrace(ex);
                }
            })).start();
            
        });
    }
    
    public SimpleVector camOrigPos;
    public SimpleVector camLookAt;

    private void moveCamera(float ax, float ay) {

        SimpleVector up = world.getCamera().getUpVector();
        SimpleVector right = world.getCamera().getSideVector();

        SimpleVector pos = world.getCamera().getPosition();
        float distance = camLookAt.distance(pos);

        right.scalarMul(ax);
        up.scalarMul(ay);

        pos.add(right);
        pos.add(up);

        world.getCamera().setPosition(pos);
        world.getCamera().lookAt(camLookAt);

        float newDistance = camLookAt.distance(pos);
        world.getCamera().moveCamera(Camera.CAMERA_MOVEIN, newDistance - distance);

    }

    private Object[] getHoveredObject() {
        SimpleVector dir = Interact2D.reproject2D3DWS(world.getCamera(), buffers[0].getBuffer(), ml.x, ml.y).normalize();
        return world.calcMinDistanceAndObject3D(world.getCamera().getPosition(), dir, 1000f);
    }

    private void highlightHoveredObject() {
        Object[] hit = getHoveredObject();
        if ((float) hit[0] != Object3D.COLLISION_NONE) {
            Object3D obj = (Object3D) hit[1];
            if (lastHighlight != null) {
                if (useShaders) {
                    GLSLShader shader = (GLSLShader) lastHighlight.getUserObject();
                    shader.setUniform("isHighlighted", 0);
                } else {
                    lastHighlight.setAdditionalColor(lastColor);
                }
            }
            if (useShaders) {
                GLSLShader shader = (GLSLShader) obj.getUserObject();
                shader.setUniform("isHighlighted", 1);
            } else {
                lastColor = obj.getAdditionalColor();
                obj.setAdditionalColor(Color.RED);
            }
            lastHighlight = obj;
        } else {
            if (lastHighlight != null) {
                if (useShaders) {
                    GLSLShader shader = (GLSLShader) lastHighlight.getUserObject();
                    shader.setUniform("isHighlighted", 0);
                } else {
                    lastHighlight.setAdditionalColor(lastColor);
                }
                lastHighlight = null;
            }
        }
    }

    private void loop() throws InterruptedException {
        while (isRunning) {

            float mdx = 0.0f;
            float mdy = 0.0f;
            if (camRt.getModel().isPressed()) {
                mdx += 0.5;
            }
            if (camLt.getModel().isPressed()) {
                mdx -= 0.5;
            }
            if (camUp.getModel().isPressed()) {
                mdy += 0.5;
            }
            if (camDn.getModel().isPressed()) {
                mdy -= 0.5;
            }
            if (mdx != 0 || mdy != 0) {
                moveCamera(mdx, mdy);
            } else if (ml.dx != 0 || ml.dy != 0) {
                moveCamera(-ml.dx, ml.dy);
                ml.dx = 0;
                ml.dy = 0;
            }

            if (!ml.pressed) {
                highlightHoveredObject();
            }

            Camera[] cameras = new Camera[]{world.getCamera(), cameraY, cameraZ};

            // TODO: Only draw when needed
            for (int i = 0; i < 3; ++i) {
                FrameBuffer buffer = buffers[i].getBuffer();
                Camera camera = cameras[i];
                Canvas canvas = buffers[i].getCanvas();
                ShadowHelper sh = shs[i];

                world.setCameraTo(camera);

                buffer.clear(java.awt.Color.GRAY);

                skybox.render(world, buffer);

                // TODO: Shadows buggy when 3 viewports
//                if (sun != null) {
//                    sh.drawScene();
//                } else {
                world.renderScene(buffer);
                world.draw(buffer);

                buffer.update();
                buffer.displayGLOnly();
                canvas.repaint();

            }

            Thread.sleep(10);
            world.setCameraTo(cameras[0]);

        }
        for (BufferPanel p : buffers) {
            p.getBuffer().disableRenderer(IRenderer.RENDERER_OPENGL);
            p.getBuffer().dispose();
        }
        skybox.dispose();
        world.removeAll();
        world.dispose();
        System.out.println("Stopped");
    }

    private class MouseListener extends MouseInputAdapter {

        private int x = 0;
        private int y = 0;

        private int dx = 0;
        private int dy = 0;

        private Point p;

        private boolean pressed = false;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            p = e.getPoint();
            pressed = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            dx = e.getX() - p.x;
            dy = e.getY() - p.y;
            p = e.getPoint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

    }
    
    public void stop() {
        isRunning = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CRT OpenGL Renderer");
            Scene scene = new TestScene();
            LiveViewPanel t = new LiveViewPanel(scene, frame);
            frame.add(t);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GUIToolkit.centerFrame(frame);
        });

    }
}
