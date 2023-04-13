package scenes;

import gameEngine.Automata;
import gameEngine.rendering.Scene;
import org.w3c.dom.css.RGBColor;
import utils.OpenSimplex2S;
import utils.PerlinNoise2D;
import utils.Voronoi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PerlinScene extends Scene {
    private int x = 0;
    private Voronoi voronoi;
    BufferedImage img = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
    int max = 0, min = 255;

    private static final long SEED = 0;
    private static final int WIDTH = 512;
    private static final int HEIGHT = 512;
    private static final double FREQUENCY = 1.0 / 20.0;

    public PerlinScene(){
        PerlinNoise2D.WIDTH = 2000;
        PerlinNoise2D.HEIGHT = 2000;
//        img = PerlinNoise2D.getNoiseImage(5, 0, 0, 5);

//        try {


            BufferedImage temp = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            /*
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                    int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                    Color c = new Color(rgb);
                    if (c.getRed() < 240)
                        rgb = 0;
                    temp.setRGB(x, y, rgb);
                }
            }
            */

        // TESTS FOR VORONOI

        Voronoi coal = new Voronoi(0.12, 1, false, 643872423);
        Voronoi iron = new Voronoi(0.15, 1, false, 784913434);
        Voronoi copper = new Voronoi(0.2, 1, false, 312313242);
        Voronoi stone = new Voronoi(0.25, 1, false, 876543234);
        Voronoi petrol = new Voronoi(0.4, 1, false, 12345654);
        Voronoi gold = new Voronoi(0.5, 1, false, 11234543);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = coal.get(x, y, 1);
                int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() < 240) {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
                else {
                    c = Color.darkGray;
                    temp.setRGB(x, y, c.getRGB());
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = iron.get(x, y, 1);
                int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() <= 5) {
                    c = Color.white;
                    temp.setRGB(x, y, c.getRGB());
                }
                else {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = copper.get(x, y, 1);
                int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() <= 4) {
                    c = Color.orange;
                    temp.setRGB(x, y, c.getRGB());
                }
                else {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = stone.get(x, y, 1);
                int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() <= 4) {
                    c = Color.gray;
                    temp.setRGB(x, y, c.getRGB());
                }
                else {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = petrol.get(x, y, 1);
                int rgb = 0x884ea0  * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() <= 2) {
                    c = Color.pink;
                    temp.setRGB(x, y, c.getRGB());
                }
                else {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
            }
        }
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                //double value = OpenSimplex2S.noise2(SEED, x * FREQUENCY, y * FREQUENCY);
                double value = gold.get(x, y, 1);
                int rgb = 0x010101 * (int) ((value + 1) * 127.5);
                Color c = new Color(rgb);
                if (c.getRed() == 0) {
                    c = Color.yellow;
                    temp.setRGB(x, y, c.getRGB());
                }
                else {
                    rgb = 0;
                    //temp.setRGB(x, y, rgb);
                }
            }
        }
//            ImageIO.write(img, "png", new File("noise.png"));
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(img, 0, 0, 2500, 2500, null);
        g.drawImage(img, 0, 0, 600, 600, this);
//        g.drawImage(PerlinNoise2D.getNoiseImage(0, 100, 0, 1), 600, 0, 600, 600, this);
//        x+=110* Engine.DELTA;
//        System.out.println(x);
    }
}
