package overlays;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.FilteredSlot;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.function.Consumer;

// OneSlotOverlay (image, texture and storage) built from his parent class MachineOverlay which is an inherited class OverlayBase
// for very basic items or machines that needs stats
public class OneSlotOverlay extends MachineOverlay{

    private static int w = 480, h = 270;
    private static Texture bg;

    private FilteredSlot slot;
    private Storage inventory;

    static {
        bg = new Texture(Automata.TEXTURE_PATH + "overlay/1slot_overlay.png", w, h);


        // Add filter to each slot
    }

    public OneSlotOverlay(GameScene main, Storage storage, Consumer<FilteredSlot> applyFilter) {
        super(main, "OneSlotOverlay", storage, w, h);
        this.inventory = storage;
        slot = new FilteredSlot(1469, 1456, 393, 394);
        applyFilter.accept(slot);
        rescaleSlots(Arrays.asList(closeButton, slot), w, h, bg);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // First draw the background image
        g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
        // Then use this function to draw the items on the gui
        paintItems(g, new Rectangle[]{slot}, inventory);
        super.paintComponent(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Used to handle click on guis
        slotClickHandler(e, new Rectangle[]{slot}, main, inventory);
    }
}
