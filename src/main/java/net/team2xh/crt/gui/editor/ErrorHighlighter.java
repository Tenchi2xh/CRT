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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
class ErrorHighlighter extends LayeredHighlighter.LayerPainter {

    final private Color color; // The color for the underline

    public ErrorHighlighter(Color color) {
        this.color = color;
    }
    
    @Override
    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
        // Do nothing: this method will never be called
    }

    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
        g.setColor(color);

        Rectangle alloc = null;
        if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
            if (bounds instanceof Rectangle) {
                alloc = (Rectangle) bounds;
            } else {
                alloc = bounds.getBounds();
            }
        } else {
            try {
                Shape shape = view.modelToView(offs0,
                        Position.Bias.Forward, offs1,
                        Position.Bias.Backward, bounds);
                alloc = (shape instanceof Rectangle) ? (Rectangle) shape
                        : shape.getBounds();
            } catch (BadLocationException e) {
                return null;
            }
        }

        FontMetrics fm = c.getFontMetrics(c.getFont());
        int baseline = alloc.y + alloc.height - fm.getDescent() + 1;
        g.drawLine(alloc.x, baseline, alloc.x + alloc.width, baseline);
        // g.drawLine(alloc.x, baseline + 1, alloc.x + alloc.width / 4, baseline + 0);
        // g.drawLine(alloc.x + alloc.width / 4, baseline + 0, alloc.x + alloc.width / 2, baseline + 2);
        // g.drawLine(alloc.x + 3 * alloc.width / 4, baseline + 2, alloc.x + alloc.width , baseline + 1);

        return alloc;
    }

}
