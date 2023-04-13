package overlays;

import gameEngine.Automata;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

// GoldChestOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
public class GoldChestOverlay extends MachineOverlay{

    // height and width of the overlay (after resizing it)
    private static int w = 480, h = 344;

    // bg is the texture (image) of the overlay
    private static Texture bg;
    // inventory is the storage (a container of objects/items)
    private static Rectangle[] slots;
    // slots is the slots available in the overlay
    private Storage inventory;



    //Inventory constants
    // slot size
    public static Rectangle SlotSize = new Rectangle(393, 393);
    // horizontal space between slots
    public static int horizontalMargin = 73;
    // vertical space between slots
    public static int verticalMargin = 74;
    // anchor where the slots begin
    public static Point anchor = new Point(91, 605);
    // nb rows in the inventory
    public static int rows = 4;
    // nb cols in the inventory
    public static int columns = 8;

    // define anchor points placements
    // (define each point where to draw the item in the inventory)
    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/golden_chest_overlay.png", w, h);
        slots = new Rectangle[rows*columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                slots[i*columns+j] = new Rectangle(anchor.x + j*SlotSize.width + horizontalMargin*j, anchor.y + i*SlotSize.height + verticalMargin*i, SlotSize.width, SlotSize.height);
            }
        }

        // Add filter to each slot

        rescaleSlots(List.of(slots), w, h, bg);
    }

    // constructor
    public GoldChestOverlay(GameScene main, Storage storage) {
        // call constructor from inherited class
        super(main, "GoldChestOverlay", storage, w, h);
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
        paintItems(g, slots, inventory);
        super.paintComponent(g);
    }

    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, slots, main, inventory);
    }
}
