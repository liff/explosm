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
    private final List<Line> lines;
    private final List<List<Position>> allPoints;
    private Line currentLine;
    private List<Cut> cuts;
    private Input input;
    private Image cutsOverlay;
    private long timeFlown;
    private long timeBetweenBeats;
    private Sound beat1;
    private Sound beat2;
    private double beatTimer1;
    private double beatTimer2;
    private double beat1Length;
    private double beat2Length;

    double test;
    double test2;

    boolean beatsPlayed;
    public Blowjob() throws SlickException {
        super("Blowjob");
        lines = new LinkedList<Line>();
        allPoints = new LinkedList<List<Position>>();
        cuts = new LinkedList<Cut>();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        resources = new Resources();
        level = new Level(ALLOCATED_TIME, resources);

        cutsOverlay = Image.createOffscreenImage(gc.getWidth(), gc.getHeight());
        final Graphics cutsG = cutsOverlay.getGraphics();
        cutsG.setBackground(Color.transparent);
        cutsG.clear();
        cutsG.setColor(new Color(0, 255, 0));

        beat1 = new Sound("src/main/resources/beat1.wav");

        final Heart heart = new Heart(getMinimumFrameTime(), LueLiikerata.read());
        player = new Player(heart);
        player.addBeatListener(new Heart.BeatListener() {
            public double phase() {
                return 0;
            }
            public void onBeat(final double phase) {
                System.out.println("phase " + phase);
                beat1.play();
            }
        });
        player.addBeatListener(new Heart.BeatListener() {
            public double phase() {
                return 0.6;
            }
            public void onBeat(final double phase) {
                System.out.println("phase " + phase);
                beat2.play();
            }
        });
        heart.speed = 10; // XXX
        System.out.println("heart duration is " + player.getBeatDuration());
        input = gc.getInput();

        beat2 = new Sound("src/main/resources/beat2.wav");
        double beatDuration = player.getBeatDuration();

        timeBetweenBeats = 1000;

        //Pitäis saada tapahtumaan jokaisen sykkeen alussa
        beatTimer1 = beatDuration/10;

        beatTimer2 = beatDuration/3;
        beat1Length = 182;
        beat2Length = 285;
        test = beat1Length / beatDuration;
        test2 = beat2Length / beatDuration;

        System.out.println("test is " +test);
        //beat2.loop((float)test, 10.0f);

        beatsPlayed = false;
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        level.update(delta);
        player.setPositionAndUpdate(input, delta);
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
        }
        applyCuts();

        timeFlown += delta;

        beatTimer1 -= delta;



       if (beatTimer1 <= 0 && beatsPlayed == false) {
           //beat1.play((float)test, 10.0f);
           beatTimer1 += player.getBeatDuration(); //tapa minut
       }

       beatTimer2 -= delta;

       if (beatTimer2 <= 0 && beatsPlayed == false) {
           //beat2.play((float)test2, 10.0f);
           beatTimer2 += player.getBeatDuration(); //lopeta tuskani
       }

       // if(timeFlown >= (player.getBeatDuration()/10 + beat1Length + player.getBeatDuration()/3 + beat2Length) ) {
       //     beatTimer1 = player.getBeatDuration()/10;
       //     beatTimer2 = player.getBeatDuration()/3;
       //
       // }


    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(level.background, 0, 0);
        g.drawImage(level.wireOverlay, 0, 0);
        g.drawImage(level.buttonOverlay, 0, 0);
        g.drawImage(cutsOverlay, 0, 0);
        g.setColor(new Color(255, 255, 255));
        g.drawString("sakset", player.getDisturbedPosition().x, player.getDisturbedPosition().y);
        g.drawString(level.remainingTimeString(), 0, 0);
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

    private void detectCuts(final Line line) {
        List<Position> points = Util.interpolate(line.start, line.end, 100);
        Position cutStart = null;
        for (Position point: points) {
            for (final Image wire: level.wires) {
                final Color colorUnderPoint = wire.getColor(point.x, point.y);
                System.out.println(colorUnderPoint);
                if (colorUnderPoint.getAlpha() != 0 && cutStart == null) {
                    cutStart = point;
                }
                else if (colorUnderPoint.getAlpha() == 0 && cutStart != null) {
                    final Cut cut = new Cut(cutStart.copy(), point.copy());
                    cuts.add(cut);
                    player.increaseScore();
                    cutStart = null;
                }
            }
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (currentLine != null) {
            currentLine.end = player.getDisturbedPosition();
            lines.add(currentLine);
            detectCuts(currentLine);
            List<Position> points = Util.interpolate(currentLine.start, currentLine.end, 100);
            currentLine = null;
            allPoints.add(points);
        }
    }

    private static double getMinimumFrameTime() {
        return 1000.0 / MAX_FRAME_RATE;
    }

    private static double getMaximumFrameTime() {
        return 1000.0 / MIN_FRAME_RATE;
    }

    private void applyCuts() throws SlickException {
        final Graphics cutsG = cutsOverlay.getGraphics();
        for (Cut cut: cuts) {
            cutsG.fillOval(cut.start.x, cut.start.y, cut.width, cut.height);
        }
        cutsG.flush();
        cuts.clear();
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
