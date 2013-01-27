import org.newdawn.slick.*;
import org.newdawn.slick.util.DefaultLogSystem;

import java.util.LinkedList;
import java.util.List;

public class Blowjob extends BasicGame {
    private static final int MAX_FRAME_RATE = 60;
    private static final int MIN_FRAME_RATE = 10;
    private static final int ALLOCATED_TIME = 1000000;
    private static final int mapStartLocation = 600;
            //3 * 60 * 1000 + 6000;
    private static final int BEAT_SIZE = 50;

    //HYVÄT KOMMENTIT TEILLÄ!!

    private Resources resources;
    private Player player;
    private Heart heart;
    private Level level;
    private Line currentLine;
    private Input input;
    private Image cutsOverlay;
    private List<Cut> pendingCuts;
    private boolean[] desiredButtonStates;

    private double mistakeSpeed = 3.0;
    private int gameState = 1;

    public List<String> consoleText;
    public StringBuffer stringBufferC;
    public StringBuffer stringBufferUserInput;
    public String[] consoleRows;
    public int consoleRowCurrent;

    public boolean showMap = false;
    public boolean lowerMap = false;
    public int mapX;
    public int mapY;
    public int mapSpeed;

    public Image hand;
    public Blowjob() throws SlickException {
        super("Blowjob");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        resources = new Resources();
        input = gc.getInput();
        level = new Level(ALLOCATED_TIME, resources);

        cutsOverlay = Image.createOffscreenImage(gc.getWidth(), gc.getHeight());
        final Graphics cutsG = cutsOverlay.getGraphics();
        cutsG.setBackground(Color.transparent);
        cutsG.clear();
        cutsG.setColor(new Color(0, 255, 0));
        desiredButtonStates = new boolean[]{true,true,true,true,true,true,true,true,true,true,true,false};
        stringBufferC = new StringBuffer("C:\\>");
        stringBufferUserInput = new StringBuffer("");
        consoleRows = new String[8];
        consoleRows[0] = "C:\\>";
        heart = new Heart(getMinimumFrameTime(), LueLiikerata.read(), 0.0, BEAT_SIZE);
        player = new Player(heart);
        hand = resources.handResting;
        mapY = 600;

        mapSpeed = 10;
        player.addBeatListener(new Heart.BeatListener() {
            public double phase() {
                return 0;
            }

            public void onBeat(final double phase) {
                resources.beat1.play(1.0f, 0.5f);
            }
        });
        player.addBeatListener(new Heart.BeatListener() {
            public double phase() {
                return 0.4;
            }

            public void onBeat(final double phase) {
                resources.beat2.play(1.0f, 0.5f);
            }
        });
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if(gameState == 1) {
        if(level.getClockRunning() == false) gameState = 2;
        }
        if(gameState == 1) {
        level.update(delta);
        player.setPositionAndUpdate(input, delta);

            if(showMap == true && mapY >= 0) {
                System.out.println("PÄÄSTIIN TÄNNE");

                mapY = mapY - mapSpeed;
            }
            if( lowerMap = true) {
               // mapY = mapY + mapSpeed;
            }

        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
        }
        if (pendingCuts != null) {
            applyCuts(pendingCuts);
            pendingCuts = null;
        }


        }

