import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class Wire {
    public final Image image;

    public Wire(final Image image) {
        this.image = image;
    }

    public Rectangle getHitRectangle(final List<Position> points) {
        Position start = null;
        Position end = null;
        for (final Position point: points) {
            if (doesHit(point) && start == null) {
                start = point;
                end = point;
            }
            else if (!doesHit(point) && start != null) {
                end = point;
            }
        }
        if (start != null)
            return new Rectangle(start, end);
        return null;
    }

    public List<Position> getHitPoints(final List<Position> points) {
        final List<Position> hits = new ArrayList<Position>();
        for (final Position point: points) {
            if (doesHit(point))
                hits.add(point);
        }
        return hits;
    }

    public boolean doesHit(final Position point) {
        final Color colorUnderPoint = image.getColor(point.x, point.y);
        return colorUnderPoint.getAlpha() != 0;
    }
}
