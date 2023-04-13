package utils;

import gameEngine.registry.base.TileBase;
import gameEngine.registry.tiles.MultiFacedTile;
import world.Tile;

import java.util.*;
import java.util.function.Consumer;

public class Network {
    public static List<Network> networks = new ArrayList<>();
    private static int id_counter = 0;
    private List<NetworkElement> elements;
    private HashMap<Tile, LinkedList<Tile>> connections;
    private TileBase networkType;
    protected int id;

    public Network(){
        this.elements = new ArrayList<>();
        id = id_counter++;
        networks.add(this);
        networkType = null;
        connections = new HashMap<>();
    }

    public void mergeNetworks(List<Network> networks){
        for (Network network : networks) {
            if (network != this) {
                for (NetworkElement element : network.getElements()) {
                    addElement(element);
                }
                network.delete();
            }
        }
    }

    public boolean containsElement(Tile tile){
        for (NetworkElement element : elements) {
            if (element.getTile() == tile)
                return true;
        }
        return false;
    }

    public boolean createConnection(Tile from, Tile to){
        elements.add(new NetworkElement(to));
        if (from != to && containsElement(from) && containsElement(to)) {
            if (connections.containsKey(from)) {
                connections.get(from).add(to);
            } else {
                LinkedList<Tile> connectedTo = new LinkedList<>();
                connectedTo.add(to);
                connections.put(from, connectedTo);
            }
            updateTileDirections(from);
            updateTileDirections(to);
            return true;
        }
        return false;
    }

    private void updateTileDirections(Tile tile){
        List<Face> incomingConnections = getIncomingFaces(tile);
        List<Face> outgoingConnections = getTileOrientation(tile);
        if (incomingConnections.size() == 1 && outgoingConnections.size() == 1){
            Face incomingFace = incomingConnections.get(0);
            Face outgoingFace = outgoingConnections.get(0);
            if (Face.isStraight(incomingFace, outgoingFace)){
                if (incomingFace == Face.RIGHT || incomingFace == Face.LEFT){
                    tile.setMultiFaceDirection(MultiFaceOrientation.HORIZONTAL);
                } else if (incomingFace == Face.UP_LEFT || incomingFace == Face.DOWN_RIGHT){
                    tile.setMultiFaceDirection(MultiFaceOrientation.UP_LEFT);
                } else if (incomingFace == Face.UP_RIGHT || incomingFace == Face.DOWN_LEFT){
                    tile.setMultiFaceDirection(MultiFaceOrientation.UP_RIGHT);
                }
            } else {
                tile.setMultiFaceDirection(MultiFaceOrientation.JUNCTION);
                MultiFacedTile multiFacedTile = (MultiFacedTile) tile.getPlaced();
                tile.getAdditionalRender().add(multiFacedTile.getHalf(incomingFace));
                tile.getAdditionalRender().add(multiFacedTile.getHalf(outgoingFace));
            }
        } else {
            tile.setMultiFaceDirection(MultiFaceOrientation.JUNCTION);
            MultiFacedTile multiFacedTile = (MultiFacedTile) tile.getPlaced();
            for (Face incomingFace : incomingConnections) {
                tile.getAdditionalRender().add(multiFacedTile.getHalf(incomingFace));
            }
            for (Face outgoingFace : outgoingConnections) {
                tile.getAdditionalRender().add(multiFacedTile.getHalf(outgoingFace));
            }
        }
    }

    public List<Tile> getConnectionsFrom(Tile from){
        if (connections.containsKey(from)){
            return connections.get(from);
        }
        return null;
    }

    public List<Face> getIncomingFaces(Tile tile){
        List<Face> faces = new ArrayList<>();
        Tile[] around = getTilesAround(tile);
        for (int i = 0; i < around.length; i++) {
            if (around[i] != null) {
                LinkedList<Tile> list = connections.get(around[i]);
                if (list != null && list.contains(tile)) {
                    faces.add(Face.GetFace(i));
                }
            }
        }
        return faces;
    }

    public List<Tile> getIncomingConnections(Tile tile){
        ArrayList<Tile> list = new ArrayList<>();
        connections.forEach((from, tiles) -> {
            if (tiles.contains(tile)){
                list.add(from);
            }
        });
        return list;
    }

    public HashMap<Tile, LinkedList<Tile>> getConnections() {
        return connections;
    }

    public void prettyPrintConnections(){
        System.out.println("{");
        connections.forEach((tile, list) -> {
            System.out.print(tile +" = ");
            System.out.println(list+",");
        });
        System.out.println("}");
    }

    public static List<Network> getNearbyNetworks(Tile tile){
        List<Network> networks1 = new ArrayList<>();
        for (Network network : networks) {
            if (tile.getPlaced() != network.networkType) continue;
            if (network.isCoordinateClose(tile.getX(), tile.getY())){
                networks1.add(network);
            }
        }
        return networks1;
    }

