import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Music;
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
    public final Sound beat1;
    public final Sound beat2;
    public final Sound buttonClick;
    public final Sound consoleBeep;
    public final Sound consoleEnter;
    public final Sound death;
    public final Sound winSound;
    public final Sound rullerTick;
    public final Music mainMusic;
    public final Image[] digits;
    public final Image colon;
    public final Image controller1;
    public final Image controller2;
    public final Image scale;
    public final Image instructions;
    public final Image hand;
    public final Image handResting;
    public final Image play;
    public final Image quit;
    public final Image logo;
    public final Image mainMenuBG;
    public final Image gameOver;
    public final Image win;
    public Image lowestRedWire;
    public Image blackWire;
    public Image greenWire;
    public Image yellowWire;
    public Image redWire;
    public Image pinkWire;
    public Image fatBlueWire;
    public Image j5;
    public Image j7;
    public Image j9;
    public Image kayra;


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
            controller1 = new Image(URLDecoder.decode(ResourceLoader.getResource("ylempisaadin.png").getPath(), "UTF-8"));
            controller2 = new Image(URLDecoder.decode(ResourceLoader.getResource("alempisaadin.png").getPath(), "UTF-8"));
            scale = new Image(URLDecoder.decode(ResourceLoader.getResource("asteikko.png").getPath(), "UTF-8"));
            instructions = new Image(URLDecoder.decode(ResourceLoader.getResource("paperiohje.png").getPath(), "UTF-8"));
            hand = new Image(URLDecoder.decode(ResourceLoader.getResource("leikkaa75.png").getPath(), "UTF-8"));
            handResting = new Image(URLDecoder.decode(ResourceLoader.getResource("levossa75.png").getPath(), "UTF-8"));
            play = new Image(URLDecoder.decode(ResourceLoader.getResource("play.png").getPath(), "UTF-8"));
            quit = new Image(URLDecoder.decode(ResourceLoader.getResource("quit.png").getPath(), "UTF-8"));
            logo = new Image(URLDecoder.decode(ResourceLoader.getResource("logo.png").getPath(), "UTF-8"));
            mainMenuBG = new Image(URLDecoder.decode(ResourceLoader.getResource("tausta2.png").getPath(), "UTF-8"));
            gameOver = new Image(URLDecoder.decode(ResourceLoader.getResource("gameover.png").getPath(), "UTF-8"));
            win = new Image(URLDecoder.decode(ResourceLoader.getResource("voitto.png").getPath(), "UTF-8"));
            winSound = new Sound(URLDecoder.decode(ResourceLoader.getResource("fin.wav").getPath(), "UTF-8"));
            compositedBackground = createCompositedBackground();
            beat1 = new Sound(URLDecoder.decode(ResourceLoader.getResource("beat1_kovempi.wav").getPath(), "UTF-8"));
            beat2 = new Sound(URLDecoder.decode(ResourceLoader.getResource("beat2_kovempi.wav").getPath(), "UTF-8"));
            death = new Sound(URLDecoder.decode(ResourceLoader.getResource("death.wav").getPath(), "UTF-8"));
            consoleBeep = new Sound(URLDecoder.decode(ResourceLoader.getResource("consolekeypress.wav").getPath(), "UTF-8"));
            consoleEnter = new Sound(URLDecoder.decode(ResourceLoader.getResource("console_enter.wav").getPath(), "UTF-8"));
            buttonClick = new Sound((URLDecoder.decode(ResourceLoader.getResource("click_maybe.wav").getPath(), "UTF-8")));
            mainMusic = new Music((URLDecoder.decode(ResourceLoader.getResource("chucknorriswatch.wav").getPath(), "UTF-8")));
            kayra = new Image(URLDecoder.decode(ResourceLoader.getResource("kayra.png").getPath(), "UTF-8"));
            rullerTick = new Sound((URLDecoder.decode(ResourceLoader.getResource("ruller_tick.wav").getPath(), "UTF-8")));
            digits = new Image[10];
            for (int i = 0; i < 10; i++) {
                digits[i] = new Image(URLDecoder.decode(ResourceLoader.getResource(i + ".png").getPath(), "UTF-8"));
            }
            colon = new Image(URLDecoder.decode(ResourceLoader.getResource("colon.png").getPath(), "UTF-8"));
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
        g.drawImage(scale, 0,0);

        g.flush();
        return image;
    }

    private List<Image> loadWires() throws SlickException {
        final List<Image> images = new ArrayList<Image>();

        images.add(fatBlueWire =new Image(ResourceLoader.getResource("j10.png").getFile()));
        images.add(j9 = new Image(ResourceLoader.getResource("j9.png").getFile()));
        images.add(lowestRedWire = new Image(ResourceLoader.getResource("j8.png").getFile()));
        images.add(j7 = new Image(ResourceLoader.getResource("j7.png").getFile()));
        images.add(yellowWire = new Image(ResourceLoader.getResource("j6.png").getFile()));
        images.add(j5 = new Image(ResourceLoader.getResource("j5.png").getFile()));
        images.add(greenWire = new Image(ResourceLoader.getResource("j4.png").getFile()));
        images.add(pinkWire = new Image(ResourceLoader.getResource("j3.png").getFile()));
        images.add(blackWire = new Image(ResourceLoader.getResource("j2.png").getFile()));
        images.add(redWire = new Image(ResourceLoader.getResource("j1.png").getFile()));
        return images;
    }
}
