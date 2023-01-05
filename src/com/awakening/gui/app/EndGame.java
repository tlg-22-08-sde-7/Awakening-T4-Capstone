package com.awakening.gui.app;

import javax.swing.*;
import java.awt.*;

public class EndGame {
    private static JButton quitButton = new JButton("QUIT");
    private static JButton restartButton = new JButton("RESTART");
    private static JPanel endGamePanel = new JPanel();
    private static ImageIcon endGame;
    private static JLabel endGameLabel;
    private static GridBagConstraints c = new GridBagConstraints();

    /**
     * Method displays win graphics and allows player to exit game or restart
     */
    public static void endGame(boolean playerWon) {
        // Purge existing game swing components
        GameManager.getSharedWindow().getContentPane().removeAll();
        GameManager.getSharedWindow().getContentPane().repaint();

        // Display winning Image
        endGamePanel.setLayout(new GridBagLayout());
        endGamePanel.setSize(new Dimension(800, 600));
        endGamePanel.setBackground(Color.black);

        endGame = playerWon ? new ImageIcon("resources/images/Game Win.PNG") :
            new ImageIcon("resources/images/Game Over.PNG");
        endGameLabel = new JLabel(endGame);


        quitButton.setBackground(Color.black);
        quitButton.setForeground(Color.lightGray);
        quitButton.setFont(Awakening_Font.getSmallTextFont());
        quitButton.setFocusPainted(false);
        quitButton.addActionListener( e -> System.exit(0));

        restartButton.setBackground(Color.black);
        restartButton.setForeground(Color.lightGray);
        restartButton.setFont(Awakening_Font.getSmallTextFont());
        restartButton.setFocusPainted(false);
        //restartButton.addActionListener( e -> GameStart.restartGame());

        // Add image
        c.gridx = 1;
        c.gridy = 0;
        endGamePanel.add(endGameLabel, c);

        // add Quit Button
        c.gridx = 0;
        c.gridy = 1;
        endGamePanel.add(quitButton, c);

        // add Restart Button
        c.gridx = 2;
        c.gridy = 1;
        endGamePanel.add(restartButton, c);

        GameManager.getSharedWindow().getContentPane().add(endGamePanel);
        GameManager.getSharedWindow().setVisible(true);
        GameManager.getSharedWindow().getContentPane().repaint();
    }
}