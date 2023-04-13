package gameEngine.registry.updatable;

import gameEngine.registry.capabilities.UpdatableTile;
import utils.Network;
import utils.NetworkElement;
import world.Item;
import world.Tile;

import java.util.LinkedList;

public class BeltUp extends UpdatableTile {
    private int sendingIndex;
    public BeltUp(Tile tile) {
        super(tile);
        this.sendingIndex = 0;
    }

    @Override
    public void update() {
        Network network = Network.getNetworkAt(tile);
        NetworkElement networkElement = network.getNetworkElement(tile);
//        System.out.println("belt update");
        if (networkElement != null && networkElement.getInternalItem() != Item.EMPTY){
            if (networkElement.incrementInternalItemProgression(2)){
                LinkedList<Tile> connections = network.getConnections().get(tile);
                if (connections != null && !connections.isEmpty()){
                    if (sendingIndex >= connections.size()){
                        sendingIndex = 0;
                    }
                    Tile destination = connections.get(sendingIndex++);
                    NetworkElement destinationElement = network.getNetworkElement(destination);
                    destinationElement.setInternalItem(networkElement.getInternalItem());
                    networkElement.setInternalItem(Item.EMPTY);
                    System.out.println("sending completed");
                } else {
                    System.out.println("sending failed");
                }
            } else {
                System.out.println("tile: "+tile+" item progression");
            }
        }
    }
}
