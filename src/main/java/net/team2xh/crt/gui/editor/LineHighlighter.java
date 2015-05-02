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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.View;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
class LineHighlighter extends LayeredHighlighter.LayerPainter {

    protected Color color; // The color for the underline

    public LineHighlighter(Color color) {
        this.color = new Color(color.getRed(), color.getBlue(), color.getRed(), 178);

    }

    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
        // Do nothing: this method will never be called
    }

    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        try {
            g.setColor(color);
            Rectangle r = c.modelToView(offs0);
            // g.fillRect(0, r.y, c.getWidth(), r.height);
            g.fillRect(r.x, r.y, c.getWidth(), r.height);
        } catch (BadLocationException e) { }

        return bounds;
    }
}
