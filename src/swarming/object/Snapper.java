package swarming.object;

import swarming.behavior.Behavior;
import swarming.behavior.SnapperBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class Snapper extends Fish {

    public Snapper(double x, double y, double orientationAngle){
        super(x, y, orientationAngle);
    }

    @Override
    public void setBehavior(Behavior behavior) {
        this.behavior = new SnapperBehavior(this);
    }

    public void setBehavior(double speed, double rotationSpeed, double comfortRadius) {
        this.behavior = new SnapperBehavior(this, speed, rotationSpeed, comfortRadius);
    }

    @Override
    public void render() {
        update();
        transformCoordinates();

        Vektor2D point1 = LineareAlgebra.add(position, LineareAlgebra.mult(orientation, 15));
        Vektor2D point2 = LineareAlgebra.sub(position, LineareAlgebra.mult(orientation, 15));
        Vektor2D point3 = new Vektor2D(point2);
        point2.add(LineareAlgebra.mult(new Vektor2D(-orientation.y, orientation.x), 10));
        point3.add(LineareAlgebra.mult(new Vektor2D(orientation.y, -orientation.x), 10));

        glColor3d(1, 1, 0);
        glBegin(GL_TRIANGLES);
            glVertex2d(point1.x, point1.y);
            glVertex2d(point2.x, point2.y);
            glVertex2d(point3.x, point3.y);
        glEnd();
    }
}
