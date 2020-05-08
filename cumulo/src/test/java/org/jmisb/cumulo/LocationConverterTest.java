package org.jmisb.cumulo;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LocationConverterTest {
    @Test
    public void testNorthWestern() {

        final double[] boston = {0.739325922, -1.2401834778, 20.0};
        final double[] ecefExpected = {1532266.0, -4464518.0, 4275271.0};

        final double[] ecef = LocationConverter.geoToEcef(boston);
        final double[] geo = LocationConverter.ecefToGeo(ecef);

        Assert.assertEquals(boston, geo, 0.000001);
        Assert.assertEquals(ecef, ecefExpected, 0.5);
    }
}
