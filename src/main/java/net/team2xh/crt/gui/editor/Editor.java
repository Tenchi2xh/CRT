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
package net.team2xh.crt.gui.editor;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.team2xh.crt.gui.themes.Theme;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Editor extends JScrollPane {

    private EditorTextPane textPane;

    public Editor(Theme theme) {
        
        // Trick for no text wrap with JTextPane
        JPanel nowrap = new JPanel(new BorderLayout());
        textPane = new EditorTextPane(theme);
        nowrap.add(textPane);
        EditorLineNumber ln = new EditorLineNumber(textPane, theme);

        // Faster scroll
        getVerticalScrollBar().setUnitIncrement(16);

        setViewportView(nowrap);
        setRowHeaderView(ln);
        setBorder(null);
    }

    public EditorTextPane getEditor() {
        return textPane;
    }
}
