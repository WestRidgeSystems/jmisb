package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for UnlockTime. */
public class UnlockTimeTest {

    public UnlockTimeTest() {}

    @Test
    public void checkCreateFromValue() {
        UnlockTime uut = new UnlockTime(60);
        assertEquals(uut.getUnlockTime(), 60l);
        assertEquals(uut.getDisplayName(), "Unlock Time");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromBytes() throws KlvParseException {
        UnlockTime uut = new UnlockTime(new byte[] {0x3C});
        assertEquals(uut.getUnlockTime(), 60l);
        assertEquals(uut.getDisplayName(), "Unlock Time");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromMultiBytes() throws KlvParseException {
        UnlockTime uut = new UnlockTime(new byte[] {0x10, 0x20, 0x30});
        assertEquals(uut.getUnlockTime(), 1056816L);
        assertEquals(uut.getDisplayName(), "Unlock Time");
        assertEquals(uut.getDisplayableValue(), "1056816");
        assertEquals(uut.getBytes(), new byte[] {0x10, 0x20, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkCreateFromValueNegative() {
        new UnlockTime(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkCreateFromBytesEmpty() throws KlvParseException {
        new UnlockTime(new byte[0]);
    }
}
