package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Player extends JPanel implements KeyListener {
    private final int x;
    private final int y;
    private int speed;
    private final Rectangle player;
    private BufferedImage image;

    Player(int x, int y, int w, int h, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        player = new Rectangle(x, y, w, h);
        loadPlayerImage("img/player.png");
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    public void setSpeed(int speedIn) {
        speed = speedIn;
    }
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        move();
        g2d.drawImage(image, player.x, player.y, null);
    }

    public void move() {
        player.x = player.x + speed;
    }

    public void loadPlayerImage(String player) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(player)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public boolean isGameRunning() {
        return Display.state == Display.STATE.GAME;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameRunning()) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_D) {
                if (player.getMaxX() + Display.GRID < Display.WIDTH) {
                    player.x = player.x + Display.GRID;
                    player.setLocation(player.x, player.y);
                }
            } else if (key == KeyEvent.VK_A) {
                if (player.getMaxX() - Display.GRID > 0) {
                    player.x = player.x - Display.GRID;
                    player.setLocation(player.x, player.y);
                }
            } else if (key == KeyEvent.VK_W) {
                speed = 0;
                if (player.getMaxY() - Display.GRID > 0)
                    player.y = player.y - Display.GRID;
                if (player.x % 50 == 0)
                    player.setLocation(player.x, player.y);
                else {
                    player.setLocation((player.x), player.y);
                }
            } else if (key == KeyEvent.VK_S) {
                speed = 0;
                if (player.getMaxY() + Display.GRID < Display.HEIGHT)
                    player.y = player.y + Display.GRID;
                if (player.x % 50 == 0)
                    player.setLocation(player.x, player.y);
                else {
                    player.setLocation((player.x), player.y);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
    public Rectangle getPlayerBounds() {
        return player;
    }
}
