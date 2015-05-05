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
package net.team2xh.crt.gui.liveview.converters;

import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.util.SkyBox;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.team2xh.crt.raytracer.Background;
import net.team2xh.crt.raytracer.Camera;
import net.team2xh.crt.raytracer.Scene;
import net.team2xh.crt.raytracer.Tracer;
import net.team2xh.crt.raytracer.math.Vector3;

/**
 *
 * @author Hamza Haiken <tenchi@team2xh.net>
 */
final public class BackgroundConverter {

    final private static Object sync = new Object();
    private static BufferedImage temp;

    final private static int SIZE = 256;

    final private static String prefix = "skybox-";
    final private static String[] names = new String[]{"left", "front", "right", "back", "top", "bottom"};

    private BackgroundConverter() {
    }

    public static SkyBox convertBackground(Background background) {

        Camera[] cameras = new Camera[]{
            new Camera(Vector3.O, Vector3.Xm, 90),
            new Camera(Vector3.O, Vector3.Z, 90),
            new Camera(Vector3.O, Vector3.X, 90),
            new Camera(Vector3.O, Vector3.Zm, 90),
            new Camera(Vector3.O, Vector3.Y, 90),
            new Camera(Vector3.O, Vector3.Ym, 90)
        };

        Scene scene = Scene.createScene(SIZE, SIZE, cameras[0]);
        scene.setBackground(background);

        for (int i = 0; i < 6; ++i) {
            scene.setCamera(cameras[i]);
            Tracer.getInstance().parallelRender(1, (int[][] picture, Integer pass) -> draw(picture, pass), scene);
            try {
                synchronized (sync) {
                    sync.wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(BackgroundConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            TextureManager.getInstance().addTexture(prefix + names[i], new Texture(temp));
        }

        return new SkyBox(prefix + names[0],
                          prefix + names[1],
                          prefix + names[2],
                          prefix + names[3],
                          prefix + names[4],
                          prefix + names[5], 1000f);
    }

    private static void draw(int[][] picture, int pass) {
        assert (pass == 0);
        temp = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < SIZE; x += 1) {
            for (int y = 0; y < SIZE; y += 1) {
                temp.setRGB(x, y, picture[x][y]);
            }
        }
        synchronized (sync) {
            sync.notify();
        }
    }

}
