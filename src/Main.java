package tetris;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);


        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();
        window.setVisible(true);
        panel.init();
    }
}