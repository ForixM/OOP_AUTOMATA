package utils;

import world.Item;
import world.Tile;

public class NetworkElement {
    private Tile tile;
    private Item internalItem;
    private int internalItemProgression;
    public NetworkElement(Tile tile){
        this.tile = tile;
        this.internalItem = Item.EMPTY;
        internalItemProgression = 0;
    }

    public NetworkElement(Tile tile, Item internalItem){
        this.tile = tile;
        this.internalItem = internalItem;
        internalItemProgression = 0;
    }

    public Tile getTile() {
        return tile;
    }

    public Item getInternalItem() {
        return internalItem;
    }

    public void setInternalItem(Item internalItem) {
        this.internalItem = internalItem;
    }

    public int getInternalItemProgression() {
        return internalItemProgression;
    }

    public boolean incrementInternalItemProgression(int incrementation){
        this.internalItemProgression += incrementation;
        if (internalItemProgression > 100){
            internalItemProgression = 100;
            return true;
        }
        return false;
    }
}
