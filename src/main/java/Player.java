import org.newdawn.slick.Input;

public class Player {
    public final Heart heart;
    private int score = 0;
    public int x = 0;
    public int y = 0;

    public Player(final Heart heart) {
        this.heart = heart;
    }

    public void setPosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(final Input input) {
        setPosition(input.getMouseX(), input.getMouseY());
    }

    public Position getDisturbedPosition() {
        final double dx = 0;
        final double dy = heart.getAdjustment();
        return new Position(this.x + (int)dx, this.y + (int)dy);
    }

    public Position getPosition() {
        return new Position(this.x, this.y);
    }

    public void update(final int delta) {
        heart.update(delta);
    }

    public void setPositionAndUpdate(final Input input, final int delta) {
        setPosition(input);
        update(delta);
    }

    public double getBeatDuration() {
        return heart.getBeatDuration();
    }

    public double getBPM() {
        return heart.getBPM();
    }

    public void increaseScore() {
        score += 1;
    }

    public int getScore() {
        return score;
    }

    public void addBeatListener(Heart.BeatListener listener) {
        heart.addBeatListener(listener);
    }

    public int getBeatSize() {
        return heart.beatSize;
    }
}
