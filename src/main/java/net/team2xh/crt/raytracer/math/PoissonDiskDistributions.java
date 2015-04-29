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
package net.team2xh.crt.raytracer.math;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class PoissonDiskDistributions {

    private final static Random rand = new Random();

    public static double cR = 0.0;

    @SuppressWarnings("unchecked")
    public static List<double[]> randomSquarePoints(int points) {
        // minDist calculated to make all points fit 2x2 square
        // Square has area 4.0
        // so the sum of all circles inside must be equal to 4
        // N * pi*r^2 = 4
        // r = sqrt(4/(N*pi))
        // factor is for gap compensation
        double minDist = 1.52*Math.sqrt(4.0 / (points*Math.PI));
        cR = minDist;
        double cellSize = minDist / 1.4142135623730950488016887242097; // sqrt(2)
        int gridWidth  = (int)(2.0 / cellSize) + 1;

        List<double[]> activeList = new LinkedList<>();
        List<double[]> pointList = new LinkedList<>();
        List<double[]>[][] grid = new List[gridWidth][gridWidth];

        for (int i = 0; i < gridWidth; ++i)
            for (int j = 0; j < gridWidth; ++j)
                grid[i][j] = new LinkedList<>();

        // Add first point
        double xr = (rand.nextDouble()*2) - 1;
        double yr = (rand.nextDouble()*2) - 1;
        // Find index
        int i = (int)((xr + 1) / cellSize);
        int j = (int)((yr + 1) / cellSize);
        // Place in grid
        double[] p = {xr, yr};
        grid[i][j].add(p);
        activeList.add(p);
        pointList.add(p);

        while (!activeList.isEmpty() && (pointList.size() < points)) { // check if points is ok instead of max_points
            int listIndex = rand.nextInt(activeList.size());
            double[] point = activeList.get(listIndex);

            boolean found = false;
            for (int k = 0; k < points; ++k) {
                // add next point
                boolean f = false;
                // Generate point around point
                double d = rand.nextDouble();
                double radius = (minDist + minDist * d);
                d = rand.nextDouble();

                double angle = 2 * Math.PI * d;
                double newX = point[0] + radius * Math.sin(angle);
                double newY = point[1] + radius * Math.cos(angle);
                double[] q = {newX, newY};

                if (newX >= -1 && newX < 1 && newY > -1 && newY < 1) {

                    i = (int)((newX + 1) / cellSize);
                    j = (int)((newY + 1) / cellSize);
                    boolean tooClose = false;
                    for (int ii = Math.max(0, i-2); ii < Math.min(gridWidth, i+3) && !tooClose; ii++) {
                        for (int jj = Math.max(0, j-2); jj < Math.min(gridWidth, j+3) && !tooClose; jj++) {
                            for (double[] gp : grid[ii][jj]) {
                                if (Math.sqrt((gp[0]-newX)*(gp[0]-newX)+(gp[1]-newY)*(gp[1]-newY)) < minDist)
                                    tooClose = true;
                            }
                        }
                    }
                    if (!tooClose) {
                        f = true;
                        activeList.add(q);
                        pointList.add(q);
                        grid[i][j].add(q);
                    }
                }
                found |= f;
            }
            if (!found)
                activeList.remove(listIndex);
        }

        return pointList;
    }

    @SuppressWarnings("unchecked")
    public static List<double[]> randomCirclePoints(int points) {
        // minDist calculated to make all points fit 2x2 square
        // Circle has area pi
        // so the sum of all circle areas inside must be equal to pi
        // N * pi*r^2 = pi
        // r = sqrt(pi/(N*pi))
        // factor is for gap compensation
        double minDist = 1.52*Math.sqrt(Math.PI / (points*Math.PI));
        cR = minDist;
        double cellSize = minDist / 1.4142135623730950488016887242097; // sqrt(2)
        int gridWidth  = (int)(2.0 / cellSize) + 1;

        List<double[]> activeList = new LinkedList<>();
        List<double[]> pointList = new LinkedList<>();
        List<double[]>[][] grid = new List[gridWidth][gridWidth];

        for (int i = 0; i < gridWidth; ++i)
            for (int j = 0; j < gridWidth; ++j)
                grid[i][j] = new LinkedList<>();

        // Add first point
        double[] p = UniformDistributions.randomCirclePoint();
        // Find index
        int i = (int)((p[0] + 1) / cellSize);
        int j = (int)((p[1] + 1) / cellSize);
        // Place in grid
        grid[i][j].add(p);
        activeList.add(p);
        pointList.add(p);

        while (!activeList.isEmpty() && (pointList.size() < points)) { // check if points is ok instead of max_points
            int listIndex = rand.nextInt(activeList.size());
            double[] point = activeList.get(listIndex);

            boolean found = false;
            for (int k = 0; k < points; ++k) {
                // add next point
                boolean f = false;
                // Generate point around point
                double d = rand.nextDouble();
                double radius = (minDist + minDist * d);
                d = rand.nextDouble();

                double angle = 2 * Math.PI * d;
                double newX = point[0] + radius * Math.sin(angle);
                double newY = point[1] + radius * Math.cos(angle);
                double[] q = {newX, newY};

                // if point is in unit circle
                if (newX*newX + newY*newY <= 1.0) {

                    i = (int)((newX + 1) / cellSize);
                    j = (int)((newY + 1) / cellSize);
                    boolean tooClose = false;
                    for (int ii = Math.max(0, i-2); ii < Math.min(gridWidth, i+3) && !tooClose; ii++) {
                        for (int jj = Math.max(0, j-2); jj < Math.min(gridWidth, j+3) && !tooClose; jj++) {
                            for (double[] gp : grid[ii][jj]) {
                                if (Math.sqrt((gp[0]-newX)*(gp[0]-newX)+(gp[1]-newY)*(gp[1]-newY)) < minDist)
                                    tooClose = true;
                            }
                        }
                    }
                    if (!tooClose) {
                        f = true;
                        activeList.add(q);
                        pointList.add(q);
                        grid[i][j].add(q);
                    }
                }
                found |= f;
            }
            if (!found)
                activeList.remove(listIndex);
        }

        return pointList;
    }

}