package game;

import java.awt.*;

public class Tile {

    private int tileX, tileY, speedX, offset;
    private Rectangle top, bot;
    public Image tileImage;


    private Gorilla harambe = StartingClass.getHarambe();
    private Background bg = StartingClass.getBg1();

    public Tile(int x, int y, int offset) {
        tileX = x + offset;
        tileY = y;

        top = new Rectangle();
        bot = new Rectangle();
        tileImage = StartingClass.tiletop;

    }

    public Tile(int x, int y, boolean isLast) {
        tileX = x + offset;
        tileY = y;

        top = new Rectangle();
        bot = new Rectangle();
        tileImage = StartingClass.tiletop;

    }

    public Tile(Tile t) {
        tileX = t.getTileX() + 120;
        tileY = t.getTileY();

        top = new Rectangle();
        bot = new Rectangle();
        tileImage = StartingClass.tiletop;

    }

    public void update() {
        speedX = bg.getSpeedX() * 2;
        tileX += speedX;
        top.setBounds(tileX, tileY, 125, 10);
        bot.setBounds(tileX, tileY + 30, 120, 10);


        if (top.intersects(Gorilla.feet) && harambe.getSpeedY() > 0 && !harambe.isFallingThrough()) {
            feetTopCollision(harambe.feet);
            harambe.onGround = true;

        }

        if (harambe.isFallingThrough() && harambe.head.intersects(top)) {
            harambe.goingThrough = false;
        }

//        if (harambe.isFallingThrough() && top.intersects())
    }



    public void feetTopCollision(Rectangle rect) {

        harambe.setJumped(false);
        harambe.setSpeedY(0);
        harambe.setCenterY(this.tileY);

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

//    public void checkSideCollision(Rectangle rleft) {
//        if (type != 5 && type != 2 && type != 0) {
//            if (rleft.intersects(r)) {
//                System.out.println("side collision");
//                harambe.setCenterX(tileX);
//                harambe.setSpeedX(0);
//
//            }
//        }
//    }

//    public boolean touchingGround(Rectangle recta) {
//
//    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public int getSpeedX() {return speedX;}

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    public Rectangle getTop() {
        return top;
    }

    public void setTop(Rectangle top) {
        this.top = top;
    }
}