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
 * Light theme
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class LightTheme extends Theme {

    // TODO: Copy greybeans theme
    public LightTheme() {
        super("#eaeaea", // 01 White
              "#f0f0f0", // 02 Very light gray
              "#eaeaea", // 03 Light gray
              "#e0e0e0", // 04 Gray
              "#555555", // 05 Dark gray
              "#66d9ef", // 06 Blue
              "#8fe22b", // 07 Green
              "#fd971f", // 08 Orange
              "#be84ff", // 09 Purple
              "#f92672", // 10 Red
              "#222222", // 11 Black
              "#e6db74", // 12 Yellow
              "#eb8888", // 13 Error
              "org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel"
        );
    }
}
