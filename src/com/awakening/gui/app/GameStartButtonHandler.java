package com.awakening.gui.app;

import com.awakening.app.Game;
import com.awakening.app.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameStartButtonHandler implements ActionListener {

    private static JPanel inputTextPanel, helpPanel;
    private static JTextField inputTextField;
    private static JButton inputTextButton, helpToMainButton;
    private static JLabel helpLabel;

    public static void showTextPage() {
        UI ui = new UI();
        Game gameClassLoad = new Game();

        gameClassLoad.generateWorld();
        String displayGameInfo = ui.displayGameInfo(Game.player);
        GameHomePage.getHomePageTextArea().setText(displayGameInfo);
        GameHomePage.getHomePageTextArea().setForeground(Color.RED);

        GameHomePage.getGameStartButtonPanel().setVisible(false);

        inputTextPanel = new JPanel();
        inputTextPanel.setBounds(250, 500, 400, 200);
        inputTextPanel.setBackground(Color.black);

        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(150, 40));
        inputTextField.setFont(Awakening_Font.getNormalFont());

        inputTextButton = new JButton("Submit");
        inputTextButton.setBackground(Color.black);
        inputTextButton.setForeground(Color.lightGray);
        inputTextButton.setFocusPainted(false);
        inputTextButton.setFont(Awakening_Font.buttonSelectionFont());
        inputTextButton.addActionListener(e -> {
            String input = e.getActionCommand();
            System.out.println(input);
            if (inputTextField.getText().equalsIgnoreCase("help")) {
                // calling the original help page works, but it looks messy.
                // Refactoring from txt to png to show help page
//                String helpPage = ui.displayGamePlayOptions();
//                System.out.println(helpPage);
//                GameHomePage.getInputTextResultLabel().setText(helpPage);

                GameHomePage.getHomePageTextPanel().setVisible(false);
                inputTextPanel.setVisible(false);

                helpPanel = new JPanel();
                helpPanel.setBounds(50, 0, 800, 600);
                helpPanel.setBackground(Color.black);

                helpLabel = new JLabel();
                ImageIcon helpIcon = new ImageIcon("resources/images/help.PNG");
                helpLabel.setIcon(helpIcon);

                helpToMainButton = new JButton("Return");
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
        });


        inputTextPanel.add(inputTextField);
        inputTextPanel.add(inputTextButton);

        GameStart.getContainer().add(inputTextPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showTextPage();
    }
}