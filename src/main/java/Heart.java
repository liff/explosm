import java.util.List;

public class Heart {
    private final double frameTime;
    private int time;
    private List<Double> points;
    public int speed;

    public Heart(final double frameTime, List<Double> points) {
        this.frameTime = frameTime;
        this.time = 0;
        this.points = points;
        this.speed = 1;
    }

    public double getAdjustment() {
        return points.get(time % points.size()) * 50;
    }

    public void update(final int delta) {
        time += speed * Math.max(1, frameTime / delta);
    }

    public double getBeatDuration() {
        return points.size() / speed * frameTime;
    }

    public double getBPM() {
        return 60.0 * (1 / (getBeatDuration() / 1000.0));
    }
}
