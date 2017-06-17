package swarming.object;

import swarming.SwarmingBehavior;
import swarming.behavior.SnapperBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class Snapper extends Fish {

    private static int counter = 0;

    public static int
            BOXING = 15,
            BORDER_LEFT = -BOXING,
            BORDER_TOP = SwarmingBehavior.HEIGHT + BOXING,
            BORDER_RIGHT = SwarmingBehavior.WIDTH + BOXING,
            BORDER_BOTTOM = -BOXING;

    public Snapper(double x, double y){
        super(x, y);
        this.id = ++counter;
    }

    public Snapper(double x, double y, double orientationAngle){
        this(x,y);
        setOrientation(orientationAngle);
    }


    public void setBehavior(double speed, double rotationSpeed, double comfortRadius) {
        this.behavior = new SnapperBehavior(this, speed, rotationSpeed, comfortRadius);
    }

    @Override
    public void render() {
        update();

        glColor3d(1, 1, 0);


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

        Vektor2D point1 = LineareAlgebra.add(position, LineareAlgebra.mult(orientation, 15));
        Vektor2D point2 = LineareAlgebra.sub(position, LineareAlgebra.mult(orientation, 15));
        Vektor2D point3 = new Vektor2D(point2);
        point2.add(LineareAlgebra.mult(new Vektor2D(-orientation.y, orientation.x), 10));
        point3.add(LineareAlgebra.mult(new Vektor2D(orientation.y, -orientation.x), 10));



        glBegin(GL_TRIANGLES);

            glVertex2d(point1.x, point1.y);
            glVertex2d(point2.x, point2.y);
            glVertex2d(point3.x, point3.y);

        glEnd();

    }
}
