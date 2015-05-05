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
package net.team2xh.crt.gui.util;

import java.lang.management.ManagementFactory;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.openide.util.Exceptions;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */

/**
 * Written with help from:
 *  - http://stackoverflow.com/a/21962037
 *  - http://stackoverflow.com/a/15402012
 */
final public class SystemInformations {

    final private static MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    final private static Runtime runtime = Runtime.getRuntime();

    public static double processLoad() {
        Double value  = (Double)getAttributeValue("ProcessCpuLoad");
        return (value == null || value == -1.0) ? 0.0 : value;
    }

    public static double systemLoad() {
        Double value  = (Double)getAttributeValue("SystemCpuLoad");
        return (value == null || value == -1.0) ? 0.0 : value;
    }

    public static long freeMemory() {
        Long value = (Long)getAttributeValue("FreePhysicalMemorySize");
        return (value == null) ? 0 : value;
    }

    public static long totalMemory() {
        Long value = (Long)getAttributeValue("TotalPhysicalMemorySize");
        return (value == null) ? 0 : value;
    }

    public static long processTotalMemory() {
        return runtime.totalMemory();
    }

    public static long processFreeMemory() {
        return runtime.freeMemory();
    }

    public static int cores() {
        Integer value = (Integer)getAttributeValue("AvailableProcessors");
        return (value == null) ? 0 : value;
    }


    private static Object getAttributeValue(String attribute) {
        try {
            MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
            ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList list = mbs.getAttributes(name, new String[] {attribute});

            Attribute att = (Attribute)list.get(0);
            return att.getValue();
        } catch (MalformedObjectNameException | NullPointerException | InstanceNotFoundException | ReflectionException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }

    public static String summary() {
        return "Process load: " + processLoad() + "\n"
              +"System load: " + systemLoad() + "\n"
              +"Free memory: " + (freeMemory() / (1024*1024)) + "\n"
              +"Total memory: " + (totalMemory() / (1024*1024)) + "\n"
              +"Heap memory: " + (processTotalMemory() / (1024*1024)) + "\n"
              +"Free heap memory: " + (processFreeMemory() / (1024*1024)) + "\n"
              +"------------------------";
    }

}