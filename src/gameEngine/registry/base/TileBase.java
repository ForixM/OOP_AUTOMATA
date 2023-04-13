package gameEngine.registry.base;

import gameEngine.Automata;
import gameEngine.registry.Registrable;
import gameEngine.registry.capabilities.Storage;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.rendering.Texture;
import world.Tile;

import java.io.File;
import java.util.function.Consumer;

public abstract class TileBase extends Registrable {
    private Texture texture;
    private ItemBase item;
    public TileBase(String registryName){
        super(registryName);
        this.texture = new Texture("assets/textures/tiles/"+registryName+".png", Tile.RENDER_SIZE);
        this.item = new ItemBase(registryName, texture) {};
    }

    public TileBase(String registryName, Consumer<TileBase> customInitializer){
        super(registryName);
        customInitializer.accept(this);
    }

    public Texture getTexture() {
        return texture;
    }

    public ItemBase getItem(){
        return item;
    }

    public UpdatableTile getUpdatableCapability(Tile tile){
        return null;
    }

    public Storage getStorageCapability(){
        return null;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setItem(ItemBase item) {
        this.item = item;
    }

}
