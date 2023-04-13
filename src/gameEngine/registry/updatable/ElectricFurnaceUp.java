package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.Registration;
import gameEngine.registry.capabilities.UpdatableTile;
import utils.Craft;
import world.Item;
import world.Tile;

import java.util.List;

public class ElectricFurnaceUp extends UpdatableTile {
    public ElectricFurnaceUp(Tile tile) {
        super(tile);
        initializeElectricalCapability();
        electricConsumption = 10.0f;
    }
    private int counter = 0;
    private Craft currentCraft = null;

    private List<Craft> listCraft = Craft.furnaceCrafts;

    @Override
    public void update() {
        if (getElectricNetwork().haveEnoughPower(this))
        {
            if (currentCraft == null) {
                for (Craft craft : listCraft) {
                    if (craft.haveIngredients(tile.getStorage())) {
                        if (!isActive) {
                            activate();
                        }
                        currentCraft = craft;
                        break;
                    }
                }
            } else {
                if (currentCraft.haveIngredients(tile.getStorage()) &&
                        (tile.getStorage().getContainer()[1].getBase() == currentCraft.getResult().getBase()
                                || tile.getStorage().isSlotEmpty(1))) {
                    if (counter == Automata.UPS) {
                        for (Item ingredient : currentCraft.getIngredients()) {
                            tile.getStorage().extract(ingredient.getBase().getRegistryName(), ingredient.getCount());
                        }
                        tile.getStorage().insert(currentCraft.getResult().copy(), 1);
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
            if (isActive)
            {
                deactivate();
                counter = 0;
            }
        }
    }
}
