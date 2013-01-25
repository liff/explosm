import org.newdawn.slick.Input;

class Position {
    public int x = 0;
    public int y = 0;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this(0, 0);
    }

    public void adjustBy(final int dx, final int dy) {
        x += dx;
        y += dy;
    }

    public void update(final Input input) {
        x = input.getMouseX();
        y = input.getMouseY();
    }

    public static Position fromMouse(final Input input) {
        return new Position(input.getMouseX(), input.getMouseY());
    }

    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }

    public Position copy() {
        return new Position(x, y);
    }
}
