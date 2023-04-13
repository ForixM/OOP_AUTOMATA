package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import overlays.FurnaceOverlay;
import overlays.GoldChestOverlay;
import overlays.MachineOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * GoldChest : Level up of the IronChest.
 * Can store until 32 items.
 */
public class GoldChest extends TileBase implements TileOverlay {
    public GoldChest(String registryName) {
        super(registryName);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(32) {
        };
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new GoldChestOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
