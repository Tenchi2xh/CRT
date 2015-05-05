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
package net.team2xh.crt.gui.graphs;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.team2xh.crt.gui.util.SystemInformations;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class SystemPanel extends JPanel {
    
    private CPUGraph cpu = new CPUGraph();
    private RAMGraph ram = new RAMGraph(SystemInformations.totalMemory());
    
    public SystemPanel() {
        setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 10));
        setLayout(new GridLayout(1, 2, 10, 0));
        add(cpu);
        add(ram);
        
        (new Thread() {
            @Override
            public void run() {
                while (true) {
                    double process = SystemInformations.processLoad();
                    double system = SystemInformations.systemLoad();
                    long free = SystemInformations.freeMemory();
                    long total = SystemInformations.totalMemory();
                    long proc = SystemInformations.processTotalMemory();
                    long procfree = SystemInformations.processFreeMemory();
                    cpu.update(process, system);
                    ram.update(proc-procfree, proc, total-free);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) { }
                }
            }
        }).start();
    }
}
