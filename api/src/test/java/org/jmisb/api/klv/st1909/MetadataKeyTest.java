package org.jmisb.api.klv.st1909;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for the ST1909 MetadataKey implementation. */
public class MetadataKeyTest {

    public MetadataKeyTest() {}

    @Test
    public void testToString() {
        assertEquals(MetadataKey.AzAngle.toString(), "Azimuth Angle");
    }
}
