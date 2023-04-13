package gameEngine.registry.base;

import overlays.MachineOverlay;
import overlays.OverlayBase;
import world.Tile;

public interface TileOverlay {
    public MachineOverlay GenerateOverlay(Tile tile);
}
