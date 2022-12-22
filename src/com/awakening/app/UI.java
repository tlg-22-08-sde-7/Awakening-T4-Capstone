package com.awakening.app;

import com.awakening.app.game.Item;
import com.awakening.app.game.Player;
import com.awakening.app.game.RoomMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UI {
    private TextParser textParser = new TextParser();

    public String displayGameInfo(Player player) {
        String infoText = "";
        String currentRoom = player.getCurrentRoom().getName();
        List<String> containers = new ArrayList<>(Arrays.asList("Desk", "Filing Cabinet", "Key Pad"));
        if (containers.contains(currentRoom)) {
            infoText += "You are at the " + player.getCurrentRoom().getName() + ".\n";
            infoText += "At the " + currentRoom + " you see:" + player.getCurrentRoom().getItems() + ".\n";
        }
        else {
            infoText += "You are in the " + player.getCurrentRoom().getName() + ".\n";
            infoText += player.getCurrentRoom().getDescription() + "\n";
            infoText += "In this room you see:" + player.getCurrentRoom().getItems() + ".\n";
        }

        infoText += "Your items are: " + player.printInventory() + "\n";

        /*
         * Will check if items with charge are in player's inventory.
         * If in inventory, it will print the charge of each item.
         */
        Item.ItemsSetup camera = playerInventory(player, "camera");
        if (player.getInventory().contains(camera)) {
            infoText += "Your camera's charge is: " + camera.getCharge() + "\n";
        }
        Item.ItemsSetup cellphone = playerInventory(player, "cellphone");
        if (player.getInventory().contains(cellphone)) {
            infoText += "Your cellphone's charge is: " + cellphone.getCharge() + "\n";
        }
        Item.ItemsSetup batteries = playerInventory(player, "batteries");
        if (player.getInventory().contains(batteries)) {
            infoText += "Your batteries' charge is: " + batteries.getCharge() + "\n";
        }

        if (player.getCurrentRoom().getNpcName() != null) {
            infoText+= "There is a ghost here, their name is " + player.getCurrentRoom().getNpcName() + ".\n";
        }
        // display exits with room names
        infoText += "Exits : " + player.getCurrentRoom().getDirections().keySet() + ".\n";
        System.out.println(wrapFrame(infoText));

        return infoText;

    }

    /**
     * This method returns the item in inventory found, so it can be printed
     * in displayGameInfo()
     */
    private Item.ItemsSetup playerInventory(Player player, String itemToPrint) {
        Item.ItemsSetup result = null;

        for (Item.ItemsSetup item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemToPrint)) {
                result = item;
            }
        }
        return result;
    }

    public void displayGamePlayOptions() {
        System.out.println("Your gameplay options are:\n" +
                "A two word command is expected: 'Verb + Noun'\n" +
                "Verb:" + textParser.displayAllowedCommands() +
                "\nNoun:" + textParser.displayAllowedNouns());
    }

    public String splashScreen() {
        String welcome = "";
        try {
            welcome = Files.readString(Path.of("resources/ASCII/banner_gui.txt"));
            System.out.println(welcome);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return welcome;
    }

    public String displayMap(RoomMap.RoomLayout currentRoom){
        File loadTxtMap = new File("resources/ASCII/hospitalLayoutASCII.txt");
        StringBuilder sb_map = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(loadTxtMap));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            String scannedSingleLine;
            while((scannedSingleLine = bufferedReader.readLine()) != null){
                if (scannedSingleLine.contains(currentRoom.getName().toUpperCase())){
                    // replace the first word with the same content with different color
                    scannedSingleLine = scannedSingleLine.replace(currentRoom.getName().toUpperCase(), TextParser.BLUE + currentRoom.getName().toUpperCase() + TextParser.RESET);
                }
                sb_map.append(scannedSingleLine).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return sb_map.toString();
    }

    public String wrapFrame(String text) {
        // wrap text in ascii frame
        String formattedText = breakIntoLines(text);
        String[] lines = formattedText.split("\\n");
        int longestLine = 0;
        for (String line : lines) {
            if (line.length() > longestLine) {
                longestLine = line.length();
            }
        }

        String frame;
        String top = "╔";
        String textBody = "";
        String bottom = "╚";
        for (int i = 0; i < longestLine + 2; i++) {
            top += "═";
            bottom += "═";
        }
        top += "╗\n";
        bottom += "╝\n";

        for (String line : lines) {
            int lineLength = line.length();
            int spaces = longestLine - lineLength;
            for (int i = 0; i < spaces; i++) {
                line += " ";
            }
            textBody += "║ " + line + " ║\n";
        }

        frame = top + textBody + bottom;

        return frame;
    }

    public static String breakIntoLines(String input) {
        // check if lines are already broken
        boolean alreadySplit = true;
        for (String line : input.split("\n")) {
            if (line.length() > 80) {
                alreadySplit = false;
                break;
            }
        }

        // If the input is already split, return it as-is.
        if (alreadySplit) {
            return input;
        }
        // If the input is not already split, split it.
        StringBuilder sb = new StringBuilder();
        int startIndex = 0;
        while (startIndex < input.length()) {
            int endIndex = startIndex + 80;
            if (endIndex > input.length()) {
                endIndex = input.length();
            }
            sb.append(input.substring(startIndex, endIndex));
            sb.append("\n");
            startIndex = endIndex;
        }
        return sb.toString();
    }


    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("Unable to clear console");
        }
    }
}