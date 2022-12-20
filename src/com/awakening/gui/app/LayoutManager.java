package com.awakening.gui.app;

import java.awt.*;
import javax.swing.*;

public class LayoutManager extends JPanel {
    GridBagConstraints constraints = new GridBagConstraints();

    public LayoutManager() {
        setLayout(new GridBagLayout());
        constraints.fill = GridBagConstraints.BOTH;
    }

    public void addGB(Component component, int x, int y, int gridH, int gridW, double weightX, double weightY) {
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.gridheight = gridH;
        constraints.gridwidth = gridW;
        constraints.gridx = x;
        constraints.gridy = y;
        add(component, constraints);
    }
}