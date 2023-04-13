package overlays;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.tiles.Furnace;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;
import world.Item;
import world.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

// FurnaceOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
public class FurnaceOverlay extends MachineOverlay{

    // height and width of the overlay (after resizing it)
    private static int w = 480, h = 344;
    // bg is the texture (image) of the overlay
    private static Texture bg;
    // inventory is the storage (a container of objects/items)
    private Storage inventory;

    // ingredient (fitted in the ingredient slot)
    private static FilteredSlot ingredient;
    // fuel (fitted in the fuel slot)
    private static FilteredSlot fuel;
    // result (fitted in the result slot)
    private static FilteredSlot result;

    // define anchor points placements
    // (define each point where to draw the item in the inventory)
    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/furnace_overlay.png", w, h);

        // ingredient slot -> filtered slot (with location in the overlay)
        ingredient = new FilteredSlot(1490, 1537, 393, 394);
        // fuel slot -> filtered slot (with location in the overlay)
        fuel = new FilteredSlot(1956, 2002, 393, 394);
        // result slot -> filtered slot (with location in the overlay)
        result = new FilteredSlot(2889,1537, 393, 394);

        // Add filter to each slot
        ingredient.whitelistItems(Registration.ironOre.get(), Registration.copperOre.get(), Registration.ironIngot.get(),Registration.goldOre.get());
        fuel.whitelistItems(Registration.coal.get());

        // rescale slots
        rescaleSlots(Arrays.asList(ingredient, fuel, result), w, h, bg);
    }

    // constructor
    public FurnaceOverlay(GameScene main, Storage storage) {
        // call constructor from inherited class
        super(main, "FurnaceOverlay", storage, w, h);
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
        paintItems(g, new Rectangle[]{ingredient, fuel, result}, inventory);
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, new Rectangle[]{ingredient, fuel, result}, main, inventory);
    }
}
