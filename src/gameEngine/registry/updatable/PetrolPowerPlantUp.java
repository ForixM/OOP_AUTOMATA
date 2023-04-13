package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Item;
import world.Tile;

public class PetrolPowerPlantUp extends UpdatableTile {
    public PetrolPowerPlantUp(Tile tile) {
        super(tile);
        super.electricProduction = 150.0f;
        initializeElectricalCapability();
    }
    private int counter = 0;

    @Override
    public void update() {
        if (tile.getStorage().contains(Registration.petrol.get()))
        {
            if (!isActive)
                activate();
            if (counter == 5*Automata.UPS) {
                counter = 0;
                tile.getStorage().extract("petrol", 1);
            } else {
                counter++;
            }
        } else {
            if (isActive) {
                deactivate();
                counter = 0;
            }
        }
    }
}
