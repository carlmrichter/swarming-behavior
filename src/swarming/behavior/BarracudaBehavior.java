package swarming.behavior;

import swarming.object.Barracuda;
import swarming.object.FishManager;

public class BarracudaBehavior extends FishBehavior {

    private Barracuda barracuda;

    private final double SPEED;


    public BarracudaBehavior(Barracuda barracuda) {
        this.barracuda = barracuda;
        this.fishManager = FishManager.getInstance();
        this.SPEED = 2;
        this.speed = SPEED;
        this.rotationSpeed = 2;
    }

    public BarracudaBehavior(Barracuda barracuda, double speed, double rotationSpeed) {
        this.barracuda = barracuda;
        this.SPEED = speed;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void update() {

    }
}
