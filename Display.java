package mainGame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Display extends JPanel implements Runnable {
    public static int GRID = 50;
    public static int WIDTH = 1080;
    public static int HEIGHT = 1080;
    public enum STATE {
        GAME,
    }
    public static STATE state = STATE.GAME;

    private BufferedImage image;
    private final Player player;
    private final Cars[] Car1a; //path 1 top car
    private final Cars[] Car1b; //path 1 bottom car
    private final Cars[] Car2a; //path 2 top car
    private final Cars[] Car2b; //path 2 bottom car

    private final Log[] Log1a;
    private final Log[] Log1b;
    private final Log[] Log2a;
    private final Log[] Log2b;
    private final Log[] Log2c;
    private int deaths = 0;
    private int score = 0;

    public Display() {
        player = new Player(540, HEIGHT - 375, 50, 50, 0);
        Car1a = new Cars[2];
        Car1b = new Cars[3];
        Car2a = new Cars[2];
        Car2b = new Cars[2];
        Log1a = new Log[2];
        Log1b = new Log[2];
        Log2a = new Log[2];
        Log2b = new Log[2];
        Log2c = new Log[2];


        loadMap();
        initializeGame();
        start();
        this.addKeyListener(player);
        setFocusable(true);
    }

    private void initializeGame() {
        for (int i = 0; i < Car1a.length; i++)
        {
            Car1a[i] = new Cars(i * 290, HEIGHT - 825, 100, 40, 3);
        }
        for (int i = 0; i < Car1b.length; i++)
        {
            Car1b[i] = new Cars(i * 270, HEIGHT - 800, 100, 40, -2);
        }
        for (int i = 0; i < Car2a.length; i++)
        {
            Car2a[i] = new Cars(i * 290, HEIGHT - 675, 100, 40, 2);
        }
        for (int i = 0; i < Car2b.length; i++)
        {
            Car2b[i] = new Cars(i * 290, HEIGHT - 640, 100, 40, -2);
        }
        for (int i = 0; i < Log1a.length; i++)
        {
            Log1a[i] = new Log(i * 250, HEIGHT - 980, 170, 50, +2);
        }
        for (int i = 0; i < Log1b.length; i++)
        {
            Log1b[i] = new Log(i * 300, HEIGHT - 930, 170, 50, -2);
        }
        for (int i = 0; i < Log2a.length; i++)
        {
            Log2a[i] = new Log(i * 330, HEIGHT - 525, 170, 50, +2);
        }
        for (int i = 0; i < Log2b.length; i++)
        {
            Log2b[i] = new Log(i * 350, HEIGHT - 475, 170, 50, -2);
        }
        for (int i = 0; i < Log2c.length; i++)
        {
            Log2c[i] = new Log(i * 200, HEIGHT - 425, 170, 50, +2);
        }

    }

    private void didIntersectCar() {
        for (Cars car : Car1a) {
            if (player.getPlayerBounds().getBounds().intersects(car.getCarBounds().getBounds())) {
                reset();
            }
        }
        for (Cars car : Car1b) {
            if (player.getPlayerBounds().getBounds().intersects(car.getCarBounds().getBounds())) {
                reset();
            }
        }
        for (Cars car : Car2a) {
            if (player.getPlayerBounds().getBounds().intersects(car.getCarBounds().getBounds())) {
                reset();
            }
        }
        for (Cars car : Car2b) {
            if (player.getPlayerBounds().getBounds().intersects(car.getCarBounds().getBounds())) {
                reset();
            }
        }
    }

    private void isInsideLog() {
        Log[][] Logarray = new Log[][] { Log1a, Log1b, Log2a, Log2b, Log2c };
        int logIndex = -1;
        for (int i = 0; i < Logarray.length; i++) {
            for (int j = 0; j < Logarray[i].length; j++)
            {
                if ((player.getPlayerBounds().getCenterX() >= Logarray[i][j].getLog().getMinX()
                        && player.getPlayerBounds().getCenterX() <= Logarray[i][j].getLog().getMaxX())
                        && (player.getPlayerBounds().getCenterY() >= Logarray[i][j].getLog().getMinY()
                        && player.getPlayerBounds().getCenterY() <= Logarray[i][j].getLog().getMaxY()))
                {
                    logIndex = i;
                }
            }
        }

        if (logIndex > -1){
            player.setSpeed(Logarray[logIndex][1].getSpeed());
        }
        if ((logIndex == -1) && ((player.getPlayerBounds().getCenterY() >= WIDTH - 980 && player.getPlayerBounds().getCenterY() <= WIDTH - 880)
                || (player.getPlayerBounds().getCenterY() >= WIDTH - 525 && player.getPlayerBounds().getCenterY() <= WIDTH - 375))) {
            reset();
        }


    }

    private void loadMap() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/map.png")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void score() {
        if (player.getPlayerBounds().getCenterY() < HEIGHT - 970) {
            score++;
            deaths--;
            reset();
        }
    }

    private void showInfo(Graphics g) {
        Graphics2D graph2D = (Graphics2D) g;
        g.setColor(Color.BLACK);
        graph2D.setFont(new Font("Arial", Font.PLAIN, 18));
        graph2D.drawString("Deaths: " + deaths, 150, 20);
        graph2D.drawString("Score: " + score, 20, 20);
    }

    private void reset() {
        deaths++;
        player.getPlayerBounds().x = 250;
        player.getPlayerBounds().y = HEIGHT - 375;
    }

    private void renderGame(Graphics g) {
        g.drawImage(image, 0, 0, null);
        for (Log Log : Log1a)
            Log.render(g);
        for (Log Log : Log1b)
            Log.render(g);
        for (Log Log : Log2a)
            Log.render(g);
        for (Log Log : Log2b)
            Log.render(g);
        for (Log Log : Log2c)
            Log.render(g);
        player.render(g);
        for (Cars car : Car1a)
            car.renderCar(g);
        for (Cars car : Car1b)
            car.renderCar(g);
        for (Cars car : Car2a)
            car.renderCar(g);
        for (Cars car : Car2b)
            car.renderCar(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state == STATE.GAME)
        {
            renderGame(g);
            score();
            showInfo(g);
            didIntersectCar();
            isInsideLog();
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
