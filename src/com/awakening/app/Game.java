package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.*;
import com.awakening.app.game.Item;
import com.awakening.app.game.Player;
import com.awakening.app.game.Room;
import com.awakening.app.game.RoomMap;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


//Class that will control gameplay
public class Game {

    public static RoomMap world;
    public static List<Item.ItemsSetup> roomItems;
    public static Player player = new Player();
    public static NPC npc = new NPC();
    private static final Prompter prompter = new Prompter(new Scanner(System.in));
    private List<String> approvedItems = new ArrayList<>(Arrays.asList("camera", "cellphone", "key", "journal", "batteries", "file", "bandages", "bandages", "paper-clip", "press-pass", "file-cabinet", "desk"));
    private List<String> usableItems = new ArrayList<>(List.of("key-pad", "batteries", "paper-clip"));
    private UI ui = new UI();
    private TextParser textParser = new TextParser();
    private List<Room> rooms = new ArrayList<>();
    boolean gameOver = false;

    public Game() {
    }

    public void initGame() {
        boolean gameStart = false;
        String confirmation;

        ui.splashScreen();


        while (!gameStart) {
            String playGame = prompter.prompt("Do you want to play Awakening? [Y/N]\n > ").toLowerCase().trim();


            switch (playGame) {
                case ("y"):
                case ("yes"):
                    System.out.println();
                    ui.displayGamePlayOptions();
                    gameStart = true;
                    break;
                case ("n"):
                case ("no"):
                    confirmation = prompter.prompt("Are you sure? [Y/N]\n > ").toLowerCase().trim();
                    if (!"y".equals(confirmation)) {
                        break;
                    }
                    gameOver = true;
                    gameStart = true;
                    break;
                default:
                    System.out.println(TextParser.RED + "Invalid input, please provide [Y] for Yes, [N] for No." + TextParser.RESET);
                    System.out.println();
            }
            //This is to add a line, with the intention of spacing out the text fields of U/I and game text
            System.out.println();
        }

        generateWorld();

        while (!gameOver) {
            ui.clearConsole();
            ui.displayGameInfo(player);

            String response = prompter.prompt("What do you want to do?\n > ");
            List<String> move = textParser.parseInput(response);
            while ("invalid".equals(move.get(0))) {
                response = prompter.prompt("What do you want to do?\n > ");
                move = textParser.parseInput(response);
            }

            if ("quit".equals(move.get(0))) {
                confirmation = prompter.prompt("Are you sure? [Y/N]\n > ").toLowerCase().trim();
                switch (confirmation) {
                    case ("y"):
                    case ("yes"):
                        gameOver = true;
                        break;

                    case ("n"):
                    case ("no"):
                        break;
                }
            } else if ("help".equals(move.get(0))) {
                ui.displayGamePlayOptions();
                prompter.prompt("Hit enter to continue...");
            } else {
                executeCommand(move);
            }
            gameStateCheck();
        }
    }

    private void gameStateCheck() {
        if (player.getCurrentRoom() != world.getRoom("Front Desk")) {
            return;
        }
        if (player.printInventory().contains("key")) {
            gameOver = true;
            printGameWon();
        }
    }

    private void printGameWon() {
        String endText;
        try{
            endText = Files.readString(Path.of("resources/ASCII/gameEnd.txt"));
            System.out.println(endText);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void executeCommand(List<String> move) {
        // execute command based on verb
        String verb = move.get(0);
        String noun = move.get(1);
        switch (verb) {
            case "go":
                move(noun);
                break;
            case "quit":
                System.out.println("Thanks for playing!");
                break;
            case "look":
                look(noun);
                break;
            case "get":
                if (approvedItems.contains(noun)) {
                    pickUp(noun);
                } else {
                    System.out.println(TextParser.RED + "Invalid command" + TextParser.RESET);
                }
                break;
            case "use":
                if (usableItems.contains(noun)){
                    use(noun);
                } else {
                    System.out.println(TextParser.RED + "Cannot use that" + TextParser.RESET);
                }
                break;
            default:
                System.out.println(TextParser.RED + "Invalid command" + TextParser.RESET);
        }
    }

    private void move(String direction) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get(direction));
        if (nextRoom == null) {
            System.out.println(TextParser.RED + "You can't go that way" + TextParser.RESET);
        } else if (nextRoom.isLocked()) {
            System.out.println(TextParser.RED + "The door is locked" + TextParser.RESET);
        } else {
            player.setCurrentRoom(nextRoom);
        }
    }

    private void look(String noun) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();

        if (noun.equals("ghost")) {
            boolean hasCamera = false;
            String npcName = currentRoom.getNpcName();
            if (npcName == null) {
                System.out.println("There is no ghost in this room");
                return;
            }
            for (Item.ItemsSetup item : player.getInventory()) {
                if (item.getName().equalsIgnoreCase("camera")) {
                    hasCamera = true;
                    String ghostDesc = "";
                    String npcGhost = npc.getGhost(npcName);
                    ghostDesc += npcGhost + "\n";
                    item.setCharge(item.getCharge()-10);
                    System.out.println(ui.wrapFrame(ghostDesc));
                    break;
                }
            }
            if (!hasCamera) {
                System.out.println(ui.wrapFrame("You must have a charged camera to communicate with the ghosts"));
            }
        }
        else if (noun.equalsIgnoreCase("file-cabinet")) {
            String fileCabinetItems = "There are patient files stored here. There is an interesting file that you can get";
            System.out.println(ui.wrapFrame(fileCabinetItems));
        }
        else if (noun.equalsIgnoreCase("desk")) {
            String deskItems = "The desk has batteries and paper-clip. You can get batteries and/or get paper-clip";
            System.out.println(ui.wrapFrame(deskItems));
        }
        else if (noun.equals("map")) {
            ui.displayMap(player.getCurrentRoom());
        } else if (approvedItems.contains(noun) && currentRoom.getItems().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();
            System.out.println(itemDesc);
        } else if (approvedItems.contains(noun) && player.printInventory().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();
            System.out.println(itemDesc);
        } else {
            System.out.println(TextParser.RED + "Invalid command" + TextParser.RESET);
        }

