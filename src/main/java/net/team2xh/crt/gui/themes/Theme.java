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

import java.awt.Color;

/**
 * Holds all the colors for a theme and its associated Look&Feel class name.
 * 
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
abstract public class Theme {
    
    final public Color COLOR_01;
    final public Color COLOR_02;
    final public Color COLOR_03;
    final public Color COLOR_04;
    final public Color COLOR_05;
    final public Color COLOR_06;
    final public Color COLOR_07;
    final public Color COLOR_08;
    final public Color COLOR_09;
    final public Color COLOR_10;
    final public Color COLOR_11;
    final public Color COLOR_12;
    final public Color COLOR_13;
    
    final public String LAF;

    public Theme(String color01, String color02, String color03, String color04, String color05,
                 String color06, String color07, String color08, String color09, String color10,
                 String color11, String color12, String color13, String laf) {
        this.COLOR_01 = c(color01);
        this.COLOR_02 = c(color02);
        this.COLOR_03 = c(color03);
        this.COLOR_04 = c(color04);
        this.COLOR_05 = c(color05);
        this.COLOR_06 = c(color06);
        this.COLOR_07 = c(color07);
        this.COLOR_08 = c(color08);
        this.COLOR_09 = c(color09);
        this.COLOR_10 = c(color10);
        this.COLOR_11 = c(color11);
        this.COLOR_12 = c(color12);
        this.COLOR_13 = c(color13);
        
        this.LAF = laf;
    }
    
    private Color c(String hex) {
        return Color.decode(hex);
    }
    
}