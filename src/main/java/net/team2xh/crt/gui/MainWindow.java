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

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.extension.gui.dock.theme.eclipse.stack.tab.RectGradientPainter;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.common.CContentArea;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.ColorMap;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.intern.DefaultCDockable;
import bibliothek.gui.dock.common.mode.ExtendedMode;
import bibliothek.gui.dock.common.theme.ThemeMap;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import net.team2xh.crt.gui.editor.Editor;
import net.team2xh.crt.gui.entities.EntityTree;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.raytracer.Scene;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MainWindow extends JFrame {

    private final CControl control;
    private final Theme theme;

    private final Editor editor;
    private final EntityTree navigator;
    private final RenderPanel render;
    private final JLabel system;
    private final JLabel settings;
    private final JLabel console;

    public MainWindow(Theme theme) {

        this.theme = theme;
        this.control = new CControl(this);

        setTitle("CRT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        ThemeMap themes = control.getThemes();
        themes.select(ThemeMap.KEY_ECLIPSE_THEME);
        control.putProperty(EclipseTheme.TAB_PAINTER, RectGradientPainter.FACTORY);

        DockController controller = control.getController();
//        controller.getThemeManager().setBorderModifier("dock.border.displayer.basic.base", (Border) -> BorderFactory.createLineBorder(Color.BLUE, 20));
//        controller.getThemeManager().setBorderModifier("dock.border.displayer.basic.content", (Border) -> BorderFactory.createLineBorder(Color.RED, 20));
//        controller.getThemeManager().setBorderModifier("dock.border.stack.eclipse", (Border) -> BorderFactory.createLineBorder(Color.CYAN, 20));
        controller.getThemeManager().setBorderModifier("dock.border.stack.eclipse.content", (Border) -> BorderFactory.createEmptyBorder());

        CContentArea contentArea = control.getContentArea();
        add(contentArea, BorderLayout.CENTER);

        editor = new Editor(theme);
        navigator = new EntityTree();
        render = new RenderPanel();
        system = new JLabel("Infos go here");
        settings = new JLabel("Settings go here");
        console = new JLabel("Console goes here");

        DefaultCDockable dEditor = (DefaultCDockable) create("Script", editor);
        DefaultCDockable dNavigator = (DefaultCDockable) create("Navigator", navigator);
        DefaultCDockable dRender = (DefaultCDockable) create("Render", render);
        DefaultCDockable dSystem = (DefaultCDockable) create("System informations", system);
        DefaultCDockable dSettings = (DefaultCDockable) create("Settings", settings);
        DefaultCDockable dConsole = (DefaultCDockable) create("Console", console);

        double wr = 0.45;
        double we = 0.35;
        double wn = 1 - wr - we;

        double h1 = 0.7;
        double h2 = 1 - h1;

        CGrid grid = new CGrid(control);
        grid.add(0,     0,  wr, h1, dRender);
        grid.add(wr,    0,  we, h1, dEditor);
        grid.add(wr+we, 0,  wn, h1, dNavigator);
        grid.addHorizontalDivider(0, 1, h1);
        grid.add(0,     h1, wr+we, h2, dConsole);
        grid.add(wr+we, h1, wn, h2, dSettings, dSystem);
        contentArea.deploy(grid);

        dRender.setExternalizable(true);
        dRender.setMaximizable(true);
        dRender.setMinimizable(true);
        dRender.setLocation(CLocation.base().minimalWest());
        dRender.setExtendedMode(ExtendedMode.NORMALIZED);

        dNavigator.setMinimizable(true);
        dNavigator.setLocation(CLocation.base().minimalEast());
        dNavigator.setExtendedMode(ExtendedMode.NORMALIZED);

        setVisible(true);
    }

    public void loadScene(Scene scene) {
        navigator.loadScene(scene);
    }

    private CDockable create(String title, JComponent component) {
        DefaultCDockable dockable = new DefaultSingleCDockable(title, title, component);
        dockable.setCloseable(false);
        dockable.setExternalizable(false);
        dockable.setMinimizable(false);
        dockable.setMaximizable(false);

        ColorMap map = dockable.getColors();
        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND, UIManager.getColor("TextArea.background", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND, UIManager.getColor("TextArea.background"));
//        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND, UIManager.getColor("TextArea.background"));

        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground"));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground"));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground"));
        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground"));
//        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground"));

//        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND_FOCUSED, UIManager.getColor("TextArea.selectionForeground"));
//        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND_SELECTED, Color.BLACK);
//        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND, Color.ORANGE);
//        map.setColor(ColorMap.COLOR_KEY_TITLE_FOREGROUND, Color.CYAN);
//        map.setColor(ColorMap.COLOR_KEY_TITLE_FOREGROUND_FOCUSED, Color.PINK);
//        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_SELECTED, Color.PINK);

        return dockable;
    }

}
