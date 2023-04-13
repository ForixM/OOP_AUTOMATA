package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.capabilities.UpdatableTile;
import utils.Craft;
import world.Item;
import world.Tile;

import java.util.Arrays;
import java.util.List;
/*
* Refinery : Tile that can be placed everywhere in the world.
* Produces plastic.
*/
public class RefineryUp extends UpdatableTile {

    public RefineryUp(Tile tile) {
        super(tile);
        super.electricConsumption = 10.0f;
        initializeElectricalCapability();
    }

    private int counter = 0;
    private Craft currentCraft = null;

    private List<Craft> listCraft = Craft.refineryCrafts;

    @Override
    public void update() {
        if (getElectricNetwork().haveEnoughPower(this)) {
            if (!isActive)
            {
                activate();
            }
            if (currentCraft == null) {
                for (Craft craft : listCraft) {
                    if (craft.haveIngredients(tile.getStorage())) {
                        currentCraft = craft;
                        break;
                    }
                }
            } else {
                if (currentCraft.haveIngredients(tile.getStorage()) && !tile.getStorage().isSlotEmpty(0)
                        && !tile.getStorage().isSlotEmpty(1) && !tile.getStorage().isSlotEmpty(2) &&
                        (tile.getStorage().getContainer()[3].getBase() == currentCraft.getResult().getBase()
                                || tile.getStorage().isSlotEmpty(3))) {
                    if (counter ==  Automata.UPS) {
                        for (Item ingredient : currentCraft.getIngredients()) {
                            tile.getStorage().extract(ingredient.getBase().getRegistryName(), ingredient.getCount());
                        }
                        tile.getStorage().insert(currentCraft.getResult().copy(), 3);
                        counter = 0;
                        currentCraft = null;
                    } else {
                        counter++;
                    }
                } else {
                    counter = 0;
                    currentCraft = null;
                }
            }
        } else {
            if (isActive)
            {
                deactivate();
                counter = 0;
            }
        }
    }
}
