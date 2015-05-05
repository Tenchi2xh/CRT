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
package net.team2xh.crt.gui.liveview;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class BufferPanel extends JPanel implements ComponentListener {

    private FrameBuffer buffer;
    private Canvas canvas;

    public BufferPanel() {
        buffer = new FrameBuffer(320, 180, FrameBuffer.SAMPLINGMODE_GL_AA_4X);
        buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
        canvas = buffer.enableGLCanvasRenderer();

        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        addComponentListener(this);
        setMinimumSize(new Dimension(160, 90));
    }

    public FrameBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(FrameBuffer buffer) {
        this.buffer = buffer;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int w = getWidth();
        int h = getHeight();
        buffer.resize(w, h);
        
        SwingUtilities.invokeLater(() -> { canvas.update(canvas.getGraphics()); });
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
