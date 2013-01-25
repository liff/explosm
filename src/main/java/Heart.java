public class Heart {
    private double time;

    public Heart() {
        this.time = 0.0;
    }

    public Position distortMouse(final Position position) {
        final int dx = (int)(3 * Math.sin(time));
        final int dy = (int)(10 * Math.cos(time));
        return position.adjustedBy(dx, dy);
    }

    public void reset() {
        time = 0.0;
    }

    public void advance() {
        time += 0.01;
    }
}
