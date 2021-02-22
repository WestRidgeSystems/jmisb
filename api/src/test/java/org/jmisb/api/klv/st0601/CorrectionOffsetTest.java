package org.jmisb.api.klv.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class CorrectionOffsetTest {
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBadLength() {
        byte[] nineByteArray =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09
                };
        new CorrectionOffset(nineByteArray);
    }

    @Test
    public void stExample() {
        long value = 5025678901L;
        byte[] origBytes =
                new byte[] {(byte) 0x01, (byte) 0x2B, (byte) 0x8D, (byte) 0xC6, (byte) 0x35};

        CorrectionOffset time = new CorrectionOffset(origBytes);
        assertEquals(time.getDisplayName(), "Correction Offset");
        assertEquals(time.getOffset(), value);
        assertEquals(time.getBytes(), origBytes);
        assertEquals(time.getDisplayableValue(), "5025678901\u00B5s");

        time = new CorrectionOffset(value);
        assertEquals(time.getDisplayName(), "Correction Offset");
        assertEquals(time.getOffset(), value);
        assertEquals(time.getBytes(), origBytes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        long value = 5025678901L;
        byte[] origBytes =
                new byte[] {(byte) 0x01, (byte) 0x2B, (byte) 0x8D, (byte) 0xC6, (byte) 0x35};
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.CorrectionOffset, origBytes);
        assertTrue(v instanceof CorrectionOffset);
        CorrectionOffset time = (CorrectionOffset) v;
        assertEquals(time.getDisplayName(), "Correction Offset");
        assertEquals(time.getOffset(), value);
        assertEquals(time.getBytes(), origBytes);
        assertEquals(time.getDisplayableValue(), "5025678901\u00B5s");

        time = new CorrectionOffset(value);
        assertEquals(time.getDisplayName(), "Correction Offset");
        assertEquals(time.getOffset(), value);
        assertEquals(time.getBytes(), origBytes);
    }
}
