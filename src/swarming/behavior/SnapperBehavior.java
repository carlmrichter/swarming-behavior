package swarming.behavior;

import swarming.object.SnapperManager;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.Snapper;


public class SnapperBehavior implements Behavior {
    private Snapper snapper;
    private SnapperManager snappers;

    private Vektor2D target;

    private double speed;
    private double rotationSpeed;

    private final double COMFORT_RADIUS;
    private final static double MIN_SWARMSIZE = 6, SWARMRADIUS = 200;
    private final double SEPARATION_STRENGTH = 5;
    private final double COHESION_STRENGTH = 5, COHESION_RADIUS;
    private final double SPEED;


    public SnapperBehavior(Snapper snapper) {
        this.snapper = snapper;
        this.snappers = SnapperManager.getInstance();
        this.SPEED = 1;
        this.speed = SPEED;
        this.rotationSpeed = 1;
        this.COMFORT_RADIUS = 40;
        this.COHESION_RADIUS = this.COMFORT_RADIUS * 1.5;
    }

    public SnapperBehavior(Snapper snapper, double speed, double rotationSpeed, double comfortRadius) {
        this.snapper = snapper;
        this.snappers = SnapperManager.getInstance();
        this.SPEED = speed;
        this.speed = SPEED;
        this.rotationSpeed = rotationSpeed;
        this.COMFORT_RADIUS = comfortRadius;
        this.COHESION_RADIUS = comfortRadius * 1.5;
    }

    @Override
    public void update() {

        target = LineareAlgebra.mult(snapper.orientation, speed);


        target.add(separation());
        target.add(cohesion());

        rotateToDirection(alignment());
        target.normalize();

        adjustSpeed();
        snapper.position.add(LineareAlgebra.mult(snapper.orientation, speed));

    }

    private void adjustSpeed() {

        Vektor2D minimalDistance = null;
        int counter = 1;

        for (int i = 1; i <= snappers.getSnapperCount(); i++) {
            if (i == snapper.id) continue;

            Vektor2D distance = LineareAlgebra.sub(snappers.getSnapper(i).position, snapper.position);


            if (distance.length() < SWARMRADIUS) {
                counter++;
            }


            if (minimalDistance == null) {
                minimalDistance = new Vektor2D(distance);
            } else {
                minimalDistance = LineareAlgebra.length(minimalDistance) < LineareAlgebra.length(distance) ?
                        new Vektor2D(minimalDistance) : new Vektor2D(distance);
            }

        }

        double dist = LineareAlgebra.length(minimalDistance);


        if (snapper.orientation.isNullVector()
                || minimalDistance != null && minimalDistance.isNullVector()
                || minimalDistance == null) {
            return;
        }

        double angle = LineareAlgebra.cosEquation(minimalDistance, snapper.orientation);

        if (counter >= MIN_SWARMSIZE) {
            if (dist < COMFORT_RADIUS && angle > 90) {
                speed = SPEED * 1.7;
            } else {
                speed = SPEED * 1.5;
            }
        } else {
            if (dist < COMFORT_RADIUS && angle > 90) {
                speed = SPEED * 1.2;
            } else {
                speed = SPEED;
            }
        }
    }

    private Vektor2D separation() {
        Vektor2D force = new Vektor2D(0,0);

        for (int i = 1; i <= snappers.getSnapperCount(); i++) {
            if (i == snapper.id) continue;


            Vektor2D distance = LineareAlgebra.sub(snappers.getSnapper(i).position, snapper.position);

            if (LineareAlgebra.length(distance) < COMFORT_RADIUS) {
                force.add(distance);
            }


        }

        force.normalize();
        force.mult(-1 * SEPARATION_STRENGTH);

        if (!force.isNullVector()) {
            rotateToDirection(force);
        }


        return force;
    }

    private Vektor2D alignment() {
        Vektor2D alignment = new Vektor2D(snapper.orientation);

        for (int i = 1; i <= snappers.getSnapperCount(); i++) {
            //if (i == snapper.id) continue;

            Vektor2D distance = LineareAlgebra.sub(snappers.getSnapper(i).position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                alignment.add(snappers.getSnapper(i).orientation);
            }
        }

        alignment.normalize();
        return alignment;
    }

    private Vektor2D cohesion() {

        Vektor2D destination = new Vektor2D(0,0);

        for (int i = 1; i <= snappers.getSnapperCount(); i++) {
            if (i == snapper.id) continue;

            Vektor2D distance = LineareAlgebra.sub(snappers.getSnapper(i).position, snapper.position);

            if (LineareAlgebra.length(distance) < COHESION_RADIUS) {
                destination.add(snappers.getSnapper(i).position);
            }


        }

        Vektor2D force = LineareAlgebra.sub(destination, snapper.position);
        force.normalize();
        force.mult(COHESION_STRENGTH);
        return force;
    }

    private void rotateToDirection(Vektor2D direction) {
        // Richtungsbestimmung (in welche Richtung muss gedreht werden?)
        int rotationDirection = LineareAlgebra.crossProduct(snapper.orientation, direction) > 0 ? 1 : -1;

        // Winkel zwischen der aktuellen Richtung und dem Vektor
        if (snapper.orientation.isNullVector() || direction.isNullVector()) return;
        double angle = Math.round(LineareAlgebra.cosEquation(snapper.orientation, direction));

        if (angle >= rotationSpeed) {

            snapper.orientation.rotate(rotationSpeed * rotationDirection);
        } else {
            snapper.orientation.rotate(angle);
        }
    }
}
