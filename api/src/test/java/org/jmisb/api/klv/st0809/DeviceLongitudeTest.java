package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeviceLongitudeTest {
    @Test
    public void testConstructFromValueMin() {
        DeviceLongitude longitude = new DeviceLongitude(-180);
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), -180.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "-180.000000°");
    }

    @Test
    public void testConstructFromEncodedMin() {
        DeviceLongitude longitude =
                new DeviceLongitude(
                        new byte[] {
                            (byte) 0xC0,
                            (byte) 0x66,
                            (byte) 0x80,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), -180.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "-180.000000°");
    }

    @Test
    public void testConstructFromValueMax() {
        DeviceLongitude longitude = new DeviceLongitude(180);
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), 180.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "180.000000°");
    }

    @Test
    public void testConstructFromEncodedMax() {
        DeviceLongitude longitude =
                new DeviceLongitude(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x66,
                            (byte) 0x80,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), 180.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "180.000000°");
    }

    @Test
    public void testConstructFromValueZero() {
        DeviceLongitude longitude = new DeviceLongitude(0);
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), 0.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "0.000000°");
    }

    @Test
    public void testConstructFromEncodedZero() {
        DeviceLongitude longitude =
                new DeviceLongitude(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(longitude.getDegrees(), 0.0);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "0.000000°");
    }

    @Test
    public void testConstructFromValue() {
        DeviceLongitude longitude = new DeviceLongitude(150.33345);
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x62,
                    (byte) 0xCA,
                    (byte) 0xAB,
                    (byte) 0x9F,
                    (byte) 0x55,
                    (byte) 0x9B,
                    (byte) 0x3D
                });
        assertEquals(longitude.getDegrees(), 150.33345, 0.0000000001);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "150.333450°");
    }

    @Test
    public void testConstructFromEncoded() {
        DeviceLongitude longitude =
                new DeviceLongitude(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x62,
                            (byte) 0xCA,
                            (byte) 0xAB,
                            (byte) 0x9F,
                            (byte) 0x55,
                            (byte) 0x9B,
                            (byte) 0x3D
                        });
        assertEquals(
                longitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x62,
                    (byte) 0xCA,
                    (byte) 0xAB,
                    (byte) 0x9F,
                    (byte) 0x55,
                    (byte) 0x9B,
                    (byte) 0x3D
                });
        assertEquals(longitude.getDegrees(), 150.33345, 0.0000000001);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "150.333450°");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x62,
                    (byte) 0xCA,
                    (byte) 0xAB,
                    (byte) 0x9F,
                    (byte) 0x55,
                    (byte) 0x9B,
                    (byte) 0x3D
                };
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.DeviceLongitude, bytes);
        Assert.assertTrue(v instanceof DeviceLongitude);
        DeviceLongitude longitude = (DeviceLongitude) v;
        assertEquals(longitude.getDegrees(), 150.33345, 0.0000000001);
        assertEquals(longitude.getDisplayName(), "Device Longitude");
        assertEquals(longitude.getDisplayableValue(), "150.333450°");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DeviceLongitude(-180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DeviceLongitude(180.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DeviceLongitude(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}
