package swarming.behavior;

import object.SnapperManager;
import swarming.SwarmingBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.Snapper;
import org.lwjgl.input.Mouse;


public class SnapperBehavior implements Behavior {
    private Snapper snapper;
    private SnapperManager snappers;

    private Vektor2D target;
    private Vektor2D path;


    private double comfortRadius;

    public SnapperBehavior(Snapper snapper) {
        this.snapper = snapper;
        this.snappers = SnapperManager.getInstance();
    }

    @Override
    public void update() {

        if (Mouse.isButtonDown(0)) {
            target = new Vektor2D(Mouse.getX(), SwarmingBehavior.HEIGHT-Mouse.getY());
        }

        swimToTarget();
        checkCollision();

    }

    private void rotateToTarget(double angle){


        // Richtungsbestimmung (in welche Richtung muss gedreht werden?)
        int rotationDirection = LineareAlgebra.crossProduct(snapper.orientation, path) > 0 ? 1 : -1;

        if (angle >= snapper.rotationSpeed) {

            snapper.orientation.rotate(snapper.rotationSpeed * rotationDirection);
        } else {
            snapper.orientation.rotate(angle);
        }
    }

    private void swimToTarget() {
        if (target != null) {

            // calculate path
            path = LineareAlgebra.normalize(LineareAlgebra.sub(target, snapper.position));


            // Winkel zwischen der aktuellen Richtung und dem Vektor zwischen der aktuellen Position und dem Target
            double angle = Math.round(LineareAlgebra.cosEquation(snapper.orientation, path));



            rotateToTarget(angle);
            snapper.position.add(LineareAlgebra.mult(snapper.orientation, snapper.speed));



        }
    }

    private void checkCollision() {
        for (int i = 1; i <= snappers.getSnapperCount(); i++) {

        }
    }
}
