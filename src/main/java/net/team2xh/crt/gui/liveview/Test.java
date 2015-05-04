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
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.Projector;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.ExtendedPrimitives;
import com.threed.jpct.util.Light;
import com.threed.jpct.util.ShadowHelper;
import com.threed.jpct.util.SkyBox;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import net.team2xh.crt.gui.RenderPanel;
import net.team2xh.crt.gui.liveview.converters.BackgroundConverter;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Material;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.entities.Box;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.entities.Plane;
import net.team2xh.crt.raytracer.entities.Sphere;
import net.team2xh.crt.raytracer.lights.ParallelLight;
import net.team2xh.crt.raytracer.lights.PointLight;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 * TODO: set focal distance function by clicking a point then trace a ray to it
 * 
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Test {

    private World world;
    private SkyBox skybox;
    private FrameBuffer buffer1;
    private FrameBuffer buffer2;
    private FrameBuffer buffer3;
    private FrameBuffer buffers[];
    
    private ShadowHelper[] shs = new ShadowHelper[3];
    private Projector projector;

    private JFrame frame;
    private Canvas canvas1;
    private Canvas canvas2;
    private Canvas canvas3;
    private RenderPanel canvas4;
    private final Canvas[] canvases;

    private int distMult = 20;
    
    SimpleVector sun = null;

    private Scene scene = new TestScene();
    
    private JButton camLt;
    private JButton camRt;
    private JButton camUp;
    private JButton camDn;
    private JButton renderButton;
    private JToggleButton toggleShdr;

    private MouseListener ml;
    
    private Object3D lastHighlight = null;
    private Color lastColor = null;
    
    private boolean useShaders = false;
    private final Camera cameraY;
    private final Camera cameraZ;
    
    public Test() {
        buffer1 = new FrameBuffer(scene.getSettings().getWidth() / 2, scene.getSettings().getHeight() / 2, FrameBuffer.SAMPLINGMODE_GL_AA_4X);
        buffer2 = new FrameBuffer(scene.getSettings().getWidth() / 2, scene.getSettings().getHeight() / 2, FrameBuffer.SAMPLINGMODE_GL_AA_4X);
        buffer3 = new FrameBuffer(scene.getSettings().getWidth() / 2, scene.getSettings().getHeight() / 2, FrameBuffer.SAMPLINGMODE_GL_AA_4X);

        buffer1.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer2.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        buffer3.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        canvas1 = buffer1.enableGLCanvasRenderer();
        canvas2 = buffer2.enableGLCanvasRenderer();
        canvas3 = buffer3.enableGLCanvasRenderer();
        
        buffers = new FrameBuffer[] { buffer1, buffer2, buffer3 };
        canvases = new Canvas[] { canvas1, canvas2, canvas3 };
        
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("CRT OpenGL Renderer");
            JPanel panel = new JPanel(new GridLayout(2, 2));
            
            canvas4 = new RenderPanel();
        
            panel.add(canvas2);
            panel.add(canvas3);
            panel.add(canvas4);
            panel.add(canvas1);
            
            // TODO: resize listener to resize buffers

            frame.getContentPane().add(panel, BorderLayout.CENTER);

            ml = new MouseListener();
            canvas1.addMouseListener(ml);
            canvas1.addMouseMotionListener(ml);
            
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
            frame.add(buttons, BorderLayout.PAGE_END);
            
            renderButton.addActionListener((ActionEvent e) -> {
                RenderPanel.renderScene(scene, canvas4);
            });

            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GUIToolkit.centerFrame(frame);
        });

        
        Config.lightMul = 1;
        Config.specPow = 100;
        Config.specTerm = 15;
        Config.glShadowZBias = 0.03f * distMult;

        world = new World();
        world.setAmbientLight(0, 0, 0);

        InputStream unknown = getClass().getResourceAsStream("/images/unknown.png");
        TextureManager.getInstance().addTexture("unknown", new Texture(unknown, true));
        
        String vertex = Loader.loadTextFile(getClass().getResourceAsStream("shaders/vertex.glsl"));
        String fragment = Loader.loadTextFile(getClass().getResourceAsStream("shaders/fragment.glsl"));
        
        for (net.team2xh.crt.raytracer.lights.Light l0 : scene.getLights()) {
            Light l1 = new Light(world);
            Class type = l0.getClass();

            if (type == PointLight.class) {
                PointLight pl = (PointLight) l0;
                l1.setPosition(pl.getOrigin().multiply(distMult).getRightHanded().simpleVector());

            } else if (type == ParallelLight.class) {
                ParallelLight pl = (ParallelLight) l0;
                SimpleVector pos = pl.getDirection(Vector3.O).multiply(20*distMult).getRightHanded().simpleVector();
                l1.setPosition(pos);
                // Shadows buggy with multiple viewports
//                if (sun == null) {
//                    sun = pos;
//                    System.out.println(pos);
//                }
            }

            SimpleVector intensity = l0.getPigment().getVector().simpleVector();
            intensity.scalarMul(255f);
            l1.setIntensity(intensity);
            l1.setAttenuation((float) l0.getFalloff() * distMult);

            if (type == ParallelLight.class) {
                intensity = l0.getPigment().getVector().simpleVector();
                intensity.scalarMul(255f);
                l1.setIntensity(intensity);
            }
        }

        float vfov = (float) scene.getCamera().getVerticalFov();
        float hfov = 2 * (float) Math.atan(Math.tan(vfov / 2) * scene.getSettings().getWidth() / scene.getSettings().getHeight());
        
        world.getCamera().setFOVLimits(0, (float) (2*Math.PI));
        world.getCamera().setFovAngle(hfov);

        camOrigPos = scene.getCamera().getPosition().multiply(distMult).getRightHanded().simpleVector();
        camLookAt = scene.getCamera().getPointing().multiply(distMult).getRightHanded().simpleVector();
        world.getCamera().setPosition(camOrigPos);
        world.getCamera().lookAt(camLookAt);
        world.getCamera().rotateCameraAxis(world.getCamera().getDirection(), (float) -scene.getCamera().getRoll());

        cameraY = new Camera();
        cameraZ = new Camera();
        cameraY.setPosition(0, -5*distMult, 0);
        cameraZ.setPosition(0, 0, -5*distMult);
        cameraY.lookAt(SimpleVector.ORIGIN);
        cameraZ.lookAt(SimpleVector.ORIGIN);
        
        if (sun != null) {
            
            projector = new Projector();
            projector.setFOV(1.5f);
            projector.setYFOV(1.5f);

            for (int i = 0; i < 3; ++i) {
                ShadowHelper sh = new ShadowHelper(world, buffers[i], projector, 4096);
                
                sh.setCullingMode(false);
                sh.setAmbientLight(new Color(0, 0, 0));
                sh.setLightMode(true);
                sh.setBorder(1);
                
                shs[i] = sh;
            }
        }

        for (Entity e : scene.getEntities()) {
            Object3D obj;
            Class type = e.getClass();

            boolean sprite = false;

            if (type == Box.class) {
                Box box = (Box) e;
                SimpleVector dimensions = SimpleVector.create((float) box.getWidth() * distMult,
                        (float) box.getHeight() * distMult,
                        (float) box.getDepth() * distMult);
                obj = ExtendedPrimitives.createBox(dimensions);
                obj.translate(box.getMinCorner().multiply(distMult).getRightHanded().simpleVector());
//                obj.setShadingMode(Object3D.SHADING_FAKED_FLAT);
            } else if (type == Sphere.class) {
                Sphere sphere = (Sphere) e;
                obj = Primitives.getSphere((float) sphere.getRadius() * distMult);
            } else if (type == Plane.class) {
                obj = ExtendedPrimitives.createPlane(distMult, 100);
            } else {
                obj = ExtendedPrimitives.createSprite(0.2f * distMult);
                obj.setTexture("unknown");
                obj.setTransparency(20);
                sprite = true;
            }

            if (sun != null && !sprite) {
                for (int i = 0; i < 3; ++i) {
                    shs[i].addCaster(obj);
                    shs[i].addReceiver(obj);
                }
            }

            obj.translate(e.getCenter().multiply(distMult).getRightHanded().simpleVector());
            obj.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);

            Material m = e.getMaterial();

            obj.setSpecularLighting(true);
            obj.setAdditionalColor(m.color.getColor().darker().darker().darker());
            
            GLSLShader shader = new GLSLShader(vertex, fragment);
            shader.setUniform("isHighlighted", 0);
            shader.setUniform("matColor", m.color.getColor().getColorComponents(null));
            obj.setUserObject(shader);
            if (useShaders) {
                obj.setRenderHook(shader);
            }

            // Compile has to be dynamic or else it crashes when having multiple viewports
            obj.compile(true);
            obj.strip();
            obj.build();

            world.addObject(obj);
        }

        if (sun != null) {
            projector.setPosition(sun);
            projector.lookAt(SimpleVector.ORIGIN);

            for (int i = 0; i < 3; ++i) {
                shs[i].updateShadowMap();
            }
        }
        
        skybox = BackgroundConverter.convertBackground(scene.getBackground());

        
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
        
    }

    private SimpleVector camOrigPos;
    private SimpleVector camLookAt;

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
        SimpleVector dir = Interact2D.reproject2D3DWS(world.getCamera(), buffer1, ml.x, ml.y).normalize();
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

        while (frame.isShowing()) {

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
            
            if (!ml.pressed) highlightHoveredObject();

            Camera[] cameras = new Camera[] { world.getCamera(), cameraY, cameraZ };

            for (int i = 0; i < 3; ++i) {
                FrameBuffer buffer = buffers[i];
                Camera camera = cameras[i];
                Canvas canvas = canvases[i];
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
        buffer1.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer2.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer3.disableRenderer(IRenderer.RENDERER_OPENGL);
        buffer1.dispose();
        buffer2.dispose();
        buffer3.dispose();
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

    public static void main(String[] args) {
        try {
            new Test().loop();
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
