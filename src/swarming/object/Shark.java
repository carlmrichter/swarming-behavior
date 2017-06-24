package swarming.object;


import swarming.SwarmingBehavior;
import swarming.behavior.SharkBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;

import static org.lwjgl.opengl.GL11.*;

public class Shark extends Fish {

    public static int sharkCount = 0;
    private static float LENGTH = 25 * SwarmingBehavior.scaling, WIDTH = 15 * SwarmingBehavior.scaling;

    public Shark(double x, double y, double orientationAngle) {
        super(x, y, orientationAngle);
        sharkCount++;
    }

    public void setBehavior(double speed, double rotationSpeed) {
        setBehavior(new SharkBehavior(this, speed, rotationSpeed));
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

        glColor3d(1, 1, 1);
        glBegin(GL_TRIANGLES);
        glVertex2d(point1.x, point1.y);
        glVertex2d(point2.x, point2.y);
        glVertex2d(point3.x, point3.y);
        glEnd();
    }
}
