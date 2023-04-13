package gameEngine.registry.base;

import gameEngine.Automata;
import gameEngine.registry.Registrable;
import gameEngine.registry.Registration;
import gameEngine.registry.RegistryObject;
import gameEngine.rendering.Texture;
import world.Item;
import world.Tile;

public abstract class Deposit extends Registrable {
    private Texture texture;
    public Deposit(String registryName) {
        super(registryName);
        this.texture = null;
        this.texture = new Texture(Automata.TEXTURE_PATH+"tiles/deposit/"+registryName+".png", Tile.RENDER_SIZE);
    }

    public Texture getTexture() {
        return texture;
    }

    public ItemBase getLoot() {
        String itemRegistryName = registryName.replace("_deposit", "");
        //System.out.println("itemRegistryName = " + itemRegistryName);
        RegistryObject registryObject = Registration.getRegistryObjectByRegistryName(itemRegistryName);
        if (registryObject == null){
            registryObject = Registration.getRegistryObjectByRegistryName(itemRegistryName+"_ore");
        }
        if (registryObject.get() instanceof ItemBase base){
            return base;
        }
        return null;
    }
}
