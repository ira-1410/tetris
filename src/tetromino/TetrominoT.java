package tetris.tetromino;
import java.awt.*;

public class TetrominoT extends Tetromino {
    public TetrominoT() {
        create(Color.MAGENTA);
    }
    public void setXY(int x, int y) {
        //   1 0 2
        //     3
        b[0].setXY(x, y);
        b[1].setXY(x, y+Block.size);
        b[2].setXY(x- Block.size, y);
        b[3].setXY(x+Block.size, y);
    }
}