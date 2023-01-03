package com.awakening.gui.app;

import com.awakening.gui.util.Audio;

import javax.swing.*;
import java.awt.*;

public class GameStart {
    private static JFrame window;
    private static Container container;
    private static JPanel splash_panel;
    private static JPanel buttonPanel_start_screen;
    private JLabel splash_label;
    private static Audio background_main = new Audio("resources/audio/background-main.wav");

    private JButton pressEnterToContinueButton, musicSwitchButton;

    public GameStart(){
        window = new JFrame();
        window.setSize(925, 900);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);

        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();

        splash_panel = new JPanel();
        splash_panel.setBounds(50, 0, 800, 600);
        splash_panel.setBackground(Color.black);

        // load the game splash/title screen image as a label
        ImageIcon icon = new ImageIcon("resources/images/titleScreen.PNG");
        splash_label = new JLabel(icon);

        background_main.loopAudio();

        // press enter to start button
        buttonPanel_start_screen = new JPanel();
        buttonPanel_start_screen.setBounds(250, 630, 400, 200);
        buttonPanel_start_screen.setBackground(Color.black);

        pressEnterToContinueButton = new JButton("PRESS ENTER TO START");
        pressEnterToContinueButton.setBackground(Color.black);
        pressEnterToContinueButton.setForeground(Color.lightGray);
        pressEnterToContinueButton.setFont(Awakening_Font.getSmallTextFont());
        pressEnterToContinueButton.setFocusPainted(false);
        pressEnterToContinueButton.addActionListener(e -> GameHomePage.createHomePage());

        musicSwitchButton = new JButton("MUSIC OFF");
        musicSwitchButton.setBackground(Color.black);
        musicSwitchButton.setForeground(Color.lightGray);
        musicSwitchButton.setFont(Awakening_Font.getSmallTextFont());
        musicSwitchButton.setFocusPainted(false);
        musicSwitchButton.addActionListener(e -> background_main.stopAudio());

        // set a default button that will automatically listen to the Enter key
        window.getRootPane().setDefaultButton(pressEnterToContinueButton);

        splash_panel.add(splash_label);
        buttonPanel_start_screen.add(pressEnterToContinueButton);
        buttonPanel_start_screen.add(musicSwitchButton);
        container.add(splash_panel);
        container.add(buttonPanel_start_screen);
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
}