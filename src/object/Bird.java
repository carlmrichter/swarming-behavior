package object;


import behavior.BirdBehavior;
import math.LineareAlgebra;
import math.Vektor2D;
import org.lwjgl.input.Mouse;

import javax.sound.sampled.Line;

import static math.LineareAlgebra.mult;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Bird extends PhysicsObject {

    public Vektor2D orientation;
    public Vektor2D position;

    public Bird(float x, float y){
        super(x, y);
        this.behavior = new BirdBehavior(this);
        this.speed = 1;
        this.orientation = new Vektor2D(1,0);
        this.position = new Vektor2D(x, y);
    }



    @Override
    public void render() {
        behavior.update();

        glColor3d(1, 1, 0);


//        glTranslatef(xPos, yPos, 0);
//        glRotatef(1, 0, 0, 1);
//        glTranslatef(-xPos, -yPos, 0);

        orientation.rotate(1);

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
