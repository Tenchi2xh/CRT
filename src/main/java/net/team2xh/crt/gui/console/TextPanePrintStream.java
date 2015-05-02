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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import org.openide.util.Exceptions;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class TextPanePrintStream extends PrintStream {

    private final JTextPane textPane;
    private final SimpleAttributeSet style;

    private final SimpleDateFormat sdf;

    public TextPanePrintStream(JTextPane textPane, SimpleAttributeSet style) {
        super(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        });
        this.textPane = textPane;
        this.style = style;
        this.sdf = new SimpleDateFormat("(hh:mm:ss) ");
    }

    @Override
    public void println(String string) {
        output(string + "\n");
    }

    @Override
    public void print(String string) {
        output(string);
    }

    private void output(String string) {
        String timestamp = sdf.format(new Date());
        try {
            Document doc = textPane.getDocument();
            textPane.getDocument().insertString(doc.getLength(), timestamp + string, style);
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
