import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Level {
    public final long allowedTime;
    public Clock clock;
    private final Resources resources;
    public final Image wireOverlay;
    public final List<Image> wires;
    public Image buttonOverlay;
    public final Image background;
    public List<Rectangle> buttonHitboxes = new ArrayList<Rectangle>();
    public boolean[] buttonStates;

    public Level(final long allowedTime, final Resources resources) throws SlickException {
        this.resources = resources;
        this.background = resources.compositedBackground;
        this.wires = resources.wires;
        this.wireOverlay = createWireOverlay();
        this.buttonStates = new boolean[3 * 4];
        this.buttonOverlay = createButtonOverlay();


        this.allowedTime = allowedTime;
        this.clock = new Clock(resources, this.allowedTime);
    }
    public void recreate() throws SlickException{
        this.buttonOverlay = createButtonOverlay();
    }
    public void toggleButton(int buttonNumber) throws SlickException {
        buttonStates[buttonNumber] = !buttonStates[buttonNumber];
        this.recreate();
    }

    public void update(final int delta) {
        clock.decrement(delta);
    }

    private long remainingMinutes() {
        return clock.remainingMinutes();
    }

    private long remainingSeconds() {
        return clock.remainingSeconds();
    }

    private long remainingTenths() {
        return clock.remainingTenths();
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
        int index = 0;
        buttonHitboxes.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                int buttonX = originX + x * (buttonWidth + margin);
                int buttonY = originY + y * (buttonHeight + margin);
                rectangle = new Rectangle(buttonX,buttonY,buttonWidth, buttonHeight);
                buttonHitboxes.add(rectangle);
                if(buttonStates[index] == false) {
                g.drawImage(resources.buttonOff, buttonX, buttonY);
                } else g.drawImage(resources.buttonOn, buttonX, buttonY);
                index++;
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
