package com.awakening.app.game;

// Java Program to Create an Employee object with all
// Attributes and generate a GSON out of it

// Importing input output classes

import com.google.gson.Gson;

import java.io.*;

// Main class
public class GSON {

    // Main driver method
    public static void main(String[] args) {

        //create an array of JSON objects
        //read JSON from a file

//        //GENERATE ITEM LIST
//        try (Reader reader = new FileReader("resources/JSON/items.json")) {
//            Map<String, String> itemMap = null;
//            itemMap = new Gson().fromJson(reader, Map.class);
//            for (Map.Entry<String, String> entry : itemMap.entrySet()) {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("\n");
        System.out.println("\n");

        //GENERATE ROOM LIST
//        ArrayList<Room> roomList = null;
//        try (Reader reader = new FileReader("resources/JSON/roomsList.json")) {
//
//            roomList = new Gson().fromJson(reader, new TypeToken<List<Room>>() {
//            }.getType());
//            roomList.forEach(System.out::println);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("\n");
//        System.out.println("\n");

        //GENERATE ROOMMAP
        RoomMap roomMap;
        try (Reader reader = new FileReader("resources/JSON/roomsListNew.json")) {
            roomMap = new Gson().fromJson(reader, RoomMap.class);
            RoomMap.Morgue morgue = roomMap.getMorgue();
            System.out.println(morgue.getNpcName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
        System.out.println("\n");

        //GENERATE GAME TEXT
        GameText gameText;
        try (Reader reader = new FileReader("resources/JSON/GameText.json")) {
            gameText = new Gson().fromJson(reader, GameText.class);
            GameText.BasementText basement = gameText.getBasementText();
            System.out.println(basement.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");

        //GENERATE NPC TEXT
        NPC npc;
        try (Reader reader = new FileReader("resources/JSON/NPC.json")) {
            npc = new Gson().fromJson(reader, NPC.class);
            NPC.Ghost1 ghost1 = npc.getGhost1();
            System.out.println(ghost1.getAttack());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GENERATE item
        Item item;
        try (Reader reader = new FileReader("resources/JSON/Items.json")) {
            item = new Gson().fromJson(reader, Item.class);
            Item.CellPhone cellPhone = item.getCellPhone();
            System.out.println(cellPhone.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}




