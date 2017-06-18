package swarming.object;

import swarming.behavior.Behavior;
import swarming.math.Vektor2D;

public abstract class Fish extends BaseObject {

    protected Behavior behavior = null;

    public Vektor2D orientation;

    public Fish(double x, double y) {
        super(x, y);
        this.orientation = new Vektor2D(1,0);
    }

    public Fish(double x, double y, double orientationAngle) {
        super(x, y);
        setOrientation(orientationAngle);
    }

    public void update() {
        if (behavior != null) {
            behavior.update();
        }
    }

    public abstract void setBehavior(Behavior behavior);

    public void setOrientation(double angle) {
        orientation = new Vektor2D(1, 0);
        orientation.rotate(angle);
    }


}
