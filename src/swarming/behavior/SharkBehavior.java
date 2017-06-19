package swarming.behavior;

import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.*;

public class SharkBehavior extends FishBehavior {

    private Shark shark;
    private final double SPEED, SEEK_RADIUS = 300;

    public SharkBehavior(Shark shark, double speed, double rotationSpeed) {
        this.shark = shark;
        this.speed = SPEED = speed;
        this.fishManager = FishManager.getInstance();
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void update() {
        target = LineareAlgebra.mult(shark.orientation, speed);

        target.add(seekForFood());

        rotate(target, this.shark);
        shark.position.add(LineareAlgebra.mult(shark.orientation, speed));
    }


    private Vektor2D seekForFood() {
        Vektor2D seekForce = new Vektor2D();

        for (int i = 1; i < fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);

            if (fish.eaten || (fish instanceof Shark)) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, shark.position);

            double dist = distance.length();

            if (dist < SEEK_RADIUS) {
                double angle = LineareAlgebra.cosEquation(distance, shark.orientation);
                if (angle < 90) {
                    seekForce.add(distance);
                }
                if (dist < 30) {
                    fish.eaten = true;
                    if (fish instanceof Snapper) Snapper.snapperCount--;
                    else if (fish instanceof Barracuda) Barracuda.barracudaCount--;
                }
            }
        }

        seekForce.mult(10);
        return seekForce;
    }
}
