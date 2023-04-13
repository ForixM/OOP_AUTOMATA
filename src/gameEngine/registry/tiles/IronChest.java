package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import overlays.FurnaceOverlay;
import overlays.IronChestOverlay;
import overlays.MachineOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * IronChest: Tile that can be placed everywhere in the world.
 * Is used to store items.
 * Can store until 16 items.
 */
public class IronChest extends TileBase implements TileOverlay {
    public IronChest(String registryName) {
        super(registryName);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(16) {
        };
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new IronChestOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
