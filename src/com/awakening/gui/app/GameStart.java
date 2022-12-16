package com.awakening.gui.app;

import com.awakening.app.game.Player;

import javax.swing.*;
import java.awt.*;


public class GameStart {
    private JFrame window;
    private Container container;
    private JPanel panel_title;
    private JPanel startButtonPanel;
    private JLabel label_title;
    private JButton start_button;

    public GameStart(){

        window = new JFrame();
        window.setSize(900, 1200);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);

        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();

        panel_title = new JPanel();
        panel_title.setBounds(50, 0, 800, 600);
        panel_title.setBackground(Color.black);

//         load the game splash/title screen image as a label
        ImageIcon icon = new ImageIcon("resources/images/titleScreen.PNG");
        label_title = new JLabel(icon);

        // start button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(250, 630, 400, 200);
        startButtonPanel.setBackground(Color.black);

        start_button = new JButton("PRESS ENTER TO START");
        start_button.setBackground(Color.black);
        start_button.setForeground(Color.lightGray);
        start_button.setFont(Awakening_Font.getNormalFont());
        start_button.addActionListener(e -> ProgressScreen.startProgressScreen(this));

        // set a default button that will automatically listen to the Enter key
        window.getRootPane().setDefaultButton(start_button);

        panel_title.add(label_title);
        startButtonPanel.add(start_button);
        container.add(panel_title);
        container.add(startButtonPanel);
    }

    public JPanel getPanel_title() {
        return panel_title;
    }

    public void setPanel_title(JPanel panel_title) {
        this.panel_title = panel_title;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public JPanel getStartButtonPanel() {
        return startButtonPanel;
    }

    public void setStartButtonPanel(JPanel startButtonPanel) {
        this.startButtonPanel = startButtonPanel;
    }
}