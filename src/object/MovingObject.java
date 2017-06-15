package object;

import behavior.Behavior;

public abstract class MovingObject extends BaseObject {

    public float ySpeed;
    public Behavior behavior = null;

    public MovingObject(float x, float y, float ySpeed) {
        super(x, y);
        this.ySpeed = ySpeed;
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
