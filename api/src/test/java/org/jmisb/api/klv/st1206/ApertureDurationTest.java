package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ST 1206 Aperture Duration (ST1206 Tag 15). */
public class ApertureDurationTest {
    @Test
    public void testConstructFromValue() {
        ApertureDuration uut = new ApertureDuration(1);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01});
        assertEquals(uut.getDisplayName(), "Aperture Duration");
        assertEquals(uut.getDisplayableValue(), "1μs");
        assertEquals(uut.getApertureDuration(), 1L);
    }

    @Test
    public void testConstructFromValue255() {
        ApertureDuration uut = new ApertureDuration(255);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF});
        assertEquals(uut.getDisplayName(), "Aperture Duration");
        assertEquals(uut.getDisplayableValue(), "255μs");
        assertEquals(uut.getApertureDuration(), 255L);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        ApertureDuration uut =
                new ApertureDuration(
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04});
        assertEquals(uut.getDisplayName(), "Aperture Duration");
        assertEquals(uut.getDisplayableValue(), "4μs");
        assertEquals(uut.getApertureDuration(), 4L);
    }

    @Test
    public void testConstructFromEncodedBytes65536() {
        ApertureDuration uut =
                new ApertureDuration(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Aperture Duration");
        assertEquals(uut.getDisplayableValue(), "65536μs");
        assertEquals(uut.getApertureDuration(), 65536L);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.ApertureDuration,
                        new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertTrue(value instanceof ApertureDuration);
        ApertureDuration uut = (ApertureDuration) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getDisplayName(), "Aperture Duration");
        assertEquals(uut.getDisplayableValue(), "16777216μs");
        assertEquals(uut.getApertureDuration(), 16777216L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new ApertureDuration(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new ApertureDuration(4294967296L);
        ;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new ApertureDuration(new byte[] {0x01, 0x02, 0x03});
    }
}
