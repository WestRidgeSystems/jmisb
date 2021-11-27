package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvConstants;
import org.testng.annotations.Test;

/** Unit tests for ActiveSamplesPerLineMessage. */
public class ActiveSamplesPerLineMessageTest {

    public ActiveSamplesPerLineMessageTest() {}

    @Test
    public void checkLabel() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(640));
        assertEquals(uut.getUniversalLabel(), KlvConstants.AnnotationActiveSamplesPerLineUl);
    }

    @Test
    public void checkDisplayHeader() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(640));
        assertEquals(uut.displayHeader(), "ST 0602, Active Samples per Line");
    }

    @Test
    public void checkValidKeys() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(640));
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveSamplesPerLine));
        assertEquals(
                uut.getField(AnnotationMetadataKey.ActiveSamplesPerLine).getDisplayableValue(),
                "640");
    }

    @Test
    public void checkInvalidKeys() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(640));
        assertEquals(uut.getIdentifiers().size(), 1);
        assertFalse(uut.getIdentifiers().contains(AnnotationMetadataKey.EventIndication));
        assertNull(uut.getField(AnnotationMetadataKey.EventIndication));
    }

    @Test
    public void checkInvalidKeysNull() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage((ActiveSamplesPerLine) null);
        assertEquals(uut.getIdentifiers().size(), 0);
        assertFalse(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveSamplesPerLine));
        assertNull(uut.getField(AnnotationMetadataKey.ActiveSamplesPerLine));
    }

    @Test
    public void checkParseFromBytes() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(
                        new byte[] {
                            0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01,
                            0x02, 0x00, 0x00, 0x00, 0x02, 0x05, 0x00
                        });
        assertTrue(uut.getIdentifiers().contains(AnnotationMetadataKey.ActiveSamplesPerLine));
        assertEquals(
                uut.getField(AnnotationMetadataKey.ActiveSamplesPerLine).getDisplayableValue(),
                "1280");
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Unexpected Annotation Active Samples per Line Universal Label passed in")
    public void checkParseWithBadLabel() throws KlvParseException {
        new ActiveSamplesPerLineMessage(
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
                    0x05,
                    0x01,
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
                    "Annotation Active Samples per Line Universal Label must have length 19")
    public void checkParseWithReallyShortLength() throws KlvParseException {
        new ActiveSamplesPerLineMessage(new byte[] {0x06});
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Active Samples per Line Universal Label must have length 19")
    public void checkParseWithBadLength() throws KlvParseException {
        new ActiveSamplesPerLineMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x04, 0x00, 0x01
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Active Samples per Line Universal Label must be specified as length 2")
    public void checkParseWithBadLengthValue() throws KlvParseException {
        new ActiveSamplesPerLineMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x01, 0x04, 0x00
                });
    }

    @Test
    public void checkFraming() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(1280));
        byte[] bytes = uut.frameMessage(false);
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x05, 0x00
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkFramingNested() throws KlvParseException {
        ActiveSamplesPerLineMessage uut =
                new ActiveSamplesPerLineMessage(new ActiveSamplesPerLine(1280));
        uut.frameMessage(true);
    }
}
