package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RadarAltimeterTest {
    @Test
    public void testConstructFromValue() {
        // From ST:
        RadarAltimeter alt = new RadarAltimeter(2154.50);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF7, (byte) 0x40});
        Assert.assertEquals(alt.getDisplayableValue(), "2154.5m");
        Assert.assertEquals(alt.getDisplayName(), "Radar Altimeter");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST:
        RadarAltimeter alt = new RadarAltimeter(new byte[] {(byte) 0x05, (byte) 0xF7, (byte) 0x40});
        Assert.assertEquals(alt.getMeters(), 2154.50, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF7, (byte) 0x40});
        Assert.assertEquals(alt.getDisplayableValue(), "2154.5m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x05, (byte) 0xF7, (byte) 0x40};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.RadarAltimeter, bytes);
        Assert.assertTrue(v instanceof RadarAltimeter);
        RadarAltimeter alt = (RadarAltimeter) v;
        Assert.assertEquals(alt.getMeters(), 2154.50, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF7, (byte) 0x40});
        Assert.assertEquals(alt.getDisplayableValue(), "2154.5m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new RadarAltimeter(-900.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new RadarAltimeter(40000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new RadarAltimeter(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
