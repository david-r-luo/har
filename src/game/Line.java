package game;

/**
 * Created by David on 2/3/2017.
 */
public class Line {

    public int x, speedX;

    private Background bg = StartingClass.getBg1();
    private Gorilla harambe = StartingClass.getHarambe();

    public Line (int x) {
        this.x = x;
        speedX = StartingClass.globalSpeed + 1;

    }

    public void update() {
        speedX = bg.getSpeedX() * 2;
        x += speedX;
    }

    public int getX() {
        return x;
    }


}
