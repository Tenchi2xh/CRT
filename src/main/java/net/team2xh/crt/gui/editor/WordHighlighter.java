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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
class WordHighlighter extends LayeredHighlighter.LayerPainter {
    
    private final Color color;

    //protected Color color = Theme.WHITE;
    
    public WordHighlighter(Color color) {
        this.color = color;
    }
    
    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
        // Do nothing: this method will never be called
    }

    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {

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
        int h = alloc.height - fm.getDescent() + 2;
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.drawRoundRect(alloc.x - 3, alloc.y, alloc.width + 3, h + 2, 6, 6);

        return alloc;
    }

}