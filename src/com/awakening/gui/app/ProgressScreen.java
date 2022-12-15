package com.awakening.gui.app;

import javax.swing.*;
import java.awt.*;

public class ProgressScreen {
    private static JPanel mainTextPanel;
    private static JTextArea mainTextArea;

    public static void startProgressScreen(GameStart game){

        game.getPanel_title().setVisible(false);
        game.getStartButtonPanel().setVisible(false);

        mainTextPanel  = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.red);
        game.getContainer().add(mainTextPanel);

        mainTextArea = new JTextArea("AHHHHHHHHHHHHH!!!!!");
        mainTextArea.setBounds(100,100,600,250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.lightGray);
        mainTextArea.setFont(Awakening_Font.getNormalFont());
        mainTextArea.setLineWrap(true);

        mainTextPanel.add(mainTextArea);
    }


}