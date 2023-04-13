package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.PetrolPowerPlantUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * PetrolPowerPlant : Tile that can be placed everywhere in the world.
 * Produces electricity by using petrol.
 */
public class PetrolPowerPlant extends TileBase implements TileOverlay {
    public PetrolPowerPlant(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new PetrolPowerPlantUp(tile){};
    }

    @Override
    public Storage getStorageCapability() {
        return new Storage(1) {};
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new OneSlotOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage(), slot -> slot.whitelistItems(Registration.petrol.get()));
    }
}
