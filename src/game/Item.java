package game;

import java.awt.*;

public abstract class Item {

    abstract void update();
    abstract int getX();
    abstract int getY();
    abstract Image  getImage();

}