package gameEngine.registry.tiles;

import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.BeltUp;
import world.Tile;

/*
 * Belt : Tile that can be placed everywhere in the world.
 * Transport all items.
 */
public class Belt extends MultiFacedTile {
    public Belt(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new BeltUp(tile);
    }
}
