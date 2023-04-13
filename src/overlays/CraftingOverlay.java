package overlays;

import gameEngine.Automata;
import gameEngine.rendering.Texture;
import scenes.GameScene;
import utils.Craft;
import world.Item;
import world.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

// CraftingOverlay (image, texture and storage) built from his inherited class OverlayBase
public class CraftingOverlay extends OverlayBase {
    // bg is the texture (image) of the overlay
    private Texture background;
    // slots is the slots available in the overlay
    private Rectangle[] slots;
    // slot texture for displaying ingredients to make the craft
    private Texture slot;
    private static int counter = 1;
    // selected craft
    private Craft selectedCraft = null;

    // crafting button to start crafting
    private Rectangle craft;


    //Inventory constants
    // slot size
    public static Rectangle SlotSize = new Rectangle(393, 393);
    // horizontal space between slots
    public static int horizontalMargin = 73;
    // vertical space between slots
    public static int verticalMargin = 74;
    // anchor where the slots begin
    public static Point anchor = new Point(91, 341);
    // nb rows in the inventory
    public static int rows = 5;
    // nb cols in the inventory
    public static int columns = 8;

    // constructor
    public CraftingOverlay(GameScene main){
        // call constructor from inherited class
        super(main, "CraftingOverlay"+(counter++));
        // set size of overlay
        setSize(480, 480);
        // set bg from png file and resize it
        this.background = new Texture(Automata.TEXTURE_PATH+"/overlay/craft_menu_overlay.png", getWidth(), getHeight());
        // set slot from png file and resize it
        this.slot = new Texture(Automata.TEXTURE_PATH+"/overlay/slot_overlay.png", 33);
        // set slots
        slots = new Rectangle[rows*columns];
        // thread to load textures when loading the game
        Thread t = new Thread(this::waitInitThread);
        // set crafting button
        craft = new Rectangle(95, 3426, 3650, 310);
        t.start();
        setLayout(null);
    }

    // rescale slots from original size to compressed one
    private void rescaleSlots(Rectangle[] slots){
        for (Rectangle slot : slots) {
            slot.x = rescaleHorizontalValue(slot.x);
            slot.y = rescaleVerticalValue(slot.y);
            slot.width = rescaleHorizontalValue(slot.width);
            slot.height = rescaleVerticalValue(slot.height);
        }
    }

    // used in rescaleSlots to resize horizontally the png
    private int rescaleHorizontalValue(int value){
        return (getSize().width*value)/background.getOriginalWidth();
    }
    // used in rescaleSlots to resize vertically the png
    private int rescaleVerticalValue(int value){
        return (getSize().height*value)/background.getOriginalHeight();
    }

    // thread to initialize size of the slots and the window
    private void waitInitThread(){
        while (getBounds().getWidth() <= 0 || getBounds().getHeight() <= 0){}
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                slots[i*columns+j] = new Rectangle(anchor.x + j*SlotSize.width + horizontalMargin*j, anchor.y + i*SlotSize.height + verticalMargin*i, SlotSize.width, SlotSize.height);
            }
        }
        rescaleSlots(slots);
        rescaleSlots(new Rectangle[]{closeButton, craft});
        System.out.println("closeButton = " + closeButton);
    }

    // display image of each item from the inventory
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
        int topInfo = (int) (getHeight()*0.84);
        for (int i = 0; i < Craft.crafts.size(); i++) {
            g.drawImage(Craft.crafts.get(i).getResult().getBase().getTexture().getImage(), (int) slots[i].getX(), (int) slots[i].getY(), (int) slots[i].getWidth(), (int) slots[i].getHeight(), null);
        }

        if (selectedCraft != null){
            topInfo = 350;
            int stepX = 50;
            for (Item ingredient : selectedCraft.getIngredients()) {
                g.drawImage(slot.getImage(), stepX, topInfo, (int) slots[0].getWidth()+3, (int) slots[0].getHeight()+3, null);
                g.drawImage(ingredient.getBase().getTexture().getImage(), stepX, topInfo, (int) slots[0].getWidth()+3, (int) slots[0].getHeight()+3, null);
                stepX += (int)slots[0].getWidth()+5;
            }
            //g.drawImage(slot.getImage(), 3122, 2811, (int) slots[0].getWidth()+3, (int) slots[0].getHeight()+3, null);
            g.drawImage(selectedCraft.getResult().getBase().getTexture().getImage(), rescaleHorizontalValue(3122), rescaleVerticalValue(2811), (int) slots[0].getWidth()+3, (int) slots[0].getHeight()+3, null);
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
                selectedCraft = (selectedCraft == null || selectedCraft != Craft.crafts.get(i)) ? Craft.crafts.get(i) : null;
            }
        }
        if (craft.contains(e.getPoint())){
            System.out.println("cacapipi");
            Player player = Automata.INSTANCE.getPlayer();
            if (selectedCraft != null && selectedCraft.haveIngredients(Arrays.asList(player.getInventory().getContainer()))){
                System.out.println("Have enough ingredients, let's go craft !");
                for (Item ingredient : selectedCraft.getIngredients()) {
                    player.getInventory().extract(ingredient.getBase().getRegistryName(), ingredient.getCount());
                }
                player.getInventory().insert(selectedCraft.getResult().copy());
            }
        }
    }
}
