package swarming.object;

import swarming.math.Vektor2D;

public abstract class BaseObject {
    public int id;
    public Vektor2D position;

    public BaseObject() {
        this(0, 0);
    }

    public BaseObject(double x, double y) {
        this.position = new Vektor2D(x, y);
    }

    public abstract void render();
}
