/**
 * Created by David on 9/11/2016.
 */

package game;

public class Enemy {
    int speedX, centerX, centerY;
    private Background ferns1 = StartingClass.getFerns1();

    public void update() {
        speedX = ferns1.getSpeedX();
        centerX += speedX;
    }

    public void attack() {

    }

    public void die() {

    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

}
