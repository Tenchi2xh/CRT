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
package net.team2xh.crt.gui.menus;

import javax.swing.JToolBar;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class ToolBar extends JToolBar {
    
    public ToolBar() {
        super(JToolBar.HORIZONTAL);
        
        add(Actions.newProject);
        add(Actions.open);
        add(Actions.save);
        
        addSeparator();
        
        add(Actions.render);
        add(Actions.preview);
        add(Actions.export);
        
        addSeparator();
        
        add(Actions.perspCode);
        add(Actions.perspLive);
        add(Actions.fullscreen);
        
        addSeparator();
        
        add(Actions.settings);
        add(Actions.about);
    }
}
