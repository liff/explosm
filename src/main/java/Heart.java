import java.util.ArrayList;
import java.util.List;

public class Heart {
    private class BeatListenerEntry {
        public final BeatListener listener;
        public boolean triggeredOnThisBeat = false;

        public BeatListenerEntry(final BeatListener listener) {
            this.listener = listener;
        }

        public void onBeat(double phase) {
            listener.onBeat(phase);
        }

        public double phase() {
            return listener.phase();
        }
    }
    public interface BeatListener {
        public double phase();
        public void onBeat(double phase);
    }
    private final double EPSILON = 0.05;
    private final double frameTime;
    private double time;
    private List<Double> points;
    public double speed;
    private int index;
    private int previousIndex;
    private List<BeatListenerEntry> beatListeners;

    public Heart(final double frameTime, List<Double> points) {
        this.frameTime = frameTime;
        this.time = 0.0;
        this.points = points;
        this.speed = 1.0;
        this.index = this.previousIndex = 0;
        this.beatListeners = new ArrayList<BeatListenerEntry>();
    }

    public void addBeatListener(final BeatListener listener) {
        beatListeners.add(new BeatListenerEntry(listener));
    }

    public double getAdjustment() {
        return points.get(index) * 50;
    }

    public double getPhase() {
        return (double)index / (double)points.size();
    }

    public void update(final int delta) {
        final double inc = speed * frameTime / (double)delta;
        time += speed * frameTime / (double)delta;
        previousIndex = index;
        index += inc;
        if (index >= points.size()) {
            index = 0;
            for (final BeatListenerEntry listener: beatListeners) {
                listener.triggeredOnThisBeat = false;
            }
        }
        for (final BeatListenerEntry listener: beatListeners) {
            if (!listener.triggeredOnThisBeat && Math.abs(getPhase() - listener.phase()) <= EPSILON) {
                listener.triggeredOnThisBeat = true;
                listener.onBeat(getPhase());
            }
        }
    }

    public double getBeatDuration() {
        return (points.size() / speed) * frameTime;
    }

    public double getBPM() {
        return 60.0 * (1 / (getBeatDuration() / 1000.0));
    }
}
