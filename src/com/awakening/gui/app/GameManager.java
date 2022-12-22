package com.awakening.gui.app;

import com.awakening.app.Game;
import com.awakening.app.TextParser;
import com.awakening.app.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class GameManager {

    private static JPanel inputTextPanel, helpPanel, directionalPanel;
    private static JTextField inputTextField;
    private static JButton inputTextSubmitButton, helpToMainButton;
    private static JLabel helpLabel, imageLabel, mapLabel;
    private static JFrame sharedWindow;
    private static LayoutManager layoutManager;
    private static Game gameClassLoad;

    public static void beginGameManager() {

        gameClassLoad = new Game();
        layoutManager = new LayoutManager();
        layoutManager.setBackground(Color.black);

        gameClassLoad.generateWorld();

        sharedWindow = GameStart.getWindow();
        sharedWindow.setContentPane(layoutManager);
        sharedWindow.setSize(1400, 850);

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

        layoutManager.addGB(imageLabel, 0, 0, 2, 4, .2, .1);
    }

    public static void populateTextGrid() {
        UI ui = new UI();

        String displayGameInfo = ui.displayGameInfo(Game.player);
        GameHomePage.getHomePageTextArea().setText(displayGameInfo);
        GameHomePage.getHomePageTextArea().setFont(Awakening_Font.getSmallTextFont());
        GameHomePage.getHomePageTextArea().setForeground(Color.green);

        layoutManager.addGB(GameStart.getContainer(), 0, 3, 3, 4, 0.1, .1);
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

        baseIcon = new ImageIcon("resources/images/North.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        north.setIcon(scaledIcon);
        north.addActionListener(e -> {
            List<String> command = new ArrayList<>();
            command.add("go");
            command.add("north");
            gameClassLoad.executeCommand(command);
        });
        north.setBackground(Color.black);
        north.setForeground(Color.black);
        directionalPanel.add(north, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        JButton south = new JButton();

        baseIcon = new ImageIcon("resources/images/South.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        south.setIcon(scaledIcon);
        south.addActionListener(e -> {
            List<String> command = new ArrayList<>();
            command.add("go");
            command.add("south");
            gameClassLoad.executeCommand(command);
        });
        south.setBackground(Color.black);
        south.setForeground(Color.black);
        directionalPanel.add(south, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        JButton east = new JButton();

        baseIcon = new ImageIcon("resources/images/East.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        east.setIcon(scaledIcon);
        east.addActionListener(e -> {
            List<String> command = new ArrayList<>();
            command.add("go");
            command.add("east");
            gameClassLoad.executeCommand(command);
        });
        east.setBackground(Color.black);
        east.setForeground(Color.black);
        directionalPanel.add(east, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        JButton west = new JButton();

        baseIcon = new ImageIcon("resources/images/West.png");
        img = baseIcon.getImage();
        img.getScaledInstance(150, 100, Image.SCALE_DEFAULT);
        scaledIcon = new ImageIcon(img);

        west.setIcon(scaledIcon);
        west.addActionListener(e -> {
            List<String> command = new ArrayList<>();
            command.add("go");
            command.add("west");
            gameClassLoad.executeCommand(command);
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
            List<String> command = textParser.parseInput(input);
            if (!command.get(0).equals("invalid")){
                if (command.get(0).equalsIgnoreCase("help")) {
                    //showImagePage("resources/images/help.PNG", "Return");
                }
                // add quit logic
                else if (command.get(0).equalsIgnoreCase("quit")) {
                    // TODO: exit page before exiting the program
                    System.exit(0);
                }
                else{
                    gameClassLoad.executeCommand(command);
                }
            }
            // resets the player's input
            inputTextField.setText("");
        });

        inputTextPanel.add(inputTextField);
        inputTextPanel.add(inputTextSubmitButton);

        layoutManager.addGB(directionalPanel, 4, 4, 2, 2, .8, .3);
        layoutManager.addGB(inputTextPanel, 4, 6, 1, 2, .5, .5);
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
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        JButton help = new JButton("Help");
        help.addActionListener(e -> {

        });

        mapPanel.add(help, constraints);

        layoutManager.addGB(mapPanel, 4, 0, 2, 2, .3, .1);
    }

//    public static void showImagePage(String imagePath, String buttonName) {
//        GameHomePage.getHomePageTextPanel().setVisible(false);
//        inputTextPanel.setVisible(false);
//
//        helpPanel = new JPanel();
//        helpPanel.setBounds(50, 0, 800, 600);
//        helpPanel.setBackground(Color.black);
//
//        helpLabel = new JLabel();
//        ImageIcon helpIcon = new ImageIcon(imagePath);
//        helpLabel.setIcon(helpIcon);
//
//        helpToMainButton = new JButton(buttonName);
//        helpToMainButton.setBackground(Color.black);
//        helpToMainButton.setForeground(Color.lightGray);
//        helpToMainButton.setFocusPainted(false);
//        helpToMainButton.setFont(Awakening_Font.buttonSelectionFont());
//        helpToMainButton.addActionListener(e1 -> {
//            helpPanel.setVisible(false);
//            GameHomePage.getHomePageTextPanel().setVisible(true);
//            showTextPage();
//        });
//
//        helpPanel.add(helpLabel);
//        helpPanel.add(helpToMainButton);
//
//        GameStart.getContainer().add(helpPanel);
//        inputTextField.setText("  ");
//    }

    public static void scaleImageAndInsertToLabel(String imageLocation, JLabel label){
        ImageIcon icon = new ImageIcon(imageLocation);
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(900, 600, Image.SCALE_SMOOTH);
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

    public static JTextField getInputTextField() {
        return inputTextField;
    }

    public static JLabel getImageLabel() {
        return imageLabel;
    }

    public static JLabel getMapLabel() {
        return mapLabel;
    }
}