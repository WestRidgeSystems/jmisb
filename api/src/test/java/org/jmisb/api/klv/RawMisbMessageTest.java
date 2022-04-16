package org.jmisb.api.klv;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for RawMisbMessage. */
public class RawMisbMessageTest {

    // This isn't real - only used for unit testing.
    private static final UniversalLabel UNIVERSAL_LABEL =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                        0x00, 0x00, 0x00, 0x01
                    });

    private static final byte[] BYTES =
            new byte[] {
                0x06, 0x0e, 0x2b, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x01, 0x05, 0x7F, 0x03, 0x01, 0x02, 0x03
            };

    @Test
    public void simple() {
        RawMisbMessage uut = new RawMisbMessage(UNIVERSAL_LABEL, BYTES);
        assertEquals(uut.displayHeader(), "Unknown");
        assertEquals(uut.getUniversalLabel(), UNIVERSAL_LABEL);
        assertEquals(uut.getBytes(), BYTES);
        assertEquals(uut.frameMessage(false), BYTES);
        assertEquals(uut.frameMessage(true), BYTES);
        assertEquals(uut.getIdentifiers().size(), 0);
        assertNull(uut.getField(null));
    }
}
