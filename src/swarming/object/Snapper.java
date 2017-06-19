package swarming.object;

import swarming.SwarmingBehavior;
import swarming.behavior.SnapperBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Snapper extends Fish {

    public static int snapperCount = 0;
    private static float LENGTH = 15 * SwarmingBehavior.scaling, WIDTH = 10 * SwarmingBehavior.scaling;

    public Snapper(double x, double y, double orientationAngle){
        super(x, y, orientationAngle);
        snapperCount++;
    }

    public void setBehavior(double speed, double rotationSpeed, double comfortRadius, double panicRadius) {
        setBehavior(new SnapperBehavior(this, speed, rotationSpeed, comfortRadius, panicRadius));
    }

    @Override
    public void render() {
        update();
        transformCoordinates();

        point1 = LineareAlgebra.add(position, LineareAlgebra.mult(orientation, LENGTH));
        point2 = LineareAlgebra.sub(position, LineareAlgebra.mult(orientation, LENGTH));
        point3 = new Vektor2D(point2);
        point2.add(LineareAlgebra.mult(new Vektor2D(-orientation.y, orientation.x), WIDTH));
        point3.add(LineareAlgebra.mult(new Vektor2D(orientation.y, -orientation.x), WIDTH));

        glColor3d(1, 1, 0);
        glBegin(GL_TRIANGLES);
            glVertex2d(point1.x, point1.y);
            glVertex2d(point2.x, point2.y);
            glVertex2d(point3.x, point3.y);
        glEnd();
    }
}
