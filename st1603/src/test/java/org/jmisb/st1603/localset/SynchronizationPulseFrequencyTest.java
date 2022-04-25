package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.SynchronizationPulseFrequency;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Synchronization Pulse Frequency. */
public class SynchronizationPulseFrequencyTest {
    @Test
    public void testConstructFromValue() {
        SynchronizationPulseFrequency uut = new SynchronizationPulseFrequency(2.3);
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66
                });
        assertEquals(uut.getDisplayName(), "Synchronization Pulse Frequency");
        assertEquals(uut.getDisplayableValue(), "2.3000 Hz");
        assertEquals(uut.getSynchronizationFrequency(), 2.3, 0.0000000001);
    }

    @Test
    public void testConstructFromBytesDouble() throws KlvParseException {
        SynchronizationPulseFrequency uut =
                new SynchronizationPulseFrequency(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x02,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66,
                            (byte) 0x66
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x66
                });
        assertEquals(uut.getDisplayName(), "Synchronization Pulse Frequency");
        assertEquals(uut.getDisplayableValue(), "2.3000 Hz");
        assertEquals(uut.getSynchronizationFrequency(), 2.3, 0.0000000001);
    }

    @Test
    public void testConstructFromBytesFloat() throws KlvParseException {
        SynchronizationPulseFrequency uut =
                new SynchronizationPulseFrequency(new byte[] {0x40, 0x13, 0x33, 0x33});
        // Slightly different to previous example because of float imprecision
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x66,
                    (byte) 0x66,
                    (byte) 0x60,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
        assertEquals(uut.getDisplayName(), "Synchronization Pulse Frequency");
        assertEquals(uut.getDisplayableValue(), "2.3000 Hz");
        assertEquals(uut.getSynchronizationFrequency(), 2.3, 0.000001);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBadLength() throws KlvParseException {
        new SynchronizationPulseFrequency(new byte[] {(byte) 0x40});
    }
}
