package world;

import gameEngine.Automata;
import gameEngine.registry.base.Deposit;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.tiles.MultiFacedTile;
import gameEngine.rendering.Texture;
import overlays.MachineOverlay;
import scenes.GameScene;
import utils.Face;
import utils.MultiFaceOrientation;
import utils.NetworkElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private final TileBase base;
    private Deposit deposit;
    private TileBase placed;
    private TileBase hover;
    private Storage storage;
    private MachineOverlay machineOverlay;
    private MultiFaceOrientation multiFaceDirection; // Used only for multifaced tiles like Pipes and Belts
    private Face orientation; // Used to determine the orientation of an arm
    private final int x;
    private final int y;
    public static int RENDER_SIZE = 50;
    private final List<Texture> additionalRender;
    private NetworkElement networkElement;
    public Tile(TileBase base, int x, int y){
        this.base = base;
        this.x = x;
        this.y = y;
        this.deposit = null;
        this.storage = null;
        this.additionalRender = new ArrayList<>();
        this.networkElement = null;
    }


    public Tile(TileBase base, int x, int y, Deposit deposit){
        this(base, x, y);
        this.deposit = deposit;
    }

    private boolean isStraight(boolean[] elements){
        return (elements[0] && elements[3]) || (elements[1] && elements[4]) || (elements[2] && elements[5]);
    }



    public boolean place(TileBase placed){
        if (this.placed != null)
            return false;
        this.placed = placed;
        this.storage = placed.getStorageCapability();

        UpdatableTile updatableTile = placed.getUpdatableCapability(this);
        if (updatableTile != null) {
            Automata.INSTANCE.getMap().addUpdatableTile(updatableTile);
        }

        if (placed instanceof TileOverlay tileOverlay){
            this.machineOverlay = tileOverlay.GenerateOverlay(this);
        }

//        if (placed.isNetworkable()){
//            List<Network> networks = Network.getNearbyNetworks(this);
//            Network network;
//            if (networks.size() == 0){
//                network = new Network();
//                network.addElement(this);
//            } else if (networks.size() == 1) {
//                network = networks.get(0);
//                network.addElement(this);
//            } else {
//                network = networks.get(0);
//                network.mergeNetworks(networks);
//                network.addElement(this);
//            }
////            System.out.println("Went to network id="+network.getNetworkId());
//        }
//        updateMultiFacedTexture();
        return true;
    }

    public void render(Graphics g){
        int renderX = x * RENDER_SIZE + (y%2==0?0:RENDER_SIZE/2) + (int)Player.POS_X;
        int renderY = y * (int) (RENDER_SIZE * 0.75) + (int) Player.POS_Y;
        if (renderX >= -RENDER_SIZE && renderY > -RENDER_SIZE && renderX < Automata.INSTANCE.getScene().getWidth() && renderY < Automata.INSTANCE.getScene().getHeight()) {
            g.drawImage(base.getTexture().getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
            if (deposit != null) {
                g.drawImage(deposit.getTexture().getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
            }
            if (placed != null){
                if (placed instanceof MultiFacedTile multiFacedTile && multiFaceDirection != null){
                    for (Texture texture : this.additionalRender) {
                        g.drawImage(texture.getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
                    }
                    g.drawImage(multiFacedTile.getTexture(multiFaceDirection).getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
                } else {
                    g.drawImage(placed.getTexture().getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
                }
            } else if (hover != null){
                if (hover instanceof MultiFacedTile multiFacedTile && multiFaceDirection != null) {
                    g.drawImage(multiFacedTile.getTexture(multiFaceDirection).getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
                } else {
                    g.drawImage(hover.getTexture().getImage(), renderX, renderY, RENDER_SIZE, RENDER_SIZE, null);
                }
            }
        }
        if (networkElement != null){
            Item item = networkElement.getInternalItem();
            if (item != Item.EMPTY) {
                int progression = networkElement.getInternalItemProgression();
                if (multiFaceDirection == MultiFaceOrientation.HORIZONTAL) {
                    g.drawImage(item.getBase().getTexture().getImage(), renderX + ((progression * (RENDER_SIZE - RENDER_SIZE / 3)) / 100), renderY + (RENDER_SIZE / 4), Item.BELT_SIZE, Item.BELT_SIZE, null);
                } else{
                    int normalizedProgression = ((progression * (RENDER_SIZE-(RENDER_SIZE/4))) / 100);
                    int xProgression = (int) (Math.cos(0.86602540378)*normalizedProgression);
                    int yProgression = (int) (Math.sin(0.86602540378)*normalizedProgression);
                    g.drawImage(item.getBase().getTexture().getImage(), renderX + xProgression, renderY +yProgression, Item.BELT_SIZE, Item.BELT_SIZE, null);
                }
            }
        }
        g.setColor(Color.RED);
//        if (placed != null)
//            g.drawString(x+", "+y, renderX+RENDER_SIZE/4, renderY+RENDER_SIZE/2);
    }

    public Item onClick(Item inHand){
        // debug info
//        System.out.println("Tile info:");
//        System.out.println("    base: "+base.getRegistryName());
//        if (deposit != null)
//            System.out.println("    deposit: "+deposit.getRegistryName());
//        if (placed != null)
//            System.out.println("    placed: "+placed.getRegistryName());
//        if (storage != null) {
//            System.out.println("    storage: " + storage.toString());
//        }

        if (machineOverlay != null){
            if (Automata.INSTANCE.getScene() instanceof GameScene gameScene){
                machineOverlay.setVisible(true);
                gameScene.add(machineOverlay);
            }
        }
        else if (inHand != Item.EMPTY){
            TileBase tile = inHand.getTileBase();
            if (tile != null && placed == null) {
                if (place(tile)){
                    if (inHand.decrease(1)){
                        inHand = Item.EMPTY;
                    }
                }
            }
        } else {
            if (deposit != null){

            }
        }
        return inHand;
    }

    public Item onClick(Item inHand, Face orientation){
        this.orientation = orientation;
        return onClick(inHand);
    }

    public void setHover(TileBase hover) {
        if (placed == null) {
            this.hover = hover;
            multiFaceDirection = MultiFaceOrientation.JUNCTION;
        }
    }

    public void setHover(TileBase hover, Face orientation){
        if (placed == null) {
            this.hover = hover;
            multiFaceDirection = MultiFaceOrientation.JUNCTION;
            this.orientation = orientation;
        }
    }

    public void setMultiFaceDirection(MultiFaceOrientation multiFaceDirection) {
        this.multiFaceDirection = multiFaceDirection;
    }

    public void setNetworkElement(NetworkElement networkElement) {
        this.networkElement = networkElement;
    }

    public void setOrientation(Face orientation) {
        this.orientation = orientation;
    }

    public Face getOrientation() {
        return orientation;
    }

    public TileBase getBase() {
        return base;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public TileBase getPlaced() {
        return placed;
    }

    public Storage getStorage() {
        return storage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Texture> getAdditionalRender() {
        return additionalRender;
    }

    public Polygon getPolygon() {
        Polygon points = new Polygon();
        int renderX = x * RENDER_SIZE + (y%2==0?0:RENDER_SIZE/2) + (int)Player.POS_X;
        int renderY = y * (int) (RENDER_SIZE * 0.75) + (int) Player.POS_Y;
        points.addPoint(renderX + RENDER_SIZE/2, renderY);
        points.addPoint(renderX + RENDER_SIZE, renderY + (int) (RENDER_SIZE*0.25));
        points.addPoint(renderX + RENDER_SIZE, renderY + (int) (RENDER_SIZE*0.75));
        points.addPoint(renderX + RENDER_SIZE/2, renderY + RENDER_SIZE);
        points.addPoint(renderX, renderY + (int) (RENDER_SIZE*0.75));
        points.addPoint(renderX, renderY + (int) (RENDER_SIZE*0.25));
        return points;
    }

    public MultiFaceOrientation getMultiFaceDirection() {
        return multiFaceDirection;
    }

    public MachineOverlay getMachineOverlay() {
        return machineOverlay;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
