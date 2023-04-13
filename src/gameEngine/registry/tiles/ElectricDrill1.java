package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricDrillUp1;
import gameEngine.registry.updatable.ElectricExtractorUp1;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Drill 1 : A tile that can be placed on ore's deposits.
 * Extract regularly only ores in the world (not liquid) by using electricity and so insert them
 * on the tile storage.
 */
public class ElectricDrill1 extends TileBase implements TileOverlay {

    public ElectricDrill1(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricDrillUp1(tile);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(1) {
        };
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new OneSlotOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), slot -> slot.whitelistItems());
    }
}
