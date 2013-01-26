import org.newdawn.slick.Graphics;

public class Clock {
    final Resources resources;
    private long timeRemaining;

    public Clock(final Resources resources, final long timeRemaining) {
        this.resources = resources;
        this.timeRemaining = timeRemaining;
    }

    public void draw(final Graphics g, final int left, final int top) {
        final int secondsLeft = left + 2 * resources.digits[0].getWidth() + resources.colon.getWidth();
        final int colonLeft = left + 2 * resources.digits[0].getWidth();
        final int minutesLeft = left;
        drawNumber(g, minutesLeft, top, (int)remainingMinutes(), 2);
        g.drawImage(resources.colon, colonLeft, top);
        drawNumber(g, secondsLeft, top, (int)remainingSeconds(), 2);
    }

    public void drawNumber(final Graphics g, final int left, final int top, final int number, final int width) {
        int n = number;
        int x = left + (width - 1) * resources.digits[0].getWidth();
        for (int i = 0; i < width; i++) {
            drawDigit(g, x, top, n % 10);
            n /= 10;
            x -= resources.digits[0].getWidth();
        }
    }

    public void drawDigit(final Graphics g, final int x, final int y, final int digit) {
        g.drawImage(resources.digits[digit], x, y);
    }

    public void decrement(final long delta) {
        timeRemaining -= delta;
    }

    public long remainingMinutes() {
        return timeRemaining / 1000 / 60;
    }

    public long remainingSeconds() {
        return (timeRemaining - (remainingMinutes() * 1000 * 60)) / 1000;
    }

    public long remainingTenths() {
        return (timeRemaining - (remainingSeconds() * 1000) - (remainingMinutes() * 1000 * 60)) / 100;
    }

}
