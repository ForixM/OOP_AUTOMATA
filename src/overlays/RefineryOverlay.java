package overlays;

import gameEngine.Automata;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

// RefineryOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
public class RefineryOverlay extends MachineOverlay{

    // height and width of the overlay (after resizing it)
    private static int w = 480, h = 225;
    // bg is the texture (image) of the overlay
    private static Texture bg;

    // ingredient1 (fitted in the ingredient slot)
    private static Rectangle ingredient1;
    // ingredient2 (fitted in the ingredient slot)
    private static Rectangle ingredient2;
    // ingredient3 (fitted in the ingredient slot)
    private static Rectangle ingredient3;
    // result (fitted in the ingredient slot)
    private static FilteredSlot result;
    // inventory is the storage (a container of objects/items)
    private Storage inventory;

    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/refinery_overlay.png", w, h);

        // ingredient1 slot -> filtered slot (with location in the overlay)
        ingredient1 = new Rectangle(557, 1324, 393, 394);
        // ingredient2 slot -> filtered slot (with location in the overlay)
        ingredient2 = new Rectangle(1024, 1324, 393, 394);
        // ingredient3 slot -> filtered slot (with location in the overlay)
        ingredient3 = new Rectangle(1490, 1324, 393, 394);
        // result slot -> filtered slot (with location in the overlay)
        result = new FilteredSlot(2889,1324, 393, 394);

        // Add filter to each slot
        rescaleSlots(Arrays.asList(ingredient1, ingredient2, ingredient3, result), w, h, bg);
    }

    // constructor
    public RefineryOverlay(GameScene main, Storage storage) {
        // call constructor from inherited class
        super(main, "RefineryOverlay", storage, w, h);
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
        paintItems(g, new Rectangle[]{ingredient1, ingredient2, ingredient3, result}, inventory);
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, new Rectangle[]{ingredient1, ingredient2, ingredient3, result}, main, inventory);
    }
}
