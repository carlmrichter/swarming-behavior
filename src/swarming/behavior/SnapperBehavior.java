package swarming.behavior;

import swarming.SwarmingBehavior;
import swarming.object.*;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;


public class SnapperBehavior extends FishBehavior {
    private Snapper snapper;

    private final static double MIN_SWARMSIZE = 5, SWARMRADIUS = 150  * SwarmingBehavior.scaling;
    private final double SEPARATION_STRENGTH = 5, COHESION_STRENGTH = 5;
    private final double COMFORT_RADIUS,  COHESION_RADIUS, PANIC_RADIUS;
    private final double SPEED;

    private Vektor2D separationForce, cohesionForce;

    public SnapperBehavior(Snapper snapper, double speed, double rotationSpeed, double comfortRadius, double panicRadius) {
        this.snapper = snapper;
        this.fishManager = FishManager.getInstance();
        this.SPEED = speed;
        this.speed = SPEED;
        this.rotationSpeed = rotationSpeed;
        this.COMFORT_RADIUS = comfortRadius  * SwarmingBehavior.scaling;
        this.COHESION_RADIUS = comfortRadius * 3  * SwarmingBehavior.scaling;
        this.PANIC_RADIUS = panicRadius  * SwarmingBehavior.scaling;
    }

    @Override
    public void update() {

        target = LineareAlgebra.mult(snapper.orientation, speed);
//        target = LineareAlgebra.normalize(snapper.orientation);


        target.add(panic());
        target.add(cohesion());
        target.add(separation());
        target.add(alignment());
        rotate(alignment(), this.snapper);
//        target.normalize();
        //rotate(target, snapper);
        adjustSpeed();
        snapper.position.add(LineareAlgebra.mult(snapper.orientation, speed));

    }

    private Vektor2D panic() {
        Vektor2D panicForce = new Vektor2D();

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);

            if (fish.eaten || (!(fish instanceof Barracuda) && !(fish instanceof Shark))) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, snapper.position);

            if (LineareAlgebra.length(distance) < PANIC_RADIUS) {
                panicForce.add(distance);
//                speed = 2 * SPEED;
            }
        }

        panicForce.normalize();
        panicForce.mult(-20);

        rotationSpeed *= 3;
        rotate(panicForce, this.snapper);
        rotationSpeed /= 3;
        return panicForce;
    }

    private void adjustSpeed() {
        Vektor2D minimalDistance = null;
        int counter = 1;

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);
            if (fish.eaten || i == snapper.id || !(fish instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, snapper.position);

            if (minimalDistance == null) {
                minimalDistance = new Vektor2D(distance);
            } else {
                minimalDistance = LineareAlgebra.length(minimalDistance) < LineareAlgebra.length(distance) ?
                        new Vektor2D(minimalDistance) : new Vektor2D(distance);
            }

            if (snapper.orientation.isNullVector() || distance.isNullVector()) continue;


            if (distance.length() < COHESION_RADIUS) {
                counter++;
            }
        }

        if (snapper.orientation.isNullVector()
                || minimalDistance != null && minimalDistance.isNullVector()
                || minimalDistance == null) {
            return;
        }

        double dist = LineareAlgebra.length(minimalDistance);
        double angle = LineareAlgebra.cosEquation(minimalDistance, snapper.orientation);

        if (counter >= MIN_SWARMSIZE) {
            if (dist < COMFORT_RADIUS && angle < 90) {
                speed = SPEED * 1.3;
            }
            else if (dist > COMFORT_RADIUS && dist < COHESION_RADIUS && angle > 120) {
                speed = SPEED * 1.3;
            }
            else if (dist > COMFORT_RADIUS && dist < COHESION_RADIUS && angle < 60) {
                speed = SPEED * 1.7;
            }
            else {
                speed = SPEED * 1.5;
            }
        } else {
            speed = SPEED;
        }
    }

    private Vektor2D separation() {
        separationForce = new Vektor2D();
        Vektor2D minimalDistance = null;

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);
            if (fish.eaten || i == snapper.id || !(fish instanceof Snapper)) continue;


            Vektor2D distance = LineareAlgebra.sub(fish.position, snapper.position);

            if (minimalDistance == null) {
                minimalDistance = new Vektor2D(distance);
            } else {
                minimalDistance = LineareAlgebra.length(minimalDistance) < LineareAlgebra.length(distance) ?
                        new Vektor2D(minimalDistance) : new Vektor2D(distance);
            }

            if (LineareAlgebra.length(distance) < COMFORT_RADIUS) {
                separationForce.add(distance);
            }


        }

        separationForce.normalize();
        separationForce.mult(-1 * SEPARATION_STRENGTH);

        if (!separationForce.isNullVector() && minimalDistance != null) {
            double dist = minimalDistance.length();

            if (dist < COMFORT_RADIUS) {
                rotate(separationForce, this.snapper);
            }
            else if (dist < COHESION_RADIUS) {
                rotate(cohesionForce, this.snapper);
            }
        }

        return separationForce;
    }

    private Vektor2D alignment() {
        Vektor2D alignment = new Vektor2D(snapper.orientation);

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);
            if (fish.eaten || !(fish instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                alignment.add(fish.orientation);
            }
        }

        alignment.normalize();
        return alignment;
    }

    private Vektor2D cohesion() {

        Vektor2D destination = new Vektor2D();

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);
            if (fish.eaten || i == snapper.id || !(fish instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                destination.add(fish.position);
            }


        }

        cohesionForce = LineareAlgebra.sub(destination, snapper.position);

        cohesionForce.normalize();
        cohesionForce.mult(COHESION_STRENGTH);
        return cohesionForce;
    }
}
