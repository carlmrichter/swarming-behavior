package swarming.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vektor2DTest {

    public Vektor2D orientation;
    public Vektor2D position;


    @Before
    public void setUp() throws Exception {
        this.orientation = new Vektor2D(1,0);
        this.position = new Vektor2D(300, 100);
    }


    @Test
    public void rotate() throws Exception {

        Vektor2D vektor = new Vektor2D(2,1);
        vektor.rotate(30);

        Assert.assertEquals(1.23, vektor.x, 0.01);
        Assert.assertEquals(1.87, vektor.y, 0.01);

    }

}