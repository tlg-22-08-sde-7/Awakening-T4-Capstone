package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.*;
import com.awakening.app.game.Item;
import com.awakening.app.game.Player;
import com.awakening.app.game.Room;
import com.awakening.app.game.RoomMap;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


//Class that will control gameplay
public class Game {

    public static RoomMap world;
    public static List<Item.ItemsSetup> roomItems;
    public static EvilSpirit evilSpirit = new EvilSpirit();
    public static NPC npc = new NPC();
    private static Prompter prompter = new Prompter(new Scanner(System.in));
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
             ui.displayGameInfo(Player.getPlayerInstance());

             if (evilSpiritCheck()) {
                 ui.wrapFrame(evilSpirit.getName() + " is in the room...");
                 initiateCombatEngine();
             }
             commandHandler();
             gameStateCheck();
        }
    }

    private void commandHandler() {
        String confirmation;
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
    }

    private void gameStateCheck() {
        if (Player.getPlayerInstance().getCurrentRoom() != world.getRoom("Front Desk")) {
            return;
        }
        if (Player.getPlayerInstance().printInventory().contains("key")) {
            gameOver = true;
            printGameWon();
        }
    }

    private boolean evilSpiritCheck() {
        return (evilSpirit.getCurrentRoom().getName().equalsIgnoreCase(Player.getPlayerInstance().getCurrentRoom().getName()));
    }

    private void initiateCombatEngine() {
        // Receive player command of either use camera or hide
        // validate camera has charge
        // if charge
        //      send evilSpirit to random room other that current
        // else
        //      show error and hide with worse chances

        // hide will utilize a random number between 0 - 100. Will need to establish rules
        // to determine success probability
        // if hide is successful, evilSpirit moves to another room, else player dies / game over
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

    public void executeCommand(List<String> move) {
        // execute command based on verb
        String verb = move.get(0);
        String noun = move.get(1);
        switch (verb) {
            case "go":
                System.out.println(CommandValidation.move(noun, Player.getPlayerInstance(), evilSpirit, world));
                break;
            case "look":
                System.out.println(CommandValidation.look(noun, Player.getPlayerInstance(), ui, npc, world));
                break;
            case "get":
                System.out.println(CommandValidation.pickUp(noun, Player.getPlayerInstance()));
                break;
            case "use":
                System.out.println(CommandValidation.use(noun, Player.getPlayerInstance(), prompter, world));
                break;
            default:
                System.out.println(TextParser.RED + "Invalid command" + TextParser.RESET);
        }
    }

    public void generateWorld() {
        try (Reader reader = new FileReader("resources/JSON/roomsListNew.json")) {
            world = new Gson().fromJson(reader, RoomMap.class);
            Player.getPlayerInstance().setCurrentRoom(world.getBasement());
            evilSpirit.setCurrentRoom(world.getFrontDesk());
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

        CommandValidation.roomItems = roomItems;
    }

    /**
     * Used for testing purposes, no usage in normal dev
     *
     * @return - RoomMap for world data
     */
    public RoomMap getWorld() {
        return world;
    }

    /**
     * Used for testing purposes, no usage in normal dev
     *
     * @return - NPC for npc data
     */
    public NPC getNpc() {
        return npc;
    }

    /**
     * Get player data
     *
     * @return - Player for player data
     */
    public Player getPlayer() {
        return Player.getPlayerInstance();
    }
}

