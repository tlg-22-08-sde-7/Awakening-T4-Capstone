package com.awakening.gui.app;

import com.awakening.app.Game;
import com.awakening.app.TextParser;
import com.awakening.app.UI;
import com.awakening.app.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class GameManager {

    private static JPanel inputTextPanel;
    private static JPanel directionalPanel;
    private static JTextField inputTextField;
    private static JButton inputTextSubmitButton;
    private static final JButton helpButton = new JButton();
    private static JButton audioButton = new JButton();
    private static JLabel imageLabel;
    private static JLabel mapLabel;
    private static JFrame sharedWindow;
    private static LayoutManager layoutManager;
    private static Game gameClassLoad;
    private static boolean helpActive = false;
    public static boolean combatActive = false;
    public static boolean audioActive = true;

    public static void beginGameManager() {
        GameStart.getContainer().remove(GameHomePage.getGameStartButtonPanel());
        gameClassLoad = new Game();
        layoutManager = new LayoutManager();
        layoutManager.setBackground(Color.black);

        gameClassLoad.generateWorld();

        sharedWindow = GameStart.getWindow();
        sharedWindow.setContentPane(layoutManager);
        //sharedWindow.setSize(1400, 850);
        //sharedWindow.pack();
        //sharedWindow.setSize(1250, 650);

        //create image
        populateImageGrid();

        //create text
        populateTextGrid();

        //create directional
        populateDirectionalGrid();

        //create map w/ game option buttons
        populateMapButtonsGrid();

        String basementFilePath = "resources/images/Basement.PNG";
        scaleImageAndInsertToLabel(basementFilePath, imageLabel);

        String startingMap = "resources/images/Map_Basement.png";
        scaleImageAndInsertToMap(startingMap, mapLabel);

        sharedWindow.setVisible(true);
    }

    public static void populateImageGrid() {
        // Refactor needed to make dynamic with current room image
        ImageIcon icon = new ImageIcon("resources/images/titleScreen.PNG");
        imageLabel = new JLabel(icon);

        layoutManager.addGB(imageLabel, 0, 0, 2, 4, 1, 1);
    }

    public static void populateTextGrid() {
        UI ui = new UI();
        String displayGameInfo;

        if (combatActive) {
            displayGameInfo = ui.displayCombatInfo(Player.getPlayerInstance(), gameClassLoad.getEvilSpirit());
        } else {
            displayGameInfo = ui.displayGameInfo(Player.getPlayerInstance());
        }

        GameHomePage.getHomePageTextArea().setText(displayGameInfo);
        GameHomePage.getHomePageTextArea().setFont(Awakening_Font.getSmallTextFont());
        GameHomePage.getHomePageTextArea().setForeground(Color.green);

        layoutManager.addGB(GameStart.getContainer(), 0, 3, 4, 4, 1, 1);
    }

    public static void populateDirectionalGrid() {
        GridBagConstraints constraints = new GridBagConstraints();
        TextParser textParser = new TextParser();
        ImageIcon baseIcon, scaledIcon;
        Image img;
        constraints.fill = GridBagConstraints.BOTH;

        // Create JPanel for Directionals with LayoutManager
        directionalPanel = new JPanel();
        directionalPanel.setBackground(Color.black);
        directionalPanel.setLayout(new GridBagLayout());

        // Create Buttons with appropriate placement
        constraints.gridx = 2;
        constraints.gridy = 0;
        JButton north = new JButton();
        north.setBorder(BorderFactory.createEmptyBorder());

        baseIcon = new ImageIcon("resources/images/North.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        north.setIcon(scaledIcon);
        north.addActionListener(e -> {
            if (gameClassLoad.evilSpiritCheck()){
                updateTextField("Invalid command, you must engage with evil spirit\n" +"You can hide or use camera");
            }
            else {
                List<String> command = new ArrayList<>();
                command.add("go");
                command.add("north");
                gameClassLoad.executeCommand(command);
            }
        });
        north.setBackground(Color.black);
        north.setForeground(Color.black);
        directionalPanel.add(north, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        JButton south = new JButton();
        south.setBorder(BorderFactory.createEmptyBorder());

        baseIcon = new ImageIcon("resources/images/South.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        south.setIcon(scaledIcon);
        south.addActionListener(e -> {
            if (gameClassLoad.evilSpiritCheck()){
                updateTextField("Invalid command, you must engage with evil spirit\n" +"You can hide or use camera");
            }
            else {
                List<String> command = new ArrayList<>();
                command.add("go");
                command.add("south");
                gameClassLoad.executeCommand(command);
            }
        });
        south.setBackground(Color.black);
        south.setForeground(Color.black);
        directionalPanel.add(south, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        JButton east = new JButton();
        east.setBorder(BorderFactory.createEmptyBorder());

        baseIcon = new ImageIcon("resources/images/East.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        east.setIcon(scaledIcon);
        east.addActionListener(e -> {
            if (gameClassLoad.evilSpiritCheck()){
                updateTextField("Invalid command, you must engage with evil spirit\n" +"You can hide or use camera");
            }
            else {
                List<String> command = new ArrayList<>();
                command.add("go");
                command.add("east");
                gameClassLoad.executeCommand(command);
            }
        });
        east.setBackground(Color.black);
        east.setForeground(Color.black);
        directionalPanel.add(east, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        JButton west = new JButton();
        west.setBorder(BorderFactory.createEmptyBorder());

        baseIcon = new ImageIcon("resources/images/West.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        west.setIcon(scaledIcon);
        west.addActionListener(e -> {
            if (gameClassLoad.evilSpiritCheck()){
                updateTextField("Invalid command, you must engage with evil spirit\n" +"You can hide or use camera");
            }
            else {
                List<String> command = new ArrayList<>();
                command.add("go");
                command.add("west");
                gameClassLoad.executeCommand(command);
            }
        });
        west.setBackground(Color.black);
        west.setForeground(Color.black);
        directionalPanel.add(west, constraints);

        inputTextPanel = new JPanel();
        inputTextPanel.setBackground(Color.black);

        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(150, 40));
        inputTextField.setFont(Awakening_Font.getNormalFont());

        // the player can press enter to submit the commands given in the text field
        inputTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    inputTextSubmitButton.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    inputTextSubmitButton.doClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // when the player clicks on the text field, clear the existing entry
        inputTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                inputTextField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        inputTextSubmitButton = new JButton();

        baseIcon = new ImageIcon("resources/images/Enter.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        inputTextSubmitButton.setIcon(scaledIcon);
        inputTextSubmitButton.setBackground(Color.black);
        inputTextSubmitButton.setForeground(Color.lightGray);
        inputTextSubmitButton.setFocusPainted(false);
        inputTextSubmitButton.setFont(Awakening_Font.buttonSelectionFont());
        inputTextSubmitButton.addActionListener(e -> {
            String input = inputTextField.getText();
            UI ui = new UI();

            // if in combat utilize combat parser
            if(combatActive) {
                List<String> command = textParser.combatParser(input);
                String text;

                if (!command.get(0).equals("invalid")) {
                    if (command.get(0).equalsIgnoreCase("help")) {
                        helpButton.doClick();
                    }
                    // add quit logic
                    else if (command.get(0).equalsIgnoreCase("quit")) {
                        // TODO: exit page before exiting the program
                        System.exit(0);
                    } else if (command.get(0).equalsIgnoreCase("hide")){
                        if (gameClassLoad.attemptToHide(false)) {
                            // TODO: Player died, exit game
                            System.exit(0);
                        } else {
                            text = "You successfully hide..." +
                                    "\n" + ui.displayGameInfo(Player.getPlayerInstance());
                            updateTextField(text);
                            combatActive = false;
                        }
                    } else {
                        switch (gameClassLoad.attemptCameraUsage()) {
                            case 1:
                                text = "The camera clicks, but you realize the batteries are dead..." +
                                        "\n" + gameClassLoad.getEvilSpirit().getName() + " begins walking towards you, and you " +
                                        "instinctively try hiding...";
                                updateTextField(text);
                                if (gameClassLoad.attemptToHide(true)) {
                                    // TODO: Player died, exit game
                                    System.exit(0);
                                } else {
                                    text = "You successfully hide..." +
                                            "\n" + ui.displayGameInfo(Player.getPlayerInstance());
                                    updateTextField(text);
                                    combatActive = false;
                                }
                                break;
                            case 2:
                                text = "You do not have the camera in your inventory..." + "\n" +
                                        gameClassLoad.getEvilSpirit().getName() + " begins walking towards you, and you " +
                                        "instinctively try hiding...";
                                updateTextField(text);
                                if (gameClassLoad.attemptToHide(true)) {
                                    // TODO: Player died, exit game
                                    System.exit(0);
                                } else {
                                    text = "You successfully hide..." +
                                            "\n" + ui.displayGameInfo(Player.getPlayerInstance());
                                    updateTextField(text);
                                    combatActive = false;
                                }
                                break;
                            case 3:
                                text = "The camera flashes and you hear an unearthly scream and snarl...\n" +
                                        gameClassLoad.getEvilSpirit().getName() + " vanishes..." +
                                        "\n\n" + ui.displayGameInfo(Player.getPlayerInstance());
                                updateTextField(text);
                                while (Player.getPlayerInstance().getCurrentRoom().getName().equalsIgnoreCase(
                                        gameClassLoad.getEvilSpirit().getCurrentRoom().getName())) {
                                    gameClassLoad.getEvilSpirit().setRandomRoom(gameClassLoad.getWorld());
                                }
                                combatActive = false;
                        }
                    }
                }
            } else {
                List<String> command = textParser.parseInput(input);
                if (!command.get(0).equals("invalid")) {
                    if (command.get(0).equalsIgnoreCase("help")) {
                        helpButton.doClick();
                    }
                    // add quit logic
                    else if (command.get(0).equalsIgnoreCase("quit")) {
                        // TODO: exit page before exiting the program
                        System.exit(0);
                    } else {
                        gameClassLoad.executeCommand(command);
                    }
                }
            }
            // resets the player's input
            inputTextField.setText("");
        });

        inputTextPanel.add(inputTextField);
        inputTextPanel.add(inputTextSubmitButton);

        layoutManager.addGB(directionalPanel, 4, 4, 2, 2, 1, 1);
        layoutManager.addGB(inputTextPanel, 4, 6, 1, 2, 1, 1);
    }

    public static void populateMapButtonsGrid() {
        GridBagConstraints constraints = new GridBagConstraints();
        JPanel mapPanel = new JPanel();

        ImageIcon icon = new ImageIcon("resources/images/Map_Basement.png");
        mapLabel = new JLabel(icon);

        constraints.fill = GridBagConstraints.BOTH;
        mapPanel.setBackground(Color.black);
        mapPanel.setLayout(new GridBagLayout());

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 3;

        mapPanel.add(mapLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        helpButton.setBorder(BorderFactory.createEmptyBorder());
        helpButton.setOpaque(true);
        helpButton.setBackground(Color.black);
        helpButton.setForeground(Color.black);
        helpButton.setIcon(getHelpIcon());

        helpButton.addActionListener(e -> {
            if (helpActive) {
                String currentRoom = "resources/images/"+gameClassLoad.getPlayer().getCurrentRoom().getName()+".PNG";
                scaleImageAndInsertToLabel(currentRoom, imageLabel);
                helpActive = false;
            } else {
                String helpImage = "resources/images/help.PNG";
                scaleImageAndInsertToLabel(helpImage, imageLabel);
                helpActive = true;
            }
        });

        mapPanel.add(helpButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        audioButton.setBorder(BorderFactory.createEmptyBorder());
        audioButton.setOpaque(true);
        audioButton.setBackground(Color.black);
        audioButton.setForeground(Color.black);
        audioButton.setIcon(getAudioIcon());

        audioButton.addActionListener(e -> {
            if (audioActive) {
                GameStart.getBackground_main().stopAudio();
                audioActive = false;
            } else {
                GameStart.getBackground_main().loopAudio();
                audioActive = true;
            }
        });
        mapPanel.add(audioButton, constraints);

        layoutManager.addGB(mapPanel, 4, 0, 2, 2, 1, 1);
    }

    public static ImageIcon getAudioOnIcon() {
        ImageIcon baseIcon = new ImageIcon("resources/images/AudioOn.png");
        Image img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);

        return new ImageIcon(img);
    }

    public static ImageIcon getAudioIcon() {
        ImageIcon baseIcon = new ImageIcon("resources/images/AudioButton.png");
        Image img = baseIcon.getImage();
        img.getScaledInstance(150, 50, Image.SCALE_DEFAULT);

        return new ImageIcon(img);
    }

    public static ImageIcon getHelpIcon() {
        ImageIcon baseIcon = new ImageIcon("resources/images/HelpButton.png");
        Image img = baseIcon.getImage();
        img.getScaledInstance(150, 50, Image.SCALE_DEFAULT);

        return new ImageIcon(img);
    }

    public static void scaleImageAndInsertToLabel(String imageLocation, JLabel label){
        ImageIcon icon = new ImageIcon(imageLocation);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(900, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    public static void scaleImageAndInsertToMap(String imageLocation, JLabel label) {
        ImageIcon icon = new ImageIcon(imageLocation);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        label.setIcon(scaledIcon);
    }

    public static JLabel getImageLabel() {
        return imageLabel;
    }

    public static JLabel getMapLabel() {
        return mapLabel;
    }

    private static void updateTextField(String text) {
        GameHomePage.getHomePageTextArea().setText(text);
        GameHomePage.getHomePageTextArea().setFont(Awakening_Font.getSmallTextFont());
        GameHomePage.getHomePageTextArea().setForeground(Color.green);
    }
}