        prompter.prompt("Hit enter to continue...");
    }

    private void pickUp(String noun) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        List<String> itemList = player.getCurrentRoom().getItems();
        boolean validItem = false;

        int index;
        Item.ItemsSetup item = findItem(noun);

        if (item == null) {
            validItem = true;
            System.out.println(noun + " is not in " + currentRoom);
        } else if (itemList.contains(noun)) {
            validItem = true;
            player.addToInventory(item);
            for (int i = 0; i < itemList.size(); i++) {
                if (noun.equals(itemList.get(i))) {
                    index = i;
                    //Remove item form room
                    player.getCurrentRoom().getItems().remove(index);
                    System.out.println("You have picked up " + noun);
                }
            }
        }
        else if (noun.equalsIgnoreCase("file") || noun.equalsIgnoreCase("paper-clip") || noun.equalsIgnoreCase("batteries")) {
            validItem = true;
            itemFinder(noun);
        }
        if (!validItem){
            System.out.println(TextParser.RED + "Invalid command" + TextParser.RESET);
        }

        prompter.prompt("Hit enter to continue...");
    }

    private void itemFinder(String noun) {
        for (Item.ItemsSetup item : roomItems) {
            if (item.getName().equalsIgnoreCase(noun)) {
                player.addToInventory(item);
                System.out.println("You have picked up " + item.getName());
                break;
            }
        }
    }

    /**
     * Provides functionality for use command from player.
     * noun should be approved outside this method
     *
     * @param noun - approved usable noun
     */
    private void use(String noun) {

        if (noun.equalsIgnoreCase("key-pad")) {
            // Keypad image
            String keyEntry = prompter.prompt("Enter PIN\n > ");

            if (keyEntry.equalsIgnoreCase("9537")) {
                RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
                RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get("east"));

                nextRoom.setLocked(false);
                System.out.println("The key-pad chimes and turns green.");
            } else {
                System.out.println("The key-pad buzzes and flashes red.");
            }
        }
        else if (noun.equalsIgnoreCase("batteries")) {
            boolean isBatteriesInInventory = false;
            for (Item.ItemsSetup batteries : player.getInventory()) {
                if (batteries.getName().equalsIgnoreCase("batteries")) {
                    isBatteriesInInventory= true;
                    String itemToCharge = prompter.prompt("What item do you want to charge?\n > ").toLowerCase();
                    itemCharge(batteries, itemToCharge);
                    break;
                }
            }
            if (!isBatteriesInInventory) {
                System.out.println("You do not have batteries in your inventory");
            }
        }
        else if (noun.equalsIgnoreCase("paper-clip")) {
            boolean isPaperClipInInventory = false;
            for (Item.ItemsSetup paperClip : player.getInventory()) {
                if (paperClip.getName().equalsIgnoreCase("paper-clip")) {
                    isPaperClipInInventory = true;
                    RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
                    RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get("south"));

                    nextRoom.setLocked(false);
                    System.out.println("You have picked the lock");
                }
            }
            if (!isPaperClipInInventory) {
                System.out.println("You do not have paper-clip in your inventory");
            }
        }

        prompter.prompt("Hit enter to continue...");
    }

    private void itemCharge(Item.ItemsSetup batteries, String itemToCharge) {
        boolean isItemInInventory = false;

        for (Item.ItemsSetup item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemToCharge)) {
                isItemInInventory = true;
                item.setCharge(item.getCharge()+20);
                batteries.setCharge(batteries.getCharge()-20);
                if (batteries.getCharge()<20) {
                    player.getInventory().remove(batteries);
                }
                break;
            }
        }
        if (!isItemInInventory) {
            System.out.println("That item is not in your inventory");
        }
    }

    private Item.ItemsSetup findItem(String noun) {
        for (Item.ItemsSetup roomItem : roomItems) {
            if (noun.equals(roomItem.getName())) {
                return roomItem;
            }
        }
        return null;
    }

    private void generateWorld() {
        try (Reader reader = new FileReader("resources/JSON/roomsListNew.json")) {
            world = new Gson().fromJson(reader, RoomMap.class);
            player.setCurrentRoom(world.getBasement());
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadNPC();
    }

    private void loadNPC() {
        try (Reader reader = new FileReader("resources/JSON/NPC.json")) {
            npc = new Gson().fromJson(reader, NPC.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateItems();
    }

    private void generateItems() {
        Item item;
        try (Reader reader = new FileReader("resources/JSON/Items.json")) {
            item = new Gson().fromJson(reader, Item.class);
            roomItems = item.loadItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

