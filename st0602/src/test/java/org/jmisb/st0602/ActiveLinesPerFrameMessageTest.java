package org.jmisb.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ActiveLinesPerFrameMessage. */
public class ActiveLinesPerFrameMessageTest {

    public ActiveLinesPerFrameMessageTest() {}

    @Test
    public void checkLabel() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(480));
        assertEquals(
                uut.getUniversalLabel(),
                ActiveLinesPerFrameMessage.AnnotationActiveLinesPerFrameUl);
    }

    @Test
    public void checkDisplayHeader() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(480));
        assertEquals(uut.displayHeader(), "ST 0602, Active Lines per Frame");
    }

    @Test
    public void checkValidKeys() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(480));
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveLinesPerFrame));
        assertEquals(
                uut.getField(AnnotationMetadataKey.ActiveLinesPerFrame).getDisplayableValue(),
                "480");
    }

    @Test
    public void checkInvalidKeys() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(480));
        assertEquals(uut.getIdentifiers().size(), 1);
        assertFalse(uut.getIdentifiers().contains(AnnotationMetadataKey.EventIndication));
        assertNull(uut.getField(AnnotationMetadataKey.EventIndication));
    }

    @Test
    public void checkInvalidKeysNull() throws KlvParseException {
        ActiveLinesPerFrameMessage uut = new ActiveLinesPerFrameMessage((ActiveLinesPerFrame) null);
        assertEquals(uut.getIdentifiers().size(), 0);
        assertFalse(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveLinesPerFrame));
        assertNull(uut.getField(AnnotationMetadataKey.ActiveLinesPerFrame));
    }

    @Test
    public void checkParseFromBytes() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(
                        new byte[] {
                            0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02,
                            0x02, 0x00, 0x00, 0x00, 0x02, 0x04, 0x00
                        });
        assertTrue(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveLinesPerFrame));
        assertEquals(
                uut.getField(AnnotationMetadataKey.ActiveLinesPerFrame).getDisplayableValue(),
                "1024");
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Unexpected Annotation Active Lines per Frame Universal Label passed in")
    public void checkParseWithBadLabel() throws KlvParseException {
        new ActiveLinesPerFrameMessage(
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x01,
                    0x01,
                    0x01,
                    0x01,
                    0x04,
                    0x01,
                    0x03,
                    0x02,
                    0x02,
                    0x00,
                    (byte) 0xFF,
                    0x00,
                    0x02,
                    0x04,
                    0x00
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Active Lines per Frame Universal Label must have length 19")
    public void checkParseWithReallyShortLength() throws KlvParseException {
        new ActiveLinesPerFrameMessage(new byte[] {0x06});
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Active Lines per Frame Universal Label must have length 19")
    public void checkParseWithBadLength() throws KlvParseException {
        new ActiveLinesPerFrameMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x04, 0x00, 0x01
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Active Lines per Frame Universal Label must be specified as length 2")
    public void checkParseWithBadLengthValue() throws KlvParseException {
        new ActiveLinesPerFrameMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x01, 0x04, 0x00
                });
    }

    @Test
    public void checkFraming() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(1024));
        byte[] bytes = uut.frameMessage(false);
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x04, 0x00
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkFramingNested() throws KlvParseException {
        ActiveLinesPerFrameMessage uut =
                new ActiveLinesPerFrameMessage(new ActiveLinesPerFrame(1024));
        uut.frameMessage(true);
    }
}
