package com.awakening.gui.app;

import com.awakening.app.UI;

import javax.swing.*;
import java.awt.*;

public class GameHomePage {
    /*
     * Game Home Page: this page will load after the player presses Enter from the splash screen.
     * This page contains:
     * -- The Intro story of the game
     * -- The START button of the game
     */
    private static JPanel homePageTextPanel;
    private static JPanel gameStartButtonPanel;
    private static JTextArea homePageTextArea;
    private static UI ui = new UI();
    private static JButton gameStartButton;


    public static void createHomePage(){
        GameStart.getSplash_panel().setVisible(false);
        GameStart.getStartButtonPanel().setVisible(false);

        homePageTextPanel = new JPanel();
        homePageTextPanel.setBounds(80, 50, 800, 300);
        homePageTextPanel.setBackground(Color.black);

        homePageTextArea = new JTextArea(ui.splashScreen());
        homePageTextArea.setBounds(50,0,600,250);
        homePageTextArea.setBackground(Color.black);
        homePageTextArea.setForeground(Color.green);
        homePageTextArea.setFont(Awakening_Font.getNormalFont());
        homePageTextArea.setLineWrap(true);
        homePageTextPanel.add(homePageTextArea);

        GameStart.getContainer().add(homePageTextPanel);
        // button
        gameStartButtonPanel = new JPanel();
        gameStartButtonPanel.setBounds(250, 500, 400, 200);
        gameStartButtonPanel.setBackground(Color.black);


        gameStartButton = new JButton("START");
        gameStartButton.setBackground(Color.black);
        gameStartButton.setForeground(Color.lightGray);
        gameStartButton.setFont(Awakening_Font.buttonSelectionFont());
        gameStartButton.setFocusPainted(false);
        gameStartButton.addActionListener(e -> GameManager.beginGameManager());

        gameStartButtonPanel.add(gameStartButton);
        GameStart.getContainer().add(gameStartButtonPanel);
    }

    public static JTextArea getHomePageTextArea() {
        return homePageTextArea;
    }

    public static JButton getGameStartButton() {
        return gameStartButton;
    }

    public static JPanel getGameStartButtonPanel() {
        return gameStartButtonPanel;
    }

    public static JPanel getHomePageTextPanel() {
        return homePageTextPanel;
    }
}