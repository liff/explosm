import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public final long allowedTime;
    public long timeRemaining;
    public final Image background;
    public final List<Image> wireOverlays;
    public final Color backgroundColor;

    public Level(final long allowedTime, final Image background, final List<Image> wireOverlays) throws SlickException {
        this.allowedTime = allowedTime;
        this.background = background;
        this.wireOverlays = wireOverlays;
        this.backgroundColor = background.getColor(5, 5);
    }

    public Level(final long allowedTime, final String backgroundFile, final List<String> wireOverlayFiles) throws SlickException {
        this(allowedTime, new Image(backgroundFile), loadWireOverlays(wireOverlayFiles));
    }

    private static List<Image> loadWireOverlays(final List<String> wireOverlayFiles)  throws SlickException {
        final List<Image> wireOverlayImages = new ArrayList<Image>();
        for (String wireOverlayFile: wireOverlayFiles) {
            wireOverlayImages.add(new Image(wireOverlayFile));
        }
        return wireOverlayImages;
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
}
