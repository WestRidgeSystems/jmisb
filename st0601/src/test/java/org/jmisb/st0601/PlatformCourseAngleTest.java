package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlatformCourseAngleTest {
    @Test
    public void testConstructFromValue() {
        // From ST:
        PlatformCourseAngle course = new PlatformCourseAngle(125.0);
        Assert.assertEquals(course.getBytes(), new byte[] {(byte) 0x1F, (byte) 0x40});
        Assert.assertEquals(course.getDisplayableValue(), "125.000\u00B0");
        Assert.assertEquals(course.getDisplayName(), "Platform Course Angle");
    }

    @Test
    public void testConstructFromEncoded() {
        // From ST:
        PlatformCourseAngle course = new PlatformCourseAngle(new byte[] {(byte) 0x1F, (byte) 0x40});
        Assert.assertEquals(course.getAngle(), 125.0, 0.001);
        Assert.assertEquals(course.getBytes(), new byte[] {(byte) 0x1F, (byte) 0x40});
        Assert.assertEquals(course.getDisplayableValue(), "125.000\u00B0");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x1F, (byte) 0x40};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.PlatformCourseAngle, bytes);
        Assert.assertTrue(v instanceof PlatformCourseAngle);
        PlatformCourseAngle course = (PlatformCourseAngle) v;
        Assert.assertEquals(course.getAngle(), 125.0, 0.001);
        Assert.assertEquals(course.getBytes(), new byte[] {(byte) 0x1F, (byte) 0x40});
        Assert.assertEquals(course.getDisplayableValue(), "125.000\u00B0");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PlatformCourseAngle(-0.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PlatformCourseAngle(360.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong() {
        new PlatformCourseAngle(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
