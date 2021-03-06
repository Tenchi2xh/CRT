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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.team2xh.crt.gui.editor.Editor;
import net.team2xh.crt.gui.editor.EditorTextPane;
import net.team2xh.crt.gui.entities.EntityTree;
import net.team2xh.crt.gui.graphs.SystemPanel;
import net.team2xh.crt.gui.liveview.LiveViewPanel;
import net.team2xh.crt.gui.liveview.TestScene;
import net.team2xh.crt.gui.menus.StatusBar;
import net.team2xh.crt.gui.menus.ToolBar;
import net.team2xh.crt.gui.util.GUIToolkit;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Tracer;


/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MainWindow extends JFrame {

    public static final MainWindow instance = new MainWindow();
    
    private final CControl control;

    private final Editor editor;
    private final EntityTree navigator;
    private final RenderPanel render;
    private final AnimationPanel animator;
    private final SystemPanel system;
    private final ConsolePanel console;
    private final StatusBar statusbar;
    private LiveViewPanel liveview;
    
    public static MainWindow getInstance() {
        return instance;
    }
    
    private final DefaultCDockable dEditor;
    private final DefaultCDockable dNavigator;
    private final DefaultCDockable dRender;
    private final DefaultCDockable dAnimator;
    private final DefaultCDockable dSystem;
    private final DefaultCDockable dConsole;
    private final DefaultCDockable dLiveview;
    
    private MainWindow() {

        this.control = new CControl(this);

        setTitle("CRT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));

        EclipseTheme etheme = new EclipseTheme();
        etheme.setMovingImageFactory(new ScreencaptureMovingImageFactory(new Dimension(500, 500)));
        control.setTheme(new CEclipseTheme(control, etheme));
        control.putProperty(EclipseTheme.TAB_PAINTER, RectGradientPainter.FACTORY);
        control.putProperty(EclipseTheme.PAINT_ICONS_WHEN_DESELECTED, true);

        DockController controller = control.getController();
        controller.getThemeManager().setBorderModifier("dock.border.stack.eclipse.content", (Border) -> BorderFactory.createEmptyBorder());
        controller.getThemeManager().setBorderModifier("dock.border.title.eclipse.button.flat", (Border) -> BorderFactory.createLineBorder(UIManager.getColor("Button.border"), 1));
        
        editor = new Editor();
        navigator = new EntityTree();
        render = new RenderPanel();
        animator = new AnimationPanel(editor.getEditor());
        system = new SystemPanel();
        console = new ConsolePanel();
        liveview = new LiveViewPanel(new TestScene(), this);
        
        CContentArea contentArea = control.getContentArea();
        ToolBar toolbar = new ToolBar();
        statusbar = new StatusBar(editor.getEditor());
        Tracer.getInstance().setProgressBar(statusbar.getProgressBar());
        
        add(toolbar, BorderLayout.PAGE_START);
        add(contentArea, BorderLayout.CENTER);
        add(statusbar, BorderLayout.PAGE_END);


        dEditor = (DefaultCDockable) create("Script", editor);
        dNavigator = (DefaultCDockable) create("Navigator", navigator);
        dRender = (DefaultCDockable) create("Render", render);
        dAnimator = (DefaultCDockable) create("Animation", animator);
        dSystem = (DefaultCDockable) create("System", system);
        dConsole = (DefaultCDockable) create("Console", console);
        dLiveview = (DefaultCDockable) create("Live view", liveview);
        
        dEditor.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/script.png")));
        dNavigator.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/navigator.png")));
        dRender.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/renderer.png")));
        dAnimator.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/play.png")));
        dSystem.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/system.png")));
        dConsole.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/console.png")));
        dLiveview.setTitleIcon(new ImageIcon(GUIToolkit.getIcon("/icons/persp_live.png")));

        double wr = 0.4;
        double we = 0.4;
        double wn = 1 - wr - we;
        double wi = 0.4;

        double h1 = 0.7;
        double h2 = 1 - h1;

        CGrid grid = new CGrid(control);
        grid.add(0, 0, wr, h1, dRender, dLiveview, dAnimator);
        grid.add(wr, 0, we, h1, dEditor);
        grid.add(wr + we, 0, wn, h1, dNavigator);
        grid.add(0, h1, wr + we, h2, dConsole);
        grid.add(1 - wi, h1, wi, h2, dSystem);
        grid.addHorizontalDivider(0, 1, h1);
        contentArea.deploy(grid);

        dRender.setExternalizable(true);
        dRender.setMaximizable(true);
        dRender.setMinimizable(true);
        dRender.setLocation(CLocation.base().minimalWest());
        dRender.setExtendedMode(ExtendedMode.NORMALIZED);

        dAnimator.setExternalizable(true);
        dAnimator.setMaximizable(true);
        dAnimator.setMinimizable(true);
        dAnimator.setLocation(CLocation.base().minimalWest());
        dAnimator.setExtendedMode(ExtendedMode.NORMALIZED);
        
        dLiveview.setMaximizable(true);
        dLiveview.setMinimizable(true);
        dLiveview.setLocation(CLocation.base().minimalWest());
        dLiveview.setExtendedMode(ExtendedMode.NORMALIZED);

        dNavigator.setMinimizable(true);
        dNavigator.setLocation(CLocation.base().minimalEast());
        dNavigator.setExtendedMode(ExtendedMode.NORMALIZED);

        dSystem.setMinimizable(true);
        dSystem.setLocation(CLocation.base().minimalSouth());
        dSystem.setExtendedMode(ExtendedMode.NORMALIZED);

        dConsole.setMinimizable(true);
        dConsole.setMaximizable(true);
        dConsole.setLocation(CLocation.base().minimalSouth());
        dConsole.setExtendedMode(ExtendedMode.NORMALIZED);

        dEditor.setMaximizable(true);
       
        dNavigator.setExtendedMode(ExtendedMode.MINIMIZED);
        dRender.toFront();
        
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

    public EditorTextPane getEditor() {
        return editor.getEditor();
    }

    public RenderPanel getRenderer() {
        return render;
    }

    public StatusBar getStatusBar() {
        return statusbar;
    }
    
    public LiveViewPanel getLiveViewPanel() {
        return liveview;
    }

    public void updateLiveView(Scene scene) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel("Initializing...", SwingConstants.CENTER);
        p.add(l, BorderLayout.CENTER);
        
        liveview.stop();
        dLiveview.remove(liveview);
        dLiveview.add(p);
        dLiveview.getContentPane().revalidate();
        dLiveview.getContentPane().repaint();
        
        SwingUtilities.invokeLater(() -> {
            liveview = new LiveViewPanel(scene, this);
            liveview.repaint();
            dLiveview.remove(p);
            dLiveview.add(liveview);
            dLiveview.getContentPane().revalidate();
            dLiveview.getContentPane().repaint();
        });

    }
}
