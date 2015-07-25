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
package net.team2xh.crt.raytracer;

import net.team2xh.crt.raytracer.lights.Light;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import net.team2xh.crt.gui.util.SystemInformations;
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
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
public class Tracer {

    public final static double E = 0.0000000001;
    
    private JProgressBar pb = null;
    private int counter = 0;
    private double total = 0;

    private final Random rand = new Random(1337);

    private static final Tracer instance = new Tracer();

    private Tracer() { }

    public static Tracer getInstance() {
        return instance;
    }

    private Pigment trace(Ray ray, int depth, double totalDist, Scene scene) {

        // Base color is black
        Pigment pigment = new Pigment(0.0);

        double minDist = -1;
        Hit closest = null;

        // Search for closest intersecting entity on the ray
        for (Entity e : scene.getEntities()) {
            Hit h = e.intersect(ray);
            if (h.intersects()) {
                double dist = h.entry();
                if (minDist == -1 || dist < minDist) {
                    minDist = dist;
                    closest = h;
                }
            }
        }
        totalDist += minDist;
        if (closest != null) {
            
            // Point should not be exactly on the intersection surface but just a bit before
            double distance = ray.origin.distanceTo(closest.point()) - E;
            Vector3 point = ray.origin.add(ray.direction.multiply(distance));
            
            Material m = closest.entity().material;
            Vector3 n = closest.normal();

            for (Light l : scene.getLights()) {

                boolean isInShadow = false;

                Ray shadowRay = new Ray(l.getDirection(point), point);
                double lightDist = l.getDistance(point);

                // Find out if point is in shadow
                for (Entity e : scene.getEntities()) {
                    Hit h2 = e.intersect(shadowRay);
                    if (h2.intersects() && point.distanceTo(h2.point()) < lightDist) {
                        isInShadow = true;
                        break;
                    }
                }

                Pigment  color    = m.color;
                Pigment  light    = l.getPigment();

                // Inverse square law
                double isl = 1.0;
                if (l.getFalloff() > 0)
                    isl = l.getFalloff() / ((lightDist+totalDist)*(lightDist+totalDist));

                // Add ambient component
                if (l.getAmbient() > 0)
                    pigment.addSelf(
                        light.mul(l.getAmbient()).mul(1.0 - m.reflectivity)
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
                        pigment.addSelf(light.mul(color).mul(factor*isl*m.specular));
                    }
                }
            }
            
//            int k = scene.getSettings().aoSamples;
//            Pigment[] ambientOcclusion = new Pigment[k];
//            for (int i = 0; i < k; ++i) {
//                ambientOcclusion[i] = tracePath(ray, 2, scene);
//            }
//            pigment = pigment.mul(Pigment.getAverage(ambientOcclusion));
            
//            int k = scene.getSettings().aoSamples;
//            if (k > 0 && depth > 0) {
//                Pigment[] ambientOcclusion = new Pigment[k];
//                for (int i = 0; i < k; ++i) {
//
//                    Vector3 r = UniformDistributions.orientedRandomVectorInHemisphere(n);
//                    Ray newRay = new Ray(r, point);
//                    // BRDF
//                    double cos_theta = r.dot(n);
//                    Pigment brdf = m.color.mul(2 * cos_theta);
//                    Pigment reflected = trace(newRay, depth - 1, totalDist, scene);
//                    ambientOcclusion[i] = (m.color.add(brdf.mul(reflected)));
//
//                }
//                pigment = pigment.mul(Pigment.getAverage(ambientOcclusion));
//            }
            
            // Add reflection component
            if (m.reflectivity > 0 && depth > 0) {
                Vector3 reflection = ray.direction.reflect(n);
                Ray reflectionRay = new Ray(reflection, point);
                Pigment reflectionColor = trace(reflectionRay, depth - 1, totalDist, scene);
                pigment.addSelf(reflectionColor.mul(m.reflectivity));
            }
        } else {
            pigment = scene.getBackground().getPoint(ray);
        }

