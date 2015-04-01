/*
 * Copyright (C) 2015 Hamza Haiken (hamza.haiken@heig-vd.ch)
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

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Matrix4 {

    double matrix[][] = new double[4][4];

    public Matrix4(double[][] matrix) {
        if (matrix.length != 4)
            throw new RuntimeException("Matrix size is not 4x4");
        for (double[] line : matrix)
            if (line.length != 4)
                throw new RuntimeException("Matrix size is not 4x4");
        this.matrix = matrix;
    }

    public Matrix4() {
        for (int i = 0; i < 4; ++i)
            matrix[i][i] = 1.0;
    }

    public Matrix4(Vector3 u, Vector3 v, Vector3 w) {
        matrix = new double[][] {
            { u.x, u.y, u.z, 0.0 },
            { v.x, v.y, v.z, 0.0 },
            { w.x, w.y, w.z, 0.0 },
            { 0.0, 0.0, 0.0, 1.0 }
        };
    }

    public Matrix4 setRow(int row, Vector3 data) {
        matrix[row][0] = data.x;
        matrix[row][1] = data.y;
        matrix[row][2] = data.z;
        return this;
    }

    public Matrix4 setColumn(int column, Vector3 data) {
        matrix[0][column] = data.x;
        matrix[1][column] = data.y;
        matrix[2][column] = data.z;
        return this;
    }

    public Matrix4 multiply(Matrix4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                for (int k = 0; k < 4; ++k)
                    result[i][j] += matrix[i][k] * other.matrix[k][j];
        return new Matrix4(result);
    }

    private Vector3 multiply(Vector3 other, double lastValue) {
        double[] vector = new double[] { other.x, other.y, other.z, lastValue };
        double[] result = new double[4];
        for (int i = 0; i < 4; ++i)
            for (int j = 0; j < 4; ++j)
                result[i] += (matrix[i][j] * vector[j]);
        return new Vector3(result[0], result[1], result[2]);
    }

    public Vector3 rotate(Vector3 other) {
        return multiply(other, 0.0);
    }

    public Vector3 translate(Vector3 other) {
        return multiply(other, 1.0);
    }

    public void print() {
        String result = "";
        for (double[] column : matrix) {
            result += "| ";
            for (double d : column)
                result += String.format("%9.6f ", d);
            result += "|\n";
        }
        System.out.println(result);
    }
}