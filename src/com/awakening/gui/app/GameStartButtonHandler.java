package com.awakening.gui.app;

import com.awakening.app.Game;
import com.awakening.app.TextParser;
import com.awakening.app.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;


public class GameStartButtonHandler {

    private static JPanel inputTextPanel, helpPanel;
    private static JTextField inputTextField;
    private static JButton inputTextSubmitButton, helpToMainButton;
    private static JLabel helpLabel;

    public static void showTextPage() {
        UI ui = new UI();
        Game gameClassLoad = new Game();
        TextParser textParser = new TextParser();

        gameClassLoad.generateWorld();

        String displayGameInfo = ui.displayGameInfo(Game.player);
        GameHomePage.getHomePageTextArea().setText(displayGameInfo);
        GameHomePage.getHomePageTextArea().setFont(Awakening_Font.getNormalFont());
        GameHomePage.getHomePageTextArea().setForeground(Color.RED);

        GameHomePage.getGameStartButtonPanel().setVisible(false);

        inputTextPanel = new JPanel();
        inputTextPanel.setBounds(250, 500, 400, 200);
        inputTextPanel.setBackground(Color.black);

        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(150, 40));
        inputTextField.setFont(Awakening_Font.getNormalFont());

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

        inputTextSubmitButton = new JButton("Submit");
        inputTextSubmitButton.setBackground(Color.black);
        inputTextSubmitButton.setForeground(Color.lightGray);
        inputTextSubmitButton.setFocusPainted(false);
        inputTextSubmitButton.setFont(Awakening_Font.buttonSelectionFont());
        inputTextSubmitButton.addActionListener(e -> {
            String input = inputTextField.getText();
            List<String> command = textParser.parseInput(input);
            if (!command.get(0).equals("invalid")){
                if (command.get(0).equalsIgnoreCase("help")) {
                    showImagePage("resources/images/help.PNG", "Return");
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
        });

        inputTextPanel.add(inputTextField);
        inputTextPanel.add(inputTextSubmitButton);

        GameStart.getContainer().add(inputTextPanel);
    }

    public static void showImagePage(String imagePath, String buttonName) {
        GameHomePage.getHomePageTextPanel().setVisible(false);
        inputTextPanel.setVisible(false);

        helpPanel = new JPanel();
        helpPanel.setBounds(50, 0, 800, 600);
        helpPanel.setBackground(Color.black);

        helpLabel = new JLabel();
        ImageIcon helpIcon = new ImageIcon(imagePath);
        helpLabel.setIcon(helpIcon);

        helpToMainButton = new JButton(buttonName);
        helpToMainButton.setBackground(Color.black);
        helpToMainButton.setForeground(Color.lightGray);
        helpToMainButton.setFocusPainted(false);
        helpToMainButton.setFont(Awakening_Font.buttonSelectionFont());
        helpToMainButton.addActionListener(e1 -> {
            helpPanel.setVisible(false);
            GameHomePage.getHomePageTextPanel().setVisible(true);
            showTextPage();
        });

        helpPanel.add(helpLabel);
        helpPanel.add(helpToMainButton);

        GameStart.getContainer().add(helpPanel);
        inputTextField.setText("  ");
    }

    public static JTextField getInputTextField() {
        return inputTextField;
    }
}