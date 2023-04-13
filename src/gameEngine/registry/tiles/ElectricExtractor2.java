package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricExtractorUp2;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Extractor 2 : Tile that can be placed on petrol's deposit.
 * Level up of the Electric Extractor 1.
 */
public class ElectricExtractor2 extends TileBase implements TileOverlay {
    public ElectricExtractor2(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricExtractorUp2(tile);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(1) {};
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new OneSlotOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), slot -> slot.whitelistItems());
    }
}
