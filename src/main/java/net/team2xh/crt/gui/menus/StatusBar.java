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

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import net.team2xh.crt.gui.editor.EditorTextPane;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class StatusBar extends JPanel {

    private JLabel lineLabel = new StatusLabel("Line: 0");
    private JLabel columnLabel = new StatusLabel("Column: 0");
    private JProgressBar progressBar = new JProgressBar();

    public StatusBar(EditorTextPane editor) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(true);
        progressBar.setString("Ready");

        add(progressBar);

        add(lineLabel);

        add(columnLabel);

        editor.addCaretListener((CaretEvent ce) -> {
            try {
                int pos = ce.getDot();
                int line = editor.getLineOfOffset(pos);
                int column = pos - editor.getLineStartOffset(line);
                lineLabel.setText("Line: " + (line + 1));
                columnLabel.setText("Column: " + (column + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    class StatusLabel extends JLabel {

        StatusLabel(String s) {
            super(s);
            int h = getPreferredSize().height;
            setPreferredSize(new Dimension(100, h));
        }
    }
}
