package mainGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;

public class Cars {
    private final Rectangle carBounds;
    private final int carSpeed;
    private BufferedImage carImage;

    public Cars(int x, int y, int width, int height, int speed) {
        carSpeed = speed;
        setCarType();
        carBounds = new Rectangle(x, y, width, height);
    }

    private void setCarType() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(2);
        String car;

        if (carSpeed > 0) {
            if (randomNumber == 1)
                car = "img/green_car_right.png";
            else
                car = "img/pink_car_right.png";
        } else {
            if (randomNumber == 1)
                car = "img/pink_car_left.png";
            else
                car = "img/green_car_left.png";
        }

        changeCar(car);
    }

    private void changeCar(String car) {
        try {
            carImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(car)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderCar(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(carImage, carBounds.x, carBounds.y, null);
        moveCar();
        if (isCarOutOfBounds()) {
            if (carBounds.x > 500)
                carBounds.x = -100;
            else if (carBounds.x < 500)
                carBounds.x = Display.WIDTH;
        }
    }

    private void moveCar()
    {
        carBounds.x += carSpeed;
    }

    public boolean isCarOutOfBounds()
    {
        return carBounds.getMinX() >= Display.WIDTH || carBounds.getMaxX() <= 0;
    }

    public Rectangle getCarBounds()
    {
        return carBounds;
    }
}
