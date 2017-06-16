package swarming;

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

    private final static int COUNT = 20000;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 900;

    private SnapperManager snappers;

    public SwarmingBehavior() {
        super("Swarming behavior", WIDTH, HEIGHT);


        initDisplay();
//        snapper = new Snapper(300, 100);
//        snapper2 = new Snapper(100, 50);

        snappers = SnapperManager.getInstance();

        Random random = ThreadLocalRandom.current();
        for (int i = 1; i <= COUNT; i++) {

            snappers.addSnapper(new Snapper(random.nextInt(WIDTH),random.nextInt(HEIGHT), random.nextFloat() * 0.5f + 1, random.nextFloat()+1.2,random.nextInt(359)));
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

            //snapper.render();
            //snapper2.render();

            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }


    public static void main(String[] args){
        new SwarmingBehavior().start();
    }
}
