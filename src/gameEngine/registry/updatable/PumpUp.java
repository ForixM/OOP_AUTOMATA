package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Item;
import world.Tile;

public class PumpUp extends UpdatableTile {
    public PumpUp(Tile tile) {
        super(tile);
        initializeElectricalCapability();
        electricConsumption = 10;
    }

    private int counter = 0;

    @Override
    public void update() {
        if (getElectricNetwork().haveEnoughPower(this)) {
            if (!isActive) {
                activate();
            }
            if (counter == Automata.UPS) {
                counter = 0;
                tile.getStorage().insert(new Item(Registration.water.get().getItem(), 1));
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
