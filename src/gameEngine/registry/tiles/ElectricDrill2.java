package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricDrillUp2;
import gameEngine.registry.updatable.ElectricExtractorUp2;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Drill 2 : A tile that can be placed on ore's deposits.
 * Level up of the electric drill 1.
 */
public class ElectricDrill2 extends TileBase implements TileOverlay {
    public ElectricDrill2(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricDrillUp2(tile);
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
