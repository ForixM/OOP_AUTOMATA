package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import overlays.FurnaceOverlay;
import overlays.IronChestOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * IronTank: Tile that can be placed everywhere in the world.
 * Is used to store fluids.
 * Can store until 500 units of liquid.
 */
public class IronTank extends TileBase implements TileOverlay {
    public IronTank(String registryName) {
        super(registryName);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(16) {
        };
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new OneSlotOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), slot -> slot.whitelistItems());
    }
}
