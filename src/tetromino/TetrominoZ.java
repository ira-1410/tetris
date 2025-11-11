package tetris.tetromino;
import java.awt.*;

public class TetrominoZ extends Tetromino {
    public TetrominoZ() {
        create(Color.red);
    }
    public void setXY(int x, int y) {
        //  1 0
        //    2 3
        b[0].setXY(x, y);
        b[1].setXY(x-Block.size, y);
        b[2].setXY(x, y+Block.size);
        b[3].setXY(x+Block.size, y+Block.size);
    }
}