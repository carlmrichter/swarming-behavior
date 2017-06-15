package object;


import behavior.BirdBehavior;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Bird extends PhysicsObject {

    private BirdBehavior behavior;

    public Bird(float x, float y){
        super(x, y);
        this.behavior = new BirdBehavior(this);
        this.speed = 1;
    }


    @Override
    public void render() {
        behavior.update();


        glColor3d(1, 1, 0);
        glBegin(GL_TRIANGLES);

            glVertex2f(xPos, yPos + 10);
            glVertex2f(xPos, yPos - 10);
            glVertex2f(xPos + 30, yPos);

        glEnd();


        glTranslatef(xPos, yPos, 0);
        glRotatef(1, 0, 0, 1);
        glTranslatef(-xPos, -yPos, 0);
    }
}
