package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Item;
import world.Tile;

/*
 * Electric Drill 1 : A tile that can be placed on ore's deposits.
 * Extract regularly only ores in the world (not liquid) by using electricity and so insert them
 * on the tile storage.
 */

public class ElectricDrillUp1 extends UpdatableTile {
    private int counter = 0;
    public ElectricDrillUp1(Tile tile) {
        super(tile);
        initializeElectricalCapability();
        electricConsumption = 10.0f;
    }

    @Override
    public void update() {
        if(tile.getDeposit() != null && !tile.getDeposit().getRegistryName().equals("petrol_deposit")) {
            if (getElectricNetwork().haveEnoughPower(this)) {
                if (!isActive) {
                    activate();
                }
                if (counter == 5* Automata.UPS) {
                    counter = 0;
                    tile.getStorage().insert(new Item(tile.getDeposit().getLoot(), 1));
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
}
