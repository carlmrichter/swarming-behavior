import frame.Window;
import object.Bird;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    private Bird bird;
    private Bird bird2;

    private final static int COUNT = 100;

    private Bird[] birds;

    public SwarmingBehavior() {
        super("Swarming behavior", 800, 600);


        initDisplay();
        bird = new Bird(300, 100);
        bird2 = new Bird(100, 50);

        birds = new Bird[COUNT];

        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < COUNT; i++) {

            birds[i] = new Bird(random.nextInt(800), random.nextInt(600));
        }
    }

    @Override
    public void renderLoop() {
        while (!Display.isCloseRequested()) {
            glClearColor(0.1f, 0.2f, 0.3f, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho (0, 800, 600, 0, 0, 1);
            glMatrixMode(GL_MODELVIEW);
            glDisable(GL_DEPTH_TEST);



            for (int i = 0; i < COUNT; i++) {
                birds[i].render();
            }

            //bird.render();
            //bird2.render();

            //labelMouse.setText("X: " + Mouse.getX() + " , Y: " + Mouse.getY());
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }


    public static void main(String[] args){
        new SwarmingBehavior().start();
    }
}
