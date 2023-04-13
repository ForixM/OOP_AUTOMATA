package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.FurnaceUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OverlayBase;
import scenes.GameScene;
import world.Tile;

/*
 * Furnace : Tile that can be placed everywhere in the world.
 * Uses coal to transform ores in ingots.
 */
public class Furnace extends TileBase implements TileOverlay {
    public Furnace(String registryName) {super(registryName);}

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {return new FurnaceUp(tile);}

    @Override
    public Storage getStorageCapability() {return new Storage(3) {};}

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new FurnaceOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
