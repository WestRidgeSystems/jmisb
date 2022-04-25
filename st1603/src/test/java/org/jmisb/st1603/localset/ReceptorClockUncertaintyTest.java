package org.jmisb.st1603.localset;

import org.jmisb.st1603.localset.ReceptorClockUncertainty;
import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ReceptorClockUncertainty. */
public class ReceptorClockUncertaintyTest {

    public ReceptorClockUncertaintyTest() {}

    @Test
    public void checkCreateFromValue() {
        ReceptorClockUncertainty uut = new ReceptorClockUncertainty(60);
        assertEquals(uut.getUncertainty(), 60l);
        assertEquals(uut.getDisplayName(), "Receptor Clock Uncertainty");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromBytes() throws KlvParseException {
        ReceptorClockUncertainty uut = new ReceptorClockUncertainty(new byte[] {0x3C});
        assertEquals(uut.getUncertainty(), 60l);
        assertEquals(uut.getDisplayName(), "Receptor Clock Uncertainty");
        assertEquals(uut.getDisplayableValue(), "60");
        assertEquals(uut.getBytes(), new byte[] {0x3C});
    }

    @Test
    public void checkCreateFromMultiBytes() throws KlvParseException {
        ReceptorClockUncertainty uut = new ReceptorClockUncertainty(new byte[] {0x10, 0x20, 0x30});
        assertEquals(uut.getUncertainty(), 1056816L);
        assertEquals(uut.getDisplayName(), "Receptor Clock Uncertainty");
        assertEquals(uut.getDisplayableValue(), "1056816");
        assertEquals(uut.getBytes(), new byte[] {0x10, 0x20, 0x30});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkCreateFromValueNegative() {
        new ReceptorClockUncertainty(-1);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkCreateFromBytesEmpty() throws KlvParseException {
        new ReceptorClockUncertainty(new byte[0]);
    }
}
