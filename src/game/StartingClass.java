package game;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class StartingClass extends Applet implements Runnable, KeyListener {

    public final int STARTSPEED = -6;
    public static int maxSpeed;
    public static int globalSpeed;
    public static int time_passed = 0;
    public int hiScore = 0;
    public static int tileTime;
    public static Tile last;
    public static int starterCount = 0;
    public static int babyStart1 = 0;
    public static int babyStart2 = 0;

    private static Gorilla harambe;
    public static int scoreX;
    public static int scoreY;
    public static Image image, currentSprite, har1, characterJumped, background, har2, har3, har4, har5, har6, dashHar, dashBlue, dashGrey, bananaImg, grass, pinkBabyImg, greyBabyImg,
    hearts4, hearts3, hearts2, hearts1, hearts0, currHeart;
    private Graphics second;
    private URL base;
    private static Background bg1, bg2, ferns1, ferns2;
    private Animation anim;
    private static Random random;
    private boolean waitInput;

    private int[] easy = {70, 50, 50};
    private int[] medium = {60, 45, 45};
    private int[] hard = {50, 40, 40};
    private static int[] currDif;
//    public static int renderCount = 2400;
    public static int scoreStay = 0;

    public static int score = 0;
    public static boolean upDown = false;


    public static Image tiletop, tiledirt;
    public static ArrayList<Tile> tileArray = new ArrayList<>();
    public static ArrayList<Item> itemArray = new ArrayList<>();

    @Override
    public void init() {
        currDif = easy;
        if (currDif[0] == 70) {
            maxSpeed = -7;
        } else if (currDif[0] == 60) {
            maxSpeed = -8;
        } else if (currDif[0] == 50) {
            maxSpeed = -9;
        }
        random = new Random();

        setSize(1080, 540);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("HaRUN!be");
        try {
            base = this.getClass().getResource("/resources/banana.png");
        } catch (Exception e) {
            throw new NullPointerException();
        }


        // Image Setups

        har1 = getImage(base, "har1.png");
        har2 = getImage(base, "har2.png");
        har3 = getImage(base, "har3.png");
        har4 = getImage(base, "har4.png");
        har5 = getImage(base, "har5.png");
        har6 = getImage(base, "har6.png");
        characterJumped = getImage(base, "jumped.png");
        dashHar = getImage(base, "dash.png");
        hearts4 = getImage(base, "heart4.png");
        hearts3 = getImage(base, "heart3.png");
        hearts2 = getImage(base, "heart2.png");
        hearts1 = getImage(base, "heart1.png");
        hearts0 = getImage(base, "heart0.png");
        dashBlue = getImage(base, "dashb.png");
        dashGrey = getImage(base, "dashg.png");

        background = getImage(base, "stolen2.png");
        grass = getImage(base, "grass.png");

        tiledirt = getImage(base, "tiledirt.png");
        bananaImg = getImage(base, "banana.png");
        pinkBabyImg = getImage(base, "pinkbaby.png");
        greyBabyImg = getImage(base, "greybaby.png");

        anim = new Animation();
        anim.addFrame(har1, 250);
        anim.addFrame(har2, 250);
        anim.addFrame(har3, 250);
        anim.addFrame(har4, 250);
        anim.addFrame(har5, 250);
        anim.addFrame(har6, 250);

        currentSprite = anim.getImage();

        tiletop = getImage(base, "tiletopnew.png");
    }

    @Override
    public void start() {
        Gorilla.health = 4;
        time_passed = 0;
        globalSpeed = STARTSPEED;
        tileArray.clear();
        itemArray.clear();
        score = 0;
        starterCount = 0;
        bg1 = new Background(0,0);
        bg2 = new Background(2693, 0);
        harambe = new Gorilla();
        ferns1 = new Background(0, 0);
        ferns2 = new Background(2693, 0);
        Tile placeHolder = new Tile(0, 160, 0);
        renderTiles(placeHolder);
        ferns1.setSpeedX(globalSpeed - 2);
        ferns2.setSpeedX(globalSpeed - 2);

        Thread thread = new Thread(this);
        thread.start();
    }



    @Override
    public void stop() {

    }

    public void restart() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(waitInput) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void run() {
        time_passed = 0;
        tileTime = 0;
        double previous = System.currentTimeMillis();
        double lag = 0;
        while (true) {
            double current = System.currentTimeMillis();
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while(lag >= 17) {

                if (harambe.getHealth() == 4) {
                    currHeart = hearts4;
                } else if (harambe.getHealth() == 3) {
                    currHeart = hearts3;
                } else if (harambe.getHealth() == 2) {
                    currHeart = hearts2;
                } else if (harambe.getHealth() == 1) {
                    currHeart = hearts1;
                } else if (harambe.getHealth() <= 0) {
                    currHeart = hearts0;
//                    currentSprite = hearts2;
                    harambe.gameOver = true;
                }

                waitInput = true;

                if (time_passed >= 720 && globalSpeed > maxSpeed) {
                    time_passed = 0;
                    globalSpeed -= 1;
                    updateBackgroundSpeed();
                    System.out.println("SPEED: " + globalSpeed);
                }


                if (last.getX() < 960) {
                    renderTiles(last);
                    last = tileArray.get(tileArray.size() - 1);
                }

                if (upDown) {
                    harambe.jump();
                }

                if (harambe.dashing) {
                    currentSprite = dashHar;
                } else if (harambe.isJumped()) {
                    currentSprite = characterJumped;
                } else if (!harambe.isJumped()) {
                    currentSprite = anim.getImage();
                }

                lag -= 17;


            }

            time_passed += 1;
            score += 1;

//            renderCount -= globalSpeed;

            harambe.update();
            bg1.update();
            bg2.update();
            ferns1.update();
            ferns2.update();
            updateTiles();
            updateItems();
            if (scoreStay > 0) {
                scoreStay -= 1;
            }


            if (harambe.gameOver) {
                if (score > hiScore) {
                    hiScore = score;
                }
                restart();
                start();
                break;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();
            animate();
        }

    }

    public static void renderTiles(Tile starting) {
//        renderCount = 0;
        int curr10 = starting.getX();
        int curr7 = starting.getX();
        int curr4 = starting.getX();

        last = starting;

        for (int j = 4; j < 11; j+=3) {
            for (int i = 0; i < 20; i++) {
                if (j == 10) {
                    if (starterCount < 5) {
                        Tile t = new Tile(curr10 + 120, j * 40, 0);
                        tileArray.add(t);
                        if (t.getX() > last.getX()) {
                            last = t;
                        }
                        starterCount += 1;
                    } else if (random.nextInt(100) < currDif[1]) {
                        Tile t = new Tile(curr10 + 120, j * 40, 0);
                        tileArray.add(t);
                        if (t.getX() > last.getX()) {
                            last = t;
                        }
                        int temp = random.nextInt(100);
                        if (temp > 6) {
                            Banana b = new Banana(curr10 + 120 + 45, j*40 - 40);
                            itemArray.add(b);
                        } else {
                            Baby bab;
                            if (temp > 3) {
                                bab = new Baby(curr10 + 120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(curr10 + 120 + 45, j*40 - 35, "pink");
                            }
                            itemArray.add(bab);

                        }
                    }

//                    if (random.nextInt(100) > 60) {
//                        Banana b = new Banana(curr10 + 120 + 45, j*40 - 40);
//                        itemArray.add(b);
//                    }



                    curr10 += 120;
                } else if (j == 7) {
                    if (random.nextInt(100) < currDif[1]) {
                        Tile t = new Tile(curr7 + 120, j * 40, 0);
                        tileArray.add(t);
                        if (t.getX() > last.getX()) {
                            last = t;
                        }
                        int temp = random.nextInt(100);
                        if (temp > 6) {
                            Banana b = new Banana(curr7 + 120 + 45, j*40 - 40);
                            itemArray.add(b);
                        } else if (babyStart1 > 10){
                            Baby bab;
                            if (temp > 3) {
                                bab = new Baby(curr7 + 120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(curr7 + 120 + 45, j*40 - 35, "pink");
                            }
                            itemArray.add(bab);

                        } else {
                            babyStart1 += 1;
                        }
                    }
//
//                    if (random.nextInt(100) > 60) {
//                        Banana b = new Banana(curr7 + 120 + 45, j*40 - 40);
//                        itemArray.add(b);
//                    }
                    curr7 += 120;
                } else if (j == 4) {
                    if (random.nextInt(100) < currDif[2]) {
                        Tile t = new Tile(curr4 + 120, j * 40, 0);
                        tileArray.add(t);
                        if (t.getX() > last.getX()) {
                            last = t;
                        }
                        int temp = random.nextInt(100);
                        if (temp > 6) {
                            Banana b = new Banana(curr4 + 120 + 45, j*40 - 40);
                            itemArray.add(b);
                        } else if (babyStart2 > 10){
                            Baby bab;
                            if (temp > 3) {
                                bab = new Baby(curr4 + 120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(curr4 + 120 + 45, j*40 - 35, "pink");
                            }
                            itemArray.add(bab);

                        } else {
                            babyStart2 += 1;
                        }
                    }
//
//                    if (random.nextInt(100) > 60) {
//                        Banana b = new Banana(curr4 + 120 + 45, j*40 - 40);
//                        itemArray.add(b);
//                    }
                    curr4 += 120;
                }
            }
        }
    }


    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        g.drawImage(image, 0, 0, this);

    }

    public static void updateTiles() {

        for (int i = 0; i < tileArray.size(); i++) {

            Tile t = tileArray.get(i);
            if (t.getX() <= -160) {
                tileArray.remove(t);
            } else {
                t.update();
            }

        }
    }

    private void updateItems() {

        for (int i = 0; i < itemArray.size(); i++) {

            Item b = itemArray.get(i);

            if (b.getX() <= -160) {
                itemArray.remove(b);
            } else {
                b.update();
            }

        }
    }

    private void paintItems(Graphics g) {
        for (int i = 0; i < itemArray.size(); i++) {
            Item b = itemArray.get(i);
            if ((b.getX() > -100) && (b.getX() < 1000)) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);

            }

        }
    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = tileArray.get(i);
            if ((t.getX() > -100) && (t.getX() < 1100)) {
                g.drawImage(t.getTileImage(), t.getX(), t.getY(), this);
            }

        }
    }


    private void paintBananaScore(Graphics g) {
        g.drawString("+50", scoreX, scoreY - 20);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

        paintTiles(g);

        if (currentSprite.equals(dashHar)) {
            g.drawImage(currentSprite, harambe.getCenterX()- 136, harambe.getCenterY() - 63, this);
        } else {
            g.drawImage(currentSprite, harambe.getCenterX() - 61, harambe.getCenterY() - 63, this);
        }

        g.setFont(new Font("AR Julian", Font.PLAIN, 60));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + Integer.toString(score), 20, 50);
        g.setFont(new Font("AR Julian", Font.PLAIN, 20));

        if (score > hiScore) {
            g.drawString("High Score: " + Integer.toString(score), 20, 70);
        } else {
            g.drawString("High Score: " + Integer.toString(hiScore), 20, 70);
        }

        g.setColor(Color.YELLOW);
        if (scoreStay > 0) {
            paintBananaScore(g);
        }

        paintItems(g);

        if (harambe.getDashCD() == 0) {
            g.drawImage(dashBlue, 20, 130, null);
        } else {
            g.drawImage(dashGrey, 20, 130, null);
        }



        g.setColor(Color.BLACK);



        if (harambe.gameOver) {
            g.setFont(new Font("AR Julian", Font.PLAIN, 60));
            g.drawString("GAME OVER", 300, 250);
            g.setFont(new Font("AR Julian", Font.ITALIC, 30));
            g.drawString("Press any key to continue", 290, 300);
            g.drawImage(hearts0, 20, 80, null);
        } else {
            g.drawImage(currHeart, 20, 80, null);
        }

        g.drawImage(null, 90, 80, null);
    }

    public void animate() {
        anim.update(50);
    }

    public static void updateBackgroundSpeed() {
        bg1.setSpeedX(globalSpeed + 1);
        bg2.setSpeedX(globalSpeed + 1);
        ferns1.setSpeedX(globalSpeed - 2);
        ferns2.setSpeedX(globalSpeed - 2);

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (harambe.gameOver) {
                    waitInput = false;
                } else {
                    upDown = true;
                }
                break;
            case KeyEvent.VK_SPACE:
                if (harambe.gameOver) {
                    waitInput = false;
                } else {
                    harambe.dash();
                }
                break;

            case KeyEvent.VK_Z:
                if (harambe.gameOver) {
                    waitInput = false;
                } else {
                    harambe.dash();
                }
                break;

            case KeyEvent.VK_DOWN :
                if (harambe.gameOver) {
                    waitInput = false;
                } else {
                    harambe.fallThroughGround();
                }
                break;

            default:
                if (harambe.gameOver) {
                    waitInput = false;
                }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                upDown = false;
                break;

            case KeyEvent.VK_DOWN:
                break;

            case KeyEvent.VK_LEFT:
                break;

            case KeyEvent.VK_RIGHT:
                break;

            case KeyEvent.VK_SPACE:
                break;

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public static Background getBg1() {
        return bg1;
    }

    public static Gorilla getHarambe() {
        return harambe;
    }

    public static Background getFerns1() {
        return ferns1;
    }





}