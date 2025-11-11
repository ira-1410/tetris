package tetris.tetromino;
import tetris.GameManager;
import tetris.KeyEventHandler;

import java.awt.*;

public class Tetromino {
    public Block b[] = new Block[4];
    public Block temp[] = new Block[4];
    public int autoDropCounter = 0;
    public boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    public int deactivateCounter=0;

    public void setXY(int x, int y) {} //implemented in specific tetromino classes

    public void create(Color c) {
        //initialize arrays with Blocks
        // a temp array is used for rotation
        for (int i=0; i<4; i++) {
            b[i] = new Block(c);
            temp[i] = new Block(c);
        }
    }
    
    public void updateXY() {
        checkRotationCollision();
        // update block position if no collision is detected
        if (!leftCollision && !rightCollision && !bottomCollision) {
            // transfer values from temp to actual block array
            for (int i=0; i<4; i++) b[i].setXY(temp[i].x, temp[i].y);
        }
    }

    public void rotateClockwise() {
        //current x and y reference the top left of the block 
        // but rotation math is done around center

        int px = b[0].x + Block.size/2;
        int py = b[0].y + Block.size/2;

        for (int i = 0; i < 4; i++) {
            // center of current block
            int cx = b[i].x + Block.size/2;
            int cy = b[i].y + Block.size/2;

            // rotate 90 deg (x, y) to (y, -x)
            int newCX = px- (cy - py);
            int newCY = py+ (cx - px);

            temp[i].setXY(newCX - Block.size/2, newCY - Block.size/2);
        } updateXY();
    }
    
    public void update() {
        // if the block is on the floor
        if (deactivating) deactivate();

        //update collision flags
        checkMovementCollision();
        
        //user presses down key
        if (KeyEventHandler.downPressed) {
            // drop the block one level and reset flag
            if (!bottomCollision) {
                for (int i=0; i<4; i++) b[i].y += Block.size;
                GameManager.score++;
                autoDropCounter=0;
            } KeyEventHandler.downPressed = false;
        }

        //user presses X to drop
        if (KeyEventHandler.dropPressed) {
            if (!bottomCollision) {
                //loop drop action until it reaches the bottom
                while (!bottomCollision) {
                    for (int i=0; i<4; i++) b[i].y += Block.size;
                    GameManager.score++;
                    checkMovementCollision();
                } autoDropCounter=0;
            } KeyEventHandler.dropPressed = false;
        }

        // user presses left or right - check collision and move
        if (KeyEventHandler.leftPressed) {
            if (!leftCollision) {
                for (int i=0; i<4; i++) b[i].x -= Block.size;
            } KeyEventHandler.leftPressed = false;
        }
        if (KeyEventHandler.rightPressed) {
            if (!rightCollision) {
                for (int i=0; i<4; i++) b[i].x += Block.size;
            } KeyEventHandler.rightPressed = false;
        }

        //user presses up key
        if (KeyEventHandler.upPressed) {
            rotateClockwise();
            KeyEventHandler.upPressed = false;
        }

        if (bottomCollision) {
            deactivating = true;
        } else {
            //increment autoDropCounter every frame, drop block when it reaches dropInterval
            autoDropCounter++;
            if (autoDropCounter==GameManager.dropInterval) {
                // drop block by one level & reset counter
                for (int i=0; i<4; i++) { b[i].y += Block.size; }
                autoDropCounter=0;
            }
        } 
    }

    // user should be able to slide the block briefly when its on the floor
    // when the block reaches the floor, its still active for 30 frames
    public void deactivate() {
        deactivateCounter++;
        if (deactivateCounter==30) {
            deactivateCounter = 0;
            checkMovementCollision();
            if (bottomCollision) active = false;
        }
    }

    //check and set collision flags, called before movement (down,left,right)
    public void checkMovementCollision() {
        leftCollision = false; 
        rightCollision = false;
        bottomCollision = false;

        checkPileCollision();

        for (int i=0; i<4; i++) {
            if (b[i].x == GameManager.leftX) leftCollision=true; 
            if (b[i].x + Block.size == GameManager.rightX) rightCollision =true;
            if (b[i].y + Block.size == GameManager.bottomY) bottomCollision =true;
        }
    }
    //check and set collision flags before rotation
    public void checkRotationCollision() {
        leftCollision = false; 
        rightCollision = false;
        bottomCollision = false;

        for (int i=0; i<4; i++) {
            if (temp[i].x < GameManager.leftX) leftCollision=true; 
            if (temp[i].x + Block.size > GameManager.rightX) rightCollision =true;
            if (temp[i].y + Block.size > GameManager.bottomY) bottomCollision =true;
        }
    }

    // check collision with the pile of previous blocks 
    public void checkPileCollision() {
        for (int i=0; i<GameManager.pile.size(); i++) {
            int x = GameManager.pile.get(i).x;
            int y = GameManager.pile.get(i).y;

            for (int j=0; j<4; j++) {
                if (b[j].y + Block.size == y && b[j].x == x) bottomCollision= true;
                if (b[j].x - Block.size == x && b[j].y == y) leftCollision= true;
                if (b[j].x + Block.size == x && b[j].y == y) rightCollision= true;
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(b[0].c);
        for (int i=0; i<4; i++) {
            g.fillRect(b[i].x, b[i].y, Block.size, Block.size);
        }
    }
}