package swarming;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import swarming.behavior.BarracudaBehavior;
import swarming.frame.Window;
import swarming.object.*;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    public static int WIDTH = 1600, HEIGHT = 900;
    public static float scaling;

    private FishManager fishManager;

    /** time at last frame */
    private long lastFrame;

    /** frames per second */
    private int fps;

    /** last fps time */
    private long lastFPS;

    public SwarmingBehavior(int width, int height, int displayMode, int snapperCount, int barracudaCount, int sharkCount) {
        super("Swarming behavior", width, height);
        initDisplay(displayMode);

        WIDTH = getWidth();
        HEIGHT = getHeight();

        float dpi = Toolkit.getDefaultToolkit().getScreenResolution();
        scaling = dpi / 160;

        System.out.println("DPI: " + dpi + " Scaling: " + scaling);
        fishManager = FishManager.getInstance();

        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < snapperCount; i++) {
            Snapper snapper = new Snapper(random.nextInt(getWidth()), random.nextInt(getHeight()), random.nextInt(359));
            snapper.setBehavior(1, 1, 35, 300);
            fishManager.addFish(snapper);
        }

        for (int i = 0; i < barracudaCount; i++) {
            Barracuda barracuda = new Barracuda(random.nextInt(getWidth()), random.nextInt(getHeight()), random.nextInt(359));
            barracuda.setBehavior(2, 1);
            fishManager.addFish(barracuda);
        }

        for (int i = 0; i < sharkCount; i++) {
            Shark shark = new Shark(random.nextInt(getWidth()), random.nextInt(getHeight()), random.nextInt(359));
            shark.setBehavior(3, 1);
            fishManager.addFish(shark);
        }
    }

    @Override
    public void renderLoop() {


        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho (0, getWidth(), getHeight(), 0, 0, 1);
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


            handleInputs();

            updateFPS();
            Display.update();
            Display.sync(65);
        }

        Display.destroy();
        fishManager.removeAll();
    }

    private void handleInputs() {
        if (Mouse.isButtonDown(0)) {
            int dx = Mouse.getDX();
            int dy = -Mouse.getDY();

            glTranslatef(dx, dy, 0);
            BaseObject.BORDER_LEFT -= dx;
            BaseObject.BORDER_RIGHT -= dx;
            BaseObject.BORDER_TOP -= dy;
            BaseObject.BORDER_BOTTOM -= dy;
        }
    }

    public static void main(String[] args){
        if (args.length != 7) {
            new SwarmingBehavior(1600, 900, NORMAL, 300, 2, 1).start();
        } else {
            int width = Integer.valueOf(args[1]);
            int height = Integer.valueOf(args[2]);
            int windowMode = Integer.valueOf(args[3]);
            int snapperCount = Integer.valueOf(args[4]);
            int barracudaCount = Integer.valueOf(args[5]);
            int sharkCount = Integer.valueOf(args[6]);

            new SwarmingBehavior(width, height, windowMode, snapperCount, barracudaCount, sharkCount).start();
        }
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
                    + " | Barracudas: " + Barracuda.barracudaCount + " | Sharks: " + Shark.sharkCount);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
}
