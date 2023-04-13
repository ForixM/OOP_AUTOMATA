package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.PumpUp;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Pump : Tile that can be placed everywhere in the world.
 * Uses electricity to extract and insert water in the tile storage.
 */
public class Pump extends TileBase implements TileOverlay {
    public Pump(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new PumpUp(tile);
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
