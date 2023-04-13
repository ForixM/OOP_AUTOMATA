package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ThermalDrillUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import overlays.TwoSlotsOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Thermal Drill : Tile that can be placed everywhere in the world.
 * Extract regularly only ores in the world (not liquid) by using coal and so insert them
 * in the tile storage.
 */
public class ThermalDrill extends TileBase implements TileOverlay {
    public ThermalDrill(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ThermalDrillUp(tile);
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(2) {};
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new TwoSlotsOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), (filteredSlot, filteredSlot2) -> {
            filteredSlot.whitelistItems(Registration.coal.get());
        });
    }
}
