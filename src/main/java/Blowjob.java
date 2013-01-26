import org.newdawn.slick.*;
import org.newdawn.slick.util.DefaultLogSystem;

import java.util.LinkedList;
import java.util.List;

public class Blowjob extends BasicGame {
    private static final int MAX_FRAME_RATE = 60;
    private static final int MIN_FRAME_RATE = 10;
    private static final int ALLOCATED_TIME = 3 * 60 * 1000 + 6000;
    private static final int BEAT_SIZE = 50;

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

        heart = new Heart(getMinimumFrameTime(), LueLiikerata.read(), 6.0, BEAT_SIZE);
        player = new Player(heart);
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
        level.update(delta);
        player.setPositionAndUpdate(input, delta);
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
        }
        if (pendingCuts != null) {
            applyCuts(pendingCuts);
            pendingCuts = null;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(level.background, 0, 0);
        g.drawImage(level.wireOverlay, 0, 0);
        g.drawImage(level.buttonOverlay, 0, 0);
        g.drawImage(cutsOverlay, 0, 0);
        level.clock.draw(g, 50, 55);
        g.setColor(new Color(255, 255, 255));
        g.drawString("sakset", player.getDisturbedPosition().x, player.getDisturbedPosition().y);

        g.setColor(new Color(255, 255, 255));
        if (currentLine != null) {
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }


    }

    @Override
    public void mousePressed(int button, int x, int y) {
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
    @Override
    public void mouseReleased(int button, int x, int y) {
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
            pendingCuts = level.detectCuts(currentLine);
            currentLine = null;
        }
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
