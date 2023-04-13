package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Item;
import world.Tile;

/*
 * Thermal Drill : Tile that can be placed everywhere in the world.
 * Extract regularly only ores in the world (not liquid) by using coal and so insert them
 * in the tile storage.
 */
public class ThermalDrillUp extends UpdatableTile {

    private int counter = 0;
    private int counter2 = 0;
    public ThermalDrillUp(Tile tile) {
        super(tile);
    }

    @Override
    public void update() {
        if (tile.getDeposit() != null && !tile.getDeposit().getRegistryName().equals("petrol")) {
            if (tile.getStorage().containsAtLeast(new Item(Registration.coal.get(), 1)) &&
                    !tile.getDeposit().getLoot().getRegistryName().equals("petrol")&&
                    !tile.getStorage().isSlotEmpty(0)) {
                if (counter == 2*Automata.UPS)
                {
                    if (counter2 == 5) {
                        tile.getStorage().extract("coal", 1);
                        counter2 = 0;
                    }
                    tile.getStorage().insert(new Item(tile.getDeposit().getLoot(), 1), 1);
                    counter2++;
                    counter = 0;
                }
                else {
                    counter++;
                }
            }
            else {
                counter = 0;
                counter2 = 0;
            }
        }
    }
}
