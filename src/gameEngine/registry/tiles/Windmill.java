package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.WindmillUp;
import overlays.MachineOverlay;
import overlays.StatsOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Windmill : Tile that can be placed everywhere in the world.
 * Produces electricity by using wind.
 */
public class Windmill extends TileBase implements TileOverlay {
    public Windmill(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new WindmillUp(tile);
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new StatsOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
