import java.util.List;

public class Heart {
    private final int frameTime;
    private int time;
    private List<Double> points;
    public int speed;

    public Heart(final int frameTime, List<Double> points) {
        this.frameTime = frameTime;
        this.time = 0;
        this.points = points;
        this.speed = 1;
    }

    public void distortMouse(final Position position) {
        position.y += points.get(time % points.size()) * 50;
    }

    public void advanceBy(final int milliseconds) {
        time += speed * Math.max(1, frameTime / milliseconds);
    }
}
