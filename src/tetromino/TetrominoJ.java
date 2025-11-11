package tetris.tetromino;
import java.awt.*;

public class TetrominoJ extends Tetromino {
    public TetrominoJ() {
        create(Color.blue);
    }
    public void setXY(int x, int y) {
        //    1
        //    0
        //  3 2
        b[0].setXY(x, y);
        b[1].setXY(x, y-Block.size);
        b[2].setXY(x, y+Block.size);
        b[3].setXY(x-Block.size, y+Block.size);
    }
}