        if(gameState == 2) {
            gameEnded();
            gameState = 3;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if(gameState == 1 ) {
        g.drawImage(level.background, 0, 0);
        g.drawImage(level.wireOverlay, 0, 0);
        g.drawImage(level.buttonOverlay, 0, 0);
        g.drawImage(cutsOverlay, 0, 0);
        level.clock.draw(g, 50, 55);
        g.setColor(new Color(255, 255, 255));
            g.setColor(Color.green);
            drawConsole(g);
        //g.drawString("sakset", player.getDisturbedPosition().x, player.getDisturbedPosition().y);
            g.drawImage(hand, player.getDisturbedPosition().x -187, player.getDisturbedPosition().y -258);
            //g.drawImage(resources.hand, 0, 0, 0 , ,0 ,10);
        //g.drawString(stringBufferC.toString(), 412, 50);


        g.setColor(new Color(255, 255, 255));
        if (currentLine != null) {
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
        if(true) {
            g.drawImage(resources.instructions,mapX,mapY);
        }
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(button == 0) {
            hand = resources.hand;
        currentLine = new Line(player.getDisturbedPosition(), player.getDisturbedPosition());
        final Position p = player.getDisturbedPosition();
        int index = -1;
        for(Rectangle rectangle: level.buttonHitboxes) {
            index++;
            if(p.x >= rectangle.x && p.x <= rectangle.endX && p.y >= rectangle.y && p.y <= rectangle.endY) {
                System.out.println("OSUI: " + rectangle);
                System.out.println("indeksi: " + index);
                try {
                    level.toggleButton(index);
                    if(level.buttonStates[index] != desiredButtonStates[index]) wrongButtonPressed();
                    resources.buttonClick.play(1.0f, 1.0f);
                } catch (SlickException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                for(int i = 0; i<level.buttonStates.length; i++) {

                    if(level.buttonStates[i] != desiredButtonStates[i]) {

                        break;
                    }

                    if(i >= level.buttonStates.length -1) correctButtonCombination();
                }
                break;
            }
            }
        } else {
        System.out.println("Nappi" + button);
          showMap = true;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        System.out.println("TOIMIIIIII " + key);
        if(key == 28) {
            System.out.println("PAINOIT ENTTERIÄ: " + stringBufferUserInput.toString());
            moveConsoleRows();
            stringBufferUserInput = new StringBuffer("");
        }
        else if(key == 14) {
            if (stringBufferUserInput.length() > 0) stringBufferUserInput.deleteCharAt(stringBufferUserInput.length()-1);
        }
        else {
            if (stringBufferUserInput.length() < 34) stringBufferUserInput.append(Character.toString(c));
//          consoleText.add(Character.toString(c));
            //stringBufferC.append(c);
        }
        consoleRows[consoleRowCurrent] = new StringBuffer("C:\\>").append( stringBufferUserInput.toString() ).toString();
    }
    public void moveConsoleRows() {

        if (consoleRowCurrent < consoleRows.length-1) {
            consoleRowCurrent++;
        }
        else {
            for (int i = 1; i < consoleRows.length; i++) {
                consoleRows[i-1] = consoleRows[i];
            }
        }
        /*
        String apu = "";
        String[] helpArray = new String[8];
        helpArray = consoleRows;
                 for(int i = 0; i<helpArray.length -1; i++) {
                         helpArray[i]
                 }
        helpArray[helpArray.length] = stringBufferC.toString();
        consoleRows = helpArray;
        */
    }

    public void drawConsole(Graphics g) {
      //  g.drawString(consoleRows[1], 412, 50);
        for (int i = 0; i < consoleRows.length; i++) {
            if (consoleRows[i] != null) g.drawString(consoleRows[i], 412, 40+i*24);
        }
    }
     //Tasks that are done when a wrong button is pressed
     public void wrongButtonPressed() {
         System.out.println("RÄJÄHTI");
         double heartSpeed = heart.getSpeed();
         heart.setSpeed(heartSpeed+ mistakeSpeed);



     }
    //Tasks that are done when player enters correct button combination
    public void correctButtonCombination() {
        System.out.println("OIKEA YHDISTELMÄ");
    }

    public void stopGame() {
        gameState = 0;
    }

    //What happens when game ends is handled here
    public void gameEnded() {
              System.out.println("PELI LOPPUI");
    }
    @Override
    public void mouseReleased(int button, int x, int y) {
        if(button == 0) {
            hand = resources.handResting;
        }
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
            pendingCuts = level.detectCuts(currentLine);
            currentLine = null;
        }
        showMap = false;
        mapY = mapStartLocation;
    }

    private void applyCuts(List<Cut> cuts) throws SlickException {
        final Graphics cutsG = cutsOverlay.getGraphics();
        for (Cut cut: cuts) {
            cutsG.fillOval(cut.start.x, cut.start.y, cut.width, cut.height);
        }
        cutsG.flush();
    }

    private static double getMinimumFrameTime() {
        return 1000.0 / MAX_FRAME_RATE;
    }

    private static double getMaximumFrameTime() {
        return 1000.0 / MIN_FRAME_RATE;
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Blowjob());

        app.setMinimumLogicUpdateInterval((int)getMinimumFrameTime());
        app.setMaximumLogicUpdateInterval((int)getMaximumFrameTime());

        app.setDisplayMode(800, 600, false);
        app.setMouseGrabbed(true);

        app.start();
    }
}
