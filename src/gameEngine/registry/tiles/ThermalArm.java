package gameEngine.registry.tiles;

import gameEngine.registry.base.TileBase;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ThermalArmUp;
import gameEngine.rendering.Texture;
import world.Tile;

public class ThermalArm extends TileBase {
    private Texture movableArm;
    public ThermalArm(String registryName) {
        super(registryName);
        this.movableArm = new Texture("assets/textures/tiles/arm_clamp.png", Tile.RENDER_SIZE);
    }

    public Texture getMovableArm() {
        return movableArm;
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ThermalArmUp(tile);
    }
}
