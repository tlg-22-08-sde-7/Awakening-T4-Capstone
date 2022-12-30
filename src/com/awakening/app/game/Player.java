package com.awakening.app.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name = "";
    private RoomMap.RoomLayout currentRoom;
    private List<Item.ItemsSetup> inventory = new ArrayList<>();

    public static final Player player = new Player();

    Player(){

    }

    public static Player getPlayerInstance(){
        return player;
    }

    public void addToInventory(Item.ItemsSetup item) {
        inventory.add(item);
    }

    public RoomMap.RoomLayout getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(RoomMap.RoomLayout currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<Item.ItemsSetup> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        inventory = inventory;
    }

    // This will allow for the player inventory to be printed to the console/terminal
    public String printInventory() {
        String inventory = "";

        for(Item.ItemsSetup item : getInventory() ){
            inventory += item.getName() + ", ";
        }

        return inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
