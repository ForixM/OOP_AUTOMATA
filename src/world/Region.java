package world;

import gameEngine.registry.Registration;
import utils.PerlinNoise2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Region {

    // Tiles in the region (the grid/matrix of tiles in the region)
    private Tile[][] tiles;
    // region size
    public static final int REGION_SIZE = 16;
    // coordinates
    private int x, y;


    // constructor
    public Region(int x, int y, Map map){

        // generate a region (of tiles) which is a grid of tiles of 'region_size'x'region_size' size
        this.tiles = new Tile[REGION_SIZE][REGION_SIZE];

        // get the perlin noise generation for the region oceans
        BufferedImage noise = PerlinNoise2D.getNoiseImage(0, REGION_SIZE*x, REGION_SIZE*y, 1);

        // get the local coordinates to generate the tile
        // local : the coordinates in the region only not in the entire map
        for (int localX = 0; localX < REGION_SIZE; localX++) {
            for (int localY = 0; localY < REGION_SIZE; localY++) {
                // get noise result for ocean generation
                int result = noise.getRGB(localX, localY);

                // get coal Voronoi generation to know where to place coal deposit
                double coalDeposit = map.getCoalGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);
                // get iron Voronoi generation to know where to place iron deposit
                double ironDeposit = map.getIronGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);
                // get copper Voronoi generation to know where to place copper deposit
                double copperDeposit = map.getCopperGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);
                // get stone Voronoi generation to know where to place stone deposit
                double stoneDeposit = map.getStoneGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);
                // get petrol Voronoi generation to know where to place petrol deposit
                double petrolDeposit = map.getPetrolGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);
                // get gold Voronoi generation to know where to place gold deposit
                double goldDeposit = map.getGoldGeneration().get(localX+(x*REGION_SIZE), localY+(y*REGION_SIZE), 1);

                // bit shifting to get values of biome generation
                int r = (result & 0xFF0000) >> 16;

                // WORLD GENERATION USING PERLIN NOISE
                if (r <= 100) // biome is ocean
                    tiles[localX][localY] = new Tile(Registration.water.get(), localX+(x*REGION_SIZE), localY+(y*REGION_SIZE));
                else if (r <= 120) // biome is beach (near oceans)
                    tiles[localX][localY] = new Tile(Registration.sand.get(), localX+(x*REGION_SIZE), localY+(y*REGION_SIZE));
                else {
                    // DEPOSITS GENERATIONS

                    // COAL
                    int coalRGB = 0x010101 * (int) ((coalDeposit + 1) * 127.5);
                    // get the color of the deposit (variation of height in the height map)
                    Color coalColor = new Color(coalRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (coalColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.coalDeposit.get());
                        continue;
                    }

                    // IRON
                    int ironRGB = 0x010101 * (int) ((ironDeposit + 1) * 127.5);
                    // get the color of the deposit (variation of height in the height map)
                    Color ironColor = new Color(ironRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (ironColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.ironDeposit.get());
                        continue;
                    }

                    // COPPER
                    int copperRGB = 0x010101 * (int) ((copperDeposit + 1) * 127.5);
                    // get the color of the deposit (variation of height in the height map)
                    Color copperColor = new Color(copperRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (copperColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.copperDeposit.get());
                        continue;
                    }

                    // STONE
                    int stoneRGB = 0x010101 * (int) ((stoneDeposit + 1) * 127.5);
                    // get the color of the deposit (variation of height in the height map)
                    Color stoneColor = new Color(stoneRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (stoneColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.stoneDeposit.get());
                        continue;
                    }

                    // PETROL
                    int petrolRGB = 0xffffff * (int) ((petrolDeposit + 1) * 300);
                    // get the color of the deposit (variation of height in the height map)
                    // 300 because petrol is rare
                    Color petrolColor = new Color(petrolRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (petrolColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.petrolDeposit.get());
                        continue;
                    }

                    // GOLD
                    int goldRGB = 0xffffff * (int) ((goldDeposit + 1) * 500);
                    // get the color of the deposit (variation of height in the height map)
                    // 500 because gold is super rare
                    Color goldColor = new Color(goldRGB);
                    // the color is equal to 0 (height == 0) -> generate deposit
                    if (goldColor.getRed() == 0) {
                        tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE), Registration.goldDeposit.get());
                        continue;
                    }

                    // in all cases, generate grass under the deposit (because deposits is generated only on grass)
                    tiles[localX][localY] = new Tile(Registration.grass.get(), localX + (x * REGION_SIZE), localY + (y * REGION_SIZE));
                }
            }
        }
        // get coordinates
        this.x = x;
        this.y = y;
    }

    // render each tile in the region
    public void renderRegion(Graphics g){
        for (Tile[] tileTable : tiles) {
            for (Tile tile : tileTable) {
                tile.render(g);
            }
        }
    }

    // getter for tiles (the list of the main matrix  of tiles)
    public Tile[][] getTiles() {
        return tiles;
    }

    // getter for a tile from the tiles (the tile of the list of tiles of the main matrix of tiles)
    public Tile getTileAt(int x, int y){
        return tiles[x][y];
    }

    // get X coordinate of the region
    public int getX() {
        return x;
    }

    // get Y coordinate of the region
    public int getY() {
        return y;
    }
}
