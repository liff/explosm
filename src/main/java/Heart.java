public class Heart {
    private final int frameTime;
    private double time;

    public Heart(final int frameTime) {
        this.frameTime = frameTime;
        this.time = 0.0;
    }

    public void distortMouse(final Position position) {
        final int dx = (int)(3 * Math.sin(time));
        final int dy = (int)(10 * Math.cos(time));
        position.adjustBy(dx, dy);
    }

    public void advanceBy(final int milliseconds) {
        time += (double)frameTime / (double)milliseconds / 10.0;
    }
}
