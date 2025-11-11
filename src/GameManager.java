package tetris;

import tetris.tetromino.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;


public class GameManager {
    public enum GameState {
        WAITING, PLAYING, PAUSED, GAME_OVER
    } public static GameState state = GameState.WAITING;

    static Sound rowClearSound = new Sound("row-clear.wav");
    static Sound gameOverSound = new Sound("game-over.wav");
    static Sound bgMusic = new Sound("background.wav");

    // think of the play area as a grid
    // each block on the grid is a square with side s
    // then width = s * columns and height = s * rows
    public final int width = 300; //10 columns
    public final int height = 600; //20 rows
    public static int topY, bottomY, leftX, rightX;
    public static int dropInterval = 50; //frames
    public static int highScore = 0;
    public static int score = 0;

    Tetromino currMino;
    final int startX, startY;

    Tetromino nextMino;
    final int nextX, nextY;

    public static ArrayList<Block> pile = new ArrayList<>();


    public GameManager() {
        bgMusic.setVolume(-5.0f);
        bgMusic.loop();
        leftX = (GamePanel.width/2) - (width/2);
        rightX = leftX+ width;
        topY = 50;
        bottomY = topY+height;

        startX= leftX+ (width/2) - Block.size;
        startY= topY + Block.size;

        nextX = rightX + 175;
        nextY = topY + 500;

        currMino = pickRandomTetromino();
        currMino.setXY(startX, startY);
        nextMino = pickRandomTetromino();
        nextMino.setXY(nextX, nextY);
    }

    public Tetromino pickRandomTetromino() {
        int i = new Random().nextInt(7);
        switch (i) {
            case 0: return new TetrominoI(); 
            case 1: return new TetrominoJ(); 
            case 2: return new TetrominoL(); 
            case 3: return new TetrominoO(); 
            case 4: return new TetrominoS();
            case 5: return new TetrominoT(); 
            case 6: return new TetrominoZ();
            default: return new TetrominoL(); 
        }
    }


    public void update() {
        if (state != GameState.PLAYING) return;
        if (currMino==null) return;

        // blocks start dropping faster as the score gets higher
        int factor = (int) (score/500);
        dropInterval = 50 - (int)(score/500) *5;
        if (dropInterval<=0) dropInterval = 7; //capped at drop per 10 frames

        if (!currMino.active) {
            for (int i=0; i<4; i++) pile.add(currMino.b[i]);

            //game over
            if (currMino.b[0].x == startX && currMino.b[0].y == startY) {
                bgMusic.stop();
                state = GameState.GAME_OVER;
                updateHighScore();
                gameOverSound.play();
                return;
            }

            currMino.deactivating= false;
            currMino = nextMino;
            currMino.setXY(startX, startY);
            nextMino = pickRandomTetromino();
            nextMino.setXY(nextX, nextY);
            checkRows();

        } else {
            currMino.update();
        }
    }

    public void checkRows() {
        int x= leftX;
        int y= topY;
        int blocks = 0;
        while (x<rightX && y < bottomY) {
            for (int i=0; i< pile.size(); i++) {
                if (pile.get(i).x == x && pile.get(i).y ==y) blocks++;
            }
            
            x+=Block.size;
            if (x==rightX) {
                if (blocks==10) {
                    rowClearSound.play();
                    score += 10;
                    for (int i=pile.size()-1 ; i >= 0; i--) {
                        if (pile.get(i).y == y) pile.remove(i);
                    }
                    for (int i=0; i<pile.size(); i++) {
                        if (pile.get(i).y < y) pile.get(i).y += Block.size;
                    }
                }
                blocks = 0;
                x= leftX;
                y+=Block.size;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.drawRect(leftX-2, topY-2, width+4, height+4);
        g2.drawRect(rightX +100, bottomY - 200, 200, 200);
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        g2.drawString("NEXT", rightX+165, bottomY-170);
        g2.drawString("Score: " + score, rightX+20, topY+30);
        g2.drawString("High Score: " + highScore, rightX+20, topY + 60);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.drawString("Use Arrows or WASD to play", leftX-250, topY+30);
        g2.drawString("Press X to drop block", leftX-250, topY+50);
        g2.drawString("Press SPACE to pause", leftX-250, topY+70);
        g2.drawString("Press R to restart", leftX-250, topY+90);

        if (currMino!=null) currMino.draw(g2);
        nextMino.draw(g2);
        for (Block b : pile)  b.draw(g2);

        if (state == GameState.WAITING) {
            g2.setFont(new Font("Arial", Font.BOLD, 30));
            g2.drawString("[SPACE] to Start", leftX+30, topY + height/2);
         } else if (state == GameState.PAUSED) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("PAUSED", leftX+70, topY + height/2);
        } else if (state == GameState.GAME_OVER) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("GAME OVER", leftX + 30, topY + height/2);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
        }
    }

    public void updateHighScore(){
        if (score>=highScore) {
            highScore = score;
        }
    }

    public void restart() {
        bgMusic.stop();
        pile.clear();
        score = 0;
        state = GameState.PLAYING;
        // Reset tetrominos
        currMino = null;
        nextMino = null;
        GamePanel.gameManager = new GameManager();
    }


}