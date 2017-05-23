package game;

import java.awt.*;

public class Baby extends Item {

    private int x, y, speedX;
    private Rectangle hitbox;
    public Image babyImage;

    private Background bg = StartingClass.getBg1();
    private Gorilla harambe = StartingClass.getHarambe();

    public Baby (int x, int y, String s) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(0, 0, 0, 0);
        if (s.equals("grey")) {
            babyImage = StartingClass.greyBabyImg;
        } else {
            babyImage = StartingClass.pinkBabyImg;
        }

    }

    public void update() {
        speedX = bg.getSpeedX() * 2;
        x += speedX;

        hitbox.setBounds(x, y, 40, 40);

        if (hitbox.intersects(harambe.body)) {
//            StartingClass.globalSpeed += 1;
//            StartingClass.updateBackgroundSpeed();
//            StartingClass.updateTiles();


            StartingClass.itemArray.remove(this);
            if (!harambe.dashing) {
                Gorilla.health -= 1;
                System.out.println("LOSE LIFE");
            }

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
        return babyImage;
    }

    public void setTileImage(Image tileImage) {
        this.babyImage = tileImage;
    }

    public Rectangle getBox() {
        return hitbox;
    }

    public void setBox(Rectangle box) {
        this.hitbox = box;
    }
}