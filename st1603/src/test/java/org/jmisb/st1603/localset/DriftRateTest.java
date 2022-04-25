package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.DriftRate;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for DriftRate. */
public class DriftRateTest {

    @Test
    public void checkFromValue() {
        DriftRate uut = new DriftRate(18.2);
        assertEquals(uut.getDisplayName(), "Drift Rate");
        assertEquals(uut.getDisplayableValue(), "18.2000 µs/s");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x32, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33});
        assertEquals(uut.getDriftRate(), 18.2, 0.00000001);
    }

    @Test
    public void checkFromBytesDouble() throws KlvParseException {
        DriftRate uut = new DriftRate(new byte[] {0x40, 0x32, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33});
        assertEquals(uut.getDisplayName(), "Drift Rate");
        assertEquals(uut.getDisplayableValue(), "18.2000 µs/s");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x32, 0x33, 0x33, 0x33, 0x33, 0x33, 0x33});
        assertEquals(uut.getDriftRate(), 18.2, 0.00000001);
    }

    @Test
    public void checkFromBytesFloat() throws KlvParseException {
        DriftRate uut =
                new DriftRate(new byte[] {(byte) 0x41, (byte) 0x91, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Drift Rate");
        assertEquals(uut.getDisplayableValue(), "18.2000 µs/s");
        // Slightly different to previous, because of float precision
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x32, 0x33, 0x33, 0x40, 0x00, 0x00, 0x00});
        assertEquals(uut.getDriftRate(), 18.2, 0.00001);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesBadLength() throws KlvParseException {
        new DriftRate(new byte[] {(byte) 0x41, (byte) 0x91, (byte) 0x99});
    }
}
