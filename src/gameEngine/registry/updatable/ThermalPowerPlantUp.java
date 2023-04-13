package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Tile;

/*
 * Thermal Power Plant : Tile that can be placed everywhere in the world.
 * Produces electricity by using coal.
 */

public class ThermalPowerPlantUp extends UpdatableTile {
    private int counter = 0;
    public ThermalPowerPlantUp(Tile tile) {
        super(tile);
        super.electricProduction = 100.0f;
        initializeElectricalCapability();
    }

    @Override
    public void update() {
        if (tile.getStorage().contains(Registration.coal.get())) {
            if (!isActive)
                activate();
            if (counter == 10*Automata.UPS) {
                tile.getStorage().extract("coal", 1);
                counter = 0;
            } else {
                counter++;
            }
        } else{
            if (isActive) {
                deactivate();
                counter = 0;
            }
        }
    }
}
