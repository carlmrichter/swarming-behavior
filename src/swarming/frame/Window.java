package swarming.frame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

public abstract class Window {
   private int width, height;
   private String title;
   public static final int NORMAL = 0, MAXIMIZED = 1, FULLSCREEN = 2;
   
   public Window() {
      this("Window", 640, 480);
   }
   
   public Window(String title, int width, int height) {
      this.setWidth(width);
      this.setHeight(height);
      this.title  = title;
   }
   
   public void initDisplay(int windowMode) {
      try {
        if (windowMode < 0 || windowMode > 2) windowMode = NORMAL;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Screen Size: " + (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight());
        switch (windowMode) {
            case NORMAL:
                Display.setDisplayMode(new DisplayMode(width, height));
                break;
            case MAXIMIZED:
                setWidth((int)screenSize.getWidth());
                setHeight((int)screenSize.getHeight());
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                break;
            case FULLSCREEN:
                setWidth((int)screenSize.getWidth());
                setHeight((int)screenSize.getHeight());
                Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
                break;
        }
        Display.setTitle(title);
        Display.create();
      } catch (LWJGLException e) {
         e.printStackTrace();
      }      
   }

   public void initDisplay(Canvas canvas) {
       try {
           Display.setParent(canvas);
       } catch (LWJGLException e) {
           e.printStackTrace();
       }
       initDisplay(NORMAL);
   }
   
   public abstract void renderLoop();


   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

    public void start() {
       renderLoop();
       Display.destroy();

    }
}

