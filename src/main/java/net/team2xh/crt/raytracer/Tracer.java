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
package net.team2xh.crt.raytracer;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiConsumer;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.CIRCLE;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.GAUSSIAN;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.HEXAGON;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.LINE;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.PENTAGON;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.SQUARE;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.TRIANGLE;
import static net.team2xh.crt.raytracer.Camera.ApertureShape.UNIFORM;
import net.team2xh.crt.raytracer.entities.Entity;
import net.team2xh.crt.raytracer.math.UniformDistributions;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken (hamza.haiken@heig-vd.ch)
 */
public class Tracer {

    private Settings settings;
    private Scene scene;

    private JProgressBar pb = null;
    private int counter = 0;
    private double total = 0;

    private Random rand = new Random(1337);

    public Tracer(Scene scene) {
        this.settings = scene.getSettings();
        this.scene = scene;
    }

    private Pigment trace(Ray ray, int depth, double totalDist) {

        // Base color is black
        Pigment pigment = new Pigment(0.0);

        double minDist = -1;
        Hit closest = null;

        // Search for closest intersecting entity on the ray
        for (Entity e : scene.getEntities()) {
            Hit h = e.intersect(ray);
            if (h.intersects()) {
                double dist = h.distance();
                if (minDist == -1 || dist < minDist) {
                    minDist = dist;
                    closest = h;
                }
            }
        }
        totalDist += minDist;
        if (closest != null) {
            Entity c = closest.entity();
            Material m = c.material;
            Vector3 n = closest.normal();

            for (Light l : scene.getLights()) {

                boolean isInShadow = false;

                Ray shadowRay = Ray.between(closest.point(), l.origin);
                double lightDist = closest.point().distanceTo(l.origin);

                // Find out if point is in shadow
                for (Entity e : scene.getEntities()) {
                    Hit h2 = e.intersect(shadowRay);
                    if (h2.intersects() && closest.point().distanceTo(h2.point()) < lightDist) {
                        isInShadow = true;
                        break;
                    }
                }

                Pigment  color    = m.color;
                Pigment  light    = l.color;

                // Inverse square law
                double isl = 1.0;
                if (l.falloff > 0)
                    isl = l.falloff / ((lightDist+totalDist)*(lightDist+totalDist));

                // Add ambient component
                if (l.ambient > 0)
                    pigment.addSelf(
                        light.mul(l.ambient).mul(1.0 - m.reflectivity)
                    );


                if (!isInShadow) {
                    // Add diffuse component
                    if (m.diffuse > 0) {
                        // Phong attenuation
                        double att = n.dot(shadowRay.direction);

                        Pigment lightContr = light.mul(att * isl);
                        Pigment materialContr = color.mul(m.diffuse);
                        pigment.addSelf(materialContr.mul(lightContr));
                    }

                    // Add specular component
                    if (m.specular > 0) {
                        Vector3 refl = shadowRay.direction.reflect(n);
                        double angle = Math.max(refl.dot(ray.direction), 0);
                        double factor = Math.pow(angle, m.shininess);
                        pigment.addSelf(light.mul(color).mul(factor*isl));
                    }
                }
            }
            // Add reflection component
            if (m.reflectivity > 0 && depth > 0) {
                Vector3 reflection = ray.direction.reflect(n);
                Ray reflectionRay = new Ray(reflection, closest.point());
                Pigment reflectionColor = trace(reflectionRay, depth - 1, totalDist);
                pigment.addSelf(reflectionColor.mul(m.reflectivity));
            }
        } else {
            pigment = scene.getBackground().getPoint(ray);
        }

        return pigment;
    }

    public void setProgressBar(JProgressBar pb) {
        this.pb = pb;
    }

    private void processPixel(int[] coords, int[][] image) {
        int supersampling = settings.supersampling;
        int x = coords[0];
        int y = coords[1];
        if (supersampling > 1) {
            Pigment[][] samples = new Pigment[supersampling][supersampling];

            for (int i = 0; i < supersampling; ++i) {
                double dx = (1.0 * i / supersampling) - 0.5;
                for (int j = 0; j < supersampling; ++j) {
                    double dy = (1.0 * j / supersampling) - 0.5;

                    samples[i][j] = traceCoords(x + dx, y + dy);
                    // Ray ray = getPrimaryRay(x + dx, y + dy);
                    // samples[i][j] = trace(ray, settings.recurDepth, 0);
                }
            }
            image[x][y] = Pigment.getAverage(samples).rgb(settings.showClip);
        } else {
            image[x][y] = traceCoords(x, y).rgb(settings.showClip);
            // Ray ray = getPrimaryRay(x, y);
            // image[x][y] = trace(ray, settings.recurDepth, 0).rgb();
        }
        if (pb != null)
            incrementProgressBar();
    }

    private synchronized void incrementProgressBar() {
        SwingUtilities.invokeLater(() -> pb.setValue((int)(++counter * 100 / total)));
    }

