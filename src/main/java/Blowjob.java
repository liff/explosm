import org.newdawn.slick.*;
import java.util.*;

public class Blowjob extends BasicGame {
    private final Heart heart;
    private List<Double> koordinaatit;

    public Blowjob() {
        super("Blowjob");
        heart = new Heart();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        LueLiikerata.main(new String[]{});
        koordinaatit = LueLiikerata.getSkaalattuLista();
        heart.reset();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        heart.advance();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        Input input = gc.getInput();
        Position position = heart.distortMouse(Position.fromMouse(input));

        g.drawString("sakset", position.x, position.y);
        float x = 0;
        for (Double item : koordinaatit)
        {
            float f = item.floatValue();
         g.drawLine(x,f*100,x,f*100+10);
            x++;
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Blowjob());

        app.setDisplayMode(800, 600, false);
        app.start();
    }
}
