package utils;

import gameEngine.registry.capabilities.Storage;
import world.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Craft {
    public static List<Craft> crafts = new ArrayList<>();
    public static List<Craft> furnaceCrafts = new ArrayList<>();
    public static List<Craft> refineryCrafts = new ArrayList<>();
    private List<Item> ingredients;
    private Item result;

    private Craft(Item result, List<Item> ingredients){
        this.ingredients = new ArrayList<>();
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
    }

    public List<Item> getIngredients() {
        return ingredients;
    }

    public Item getResult() {
        return result;
    }

    public boolean haveIngredients(List<Item> items){
        for (Item ingredient : ingredients) {
            boolean found = false;
            for (Item item : items) {
                if (item != Item.EMPTY && item.sameItem(ingredient) && item.getCount() >= ingredient.getCount()) {
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public boolean haveIngredients(Storage storage){
        return haveIngredients(Arrays.asList(storage.getContainer()));
    }

    public static List<Craft> getCraftable(List<Item> items){
        List<Craft> toReturn = new ArrayList<>();
        for (Craft craft : crafts) {
            if (craft.haveIngredients(items)){
                toReturn.add(craft);
            }
        }
        return toReturn;
    }

    public static void registerCraft(Item result, List<Item> ingredients){
        crafts.add(new Craft(result, ingredients));
    }
    public static void registerFurnaceCraft(Item result, List<Item> ingredients){
        furnaceCrafts.add(new Craft(result, ingredients));
    }
    public static void registerRefineryCraft(Item result, List<Item> ingredients){
        refineryCrafts.add(new Craft(result, ingredients));
    }

}