        return pigment;
    }

    private Pigment tracePath(Ray ray, int depth, Scene scene) {
        // Global illumination experiment
        if (depth > 0) {
            
            double minDist = -1;
            Hit closest = null;

            // Search for closest intersecting entity on the ray
            for (Entity e : scene.getEntities()) {
                Hit h = e.intersect(ray);
                if (h.intersects()) {
                    double dist = h.entry();
                    if (minDist == -1 || dist < minDist) {
                        minDist = dist;
                        closest = h;
                    }
                }
            }
            
            if (closest == null) {
                return new Pigment(0);
            }
            
            // Point should not be exactly on the intersection surface but just a bit before
            double distance = ray.origin.distanceTo(closest.point()) - E;
            Vector3 point = ray.origin.add(ray.direction.multiply(distance));
            Vector3 n = closest.normal();
            Material m = closest.entity().material;
            
            // http://en.wikipedia.org/wiki/Path_tracing except it IS cosine weighted
            Vector3 r = UniformDistributions.orientedRandomVectorInHemisphere(n);
            Ray newRay = new Ray(r, point);
            // BRDF
            double cos_theta = r.dot(n);
            Pigment brdf = m.color.mul(2 * cos_theta);
            Pigment reflected = tracePath(newRay, depth - 1, scene);
            return m.color.add(brdf.mul(reflected));

        } else {
            return new Pigment(0);
        }

    }
    
    public void setProgressBar(JProgressBar pb) {
        this.pb = pb;
    }

    private void processPixel(int[] coords, int[][] image, Scene scene) {
        Settings settings = scene.getSettings();

        int supersampling = settings.supersampling;
        int x = coords[0];
        int y = coords[1];
        if (supersampling > 1) {
            Pigment[][] samples = new Pigment[supersampling][supersampling];

            for (int i = 0; i < supersampling; ++i) {
                double dx = (1.0 * i / supersampling) - 0.5;
                for (int j = 0; j < supersampling; ++j) {
                    double dy = (1.0 * j / supersampling) - 0.5;

                    samples[i][j] = traceCoords(x + dx, y + dy, scene);
                    // Ray ray = getPrimaryRay(x + dx, y + dy);
                    // samples[i][j] = trace(ray, settings.recurDepth, 0);
                }
            }
            image[x][y] = Pigment.getAverage(samples).rgb(settings.showClip);
        } else {
            image[x][y] = traceCoords(x, y, scene).rgb(settings.showClip);
            // Ray ray = getPrimaryRay(x, y);
            // image[x][y] = trace(ray, settings.recurDepth, 0).rgb();
        }
        if (pb != null)
            incrementProgressBar();
    }

    private synchronized void incrementProgressBar() {
        SwingUtilities.invokeLater(() -> pb.setValue((int)(++counter * 100 / total)));
    }
    
    private ForkJoinPool pool;
    
    public ForkJoinPool parallelRender(int passes, BiConsumer<int[][], Integer> drawer, Runnable ender, Scene scene) {
        pool = new ForkJoinPool(SystemInformations.cores());
        pool.execute(() -> {
            render(passes, drawer, scene);
            ender.run();
        });
        return pool;
    }

    public void stop() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }
    
    public int[][] render(int passes, BiConsumer<int[][], Integer> drawer, Scene scene) {
        Settings settings = scene.getSettings();

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

        if (pb != null) SwingUtilities.invokeLater(() -> pb.setValue(0));
        long start = System.currentTimeMillis();
        for (int i = 0; i < passes; ++i) {
            final int j = i+1;
            if (pb != null) SwingUtilities.invokeLater(() -> pb.setString("Pass " + j + "/" + passes));
            coords.get(i).parallelStream().forEach((int[] c) -> processPixel(c, image, scene));
            drawer.accept(image, passes - i - 1);
        }
        long end = System.currentTimeMillis();

        if (pb != null) SwingUtilities.invokeLater(() -> pb.setString(String.format("%.3fs", (end - start) / 1000.0)));
        if (pb != null) SwingUtilities.invokeLater(() -> pb.setValue(0));

        return image;
    }

    private Pigment traceCoords(double x, double y, Scene scene) {

        Settings settings = scene.getSettings();

        // TODO: option to toggle camera distance ISL

        // Normalized coordinates from -1 to 1
        double nX = (2*((x + 0.5) / settings.width) - 1);
        double nY = (1 - 2*((y + 0.5) / settings.height));

        Camera camera = scene.getCamera();
        double vfov = camera.getVerticalFov();
        double hfov = 2 * Math.atan(settings.ratio * Math.tan(vfov / 2));
        double focalDistance = camera.getFocalDistance();
        Vector3 direction = camera.getDirection();
        Vector3 origin = camera.getPosition();

        double camX = 0, camY = 0;
        
        switch (settings.projection) {
            case PINHOLE:
                camX = nX * settings.fovFactor * settings.ratio;
                camY = nY * settings.fovFactor;
                break;
            case SPHERICAL:
                camX = Math.cos((vfov/2) * nX * settings.ratio - 0.5*Math.PI);
                camY = Math.sin((vfov/2) * nY);
                break;
            case CYLINDRICAL:
                camX = Math.cos((vfov/2) * nX * settings.ratio - 0.5*Math.PI);
                camY = nY * settings.fovFactor;
                break;
        }
        
        Vector3 rightComponent = camera.getRight().multiply(camX);
        Vector3 upComponent = camera.getUp().multiply(camY);
        direction = direction.add(rightComponent).add(upComponent).normalize();

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

                // TODO: Find new point position with direction from camera (rotate)
                
//                Vector3 aperturePoint = camera.getMatrix().rotate(new Vector3(focX, focY, 0));
//                Vector3 newDirection = direction.add(aperturePoint);
//                Vector3 newOrigin = origin.subtract(aperturePoint);

                // TODO diagrams
                rightComponent = camera.getRight().multiply(focX);
                upComponent = camera.getUp().multiply(focY);
                Vector3 aperturePoint = rightComponent.add(upComponent);
                Vector3 newOrigin = origin.subtract(aperturePoint);
                Vector3 newDirection = direction.multiply(focalDistance).add(aperturePoint).normalize();

                samples[i] = trace(new Ray(newDirection, newOrigin), settings.recurDepth, 0, scene);
            }
            pigment = Pigment.getAverage(samples);
        }
        else {
            pigment = trace(new Ray(direction, origin), settings.recurDepth, 0, scene);
        }

        return pigment;
    }

}