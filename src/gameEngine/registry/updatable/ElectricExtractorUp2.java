package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Item;
import world.Tile;

public class ElectricExtractorUp2 extends UpdatableTile {

    private int counter = 0;

    public ElectricExtractorUp2(Tile tile) {
        super(tile);
        initializeElectricalCapability();
        electricConsumption = 15;
    }

    @Override
    public void update() {
        if (tile.getDeposit() != null && tile.getDeposit().getRegistryName().equals("petrol_deposit")) {
            if (getElectricNetwork().haveEnoughPower(this)) {
                if (!isActive) {
                    activate();
                }
                if (counter == Automata.UPS) {
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
