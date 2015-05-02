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
package net.team2xh.crt.gui.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import net.team2xh.crt.gui.themes.LightTheme;
import net.team2xh.crt.gui.themes.Theme;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class ConsolePanel extends JPanel {

    private final Theme theme;
    private Color bg;
    private Color fg;

    private final JTextPane console;
    private final JScrollPane scrollpane;

    SimpleAttributeSet out = new SimpleAttributeSet();
    SimpleAttributeSet err = new SimpleAttributeSet();

    public ConsolePanel(Theme theme) {
        this.theme = theme;
        initColors();

        console = new JTextPane();
        console.setEditable(false);
        console.setBackground(bg);
        console.setOpaque(true);
        console.setFont(new Font("Envy Code R", Font.PLAIN, 14));

        setLayout(new BorderLayout());
        add(console, BorderLayout.CENTER);

        scrollpane = new JScrollPane();
        scrollpane.getVerticalScrollBar().setUnitIncrement(16);
        scrollpane.setViewportView(console);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollpane);

        console.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                scrollToBottom();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                scrollToBottom();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void scrollToBottom() {
                console.setCaretPosition(console.getDocument().getLength());
            }
        });

        StyleConstants.setForeground(out, fg);
        StyleConstants.setForeground(err, Color.RED);

        System.setOut(new TextPanePrintStream(console, out));
        System.setErr(new TextPanePrintStream(console, err));
    }

    private void initColors() {
        bg = Color.BLACK;
        fg = Color.WHITE;
        if (theme.getClass() == LightTheme.class) {
            bg = Color.WHITE;
            fg = Color.BLACK;
        }
    }

}
