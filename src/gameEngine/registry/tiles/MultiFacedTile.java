package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.ItemBase;
import gameEngine.registry.base.TileBase;
import gameEngine.rendering.Texture;
import utils.Face;
import utils.MultiFaceOrientation;
import world.Item;
import world.Tile;

import java.io.File;

public abstract class MultiFacedTile extends TileBase {
    private final Texture horizontal;
    private final Texture upRight;
    private final Texture upLeft;
    private final Texture junctionTexture;
    private final Texture half0;
    private final Texture half1;
    private final Texture half2;
    private final Texture half3;
    private final Texture half4;
    private final Texture half5;
    public MultiFacedTile(String registryName) {
        super(registryName, (tileBase -> {
            File textureFile = new File(Automata.TEXTURE_PATH+"/items/"+registryName+".png");
            if (textureFile.exists()){
                tileBase.setItem(new ItemBase(registryName, new Texture(Automata.TEXTURE_PATH+"/items/"+registryName+".png", Item.RENDER_SIZE)) {});
            } else {
                tileBase.setItem(new ItemBase(registryName, new Texture(Automata.TEXTURE_PATH+"/tiles/"+registryName+".png", Item.RENDER_SIZE)) {});
            }

        }));
        this.horizontal = new Texture(Automata.TEXTURE_PATH+"tiles/"+registryName+".png", Tile.RENDER_SIZE);
        this.upRight = new Texture(Automata.TEXTURE_PATH+"tiles/"+registryName+".png", Tile.RENDER_SIZE);
        this.upRight.rotate(-57);
        this.upLeft = new Texture(Automata.TEXTURE_PATH+"tiles/"+registryName+".png", Tile.RENDER_SIZE);
        this.upLeft.rotate(57);
        this.junctionTexture = new Texture(Automata.TEXTURE_PATH+"tiles/"+registryName+"_junction.png", Tile.RENDER_SIZE);
        this.half0 = new Texture(Automata.TEXTURE_PATH+"tiles/"+registryName+"_half.png", Tile.RENDER_SIZE);
        half0.rotate(60);
        this.half1 = half0.copy(60);
        this.half2 = half0.copy(60*2);
        this.half3 = half0.copy(60*3);
        this.half4 = half0.copy(60*4);
        this.half5 = half0.copy(60*5);
    }

    public Texture getTexture(MultiFaceOrientation pipeDirection) {
        return switch (pipeDirection) {
            case JUNCTION -> junctionTexture;
            case UP_LEFT -> upLeft;
            case UP_RIGHT -> upRight;
            case HORIZONTAL -> horizontal;
        };
    }

    public Texture getTexture(Face face) {
        return switch (face) {
            case UP_RIGHT, DOWN_LEFT -> upRight;
            case RIGHT, LEFT -> horizontal;
            case DOWN_RIGHT, UP_LEFT -> upLeft;
        };
    }

    public Texture getHalf0() {
        return half0;
    }

    public Texture getHalf1() {
        return half1;
    }

    public Texture getHalf2() {
        return half2;
    }

    public Texture getHalf3() {
        return half3;
    }

    public Texture getHalf4() {
        return half4;
    }

    public Texture getHalf5() {
        return half5;
    }

    public Texture getHalf(Face face){
       return switch (face) {
            case UP_RIGHT -> half1;
            case RIGHT -> half2;
            case DOWN_RIGHT -> half3;
            case DOWN_LEFT -> half4;
            case LEFT -> half5;
            case UP_LEFT -> half0;
        };
    }

}
