package gameEngine.registry.tiles;

import gameEngine.Automata;
import gameEngine.registry.base.TileBase;
import gameEngine.registry.base.TileOverlay;
import overlays.MachineOverlay;
import overlays.OneSlotOverlay;
import overlays.StatsOverlay;
import scenes.GameScene;
import world.Tile;

/*
 * Robot : Tile that can be placed everywhere in the world.
 * => Goal of the game. <=
 * Will allow the gamer to increase productivity of all his engines by 5%.
 */
public class Robot extends TileBase implements TileOverlay {
    public Robot(String registryName) {
        super(registryName);
    }

    @Override
    public MachineOverlay GenerateOverlay(Tile tile) {
        return new StatsOverlay((GameScene) Automata.INSTANCE.getScene(), tile.getStorage());
    }
}
