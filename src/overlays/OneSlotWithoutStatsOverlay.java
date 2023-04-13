package overlays;

import gameEngine.Automata;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

// OneSlotOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
// for very basic items or machines
public class OneSlotWithoutStatsOverlay extends MachineOverlay{

    // height and width of the overlay (after resizing it)
    private static int w = 480, h = 188;
    // bg is the texture (image) of the overlay
    private static Texture bg;

    // result (fitted in the ingredient slot)
    private static FilteredSlot slot;
    // inventory is the storage (a container of objects/items)
    private Storage inventory;

    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/1slotwithoutstats_overlay.png", w, h);

        // slot -> filtered slot (with location in the overlay)
        slot = new FilteredSlot(1469, 893, 393, 394);

        // Add filter to each slot
        rescaleSlots(Arrays.asList(slot), w, h, bg);
    }

    // constructor
    public OneSlotWithoutStatsOverlay(GameScene main, Storage storage) {
        // call constructor from inherited class
        super(main, "OneSlotWithoutStatsOverlay", storage, w, h);
        // set inventory storage
        this.inventory = storage;
        // rescale slots
        rescaleSlots(Arrays.asList(closeButton), w, h, bg);
    }

    // display image of each item from the inventory
    @Override
    protected void paintComponent(Graphics g) {
        // First draw the background image
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
        // Then use this function to draw the items on the gui
        paintItems(g, new Rectangle[]{slot}, inventory);
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, new Rectangle[]{slot}, main, inventory);
    }
}
