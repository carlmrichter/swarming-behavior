package frame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;

public abstract class Window {
   private int width, height;
   private String title;
   
   public Window() {
      this("Window", 640, 480);
   }
   
   public Window(String title, int width, int height) {
      this.setWidth(width);
      this.setHeight(height);
      this.title  = title;
   }
   
   public void initDisplay() {
      try {
         Display.setDisplayMode(new DisplayMode(getWidth(), getHeight()));
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
       initDisplay();
   }
   
   public abstract void renderLoop();
   
   public void start() {
      renderLoop();
      Display.destroy();
      System.exit(0);
   }

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
}

