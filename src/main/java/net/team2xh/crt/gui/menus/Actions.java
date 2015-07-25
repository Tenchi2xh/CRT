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
package net.team2xh.crt.gui.menus;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.team2xh.crt.gui.MainWindow;
import net.team2xh.crt.gui.RenderPanel;
import net.team2xh.crt.gui.editor.EditorTextPane;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.language.compiler.Script;
import net.team2xh.crt.language.compiler.Compiler;
import net.team2xh.crt.raytracer.Tracer;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
final public class Actions {
    
    private static String lastPath = Paths.get(".").toAbsolutePath().normalize().toString();
    
    public final static Action newProject =
            new Action("new", "New", "Create a new project", KeyEvent.VK_N, (Action a) -> {
                MainWindow main = MainWindow.getInstance();
                EditorTextPane editor = main.getEditor();
                boolean cancelled = checkChanged();
                if (!cancelled) {
                    editor.setText("");
                    editor.resetChanged();
                }
            });
    
    public final static Action open =
            new Action("open", "Open...", "Open an existing project", KeyEvent.VK_O, (Action a) -> {
                MainWindow main = MainWindow.getInstance();
                EditorTextPane editor = main.getEditor();
                
                boolean cancelled = checkChanged();
                if (!cancelled) {
                    open();
                }
            });
    
    public final static Action save =
            new Action("save", "Save", "Save current project", KeyEvent.VK_S, (Action a) -> {
                saveAs();
            });
    
    // TODO: refactor to ToggleAction
    public final static Action render =
            new Action("play", "Render", "Render the current scene", KeyEvent.VK_R, (Action a) -> {
                MainWindow main = MainWindow.getInstance();
                EditorTextPane editor = main.getEditor();
                if ((boolean) a.getUserObject()) {
                    a.init("stop", "Stop", "Cancel the render", KeyEvent.VK_R);
                    a.setUserObject(false);
                    String code = editor.getText();
                    Script script = Compiler.compile(code);
                    script.getSettings().setResolution(main.getRenderer().getWidth(), main.getRenderer().getHeight());
                    refreshLiveView(script);
                    RenderPanel.renderScene(script.getScene(), () -> {
                        a.init("play", "Render", "Render the current scene", KeyEvent.VK_R);
                        a.setUserObject(true);
                    }, MainWindow.getInstance().getRenderer());
                } else {
                    Tracer.getInstance().stop();
                    a.init("play", "Render", "Render the current scene", KeyEvent.VK_R);
                    a.setUserObject(true);
                    MainWindow.getInstance().getStatusBar().getProgressBar().setString("Canceled");
                }
            }, true);
    
    public final static Action preview =
            new Action("preview", "Preview", "Refresh the live preview window", KeyEvent.VK_P, (Action a) -> {
                refreshLiveView();
            });

    public final static Action export =
            new Action("export", "Export", "Export current render as an image file", KeyEvent.VK_E, (Action a) -> {
                BufferedImage bi = MainWindow.getInstance().getRenderer().getImage();
                if (bi != null) {
                    saveImage(bi);
                }
            });
    
    public final static Action perspCode =
            new Action("persp_code", "Code Perspective", "Switch to the code perspective", KeyEvent.VK_C, (Action a) -> {
            });
    
    public final static Action perspLive =
            new Action("persp_live", "Live Perspective", "Switch to the live perspective", KeyEvent.VK_L, (Action a) -> {
            });
    
    public final static Action fullscreen
            = new Action("fullscreen", "Fullscreen", "Toggle fullscreen view", KeyEvent.VK_F, (Action a) -> {
                GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
                MainWindow main = MainWindow.getInstance();

                if (!(boolean) a.getUserObject()) {
                    main.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                    device.setFullScreenWindow(null);
                    main.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    a.setUserObject(true);
                } else {
                    main.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
                    device.setFullScreenWindow(main);
                    a.setUserObject(false);
                }
            }, true);
    
    public final static Action settings =
            new Action("settings", "Settings", "Application settings", KeyEvent.VK_T, (Action a) -> {
            });
    
