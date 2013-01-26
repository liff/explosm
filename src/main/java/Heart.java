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
    private final double frameTime;
    private List<Double> points;
    private double epsilon;
    private double speed;
    private int index;
    private List<BeatListenerEntry> beatListeners;
    public int beatSize;

    public Heart(final double frameTime, final List<Double> points, final double initialSpeed, final int beatSize) {
        this.frameTime = frameTime;
        this.points = points;
        this.index = 0;
        this.beatListeners = new ArrayList<BeatListenerEntry>();
        this.setSpeed(initialSpeed);
        this.beatSize = beatSize;
    }

    public void addBeatListener(final BeatListener listener) {
        beatListeners.add(new BeatListenerEntry(listener));
    }

    public double getAdjustment() {
        return points.get(index) * beatSize;
    }

    public double getPhase() {
        return (double)index / (double)points.size();
    }

    public void update(final int delta) {
        index += speed * frameTime / (double)delta;;
        if (index >= points.size()) {
            index = 0;
            for (final BeatListenerEntry listener: beatListeners) {
                listener.triggeredOnThisBeat = false;
            }
        }
        for (final BeatListenerEntry listener: beatListeners) {
            if (!listener.triggeredOnThisBeat && Math.abs(getPhase() - listener.phase()) <= epsilon) {
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

    public void setSpeed(final double newSpeed) {
        speed = newSpeed;
        epsilon = (speed + 2) / this.points.size();
    }

    public double getSpeed() {
              return this.speed;
    }
}
