package swarming.object;

import swarming.SwarmingBehavior;
import swarming.behavior.BarracudaBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Barracuda extends Fish {

    private static float LENGTH = 25 * SwarmingBehavior.scaling, WIDTH = 15 * SwarmingBehavior.scaling;
    public static int barracudaCount = 0;

    public Barracuda(double x, double y, double orientationAngle) {
        super(x, y, orientationAngle);
        barracudaCount++;
    }

    public void setBehavior(double speed, double rotationSpeed) {
        setBehavior(new BarracudaBehavior(this, speed, rotationSpeed));
    }

    @Override
    public void render() {
        update();
        transformCoordinates();

        Vektor2D point1 = LineareAlgebra.add(position, LineareAlgebra.mult(orientation, LENGTH));
        Vektor2D point2 = LineareAlgebra.sub(position, LineareAlgebra.mult(orientation, LENGTH));
        Vektor2D point3 = new Vektor2D(point2);
        point2.add(LineareAlgebra.mult(new Vektor2D(-orientation.y, orientation.x), WIDTH));
        point3.add(LineareAlgebra.mult(new Vektor2D(orientation.y, -orientation.x), WIDTH));

        glColor3d(1, 0.5, 1);
        glBegin(GL_TRIANGLES);
            glVertex2d(point1.x, point1.y);
            glVertex2d(point2.x, point2.y);
            glVertex2d(point3.x, point3.y);
        glEnd();
    }
}
