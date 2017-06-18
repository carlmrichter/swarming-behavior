package swarming.object;

import swarming.SwarmingBehavior;
import swarming.math.Vektor2D;

public abstract class BaseObject {
    public int id;
    public Vektor2D position;
    protected static int counter = 0;

    private static final int BOXING = 15;
    public static int
            BORDER_LEFT = -BOXING,
            BORDER_TOP = SwarmingBehavior.HEIGHT + BOXING,
            BORDER_RIGHT = SwarmingBehavior.WIDTH + BOXING,
            BORDER_BOTTOM = -BOXING;

    public BaseObject() {
        this(0, 0);
    }

    public BaseObject(double x, double y) {
        this.position = new Vektor2D(x, y);
        this.id = ++counter;
    }

    public abstract void render();

    protected void transformCoordinates() {
        if (position.x < BORDER_LEFT) {
            position.x = BORDER_RIGHT;
        } else if (position.x > BORDER_RIGHT) {
            position.x = BORDER_LEFT;
        }
        if (position.y < BORDER_BOTTOM) {
            position.y = BORDER_TOP;
        } else if (position.y > BORDER_TOP) {
            position.y = BORDER_BOTTOM;
        }
    }
}
