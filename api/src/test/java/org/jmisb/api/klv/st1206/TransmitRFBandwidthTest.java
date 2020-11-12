package org.jmisb.api.klv.st1206;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SARMI Transmit RF Bandwidth (ST1206 Tag 20). */
public class TransmitRFBandwidthTest {
    @Test
    public void testConstructFromValue() {
        TransmitRFBandwidth uut = new TransmitRFBandwidth(800000000.0);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0xbe, (byte) 0xbc, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Transmit RF Bandwidth");
        assertEquals(uut.getDisplayableValue(), "800000000.0Hz");
        assertEquals(uut.getBandwidth(), 800000000.0, 0.1);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TransmitRFBandwidth uut =
                new TransmitRFBandwidth(
                        new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Transmit RF Bandwidth");
        assertEquals(uut.getDisplayableValue(), "4245504.0Hz");
        assertEquals(uut.getBandwidth(), 4245504.0, 0.01);
    }

    @Test
    public void testFactory() throws KlvParseException {
        ISARMIMetadataValue value =
                SARMILocalSet.createValue(
                        SARMIMetadataKey.TransmitRFBandwidth,
                        new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertTrue(value instanceof TransmitRFBandwidth);
        TransmitRFBandwidth uut = (TransmitRFBandwidth) value;
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x20});
        assertEquals(uut.getDisplayName(), "Transmit RF Bandwidth");
        assertEquals(uut.getDisplayableValue(), "51200.0Hz");
        assertEquals(uut.getBandwidth(), 51200.0, 0.001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TransmitRFBandwidth(-0.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TransmitRFBandwidth(100000000000.1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TransmitRFBandwidth(new byte[] {0x01, 0x02, 0x03});
    }
}