    public final static Action about =
            new Action("about", "About", "About this program", KeyEvent.VK_A, (Action a) -> {
                try {
                    JDialog d = new JDialog();
                    d.setTitle("About CRT");
                    
                    JPanel contents = new JPanel();
                    contents.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    contents.setLayout(new BorderLayout());

                    URI uri = d.getClass().getResource("/images/logo.png").toURI();
                    File file = new File(uri);
                    ImageIcon logo = new ImageIcon(ImageIO.read(file));
                    System.out.println(logo);

                    JLabel logoL = new JLabel(logo);
                    contents.add(logoL, BorderLayout.PAGE_START);
                    JLabel text = new JLabel("<html><center><b>CRT &mdash; Cathode Ray Tracer</b></center>"
                            + "<br/>Bachelor's Thesis Project at HEIG-VD.<br/>Code available on GitHub at http://github.com/Tenchi2xh/CRT"
                            + "<br/><br/>Author: Hamza Haiken (tenchi@team2xh.net)"
                            + "<br/>Advisor: Pier Donini (pier.donini@heig-vd.ch)"
                            + "<br/><br/>Licensed under the GPL3 license"
                            + "<br/>Copyright 2015 Team2xh</html>", SwingConstants.CENTER);
                    contents.add(text, BorderLayout.CENTER);
                    text.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                    JButton b = new JButton("Ok");
                    b.addActionListener((ActionEvent e) -> {
                        d.setVisible(false);
                        d.dispose();
                    });
                    contents.add(b, BorderLayout.PAGE_END);
                    d.add(contents, BorderLayout.CENTER);
                    d.pack();
                    d.setVisible(true);
                    GUIToolkit.centerFrame(d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    
    private Actions() { }
    
    private static final JFileChooser fc = new JFileChooser();
    static {
        fc.setFileFilter(new FileNameExtensionFilter("CRT Script", "crt"));
        fc.setAcceptAllFileFilterUsed(true);
    }    
    private static final JFileChooser fc2 = new JFileChooser();
    static {
        fc2.setFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
        fc2.setAcceptAllFileFilterUsed(false);
    }

    private static boolean checkChanged() {
        MainWindow main = MainWindow.getInstance();
        EditorTextPane editor = main.getEditor();

        if (editor.getChanged()) {
            int choice = JOptionPane.showConfirmDialog(main, "Script was changed - do you want to save?", "Save modifications?",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                return !saveAs();
            } else if (choice == JOptionPane.CANCEL_OPTION) {
                return true;
            }
        }
        
        return false;
    }

    private static boolean saveAs() {
        MainWindow main = MainWindow.getInstance();
        EditorTextPane editor = main.getEditor();
        
        File lf = new File(lastPath);
        fc.setCurrentDirectory(lf);
        if (lf.isFile())
            fc.setSelectedFile(lf);
        int rval = fc.showSaveDialog(main);
        lastPath = fc.getSelectedFile().getAbsolutePath();
        
        if (rval == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            if (!fc.getSelectedFile().getAbsolutePath().endsWith(".crt")) {
                f = new File(fc.getSelectedFile() + ".crt");
                lastPath = f.getAbsolutePath();
                System.out.println(lastPath);
            }
            try (Writer writer = new BufferedWriter(new FileWriter(f))) {
                writer.write(editor.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }
    
    private static void open() {
        MainWindow main = MainWindow.getInstance();
        EditorTextPane editor = main.getEditor();
        
        File lf = new File(lastPath);
        fc.setCurrentDirectory(lf);
        if (lf.isFile())
            fc.setSelectedFile(lf);
        int rval = fc.showOpenDialog(main);
        lastPath = fc.getSelectedFile().getAbsolutePath();

        if (rval == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (Reader reader = new FileReader(f)) {
                char[] chars = new char[(int) f.length()];
                reader.read(chars);
                editor.setText(new String(chars));
                editor.resetChanged();
                refreshLiveView();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean saveImage(BufferedImage bi) {
        MainWindow main = MainWindow.getInstance();
        
        fc2.setCurrentDirectory(new File(lastPath.substring(0, lastPath.toString().lastIndexOf(File.separator))));
        int rval = fc2.showSaveDialog(main);
        lastPath = fc2.getSelectedFile().getAbsolutePath();
        
        if (rval == JFileChooser.APPROVE_OPTION) {
            File f = fc2.getSelectedFile();
            if (!fc2.getSelectedFile().getAbsolutePath().endsWith(".png")) {
                f = new File(fc2.getSelectedFile() + ".png");
                lastPath = f.getAbsolutePath();
                System.out.println(lastPath);
            }
            try {
                ImageIO.write(bi, "PNG", f);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private static void refreshLiveView() {
        MainWindow main = MainWindow.getInstance();
        EditorTextPane editor = main.getEditor();

        String code = editor.getText();
        Script script = Compiler.compile(code);
        
        refreshLiveView(script);
    }
    
    private static void refreshLiveView(Script script) {
        MainWindow main = MainWindow.getInstance();

        script.getSettings().setResolution(main.getLiveViewPanel().getWidth() / 2, main.getLiveViewPanel().getHeight() / 2);

        main.updateLiveView(script.getScene());
    }

}
