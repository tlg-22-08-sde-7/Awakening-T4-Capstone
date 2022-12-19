package com.awakening.gui.app;

import com.awakening.app.Game;

import javax.swing.*;
import java.awt.*;

public class GameStart {
    private JFrame window;
    private static Container container;
    private static JPanel splash_panel;
    private static JPanel pressEnterToStartButton_panel;
    private JLabel splash_label;

    private JButton pressEnterToContinueButton;

    public GameStart(){

        Game game = new Game();
        game.generateWorld();

        window = new JFrame();
        window.setSize(900, 1200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);

        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();

        splash_panel = new JPanel();
        splash_panel.setBounds(50, 0, 800, 600);
        splash_panel.setBackground(Color.black);

//         load the game splash/title screen image as a label
        ImageIcon icon = new ImageIcon("resources/images/titleScreen.PNG");
        splash_label = new JLabel(icon);

        // press enter to start button
        pressEnterToStartButton_panel = new JPanel();
        pressEnterToStartButton_panel.setBounds(250, 630, 400, 200);
        pressEnterToStartButton_panel.setBackground(Color.black);

        pressEnterToContinueButton = new JButton("PRESS ENTER TO START");
        pressEnterToContinueButton.setBackground(Color.black);
        pressEnterToContinueButton.setForeground(Color.lightGray);
        pressEnterToContinueButton.setFont(Awakening_Font.getNormalFont());
        pressEnterToContinueButton.setFocusPainted(false);
        pressEnterToContinueButton.addActionListener(e -> GameHomePage.createHomePage());

        // set a default button that will automatically listen to the Enter key
        window.getRootPane().setDefaultButton(pressEnterToContinueButton);

        splash_panel.add(splash_label);
        pressEnterToStartButton_panel.add(pressEnterToContinueButton);
        container.add(splash_panel);
        container.add(pressEnterToStartButton_panel);
    }

    public static JPanel getSplash_panel() {
        return splash_panel;
    }

    public static Container getContainer() {
        return container;
    }

    public static JPanel getStartButtonPanel() {
        return pressEnterToStartButton_panel;
    }
}