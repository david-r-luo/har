package game;

import java.awt.*;

public class Banana extends Item {

    private int x, y, speedX;
    private Rectangle hitbox;
    public Image bananaImage;

    private Background bg = StartingClass.getBg1();
    private Gorilla harambe = StartingClass.getHarambe();

    public Banana (int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(0, 0, 0, 0);
        bananaImage = StartingClass.bananaImg;

    }

    public void update() {
        speedX = bg.getSpeedX() * 2;
        x += speedX;

        hitbox.setBounds(x, y, 40, 40);

        if (hitbox.intersects(Gorilla.body)) {
            StartingClass.score += 50;
            StartingClass.itemArray.remove(this);
            StartingClass.scoreStay = 20;
            StartingClass.scoreX = harambe.getCenterX();
            StartingClass.scoreY = harambe.getCenterY();
        }
    }



    public int getX() {
        return x;
    }

    public void setX(int tileX) {
        this.x = tileX;
    }

    public int getY() {
        return y;
    }

    public void setY(int tileY) {
        this.y = tileY;
    }

    public Image getImage() {
        return bananaImage;
    }

}