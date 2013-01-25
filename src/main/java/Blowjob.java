import org.newdawn.slick.*;
import java.util.LinkedList;
import java.util.List;

public class Blowjob extends BasicGame {
    private static final int MAX_FRAME_RATE = 60;
    private static final int MIN_FRAME_RATE = 10;
    private static final int ALLOCATED_TIME = 3 * 60 * 1000 + 6000;

    private Player player;
    private Level level;
    private final List<Line> lines;
    private final List<List<Position>> allPoints;
    private Line currentLine;
    private List<Position> cuts;
    private Input input;
    private Sound beat;

    public Blowjob() throws SlickException {
        super("Blowjob");
        lines = new LinkedList<Line>();
        allPoints = new LinkedList<List<Position>>();
        cuts = new LinkedList<Position>();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        level = new Level(ALLOCATED_TIME, "src/main/resources/testi.png");

        final Heart heart = new Heart(getMinimumFrameTime(), LueLiikerata.read());
        player = new Player(heart);
        heart.speed = 8; // XXX

        input = gc.getInput();

        beat = new Sound("src/main/resources/beat1.wav");
        beat.loop(0.5f, 1.0f);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        level.update(delta);
        player.setPositionAndUpdate(input, delta);
        if (currentLine != null) {
            currentLine.end = player.getDistrubedPosition();
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.drawImage(level.background, 0, 0);
        g.drawString("sakset", player.getDistrubedPosition().x, player.getDistrubedPosition().y);
        g.drawString(level.remainingTimeString(), 0, 0);
        g.drawString(String.format("%.2f BPM", player.getBPM()), 0, 40);
        //for (Line line: lines) {
        //    g.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        //}
        for (List<Position> points: allPoints) {
            for (Position point: points) {
                g.drawOval(point.x - 1, point.y - 1, 2, 2);
            }
        }
        for (Position cut: cuts) {
            g.drawOval(cut.x - 3, cut.y - 3, 12, 12);
        }
        if (currentLine != null) {
            g.drawLine(currentLine.start.x, currentLine.start.y, currentLine.end.x, currentLine.end.y);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        currentLine = new Line(player.getDistrubedPosition(), player.getDistrubedPosition());
    }

    private void detectCuts(final Line line) {
        List<Position> points = Util.interpolate(line.start, line.end, 100);
        Position cutStart = null;
        for (Position point: points) {
            Color color = level.background.getColor(point.x, point.y);
            if (!color.equals(level.backgroundColor)) {
                cutStart = point;
            }
            else if (cutStart != null) {
                final List<Position> cutPoints = Util.interpolate(cutStart, point, 3);
                final Position middle = cutPoints.get(1);
                cuts.add(middle);
                cutStart = null;
            }
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (currentLine != null) {
            currentLine.end = player.getDistrubedPosition();
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

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Blowjob());

        app.setMinimumLogicUpdateInterval((int)getMinimumFrameTime());
        app.setMaximumLogicUpdateInterval((int)getMaximumFrameTime());

        app.setDisplayMode(800, 600, false);
        app.setMouseGrabbed(true);

        app.start();
    }
}
