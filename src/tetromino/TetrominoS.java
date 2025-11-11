package tetris.tetromino;
import java.awt.*;

public class TetrominoS extends Tetromino {
    public TetrominoS() {
        create(Color.green);
    }
    public void setXY(int x, int y) {
        //    0 1
        //  3 2
        b[0].setXY(x, y);
        b[1].setXY(x+Block.size, y);
        b[2].setXY(x, y+Block.size);
        b[3].setXY(x-Block.size, y+Block.size);


    }
}