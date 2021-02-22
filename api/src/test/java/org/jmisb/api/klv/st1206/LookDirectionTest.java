package org.jmisb.api.klv.st1206;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LookDirectionTest {

    @Test
    public void testConstructFromValueLeft() {
        LookDirection uut = new LookDirection((byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(uut.getDisplayableValue(), "Left");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test
    public void testConstructFromValueRight() {
        LookDirection uut = new LookDirection((byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(uut.getDisplayableValue(), "Right");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test
    public void testConstructFromEncodedLeft() {
        LookDirection uut = new LookDirection(new byte[] {(byte) 0});
        Assert.assertEquals(uut.getLookDirection(), (byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(uut.getDisplayableValue(), "Left");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test
    public void testConstructFromEncodedRight() {
        LookDirection uut = new LookDirection(new byte[] {(byte) 1});
        Assert.assertEquals(uut.getLookDirection(), (byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(uut.getDisplayableValue(), "Right");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test
    public void testFactoryLeft() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        ISARMIMetadataValue v = SARMILocalSet.createValue(SARMIMetadataKey.LookDirection, bytes);
        Assert.assertTrue(v instanceof LookDirection);
        LookDirection uut = (LookDirection) v;
        Assert.assertEquals(uut.getLookDirection(), (byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(uut.getDisplayableValue(), "Left");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test
    public void testFactoryRight() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01};
        ISARMIMetadataValue v = SARMILocalSet.createValue(SARMIMetadataKey.LookDirection, bytes);
        Assert.assertTrue(v instanceof LookDirection);
        LookDirection uut = (LookDirection) v;
        Assert.assertEquals(uut.getLookDirection(), (byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(uut.getDisplayableValue(), "Right");
        Assert.assertEquals(uut.getDisplayName(), "Look Direction");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new LookDirection((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new LookDirection((byte) 2);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new LookDirection(new byte[] {0x00, 0x00});
    }
}
