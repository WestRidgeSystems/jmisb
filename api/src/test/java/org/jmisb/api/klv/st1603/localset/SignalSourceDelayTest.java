package org.jmisb.api.klv.st1603.localset;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for SignalSourceDelay. */
public class SignalSourceDelayTest {

    public SignalSourceDelayTest() {}

    @Test
    public void checkCreateFromValue() {
        SignalSourceDelay uut = new SignalSourceDelay(60);
        assertEquals(uut.getDelay(), 60l);
        assertEquals(uut.getDisplayName(), "Signal Source Delay");
        assertEquals(uut.getDisplayableValue(), "60 ns");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromBytes() throws KlvParseException {
        SignalSourceDelay uut = new SignalSourceDelay(new byte[] {0x3C});
        assertEquals(uut.getDelay(), 60l);
        assertEquals(uut.getDisplayName(), "Signal Source Delay");
        assertEquals(uut.getDisplayableValue(), "60 ns");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromMultiBytes() throws KlvParseException {
        SignalSourceDelay uut = new SignalSourceDelay(new byte[] {0x10, 0x20, 0x30});
        assertEquals(uut.getDelay(), 1056816L);
        assertEquals(uut.getDisplayName(), "Signal Source Delay");
        assertEquals(uut.getDisplayableValue(), "1056816 ns");
        assertEquals(uut.getBytes(), new byte[] {0x10, 0x20, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkCreateFromValueNegative() {
        new SignalSourceDelay(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkCreateFromBytesEmpty() throws KlvParseException {
        new SignalSourceDelay(new byte[0]);
    }
}
