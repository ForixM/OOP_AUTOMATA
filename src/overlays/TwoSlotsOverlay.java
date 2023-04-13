package overlays;

import gameEngine.Automata;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.BiConsumer;

// OneSlotOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
// for machines that needs two slots
public class TwoSlotsOverlay extends MachineOverlay{

    // height and width of the overlay (after resizing it)
    private static int w = 480, h = 275;
    // bg is the texture (image) of the overlay
    private static Texture bg;

    // slot1 (fitted in the ingredient slot)
    private FilteredSlot slot1;
    // slot2 (fitted in the ingredient slot)
    private FilteredSlot slot2;
    // inventory is the storage (a container of objects/items)
    private Storage inventory;

    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/2slots_overlay.png", w, h);
    }

    // constructor (bi consumer for both filtered slots)
    public TwoSlotsOverlay(GameScene main, Storage storage, BiConsumer<FilteredSlot, FilteredSlot> applyFilter) {
        // call constructor from inherited class
        super(main, "TwoSlotsOverlay", storage, w, h);
        // set inventory storage
        this.inventory = storage;

        // filtering slots (with coordinates)
        slot1 = new FilteredSlot(1490, 1537, 393, 394);
        slot2 = new FilteredSlot(2889, 1537, 393, 394);
        // rescale slots
        rescaleSlots(Arrays.asList(closeButton, slot1, slot2), w, h, bg);

        // apply filters
        applyFilter.accept(slot1, slot2);
    }

    // display image of each item from the inventory
    @Override
    protected void paintComponent(Graphics g) {
        // First draw the background image
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
        // Then use this function to draw the items on the gui
        paintItems(g, new Rectangle[]{slot1, slot2}, inventory);
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, new Rectangle[]{slot1, slot2}, main, inventory);
    }
}
