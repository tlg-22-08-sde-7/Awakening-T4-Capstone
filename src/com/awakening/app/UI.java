package com.awakening.app;

import com.awakening.app.game.Item;
import com.awakening.app.game.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class UI {
    private TextParser textParser = new TextParser();

    public void displayGameInfo(Player player) {
        String infoText = "";
        String currentRoom = player.getCurrentRoom().getName();
        List<String> containers = new ArrayList<>(Arrays.asList("Desk", "Filing Cabinet", "Key Pad"));
        if (containers.contains(currentRoom)) {
            infoText += "You are at the " + player.getCurrentRoom().getName() + ".\n";
            infoText += "At the " + currentRoom + " you see:" + player.getCurrentRoom().getItems() + ".\n";
        }
        else {
            infoText += "You are in the " + player.getCurrentRoom().getName() + ".\n";
            infoText += "In this room you see:" + player.getCurrentRoom().getItems() + ".\n";
        }

        infoText += "Your items are: " + player.printInventory() + "\n";
        for (Item.ItemsSetup camera : player.getInventory()) {
            if (camera.getName().equalsIgnoreCase("camera")) {
                infoText += "Your camera's charge is: " + camera.getCharge() + "\n";
                break;
            }
        }
        if (player.getCurrentRoom().getNpcName() != null) {
            infoText+= "There is a ghost here, their name is " + player.getCurrentRoom().getNpcName() + ".\n";
        }
        // display exits with room names
        infoText += "Exits : " + player.getCurrentRoom().getDirections().keySet() + ".\n";
        System.out.println(wrapFrame(infoText));
    }
    public void displayGamePlayOptions() {
        System.out.println("Your gameplay options are:\n" +
                "A two word command is expected: 'Verb + Noun'\n" +
                "Verb:" + textParser.displayAllowedCommands() +
                "\nNoun:" + textParser.displayAllowedNouns());

    }
    public void splashScreen() {
        String welcome;
        try {
            welcome = Files.readString(Path.of("resources/ASCII/banner.txt"));
            System.out.println(welcome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMap(){
        String map;
        try{
            map = Files.readString(Path.of("resources/ASCII/hospitalLayoutASCII.txt"));
            System.out.println(map);
        }catch (IOException e){
            e.printStackTrace();
        }
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