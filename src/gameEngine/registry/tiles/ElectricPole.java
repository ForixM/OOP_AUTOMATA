package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.updatable.ElectricPoleUp;
import overlays.FurnaceOverlay;
import overlays.MachineOverlay;
import overlays.StatsOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Electric Pole : Tile that can be placed everywhere in the world.
 * Distribute electricity to the electric engines.
 */
public class ElectricPole extends TileBase implements TileOverlay {
    public ElectricPole(String registryName) {
        super(registryName);
    }

    @Override
    public UpdatableTile getUpdatableCapability(Tile tile) {
        return new ElectricPoleUp(tile);
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new StatsOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
