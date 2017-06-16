import frame.Window;
import object.Snapper;
import org.lwjgl.opengl.Display;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    private Snapper snapper;
    private Snapper snapper2;

    private final static int COUNT = 100;

    private Snapper[] snappers;

    public SwarmingBehavior() {
        super("Swarming behavior", 800, 600);


        initDisplay();
        snapper = new Snapper(300, 100);
        snapper2 = new Snapper(100, 50);

        snappers = new Snapper[COUNT];

        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < COUNT; i++) {

            snappers[i] = new Snapper(random.nextInt(800), random.nextInt(600));
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
                snappers[i].render();
            }

            //snapper.render();
            //snapper2.render();

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
