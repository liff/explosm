import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Controller {
    public final Image image;
    public float currentAngle;

    public Controller(final Image image, float centerX, float centerY) {
        this.image = image;
        setCenterOfRotation(centerX, centerY);
    }

    public void rotateBy(final float angleDelta) {
        image.rotate(angleDelta);
        currentAngle += angleDelta;
    }

    public void draw(Graphics g) {
        g.drawImage(image, 0, 0);
    }

    public void setCenterOfRotation(float x, float y) {
        image.setCenterOfRotation(x, y);
    }
}
