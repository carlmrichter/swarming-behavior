package swarming.object;

import swarming.behavior.Behavior;
import swarming.math.Vektor2D;

public abstract class Fish extends BaseObject {

    public Behavior behavior = null;

    public Vektor2D orientation;



    public Fish(double x, double y) {
        super(x, y);
        this.orientation = new Vektor2D(1,0);
    }


    public void update() {
        if (behavior != null) {
            behavior.update();
        }
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void setOrientation(double angle) {
        orientation = new Vektor2D(1, 0);
        orientation.rotate(angle);
    }


}
