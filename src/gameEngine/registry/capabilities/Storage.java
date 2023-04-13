package gameEngine.registry.capabilities;

import gameEngine.registry.base.ItemBase;
import world.Item;

import java.util.Arrays;

public abstract class Storage {
    private Item[] container;

    public Storage(int capacity){
        this.container = new Item[capacity];
        for (int i = 0; i < container.length; i++) {
            container[i] = Item.EMPTY;
        }
    }

    public void insert(Item toInsert){
        int remaining = 0;
        for (Item t : container) {
            if (t != Item.EMPTY && t.sameItem(toInsert)){
                remaining = t.grow(toInsert.getCount());
                if (remaining == 0)
                    return;
            }
        }
        if (remaining > 0){
            toInsert.decrease(toInsert.getCount()-remaining);
        }
        for (int i = 0; i < container.length; i++) {
            if (container[i] == Item.EMPTY){
                container[i] = toInsert;
                return;
            }
        }
    }

    public Item insert(Item toInsert, int slot){
        if (slot < container.length){
            if (isSlotEmpty(slot)){
                container[slot] = toInsert;
                return Item.EMPTY;
            } else if (toInsert.getBase() == container[slot].getBase()) {
                int remaining = container[slot].grow(toInsert.getCount());
                if (remaining == 0)
                    return Item.EMPTY;
                toInsert.decrease(toInsert.getCount()-remaining);
                return toInsert;
            } else {
                Item toReturn = container[slot];
                container[slot] = toInsert;
                return toReturn;
            }
        }
        return toInsert;
    }

    public Item insert(Item toInsert, int slot, int amount){
        if (slot < container.length){
            if (isSlotEmpty(slot)){
                if (amount >= toInsert.getCount()) {
                    container[slot] = toInsert;
                    return Item.EMPTY;
                } else {
                    container[slot] = toInsert.split(amount);
                    return toInsert;
                }
            } else if (toInsert.getBase() == container[slot].getBase()) {
                container[slot].grow(amount);
                toInsert.decrease(1);
                if (toInsert.isEmpty()) {
                    return Item.EMPTY;
                }
                return toInsert;
            } else {
                Item toReturn = container[slot];
                container[slot] = toInsert;
                return toReturn;
            }
        }
        return toInsert;
    }

    public Item extract(int slot, boolean half){
        Item toReturn;
        if (!half) {
            toReturn = container[slot];
            container[slot] = Item.EMPTY;
        } else {
            toReturn = container[slot].split(container[slot].getCount()/2);
            if (container[slot].isEmpty()){
                container[slot] = Item.EMPTY;
            }
        }
        if (toReturn.getCount() == 0){
            toReturn = Item.EMPTY;
        }
        return toReturn;
    }

    public Item extract(int slot, int amount){
        Item toReturn = container[slot].split(amount);
        if (container[slot].isEmpty())
            container[slot] = Item.EMPTY;
        return toReturn;
    }

    public Item extract(String registryName, int amount){
        for (int i = 0; i < container.length; i++) {
            if (container[i] != Item.EMPTY && container[i].getBase().getRegistryName().equals(registryName)){
                Item toReturn = container[i].split(amount);
                if (container[i].isEmpty())
                    container[i] = Item.EMPTY;
                return toReturn;
            }
        }
        return Item.EMPTY;
    }

    public Item extract(String registryName, boolean half){
        for (int i = 0; i < container.length; i++) {
            if (container[i] != Item.EMPTY && container[i].getBase().getRegistryName().equals(registryName)){
                Item toReturn;
                if (half) {
                    toReturn = container[i].split(container[i].getCount()/2);
                    if (container[i].isEmpty())
                        container[i] = Item.EMPTY;
                } else {
                    toReturn = container[i];
                    container[i] = Item.EMPTY;
                }
                return toReturn;
            }
        }
        return Item.EMPTY;
    }

    public boolean contains(ItemBase itemBase){
        for (Item item : container) {
            if (item != Item.EMPTY && item.getBase().getRegistryName().equals(itemBase.getRegistryName())){
                return true;
            }
        }
        return false;
    }

    public boolean contains(Item item){
        for (Item t : container) {
            if (t.sameItem(item)){
                return true;
            }
        }
        return false;
    }

    public boolean containsAtLeast(Item item){
        for (Item t : container) {
            if (t != Item.EMPTY && t.sameItem(item) && t.getCount() >= item.getCount()){
                return true;
            }
        }
        return false;
    }

    public boolean isSlotEmpty(int slot){
        if (slot < container.length){
            return container[slot] == Item.EMPTY;
        }
        return true;
    }

    public Item[] getContainer() {
        return container;
    }

    public int getCapacity() {
        return container.length;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "container=" + Arrays.toString(container) +
                '}';
    }
}
