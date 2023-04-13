package gameEngine.rendering;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Texture {
    public static int ID_COUNT = 0;
    private Image texture;
    private int id;

    private int originalWidth;
    private int originalHeight;

    private Texture(){

    }

    public Texture(String path, int renderSize){
        try {
            BufferedImage resizedImage = new BufferedImage(renderSize, renderSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            BufferedImage temp = ImageIO.read(new File(path));
            this.originalWidth = temp.getWidth();
            this.originalHeight = temp.getHeight();
            g.drawImage(temp, 0, 0, renderSize, renderSize, null);
            g.dispose();
            this.texture = resizedImage;
            id = ID_COUNT++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Texture(String path, int width, int height){
        try {
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            BufferedImage temp = ImageIO.read(new File(path));
            this.originalWidth = temp.getWidth();
            this.originalHeight = temp.getHeight();
            g.drawImage(temp, 0, 0, width, height, null);
            g.dispose();
            this.texture = resizedImage;
            id = ID_COUNT++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void rotate(int deg){
        BufferedImage texture = (BufferedImage) this.texture;
        final double rads = Math.toRadians(deg);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = ((BufferedImage)this.texture).getWidth();
        final int h = ((BufferedImage)this.texture).getHeight();
        final BufferedImage rotatedImage = new BufferedImage(w, h, texture.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-texture.getWidth() / 2, -texture.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(texture,rotatedImage);
        this.texture = rotatedImage;
    }

    public Texture reverse(){
        return copy(180);
    }

    public Image getImage() {
        return texture;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public int getId() {
        return id;
    }

    public Texture copy(int rotation){
        Texture copy = new Texture();
        copy.texture = deepCopy((BufferedImage) this.texture);
        copy.originalWidth = this.originalWidth;
        copy.originalHeight = this.originalHeight;
        copy.id = ID_COUNT++;
        copy.rotate(rotation);
        return copy;
    }

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
