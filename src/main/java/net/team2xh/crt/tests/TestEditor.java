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
package net.team2xh.crt.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import net.team2xh.crt.gui.editor.Editor;
import net.team2xh.crt.gui.themes.DarkTheme;
import net.team2xh.crt.gui.themes.Theme;
import net.team2xh.crt.gui.util.GUIToolkit;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class TestEditor {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Editor test");

            frame.getContentPane().add(new Editor(), BorderLayout.CENTER);
            frame.setPreferredSize(new Dimension(500, 500));
            frame.pack();

            GUIToolkit.centerFrame(frame);

            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });

    }
}
