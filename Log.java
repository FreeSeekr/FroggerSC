package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Log {
    private final int w;
    private final int h;
    private final int speed;
    private final Rectangle log;
    private BufferedImage image;

    public Log(int x, int y, int w, int h, int speed) {
        this.w = w;
        this.h = h;
        this.speed = speed;
        loadImage();
        log = new Rectangle(x, y, w, h);
    }

    private void loadImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/log.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, log.x, log.y, w, h, null);
        move();
        if (isOut()) {
            if (log.x >= Display.WIDTH / 2)
                log.x = -w;
            else if (log.x < Display.WIDTH / 2)
                log.x = Display.WIDTH;
        }
    }

    public boolean isOut() {
        return log.getMinX() >= Display.WIDTH || log.getMaxX() <= 0;
    }

    private void move() {
        log.x += speed;
    }

    public Rectangle getLog() {
        return log;
    }

    public int getSpeed() {
        return speed;
    }
}
