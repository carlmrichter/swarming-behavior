package swarming.behavior;

import swarming.math.LineareAlgebra;
import swarming.math.Vektor2D;
import swarming.object.Barracuda;
import swarming.object.FishManager;
import swarming.object.Snapper;

public class BarracudaBehavior extends FishBehavior {

    private Barracuda barracuda;

    private final double SPEED;
    private final double SEEK_RADIUS = 200;


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

        target = LineareAlgebra.mult(barracuda.orientation, speed);

        target.add(seekForSnapper());

        rotate(target, this.barracuda);
        barracuda.position.add(LineareAlgebra.mult(barracuda.orientation, speed));
    }


    private Vektor2D seekForSnapper() {
        Vektor2D seekForce = new Vektor2D();

        for (int i = 1; i <= fishManager.getFishCount(); i++) {
            if (!(fishManager.getFish(i) instanceof Snapper)) continue;


            Vektor2D distance = LineareAlgebra.sub(fishManager.getFish(i).position, barracuda.position);

            double angle = LineareAlgebra.cosEquation(distance, barracuda.orientation);
            double dist = LineareAlgebra.length(distance);
            if (/*dist < SEEK_RADIUS &&*/ angle < 90) {
                seekForce.add(distance);
            }
            if (dist < 10) {
                fishManager.removeFish(((Snapper) fishManager.getFish(i)).id);
            }

        }

        seekForce.mult(10);
        return seekForce;
    }
}
