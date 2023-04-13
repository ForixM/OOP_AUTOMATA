package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.AutoCrafterUp;
import gameEngine.registry.updatable.RefineryUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import overlays.RefineryOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Refinery : Tile that can be placed everywhere in the world.
 * Produces plastic.
 */
public class Refinery extends TileBase implements TileOverlay {
    public Refinery(String registryName) {
        super(registryName);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(4) {
        };
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new RefineryUp(tile);
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new RefineryOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
