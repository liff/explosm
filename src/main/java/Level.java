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
    public final List<Wire> wires;
    public Wire lowestRedWire;
    public Wire blackWire;
    public Wire greenWire;
    public Wire yellowWire;
    public Wire redWire;
    public Wire pinkWire;
    public Wire fatBlueWire;
    public Wire j5;
    public Wire j7;
    public Wire j9;
    public Image buttonOverlay;
    public final Image background;
    public List<Rectangle> buttonHitboxes = new ArrayList<Rectangle>();
    public boolean[] buttonStates;
    public List<Cut> allCuts;
    public Controller upperController;
    public Controller lowerController;
    public int step = 1;

    public boolean clockRunning = true;


    public Level(final long allowedTime, final Resources resources) throws SlickException {
        this.resources = resources;
        this.background = resources.compositedBackground;
        this.wires = new ArrayList<Wire>();
        this.wires.add(fatBlueWire = new Wire(resources.fatBlueWire, "fat blue"));
        this.wires.add(j9 = new Wire(resources.j9, "j9"));
        this.wires.add(lowestRedWire = new Wire(resources.lowestRedWire, "lowest red"));
        this.wires.add(j7 = new Wire(resources.j7, "j7"));
        this.wires.add(yellowWire = new Wire(resources.yellowWire, "yellow"));
        this.wires.add(j5 = new Wire(resources.j5, "j5"));
        this.wires.add(greenWire = new Wire(resources.greenWire, "green"));
        this.wires.add(pinkWire = new Wire(resources.pinkWire, "pink"));
        this.wires.add(blackWire = new Wire(resources.blackWire, "black"));
        this.wires.add(redWire = new Wire(resources.redWire, "red"));
        this.allCuts = new ArrayList<Cut>();
        this.wireOverlay = createWireOverlay();
        this.buttonStates = new boolean[3 * 4];
        this.buttonOverlay = createButtonOverlay();

        this.allowedTime = allowedTime;
        this.clock = new Clock(resources, this.allowedTime);
        this.upperController = new Controller(resources.controller1, 285, 300);
        this.lowerController = new Controller(resources.controller2, 285, 467);
    }

    public boolean getClockRunning() {
        return clockRunning;
    }

    public void recreate() throws SlickException{
        this.buttonOverlay = createButtonOverlay();
    }

    public void toggleButton(int buttonNumber) throws SlickException {
        buttonStates[buttonNumber] = !buttonStates[buttonNumber];
        this.recreate();
    }

    public void update(final int delta) {
         if(clockRunning) clock.decrement(delta);
        if(clock.remainingMinutes() == 0 && clock.remainingSeconds() == 0 && clock.remainingTenths() == 0) {
          clockRunning = false;

        }
    }

    public void stopLevel() {
        clockRunning = false;
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

    public Controller getControllerUnder(final Position position) {
        if (resources.controller1.getColor(position.x, position.y).getAlpha() != 0)
            return upperController;
        if (resources.controller2.getColor(position.x, position.y).getAlpha() != 0)
            return lowerController;
        return null;
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
        for (final Wire wire: wires) {
            for (Position point: points) {
                final Color colorUnderPoint = wire.image.getColor(point.x, point.y);
                if (colorUnderPoint.getAlpha() != 0 && cutStart == null) {
                    cutStart = point;
                }
                else if (colorUnderPoint.getAlpha() == 0 && cutStart != null) {
                    final Cut cut = new Cut(wire, cutStart.copy(), point.copy());
                    newCuts.add(cut);
                    cutStart = null;
                }
            }
        }
        allCuts.addAll(newCuts);
        return newCuts;
    }

    public void nextStep() {
        step += 1;
        System.out.println("step was " + (step - 1) + " now " + step);
    }
}
