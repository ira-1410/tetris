package tetris.tetromino;
import java.awt.*;

public class TetrominoI extends Tetromino {
    public TetrominoI() {
        create(Color.CYAN);
    }
    public void setXY(int x, int y) {
        //   1 0 2 3

        b[0].setXY(x, y);
        b[1].setXY(x-Block.size, y);
        b[2].setXY(x+Block.size, y);
        b[3].setXY(x+2*Block.size, y);
    }
}