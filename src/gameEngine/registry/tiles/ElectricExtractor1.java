package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricExtractorUp1;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Extractor 1 : Tile that can be placed on petrol's deposit.
 * Uses electricity to extract and insert petrol in the tile storage.
 * Faster than the thermal drill
 */
public class ElectricExtractor1 extends TileBase implements TileOverlay {
    public ElectricExtractor1(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricExtractorUp1(tile);
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
