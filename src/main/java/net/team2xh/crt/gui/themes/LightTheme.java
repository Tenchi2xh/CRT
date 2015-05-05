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

import org.pushingpixels.substance.api.SubstanceLookAndFeel;

/**
 * Light theme
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class LightTheme extends Theme {

    // TODO: Copy greybeans theme
    LightTheme() {
        super("#e9eef2", // 01 Margin
              "#f5faff", // 02 Background
              "#9da5ab", // 03 Line numbers background
              "#d8dde1", // 04 Margin line
              "#ffffff", // 05 Line numbers
              "#00a0bd", // 06 Keywords (blue)
              "#8fe22b", // 07 Green
              "#fd971f", // 08 Orange
              "#ec3933", // 09 Numbers
              "#00a0bd", // 10 Operators
              "#222222", // 11 Black
              "#fc35e4", // 12 Strings
              "#eb8888", // 13 Error
              "#f7eed0", // 14 Current line highlight
              "#aeb7bd", // 15 Line numbers highlight background
              "#9da5ab", // 16 Comments
              "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"
        );

    }
}