    public int[][] render(int passes, BiConsumer<int[][], Integer> drawer) {

        boolean[][] done = new boolean[settings.width][settings.height];

        ArrayList<ArrayList<int[]>> coords = new ArrayList<>();
        for (int i = passes - 1; i >= 0; --i) {
            int step = (int) Math.pow(2, i);
            ArrayList<int[]> l = new ArrayList<>();
            for (int x = 0; x < settings.width; x += step) {
                for (int y = 0; y < settings.height; y += step) {
                    if (!done[x][y]) {
                        l.add(new int[]{x, y});
                        done[x][y] = true;
                    }
                }
            }
            coords.add(l);
        }

        total = settings.width*settings.height;
        counter = 0;
        int[][] image = new int[settings.width][settings.height];

        // SwingUtilities.invokeLater(() -> pb.setValue(0));
        long start = System.currentTimeMillis();
        for (int i = 0; i < passes; ++i) {
            final int j = i+1;
            // SwingUtilities.invokeLater(() -> pb.setString("Pass " + j + "/" + passes));
            coords.get(i).parallelStream().forEach((int[] c) -> processPixel(c, image));
            drawer.accept(image, passes - i - 1);
        }
        long end = System.currentTimeMillis();

        // SwingUtilities.invokeLater(() -> pb.setString(String.format("%.3fs", (end - start) / 1000.0)));
        // SwingUtilities.invokeLater(() -> pb.setValue(0));

        return image;
    }

    private Pigment traceCoords(double x, double y) {

        // TODO: option to toggle camera distance ISL

        // Normalized coordinates from -1 to 1
        double nX = (2*((x + 0.5) / settings.width) - 1);
        double nY = (1 - 2*((y + 0.5) / settings.height));

        Camera camera = scene.getCamera();
        double vfov = camera.getVerticalFov();
        double focalDistance = camera.getFocalDistance();

        double camX = 0, camY = 0, camZ = 0;

        switch (settings.projection) {
            case PINHOLE:
                // Normal pinhole
                camX = nX * settings.fovFactor * settings.ratio;
                camY = nY * settings.fovFactor;
                camZ = -1;
                break;
            case SPHERICAL:
                // Spherical mapping
                camX = Math.cos((vfov/2) * settings.ratio * nX - 0.5*Math.PI);
                camY = Math.sin((vfov/2) * nY);
                camZ = Math.sin((vfov/2) * settings.ratio * nX - 0.5*Math.PI);
                break;
            case CYLINDRICAL:
                camX = Math.cos((vfov/2) * settings.ratio * nX - 0.5*Math.PI);
                camY = nY * settings.fovFactor;
                camZ = Math.sin((vfov/2) * settings.ratio * nX - 0.5*Math.PI);
                break;
        }

        Vector3 direction = new Vector3(camX, camY, camZ).multiply(focalDistance);
        Vector3 origin = camera.getPosition();
        direction = camera.getMatrix().rotate(direction);
        // Vector3 originCam = settings.camera.getMatrix().multiply(origin);
        // if (x == (settings.width/2) && y == (settings.height/2)) {
        //     System.out.println("cam O: " + pos);
        // }

        // pos = settings.camera.getMatrix().multiply(pos);
        // if (x == (settings.width/2) && y == (settings.height/2)) {
        //     System.out.println("cam N: " + pos);
        //     settings.camera.getMatrix().print();
        // }


        // Fisheye #1 from http://paulbourke.net/dome/fisheye
            // double r = Math.atan2(Math.sqrt(nX*nX+nY*nY), 1) / Math.PI;
            // double phi = Math.atan2(nY, nX);
            // double camX = r * Math.cos(phi) * 2 * settings.ratio;
            // double camY = r * Math.sin(phi) * 2;
            // Vector3 pos = new Vector3(camX, camY, -1);

        // Fisheye #2 from Stack Overflow
            // double z = Math.sqrt(1.0 - nX*nX - nY*nY);
            // double a = 10. / (z * Math.tan(Math.PI * 0.5 * 0.5));
            // double camX = nX * a;
            // double camY = nY * a;
            // Vector3 pos = new Vector3(camX, camY, z);

        Pigment pigment;

        if (settings.dofSamples > 1) {

            int n = settings.dofSamples;
            Pigment[] samples = new Pigment[n];

            // Point on the aperture hole from where the ray will go
            double[] p = new double[2];

            // java.util.List<double[]> points = PoissonDiskDistributions.randomCirclePoints(n);
            // Pigment[] samples = new Pigment[points.size()];
            // for (int i = 0; i < points.size(); ++i) {
            //     double[] p = points.get(i);
            for (int i = 0; i < n; ++i) {
                switch (camera.getShape()) {
                    case GAUSSIAN:
                        p[0] = rand.nextGaussian();
                        p[1] = rand.nextGaussian();
                        break;
                    case UNIFORM:
                        p[0] = rand.nextDouble()*2.0 - 1.0;
                        p[1] = rand.nextDouble()*2.0 - 1.0;
                        break;
                    case CIRCLE:   p = UniformDistributions.randomCirclePoint(); break;
                    case HEXAGON:  p = UniformDistributions.randomPolygonPoint(6); break;
                    case PENTAGON: p = UniformDistributions.randomPolygonPoint(5); break;
                    case SQUARE:   p = UniformDistributions.randomPolygonPoint(4); break;
                    case TRIANGLE: p = UniformDistributions.randomPolygonPoint(3); break;
                    case LINE:     p = UniformDistributions.randomPolygonPoint(2); break;
                }

                double focX = p[0] * camera.getAperture() * (1.0 / settings.width);
                double focY = p[1] * camera.getAperture() * (1.0 / settings.height);

                Vector3 aperturePoint = camera.getMatrix().rotate(new Vector3(focX, focY, 0));
                Vector3 newDirection = direction.add(aperturePoint);
                Vector3 newOrigin = origin.subtract(aperturePoint);

                samples[i] = trace(new Ray(newDirection, newOrigin), settings.recurDepth, 0);
            }
            pigment = Pigment.getAverage(samples);
        }
        else {
            pigment = trace(new Ray(direction, origin), settings.recurDepth, 0);
        }

        return pigment;
    }

    public Settings getSettings() {
        return settings;
    }
}