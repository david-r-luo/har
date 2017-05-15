package game;

import java.awt.*;

public class Banana {

    private int x, y, speedX;
    private Rectangle hitbox;
    public Image bananaImage;

    private Background bg = StartingClass.getBg1();
    private Gorilla harambe = StartingClass.getHarambe();

    public Banana (int x, int y) {
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(0, 0, 0, 0);

    }

    public void update() {
        speedX = bg.getSpeedX() * 2;
        x += speedX;

        hitbox.setBounds(x, y, 40, 40);

        if (hitbox.intersects(Gorilla.body)) {
            StartingClass.score += 50;
            StartingClass.bananaArray.remove(this);
            StartingClass.scoreStay = 20;
            StartingClass.scoreX = harambe.getCenterX();
            StartingClass.scoreY = harambe.getCenterY();
        }
    }

//    public void collisionCurr() {
//        if (top.intersects(Gorilla.feet) && type != 0) {
//            harambe.onGround = true;
//            System.out.println("ay");
//        } else {
//            harambe.currTouch = false;
//            System.out.println(harambe.currTouch);
//        }
//    }



    public void feetTopCollision(Rectangle rect) {

        StartingClass.score += 200;

    }

//    public void headBotCollision(Rectangle rect) {
//        harambe.setSpeedY(3);
//    }

//    public void checkVerticalCollision(Rectangle rbot){
//        if (rbot.intersects(top) && type == 8) {
//            harambe.setJumped(false);
//            harambe.setSpeedY(0);
//        }
//    }



    public int getTileX() {
        return x;
    }

    public void setTileX(int tileX) {
        this.x = tileX;
    }

    public int getTileY() {
        return y;
    }

    public void setTileY(int tileY) {
        this.y = tileY;
    }

    public Image getTileImage() {
        return bananaImage;
    }

    public void setTileImage(Image tileImage) {
        this.bananaImage = tileImage;
    }

    public Rectangle getBox() {
        return hitbox;
    }

    public void setBox(Rectangle box) {
        this.hitbox = box;
    }
}