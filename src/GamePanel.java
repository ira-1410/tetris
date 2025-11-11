package tetris;
import tetris.tetromino.*;
import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int width = 1000;
    public static final int height = 720;
    public static final int fps = 48;
    Thread gameThread;
    static GameManager gameManager = new GameManager();

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyEventHandler());
        this.setFocusable(true);
    }

    public void init() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double interval = 1000_000_000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread!=null) {
            currentTime = System.nanoTime();
            delta+= (currentTime - lastTime) / interval;
            lastTime = currentTime;
            if (delta>=1) {
                update();
                repaint();
                delta-=1;
            }
        }

    }
    public void update() {
        gameManager.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        gameManager.draw(g2);
    }

}