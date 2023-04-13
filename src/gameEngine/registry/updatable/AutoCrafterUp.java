package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.capabilities.UpdatableTile;
import utils.Craft;
import world.Item;
import world.Tile;

import java.util.Arrays;

public class AutoCrafterUp extends UpdatableTile {

    private int counter = 0;

    private Craft selectedCraft = null;
    public AutoCrafterUp(Tile tile) {
        super(tile);
    }

    @Override
    public void update() {
        if(selectedCraft != null)
        {
            if(selectedCraft.haveIngredients(Arrays.asList(tile.getStorage().getContainer()))) {
                if (counter == Automata.UPS) {
                    counter = 0;
                        for (Item ingredient : selectedCraft.getIngredients()) {
                            tile.getStorage().extract(ingredient.getTileBase().getRegistryName(), ingredient.getCount());
                        }
                        tile.getStorage().insert(selectedCraft.getResult().copy());

                } else {
                    counter++;
                }
            } else {
                counter = 0;
            }
        }
    }
}
