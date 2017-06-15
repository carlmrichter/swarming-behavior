package object;

public abstract class BaseObject {
    public int id;
    public float xPos, yPos;

    public BaseObject() {
        this(0, 0);
    }

    public BaseObject(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public abstract void render();
}
