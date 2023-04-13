package gameEngine.registry.updatable;

import gameEngine.registry.capabilities.UpdatableTile;
import world.Tile;

public class ElectricPoleUp extends UpdatableTile {
    public ElectricPoleUp(Tile tile) {
        super(tile);
        initializeElectricalCapability();
    }

    @Override
    public void update() {

    }
}
