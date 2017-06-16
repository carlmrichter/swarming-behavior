package object;

import behavior.Behavior;
import math.Vektor2D;

public abstract class Fish extends BaseObject {

    public Behavior behavior = null;

    public float mass;
    public float maxSpeed;

    public float speed;


    public Fish(float x, float y) {
        super(x, y);
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void update() {
        if (behavior != null) {
            behavior.update();
        }
    }
}
