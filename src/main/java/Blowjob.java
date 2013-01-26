import org.newdawn.slick.*;
import java.util.LinkedList;
import java.util.List;

public class Blowjob extends BasicGame {
    private static final int MAX_FRAME_RATE = 60;
    private static final int MIN_FRAME_RATE = 10;
    private static final int ALLOCATED_TIME = 3 * 60 * 1000 + 6000;

    private Resources resources;
    private Player player;
    private Level level;
    private Line currentLine;
    private Input input;
    private Image cutsOverlay;
    private List<Cut> pendingCuts;

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

        final Heart heart = new Heart(getMinimumFrameTime(), LueLiikerata.read(), 10.0);
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
        g.setColor(new Color(255, 255, 255));
        g.drawString("sakset", player.getDisturbedPosition().x, player.getDisturbedPosition().y);

        // jäljellä oleva aika
        g.drawString(level.remainingTimeString(), 0, 0);

        // turhia...
        g.drawString(String.format("%.2f BPM", player.getBPM()), 0, 40);
        g.drawString(String.format("Score: %d", player.getScore()), 0, 60);


        g.setColor(new Color(255, 255, 255));
        if (currentLine != null) {
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        currentLine = new Line(player.getDisturbedPosition(), player.getDisturbedPosition());
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
            pendingCuts = level.detectCuts(currentLine);
            currentLine = null;
            //allPoints.add(points);

            //testiä
            for(Rectangle rectangle : level.buttonHitboxes)
            {
                if(x >= rectangle.x && x <= rectangle.width && y >= rectangle.y && y <= rectangle.height ) {
                System.out.println("OSUI");
            }
            }
        }
    }

    private static double getMinimumFrameTime() {
        return 1000.0 / MAX_FRAME_RATE;
    }

    private static double getMaximumFrameTime() {
        return 1000.0 / MIN_FRAME_RATE;
    }

    private void applyCuts(List<Cut> cuts) throws SlickException {
        final Graphics cutsG = cutsOverlay.getGraphics();
        for (Cut cut: cuts) {
            cutsG.fillOval(cut.start.x, cut.start.y, cut.width, cut.height);
        }
        cutsG.flush();
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
