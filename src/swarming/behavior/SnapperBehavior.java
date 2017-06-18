package swarming.behavior;

import swarming.object.FishManager;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.Snapper;


public class SnapperBehavior extends FishBehavior {
    private Snapper snapper;

    private final double COMFORT_RADIUS;
    private final static double MIN_SWARMSIZE = 10, SWARMRADIUS = 150;
    private final double SEPARATION_STRENGTH = 10;
    private final double COHESION_STRENGTH = 10, COHESION_RADIUS;
    private final double SPEED;

    private Vektor2D separationForce, cohesionForce;

    public SnapperBehavior(Snapper snapper) {
        this.snapper = snapper;
        this.fishManager = FishManager.getInstance();
        this.SPEED = 1;
        this.speed = SPEED;
        this.rotationSpeed = 1;
        this.COMFORT_RADIUS = 40;
        this.COHESION_RADIUS = this.COMFORT_RADIUS * 4;
    }

    public SnapperBehavior(Snapper snapper, double speed, double rotationSpeed, double comfortRadius) {
        this.snapper = snapper;
        this.fishManager = FishManager.getInstance();
        this.SPEED = speed;
        this.speed = SPEED;
        this.rotationSpeed = rotationSpeed;
        this.COMFORT_RADIUS = comfortRadius;
        this.COHESION_RADIUS = comfortRadius * 4;
    }

    @Override
    public void update() {

        target = LineareAlgebra.mult(snapper.orientation, speed);
//        target = LineareAlgebra.normalize(snapper.orientation);

        target.add(cohesion());
        target.add(separation());
        rotate(alignment());
        target.normalize();

        adjustSpeed();
        snapper.position.add(LineareAlgebra.mult(snapper.orientation, speed));

    }

    private void adjustSpeed() {
        Vektor2D minimalDistance = null;
        int counter = 1;

        for (int i = 1; i <= fishManager.getFishCount(); i++) {

            if (i == snapper.id || !(fishManager.getFish(i) instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fishManager.getFish(i).position, snapper.position);

            if (minimalDistance == null) {
                minimalDistance = new Vektor2D(distance);
            } else {
                minimalDistance = LineareAlgebra.length(minimalDistance) < LineareAlgebra.length(distance) ?
                        new Vektor2D(minimalDistance) : new Vektor2D(distance);
            }

            if (snapper.orientation.isNullVector() || distance.isNullVector()) continue;


            if (distance.length() < SWARMRADIUS) {
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
            else if (dist > COMFORT_RADIUS && dist < COHESION_RADIUS && angle < 90) {
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
            if (i == snapper.id || !(fishManager.getFish(i) instanceof Snapper)) continue;


            Vektor2D distance = LineareAlgebra.sub(fishManager.getFish(i).position, snapper.position);

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
                rotate(separationForce);
            }
            else if (dist < COHESION_RADIUS) {
                rotate(cohesionForce);
            }
        }

        return separationForce;
    }

    private Vektor2D alignment() {
        Vektor2D alignment = new Vektor2D(snapper.orientation);

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            if (!(fishManager.getFish(i) instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fishManager.getFish(i).position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                alignment.add(fishManager.getFish(i).orientation);
            }
        }

        alignment.normalize();
        return alignment;
    }

    private Vektor2D cohesion() {

        Vektor2D destination = new Vektor2D();

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            if (i == snapper.id || !(fishManager.getFish(i) instanceof Snapper)) continue;

            Vektor2D distance = LineareAlgebra.sub(fishManager.getFish(i).position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                destination.add(fishManager.getFish(i).position);
            }


        }

        cohesionForce = LineareAlgebra.sub(destination, snapper.position);

        cohesionForce.normalize();
        cohesionForce.mult(COHESION_STRENGTH);
        return cohesionForce;
    }

    private void rotate(Vektor2D direction) {
        // Winkel zwischen der aktuellen Richtung und dem Vektor
        if (snapper.orientation.isNullVector() || direction.isNullVector()) return;
        double angle = Math.round(LineareAlgebra.cosEquation(snapper.orientation, direction));

        if (angle == 0) return;

        // Richtungsbestimmung (in welche Richtung muss gedreht werden?)
        if (LineareAlgebra.crossProduct(snapper.orientation, direction) < 0) {
            angle *= -1;
        }

        rotateByAngle(angle);
    }

    private void rotateByAngle(double angle) {
        if (angle == 0) return;

        if (Math.abs(angle) >= rotationSpeed) {
            snapper.orientation.rotate(rotationSpeed * (angle < 0 ? -1 : 1));
        } else {
            snapper.orientation.rotate(angle);
        }
    }
}
