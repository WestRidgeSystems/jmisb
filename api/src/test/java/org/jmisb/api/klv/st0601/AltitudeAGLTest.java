package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AltitudeAGLTest {
    @Test
    public void testConstructFromValue() {
        // From ST:
        AltitudeAGL alt = new AltitudeAGL(2150.0);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF5, (byte) 0x00});
        Assert.assertEquals(alt.getDisplayableValue(), "2150.0m");
        Assert.assertEquals(alt.getDisplayName(), "Altitude AGL");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST:
        AltitudeAGL alt = new AltitudeAGL(new byte[] {(byte) 0x05, (byte) 0xF5, (byte) 0x00});
        Assert.assertEquals(alt.getMeters(), 2150.0, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF5, (byte) 0x00});
        Assert.assertEquals(alt.getDisplayableValue(), "2150.0m");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x05, (byte) 0xF5, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.AltitudeAgl, bytes);
        Assert.assertTrue(v instanceof AltitudeAGL);
        AltitudeAGL alt = (AltitudeAGL) v;
        Assert.assertEquals(alt.getMeters(), 2150.0, 0.01);
        Assert.assertEquals(alt.getBytes(), new byte[] {(byte) 0x05, (byte) 0xF5, (byte) 0x00});
        Assert.assertEquals(alt.getDisplayableValue(), "2150.0m");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new AltitudeAGL(-900.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new AltitudeAGL(40000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new AltitudeAGL(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
