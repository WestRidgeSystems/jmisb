package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ByteOrderMessage. */
public class ByteOrderMessageTest {

    public ByteOrderMessageTest() {}

    @Test
    public void checkLabel() throws KlvParseException {
        AnnotationByteOrderMessage uut = new AnnotationByteOrderMessage();
        assertEquals(uut.getUniversalLabel(), AnnotationByteOrderMessage.AnnotationByteOrderUl);
    }

    @Test
    public void checkDisplayHeader() throws KlvParseException {
        AnnotationByteOrderMessage uut = new AnnotationByteOrderMessage();
        assertEquals(uut.displayHeader(), "ST 0602, Byte Order");
    }

    @Test
    public void checkParseFromBytes() throws KlvParseException {
        AnnotationByteOrderMessage uut =
                new AnnotationByteOrderMessage(
                        new byte[] {
                            0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01,
                            0x02, 0x00, 0x00, 0x00, 0x02, 0x4d, 0x4d
                        });
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(AnnotationMetadataKey.ByteOrder));
        assertEquals(uut.getField(AnnotationMetadataKey.ByteOrder).getDisplayName(), "Byte Order");
        assertEquals(
                uut.getField(AnnotationMetadataKey.ByteOrder).getDisplayableValue(),
                "Big Endian (MM)");
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Unexpected Annotation Byte Order Universal Label passed in")
    public void checkParseWithBadLabel() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x01,
                    0x01,
                    0x01,
                    0x01,
                    0x03,
                    0x01,
                    0x02,
                    0x01,
                    0x02,
                    0x00,
                    0x00,
                    (byte) 0xFF,
                    0x02,
                    0x4d,
                    0x4d
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Byte Order Universal Label must have length 19")
    public void checkParseWithReallyShortLength() throws KlvParseException {
        new AnnotationByteOrderMessage(new byte[] {0x06});
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Byte Order Universal Label must have length 19")
    public void checkParseWithBadLength() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x4d, 0x4d, 0x4d
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp =
                    "Annotation Byte Order Universal Label must be specified as length 2")
    public void checkParseWithBadLengthValue() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x01, 0x4d, 0x4d
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp = "Annotation Byte Order value must be 0x4D4D")
    public void checkParseWithBadValue1() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x4e, 0x4d
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp = "Annotation Byte Order value must be 0x4D4D")
    public void checkParseWithBadValue2() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x4d, 0x4e
                });
    }

    @Test(
            expectedExceptions = KlvParseException.class,
            expectedExceptionsMessageRegExp = "Annotation Byte Order value must be 0x4D4D")
    public void checkParseWithBadValueBoth() throws KlvParseException {
        new AnnotationByteOrderMessage(
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x49, 0x49
                });
    }

    @Test
    public void checkFraming() throws KlvParseException {
        AnnotationByteOrderMessage uut = new AnnotationByteOrderMessage();
        byte[] bytes = uut.frameMessage(false);
        assertEquals(
                bytes,
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01, 0x02,
                    0x00, 0x00, 0x00, 0x02, 0x4d, 0x4d
                });
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkFramingNested() throws KlvParseException {
        AnnotationByteOrderMessage uut = new AnnotationByteOrderMessage();
        uut.frameMessage(true);
    }
}
