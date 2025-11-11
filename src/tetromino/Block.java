package tetris.tetromino;

import java.awt.*;

public class Block extends Rectangle {
    public static final int size = 30;
    public int x, y;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g) {
        g.setColor(c);
        g.fillRect(x,y, size, size);
    }

}