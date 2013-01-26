public class Cut {
    public final Wire wire;
    public final Position start;
    public final Position end;
    public final Position middle;
    public final int width;
    public final int height;

    public Cut(final Wire wire, final Position start, final Position end) {
        this.wire = wire;
        this.start = new Position(Math.min(start.x, end.x), Math.min(start.y, end.y));
        this.end = new Position(Math.max(start.x, end.x), Math.max(start.y, end.y));
        this.middle = new Position(this.start.x + (this.end.x - this.start.x)/2, this.start.y + (this.end.y - this.start.y)/2);
        this.width = Math.max(1, this.end.x - this.start.x);
        this.height = Math.max(1, this.end.y - this.start.y);
    }

    public String toString() {
        return String.format("Cut(%s, %s, %s, %d, %d)", start.toString(), end.toString(), middle.toString(), width, height);
    }
}
