package behavior;

import object.Bird;
import org.lwjgl.input.Mouse;

public class BirdBehavior implements Behavior {
    private Bird bird;

    public BirdBehavior(Bird bird) {
        this.bird = bird;
    }

    @Override
    public void update() {
//        bird.yPos = 600-Mouse.getY();
//        bird.xPos = Mouse.getX();
    }
}
