package scenes;

import gameEngine.Automata;
import gameEngine.rendering.Scene;
import overlays.CraftingOverlay;
import overlays.InventoryOverlay;
import overlays.OverlayBase;
import world.Item;
import world.Player;
import world.Region;
import world.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene implements MouseListener, MouseMotionListener {
    public static float MOVE_SPEED = 500;
    private List<OverlayBase> overlays;
    private boolean overlayShown = true;
//    public Item inHand = Item.EMPTY;
    private Player player;
    private boolean drawInHand = true;

    public int cursorX = 0, cursorY = 0;
    public GameScene(){
        addMouseListener(this);
        addMouseMotionListener(this);
        this.overlays = new ArrayList<>();
//        overlays.add(new InventoryOverlay(this));
//        overlays.add(new CraftingOverlay(this));
        setLayout(null);
        for (Scene inventoryOverlay : overlays) {
////            inventoryOverlay.setVisible(false);
//            System.out.println("inventoryOverlay = " + inventoryOverlay);
            add(inventoryOverlay);
        }
        Thread t = new Thread(this::initOverlays);
        t.start();
        this.player = Automata.INSTANCE.getPlayer();
    }
    
    private void initOverlays(){
        while (getBounds().getWidth() <= 0 || getBounds().getHeight() <= 0){}
        int startX = 50;
        int startY = 50;
        for (int i = 0; i < overlays.size(); i++) {
            Scene scene = overlays.get(i);
            if (startX+scene.getWidth() > getWidth()){
                startX = 50;
                startY += scene.getHeight()+50;
            }
            scene.setLocation(startX, startY);
            startX += scene.getWidth()+50;
        }
    }

    public void makeOverlayOnTop(OverlayBase overlayBase){
        for (OverlayBase base : overlays) {
            remove(base);
        }
        List<OverlayBase> newOverlays = new ArrayList<>();
        newOverlays.add(overlayBase);
        for (OverlayBase base : overlays) {
            if (base != overlayBase){
                newOverlays.add(base);
            }
        }
        overlays = newOverlays;

        for (OverlayBase base : overlays) {
            add(base);
        }
    }

    public void switchOverlayVisibility(OverlayBase base){
        for (OverlayBase overlay : overlays) {
            if (overlay == base){
                if (overlay.isVisible()){
                    System.out.println("switching off");
                    System.out.println("overlay = " + overlay);
                    remove(overlay);
                    overlay.setVisible(false);
                } else {
                    System.out.println("switching on");
                    System.out.println("overlay = " + overlay);
                    overlay.setVisible(true);
                    add(overlay);
                }
                return;
            }
        }
        setVisible(true);
    }

    public List<OverlayBase> getOverlays() {
        return overlays;
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Automata.INSTANCE.getMap().renderMap(g);
        if (player.getMiningCounter() > 0){
            g.setColor(Color.WHITE);
            g.drawRect(getWidth()/4, getHeight()-50, getWidth()/2, 20);
            g.fillRect(getWidth()/4+2, getHeight()-48,  ((getWidth()/2-4)*player.getMiningCounter())/(player.MINING_TIME * Automata.UPS), 17);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.setMousePressed(true);
        for (Region renderedRegion : Automata.INSTANCE.getMap().getRenderedRegions()) {
            for (Tile[] tiles : renderedRegion.getTiles()) {
                for (Tile tile : tiles) {
                    if (tile.getPolygon().contains(e.getPoint())) {
                        player.setClickedTile(tile);
//                        player.setInHand(tile.onClick(player.getInHand()));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        player.setMousePressed(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        drawInHand = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        drawInHand = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.cursorX = e.getX();
        this.cursorY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.cursorX = e.getX();
        this.cursorY = e.getY();
        for (Region renderedRegion : Automata.INSTANCE.getMap().getRenderedRegions()) {
            for (Tile[] tiles : renderedRegion.getTiles()) {
                for (Tile tile : tiles) {
                    if (tile.getPolygon().contains(e.getPoint())) {
                        if (Automata.INSTANCE.getPlayer().getHoverTile() != tile)
                            Automata.INSTANCE.getPlayer().setHoverTile(tile);
                        return;
                    }
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }
}
