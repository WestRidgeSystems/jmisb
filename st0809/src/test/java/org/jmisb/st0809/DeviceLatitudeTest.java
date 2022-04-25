package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeviceLatitudeTest {
    @Test
    public void testConstructFromValueMin() {
        DeviceLatitude latitude = new DeviceLatitude(-90);
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x56,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(latitude.getDegrees(), -90.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "-90.000000°");
    }

    @Test
    public void testConstructFromEncodedMin() {
        DeviceLatitude latitude =
                new DeviceLatitude(
                        new byte[] {
                            (byte) 0xC0,
                            (byte) 0x56,
                            (byte) 0x80,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x56,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(latitude.getDegrees(), -90.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "-90.000000°");
    }

    @Test
    public void testConstructFromValueMax() {
        DeviceLatitude latitude = new DeviceLatitude(90);
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x56,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(latitude.getDegrees(), 90.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "90.000000°");
    }

    @Test
    public void testConstructFromEncodedMax() {
        DeviceLatitude latitude =
                new DeviceLatitude(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x56,
                            (byte) 0x80,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x56,
                    (byte) 0x80,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(latitude.getDegrees(), 90.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "90.000000°");
    }

    @Test
    public void testConstructFromValueZero() {
        DeviceLatitude latitude = new DeviceLatitude(0);
        assertEquals(
                latitude.getBytes(),
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
        assertEquals(latitude.getDegrees(), 0.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "0.000000°");
    }

    @Test
    public void testConstructFromEncodedZero() {
        DeviceLatitude latitude =
                new DeviceLatitude(
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
                latitude.getBytes(),
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
        assertEquals(latitude.getDegrees(), 0.0);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "0.000000°");
    }

    @Test
    public void testConstructFromValue() {
        DeviceLatitude latitude = new DeviceLatitude(-37.33345);
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x42,
                    (byte) 0xAA,
                    (byte) 0xAE,
                    (byte) 0x7D,
                    (byte) 0x56,
                    (byte) 0x6C,
                    (byte) 0xF4
                });
        assertEquals(latitude.getDegrees(), -37.33345, 0.0000000001);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "-37.333450°");
    }

    @Test
    public void testConstructFromEncoded() {
        DeviceLatitude latitude =
                new DeviceLatitude(
                        new byte[] {
                            (byte) 0xC0,
                            (byte) 0x42,
                            (byte) 0xAA,
                            (byte) 0xAE,
                            (byte) 0x7D,
                            (byte) 0x56,
                            (byte) 0x6C,
                            (byte) 0xF4
                        });
        assertEquals(
                latitude.getBytes(),
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x42,
                    (byte) 0xAA,
                    (byte) 0xAE,
                    (byte) 0x7D,
                    (byte) 0x56,
                    (byte) 0x6C,
                    (byte) 0xF4
                });
        assertEquals(latitude.getDegrees(), -37.33345, 0.0000000001);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "-37.333450°");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0xC0,
                    (byte) 0x42,
                    (byte) 0xAA,
                    (byte) 0xAE,
                    (byte) 0x7D,
                    (byte) 0x56,
                    (byte) 0x6C,
                    (byte) 0xF4
                };
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.DeviceLatitude, bytes);
        Assert.assertTrue(v instanceof DeviceLatitude);
        DeviceLatitude latitude = (DeviceLatitude) v;
        assertEquals(latitude.getDegrees(), -37.33345, 0.0000000001);
        assertEquals(latitude.getDisplayName(), "Device Latitude");
        assertEquals(latitude.getDisplayableValue(), "-37.333450°");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DeviceLatitude(-90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DeviceLatitude(90.01);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DeviceLatitude(new byte[] {0x01, 0x02, 0x03, 0x04});
    }
}
