package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window= new JFrame("Simple Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel= new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.launchGame();

    }
}