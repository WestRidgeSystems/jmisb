package org.jmisb.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ImageHorizonPixelPackTest {

    @Test
    public void testConstructFromValue() {
        ImageHorizonPixelPack imageHorizon = new ImageHorizonPixelPack(10, 0, 0, 20);
        Assert.assertEquals(imageHorizon.getX0(), 10);
        Assert.assertEquals(imageHorizon.getY0(), 0);
        Assert.assertEquals(imageHorizon.getX1(), 0);
        Assert.assertEquals(imageHorizon.getY1(), 20);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x14});
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
        Assert.assertEquals(imageHorizon.getDisplayableValue(), "(10%, 0%),(0%, 20%)");
    }

    @Test
    public void testConstructFromValueOptions() {
        ImageHorizonPixelPack imageHorizon = new ImageHorizonPixelPack(10, 0, 0, 20);
        imageHorizon.setLatitude0(-34.3);
        imageHorizon.setLongitude0(143.2);
        imageHorizon.setLatitude1(-34.56);
        imageHorizon.setLongitude1(143.29);
        Assert.assertEquals(imageHorizon.getX0(), 10);
        Assert.assertEquals(imageHorizon.getY0(), 0);
        Assert.assertEquals(imageHorizon.getX1(), 0);
        Assert.assertEquals(imageHorizon.getY1(), 20);
        Assert.assertEquals(imageHorizon.getLatitude0(), -34.3, 0.00001);
        Assert.assertEquals(imageHorizon.getLongitude0(), 143.2, 0.00001);
        Assert.assertEquals(imageHorizon.getLatitude1(), -34.56, 0.00001);
        Assert.assertEquals(imageHorizon.getLongitude1(), 143.29, 0.00001);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x0a,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0xcf,
                    (byte) 0x37,
                    (byte) 0xc0,
                    (byte) 0x49,
                    (byte) 0x65,
                    (byte) 0xd4,
                    (byte) 0xc3,
                    (byte) 0xb2,
                    (byte) 0xce,
                    (byte) 0xd9,
                    (byte) 0x16,
                    (byte) 0x88,
                    (byte) 0x65,
                    (byte) 0xe5,
                    (byte) 0x26,
                    (byte) 0x00
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
        Assert.assertEquals(imageHorizon.getDisplayableValue(), "(10%, 0%),(0%, 20%)");
    }

    @Test
    public void testConstructFromEncoded() {
        ImageHorizonPixelPack imageHorizon =
                new ImageHorizonPixelPack(
                        new byte[] {(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x14});
        Assert.assertEquals(imageHorizon.getX0(), 10);
        Assert.assertEquals(imageHorizon.getY0(), 0);
        Assert.assertEquals(imageHorizon.getX1(), 0);
        Assert.assertEquals(imageHorizon.getY1(), 20);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {(byte) 0x0a, (byte) 0x00, (byte) 0x00, (byte) 0x14});
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
        Assert.assertEquals(imageHorizon.getDisplayableValue(), "(10%, 0%),(0%, 20%)");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x00, (byte) 0x0a, (byte) 0x14, (byte) 0x00};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {(byte) 0x00, (byte) 0x0a, (byte) 0x14, (byte) 0x00});
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValues() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertEquals(imageHorizon.getLatitude0(), -79.163850051892850, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude0(), 166.40081296041646, 85e-9);
        Assert.assertEquals(imageHorizon.getLatitude1(), -10.542388633146132, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
        Assert.assertEquals(imageHorizon.getDisplayableValue(), "(0%, 10%),(20%, 0%)");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLat0() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLatitude0()));
        Assert.assertEquals(imageHorizon.getLongitude0(), 166.40081296041646, 85e-9);
        Assert.assertEquals(imageHorizon.getLatitude1(), -10.542388633146132, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLon0() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertEquals(imageHorizon.getLatitude0(), -79.163850051892850, 43e-9);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLongitude0()));
        Assert.assertEquals(imageHorizon.getLatitude1(), -10.542388633146132, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLat0Lon0() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLatitude0()));
        Assert.assertTrue(Double.isNaN(imageHorizon.getLongitude0()));
        Assert.assertEquals(imageHorizon.getLatitude1(), -10.542388633146132, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLat1() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertEquals(imageHorizon.getLatitude0(), -79.163850051892850, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude0(), 166.40081296041646, 85e-9);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLatitude1()));
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLon1() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertEquals(imageHorizon.getLatitude0(), -79.163850051892850, 43e-9);
        Assert.assertEquals(imageHorizon.getLongitude0(), 166.40081296041646, 85e-9);
        Assert.assertEquals(imageHorizon.getLatitude1(), -10.542388633146132, 43e-9);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLongitude1()));
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x8F,
                    (byte) 0x69,
                    (byte) 0x52,
                    (byte) 0x62,
                    (byte) 0x76,
                    (byte) 0x54,
                    (byte) 0x57,
                    (byte) 0xF2,
                    (byte) 0xF1,
                    (byte) 0x01,
                    (byte) 0xA2,
                    (byte) 0x29,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test
    public void testFactoryOptionalValuesInvalidLat0Lon0Lat1() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.ImageHorizonPixelPack, bytes);
        Assert.assertEquals(v.getDisplayName(), "Image Horizon");
        Assert.assertTrue(v instanceof ImageHorizonPixelPack);
        ImageHorizonPixelPack imageHorizon = (ImageHorizonPixelPack) v;
        Assert.assertEquals(imageHorizon.getX0(), 0);
        Assert.assertEquals(imageHorizon.getY0(), 10);
        Assert.assertEquals(imageHorizon.getX1(), 20);
        Assert.assertEquals(imageHorizon.getY1(), 0);
        Assert.assertTrue(Double.isNaN(imageHorizon.getLatitude0()));
        Assert.assertTrue(Double.isNaN(imageHorizon.getLongitude0()));
        Assert.assertTrue(Double.isNaN(imageHorizon.getLatitude1()));
        Assert.assertEquals(imageHorizon.getLongitude1(), 29.157890122923014, 85e-9);
        Assert.assertEquals(
                imageHorizon.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x0a,
                    (byte) 0x14,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0xBC,
                    (byte) 0x08,
                    (byte) 0x2B
                });
        Assert.assertEquals(imageHorizon.getDisplayName(), "Image Horizon");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthTooShort() {
        new ImageHorizonPixelPack(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength5() {
        new ImageHorizonPixelPack(new byte[5]);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthTooLong() {
        new ImageHorizonPixelPack(new byte[21]);
    }
}
