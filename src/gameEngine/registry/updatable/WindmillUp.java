package gameEngine.registry.updatable;

import gameEngine.registry.capabilities.UpdatableTile;
import world.Tile;

/*
 * Windmill : Tile that can be placed everywhere in the world.
 * Produces electricity by using wind.
 */
public class WindmillUp extends UpdatableTile {

    public WindmillUp(Tile tile) {
        super(tile);
        super.electricProduction = 100.0f;
        initializeElectricalCapability();
        activate();
    }

    @Override
    public void update() {}
}
