package gameEngine;

import gameEngine.registry.Registration;
import gameEngine.rendering.Scene;
import overlays.CraftingOverlay;
import overlays.InventoryOverlay;
import overlays.OverlayBase;
import scenes.GameScene;
import utils.Craft;
import world.Item;
import world.Map;
import world.Player;
import world.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Automata {
    public static final String ASSETS_PATH = "assets/";
    public static final String TEXTURE_PATH = ASSETS_PATH+"textures/";
    public static int WIN_WIDTH = 1280;
    public static int WIN_HEIGHT = 720;
    public static int FPS; //Frames Per Second

    static {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();

            int refreshRate = dm.getRefreshRate();
            if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                System.out.println("Unknown rate");
            } else {
                FPS = refreshRate;
            }

            int bitDepth = dm.getBitDepth();
            int numColors = (int) Math.pow(2, bitDepth);
        }
    }

    public static final int UPS = 20; //Update Per Second
    public static double DELTA = (double)1/FPS;

    private Map map;
    private Player player;

    private Window window;
    private Scene scene;

    private Thread renderThread;
    private Thread logicThread;

    private KeyHandling keyHandling;

    public static Automata INSTANCE;
    public Automata(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();

            int refreshRate = dm.getRefreshRate();
            if (refreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
                System.out.println("Unknown rate");
            } else {
                FPS = refreshRate;
            }
        }

        this.keyHandling = new KeyHandling();
        window = new Window(WIN_WIDTH, WIN_HEIGHT, "default");
        INSTANCE = this;
        this.renderThread = new Thread(this::renderLoop);
    }

    public void beginInitializers(){
        this.player = new Player();
        player.getInventory().insert(new Item(Registration.pipe.get().getItem(), 50));
        this.map = new Map();
        this.logicThread = new Thread(this::logicLoop);
        registerKeys();
        Registration.registerCrafts(new File("./assets/auto_crafts.json"), Craft::registerCraft);
        Registration.registerCrafts(new File("./assets/furnace_crafts.json"), Craft::registerFurnaceCraft);
        Registration.registerCrafts(new File("./assets/refinery_crafts.json"), Craft::registerRefineryCraft);
    }

    public KeyHandling getKeyHandling() {
        return keyHandling;
    }

    private void registerKeys(){
        keyHandling.handleKey(KeyEvent.VK_Z, () -> Player.POS_Y += GameScene.MOVE_SPEED * Automata.DELTA, false);
        keyHandling.handleKey(KeyEvent.VK_S, () -> Player.POS_Y -= GameScene.MOVE_SPEED * Automata.DELTA, false);
        keyHandling.handleKey(KeyEvent.VK_Q, () -> Player.POS_X += GameScene.MOVE_SPEED * Automata.DELTA, false);
        keyHandling.handleKey(KeyEvent.VK_D, () -> Player.POS_X -= GameScene.MOVE_SPEED * Automata.DELTA, false);
        keyHandling.handleKey(KeyEvent.VK_TAB, () -> {
            if (window.getContentPane() instanceof GameScene gameScene){
                InventoryOverlay inventoryOverlay = player.getInventoryOverlay();
                if (inventoryOverlay == null){
                    inventoryOverlay = player.generateInventoryOverlay(gameScene);
                }
                if (gameScene.getOverlays().contains(inventoryOverlay)){
                    gameScene.remove(inventoryOverlay);
                    gameScene.getOverlays().remove(inventoryOverlay);
                } else {
                    gameScene.add(inventoryOverlay);
                    gameScene.getOverlays().add(inventoryOverlay);
                }
            }
        }, true);
        keyHandling.handleKey(KeyEvent.VK_E, () -> {
            if (window.getContentPane() instanceof GameScene gameScene){
                CraftingOverlay inventoryOverlay = player.getCraftingOverlay();
                if (inventoryOverlay == null){
                    inventoryOverlay = player.generateCraftingOverlay(gameScene);
                }
                if (gameScene.getOverlays().contains(inventoryOverlay)){
                    gameScene.remove(inventoryOverlay);
                    gameScene.getOverlays().remove(inventoryOverlay);
                } else {
                    gameScene.add(inventoryOverlay);
                    gameScene.getOverlays().add(inventoryOverlay);
                }
            }
        }, true);
        keyHandling.handleKey(KeyEvent.VK_R, () -> player.incrementOrientation(1), true);
        keyHandling.handleKey(KeyEvent.VK_C, () -> {
            Player.POS_X = 0;
            Player.POS_Y = 0;
        }, true);
        keyHandling.handleKey(KeyEvent.VK_V, () -> {
            Player.POS_X = 2500;
            Player.POS_Y = -500;
        }, true);
    }

    /**
     * Used to switch the current displaying scene in the window.
     * @param scene A Scene to be displayed to the screen
     * @param <T> Any class can be passed as a parameter while it extends the Scene abstract class
     */
    public <T extends Scene> void switchScene(T scene){
        this.scene = scene;
        window.setContentPane(scene);
        window.setVisible(true);
    }

    /**
     * Main game loop. Where game displaying and logic should be executed.
     */
    public void gameLoop(){
        logicThread.start();
        renderThread.start();
    }

    private void renderLoop(){
        while (true){
            long previousTime = System.nanoTime();
            keyHandling.handleKeys();
            scene.repaint();
//                System.out.println("render time: "+((double)(System.nanoTime()-previousTime))+"ms");
            try {
                Thread.sleep((long) Math.abs((1000/FPS)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            DELTA = (double)(System.nanoTime()-previousTime)/1000000000;
//                System.out.println("DELTA="+DELTA);
        }
//        System.out.println("finished test with "+count+" frames rendered");
    }

    private void logicLoop(){
        while (true){
            map.updateTiles();
            player.update();
            try {
                Thread.sleep(1000/UPS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //GETTERS AND SETTER

    public Window getWindow() {
        return window;
    }

    public Scene getScene() {
        return scene;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }
}
