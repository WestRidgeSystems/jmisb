package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for TylerTrackingFrequency. */
public class TylerTrackingFrequencyTest {

    @Test
    public void fromValue() {
        TylerTrackingFrequency uut = new TylerTrackingFrequency(8.3f);
        assertEquals(uut.getFrequency(), 8.3f);
        assertEquals(uut.getDisplayName(), "Tyler Tracking Frequency");
        assertEquals(uut.getDisplayableValue(), "8.3 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x04, (byte) 0xcc, (byte) 0xcd});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        TylerTrackingFrequency uut =
                new TylerTrackingFrequency(
                        new byte[] {(byte) 0x41, (byte) 0x04, (byte) 0xcc, (byte) 0xcd});
        assertEquals(uut.getFrequency(), 8.3f);
        assertEquals(uut.getDisplayName(), "Tyler Tracking Frequency");
        assertEquals(uut.getDisplayableValue(), "8.3 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x04, (byte) 0xcc, (byte) 0xcd});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.TylerTrackingFrequency,
                        new byte[] {(byte) 0x41, (byte) 0x04, (byte) 0xcc, (byte) 0xcd});
        assertTrue(v instanceof TylerTrackingFrequency);
        TylerTrackingFrequency uut = (TylerTrackingFrequency) v;
        assertEquals(uut.getFrequency(), 8.3f);
        assertEquals(uut.getDisplayName(), "Tyler Tracking Frequency");
        assertEquals(uut.getDisplayableValue(), "8.3 Hz");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x04, (byte) 0xcc, (byte) 0xcd});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new TylerTrackingFrequency(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new TylerTrackingFrequency(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new TylerTrackingFrequency(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
