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
                 gameOver = initiateCombatEngine();
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

    public boolean evilSpiritCheck() {
        return (evilSpirit.getCurrentRoom().getName().equalsIgnoreCase(Player.getPlayerInstance().getCurrentRoom().getName()));
    }

    /**
     * Method begins combat sequence with evil spirit
     * Receive command from player and validate
     * Call appropriate methods for combat action
     *
     * @return boolean - signals if player died
     */
    public boolean initiateCombatEngine() {
        // Receive player command
        String response;
        List<String> command;
        boolean playerDied = false;

        response = prompter.prompt("What do you want to do?\n > ");

        command = textParser.combatParser(response);
        while ("invalid".equals(command.get(0))) {
            response = prompter.prompt("What do you want to do?\n > ");
            command = textParser.combatParser(response);
        }

        switch (command.size()) {
            case 1:
                if (command.get(0).equalsIgnoreCase("help")) {
                    ui.displayCombatInfo(Player.getPlayerInstance(), evilSpirit);
                    prompter.prompt("Hit enter to continue...");
                }
                else if (command.get(0).equalsIgnoreCase("hide")) {
                    // hide manager
                    playerDied = attemptToHide(false);
                }
                break;
            case 2:
                if (command.get(0).equalsIgnoreCase("use") && command.get(1).equalsIgnoreCase("camera")) {
                    switch (attemptCameraUsage()) {
                        case 1:
                            prompter.prompt("The camera clicks, but you realize the batteries are dead...");
                            prompter.prompt(evilSpirit.getName() + " begins walking towards you, and you " +
                                    "instinctively try hiding..");
                            playerDied = attemptToHide(true);
                            break;
                        case 2:
                            prompter.prompt("You do not have the camera in your inventory...");
                            prompter.prompt(evilSpirit.getName() + " begins walking towards you, and you " +
                                    "instinctively try hiding..");
                            playerDied = attemptToHide(true);
                            break;
                        case 3:
                            prompter.prompt("The camera flashes and you hear an unearthly scream and snarl..");
                            prompter.prompt(evilSpirit.getName() + " vanishes...");

                            while (evilSpirit.getCurrentRoom().getName().equalsIgnoreCase(
                                    Player.getPlayerInstance().getCurrentRoom().getName())) {
                                evilSpirit.setRandomRoom(world);
                            }
                            break;
                    }
                } else {
                    prompter.prompt("Invalid command with evil spirit present..");
                }
                break;
            default:
                prompter.prompt("Invalid command with evil spirit present..");
        }

        return playerDied;
    }

    /**
     * Method to attempt to deter evil spirit with camera
     * Validates camera is usable
     *
     * @return int for scenario to run following attempted use: 1 - fail, 2 - no camera, 3 - success
     */
    public int attemptCameraUsage() {
        boolean hasCamera = false;
        int scenarioCase = 0;

        for (Item.ItemsSetup inventory : Player.player.getInventory()) {
            if (inventory.getName().equalsIgnoreCase("camera")) {
                hasCamera = true;
                if (inventory.getCharge() <= 0) {
                    scenarioCase = 1;

                } else {
                    scenarioCase = 3;
                    inventory.setCharge(inventory.getCharge() - 10);
                }
            }
        }
        if (!hasCamera) {
            scenarioCase = 2;
        }

        return  scenarioCase;
    }

    /**
     * Method for combat action - hide
     * Takes a parameter that disadvantages player if they attempted to use the camera with no charge
     * i.e player scale (0 - 10), hideNumber = 6, player dies | hideNumber = 25, player lives
     *
     * @param disadvantage - boolean to flag disadvantage scale
     * @return boolean - playerDied boolean
     */
    public boolean attemptToHide(boolean disadvantage) {
        // hide will utilize a random number between 0 - 100. Will need to establish rules
        // to determine success probability
        // if hide is successful, evilSpirit moves to another room, else player dies / game over
        Random rand = new Random();
        int hideNumber = rand.nextInt(100);
        int scale = (disadvantage) ? 25 : 10;

        if (hideNumber <= scale) {
            printGameWon();
            return true;
        } else {
            while (evilSpirit.getCurrentRoom().getName().equalsIgnoreCase(
                    Player.getPlayerInstance().getCurrentRoom().getName())) {
                evilSpirit.setRandomRoom(world);
            }
            return false;
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

    public void executeCommand(List<String> move) {
        // execute command based on verb
        String verb = move.get(0);
        String noun = move.get(1);
        switch (verb) {
            case "go":
                System.out.println(CommandValidation.move(noun, Player.getPlayerInstance(), evilSpirit, world));
                break;
            case "look":
                System.out.println(CommandValidation.look(noun, Player.getPlayerInstance(), ui, npc, evilSpirit, world));
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
     * Used for testing purposes, no usage in normal dev
     *
     * @return - evilSpirit for EvilSpirit data
     */
    public EvilSpirit getEvilSpirit() {
        return evilSpirit;
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

