package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import utils.Craft;
import world.Item;
import world.Tile;

import java.util.List;
/*
 * Furnace : Tile that can be placed everywhere in the world.
 * Uses coal to transform ores in ingots.
 */

public class FurnaceUp extends UpdatableTile {
    public FurnaceUp(Tile tile) {super(tile);}

    private int counter = 0;
    private int counter2 = 0;
    private Craft currentCraft = null;

    private List<Craft> listCraft = Craft.furnaceCrafts;


    @Override
    public void update() {
        if (tile.getStorage().containsAtLeast(new Item(Registration.coal.get(), 1))) {
            if (currentCraft == null) {
                for (Craft craft : listCraft) {
                    if (craft.haveIngredients(tile.getStorage())) {
                        currentCraft = craft;
                        break;
                    }
                }
            } else {
                if (currentCraft.haveIngredients(tile.getStorage()) && !tile.getStorage().isSlotEmpty(0) &&
                        (tile.getStorage().getContainer()[2].getBase() == currentCraft.getResult().getBase()
                         || tile.getStorage().isSlotEmpty(2))) {
                    if (counter == 2 * Automata.UPS) {
                        if (counter2 == 4) {
                            tile.getStorage().extract("coal", 1);
                            counter2 = 0;
                        }
                        for (Item ingredient : currentCraft.getIngredients()) {
                            tile.getStorage().extract(ingredient.getBase().getRegistryName(), ingredient.getCount());
                        }
                        tile.getStorage().insert(currentCraft.getResult().copy(), 2);
                        counter2++;
                        currentCraft = null;
                        counter = 0;
                    } else {
                        counter++;
                    }
                } else {
                    currentCraft = null;
                    counter = 0;
                }
            }
        } else {
            currentCraft = null;
            counter = 0;
        }
    }
}
