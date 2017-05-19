package game;

import java.awt.*;

public abstract class Item {

    private int x, y, speedX;
    private Rectangle hitbox;
    public Image image;


    abstract void update();
    abstract int getX();
    abstract void setX(int x);
    abstract int getY();
    abstract void setY(int y);
    abstract Image  getImage();

}