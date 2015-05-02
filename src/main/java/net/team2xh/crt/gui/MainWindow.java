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

import bibliothek.extension.gui.dock.preference.PreferenceDialog;
import bibliothek.extension.gui.dock.preference.PreferenceModel;
import bibliothek.extension.gui.dock.preference.PreferenceTreeDialog;
import bibliothek.extension.gui.dock.preference.PreferenceTreeModel;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.common.CContentArea;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.CPreferenceModel;
import bibliothek.gui.dock.common.ColorMap;
import bibliothek.gui.dock.common.DefaultSingleCDockable;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.intern.DefaultCDockable;
import bibliothek.gui.dock.common.theme.ThemeMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
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
    private final JLabel render;
    private final JLabel system;
    private final JLabel settings;

    public MainWindow(Theme theme) {

        this.theme = theme;
        this.control = new CControl(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));

        ThemeMap themes = control.getThemes();
        themes.select(ThemeMap.KEY_ECLIPSE_THEME);

        DockController controller = control.getController();
//        controller.getThemeManager().setBorderModifier(null, null);

        PreferenceModel model = new CPreferenceModel(control);
        control.setPreferenceModel(model);
        Component owner = control.intern().getController().findRootWindow();
        if (model instanceof PreferenceTreeModel) {
            PreferenceTreeModel tree = (PreferenceTreeModel) model;
            PreferenceTreeDialog.openDialog(tree, owner);
        } else {
            PreferenceDialog.openDialog(model, owner);
        }

        CContentArea contentArea = control.getContentArea();
        add(contentArea, BorderLayout.CENTER);

        editor = new Editor(theme);
        navigator = new EntityTree();
        render = new JLabel("Render goes here");
        system = new JLabel("Infos go here");
        settings = new JLabel("Settings go here");

        DefaultCDockable dEditor = (DefaultCDockable) create("Script", editor);
        DefaultCDockable dNavigator = (DefaultCDockable) create("Navigator", navigator);
        DefaultCDockable dRender = (DefaultCDockable) create("Render", render);
        DefaultCDockable dSystem = (DefaultCDockable) create("System informations", system);
        DefaultCDockable dSettings = (DefaultCDockable) create("Settings", settings);

        dRender.setExternalizable(true);
        // TODO: see how to minimize on the left or right

        CGrid grid = new CGrid(control);
        grid.add(0, 0, 0.5, 0.75, dRender);
        grid.add(0.5, 0, 0.25, 0.75, dEditor);
        grid.add(0.75, 0, 0.25, 1, dNavigator);
        grid.add(0, 0.75, 0.375, 0.25, dSystem);
        grid.add(0.375, 0.75, 0.375, 0.25, dSettings);
        contentArea.deploy(grid);

        setVisible(true);
    }

    public void loadScene(Scene scene) {
        navigator.loadScene(scene);
    }

    private CDockable create(String title, JComponent component) {
        DefaultCDockable dockable = new DefaultSingleCDockable(title, title, component);
        dockable.setCloseable(false);
        dockable.setExternalizable(false);

        ColorMap map = dockable.getColors();
        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND, UIManager.getColor("TextArea.background", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND, UIManager.getColor("TextArea.background", null));
        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND, UIManager.getColor("TextArea.background", null));

        map.setColor(ColorMap.COLOR_KEY_TITLE_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_MINIMIZED_BUTTON_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground", null));
        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_FOCUSED, UIManager.getColor("TextArea.selectionBackground", null));
//        map.setColor(ColorMap.COLOR_KEY_TAB_BACKGROUND_SELECTED, UIManager.getColor("TextArea.selectionBackground", null));

        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND_FOCUSED, Color.RED);
        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND_SELECTED, Color.BLACK);
        map.setColor(ColorMap.COLOR_KEY_TAB_FOREGROUND, Color.ORANGE);
        map.setColor(ColorMap.COLOR_KEY_TITLE_FOREGROUND, Color.CYAN);
        map.setColor(ColorMap.COLOR_KEY_TITLE_FOREGROUND_FOCUSED, Color.PINK);

        return dockable;
    }

}
