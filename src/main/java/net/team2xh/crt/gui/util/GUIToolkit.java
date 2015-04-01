/*
 * Copyright (C) 2015 Hamza Haiken (hamza.haiken@heig-vd.ch)
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
package net.team2xh.crt.gui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.io.IOUtils;
import org.pushingpixels.substance.api.SubstanceConstants.SubstanceWidgetType;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;

/**
 * Utility class for missing Swing features.
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
final public class GUIToolkit {

    private GUIToolkit() { }

    /**
     * Centers a frame on screen
     *
     * @param frame Frame to center
     */
    public static void centerFrame(java.awt.Window frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getWidth()) / 2,
                (screen.height - frame.getHeight()) / 2);
    }

    /**
     * Register a local font to the graphical environment.
     *
     * After registering a font, its font family can be used normally as if
     * the font was installed on the OS.
     *
     * @param path Font file path
     */
    public static void registerFont(String path) {
        try {
            URI uri = ClassLoader.getSystemResource(path).toURI();
            File fontFile = new File(uri);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException | IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Defines the default font for Swing.
     *
     * All Swing elements instantiated after this method call will
     * use the provided font name and size.
     *
     * @param name Name of the font family
     * @param size Font size
     */
    public static void setDefaultFont(String name, int size) {
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        Font font = new Font(name, Font.PLAIN, size);
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            // Replace all instances of font resources
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }

    /**
     * Adds a frame with a graph of the heap to a window (Substance only)
     *
     * @param frame Frame to be decorated.
     */
    public static void enableHeapStatus(JFrame frame) {
        SubstanceLookAndFeel.setWidgetVisible(frame.getRootPane(),
                true, SubstanceWidgetType.TITLE_PANE_HEAP_STATUS);
    }

    /**
     * Provides a BufferedImage in the format most compatible with current graphics card.
     *
     * @param  w Width of the image
     * @param  h Height of the image
     * @return   Optimized BufferedImage
     */
    public static BufferedImage getEfficientBuffer(int w, int h) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        return config.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
    }

    /**
     * Activeate Substance Look&Feel.
     *
     * Must be called before opening any window.
     */
    public static void setSubstanceLAF() {
        try {
            LookAndFeel laf = new SubstanceGraphiteLookAndFeel();
            UIManager.setLookAndFeel(laf);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GUIToolkit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Converts a "\n" separated string in a format accepted by JLabel.
     *
     * @param  orig Normally formatted string
     * @return      HTML formatted string
     */
    public static String convertToMultiline(String orig) {
        return "<html>" + orig.replaceAll("\n", "<br />") + "</html>";
    }

    /**
     * Displays a dialog with "OK" button.
     *
     * @param parent  Parent window
     * @param message Message (can contain "\n" characters)
     * @param title   Dialog title
     * @param type    Icon type (use JOptionPane static values)
     */
    public static void showOkDialog(java.awt.Window parent, String message, String title, int type) {
        SwingUtilities.invokeLater(() -> {
            Object[] options = {"OK"};
            JLabel msg = new JLabel(convertToMultiline(message));
            Dimension sz = msg.getPreferredSize();
            msg.setPreferredSize(new Dimension(sz.width + 20, sz.height));
            JOptionPane.showOptionDialog(parent, msg, title, JOptionPane.DEFAULT_OPTION,
                                         type, null, options, options[0]);
        });
    }

    /**
     * Provides a ImageIcon from a file name.
     *
     * @param path Picture file name
     * @return     ImageIcon
     */
    public static ImageIcon getIcon(String path) {
        Image img = null;
        try {
            img = ImageIO.read(ClassLoader.getSystemResource(path));
        } catch (IOException ex) {
            Logger.getLogger(GUIToolkit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ImageIcon(img);
    }

    /**
     * Initialises default GUI parameters and fonts.
     */
    public static void initGUI() {
        try {
            // Import custom fonts
            GUIToolkit.registerFont("res/fonts/SOURCESANSPRO-REGULAR.TTF");
            GUIToolkit.registerFont("res/fonts/ENVYCODER.TTF");
            GUIToolkit.registerFont("res/fonts/ENVYCODERITALIC.TTF");
            GUIToolkit.registerFont("res/fonts/ENVYCODERBOLD.TTF");
            // Set default dialog font
            GUIToolkit.setDefaultFont("Source Sans Pro", 16);
            // Enable Substance Look&Feel
            GUIToolkit.setSubstanceLAF();

            // Substance forgot to put a border for JTextArea,
            // So we give it the same border that TextField has
            Object border = UIManager.get("TextField.border");
            UIManager.put("TextArea.border", border);
            // UIManager.put("InternalFrame.icon", MainWindow.icon);
            // UIManager.put("ProgressBar.background", Dark.DARKBLACK);

        } catch (Exception e) {
            Logger.getLogger(GUIToolkit.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static File getFile(String path) throws IOException {
        InputStream in = GUIToolkit.class.getClassLoader().getResourceAsStream(path);
        final File tempFile = File.createTempFile(path, ".tmp");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }
}