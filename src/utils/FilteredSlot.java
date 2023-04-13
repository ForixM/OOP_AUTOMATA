package utils;

import gameEngine.registry.base.ItemBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilteredSlot extends Rectangle {
    private List<ItemBase> acceptedItems;

    public FilteredSlot(){
        acceptedItems = new ArrayList<>();
    }

    public FilteredSlot(int x, int y, int width, int height){
        super(x, y, width, height);
        acceptedItems = new ArrayList<>();
    }

    public void whitelistItems(ItemBase... items){
        acceptedItems.addAll(List.of(items));
    }

    public List<ItemBase> getAcceptedItems() {
        return acceptedItems;
    }
}
