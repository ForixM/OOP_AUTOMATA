package gameEngine.registry.updatable;

import gameEngine.Automata;
import gameEngine.registry.capabilities.UpdatableTile;
import world.Tile;

public class ThermalArmUp extends UpdatableTile {
    private Tile from;
    private Tile to;
    public ThermalArmUp(Tile tile) {
        super(tile);
        System.out.println("tile.getOrientation().getOppositeSide() = " + tile.getOrientation().getOppositeSide());
        this.from = Automata.INSTANCE.getMap().getAdjascentTile(tile, tile.getOrientation().getOppositeSide());
        this.to = Automata.INSTANCE.getMap().getAdjascentTile(tile, tile.getOrientation());
        System.out.println("from.getOrientation() = " + from.getOrientation());
        System.out.println("to.getOrientation() = " + to.getOrientation());
    }

    @Override
    public void update() {
//        System.out.println("tile.getOrientation() = " + tile.getOrientation());
    }
}
