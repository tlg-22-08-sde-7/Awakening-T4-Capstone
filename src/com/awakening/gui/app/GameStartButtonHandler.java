package com.awakening.gui.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartButtonHandler implements ActionListener {

    public static void showTextPage(){
        GameHomePage.getHomePageTextArea().setText("AHHHHH!!!!!!");
        GameHomePage.getHomePageTextArea().setForeground(Color.RED);
        GameHomePage.getGameStartButton().setText("Continue");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showTextPage();
    }
}