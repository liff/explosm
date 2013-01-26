import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

public class Resources {
    private final Image background;
    private final Image clockBackground;
    private final Image panelBackground;
    private final Image connectors;
    public final Image buttonOn;
    public final Image buttonOff;
    public final Image compositedBackground;
    public final List<Image> wires;

    public Resources() throws SlickException {
        background = new Image(ResourceLoader.getResource("background.png").getFile());
        clockBackground = new Image(ResourceLoader.getResource("clock_background.png").getFile());
        panelBackground = new Image(ResourceLoader.getResource("panel_background.png").getFile());
        buttonOn = new Image(ResourceLoader.getResource("button_on.png").getFile());
        buttonOff = new Image(ResourceLoader.getResource("button_off.png").getFile());
        connectors = new Image(ResourceLoader.getResource("connectors.png").getFile());
        wires = loadWires();
        compositedBackground = createCompositedBackground();
    }

    private Image createCompositedBackground() throws SlickException {
        final Image image = Image.createOffscreenImage(background.getWidth(), background.getHeight());
        final Graphics g = image.getGraphics();
        g.drawImage(background, 0, 0);
        g.drawImage(clockBackground, 0, 0);
        g.drawImage(panelBackground, 0, 0);
        g.drawImage(connectors, 0, 0);
        g.flush();
        return image;
    }

    private List<Image> loadWires() throws SlickException {
        final List<Image> images = new ArrayList<Image>();
        images.add(new Image(ResourceLoader.getResource("wire1.png").getFile()));
        return images;
    }
}
