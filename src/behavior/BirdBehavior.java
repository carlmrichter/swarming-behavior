package behavior;

import math.LineareAlgebra;
import math.Vektor2D;
import object.Bird;
import org.lwjgl.input.Mouse;

public class BirdBehavior implements Behavior {
    private Bird bird;
    private int counter;

    public BirdBehavior(Bird bird) {
        this.bird = bird;
        counter = 0;
    }

    @Override
    public void update() {
        if (counter < 90) {
            bird.orientation.rotate(1);
            counter++;
        }

        bird.position.add(LineareAlgebra.mult(bird.orientation, bird.speed));
    }
}
