import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Resources {
    private final Image background;
    private final Image clockBackground;
    private final Image panelBackground;
    private final Image connectors;
    private final Image sticker;
    public final Image buttonOn;
    public final Image buttonOff;
    public final Image compositedBackground;
    public final List<Image> wires;

    public Resources() throws SlickException {
        try {
            background = new Image(URLDecoder.decode(ResourceLoader.getResource("background.png").getPath(), "UTF-8"));
            clockBackground = new Image(URLDecoder.decode(ResourceLoader.getResource("clock_background.png").getPath(), "UTF-8"));
            panelBackground = new Image(URLDecoder.decode(ResourceLoader.getResource("panel_background.png").getPath(), "UTF-8"));
            buttonOn = new Image(URLDecoder.decode(ResourceLoader.getResource("button_on.png").getPath(), "UTF-8"));
            buttonOff = new Image(URLDecoder.decode(ResourceLoader.getResource("button_off.png").getPath(), "UTF-8"));
            connectors = new Image(URLDecoder.decode(ResourceLoader.getResource("connectors.png").getPath(), "UTF-8"));
            sticker = new Image(URLDecoder.decode(ResourceLoader.getResource("sticker.png").getPath(), "UTF-8"));
            wires = loadWires();
            compositedBackground = createCompositedBackground();
        }
        catch (UnsupportedEncodingException e) {
            throw new SlickException("PLAH", e);
        }
    }

    private Image createCompositedBackground() throws SlickException {
        final Image image = Image.createOffscreenImage(background.getWidth(), background.getHeight());
        final Graphics g = image.getGraphics();
        g.drawImage(background, 0, 0);
        g.drawImage(clockBackground, 0, 0);
        g.drawImage(panelBackground, 0, 0);
        g.drawImage(connectors, 0, 0);
        g.drawImage(sticker, 0, 0);
        g.flush();
        return image;
    }

    private List<Image> loadWires() throws SlickException {
        final List<Image> images = new ArrayList<Image>();
        images.add(new Image(ResourceLoader.getResource("wire1.png").getFile()));
        return images;
    }
}