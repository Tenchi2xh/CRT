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

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import net.team2xh.crt.gui.util.GUIToolkit;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Action extends AbstractAction {

    private final Consumer<Action> function;
    private Object userObject;

    public Action(String icon, String name, String tooltip, Integer mnemonic, Consumer<Action> function) {
        this.function = function;
        init(icon, name, tooltip, mnemonic);
    }
    
    public Action(String icon, String name, String tooltip, Integer mnemonic, Consumer<Action> function, Object userObject) {
        this(icon, name, tooltip, mnemonic, function);
        this.userObject = userObject;
    }
    
    public void init(String icon, String name, String tooltip, Integer mnemonic) {
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, tooltip);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(SMALL_ICON, new ImageIcon(GUIToolkit.getIcon("/icons/" + icon + ".png")));
    }

    public Object getUserObject() {
        return userObject;
    }

    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        function.accept(this);
    }
}