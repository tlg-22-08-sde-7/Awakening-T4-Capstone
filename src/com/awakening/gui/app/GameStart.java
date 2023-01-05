package com.awakening.gui.app;

import com.awakening.app.game.Player;
import com.awakening.gui.util.Audio;

import javax.swing.*;
import java.awt.*;

public class GameStart {
    private static JFrame window;
    private static Container container;
    private static JPanel splash_panel;
    private static JPanel buttonPanel_start_screen;
    private static JLabel splash_label;
    private static Audio background_main = new Audio("resources/audio/background-main.wav");

    private static JButton pressEnterToContinueButton, musicSwitchButton;

    public GameStart(){
        window = new JFrame();
        beginGame();
    }

    public static void beginGame() {
        GridBagConstraints c = new GridBagConstraints();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);

        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();
        container.setLayout(new GridBagLayout());

        splash_panel = new JPanel();
        splash_panel.setSize(new Dimension(800, 600));
        splash_panel.setBackground(Color.black);

        // load the game splash/title screen image as a label
        ImageIcon icon = new ImageIcon("resources/images/titleScreen.PNG");
        splash_label = new JLabel(icon);

        background_main.loopAudio();

        // press enter to start button
        buttonPanel_start_screen = new JPanel();
        buttonPanel_start_screen.setSize(new Dimension(400, 200));
        buttonPanel_start_screen.setBackground(Color.black);

        pressEnterToContinueButton = new JButton("PRESS ENTER TO START");
        pressEnterToContinueButton.setBackground(Color.black);
        pressEnterToContinueButton.setForeground(Color.lightGray);
        pressEnterToContinueButton.setFont(Awakening_Font.getSmallTextFont());
        pressEnterToContinueButton.setFocusPainted(false);
        pressEnterToContinueButton.addActionListener(e -> GameHomePage.createHomePage());

        musicSwitchButton = new JButton("MUSIC");
        musicSwitchButton.setBackground(Color.black);
        musicSwitchButton.setForeground(Color.lightGray);
        musicSwitchButton.setFont(Awakening_Font.getSmallTextFont());
        musicSwitchButton.setFocusPainted(false);
        musicSwitchButton.addActionListener(e -> {
            if (GameManager.audioActive) {
                background_main.stopAudio();
                GameManager.audioActive = false;
            } else {
                background_main.loopAudio();
                GameManager.audioActive = true;
            }
        });

        // set a default button that will automatically listen to the Enter key
        window.getRootPane().setDefaultButton(pressEnterToContinueButton);

        splash_panel.add(splash_label);
        buttonPanel_start_screen.add(pressEnterToContinueButton);
        buttonPanel_start_screen.add(musicSwitchButton);

        c.gridx = 0;
        c.gridy = 1;
        container.add(splash_panel, c);

        c.gridx = 0;
        c.gridy = 2;
        container.add(buttonPanel_start_screen, c);
        window.getContentPane().repaint();
    }

    public static void restartGame() {
        window.getContentPane().removeAll();
        window.getContentPane().repaint();
        Player.getPlayerInstance().resetPlayer();
        beginGame();
    }

    public static JPanel getSplash_panel() {
        return splash_panel;
    }

    public static Container getContainer() {
        return container;
    }

    public static JPanel getStartButtonPanel() {
        return buttonPanel_start_screen;
    }

    public static JFrame getWindow() {
        return window;
    }

    public static Audio getBackground_main() {
        return background_main;
    }
}