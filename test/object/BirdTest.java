package object;

import math.LineareAlgebra;
import math.Vektor2D;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BirdTest {

    public Vektor2D orientation;
    public Vektor2D position;


    @Before
    public void setUp() throws Exception {
        this.orientation = new Vektor2D(1,0);
        this.position = new Vektor2D(300, 100);
    }

    @Test
    public void points() {


        Vektor2D point1 = LineareAlgebra.add(position, LineareAlgebra.mult(orientation, 15));
        Assert.assertEquals(315, point1.x, 0.01);
        Assert.assertEquals(100, point1.y, 0.01);



        Vektor2D point2 = LineareAlgebra.sub(position, LineareAlgebra.mult(orientation, 15));
        Vektor2D point3 = new Vektor2D(point2);
        point2.add(LineareAlgebra.mult(new Vektor2D(-orientation.y, orientation.x), 10));
        point3.add(LineareAlgebra.mult(new Vektor2D(orientation.y, -orientation.x), 10));


        Assert.assertEquals(285, point2.x, 0.01);
        Assert.assertEquals(110, point2.y, 0.01);

        Assert.assertEquals(285, point3.x, 0.01);
        Assert.assertEquals(90, point3.y, 0.01);
    }
}