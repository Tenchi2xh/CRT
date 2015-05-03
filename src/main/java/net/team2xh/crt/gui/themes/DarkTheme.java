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
package net.team2xh.crt.gui.themes;

/**
 * Dark theme
 * 
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class DarkTheme extends Theme {
    
    DarkTheme() {
        super("#1a1a1a", // 01 Black
              "#222222", // 02 Light black
              "#2a2a2a", // 03 Dark Gray
              "#333333", // 04 Gray
              "#969696", // 05 Light Gray
              "#66d9ef", // 06 Blue
              "#8fe22b", // 07 Green
              "#fd971f", // 08 Orange
              "#be84ff", // 09 Purple
              "#f92672", // 10 Red
              "#f8f8f2", // 11 White
              "#e6db74", // 12 Yellow
              "#5e3535", // 13 Error
              "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel"
        );
    }
}
