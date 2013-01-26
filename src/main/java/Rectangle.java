public class Rectangle {
    public final int x;
    public final int y;
    public final int endX;
    public final int endY;
    public final int width;
    public final int height;

    public Rectangle(int x, int y, int width, int height)  {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.endX = x + width;
        this.endY = y + height;
    }

    public Rectangle(final Position start, final Position end) {
        this(start.x, start.y, end.x - start.x, end.y - start.y);
    }
}
