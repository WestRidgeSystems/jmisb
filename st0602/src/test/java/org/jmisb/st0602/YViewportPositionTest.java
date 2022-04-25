package org.jmisb.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Y Viewport Position implementation. */
public class YViewportPositionTest {

    public YViewportPositionTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        YViewportPosition uut = new YViewportPosition((short) 1024);
        assertEquals(uut.getDisplayName(), "Y Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1024 pixels");
        assertEquals(uut.getPosition(), 1024);
        assertEquals(uut.getBytes(), new byte[] {0x04, 0x00});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        YViewportPosition uut = new YViewportPosition(new byte[] {0x04, 0x00});
        assertEquals(uut.getDisplayName(), "Y Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1024 pixels");
        assertEquals(uut.getPosition(), 1024);
        assertEquals(uut.getBytes(), new byte[] {0x04, 0x00});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.YViewportPosition, new byte[] {0x04, 0x00});
        assertTrue(value instanceof YViewportPosition);
        YViewportPosition uut = (YViewportPosition) value;
        assertEquals(uut.getDisplayName(), "Y Viewport Position");
        assertEquals(uut.getDisplayableValue(), "1024 pixels");
        assertEquals(uut.getPosition(), 1024);
        assertEquals(uut.getBytes(), new byte[] {0x04, 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new YViewportPosition(new byte[] {0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new YViewportPosition(new byte[] {0x00, 0x01, 0x02});
    }

    @Test
    public void checkConstructionMin() throws KlvParseException {
        YViewportPosition uut = new YViewportPosition((short) -32768);
        assertEquals(uut.getDisplayName(), "Y Viewport Position");
        assertEquals(uut.getDisplayableValue(), "-32768 pixels");
        assertEquals(uut.getPosition(), -32768);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x80, (byte) 0x00});
    }

    @Test
    public void checkConstructionMax() throws KlvParseException {
        YViewportPosition uut = new YViewportPosition((short) 32767);
        assertEquals(uut.getDisplayName(), "Y Viewport Position");
        assertEquals(uut.getDisplayableValue(), "32767 pixels");
        assertEquals(uut.getPosition(), 32767);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x7F, (byte) 0xFF});
    }
}
