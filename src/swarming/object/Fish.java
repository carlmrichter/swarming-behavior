package swarming.object;

import swarming.behavior.Behavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public abstract class Fish extends BaseObject {

    protected Behavior behavior = null;

    public boolean eaten = false;
    public Vektor2D orientation;
    protected Vektor2D point1, point2, point3;

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

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void setOrientation(double angle) {
        orientation = new Vektor2D(1, 0);
        orientation.rotate(angle);
    }

}
