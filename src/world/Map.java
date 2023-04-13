package world;

import gameEngine.registry.capabilities.UpdatableTile;
import utils.Face;
import utils.PerlinNoise2D;
import utils.Voronoi;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Map {
    private List<Region> regions;
    private List<UpdatableTile> updatableTiles;
    private List<Region> renderedRegions;

    // coal Voronoi Generation seed and parameters
    private Voronoi coalGeneration;
    // iron Voronoi Generation seed and parameters
    private Voronoi ironGeneration;
    // copper Voronoi Generation seed and parameters
    private Voronoi copperGeneration;
    // stone Voronoi Generation seed and parameters
    private Voronoi stoneGeneration;
    // petrol Voronoi Generation seed and parameters
    private Voronoi petrolGeneration;
    // gold Voronoi Generation seed and parameters
    private Voronoi goldGeneration;

    public Map(){
        this.regions = new ArrayList<>();
        this.updatableTiles = new ArrayList<>();
        this.renderedRegions = new LinkedList<>();
        PerlinNoise2D.WIDTH = Region.REGION_SIZE;
        PerlinNoise2D.HEIGHT = Region.REGION_SIZE;


        // ----- VORONOI GENERATION -----
        // more the frequency (first parameter) is big, more the size of the vein is big
        // ------------------------------

        // coal voronoi generation for coalOre
        this.coalGeneration = new Voronoi(0.2, 1, false, 643872423);
        // iron voronoi generation for ironOre
        this.ironGeneration = new Voronoi(0.2, 1, false, 784913434);
        // copper voronoi generation for copperOre
        this.copperGeneration = new Voronoi(0.25, 1, false, 312313242);
        // stone voronoi generation for stoneOre
        this.stoneGeneration = new Voronoi(0.3, 1, false, 876543234);
        // petrol voronoi generation for petrolDeposit
        this.petrolGeneration = new Voronoi(0.7, 1, false, 12345654);
        // gold voronoi generation for goldOre
        this.goldGeneration = new Voronoi(0.8, 1, false, 24324321);

        // ------------------------------

        // generate regions around the window(5x5 regions)
        for (int x = -10; x < 10; x++){
            for (int y = -10; y < 10; y++){
                Region region = new Region(x, y, this);
                regions.add(region);
            }
        }
    }
    public Voronoi getCoalGeneration() {
        return coalGeneration;
    }

    public Voronoi getIronGeneration() {
        return ironGeneration;
    }

    public Voronoi getCopperGeneration() {
        return copperGeneration;
    }

    public Voronoi getStoneGeneration() {
        return stoneGeneration;
    }

    public Voronoi getPetrolGeneration() {
        return petrolGeneration;
    }

    public Voronoi getGoldGeneration() {
        return goldGeneration;
    }

    public void renderMap(Graphics g){
        double currentX = Player.POS_X / (Region.REGION_SIZE*Tile.RENDER_SIZE) *-1;
        double currentY = Player.POS_Y / (Region.REGION_SIZE*Tile.RENDER_SIZE*0.75) *-1;
        renderedRegions.clear();
        for (int dX = -10; dX <= 10; dX++){
            for (int dY = -10; dY <= 10; dY++){
                renderedRegions.add(getRegion((int) currentX+dX, (int) currentY+dY));
//                getRegion((int) currentX+dX, (int) currentY+dY).renderRegion(g);
            }
        }
        for (Region renderedRegion : renderedRegions) {
            renderedRegion.renderRegion(g);
        }
    }

    public void updateTiles(){
        updatableTiles.forEach(UpdatableTile::update);
    }

    public void addUpdatableTile(UpdatableTile updatableTile){
        updatableTiles.add(updatableTile);
    }

    public Tile getTileAt(int x, int y){
        int regionX;
        int regionY;
        if (x >= 0)
            regionX = x/Region.REGION_SIZE;
        else
            regionX = x/Region.REGION_SIZE-1;
        if (y >= 0)
            regionY = y/Region.REGION_SIZE;
        else
            regionY = y/Region.REGION_SIZE-1;
        System.out.println(x%16);
        x%=16;
        y%=16;
        if (x < 0){
            x = 16+x;
        }
        if (y < 0){
            y = 16+y;
        }
        return getRegion(regionX, regionY).getTileAt(x, y);
    }

    public Tile[] getTilesAround(Tile tile){
        return getTilesAround(tile.getX(), tile.getY());
    }

    public Tile[] getTilesAround(int x, int y){
        Tile[] tiles = new Tile[6];
        tiles[2] = getTileAt(x+1, y);
        tiles[5] = getTileAt(x-1, y);
        if ((y) % 2 == 1) {
            tiles[0] = getTileAt(x-1, y-1);
            tiles[1] = getTileAt(x, y-1);
            tiles[3] = getTileAt(x, y+1);
            tiles[4] = getTileAt(x-1, y+1);
        } else {
            tiles[0] = getTileAt(x, y-1);
            tiles[1] = getTileAt(x+1, y-1);
            tiles[3] = getTileAt(x+1, y+1);
            tiles[4] = getTileAt(x, y+1);
        }
        return tiles;
    }

    public Tile getAdjascentTile(Tile tile, Face face){
        Tile[] tilesAround = getTilesAround(tile);
        for (int i = 0; i < tilesAround.length; i++) {
            if (i == face.index){
                return tilesAround[i];
            }
        }
        return null;
    }

    public Region getRegion(int x, int y){
        Optional<Region> result = regions.stream().filter(r -> r.getX() == x && r.getY() == y).findFirst();
        if (result.isPresent()){
            return result.get();
        }
        Region newRegion = new Region(x, y, this);
        regions.add(newRegion);
        return newRegion;
    }

    public List<Region> getRenderedRegions() {
        return renderedRegions;
    }
}
