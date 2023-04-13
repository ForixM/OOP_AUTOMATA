package world;

import gameEngine.registry.Registrable;
import gameEngine.registry.Registration;
import gameEngine.registry.base.ItemBase;
import gameEngine.registry.base.TileBase;

import java.awt.image.BufferedImage;

public class Item {
    private ItemBase base;
    private int count;
    public static int RENDER_SIZE = 500;
    public static int BELT_SIZE = 30;
    public static int TAKEN_SIZE = 50;
    public static final Item EMPTY = new Item(null, -1);

    private static int MAX_STACK = 100;
    public Item(ItemBase itemBase, int count){
        this.base = itemBase;
        if (count > MAX_STACK)
            this.count = MAX_STACK;
        else
            this.count = count;
    }

    public ItemBase getBase() {
        return base;
    }
    public TileBase getTileBase(){
        Registrable registrable = Registration.getRegistryObjectByRegistryName(base.getRegistryName()).get();
        if (registrable instanceof TileBase tileBase){
            return tileBase;
        }
        return null;
    }

    public int getCount() {
        return count;
    }

    public static int getMaxStack() {
        return MAX_STACK;
    }

    public boolean sameItem(Item item){
        return base.getRegistryName().equals(item.getBase().getRegistryName());
    }

    public int grow(int amount){
        count += amount;
        if (count > MAX_STACK){
            int toReturn = count-MAX_STACK;
            count = MAX_STACK;
            return toReturn;
        }
        return 0;
    }

    public boolean decrease(int amount){
        count -= amount;
        if (count <= 0){
            return true;
        }
        return false;
    }

    public Item split(int amount){
        Item toReturn = new Item(base, amount);
        if (amount >= count){
            count = 0;
        } else {
            count -= amount;
        }
        return toReturn;
    }

    public Item copy(){
        return new Item(base, count);
    }

    public boolean isEmpty(){
        return count == 0;
    }

    @Override
    public String toString() {
        return this == Item.EMPTY ? "ITEM.EMPTY" : "{item="+getBase().getRegistryName()+", cout="+count+", texture="+((BufferedImage)getBase().getTexture().getImage()).getWidth()+"}";
    }
}
