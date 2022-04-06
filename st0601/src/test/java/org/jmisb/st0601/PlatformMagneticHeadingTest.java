package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformMagneticHeadingTest {
    @Test
    public void testConstructFromValue() {
        PlatformMagneticHeading angle = new PlatformMagneticHeading(0.0);
        byte[] bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[] {0x00, 0x00});
        Assert.assertEquals(angle.getDegrees(), 0.0);

        angle = new PlatformMagneticHeading(360.0);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xff, (byte) 0xff});
        Assert.assertEquals(angle.getDegrees(), 360.0);

        // Example from standard
        angle = new PlatformMagneticHeading(311.868162);
        bytes = angle.getBytes();
        Assert.assertEquals(bytes, new byte[] {(byte) 0xDD, (byte) 0xC5});
        Assert.assertEquals(angle.getDegrees(), 311.868162);
        Assert.assertEquals("311.8682\u00B0", angle.getDisplayableValue());

        Assert.assertEquals(angle.getDisplayName(), "Platform Magnetic Heading");
    }

    @Test
    public void testConstructFromEncoded() {
        byte[] bytes = new byte[] {0x00, 0x00};
        PlatformMagneticHeading angle = new PlatformMagneticHeading(bytes);
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        angle = new PlatformMagneticHeading(bytes);
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);

        Assert.assertEquals(angle.getDisplayName(), "Platform Magnetic Heading");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {0x00, 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformMagneticHeading, bytes);
        Assert.assertTrue(v instanceof PlatformMagneticHeading);
        PlatformMagneticHeading angle = (PlatformMagneticHeading) v;
        Assert.assertEquals(angle.getDegrees(), 0.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Platform Magnetic Heading");

        bytes = new byte[] {(byte) 0xff, (byte) 0xff};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformMagneticHeading, bytes);
        Assert.assertTrue(v instanceof PlatformMagneticHeading);
        angle = (PlatformMagneticHeading) v;
        Assert.assertEquals(angle.getDegrees(), 360.0);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Platform Magnetic Heading");

        bytes = new byte[] {(byte) 0xdd, (byte) 0xc5};
        v = UasDatalinkFactory.createValue(UasDatalinkTag.PlatformMagneticHeading, bytes);
        Assert.assertTrue(v instanceof PlatformMagneticHeading);
        angle = (PlatformMagneticHeading) v;
        Assert.assertEquals(angle.getDegrees(), 311.8682, 0.0001);
        Assert.assertEquals(angle.getBytes(), bytes);
        Assert.assertEquals(angle.getDisplayName(), "Platform Magnetic Heading");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformMagneticHeading(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformMagneticHeading(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PlatformMagneticHeading(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
