package gameEngine.registry.capabilities;

import utils.ElectricNetwork;
import world.Tile;

import java.util.List;

public abstract class UpdatableTile{
    protected Tile tile;
    protected boolean isElectric;
    protected boolean isActive;
    protected float electricConsumption;
    protected float electricProduction;
    protected int network_id;

    public UpdatableTile(Tile tile){
        this.tile = tile;
        this.isElectric = false;
        this.isActive = false;
        this.electricConsumption = 0.0f;
        this.electricProduction = 0.0f;
        this.network_id = -1;
    }

    protected void initializeElectricalCapability(){
        isElectric = true;
        List<ElectricNetwork> networks = ElectricNetwork.getNearbyNetworks(tile);
        if (networks.size() == 0) {
            System.out.println("Creating new network");
            ElectricNetwork electricNetwork = new ElectricNetwork();
            electricNetwork.addElement(this);
            this.network_id = electricNetwork.getNetworkId();
            System.out.println("new network id: " + electricNetwork.getNetworkId());
            electricNetwork.printNetworkInfo();
        } else if (networks.size() == 1) {
            ElectricNetwork electricNetwork = networks.get(0);
            System.out.println("Found network with id: " + electricNetwork.getNetworkId());
            electricNetwork.addElement(this);
            this.network_id = electricNetwork.getNetworkId();
            electricNetwork.printNetworkInfo();
        } else {
            networks.get(0).mergeNetworks(networks);
            networks.get(0).addElement(this);
            this.network_id = networks.get(0).getNetworkId();
            networks.get(0).printNetworkInfo();
        }
    }

    public abstract void update();

    protected void activate(){
        isActive = true;
        getElectricNetwork().tileStateUpdated(this);
    }

    protected void deactivate(){
        isActive = false;
        getElectricNetwork().tileStateUpdated(this);
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isActive() {
        return isActive;
    }

    public float getElectricProduction() {
        return electricProduction;
    }

    public float getElectricConsumption() {
        return electricConsumption;
    }

    public int getNetwork_id() {
        return network_id;
    }

    public ElectricNetwork getElectricNetwork(){
        return ElectricNetwork.getElectricNetwork(network_id);
    }
}
