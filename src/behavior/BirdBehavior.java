package behavior;

import math.LineareAlgebra;
import math.Vektor2D;
import object.Bird;
import org.lwjgl.input.Mouse;

import javax.sound.sampled.Line;

public class BirdBehavior implements Behavior {
    private Bird bird;
    private int counter;

    private short rotationDirection = 1;

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
            path = LineareAlgebra.normalize(LineareAlgebra.sub(target, bird.position));


        }

        if (target != null) {

            double angle = Math.round(LineareAlgebra.cosEquation(bird.orientation, path));

            double orient = LineareAlgebra.crossProduct(bird.orientation, path);

            //System.out.println("angle: " + angle + "\torient: " + orient);

            if (angle != 0) {
                rotateToTarget(angle, orient);
            }

        }


    }

    private void rotateToTarget(double angle, double orient){


        if (orient < 0) {
            rotationDirection = -1;
        } else {
            rotationDirection = 1;
        }

        //System.out.println("Angle: " + angle + "\trotationDirection: " + rotationDirection);


        if (angle >= bird.rotationSpeed) {

            bird.orientation.rotate(bird.rotationSpeed * rotationDirection);
        } else {
            bird.orientation.rotate(angle);
        }


    }
}