    public static Network getNetworkAt(Tile tile){
        for (Network network : networks) {
            for (NetworkElement element : network.elements) {
                if (element.getTile() == tile){
                    return network;
                }
            }
        }
        Network newNetwork = new Network();
        newNetwork.addElement(tile);
        return newNetwork;
    }

    public NetworkElement getNetworkElement(Tile tile){
        for (NetworkElement element : elements) {
            if (element.getTile() == tile){
                return element;
            }
        }
        return null;
    }

    public boolean isTileClose(Tile tile){
        return isCoordinateClose(tile.getX(), tile.getY());
    }

    public boolean isCoordinateClose(int x, int y){
        for (NetworkElement element : elements) {
            int xDistance = x - element.getTile().getX();
            int yDistance = y - element.getTile().getY();
            if ((y) % 2 == 1) {
                if (yDistance >= -1 && yDistance <= 1) {
                    if (xDistance == -1 || xDistance == 0) {
                        return true;
                    }
                }
                if (xDistance == 1 && yDistance == 0) {
                    return true;
                }
            } else {
                if (xDistance == 0){
                    if (yDistance == -1 || yDistance == 1){
                        return true;
                    }
                }
                if (xDistance == 1){
                    if (yDistance >= -1 && yDistance <= 1){
                        return true;
                    }
                }
                if (xDistance == -1 && yDistance == 0){
                    return true;
                }
            }
        }
        return false;
    }

    private List<Face> getTileOrientation(Tile tile){
        ArrayList<Face> directions = new ArrayList<>();
        if (connections.containsKey(tile)){
            LinkedList<Tile> list = connections.get(tile);
            Tile[] tilesAround = getTilesAround(tile);
            for (int i = 0; i < tilesAround.length; i++) {
                for (Tile tile1 : list) {
                    if (tilesAround[i] == tile1){
                        directions.add(Face.GetFace(i));
                    }
                }
            }
            return directions;
        }
        return directions;
    }

    public Tile[] getTilesAround(Tile tile){
        if (containsElement(tile)){
            Tile[] tiles = new Tile[6];
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = null;
            }
            for (NetworkElement networkElement : elements) {
                Tile element = networkElement.getTile();
                if (element.getX() == tile.getX()+1 && element.getY() == tile.getY()){
                    tiles[1] = element;
                }
                if (element.getX() == tile.getX()-1 && element.getY() == tile.getY()){
                    tiles[4] = element;
                }
                if (tile.getY() % 2 == 0){
                    if (element.getX() == tile.getX()-1 && element.getY() == tile.getY()-1){
                        tiles[5] = element;
                    }
                    if (element.getX() == tile.getX() && element.getY() == tile.getY()-1){
                        tiles[0] = element;
                    }
                    if (element.getX() == tile.getX() && element.getY() == tile.getY()+1){
                        tiles[2] = element;
                    }
                    if (element.getX() == tile.getX()-1 && element.getY() == tile.getY()+1){
                        tiles[3] = element;
                    }
                } else {
                    if (element.getX() == tile.getX() && element.getY() == tile.getY()-1){
                        tiles[5] = element;
                    }
                    if (element.getX() == tile.getX()+1 && element.getY() == tile.getY()-1){
                        tiles[0] = element;
                    }
                    if (element.getX() == tile.getX()+1 && element.getY() == tile.getY()+1){
                        tiles[2] = element;
                    }
                    if (element.getX() == tile.getX() && element.getY() == tile.getY()+1){
                        tiles[3] = element;
                    }
                }
            }
            return tiles;
        }
        return null;
    }

    public void makeActionOnAllTiles(Consumer<Tile> runnable){
        for (NetworkElement element : elements) {
            runnable.accept(element.getTile());
        }
    }

    public void addElement(Tile tile){
        if (networkType == null){
            networkType = tile.getPlaced();
        }
        this.elements.add(new NetworkElement(tile));
    }

    public void addElement(NetworkElement networkElement){
        if (networkType == null){
            networkType = networkElement.getTile().getPlaced();
        }
        this.elements.add(networkElement);
    }

    public List<NetworkElement> getElements() {
        return elements;
    }

    public int getNetworkId() {
        return id;
    }

    public void setNetworkId(int id){
        this.id = id;
    }

    public void delete(){
        networks.remove(this);
        for (int i = 0; i < networks.size()-1; i++) {
            if (networks.get(i+1).getNetworkId() - networks.get(i).getNetworkId() > 1){
                networks.get(i+1).setNetworkId(networks.get(i).getNetworkId()+1);
            }
        }
        id_counter--;
    }
}
