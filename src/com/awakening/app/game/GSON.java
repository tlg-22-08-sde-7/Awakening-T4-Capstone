package com.awakening.app.game;

// Java Program to Create an Employee object with all
// Attributes and generate a GSON out of it

// Importing input output classes

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

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
        ArrayList<Room> roomList = null;
        try (Reader reader = new FileReader("resources/JSON/roomsList.json")) {

            roomList = new Gson().fromJson(reader, new TypeToken<List<Room>>() {
            }.getType());
            roomList.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
        System.out.println("\n");

        //GENERATE ROOMMAP
        RoomMap roomMap = new RoomMap();
        try (Reader reader = new FileReader("resources/JSON/roomsListNew.json")) {
            roomMap = new Gson().fromJson(reader, RoomMap.class);
            RoomMap.Basement basement = roomMap.getBasement();
            System.out.println(basement.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n");
        System.out.println("\n");

        //GENERATE GAME TEXT
        GameText gameText = new GameText();
        try (Reader reader = new FileReader("resources/JSON/GameText.json")) {
            gameText = new Gson().fromJson(reader, GameText.class);
            GameText.BasementText basement = gameText.getBasementText();
            System.out.println(basement.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}




