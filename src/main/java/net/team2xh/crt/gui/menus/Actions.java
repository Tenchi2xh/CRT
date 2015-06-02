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
import net.team2xh.crt.gui.MainWindow;
import net.team2xh.crt.gui.RenderPanel;
import net.team2xh.crt.language.compiler.Script;
import net.team2xh.crt.language.compiler.Compiler;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
final public class Actions {
    
    public final static Action newProject =
            new Action("new", "New", "Create a new project", KeyEvent.VK_N, (Action a) -> {
            });
    
    public final static Action open =
            new Action("open", "Open...", "Open an existing project", KeyEvent.VK_O, (Action a) -> {
            });
    
    public final static Action save =
            new Action("save", "Save", "Save current project", KeyEvent.VK_S, (Action a) -> {
            });
    
    // TODO: refactor to ToggleAction
    public final static Action render =
            new Action("play", "Render", "Render the current scene", KeyEvent.VK_R, (Action a) -> {
                if ((boolean) a.getUserObject()) {
                    a.init("stop", "Stop", "Cancel the render", KeyEvent.VK_R);
                    a.setUserObject(false);
                    // TODO: do in forkpool for interrupt
                    String code = MainWindow.getInstance().getEditor().getText();
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
    
}
