package com.awakening.app.game;

import java.util.Random;

public class EvilSpirit extends Player{

    private final String WARNING_IMAGE_LOCATION = "resources/images/warningSpirit.png";
    private final String PLAYER_SCARE_IMAGE_LOCATION = "resources/images/scarePlayer.png";
    private boolean moveReady = false;

    public EvilSpirit() {
        super();
        this.setName("Death's Embrace");
    }

    /**
     * Method randomly puts the Evil Spirit into a room in the world
     * Using Random, a pseudorandom number is chosen that corresponds to a particular room
     *
     * @param world - RoomMap object for world room data
     */
    public void setRandomRoom(RoomMap world) {
        RoomMap.RoomLayout nextRoom;
        Random random = new Random();
        int roomVal = random.nextInt(7);

        switch (roomVal) {
            case 0:
                nextRoom = world.getBasement();
                break;
            case 1:
                nextRoom = world.getMorgue();
                break;
            case 2:
                nextRoom = world.getEmergencyRoom();
                break;
            case 3:
                nextRoom = world.getOffice();
                break;
            case 4:
                nextRoom = world.getHallway();
                break;
            case 5:
                nextRoom = world.getPatientRoom();
                break;
            default:
                nextRoom = world.getFrontDesk();
                break;
        }

        this.setCurrentRoom(nextRoom);
    }


    public boolean isMoveReady() {
        return moveReady;
    }

    public void setMoveReady(boolean moveReady) {
        this.moveReady = moveReady;
    }
}