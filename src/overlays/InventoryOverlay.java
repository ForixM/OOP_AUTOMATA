package overlays;

import gameEngine.Automata;
import gameEngine.registry.capabilities.Storage;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import world.Item;

import java.awt.*;
import java.awt.event.MouseEvent;

// InventoryOverlay (image, texture and storage) built from his inherited class OverlayBase
public class InventoryOverlay extends OverlayBase {
    // bg is the texture (image) of the overlay
    private Texture background;
    // inventory is the storage (a container of objects/items)
    private Storage inventory;
    // slots is the slots available in the overlay
    private Rectangle[] slots;
    private static int counter = 1;

    //Inventory constants
    // slot size
    public static Rectangle SlotSize = new Rectangle(393, 393);
    // horizontal space between slots
    public static int horizontalMargin = 73;
    // vertical space between slots
    public static int verticalMargin = 74;
    // anchor where the slots begin
    public static Point anchor = new Point(91, 748);
    // nb rows in the inventory
    public static int rows = 3;
    // nb cols in the inventory
    public static int columns = 8;

    // constructor
    public InventoryOverlay(GameScene main){
        // call constructor from inherited class
        super(main, "InventoryOverlay"+(counter++));
        // set size of overlay
        setSize(480, 270);
        // location in the main window
        setLocation(10, 10);
        // set bg from png file and resize it
        this.background = new Texture(Automata.TEXTURE_PATH+"/overlay/inventory_overlay.png", getWidth(), getHeight());
        // set player's inventory
        this.inventory = Automata.INSTANCE.getPlayer().getInventory();
        // set slots
        this.slots = new Rectangle[inventory.getCapacity()];
        // thread to load textures when loading the game
        Thread t = new Thread(this::waitInitThread);
        t.start();
    }

    // rescale slots from original size to compressed one
    private void rescaleSlots(Rectangle[] slots){
        System.out.println("rescaling slots");
        for (Rectangle slot : slots) {
            slot.x = rescaleHorizontalValue(slot.x);
            slot.y = rescaleVerticalValue(slot.y);
            slot.width = rescaleHorizontalValue(slot.width);
            slot.height = rescaleVerticalValue(slot.height);
        }
    }

    // used in rescaleSlots to resize horizontally the png
    private int rescaleHorizontalValue(int value){
        return (getSize().width*value)/ background.getOriginalWidth();
    }

    // used in rescaleSlots to resize horizontally the png
    private int rescaleVerticalValue(int value){
        return (getSize().height*value)/background.getOriginalHeight();
    }

    // thread to initialize size of the slots and the window
    private void waitInitThread(){
        while (getBounds().getWidth() <= 0 || getBounds().getHeight() <= 0){}
        System.out.println("wait finished");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                slots[i*columns+j] = new Rectangle(anchor.x + j*SlotSize.width + horizontalMargin*j, anchor.y + i*SlotSize.height + verticalMargin*i, SlotSize.width, SlotSize.height);
            }
        }
        // rescale all slots
        rescaleSlots(slots);
        rescaleSlots(new Rectangle[]{closeButton});
        System.out.println("inventory = " + inventory);
    }

    // display image of each item from the inventory
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
        for (int i = 0; i < inventory.getCapacity(); i++) {
            if (inventory.getContainer()[i] != Item.EMPTY){
                g.drawImage(inventory.getContainer()[i].getBase().getTexture().getImage(), (int) slots[i].getX(), (int) slots[i].getY(), (int) slots[i].getWidth(), (int) slots[i].getHeight(), null);
                g.setColor(Color.RED);
                g.drawString(Integer.toString(inventory.getContainer()[i].getCount()), (int) slots[i].getX() + 5, (int) slots[i].getY() + 15);
            }
        }
        super.paintComponent(g);
    }


    // detect mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].contains(e.getPoint())){
                System.out.println("Clicked on slot "+i);
                System.out.println("e.getButton() = " + e.getButton());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
                        main.getPlayer().inHand = inventory.extract(i, false);
                    } else if (main.getPlayer().inHand != Item.EMPTY) {
                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3){
                    if (main.getPlayer().inHand == Item.EMPTY && !inventory.isSlotEmpty(i)) {
                        main.getPlayer().inHand = inventory.extract(i, true);
                    } else if (main.getPlayer().inHand != Item.EMPTY) {
                        main.getPlayer().inHand = inventory.insert(main.getPlayer().inHand, i, 1);
                    }
                }
            }
        }
    }
}
