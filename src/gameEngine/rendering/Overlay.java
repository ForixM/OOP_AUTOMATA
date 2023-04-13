package gameEngine.rendering;

import gameEngine.Automata;

import javax.swing.*;
import java.awt.*;

public abstract class Overlay extends JPanel {
    protected Texture background;
    protected String overlayName;

    public Overlay(String overlayName, int width, int height){
        this.background = new Texture(Automata.TEXTURE_PATH+"overlay/"+overlayName+".png", width, height);
        this.overlayName = overlayName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, null);
    }

    public Texture getBackgroundTexture() {
        return background;
    }

    public String getOverlayName() {
        return overlayName;
    }
}
