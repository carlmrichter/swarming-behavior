package object;


import behavior.SnapperBehavior;
import math.LineareAlgebra;
import math.Vektor2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class Snapper extends Fish {

    public Vektor2D orientation;
    public Vektor2D position;
    public double rotationSpeed;


    public Snapper(float x, float y){
        super(x, y);
        this.behavior = new SnapperBehavior(this);
        this.speed = 2;
        this.rotationSpeed = 2;
        this.orientation = new Vektor2D(1,0);
        this.position = new Vektor2D(x, y);
    }

    public Snapper(float x, float y, float speed, double rotationSpeed, Vektor2D orientation, Vektor2D position){
        super(x,y);
        this.behavior = new SnapperBehavior(this);
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.orientation = orientation;
        this.position = position;
    }



    @Override
    public void render() {
        behavior.update();

        glColor3d(1, 1, 0);


//        glTranslatef(xPos, yPos, 0);
//        glRotatef(1, 0, 0, 1);
//        glTranslatef(-xPos, -yPos, 0);



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
