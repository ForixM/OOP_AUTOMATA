package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.AutoCrafterUp;
import overlays.AutocrafterOverlay;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * AutoCrafter : Tile that can be placed everywhere in the world.
 * Automatic engine that can create faster all the objects in the inventory.
 */
public class AutoCrafter extends TileBase implements TileOverlay {

    public AutoCrafter(String registryName) {
        super(registryName);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(6) {
        };
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new AutoCrafterUp(tile);
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new AutocrafterOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
