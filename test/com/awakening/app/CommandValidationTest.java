package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CommandValidationTest {
    Game gameData;

    @Before
    public void setUp() {
        gameData = new Game();
        gameData.generateWorld();
    }

    @Test
    public void correctPIN_keypadUsed_nextDoorUnlocks() {
        Player player = new Player();
        Prompter prompter = null;
        try {
            prompter = new Prompter(new Scanner(new File("test-resources/CorrectKeypadResponsesTest.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        player.setCurrentRoom(gameData.getWorld().getHallway());

        assertTrue(CommandValidation.use("key-pad", player, prompter, gameData.getWorld()).equalsIgnoreCase("The key-pad chimes and turns green."));
        player.setCurrentRoom(gameData.getWorld().getRoom(player.getCurrentRoom().getDirections().get("east")));
        assertFalse(player.getCurrentRoom().isLocked());
    }

    @Test
    public void incorrectPIN_KeypadUsed_nextDoorStaysLocked() {
        Player player = new Player();
        Prompter prompter = null;
        try {
            prompter = new Prompter(new Scanner(new File("test-resources/IncorrectKeypadResponsesTest.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        player.setCurrentRoom(gameData.getWorld().getHallway());

        assertTrue(CommandValidation.use("key-pad", player, prompter, gameData.getWorld()).equalsIgnoreCase("The key-pad buzzes and flashes red."));
        player.setCurrentRoom(gameData.getWorld().getRoom(player.getCurrentRoom().getDirections().get("east")));
        assertTrue(player.getCurrentRoom().isLocked());
    }

    @Test
    public void correctDirection_moveCommand_movesPlayer() {
        Player player = new Player();

        player.setCurrentRoom(gameData.getWorld().getEmergencyRoom());

        //Test North
        assertTrue(CommandValidation.move("north", player, gameData.getWorld()).equalsIgnoreCase("You have moved: north"));
        assertTrue(player.getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getMorgue().getName()));

        //Test South
        assertTrue(CommandValidation.move("south", player, gameData.getWorld()).equalsIgnoreCase("You have moved: south"));
        assertTrue(player.getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getEmergencyRoom().getName()));

        //Test East
        assertTrue(CommandValidation.move("east", player, gameData.getWorld()).equalsIgnoreCase("You have moved: east"));
        assertTrue(player.getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getHallway().getName()));

        //Test West
        assertTrue(CommandValidation.move("west", player, gameData.getWorld()).equalsIgnoreCase("You have moved: west"));
        assertTrue(player.getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getEmergencyRoom().getName()));
    }

    @Test
    public void incorrectDirection_moveCommand_playerDoesNotMove() {
        Player player = new Player();

        player.setCurrentRoom(gameData.getWorld().getMorgue());

        //Test East, not available direction from morgue
        //assertTrue(CommandValidation.move("east", player, gameData.getWorld()).equalsIgnoreCase("You can't go that way"));
        assertTrue(player.getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getMorgue().getName()));
    }

    @Test
    public void ghostPresentCameraEquipped_lookCommand_returnCorrectGhostDescription() {
        Player player = new Player();
        UI ui = new UI();

        player.setCurrentRoom(gameData.getWorld().getBasement());
        CommandValidation.pickUp("camera", player);

        player.setCurrentRoom(gameData.getWorld().getEmergencyRoom());
        assertTrue(CommandValidation.look("ghost", player, ui, gameData.getNpc(), gameData.getWorld()).equalsIgnoreCase(ui.wrapFrame(gameData.getNpc().getGhost2().getDescription())));
    }

    @Test
    public void ghostPresentCameraNotEquipped_lookCommand_returnCameraNotEquippedError() {
        Player player = new Player();
        UI ui = new UI();

        player.setCurrentRoom(gameData.getWorld().getEmergencyRoom());
        assertTrue(CommandValidation.look("ghost", player, ui, gameData.getNpc(), gameData.getWorld()).equalsIgnoreCase(ui.wrapFrame("You must have a charged camera to communicate with the ghosts")));
    }

    @Test
    public void ghostNotPresent_lookCommand_noGhostError() {
        Player player = new Player();
        UI ui = new UI();

        player.setCurrentRoom(gameData.getWorld().getBasement());
        assertTrue(CommandValidation.look("ghost", player, ui, gameData.getNpc(), gameData.getWorld()).equalsIgnoreCase("There is no ghost in this room"));
    }

    @Test
    public void itemPresent_pickupCommand_addedToPlayerInventory() {
        Player player = new Player();

        player.setCurrentRoom(gameData.getWorld().getBasement());
        assertTrue(CommandValidation.pickUp("camera", player).equalsIgnoreCase("You have picked up camera"));
        assertEquals(1, player.getInventory().size());
    }

    @Test
    public void itemNotPresent_pickupCommand_notInRoomError() {

        Player player = new Player();

        player.setCurrentRoom(gameData.getWorld().getBasement());

        assertTrue(CommandValidation.pickUp("paper-clip", player).equalsIgnoreCase("paper-clip is not in Basement"));
        assertEquals(0, player.getInventory().size());
    }
}