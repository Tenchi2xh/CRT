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

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.team2xh.crt.gui.MainWindow;
import net.team2xh.crt.gui.RenderPanel;
import net.team2xh.crt.gui.editor.EditorTextPane;
import net.team2xh.crt.language.compiler.Script;
import net.team2xh.crt.language.compiler.Compiler;
import org.openide.util.Exceptions;

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
                    // TODO: do in forkpool for interrupt
                    String code = editor.getText();
                    Script script = Compiler.compile(code);
                    RenderPanel.renderScene(script.getScene(), MainWindow.getInstance().getRenderer());
                } else {
                    a.init("play", "Render", "Render the current scene", KeyEvent.VK_R);
                    a.setUserObject(true);
                }
            }, true);
    
    public final static Action preview =
            new Action("preview", "Preview", "Do a quick render of the current scene", KeyEvent.VK_P, (Action a) -> {
            });
    
    public final static Action export =
            new Action("export", "Export", "Export current render as an image file", KeyEvent.VK_E, (Action a) -> {
            });
    
    public final static Action perspCode =
            new Action("persp_code", "Code Perspective", "Switch to the code perspective", KeyEvent.VK_C, (Action a) -> {
            });
    
    public final static Action perspLive =
            new Action("persp_live", "Live Perspective", "Switch to the live perspective", KeyEvent.VK_L, (Action a) -> {
            });
    
    public final static Action fullscreen =
            new Action("fullscreen", "Fullscreen", "Toggle fullscreen view", KeyEvent.VK_F, (Action a) -> {
                if ((boolean) a.getUserObject()) {
                    a.setUserObject(false);
                } else {
                    a.setUserObject(true);
                }
            }, true);
    
    public final static Action settings =
            new Action("settings", "Settings", "Application settings", KeyEvent.VK_T, (Action a) -> {
            });
    
    public final static Action about =
            new Action("about", "About", "About this program", KeyEvent.VK_A, (Action a) -> {
            });
    
    private Actions() { }
    
    private static final JFileChooser fc = new JFileChooser();
    static {
        fc.setFileFilter(new FileNameExtensionFilter("CRT Script", "crt"));
        fc.setAcceptAllFileFilterUsed(true);
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
