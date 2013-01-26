import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public final long allowedTime;
    public long timeRemaining;
    private final Resources resources;
    public final Image wireOverlay;
    public final List<Image> wires;
    public final Image buttonOverlay;
    public final Image background;

    public Level(final long allowedTime, final Resources resources) throws SlickException {
        this.allowedTime = allowedTime;
        this.resources = resources;
        this.background = resources.compositedBackground;
        this.wires = resources.wires;
        this.wireOverlay = createWireOverlay();
        this.buttonOverlay = createButtonOverlay();
    }

    public void update(final int delta) {
        timeRemaining -= delta;
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
        final int buttonWidth = resources.buttonOn.getWidth();
        final int buttonHeight = resources.buttonOn.getHeight();
        final int margin = 10;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                g.drawImage(resources.buttonOff, originX + x * (buttonWidth + margin), originY + y * (buttonHeight + margin));
            }
        }
        g.flush();
        return image;
    }
}
