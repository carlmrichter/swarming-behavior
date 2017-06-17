package swarming;

import org.lwjgl.input.Mouse;
import swarming.frame.Window;
import swarming.object.Snapper;
import swarming.object.SnapperManager;
import org.lwjgl.opengl.Display;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    private Snapper snapper;
    private Snapper snapper2;

    private final static int COUNT = 500;
    public static final int WIDTH = 3200;
    public static final int HEIGHT = 1800;

    private SnapperManager snappers;

    public SwarmingBehavior() {
        super("Swarming behavior", WIDTH, HEIGHT);
        initDisplay();

        snappers = SnapperManager.getInstance();

        Random random = ThreadLocalRandom.current();
        for (int i = 1; i <= COUNT; i++) {
            Snapper snapper = new Snapper(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            snapper.setBehavior(1, 1, 30);
            snappers.addSnapper(snapper);
        }
    }

    @Override
    public void renderLoop() {
        while (!Display.isCloseRequested()) {
            glClearColor(0.1f, 0.2f, 0.3f, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho (0, WIDTH, HEIGHT, 0, 0, 1);
            glMatrixMode(GL_MODELVIEW);
            glDisable(GL_DEPTH_TEST);



            for (int i = 1; i <= snappers.getSnapperCount(); i++) {
                snappers.getSnapper(i).render();
            }


            //handleInputs();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    private void handleInputs() {
        if (Mouse.isButtonDown(0)) {

            int dx = Mouse.getDX();
            int dy = -Mouse.getDY();

            glTranslatef(dx, dy, 0);
            Snapper.BORDER_LEFT -= dx;
            Snapper.BORDER_RIGHT -= dx;
            Snapper.BORDER_TOP -= dy;
            Snapper.BORDER_BOTTOM -= dy;
        }

    }

    public static void main(String[] args){
        new SwarmingBehavior().start();
    }
}
