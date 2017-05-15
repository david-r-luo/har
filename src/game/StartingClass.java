package game;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class StartingClass extends Applet implements Runnable, KeyListener {

    public final int STARTSPEED = -6;
    public static int globalSpeed;
    public static int time_passed = 0;
    public static int tileLineUp = 120;
    public int hiScore = 0;
    public static int tileTime;
    public static Tile first;
    public static Tile last;

    private static Gorilla harambe;
    public static int scoreX;
    public static int scoreY;
    public static Image image, currentSprite, har1, characterJumped, background, har2, har3, har4, har5, har6, dashHar, dashBlue, dashGrey, bananaImg, grass, pinkBabyImg, greyBabyImg,
    hearts4, hearts3, hearts2, hearts1, hearts0, currHeart;
    private Graphics second;
    private URL base;
    private static Background bg1, bg2, ferns1, ferns2, grass1, grass2;
    private Animation anim;
    private static Random random;
    private boolean waitInput;
    private static ArrayList<String> tileLines;
    private int[] easy = {70, 50, 50};
    private int[] medium = {55, 45, 45};
    private int[] hard = {50, 40, 40};
    private static int[] currDif;
    public static int renderCount = 2400;
//    private int[] tests = {100, 100, 100};
    public static int scoreStay = 0;
//    private int width;
    public static HashMap<Integer, Integer> tileScroll = new HashMap<>();

    public static int groundCount = 0;
    public static int score = 0;
    public static boolean upDown = false;


    public static Image tiletop, tiledirt;
    public static ArrayList<Tile> tileArray = new ArrayList<>();
    public static ArrayList<Banana> bananaArray = new ArrayList<>();
    public static ArrayList<Baby> babyArray = new ArrayList<>();
    public static ArrayList<Line> linesss = new ArrayList<>();

    @Override
    public void init() {
        tileScroll.put(-4, 378);
        tileScroll.put(-5, 284);
        tileScroll.put(-6, 224);
        tileScroll.put(-7, 179);
        tileScroll.put(-8, 153);
        currDif = hard;
        random = new Random();

        setSize(1960, 540);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("HaRUN!be");
        try {
            base = getDocumentBase();
        } catch (Exception e) {
            throw new NullPointerException();
        }

        // Image Setups
        har1 = getImage(base, "data/har1.png");
        har2 = getImage(base, "data/har2.png");
        har3 = getImage(base, "data/har3.png");
        har4 = getImage(base, "data/har4.png");
        har5 = getImage(base, "data/har5.png");
        har6 = getImage(base, "data/har6.png");
        characterJumped = getImage(base, "data/jumped.png");
        dashHar = getImage(base, "data/dash.png");
        hearts4 = getImage(base, "data/heart4.png");
        hearts3 = getImage(base, "data/heart3.png");
        hearts2 = getImage(base, "data/heart2.png");
        hearts1 = getImage(base, "data/heart1.png");
        hearts0 = getImage(base, "data/heart0.png");
        dashBlue = getImage(base, "data/dashblue.png");
        dashGrey = getImage(base, "data/dashgrey.png");

//        keeper1img = getImage(base, "data/keeper2.png");
//        keeper2img = getImage(base, "data/keeper2.png");
        background = getImage(base, "data/stolen2.png");
        grass = getImage(base, "data/grass.png");

        tiledirt = getImage(base, "data/tiledirt.png");
        bananaImg = getImage(base, "data/banana.png");
        pinkBabyImg = getImage(base, "data/pinkbaby.png");
        greyBabyImg = getImage(base, "data/greybaby.png");

        anim = new Animation();
        anim.addFrame(har1, 250);
        anim.addFrame(har2, 250);
        anim.addFrame(har3, 250);
        anim.addFrame(har4, 250);
        anim.addFrame(har5, 250);
        anim.addFrame(har6, 250);

        currentSprite = anim.getImage();



        tiletop = getImage(base, "data/tiletopnew.png");
    }

    @Override
    public void start() {
        Gorilla.health = 4;
        time_passed = 0;
        tileLineUp = 120;
        linesss.clear();


        globalSpeed = STARTSPEED;
        tileArray.clear();
        bananaArray.clear();
        babyArray.clear();
        score = 0;
        bg1 = new Background(0,0);
        bg2 = new Background(2693, 0);
        harambe = new Gorilla();
        ferns1 = new Background(0, 0);
        ferns2 = new Background(2693, 0);
//        grass1 = new Background(0, 0);
//        grass1.setSpeedX(globalSpeed*2);
//        grass2 = new Background(2693, 0);
//        grass2.setSpeedX(globalSpeed*2);
        ferns1.setSpeedX(globalSpeed - 2);
        ferns2.setSpeedX(globalSpeed - 2);

//        linesss.add(new Line(0));
//        linesss.add(new Line(120));
//        linesss.add(new Line(240));
//        linesss.add(new Line(360));
//        linesss.add(new Line(480));
//        linesss.add(new Line(600));
//        linesss.add(new Line(720));
//        linesss.add(new Line(840));

        try {
            loadMap("C:/Users/David/AppData/Local/Temp/data/testmap.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(this);
        thread.start();
    }


    private void loadMap(String filename) throws IOException{
        tileLines = new ArrayList();

        BufferedReader reader = new BufferedReader(new FileReader(filename));

        while (true) {
            String line = reader.readLine();

            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                tileLines.add(line);

            }


        }

        renderTiles(0);
//        renderTiles(10);

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
        tileLineUp = 120;
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
                    currentSprite = hearts2;
                    harambe.gameOver = true;
                }

                waitInput = true;
                tileLineUp += (globalSpeed * 2);
                if (tileLineUp <= 0) {
                    tileLineUp += 120;
                }

//                if (elapsed == 360 && globalSpeed > -7) {
//                    time_passed = 0;
//                    globalSpeed -= 1;
//                    updateBackgroundSpeed();
//                    System.out.println("SPEED: " + globalSpeed);
//                    tileTime = 0;
//                }


                if (tileTime % tileScroll.get(globalSpeed) == 0) {
                    renderTiles(2);
                    System.out.println("render");
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
            tileTime += 1;
            score += 1;

            renderCount -= globalSpeed;

            harambe.update();
            bg1.update();
            bg2.update();
            ferns1.update();
            ferns2.update();
//            grass1.update();
//            grass2.update();

            updateTiles();

            updateBananas();
            updateBabies();
            if (scoreStay > 0) {
                scoreStay -= 1;
            }


            if (harambe.gameOver) {
                if (score > hiScore) {
                    hiScore = score - 1;
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

    public static void renderTiles(int startIndex) {
        renderCount = 0;
        for (int j = 4; j < 11; j++) {
            String line = tileLines.get(j);
            for (int i = startIndex; i < startIndex + 20; i++) {

                if (i < line.length()) {

                    if (i < 10 && j == 10) {
                        char ch = line.charAt(i);
                        Tile t = new Tile(i, j, Character.getNumericValue(ch));
                        tileArray.add(t);
                    }
                    int tile1 = random.nextInt(100);
                    int tile2 = random.nextInt(100);
                    int tile3 = random.nextInt(100);

                    if (0 < currDif[0] && j == 10) {
                        char ch = line.charAt(i);
                        Tile t = new Tile(i, j, Character.getNumericValue(ch));
                        tileArray.add(t);
                        if (random.nextInt(100) > 60) {
                            Banana b = new Banana(i*120 + 45, j*40 - 40);
                            bananaArray.add(b);
                        } else if (random.nextInt(100) > 95) {
                            Baby bab;
                            if (random.nextInt(100) > 50) {
                                bab = new Baby(i*120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(i*120 + 45, j*40 - 35, "pink");
                            }
                            babyArray.add(bab);
                        }
                    } if (0 < currDif[1] && j == 7) {
                        char ch = line.charAt(i);
                        Tile t = new Tile(i, j, Character.getNumericValue(ch));
                        tileArray.add(t);
                        if (i == startIndex) {
                            first = t;
                        }

                        if (i == startIndex + 19) {
                            last = t;
                        }
                        if (random.nextInt(100) > 60) {
                            Banana b = new Banana(i*120 + 45, j*40 - 40);
                            bananaArray.add(b);
                        } else if (random.nextInt(100) > 95) {
                            Baby bab;
                            if (random.nextInt(100) > 50) {
                                bab = new Baby(i*120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(i*120 + 45, j*40 - 35, "pink");
                            }
                            babyArray.add(bab);
                        }
                    } if (0 < currDif[2] && j == 4) {
                        char ch = line.charAt(i);
                        Tile t = new Tile(i, j, Character.getNumericValue(ch));
                        tileArray.add(t);
                        if (random.nextInt(100) > 60) {
                            Banana b = new Banana(i*120 + 45, j*40 - 40);
                            bananaArray.add(b);
                        } else if (random.nextInt(100) > 95) {
                            Baby bab;
                            if (random.nextInt(100) > 50) {
                                bab = new Baby(i*120 + 45, j*40 - 35, "grey");
                            } else {
                                bab = new Baby(i*120 + 45, j*40 - 35, "pink");
                            }
                            babyArray.add(bab);
                        }
                    }

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

//        harambe.onGround = false;
        for (int i = 0; i < tileArray.size(); i++) {

            Tile t = tileArray.get(i);
            if (t.getTileX() <= -160) {
                tileArray.remove(t);
            } else {
                t.update();
                if (t == first && harambe.feet.intersects(t.getTop())) {
                    System.out.println(tileTime);
                } else if (t == last && harambe.feet.intersects(t.getTop())) {
                    System.out.println(tileTime);
                }
            }

        }
    }

//    public static void updateLines() {
//        for (int i = 0; i < linesss.size(); i++) {
//            Line l = linesss.get(i);
//            if (l.getX() == 0) {
//                l.x += 840;
//            } else {
//                l.update();
//            }
//
//        }
//    }

    private void updateBananas() {

        for (int i = 0; i < bananaArray.size(); i++) {

            Banana b = bananaArray.get(i);

            if (b.getTileX() <= -160) {
                bananaArray.remove(b);
            } else {
                b.update();
            }

        }
    }

    private void updateBabies() {

        for (int i = 0; i < babyArray.size(); i++) {

            Baby b = babyArray.get(i);

            if (b.getTileX() <= -160) {
                babyArray.remove(b);
            } else {
                b.update();
            }

        }
    }

    private void paintBananas(Graphics g) {
        for (int i = 0; i < bananaArray.size(); i++) {
            Banana b = bananaArray.get(i);
            if ((b.getTileX() > -100) && (b.getTileX() < 1000)) {
                g.drawImage(bananaImg, b.getTileX(), b.getTileY(), this);
            }

        }
    }

    private void paintBabies(Graphics g) {
        for (int i = 0; i < babyArray.size(); i++) {
            Baby b = babyArray.get(i);
            if ((b.getTileX() > -100) && (b.getTileX() < 1000)) {
                g.drawImage(b.babyImage, b.getTileX(), b.getTileY(), this);

            }

        }
    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tileArray.size(); i++) {
            Tile t = tileArray.get(i);
            if ((t.getTileX() > -100) && (t.getTileX() < 1000)) {
                g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
            }

        }
    }

//    public void paintLines(Graphics g) {
//        for (int i = 0; i < linesss.size(); i++) {
//            if (linesss.get(i).x == 0) {
//                linesss.get(i).x += 840;
//            }
//            g.drawLine(linesss.get(i).x, 0, linesss.get(i).x, 500);
//        }
//    }


    private void paintBananaScore(Graphics g) {
        g.drawString("+50", scoreX, scoreY - 20);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
//        g.drawImage(ferns, ferns1.getBgX(), ferns1.getBgY(), this);
//        g.drawImage(ferns, ferns2.getBgX(), ferns2.getBgY(), this);

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


        paintBananas(g);
        paintBabies(g);

        if (harambe.getDashCD() == 0) {
            g.drawImage(dashBlue, 10, 140, null);
        } else {
            g.drawImage(dashGrey, 10, 140, null);
        }



        g.setColor(Color.BLACK);
        g.drawImage(currHeart, 10, 80, null);


        if (harambe.gameOver) {
            g.setFont(new Font("AR Julian", Font.PLAIN, 60));
            g.drawString("GAME OVER", 300, 250);
            g.setFont(new Font("AR Julian", Font.ITALIC, 30));
            g.drawString("Press any key to continue", 290, 300);
            g.drawImage(hearts0, 10, 80, null);
        }

//        paintLines(g);



//        g.drawImage(grass, grass1.getBgX(), grass1.getBgY(), this);
//        g.drawImage(grass, grass2.getBgX(), grass2.getBgY(), this);
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
//			currentSprite = character;
//			harambe.setDucked(false);
                break;

            case KeyEvent.VK_LEFT:
//			harambe.stopLeft();
                break;

            case KeyEvent.VK_RIGHT:
//			harambe.stopRight();
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

    public static Background getBg2() {
        return bg2;
    }

    public static Gorilla getHarambe() {
        return harambe;
    }

    public static Background getFerns1() {
        return ferns1;
    }

    public static Background getFerns2() {
        return ferns2;
    }




}