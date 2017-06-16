import frame.Window;
import object.Bird;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.lwjgl.opengl.GL11.*;

public class SwarmingBehavior extends Window {

    private Label labelMouse;
    private Bird bird;
    private Bird bird2;

    public SwarmingBehavior() {
        super("Swarming behavior", 800, 600);

//        Frame frame = new Frame("test");
//        frame.setVisible(true);
//        frame.setSize(800, 650);
//        frame.setLayout(new BorderLayout());
//        Canvas canvas = new Canvas();
//
//        Panel panel = new Panel();
//        panel.setLayout(new FlowLayout());
//        labelMouse = new Label("X: 000, Y: 000");
//        panel.add(labelMouse);
//        frame.add(panel, BorderLayout.SOUTH);
//        frame.add(canvas);
//        frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//
//        initDisplay(canvas);

        initDisplay();
        bird = new Bird(300, 100);
        bird2 = new Bird(100, 50);
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



            bird.render();
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
