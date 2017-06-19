package swarming;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import swarming.behavior.BarracudaBehavior;
import swarming.frame.Window;
import swarming.object.*;
import org.lwjgl.opengl.Display;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    private final static int SNAPPER_COUNT = 300, BARRACUDA_COUNT = 2, SHARK_COUNT = 1;
    public static final int WIDTH = 1600, HEIGHT = 900;

    private FishManager fishManager;

    /** time at last frame */
    private long lastFrame;

    /** frames per second */
    private int fps;

    /** last fps time */
    private long lastFPS;

    public SwarmingBehavior() {
        super("Swarming behavior", WIDTH, HEIGHT);
        initDisplay();

        fishManager = FishManager.getInstance();

        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < SNAPPER_COUNT; i++) {
            Snapper snapper = new Snapper(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            snapper.setBehavior(1, 1, 35, 300);
            fishManager.addFish(snapper);
        }

        for (int i = 0; i < BARRACUDA_COUNT; i++) {
            Barracuda barracuda = new Barracuda(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            barracuda.setBehavior(2, 1);
            fishManager.addFish(barracuda);
        }

        for (int i = 0; i < SHARK_COUNT; i++) {
            Shark shark = new Shark(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            shark.setBehavior(3, 1);
            fishManager.addFish(shark);
        }
    }

    @Override
    public void renderLoop() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho (0, WIDTH, HEIGHT, 0, 0, 1);
        glMatrixMode(GL_MODELVIEW);
        glDisable(GL_DEPTH_TEST);

        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {

            glClearColor(0.1f, 0.2f, 0.3f, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            fishManager.getFishMap().forEach((id, fish) -> {
                if (!fish.eaten) {
                    fish.render();
                }
            });

//            for (int i = 1; i <= fishManager.getFishCount(); i++) {
//
//                Fish fish = fishManager.getFish(i);
//                if (fish == null) continue;
//                fish.render();
//
//            }

            handleInputs();


            updateFPS();
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

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps + " | Snappers: " + Snapper.snapperCount
                    + " | Barracudas: " + Barracuda.barracudaCount + " | Sharks: " + SHARK_COUNT);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
}
