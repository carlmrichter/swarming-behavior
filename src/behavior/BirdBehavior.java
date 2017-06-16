package behavior;

import math.LineareAlgebra;
import math.Vektor2D;
import object.Bird;
import org.lwjgl.input.Mouse;

import javax.sound.sampled.Line;

public class BirdBehavior implements Behavior {
    private Bird bird;
    private int counter;


    private Vektor2D target;
    private Vektor2D path;

    public BirdBehavior(Bird bird) {
        this.bird = bird;
        counter = 0;
    }

    @Override
    public void update() {

        if (Mouse.isButtonDown(0)) {
            target = new Vektor2D(Mouse.getX(), 600-Mouse.getY());
        }

        flyToTarget();


    }

    private void rotateToTarget(double angle){


        // Richtungsbestimmung (in welche Richtung muss gedreht werden?)
        int rotationDirection = LineareAlgebra.crossProduct(bird.orientation, path) > 0 ? 1 : -1;

        if (angle >= bird.rotationSpeed) {

            bird.orientation.rotate(bird.rotationSpeed * rotationDirection);
        } else {
            bird.orientation.rotate(angle);
        }
    }

    private void flyToTarget() {
        if (target != null) {

            // calculate path
            path = LineareAlgebra.normalize(LineareAlgebra.sub(target, bird.position));


            // Winkel zwischen der aktuellen Richtung und dem Vektor zwischen der aktuellen Position und dem Target
            double angle = Math.round(LineareAlgebra.cosEquation(bird.orientation, path));



            rotateToTarget(angle);
            bird.position.add(LineareAlgebra.mult(bird.orientation, bird.speed));



        }
    }
}
