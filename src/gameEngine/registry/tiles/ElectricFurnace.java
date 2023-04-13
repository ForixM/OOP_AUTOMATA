package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricFurnaceUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.TwoSlotsOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Furnace : Tile that can be placed everywhere in the world.
 * Uses electricity to transform ores in ingots.
 * Faster than the simple furnace.
 */
public class ElectricFurnace extends TileBase implements TileOverlay {
    public ElectricFurnace(String registryName) {super(registryName);}

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricFurnaceUp(tile);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(2) {};
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new TwoSlotsOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), (filteredSlot, filteredSlot2) -> {});
    }
}
