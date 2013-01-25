import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Position> interpolate(final Position start, final Position end, final int steps) {
        final List<Position> positions = new ArrayList<Position>();
        final double xDistance = end.x - start.x;
        final double yDistance = end.y - start.y;
        final double xStep = xDistance / steps;
        final double yStep = yDistance / steps;
        for (int i = 0; i < steps; i++) {
            positions.add(new Position((int)(start.x + (xStep * i)), (int)(start.y + (yStep * i))));
        }
        return positions;
    }
}
