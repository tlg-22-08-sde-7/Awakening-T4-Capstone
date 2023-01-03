package com.awakening.app;

import com.apps.util.Prompter;
import com.awakening.app.game.EvilSpirit;
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
        Prompter prompter = null;
        try {
            prompter = new Prompter(new Scanner(new File("test-resources/CorrectKeypadResponsesTest.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getHallway());

        assertTrue(CommandValidation.use("key-pad", Player.getPlayerInstance(), prompter, gameData.getWorld()).equalsIgnoreCase("The key-pad chimes and turns green."));
        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getRoom(Player.getPlayerInstance().getCurrentRoom().getDirections().get("east")));
        assertFalse(Player.getPlayerInstance().getCurrentRoom().isLocked());
    }

    @Test
    public void incorrectPIN_KeypadUsed_nextDoorStaysLocked() {
        Prompter prompter = null;
        try {
            prompter = new Prompter(new Scanner(new File("test-resources/IncorrectKeypadResponsesTest.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getHallway());

        assertTrue(CommandValidation.use("key-pad", Player.getPlayerInstance(), prompter, gameData.getWorld()).equalsIgnoreCase("The key-pad buzzes and flashes red."));
        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getRoom(Player.getPlayerInstance().getCurrentRoom().getDirections().get("east")));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().isLocked());
    }

    @Test
    public void correctDirection_moveCommand_movesPlayer() {
        EvilSpirit evilSpirit = new EvilSpirit();

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getEmergencyRoom());

        //Test North
        assertTrue(CommandValidation.move("north", Player.getPlayerInstance(), evilSpirit, gameData.getWorld()).equalsIgnoreCase("You have moved: north"));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getMorgue().getName()));

        //Test South
        assertTrue(CommandValidation.move("south", Player.getPlayerInstance(),evilSpirit, gameData.getWorld()).equalsIgnoreCase("You have moved: south"));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getEmergencyRoom().getName()));

        //Test East
        assertTrue(CommandValidation.move("east", Player.getPlayerInstance(), evilSpirit, gameData.getWorld()).equalsIgnoreCase("You have moved: east"));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getHallway().getName()));

        //Test West
        assertTrue(CommandValidation.move("west", Player.getPlayerInstance(),evilSpirit, gameData.getWorld()).equalsIgnoreCase("You have moved: west"));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getEmergencyRoom().getName()));
    }

    @Test
    public void incorrectDirection_moveCommand_playerDoesNotMove() {

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getMorgue());

        //Test East, not available direction from morgue
        //assertTrue(CommandValidation.move("east", player, gameData.getWorld()).equalsIgnoreCase("You can't go that way"));
        assertTrue(Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(gameData.getWorld().getMorgue().getName()));
    }

    @Test
    public void ghostPresentCameraEquipped_lookCommand_returnCorrectGhostDescription() {
        UI ui = new UI();

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getBasement());
        CommandValidation.pickUp("camera", Player.getPlayerInstance());

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getEmergencyRoom());
        assertTrue(CommandValidation.look("ghost", Player.getPlayerInstance(), ui, gameData.getNpc(), gameData.getEvilSpirit(), gameData.getWorld()).equalsIgnoreCase(ui.wrapFrame(gameData.getNpc().getGhost2().getDescription())));
    }

    @Test
    public void ghostPresentCameraNotEquipped_lookCommand_returnCameraNotEquippedError() {
        UI ui = new UI();
        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getEmergencyRoom());
        assertTrue(CommandValidation.look("ghost", Player.getPlayerInstance(), ui, gameData.getNpc(), gameData.getEvilSpirit(), gameData.getWorld()).equalsIgnoreCase(ui.wrapFrame("You must have a charged camera to communicate with the ghosts")));
    }

    @Test
    public void ghostNotPresent_lookCommand_noGhostError() {
        UI ui = new UI();

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getBasement());
        assertTrue(CommandValidation.look("ghost", Player.getPlayerInstance(), ui, gameData.getNpc(), gameData.getEvilSpirit(), gameData.getWorld()).equalsIgnoreCase("There is no ghost in this room"));
    }

    @Test
    public void itemPresent_pickupCommand_addedToPlayerInventory() {

        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getBasement());
        assertTrue(CommandValidation.pickUp("camera", Player.getPlayerInstance()).equalsIgnoreCase("You have picked up camera"));
        assertEquals(1, Player.getPlayerInstance().getInventory().size());
    }

    @Test
    public void itemNotPresent_pickupCommand_notInRoomError() {
        Player.getPlayerInstance().setCurrentRoom(gameData.getWorld().getBasement());

        assertTrue(CommandValidation.pickUp("paper-clip", Player.getPlayerInstance()).equalsIgnoreCase("paper-clip is not in Basement"));
        assertEquals(0, Player.getPlayerInstance().getInventory().size());
    }
}