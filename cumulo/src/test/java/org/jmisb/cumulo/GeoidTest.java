package org.jmisb.cumulo;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GeoidTest {

    public static final double EPSILON = 0.00000001;

    @Test
    public void testGetGridOffsetValues() {

        try {
            Geoid geoid = Geoid.getInstance();

            // poles
            Assert.assertEquals(geoid.getOffset(90.0, 0.0), 13.68, EPSILON);
            Assert.assertEquals(geoid.getOffset(-90.0, 0.0), -29.79, EPSILON);

            // some random values
            Assert.assertEquals(geoid.getOffset(89.74, 5.75), 13.89, EPSILON);
            Assert.assertEquals(geoid.getOffset(86.5, 217.25), 11.76, EPSILON);
            Assert.assertEquals(geoid.getOffset(71.25, 8.0), 43.56, EPSILON);
            Assert.assertEquals(geoid.getOffset(54.0, 182.25),  2.35, EPSILON);
            Assert.assertEquals(geoid.getOffset(35.5, 186.0), -12.91, EPSILON);
            Assert.assertEquals(geoid.getOffset(14.75, 213.5), -8.07, EPSILON);
            Assert.assertEquals(geoid.getOffset(-22.25, 221.5), -10.18, EPSILON);
            Assert.assertEquals(geoid.getOffset(-37.5, 13.0), 25.61, EPSILON);
            Assert.assertEquals(geoid.getOffset(-51.75, 157.25), -17.88, EPSILON);
            Assert.assertEquals(geoid.getOffset(-70.5, 346.0), 5.18, EPSILON);
            Assert.assertEquals(geoid.getOffset(-74.5, 22.0), 17.5, EPSILON);
            Assert.assertEquals(geoid.getOffset(-84.5, 306.25), -25.71, EPSILON);
            Assert.assertEquals(geoid.getOffset(-86.75, 328.75), -21.81, EPSILON);
        }
        catch (IOException e) {
            Assert.fail("IO Exception reading geoid file");
        }
    }


    @Test
    public void testGetOffsetValuesSimple() {
        try {
            Geoid geoid = Geoid.getInstance();
            Assert.assertEquals(43.56, geoid.getOffset(71.25, 8.0), EPSILON);
            Assert.assertEquals(2.35, geoid.getOffset(54.0, 182.25), EPSILON);
            Assert.assertEquals(-12.91, geoid.getOffset(35.5, 186.0), EPSILON);
            Assert.assertEquals(-8.07, geoid.getOffset(14.75, 213.5), EPSILON);
            Assert.assertEquals(5.18, geoid.getOffset(-70.5, 346.0), EPSILON);
            Assert.assertEquals(17.5, geoid.getOffset(-74.5, 22.0), EPSILON);
            Assert.assertEquals(-25.71, geoid.getOffset(-84.5, 306.25), EPSILON);
            Assert.assertEquals(-29.79, geoid.getOffset(-90.0, 0.0), EPSILON);
        }
        catch (IOException e) {
            Assert.fail("IO Exception reading geoid file");
        }
    }
}
