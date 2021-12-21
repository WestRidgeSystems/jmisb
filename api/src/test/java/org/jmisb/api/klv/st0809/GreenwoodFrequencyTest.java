package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for GreenwoodFrequency. */
public class GreenwoodFrequencyTest {

    @Test
    public void fromValue() {
        GreenwoodFrequency uut = new GreenwoodFrequency(77.0f);
        assertEquals(uut.getFrequency(), 77.0f);
        assertEquals(uut.getDisplayName(), "Greenwood Frequency");
        assertEquals(uut.getDisplayableValue(), "77.0 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x9a, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        GreenwoodFrequency uut =
                new GreenwoodFrequency(
                        new byte[] {(byte) 0x42, (byte) 0x9a, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getFrequency(), 77.0f);
        assertEquals(uut.getDisplayName(), "Greenwood Frequency");
        assertEquals(uut.getDisplayableValue(), "77.0 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x9a, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.GreenwoodFrequency,
                        new byte[] {(byte) 0x42, (byte) 0x9a, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof GreenwoodFrequency);
        GreenwoodFrequency uut = (GreenwoodFrequency) v;
        assertEquals(uut.getFrequency(), 77.0f);
        assertEquals(uut.getDisplayName(), "Greenwood Frequency");
        assertEquals(uut.getDisplayableValue(), "77.0 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x9a, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new GreenwoodFrequency(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new GreenwoodFrequency(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new GreenwoodFrequency(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
