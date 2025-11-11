package tetris.tetromino;
import java.awt.*;

public class TetrominoO extends Tetromino {
    public TetrominoO() {
        create(Color.yellow);
    }
    public void setXY(int x, int y) {
        //  0 2
        //  1 3
        b[0].setXY(x, y);
        b[1].setXY(x, y+Block.size);
        b[2].setXY(x+Block.size, y);
        b[3].setXY(x+Block.size, y+Block.size);
    }
}