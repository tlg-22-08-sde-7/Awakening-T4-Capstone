package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.*;
import com.awakening.gui.app.GameHomePage;
import com.awakening.gui.app.GameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class provides validation checks to player commands and actions valid commands
 */
public class CommandValidation {
    private static List<String> approvedItems = new ArrayList<>(Arrays.asList("camera", "cellphone", "key", "journal", "batteries", "file", "bandages", "bandages", "paper-clip", "press-pass", "desk", "table"));
    private static List<String> usableItems = new ArrayList<>(List.of("key-pad", "batteries", "paper-clip"));
    public static List<Item.ItemsSetup> roomItems;
    private static UI ui = new UI();
    private static JFrame popup_frame;

    /**
     * Move command, validates and actions player's commands
     *
     * @param direction - String from user command (i.e. 'north', 'south')
     * @param player    - Player object used for game data
     * @param world     - RoomMap object used for game data
     * @return String - result of player's command
     */
    public static String move(String direction, Player player, EvilSpirit evilSpirit, RoomMap world) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get(direction));

        String commandResult;

        if (nextRoom == null) {
            commandResult = "You can't go that way";
        } else if (nextRoom.isLocked()) {
            commandResult = "The door is locked";
        } else {
            commandResult = "You have moved: " + direction;
            player.setCurrentRoom(nextRoom);

            // Ensure that spirit only moves after player moves twice.
            if (evilSpirit.isMoveReady()) {
                evilSpirit.setRandomRoom(world);
                evilSpirit.setMoveReady(false);
            } else {
                evilSpirit.setMoveReady(true);
            }
        }
        GameHomePage.getHomePageTextArea().setText(commandResult + "\n" + ui.displayGameInfo(Player.getPlayerInstance()));

        String imageLocation = "resources/images/" + player.getCurrentRoom().getName() + ".PNG";
        String mapImage = "resources/images/Map_" + player.getCurrentRoom().getName() + ".png";

        GameManager.scaleImageAndInsertToLabel(imageLocation, GameManager.getImageLabel());
        GameManager.scaleImageAndInsertToMap(mapImage, GameManager.getMapLabel());

        return commandResult;
    }

    /**
     * Player command look - i.e. ('look keys', 'look ghost')
     *
     * @param noun   - String representing item player wishes to 'look' at
     * @param player - Player object for game data
     * @param ui     - UI object for specific UI visible adjustments
     * @param npc    - NPC object for ghost objects
     * @param world  - RoomMap object for game world data
     * @return String - result of command
     */
    public static String look(String noun, Player player, UI ui, NPC npc, RoomMap world) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        String commandResult = "";

        if (noun.equals("ghost")) {
            boolean hasCamera = false;
            String npcName = currentRoom.getNpcName();

            if (npcName == null) {
                commandResult = "There is no ghost in this room";
            } else {
                for (Item.ItemsSetup item : player.getInventory()) {
                    if (item.getName().equalsIgnoreCase("camera")) {
                        hasCamera = true;
                        String ghostDesc = "";
                        String npcGhost = npc.getGhost(npcName);
                        ghostDesc += npcGhost + "\n";
                        item.setCharge(item.getCharge() - 10);
                        commandResult = ui.wrapFrame(ghostDesc);
                        GameHomePage.getHomePageTextArea().setText(commandResult + "\n" + ui.displayGameInfo(Player.getPlayerInstance()));
                        break;
                    }
                }
                if (!hasCamera) {
                    commandResult = ui.wrapFrame("You must have a charged camera to communicate with the ghosts");
                }
            }
        } else if (noun.equals("map")) {
            commandResult = ui.displayMap(player.getCurrentRoom());
        } else if (approvedItems.contains(noun) && currentRoom.getItems().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();

            if (noun.equalsIgnoreCase("table")) {
                // Validate player hasn't already spawned hidden items
                List<String> currentRoomItems = world.getPatientRoom().getItems();
                boolean itemSpawned = false;

                if (!currentRoomItems.contains("key")) {
                    for (Item.ItemsSetup playerItem : player.getInventory()) {
                        if (playerItem.getName().equalsIgnoreCase("key")) {
                            itemSpawned = true;
                            break;
                        }
                    }
                    if (!itemSpawned) {
                        world.getPatientRoom().addItem("key");
                    }
                }

                itemSpawned = false; //reset for next check
                if (!currentRoomItems.contains("press-pass")) {
                    for (Item.ItemsSetup playerItem : player.getInventory()) {
                        if (playerItem.getName().equalsIgnoreCase("press-pass")) {
                            itemSpawned = true;
                            break;
                        }
                    }
                    if (!itemSpawned) {
                        world.getPatientRoom().addItem("press-pass");
                    }
                }
            } else if (noun.equalsIgnoreCase("desk")) {
                // Validate player hasn't already spawned hidden items
                List<String> currentRoomItems = world.getPatientRoom().getItems();
                boolean itemSpawned = false;

                if (!currentRoomItems.contains("batteries")) {
                    for (Item.ItemsSetup playerItem : player.getInventory()) {
                        if (playerItem.getName().equalsIgnoreCase("batteries")) {
                            itemSpawned = true;
                            break;
                        }
                    }
                    if (!itemSpawned) {
                        world.getOffice().addItem("batteries");
                    }
                }

                itemSpawned = false; //reset for next check
                if (!currentRoomItems.contains("paper-clip")) {
                    for (Item.ItemsSetup playerItem : player.getInventory()) {
                        if (playerItem.getName().equalsIgnoreCase("paper-clip")) {
                            itemSpawned = true;
                            break;
                        }
                    }
                    if (!itemSpawned) {
                        world.getOffice().addItem("paper-clip");
                    }
                }

                itemSpawned = false; //reset for next check
                if (!currentRoomItems.contains("file")) {
                    for (Item.ItemsSetup playerItem : player.getInventory()) {
                        if (playerItem.getName().equalsIgnoreCase("file")) {
                            itemSpawned = true;
                            break;
                        }
                    }
                    if (!itemSpawned) {
                        world.getOffice().addItem("file");
                    }
                }
            }

            commandResult = ui.wrapFrame(itemDesc);
        } else if (approvedItems.contains(noun) && player.printInventory().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();
            commandResult = itemDesc;
        } else {
            commandResult = TextParser.RED + "Invalid command" + TextParser.RESET;
        }

        GameHomePage.getHomePageTextArea().setText(commandResult + "\n" + ui.displayGameInfo(Player.getPlayerInstance()));
        return commandResult;
    }

    /**
     * Provides functionality for get command from player
     * Validation occurs within method
     *
     * @param noun   - subject of player's get command
     * @param player - Player object for game data
     * @return - String result of command
     */
    public static String pickUp(String noun, Player player) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        List<String> itemList = player.getCurrentRoom().getItems();
        int index;
        String commandResult = "";

        if (approvedItems.contains(noun)) {
            Item.ItemsSetup item = findItem(noun);

            if (item == null) {
                commandResult = noun + " is not in " + currentRoom.getName();
            } else if (itemList.contains(noun)) {
                if (noun.equalsIgnoreCase("desk") || noun.equalsIgnoreCase("table")){
                    commandResult = "You cannot pick up the "+ noun+".";
                }
                else{
                    player.addToInventory(item);
                    for (int i = 0; i < itemList.size(); i++) {
                        if (noun.equals(itemList.get(i))) {
                            index = i;
                            //Remove item from room
                            player.getCurrentRoom().getItems().remove(index);
                            commandResult = "You have picked up " + noun;
                        }
                    }
                }
            } else {
                commandResult = noun + " is not in " + currentRoom.getName();
            }
        } else {
            commandResult = TextParser.RED + "Invalid command" + TextParser.RESET;
        }

        GameHomePage.getHomePageTextArea().setText(commandResult + "\n" + ui.displayGameInfo(Player.getPlayerInstance()));
        return commandResult;
    }

    /**
     * Provides functionality for use command from player
     * Validation occurs within method
     *
     * @param noun     - subject of 'use' command
     * @param player   - Player object for game data
     * @param prompter - Prompter object for player prompts
     * @param world    - RoomMap object for game data
     * @return String - result of command
     */
    public static String use(String noun, Player player, Prompter prompter, RoomMap world) {
        String commandResult = "";

        if (usableItems.contains(noun)) {
            if (noun.equalsIgnoreCase("key-pad")) {
                // Keypad image
                String imageLocation = "resources/images/key-pad.PNG";
                GameManager.scaleImageAndInsertToLabel(imageLocation, GameManager.getImageLabel());

                GameHomePage.getHomePageTextArea().setText("Enter PIN\n >");

                if (createWindow().equalsIgnoreCase("9537")) {
                    RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
                    RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get("east"));

                    nextRoom.setLocked(false);
                    commandResult = "The key-pad chimes and turns green.";

                } else {
                    commandResult = "The key-pad buzzes and flashes red.";

                }
                GameHomePage.getHomePageTextArea().setText(commandResult+"\n"+ui.displayGameInfo(Player.getPlayerInstance()));
                popup_frame.setVisible(false);
            } else if (noun.equalsIgnoreCase("batteries")) {
                boolean isBatteriesInInventory = false;
                for (Item.ItemsSetup batteries : player.getInventory()) {
                    if (batteries.getName().equalsIgnoreCase("batteries")) {
                        isBatteriesInInventory = true;
                        String itemToCharge = prompter.prompt("What item do you want to charge?\n > ").toLowerCase();
                        itemCharge(batteries, itemToCharge, player);
                        break;
                    }
                }
                if (!isBatteriesInInventory) {
                    commandResult = "You do not have batteries in your inventory";
                }
            } else if (noun.equalsIgnoreCase("paper-clip")) {
                boolean isPaperClipInInventory = false;
                for (Item.ItemsSetup paperClip : player.getInventory()) {
                    if (paperClip.getName().equalsIgnoreCase("paper-clip")) {
                        isPaperClipInInventory = true;
                        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
                        RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get("south"));

                        nextRoom.setLocked(false);
                        commandResult = "You have picked the lock";
                        GameHomePage.getHomePageTextArea().setText(commandResult+"\n"+ui.displayGameInfo(Player.getPlayerInstance()));
                    }
                }
                if (!isPaperClipInInventory) {
                    commandResult = "You do not have paper-clip in your inventory";
                    GameHomePage.getHomePageTextArea().setText(commandResult+"\n"+ui.displayGameInfo(Player.getPlayerInstance()));
                }
            }
        } else {
            commandResult = "Invalid command";
            GameHomePage.getHomePageTextArea().setText(commandResult+"\n"+ui.displayGameInfo(Player.getPlayerInstance()));
        }

        return commandResult;
    }

    private static void itemCharge(Item.ItemsSetup batteries, String itemToCharge, Player player) {
        boolean isItemInInventory = false;

        for (Item.ItemsSetup item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemToCharge)) {
                isItemInInventory = true;
                item.setCharge(item.getCharge() + 20);
                batteries.setCharge(batteries.getCharge() - 20);
                if (batteries.getCharge() < 20) {
                    player.getInventory().remove(batteries);
                }
                break;
            }
        }
        if (!isItemInInventory) {
            System.out.println("That item is not in your inventory");
        }
    }

    private static Item.ItemsSetup findItem(String noun) {
        for (Item.ItemsSetup roomItem : roomItems) {
            if (noun.equals(roomItem.getName())) {
                return roomItem;
            }
        }
        return null;
    }

    private static String createWindow() {
        popup_frame = new JFrame("Make your selection");
        popup_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String input = createUI(popup_frame);
        popup_frame.setSize(560, 200);
        popup_frame.setLocationRelativeTo(null);
        popup_frame.setVisible(true);
        return input;
    }

    private static String createUI(final JFrame frame) {
        String uInput = "";
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        String result = (String) JOptionPane.showInputDialog(
                frame,
                "Please provide your input: ",
                "Make the selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "1234"
        );
        if (result != null && result.length() > 0) {
            uInput = result;
        }
        frame.add(panel, BorderLayout.CENTER);
        return uInput;
    }
}