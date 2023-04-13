package world;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.Storage;
import overlays.CraftingOverlay;
import overlays.InventoryOverlay;
import scenes.GameScene;
import utils.Face;
import utils.Network;
import utils.NetworkElement;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Player {

    private Storage inventory;
    private InventoryOverlay inventoryOverlay;
    private CraftingOverlay craftingOverlay;
    private Tile clickedTile;
    private Tile hoverTile;
    public Item inHand = Item.EMPTY;
    private int miningCounter = 0;
    private boolean placingNetwork;
    private boolean mousePressed;
    private Face orientation;
    private final BiConsumer<Tile, Tile> clickAction = (tile, previous) -> {
        tile.onClick(inHand);
        if (previous != null){
            Network network = Network.getNetworkAt(previous);
            if (network != null) {
                network.createConnection(previous, tile);
            }
        }
        tile.setNetworkElement(Network.getNetworkAt(tile).getNetworkElement(tile));
    };
    public final int MINING_TIME = 5; //Time to mine
    public static double POS_X = 0;
    public static double POS_Y = 0;
    public Player(){
        this.mousePressed = false;
        this.inventoryOverlay = null;
        this.craftingOverlay = null;
        this.placingNetwork = false;
        this.inventory = new Storage(24) {};
        inventory.insert(new Item(Registration.belt.get().getItem(), 20));
        inventory.insert(new Item(Registration.coal.get(), 25));
        inventory.insert(new Item(Registration.thermalDrill.get().getItem(), 3));
        inventory.insert(new Item(Registration.electricDrill1.get().getItem(), 1));
        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
        inventory.insert(new Item(Registration.thermalPowerPlant.get().getItem(), 1));
        // Furnace, thermaldrill, extractor
//        inventory.insert(new Item(Registration.thermalArm.get().getItem(), 2));
        this.orientation = Face.RIGHT;
        // Testing furnace:
//        inventory.insert(new Item(Registration.coal.get(), 1));
//        inventory.insert(new Item(Registration.ironOre.get(), 10));
//        inventory.insert(new Item(Registration.copperOre.get(), 10));
//        inventory.insert(new Item(Registration.goldOre.get(), 10));


        // Testing autocrafter
//        inventory.insert(new Item(Registration.autoCrafter.get().getItem(), 10));
//        inventory.insert(new Item(Registration.stoneOre.get(), 10));
//        inventory.insert(new Item(Registration.ironIngot.get(), 10));
//        inventory.insert(new Item(Registration.copperIngot.get(), 10));
//        inventory.insert(new Item(Registration.coil.get(), 10));
//        inventory.insert(new Item(Registration.goldIngot.get(), 10));
//        inventory.insert(new Item(Registration.stoneBrick.get(), 10));
//        inventory.insert(new Item(Registration.pipe.get().getItem(), 10));
//        inventory.insert(new Item(Registration.gear.get(), 10));
//        inventory.insert(new Item(Registration.steel.get(), 10));
//        inventory.insert(new Item(Registration.electronicCircuit.get(), 10));
//        inventory.insert(new Item(Registration.engine.get(), 10));
//        inventory.insert(new Item(Registration.plastic.get(), 10));

        // Testing Refinery
//        inventory.insert(new Item(Registration.refinery.get().getItem(), 10));
//        inventory.insert(new Item(Registration.water.get().getItem(), 20));
//        inventory.insert(new Item(Registration.petrol.get(), 20));
//        inventory.insert(new Item(Registration.coal.get(), 1));
//        inventory.insert(new Item(Registration.electronicCircuit.get(), 10));
//        inventory.insert(new Item(Registration.windmill.get().getItem(), 10));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));

        // Testing ThermalDrill
        // "gameEngine.registry.Registration.getRegistryObjectByRegistryName(String)" is null
//        inventory.insert(new Item(Registration.thermalDrill.get().getItem(), 10));
//        inventory.insert(new Item(Registration.coal.get(), 10));

        // Testing ThermalPowerPlant
//        inventory.insert(new Item(Registration.thermalPowerPlant.get().getItem(), 10));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
//        inventory.insert(new Item(Registration.coal.get(), 100));

        // Testing PetrolPowerPlant
//        inventory.insert(new Item(Registration.petrolPowerPlant.get().getItem(), 10));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
//        inventory.insert(new Item(Registration.petrol.get(), 100));

        // Testing Extractors
//        inventory.insert(new Item(Registration.electricDrill2.get().getItem(), 10));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
//        inventory.insert(new Item(Registration.windmill.get().getItem(), 10));

        // Testing ElectricFurnace
//        inventory.insert(new Item(Registration.electricFurnace.get().getItem(), 1));
//        inventory.insert(new Item(Registration.coal.get(), 1));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
//        inventory.insert(new Item(Registration.windmill.get().getItem(), 10));
//        inventory.insert(new Item(Registration.ironOre.get(), 10));
//        inventory.insert(new Item(Registration.copperOre.get(), 10));
//        inventory.insert(new Item(Registration.goldOre.get(), 10));

        // Testing PumpUp
//        inventory.insert(new Item(Registration.pump.get().getItem(), 10));
//        inventory.insert(new Item(Registration.electricPole.get().getItem(), 10));
//        inventory.insert(new Item(Registration.windmill.get().getItem(), 10));


        inventory.insert(new Item(Registration.ironIngot.get(), 10));

    }

    public void update(){
        if (mousePressed && clickedTile != null && clickedTile.getDeposit() != null) {
            if (miningCounter == MINING_TIME * Automata.UPS) {
                inventory.insert(new Item(clickedTile.getDeposit().getLoot(), 1));
                miningCounter = 0;
            } else {
                miningCounter++;
            }
        } else {
            miningCounter = 0;
        }
    }

    public InventoryOverlay generateInventoryOverlay(GameScene gameScene){
        this.inventoryOverlay = new InventoryOverlay(gameScene);
        return inventoryOverlay;
    }

    public CraftingOverlay generateCraftingOverlay(GameScene gameScene){
        this.craftingOverlay = new CraftingOverlay(gameScene);
        return craftingOverlay;
    }

    public void setClickedTile(Tile clickedTile) {
        System.out.println("clicked on "+clickedTile);
        if (inHand != Item.EMPTY && inHand.getTileBase() == Registration.belt.get()){
            placingNetwork = !placingNetwork;
            System.out.println(placingNetwork ? "beginning network placement" : "finished network placement");
            if (!placingNetwork){
                Tile previousPlaced = null;

                previousPlaced = horizontalAction(this.clickedTile, clickedTile, previousPlaced, clickAction);
                System.out.println("previousPlaced = " + previousPlaced);
                previousPlaced = verticalAction(this.clickedTile, clickedTile, previousPlaced, clickAction);
                Network network = Network.getNetworkAt(previousPlaced);
                if (network != null) {
                    network.prettyPrintConnections();
                }
                this.clickedTile = clickedTile;
            } else {
                this.clickedTile = clickedTile;
            }
        } else {
            placingNetwork = false;
            this.clickedTile = clickedTile;
            inHand = clickedTile.onClick(inHand, orientation);
        }
    }

    private int computeHorizontalDiff(Tile from, Tile to){
        int diff;
        Supplier<Integer> floor = () ->Math.abs((int) Math.floor((double) (to.getY() - from.getY()) / 2.0));
        Supplier<Integer> ceil = () ->Math.abs((int) Math.ceil((double) (to.getY() - from.getY()) / 2.0));
        if (from.getX() < to.getX()) {
            if (from.getY() % 2 == 0) {
                if (from.getY() < to.getY())
                    diff = floor.get();
                else
                    diff = ceil.get();
            } else {
                if (from.getY() < to.getY())
                    diff = ceil.get();
                else
                    diff = floor.get();
            }
        } else {
            if (from.getY() % 2 == 0) {
                if (from.getY() < to.getY())
                    diff = ceil.get();
                else
                    diff = floor.get();
            } else {
                if (from.getY() < to.getY())
                    diff = floor.get();
                else
                    diff = ceil.get();
            }
        }
        return diff;
    }

    private Tile horizontalAction(Tile from, Tile to, Tile previousPlaced, BiConsumer<Tile, Tile> action){
        int fromX = from.getX();
        int toX = to.getX();
        int y = from.getY();
        int diff = computeHorizontalDiff(from, to);
        Tile firstTile = null;
        if (fromX < toX){
            for (int i = fromX; i <= (toX-diff); i++){
                Tile tile = Automata.INSTANCE.getMap().getTileAt(i, y);
                if (firstTile == null){
                    firstTile = tile;
                }
                action.accept(tile, previousPlaced);
//                if (previousPlaced != null){
//                    Network network = Network.getNetworkAt(previousPlaced);
//                    if (network != null) {
//                        network.createConnection(previousPlaced, tile);
//                    }
//                }
                previousPlaced = tile;
            }
        } else {
            for (int i = fromX; i >= toX+diff; i--){
                Tile tile = Automata.INSTANCE.getMap().getTileAt(i, y);
                if (firstTile == null){
                    firstTile = tile;
                }
                action.accept(tile, previousPlaced);
//                if (previousPlaced != null){
//                    Network network = Network.getNetworkAt(previousPlaced);
//                    if (network != null) {
//                        network.createConnection(previousPlaced, tile);
//                    }
//                }
                previousPlaced = tile;
            }
        }
        if (action == clickAction){
            Network network = Network.getNetworkAt(firstTile);
            if (network != null){
                NetworkElement networkElement = network.getNetworkElement(firstTile);
                if (networkElement != null){
                    System.out.println("beginning first item inserting");
                    networkElement.setInternalItem(new Item(Registration.goldOre.get(), 1));
                }
            }
        }
        return previousPlaced;
    }

    private Tile verticalAction(Tile from, Tile to, Tile previousPlaced, BiConsumer<Tile, Tile> action){
        int diff = computeHorizontalDiff(from, to);
        if (from.getY() < to.getY()){
            boolean firstLoop = true;
            int i = to.getX();
            i+= from.getX() < to.getX() ? -diff : diff;
            for (int j = from.getY(); j <= to.getY(); j++){
                if (!firstLoop) {
                    if (from.getX() < to.getX()){
                        if (j % 2 == 0)
                            i++;
                    } else {
                        if (j % 2 == 1)
                            i--;
                    }
                } else {
                    firstLoop = false;
                }
                Tile tile = Automata.INSTANCE.getMap().getTileAt(i, j);
                action.accept(tile, previousPlaced);
//                if (previousPlaced != null){
//                    Network network = Network.getNetworkAt(previousPlaced);
//                    if (network != null) {
//                        try {
//                            network.createConnection(previousPlaced, tile);
//                        } catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
                previousPlaced = tile;
            }
        } else {
            boolean firstLoop = true;
//            int i = to.getX()-diff;
            int i = to.getX();
            i+= from.getX() < to.getX() ? -diff : diff;
            for (int j = from.getY(); j >= to.getY(); j--){
                if (!firstLoop) {
                    if (from.getX() < to.getX()){
                        if (j % 2 == 0)
                            i++;
                    } else {
                        if (j % 2 == 1)
                            i--;
                    }
                } else {
                    firstLoop = false;
                }
                Tile tile = Automata.INSTANCE.getMap().getTileAt(i, j);
                action.accept(tile, previousPlaced);
//                if (previousPlaced != null){
//                    Network network = Network.getNetworkAt(previousPlaced);
//                    if (network != null) {
//                        network.createConnection(previousPlaced, tile);
//                    }
//                }
                previousPlaced = tile;
            }
        }
        return previousPlaced;
    }

    public void setHoverTile(Tile hoverTile) {
//        System.out.println("hovering on "+hoverTile);
        if (this.hoverTile != null){
            this.hoverTile.setHover(null);
            if (placingNetwork) {
                horizontalAction(clickedTile, this.hoverTile, null, (tile, previous) -> tile.setHover(null));
                verticalAction(clickedTile, this.hoverTile, null, (tile, previous) -> tile.setHover(null));
            }
        }
        this.hoverTile = hoverTile;
        if (placingNetwork) {
            horizontalAction(clickedTile, hoverTile, null, (tile, previous) -> tile.setHover(inHand.getTileBase()));
            verticalAction(clickedTile, hoverTile, null, (tile, previous) -> tile.setHover(inHand.getTileBase()));
        }
        if (this.hoverTile != null && inHand != Item.EMPTY){
            this.hoverTile.setHover(inHand.getTileBase(), orientation);
        }
    }

    public void incrementOrientation(int incr){
        this.orientation = Face.GetFace((this.orientation.index+incr)%6);
        System.out.println("orientation = " + orientation);
    }

    public Face getOrientation() {
        return orientation;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Tile getClickedTile() {
        return clickedTile;
    }

    public Tile getHoverTile() {
        return hoverTile;
    }

    public InventoryOverlay getInventoryOverlay() {
        return inventoryOverlay;
    }

    public CraftingOverlay getCraftingOverlay() {
        return craftingOverlay;
    }

    public Storage getInventory() {
        return inventory;
    }

    public Item getInHand() {
        return inHand;
    }

    public int getMiningCounter() {
        return miningCounter;
    }

    public boolean isPlacingNetwork() {
        return placingNetwork;
    }

    public void setPlacingNetwork(boolean placingNetwork) {
        this.placingNetwork = placingNetwork;
    }

    public void setInHand(Item inHand) {
        this.inHand = inHand;
    }
}
