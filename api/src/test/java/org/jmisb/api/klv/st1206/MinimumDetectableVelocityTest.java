package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Minimum Detectable Velocity (ST 1206 Item 17) */
public class MinimumDetectableVelocityTest {
    @Test
    public void testConstructFromValue() {
        MinimumDetectableVelocity uut = new MinimumDetectableVelocity(10.0);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x0a, (byte) 0x0});
        assertEquals(uut.getDisplayName(), "Minimum Detectable Velocity");
        assertEquals(uut.getDisplayableValue(), "10.000m/s");
        assertEquals(uut.getBandwidth(), 10.0, 0.01);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        MinimumDetectableVelocity uut =
                new MinimumDetectableVelocity(new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Minimum Detectable Velocity");
        assertEquals(uut.getDisplayableValue(), "0.004m/s");
        assertEquals(uut.getBandwidth(), 0.004, 0.001);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.MinimumDetectableVelocity,
                        new byte[] {(byte) 0x01, (byte) 0x03});
        assertTrue(value instanceof MinimumDetectableVelocity);
        MinimumDetectableVelocity uut = (MinimumDetectableVelocity) value;
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x03});
        assertEquals(uut.getDisplayName(), "Minimum Detectable Velocity");
        assertEquals(uut.getDisplayableValue(), "1.012m/s");
        assertEquals(uut.getBandwidth(), 1.012, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new MinimumDetectableVelocity(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new MinimumDetectableVelocity(100.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new MinimumDetectableVelocity(new byte[] {0x01, 0x02, 0x03});
    }
}
