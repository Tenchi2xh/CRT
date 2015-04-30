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
package net.team2xh.crt;

import java.awt.Color;
import java.beans.Introspector;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import net.team2xh.crt.tests.TestEntityProperties;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Launcher {

    public static void main(String[] args) {
        try {
            
            // Set BeanInfo and Editors paths
            Introspector.setBeanInfoSearchPath(new String[]{"net.team2xh.crt.gui.beans.infos"});
            PropertyEditorManager.setEditorSearchPath(new String[]{"net.team2xh.crt.gui.beans.editors"});
            
            // Instead of forcing user to launch jarfile with -Djava.library.path=..., set with hack
            System.setProperty("java.library.path", "lib/natives/");
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
            
            TestEntityProperties.main(args);
        } catch (Exception ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
