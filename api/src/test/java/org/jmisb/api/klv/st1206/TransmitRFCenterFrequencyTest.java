package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Transmit RF Center Frequency (ST1206 Tag 20). */
public class TransmitRFCenterFrequencyTest {
    @Test
    public void testConstructFromValue() {
        TransmitRFCenterFrequency uut = new TransmitRFCenterFrequency(8000000000.0);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xee, (byte) 0x6b, (byte) 0x28});
        assertEquals(uut.getDisplayName(), "Transmit RF Center Frequency");
        assertEquals(uut.getDisplayableValue(), "8000000000.0Hz");
        assertEquals(uut.getFrequency(), 8000000000.0, 0.1);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TransmitRFCenterFrequency uut =
                new TransmitRFCenterFrequency(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Transmit RF Center Frequency");
        assertEquals(uut.getDisplayableValue(), "33964032.0Hz");
        assertEquals(uut.getFrequency(), 33964032.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.TransmitRFCenterFrequency,
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertTrue(value instanceof TransmitRFCenterFrequency);
        TransmitRFCenterFrequency uut = (TransmitRFCenterFrequency) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Transmit RF Center Frequency");
        assertEquals(uut.getDisplayableValue(), "409600.0Hz");
        assertEquals(uut.getFrequency(), 409600.0, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TransmitRFCenterFrequency(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TransmitRFCenterFrequency(1000000000000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TransmitRFCenterFrequency(new byte[] {0x01, 0x02, 0x03});
    }
}
