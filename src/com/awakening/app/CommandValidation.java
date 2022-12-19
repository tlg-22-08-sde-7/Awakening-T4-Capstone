package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.Item;
import com.awakening.app.game.NPC;
import com.awakening.app.game.Player;
import com.awakening.app.game.RoomMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class provides validation checks to player commands and actions valid commands
 */
public class CommandValidation {
    private static List<String> approvedItems = new ArrayList<>(Arrays.asList("camera", "cellphone", "key", "journal", "batteries", "file", "bandages", "bandages", "paper-clip", "press-pass", "file-cabinet", "desk"));
    private static List<String> usableItems = new ArrayList<>(List.of("key-pad", "batteries"));
    public static List<Item.ItemsSetup> roomItems;

    /**
     * Move command, validates and actions player's commands
     *
     * @param direction - String from user command (i.e. 'north', 'south')
     * @param player - Player object used for game data
     * @param world - RoomMap object used for game data
     *
     * @return String - result of player's command
     */
    public static String move(String direction, Player player, RoomMap world) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get(direction));
        String commandResult;

        if (nextRoom == null) {
            commandResult = TextParser.RED + "You can't go that way" + TextParser.RESET;
        } else if (nextRoom.isLocked()) {
            commandResult = TextParser.RED + "The door is locked" + TextParser.RESET;
        } else {
            player.setCurrentRoom(nextRoom);
            commandResult = "You have moved: " + direction;
        }

        return commandResult;
    }

    /**
     * Player command look - i.e. ('look keys', 'look ghost')
     *
     * @param noun - String representing item player wishes to 'look' at
     * @param player - Player object for game data
     * @param ui - UI object for specific UI visible adjustments
     * @param npc - NPC object for ghost objects
     *
     * @return String - result of command
     */
    public static String look(String noun, Player player, UI ui, NPC npc) {
        RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
        String commandResult = "";

        if (noun.equals("ghost")) {
            boolean hasCamera = false;
            String npcName = currentRoom.getNpcName();

            if (npcName == null) {
                commandResult = "There is no ghost in this room";
            }
            else {
                for (Item.ItemsSetup item : player.getInventory()) {
                    if (item.getName().equalsIgnoreCase("camera")) {
                        hasCamera = true;
                        String ghostDesc = "";
                        String npcGhost = npc.getGhost(npcName);
                        ghostDesc += npcGhost + "\n";
                        item.setCharge(item.getCharge() - 10);
                        commandResult = ui.wrapFrame(ghostDesc);
                        break;
                    }
                }
                if (!hasCamera) {
                    commandResult = ui.wrapFrame("You must have a charged camera to communicate with the ghosts");
                }
            }
        }
        else if (noun.equalsIgnoreCase("file-cabinet")) {
            String fileCabinetItems = "There are patient files stored here. There is an interesting file that you can get";
            commandResult = ui.wrapFrame(fileCabinetItems);
        }
        else if (noun.equalsIgnoreCase("desk")) {
            String deskItems = "The desk has batteries and paper-clip. You can get batteries and/or get paper-clip";
            commandResult = ui.wrapFrame(deskItems);
        }
        else if (noun.equals("map")) {
            commandResult = ui.displayMap(player.getCurrentRoom());
        } else if (approvedItems.contains(noun) && currentRoom.getItems().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();
            commandResult = itemDesc;
        } else if (approvedItems.contains(noun) && player.printInventory().contains(noun)) {
            String itemDesc;
            Item.ItemsSetup item = findItem(noun);
            assert item != null;
            itemDesc = item.getDescription();
            commandResult = itemDesc;
        } else {
            commandResult = TextParser.RED + "Invalid command" + TextParser.RESET;
        }

        return commandResult;
    }

    /**
     * Provides functionality for get command from player
     * Validation occurs within method
     *
     * @param noun - subject of player's get command
     * @param player - Player object for game data
     *
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
                commandResult = noun + " is not in " + currentRoom;
            } else if (itemList.contains(noun)) {
                player.addToInventory(item);
                for (int i = 0; i < itemList.size(); i++) {
                    if (noun.equals(itemList.get(i))) {
                        index = i;
                        //Remove item from room
                        player.getCurrentRoom().getItems().remove(index);
                        commandResult = "You have picked up " + noun;
                    }
                }
            } else if (noun.equalsIgnoreCase("file") || noun.equalsIgnoreCase("paper-clip") || noun.equalsIgnoreCase("batteries")) {
                commandResult = itemFinder(noun, player);
            }
        }
        else {
            commandResult = TextParser.RED + "Invalid command" + TextParser.RESET;
        }

        return commandResult;
    }

    /**
     * Provides functionality for use command from player
     * Validation occurs within method
     *
     * @param noun - subject of 'use' command
     * @param player - Player object for game data
     * @param prompter - Prompter object for player prompts
     * @param world - RoomMap object for game data
     *
     * @return String - result of command
     */
    public static String use(String noun, Player player, Prompter prompter, RoomMap world) {
        String commandResult = "";

        if (usableItems.contains(noun)) {
            if (noun.equalsIgnoreCase("key-pad")) {
                // Keypad image
                String keyEntry = prompter.prompt("Enter PIN\n > ");

                if (keyEntry.equalsIgnoreCase("9537")) {
                    RoomMap.RoomLayout currentRoom = player.getCurrentRoom();
                    RoomMap.RoomLayout nextRoom = world.getRoom(currentRoom.getDirections().get("east"));

                    nextRoom.setLocked(false);
                    commandResult = "The key-pad chimes and turns green.";
                } else {
                    commandResult = "The key-pad buzzes and flashes red.";
                }
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
            }
        }
        else {
            commandResult = TextParser.RED + "Invalid command" + TextParser.RESET;
        }

        return commandResult;
    }

    private static void itemCharge(Item.ItemsSetup batteries, String itemToCharge, Player player) {
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


    private static String itemFinder(String noun, Player player) {
        for (Item.ItemsSetup item : roomItems) {
            if (item.getName().equalsIgnoreCase(noun)) {
                player.addToInventory(item);
                return "You have picked up " + item.getName();
            }
        }

        return noun + " is not in room.";
    }

    private static Item.ItemsSetup findItem(String noun) {
        for (Item.ItemsSetup roomItem : roomItems) {
            if (noun.equals(roomItem.getName())) {
                return roomItem;
            }
        }
        return null;
    }
}