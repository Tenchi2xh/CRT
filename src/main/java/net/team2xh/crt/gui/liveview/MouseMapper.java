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
import org.lwjgl.input.Mouse;

/**
 * Taken from jPCT tutorial
 * 
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class MouseMapper {

    private boolean hidden = false;

    private int height = 0;

    public MouseMapper(int height) {
        init();
    }

    public void hide() {
        if (!hidden) {
            Mouse.setGrabbed(true);
            hidden = true;
        }
    }

    public void show() {
        if (hidden) {
            Mouse.setGrabbed(false);
            hidden = false;
        }
    }

    public boolean isVisible() {
        return !hidden;
    }

    public void destroy() {
        show();
        if (Mouse.isCreated()) {
            Mouse.destroy();
        }
    }

    public boolean buttonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    public int getMouseX() {
        return Mouse.getX();
    }

    public int getMouseY() {
        return height - Mouse.getY();
    }

    public int getDeltaX() {
        if (Mouse.isGrabbed()) {
            return Mouse.getDX();
        } else {
            return 0;
        }
    }

    public int getDeltaY() {
        if (Mouse.isGrabbed()) {
            return Mouse.getDY();
        } else {
            return 0;
        }
    }

    private void init() {
        try {
            if (!Mouse.isCreated()) {
                Mouse.create();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
