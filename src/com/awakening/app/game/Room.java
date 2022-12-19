package com.awakening.app.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {

    private String name;
    private HashMap<String, String> directions = new HashMap<>();
    private String description;
    private boolean isLocked;
    private List<String> items;

    public Room(){

    }
    public Room(String name, HashMap<String, String> directions, String description, boolean isLocked, ArrayList<String> items) {
        this.name = name;
        this.directions = directions;
        this.description = description;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getDirections() {
        return directions;
    }

    public void setDirections(HashMap<String, String> directions) {
        this.directions = directions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override public String toString()
    {
        return "Room [name=" + name + ", directions=" + directions
                + ", description=" + description + ", isLocked="
                + isLocked + ", items=" + items + "]";
    }
}
