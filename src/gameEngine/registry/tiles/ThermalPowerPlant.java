package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ThermalPowerPlantUp;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Thermal Power Plant : Tile that can be placed everywhere in the world.
 * Produces electricity by using coal.
 */
public class ThermalPowerPlant extends TileBase  implements TileOverlay {
    public ThermalPowerPlant(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ThermalPowerPlantUp(tile){};
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(1) {};
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new OneSlotOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), slot -> slot.whitelistItems(Registration.coal.get()));
    }
}
