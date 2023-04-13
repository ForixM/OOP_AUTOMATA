package overlays;

import gameEngine.rendering.Scene;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import world.Item;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.List;

// Parent class for all overlays (for machines and player inventories)
public class OverlayBase extends Scene implements MouseListener, MouseMotionListener {
    // game scene of AUTOMATA
    protected GameScene main;
    // for drag and drop and managing overlays
    private boolean drag = false;
    // used for drag and drop
    private Point lastPoint = null;
    // used to know if it is necessary to draw the texture of an item taken in hand
    private boolean drawInHand = false;
    // cursor location
    protected int cursorX = 0, cursorY = 0;
    // close button for overlays
    protected Rectangle closeButton;
    // name of the overlay
    protected String name;

    // constructor
    public OverlayBase(GameScene main, String name){
        this.main = main;
        this.name = name;
        // add event listeners for mouse
        addMouseListener(this);
        addMouseMotionListener(this);
        // real coordinates for the close button (in the original pngs)
        closeButton = new Rectangle(3590, 0, 250, 250);
    }

    // rescale slots from original size to compressed one
    protected static void rescaleSlots(List<Rectangle> slots, int width, int height, Texture bg){
        for (Rectangle slot : slots) {
            slot.x = rescaleHorizontalValue(slot.x, width, bg.getOriginalWidth());
            slot.y = rescaleVerticalValue(slot.y, height, bg.getOriginalHeight());
            slot.width  = rescaleHorizontalValue(slot.width, width, bg.getOriginalWidth());
            slot.height = rescaleVerticalValue(slot.height, height, bg.getOriginalHeight());
        }
    }

    // used in rescaleSlots to resize horizontally the png
    private static int rescaleHorizontalValue(int value, int width, int originalWidth){
        return (width*value)/originalWidth;
    }
    // used in rescaleSlots to resize vertically the png
    private static int rescaleVerticalValue(int value, int height, int originalHeight){
        return (height*value)/originalHeight;
    }


    // display image of each item from the inventory
    @Override
    protected void paintComponent(Graphics g) {
        if (main.getPlayer().inHand != Item.EMPTY && drawInHand){
            g.drawImage(main.getPlayer().inHand.getBase().getTexture().getImage(), cursorX-(Item.TAKEN_SIZE/2), cursorY-(Item.TAKEN_SIZE/2), Item.TAKEN_SIZE, Item.TAKEN_SIZE, null);
        }
    }

    // detect mouse click events
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // detect mouse press events
    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = e.getLocationOnScreen();
        if (e.getY() <= 30){
            if (!drag){
                main.makeOverlayOnTop(this);
            }
            drag = true;
        }
        if (closeButton.contains(e.getPoint())){
            if (main.getOverlays().contains(this)){
                main.getOverlays().remove(this);
            }
            main.remove(this);
        }
    }

    // detect mouse release events
    @Override
    public void mouseReleased(MouseEvent e) {
        drag = false;
    }

    // detect mouse enter events
    @Override
    public void mouseEntered(MouseEvent e) {
        drawInHand = true;
        main.getPlayer().setHoverTile(null);
    }

    // detect mouse exit events
    @Override
    public void mouseExited(MouseEvent e) {
        drawInHand = false;
    }

    // detect mouse drag events
    @Override
    public void mouseDragged(MouseEvent e) {
        if (drag) {
            Point point = e.getLocationOnScreen();
            int offsetX = point.x - lastPoint.x;
            int offsetY = point.y - lastPoint.y;
            Rectangle bounds = getBounds();
            bounds.x += offsetX;
            bounds.y += offsetY;
            setBounds(bounds);
            lastPoint = point;
        }
        this.cursorX = e.getX();
        this.cursorY = e.getY();
    }

    // detect mouse move events
    @Override
    public void mouseMoved(MouseEvent e) {
        this.cursorX = e.getX();
        this.cursorY = e.getY();
    }

    // toStrong override
    @Override
    public String toString() {
        return name;
    }
}
