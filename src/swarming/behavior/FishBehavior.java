package swarming.behavior;

import swarming.math.Vektor2D;
import swarming.object.FishManager;

abstract class FishBehavior implements Behavior {

    protected FishManager fishManager;
    protected double speed;
    protected double rotationSpeed;
    protected Vektor2D target;

}
