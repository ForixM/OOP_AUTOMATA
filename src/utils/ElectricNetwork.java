package utils;

import gameEngine.registry.capabilities.UpdatableTile;
import gameEngine.registry.tiles.ElectricPole;
import world.Tile;

import java.util.ArrayList;
import java.util.List;

public class ElectricNetwork {
    public static List<ElectricNetwork> electricNetworks = new ArrayList<>();
    private static int id_counter = 0;
    protected List<UpdatableTile> elements;
    protected int id;

    private float producedPower;
    private float consumedPower;

    public ElectricNetwork(){
        producedPower = 0;
        consumedPower = 0;

        this.elements = new ArrayList<>();
        id = id_counter++;
        electricNetworks.add(this);
    }

    public void tileStateUpdated(UpdatableTile updatableTile){
        if (elements.contains(updatableTile)){
            System.out.println("contains");
            if (updatableTile.isActive()){
                producedPower += updatableTile.getElectricProduction();
                consumedPower += updatableTile.getElectricConsumption();
            } else {
                producedPower -= updatableTile.getElectricProduction();
                consumedPower -= updatableTile.getElectricConsumption();
            }
        }
        System.out.println("Electric Network "+id+" production: "+producedPower);
        System.out.println("Electric Network "+id+" consumption: "+consumedPower);
    }

    public boolean haveEnoughPower(UpdatableTile updatableTile){
        return getRemainingPower() >= updatableTile.getElectricConsumption();
    }

    public void mergeNetworks(List<ElectricNetwork> networks){
        for (ElectricNetwork network : networks) {
            if (network != this) {
                for (UpdatableTile element : network.getElements()) {
                    addElement(element);
                }
                network.delete();
            }
        }
        System.out.println("merging complete, id counter="+id_counter);
    }

    public static List<ElectricNetwork> getNearbyNetworks(Tile tile){
        return getNearbyNetworks(tile.getX(), tile.getY());
    }

    public static List<ElectricNetwork> getNearbyNetworks(int x, int y){
        List<ElectricNetwork> networks1 = new ArrayList<>();
        for (ElectricNetwork network : electricNetworks) {
            if (network.isCoordinateClose(x, y)){
                networks1.add(network);
            }
        }
        return networks1;
    }

    public static ElectricNetwork getElectricNetwork(int network_id){
        if (network_id < electricNetworks.size()){
            return electricNetworks.get(network_id);
        }
        return null;
    }

    public boolean isTileClose(Tile tile){
        return isCoordinateClose(tile.getX(), tile.getY());
    }

    public boolean isCoordinateClose(int x, int y){
        for (UpdatableTile element : elements) {
            int xDistance = x - element.getTile().getX();
            int yDistance = y - element.getTile().getY();
            double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);
            if (distance <= 5) {
                return true;
            }
        }
        return false;
    }

    public void addElement(UpdatableTile element){
        this.elements.add(element);
    }

    public List<UpdatableTile> getElements() {
        return elements;
    }

    public int getNetworkId() {
        return id;
    }

    public float getProducedPower() {
        return producedPower;
    }

    public float getConsumedPower() {
        return consumedPower;
    }

    public float getRemainingPower(){
        return producedPower-consumedPower;
    }

    public void setNetworkId(int id){
        this.id = id;
    }

    public void delete(){
        electricNetworks.remove(this);
        for (int i = 0; i < electricNetworks.size()-1; i++) {
            if (electricNetworks.get(i+1).getNetworkId() - electricNetworks.get(i).getNetworkId() > 1){
                electricNetworks.get(i+1).setNetworkId(electricNetworks.get(i).getNetworkId()+1);
            }
        }
        id_counter--;
    }

    public void printNetworkInfo(){
        System.out.println("{id="+id+", producedPower="+producedPower+", consumedPower="+consumedPower+"}");
    }
}
