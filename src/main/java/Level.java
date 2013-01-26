import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Level {
    public final long allowedTime;
    public long timeRemaining;
    public Clock clock;
    private final Resources resources;
    public final Image wireOverlay;
    public final List<Image> wires;
    public final Image buttonOverlay;
    public final Image background;
    public List<Rectangle> buttonHitboxes = new ArrayList<Rectangle>();
    public boolean[] buttonStates;

    public Level(final long allowedTime, final Resources resources) throws SlickException {
        this.resources = resources;
        this.background = resources.compositedBackground;
        this.wires = resources.wires;
        this.wireOverlay = createWireOverlay();
        this.buttonOverlay = createButtonOverlay();
        this.buttonStates = new boolean[3 * 4];

        this.allowedTime = allowedTime;
        this.timeRemaining = this.allowedTime;
        this.clock = new Clock(resources, this.timeRemaining);
    }

    public void toggleButton(int buttonNumber) {
        buttonStates[buttonNumber] = !buttonStates[buttonNumber];
    }

    public void update(final int delta) {
        timeRemaining -= delta;
        clock.decrement(delta);
    }

    private long remainingMinutes() {
        return timeRemaining / 1000 / 60;
    }

    private long remainingSeconds() {
        return (timeRemaining - (remainingMinutes() * 1000 * 60)) / 1000;
    }

    private long remainingTenths() {
        return (timeRemaining - (remainingSeconds() * 1000) - (remainingMinutes() * 1000 * 60)) / 100;
    }

    public String remainingTimeString() {
        return String.format("%02d:%02d.%d", remainingMinutes(), remainingSeconds(), remainingTenths());
    }

    private Image createWireOverlay() throws SlickException {
        final Image image = Image.createOffscreenImage(resources.compositedBackground.getWidth(), resources.compositedBackground.getHeight());
        final Graphics g = image.getGraphics();
        for (final Image wire: resources.wires) {
            g.drawImage(wire, 0, 0);
        }
        g.flush();
        return image;
    }

    private Image createButtonOverlay() throws SlickException {
        final Image image = Image.createOffscreenImage(resources.compositedBackground.getWidth(), resources.compositedBackground.getHeight());
        final Graphics g = image.getGraphics();
        final int originX = 30;
        final int originY = 345;
        final int buttonWidth = resources.buttonOff.getWidth();
        final int buttonHeight = resources.buttonOff.getHeight();
        final int margin = 10;
        //Rectangle rectangle = new Rectangle(buttonX,buttonY,buttonWidth, buttonHeight);
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                int buttonX = originX + x * (buttonWidth + margin);
                int buttonY = originY + y * (buttonHeight + margin);
                rectangle = new Rectangle(buttonX,buttonY,buttonWidth, buttonHeight);
                buttonHitboxes.add(rectangle);
                g.drawImage(resources.buttonOff, buttonX, buttonY);
            }
        }
        g.flush();
        return image;
    }

    public List<Cut> detectCuts(final Line line) {
        final List<Cut> newCuts = new LinkedList<Cut>();
        List<Position> points = Util.interpolate(line.start, line.end, 100);
        Position cutStart = null;
        for (final Image wire: wires) {
            for (Position point: points) {
                final Color colorUnderPoint = wire.getColor(point.x, point.y);
                if (colorUnderPoint.getAlpha() != 0 && cutStart == null) {
                    cutStart = point;
                }
                else if (colorUnderPoint.getAlpha() == 0 && cutStart != null) {
                    final Cut cut = new Cut(cutStart.copy(), point.copy());
                    newCuts.add(cut);
                    cutStart = null;
                }
            }
        }
        return newCuts;
    }
}
