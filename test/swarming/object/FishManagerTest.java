package swarming.object;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import swarming.math.Vektor2D;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;
import static swarming.SwarmingBehavior.HEIGHT;
import static swarming.SwarmingBehavior.WIDTH;


public class FishManagerTest {

    private static final int SNAPPER_COUNT = 500;
    private static final int BARRACUDA_COUNT = 5;
    private static FishManager fishManager;

    @BeforeClass
    public static void setUp() throws Exception {
        fishManager = FishManager.getInstance();
        Random random = ThreadLocalRandom.current();
        for (int i = 1; i <= SNAPPER_COUNT; i++) {
            Snapper snapper = new Snapper(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            snapper.setBehavior(1, 1, 35, 300);
            fishManager.addFish(snapper);
        }

        for (int i = 1; i <= BARRACUDA_COUNT; i++) {
            Barracuda barracuda = new Barracuda(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(359));
            barracuda.setBehavior(2, 2);
            fishManager.addFish(barracuda);
        }
    }

    @Test
    public void addFish() throws Exception {
    }

    @Test
    public void getFish() throws Exception {
    }

    @Test
    public void removeFish() throws Exception {
        System.out.println(fishManager.getFishMap());
        System.out.println(fishManager.getFishCount());
        fishManager.removeFish(4);
        System.out.println(fishManager.getFishCount());

        fishManager.getFishMap().forEach((id, fish) -> System.out.println(id + ", " + fish.id));
    }

}