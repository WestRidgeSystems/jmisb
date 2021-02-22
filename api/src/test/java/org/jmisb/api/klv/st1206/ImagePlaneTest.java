package org.jmisb.api.klv.st1206;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ImagePlaneTest {

    @Test
    public void testConstructFromValueSlantPlane() {
        ImagePlane uut = new ImagePlane((byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0});
        Assert.assertEquals(uut.getDisplayableValue(), "Slant Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test
    public void testConstructFromValueGroundPlane() {
        ImagePlane uut = new ImagePlane((byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 1});
        Assert.assertEquals(uut.getDisplayableValue(), "Ground Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test
    public void testConstructFromEncodedSlantPlane() {
        ImagePlane uut = new ImagePlane(new byte[] {(byte) 0});
        Assert.assertEquals(uut.getImagePlane(), (byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(uut.getDisplayableValue(), "Slant Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test
    public void testConstructFromEncodedGroundPlane() {
        ImagePlane uut = new ImagePlane(new byte[] {(byte) 1});
        Assert.assertEquals(uut.getImagePlane(), (byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(uut.getDisplayableValue(), "Ground Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test
    public void testFactorySlantPlane() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00};
        ISARMIMetadataValue v = SARMILocalSet.createValue(SARMIMetadataKey.ImagePlane, bytes);
        Assert.assertTrue(v instanceof ImagePlane);
        ImagePlane uut = (ImagePlane) v;
        Assert.assertEquals(uut.getImagePlane(), (byte) 0);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x00});
        Assert.assertEquals(uut.getDisplayableValue(), "Slant Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test
    public void testFactoryGroundPlane() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x01};
        ISARMIMetadataValue v = SARMILocalSet.createValue(SARMIMetadataKey.ImagePlane, bytes);
        Assert.assertTrue(v instanceof ImagePlane);
        ImagePlane uut = (ImagePlane) v;
        Assert.assertEquals(uut.getImagePlane(), (byte) 1);
        Assert.assertEquals(uut.getBytes(), new byte[] {(byte) 0x01});
        Assert.assertEquals(uut.getDisplayableValue(), "Ground Plane");
        Assert.assertEquals(uut.getDisplayName(), "Image Plane");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ImagePlane((byte) -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ImagePlane((byte) 3);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ImagePlane(new byte[] {0x00, 0x00});
    }
}
