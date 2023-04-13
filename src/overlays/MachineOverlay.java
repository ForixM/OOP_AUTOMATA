package overlays;

import gameEngine.registry.base.ItemBase;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;
import world.Item;
import world.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

// Machine overlay -> parent class of all overlays for machines (inherited from OverlayBase)
public class MachineOverlay extends OverlayBase{
    // slots is the slots available in the overlay
    private Storage inventory;

    // filter items to be in the slots (whitelist items to be fitted in the slots)
    private static BiPredicate<ItemBase, Rectangle> filterItem = (itemBase, slot) -> {
        if (slot instanceof FilteredSlot filteredSlot) {
            for (ItemBase acceptedItem : filteredSlot.getAcceptedItems()) {
                if (acceptedItem == itemBase){
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    };

    // event of clicks from the slots
    protected static void slotClickHandler(MouseEvent e, Rectangle[] slots, GameScene main, Storage inventory){
        System.out.println("e.getPoint() = " + e.getPoint());

        for (int i = 0; i < slots.length; i++) {
            Rectangle slot = slots[i];
            if (slots[i].contains(e.getPoint())){
                System.out.println("clicked slot "+i);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
                        main.getPlayer().inHand = inventory.extract(i, false);
                    } else if (main.getPlayer().inHand != Item.EMPTY && filterItem.test(main.getPlayer().inHand.getBase(), slot)) {
                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
                        main.getPlayer().inHand = inventory.extract(i, true);
                    } else if (main.getPlayer().inHand != Item.EMPTY && filterItem.test(main.getPlayer().inHand.getBase(), slot)) {
                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i, 1);
                    }
                }
            }
        }
    }

    // display items in the inventory
    protected static void paintItems(Graphics g, Rectangle[] slots, Storage inventory){
        for (int i = 0; i < inventory.getCapacity(); i++) {
            if (inventory.getContainer()[i] != Item.EMPTY){
                g.drawImage(inventory.getContainer()[i].getBase().getTexture().getImage(), (int) slots[i].getX(), (int) slots[i].getY(), (int) slots[i].getWidth(), (int) slots[i].getHeight(), null);
                g.setColor(Color.RED);
                g.drawString(Integer.toString(inventory.getContainer()[i].getCount()), (int) slots[i].getX() + 5, (int) slots[i].getY() + 15);
            }
        }
    }

    // overlay of the machines
    public MachineOverlay(GameScene main, String name, Storage inventory, int width, int height) {
        super(main, name);
        this.inventory = inventory;
        setSize(width, height);
        // removed button
    }


    // display image of each item from the inventory (override)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
//        for (int i = 0; i < slots.length; i++) {
//            if (slots[i].contains(e.getPoint())){
//                if (e.getButton() == MouseEvent.BUTTON1) {
//                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
//                        main.getPlayer().inHand = inventory.extract(i, false);
//                    } else if (main.getPlayer().inHand != Item.EMPTY) {
//                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i);
//                    }
//                } else if (e.getButton() == MouseEvent.BUTTON3){
//                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
//                        main.getPlayer().inHand = inventory.extract(i, true);
//                    } else if (main.getPlayer().inHand != Item.EMPTY) {
//                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i, 1);
//                    }
//                }
//            }
//        }
    }
}
