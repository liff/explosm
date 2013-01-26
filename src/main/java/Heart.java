import java.util.List;

public class Heart {
    private final double frameTime;
    private double time;
    private List<Double> points;
    public double speed;
    private int ss;

    public Heart(final double frameTime, List<Double> points) {
        this.frameTime = frameTime;
        this.time = 0.0;
        this.points = points;
        this.speed = 1.0;
        this.ss = 0;
    }

    public double getAdjustment() {
        final int index = (int)time % points.size();
        if (index < ss) {
            System.out.println("beat! " + System.currentTimeMillis());
        }
        ss = index;
        return points.get(index) * 50;
    }

    public void update(final int delta) {
        time += speed * frameTime / (double)delta;
    }

    public double getBeatDuration() {
        return (points.size() / speed) * frameTime;
    }

    public double getBPM() {
        return 60.0 * (1 / (getBeatDuration() / 1000.0));
    }
}
