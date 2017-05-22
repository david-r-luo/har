package game;

import java.awt.*;

public class Gorilla {

    // Constants are Here
    final int JUMPSPEED = -15;

    private int centerX = 150;
    private int centerY = 0;
    private int dashCD = 0;
    public static int health = 4;
    private boolean inAir = false;
    public boolean gameOver = false;
    public boolean onGround = true;
    public boolean falling = false;
    public boolean goingThrough = false;

    public boolean dashing = false;

//    public boolean currTouch = true;
//    public boolean nextTouch = true;

    private int speedY = 0;
    private int speedX = 0;

    public static Rectangle feet = new Rectangle(0, 0, 0, 0);
    public static Rectangle body = new Rectangle(0, 0, 0, 0);
    public static Rectangle head = new Rectangle(0, 0, 0, 0);
    public static Rectangle rectCurr = new Rectangle(0, 0, 0, 0);
    public static Rectangle rectNext = new Rectangle(0, 0, 0, 0);


    public void update() {
        // Moves Character or Scrolls Background accordingly.
        if (dashCD > 0) {
            dashCD -= 1;
        }

        centerY += speedY;
        centerX += speedX;
        if (centerX > 150) {
            centerX -= 1;
        }
        if (speedX == 0 && dashing) {
            dashing = false;
        }

        if (inAir) {
            speedY += 1;
        }

        if (falling) {
            fall();
        }

        if (!falling && !inAir) {
            speedY += 1;
        }

        if (dashing) {
            speedX -= 1;
            speedY = 0;
        }

//



        feet.setRect(centerX - 14, centerY - 23, 5, 30);
        body.setRect(centerX - 54, centerY - 54, 50, 50);
        head.setRect(centerX - 54, centerY - 54, 50, 10);
        rectCurr.setRect(centerX - 74, centerY - 4, 10, 10);
        rectNext.setRect(centerX + 4, centerY - 4, 10, 10);


        if (centerY >= 600 || health <= 0) {
            System.out.println("GAME OVER MAN");
            gameOver = true;
            StartingClass.last = null;
        }

    }


    public void jump() {
        if (inAir == false && speedY == 0) {
            speedY = JUMPSPEED;
            inAir = true;
        }

    }

    public void fall() {
        speedY = 5;
        falling = false;
    }

    public void dash() {
        if (dashCD == 0) {
            speedY = 0;
            speedX = 15;
            dashing = true;
            dashCD = 100;
        }


    }

    public void fallThroughGround() {
        if (onGround && !inAir) {
            goingThrough = true;
            speedY = 5;
            falling = true;
        }

        onGround = false;
        inAir = true;
//        feet.setBounds(1, 1, 1, 1);
//        rectCurr.setRect(1, 1, 1, 1);
//        rectNext.setRect(1, 1, 1, 1);
//        falling = true;
//        inAir = true;
//        feet.setRect(centerX - 14, centerY - 23, 5, 30);
//        rectCurr.setRect(centerX - 74, centerY - 4, 10, 10);
//        rectNext.setRect(centerX + 4, centerY - 4, 10, 10);
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public boolean isJumped() {
        return inAir;
    }


    public int getSpeedY() {
        return speedY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public void setJumped(boolean inAir) {
        this.inAir = inAir;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getDashCD() {
        return dashCD;
    }

    public boolean isFallingThrough() {
        return goingThrough;
    }

    public int getHealth() {
        return health;
    }

}
