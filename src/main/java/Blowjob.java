import org.newdawn.slick.*;

public class Blowjob extends BasicGame {
    private final Heart heart;

    public Blowjob() {
        super("Blowjob");
        heart = new Heart();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
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
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Blowjob());

        app.setDisplayMode(800, 600, false);
        app.start();
    }
}
