package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 X Viewport Position implementation. */
public class XViewportPositionTest {

    public XViewportPositionTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        XViewportPosition uut = new XViewportPosition((short) 1280);
        assertEquals(uut.getDisplayName(), "X Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1280 pixels");
        assertEquals(uut.getPosition(), 1280);
        assertEquals(uut.getBytes(), new byte[] {0x05, 0x00});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        XViewportPosition uut = new XViewportPosition(new byte[] {0x05, 0x00});
        assertEquals(uut.getDisplayName(), "X Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1280 pixels");
        assertEquals(uut.getPosition(), 1280);
        assertEquals(uut.getBytes(), new byte[] {0x05, 0x00});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.XViewportPosition, new byte[] {0x05, 0x00});
        assertTrue(value instanceof XViewportPosition);
        XViewportPosition uut = (XViewportPosition) value;
        assertEquals(uut.getDisplayName(), "X Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1280 pixels");
        assertEquals(uut.getPosition(), 1280);
        assertEquals(uut.getBytes(), new byte[] {0x05, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new XViewportPosition(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new XViewportPosition(new byte[] {0x00, 0x01, 0x02});
    }

    @Test
    public void checkConstructionMin() throws KlvParseException {
        XViewportPosition uut = new XViewportPosition((short) -32768);
        assertEquals(uut.getDisplayName(), "X Viewport Position");
        assertEquals(uut.getDisplayableValue(), "-32768 pixels");
        assertEquals(uut.getPosition(), -32768);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x80, (byte) 0x00});
    }

    @Test
    public void checkConstructionMax() throws KlvParseException {
        XViewportPosition uut = new XViewportPosition((short) 32767);
        assertEquals(uut.getDisplayName(), "X Viewport Position");
        assertEquals(uut.getDisplayableValue(), "32767 pixels");
        assertEquals(uut.getPosition(), 32767);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7F, (byte) 0xFF});
    }
}
