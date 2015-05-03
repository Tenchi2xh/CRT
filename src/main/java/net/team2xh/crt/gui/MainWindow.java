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

import net.team2xh.crt.gui.console.ConsolePanel;
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
import bibliothek.gui.dock.common.theme.CEclipseTheme;
import bibliothek.gui.dock.dockable.ScreencaptureMovingImageFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import net.team2xh.crt.gui.editor.Editor;
import net.team2xh.crt.gui.entities.EntityTree;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Scene;


/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MainWindow extends JFrame {

    private final CControl control;

    private final Editor editor;
    private final EntityTree navigator;
    private final RenderPanel render;
    private final JLabel system;
    private final JLabel settings;
    private final ConsolePanel console;

    public MainWindow() {

        this.control = new CControl(this);

        setTitle("CRT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));

        EclipseTheme etheme = new EclipseTheme();
        etheme.setMovingImageFactory(new ScreencaptureMovingImageFactory(new Dimension(500, 500)));
        control.setTheme(new CEclipseTheme(control, etheme));
        control.putProperty(EclipseTheme.TAB_PAINTER, RectGradientPainter.FACTORY);
        control.putProperty(EclipseTheme.PAINT_ICONS_WHEN_DESELECTED, true);

        DockController controller = control.getController();
        controller.getThemeManager().setBorderModifier("dock.border.stack.eclipse.content", (Border) -> BorderFactory.createEmptyBorder());
        controller.getThemeManager().setBorderModifier("dock.border.title.eclipse.button.flat", (Border) -> BorderFactory.createLineBorder(UIManager.getColor("Button.border"), 1));
        
        CContentArea contentArea = control.getContentArea();
        add(contentArea, BorderLayout.CENTER);

        editor = new Editor();
        navigator = new EntityTree();
        render = new RenderPanel();
        system = new JLabel("Infos go here");
        settings = new JLabel("Settings go here");
        console = new ConsolePanel();

        DefaultCDockable dEditor = (DefaultCDockable) create("Script", editor);
        DefaultCDockable dNavigator = (DefaultCDockable) create("Navigator", navigator);
        DefaultCDockable dRender = (DefaultCDockable) create("Render", render);
        DefaultCDockable dSystem = (DefaultCDockable) create("System", system);
        DefaultCDockable dSettings = (DefaultCDockable) create("Settings", settings);
        DefaultCDockable dConsole = (DefaultCDockable) create("Console", console);
        
        dEditor.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/script.png")));
        dNavigator.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/navigator.png")));
        dRender.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/renderer.png")));
        dSystem.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/system.png")));
        dSettings.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/settings.png")));
        dConsole.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/console.png")));

        double wr = 0.4;
        double we = 0.4;
        double wn = 1 - wr - we;

        double h1 = 0.7;
        double h2 = 1 - h1;

        CGrid grid = new CGrid(control);
        grid.add(0, 0, wr, h1, dRender);
        grid.add(wr, 0, we, h1, dEditor);
        grid.add(wr + we, 0, wn, h1, dNavigator);
        grid.add(0, h1, wr + we, h2, dConsole);
        grid.add(wr + we, h1, wn, h2, dSystem, dSettings);
        grid.addHorizontalDivider(0, 1, h1);
//        grid.addVerticalDivider(wr + we, 0, 1);
        contentArea.deploy(grid);

        dRender.setExternalizable(true);
        dRender.setMaximizable(true);
        dRender.setMinimizable(true);
        dRender.setLocation(CLocation.base().minimalWest());
        dRender.setExtendedMode(ExtendedMode.NORMALIZED);

        dNavigator.setMinimizable(true);
        dNavigator.setLocation(CLocation.base().minimalEast());
        dNavigator.setExtendedMode(ExtendedMode.NORMALIZED);

        dSettings.setMinimizable(true);
        dSettings.setLocation(CLocation.base().minimalSouth());
        dSettings.setExtendedMode(ExtendedMode.NORMALIZED);

        dSystem.setMinimizable(true);
        dSystem.setLocation(CLocation.base().minimalSouth());
        dSystem.setExtendedMode(ExtendedMode.NORMALIZED);

        dConsole.setMinimizable(true);
        dConsole.setMaximizable(true);
        dConsole.setLocation(CLocation.base().minimalSouth());
        dConsole.setExtendedMode(ExtendedMode.NORMALIZED);

        dEditor.setMaximizable(true);

        setVisible(true);

        System.out.println("Welcome to CRT");
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
