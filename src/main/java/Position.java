import org.newdawn.slick.Input;

class Position {
    public final int x;
    public final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position adjustedBy(final int dx, final int dy) {
        return new Position(x + dx, y + dy);
    }

    public static Position fromMouse(Input input) {
        return new Position(input.getMouseX(), input.getMouseY());
    }
}
