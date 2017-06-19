package swarming.behavior;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import swarming.SwarmingBehavior;
import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.*;

public class SharkBehavior extends FishBehavior {

    private Shark shark;
    private final double SPEED, SEEK_RADIUS = 300;

    public SharkBehavior(Shark shark, double speed, double rotationSpeed) {
        this.shark = shark;
        this.speed = SPEED = speed;
        this.fishManager = FishManager.getInstance();
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void update() {

        handleInputs();
        seekForFood();
        shark.position.add(LineareAlgebra.mult(shark.orientation, speed));
    }

    private void handleInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            rotateByAngle(-1, this.shark);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            rotateByAngle(1, this.shark);
        }

    }

    private void seekForFood() {

        for (int i = 1; i < fishManager.getFishCount(); i++) {
            Fish fish = fishManager.getFish(i);

            if (fish.eaten || (fish instanceof Shark)) continue;

            Vektor2D distance = LineareAlgebra.sub(fish.position, shark.position);

            double dist = distance.length();

            if (dist < SEEK_RADIUS) {
                if (dist < 30) {
                    fish.eaten = true;
                    if (fish instanceof Snapper) Snapper.snapperCount--;
                    else if (fish instanceof Barracuda) Barracuda.barracudaCount--;
                }
            }
        }
    }
}
