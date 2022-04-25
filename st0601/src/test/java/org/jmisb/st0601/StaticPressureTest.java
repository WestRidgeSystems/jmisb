package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StaticPressureTest {
    @Test
    public void testConstructFromValue() {
        StaticPressure pressure = new StaticPressure(0.0);
        byte[] bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[] {0x00, 0x00});
        Assert.assertEquals(pressure.getMillibars(), 0.0);

        pressure = new StaticPressure(5000.0);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(pressure.getMillibars(), 5000.0);

        // Example from standard
        pressure = new StaticPressure(3725.18502);
        bytes = pressure.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xBE, (byte) 0xBA});
        Assert.assertEquals(pressure.getMillibars(), 3725.18502);
        Assert.assertEquals("3725.19mB", pressure.getDisplayableValue());

        Assert.assertEquals(pressure.getDisplayName(), "Static Pressure");
    }

    @Test
    public void testConstructFromEncoded() {
        byte[] bytes = new byte[] {0x00, 0x00};
        StaticPressure pressure = new StaticPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        pressure = new StaticPressure(bytes);
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        // Some random byte arrays
        bytes = new byte[] {(byte) 0xa8, (byte) 0x73};
        pressure = new StaticPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[] {(byte) 0x34, (byte) 0x9d};
        pressure = new StaticPressure(bytes);
        Assert.assertEquals(pressure.getBytes(), bytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x00};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.StaticPressure, bytes);
        Assert.assertTrue(v instanceof StaticPressure);
        StaticPressure pressure = (StaticPressure) v;
        Assert.assertEquals(pressure.getMillibars(), 0.0);
        Assert.assertEquals(pressure.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.StaticPressure, bytes);
        Assert.assertTrue(v instanceof StaticPressure);
        pressure = (StaticPressure) v;
        Assert.assertEquals(pressure.getMillibars(), 5000.0);
        Assert.assertEquals(pressure.getBytes(), bytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new StaticPressure(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new StaticPressure(5000.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new StaticPressure(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
