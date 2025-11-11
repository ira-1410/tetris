package tetris;

import java.awt.event.*;

public class KeyEventHandler implements KeyListener {
    public static boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, dropPressed, restart;
    
    @Override 
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) upPressed=true;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) downPressed=true;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) leftPressed=true;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) rightPressed=true;
        if (keyCode == KeyEvent.VK_X || keyCode == KeyEvent.VK_SHIFT) dropPressed = true;
        if (keyCode == KeyEvent.VK_R) GamePanel.gameManager.restart(); 
        if (keyCode == KeyEvent.VK_SPACE) {
            if (GameManager.state == GameManager.GameState.PLAYING) {
                GameManager.state = GameManager.GameState.PAUSED;
            } else if (GameManager.state == GameManager.GameState.PAUSED) {
                GameManager.state = GameManager.GameState.PLAYING;
            } else if (GameManager.state == GameManager.GameState.WAITING) {
                GameManager.state = GameManager.GameState.PLAYING;
            }
        }  
        

    }
}