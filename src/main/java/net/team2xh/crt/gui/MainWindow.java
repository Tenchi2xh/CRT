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

import bibliothek.gui.dock.common.CContentArea;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.ColorMap;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.intern.DefaultCDockable;
import bibliothek.gui.dock.common.theme.ThemeMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import net.team2xh.crt.gui.editor.Editor;
import net.team2xh.crt.gui.entities.EntityTree;
import net.team2xh.crt.gui.themes.SubstanceTheme;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.raytracer.Scene;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MainWindow extends JFrame {

    private final CControl control;
    private final Theme theme;
    
    private final Editor editorComponent;
    private final EntityTree navigatorComponent;
    private final JLabel renderComponent;
    private final JLabel systemComponent;
    private final JLabel settingsComponent;

    public MainWindow(Theme theme) {

        this.theme = theme;
        this.control = new CControl(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        
        ThemeMap themes = control.getThemes();
        themes.select(ThemeMap.KEY_ECLIPSE_THEME);

        CContentArea contentArea = control.getContentArea();
        add(contentArea, BorderLayout.CENTER);
        
        editorComponent = new Editor(theme);
        navigatorComponent = new EntityTree();
        renderComponent = new JLabel("Render goes here");
        systemComponent = new JLabel("Infos go here");
        settingsComponent = new JLabel("Settings go here");

        DefaultCDockable editor = (DefaultCDockable) create("Script", editorComponent);
        DefaultCDockable navigator = (DefaultCDockable) create("Navigator", navigatorComponent);
        DefaultCDockable render = (DefaultCDockable) create("Render", renderComponent);
        DefaultCDockable system = (DefaultCDockable) create("System informations", systemComponent);
        DefaultCDockable settings = (DefaultCDockable) create("Settings", settingsComponent);

        CGrid grid = new CGrid(control);
        grid.add(0, 0, 0.5, 0.75, render);
        grid.add(0.5, 0, 0.25, 0.75, editor);
        grid.add(0.75, 0, 0.25, 1, navigator);
        grid.add(0, 0.75, 0.375, 0.25, system);
        grid.add(0.375, 0.75, 0.375, 0.25, settings);
        contentArea.deploy(grid);

        setVisible(true);
    }
    
    public void loadScene(Scene scene) {
        navigatorComponent.loadScene(scene);
    }

    private CDockable create(String title, JComponent component) {
        DefaultCDockable dockable = new DefaultSingleCDockable(title, title, component);
        dockable.setCloseable(false);
        
        ColorMap map = dockable.getColors();
        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND, UIManager.getColor("TextArea.background", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND, UIManager.getColor("TextArea.background", null));
        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND, UIManager.getColor("TextArea.background", null));
        
        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
//        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground", null));
        
        return dockable;
    }

}
