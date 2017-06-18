package swarming.behavior;

import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.Fish;
import swarming.object.FishManager;

abstract class FishBehavior implements Behavior {

    protected FishManager fishManager;
    protected double speed;
    protected double rotationSpeed;
    protected Vektor2D target;

    protected void rotate(Vektor2D direction, Fish fish) {
        // Winkel zwischen der aktuellen Richtung und dem Vektor
        if (fish.orientation.isNullVector() || direction.isNullVector()) return;
        double angle = Math.round(LineareAlgebra.cosEquation(fish.orientation, direction));

        if (angle == 0) return;

        // Richtungsbestimmung (in welche Richtung muss gedreht werden?)
        if (LineareAlgebra.crossProduct(fish.orientation, direction) < 0) {
            angle *= -1;
        }

        rotateByAngle(angle, fish);
    }

    protected void rotateByAngle(double angle, Fish fish) {
        if (angle == 0) return;

        if (Math.abs(angle) >= rotationSpeed) {
            fish.orientation.rotate(rotationSpeed * (angle < 0 ? -1 : 1));
        } else {
            fish.orientation.rotate(angle);
        }
    }
}
