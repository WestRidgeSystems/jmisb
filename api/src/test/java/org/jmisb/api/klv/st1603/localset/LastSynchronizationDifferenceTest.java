package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for LastSynchronizationDifference. */
public class LastSynchronizationDifferenceTest {

    public LastSynchronizationDifferenceTest() {}

    @Test
    public void checkCreateFromValue() {
        LastSynchronizationDifference uut = new LastSynchronizationDifference(60);
        assertEquals(uut.getDifference(), 60l);
        assertEquals(uut.getDisplayName(), "Last Synchronization Difference");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromBytes() throws KlvParseException {
        LastSynchronizationDifference uut = new LastSynchronizationDifference(new byte[] {0x3C});
        assertEquals(uut.getDifference(), 60l);
        assertEquals(uut.getDisplayName(), "Last Synchronization Difference");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromMultiBytes() throws KlvParseException {
        LastSynchronizationDifference uut =
                new LastSynchronizationDifference(new byte[] {0x10, 0x20, 0x30});
        assertEquals(uut.getDifference(), 1056816L);
        assertEquals(uut.getDisplayName(), "Last Synchronization Difference");
        assertEquals(uut.getDisplayableValue(), "1056816");
        assertEquals(uut.getBytes(), new byte[] {0x10, 0x20, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkCreateFromValueNegative() {
        new LastSynchronizationDifference(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkCreateFromBytesEmpty() throws KlvParseException {
        new LastSynchronizationDifference(new byte[0]);
    }
}
