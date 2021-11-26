package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 MIME Media Type implementation. */
public class MIMEMediaTypeTest {

    public MIMEMediaTypeTest() {}

    @Test
    public void checkPNG() {
        MIMEMediaType uut = new MIMEMediaType("image/png");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/png");
        assertTrue(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(), new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x70, 0x6e, 0x67});
    }

    @Test
    public void checkPNGFromBytes() {
        MIMEMediaType uut =
                new MIMEMediaType(
                        new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x70, 0x6e, 0x67});
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/png");
        assertTrue(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(), new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x70, 0x6e, 0x67});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.MIMEMediaType,
                        new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x70, 0x6e, 0x67});
        assertTrue(value instanceof MIMEMediaType);
        MIMEMediaType uut = (MIMEMediaType) value;
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/png");
        assertTrue(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(), new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x70, 0x6e, 0x67});
    }

    @Test
    public void checkBMP() {
        MIMEMediaType uut = new MIMEMediaType("image/x-ms-bmp");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/x-ms-bmp");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertTrue(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x78, 0x2d, 0x6d, 0x73, 0x2d, 0x62, 0x6d,
                    0x70
                });
    }

    @Test
    public void checkBMPFromBytes() {
        MIMEMediaType uut =
                new MIMEMediaType(
                        new byte[] {
                            0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x78, 0x2d, 0x6d, 0x73, 0x2d, 0x62,
                            0x6d, 0x70
                        });
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/x-ms-bmp");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertTrue(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x78, 0x2d, 0x6d, 0x73, 0x2d, 0x62, 0x6d,
                    0x70
                });
    }

    @Test
    public void checkJPEG() {
        MIMEMediaType uut = new MIMEMediaType("image/jpeg");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/jpeg");
        assertFalse(uut.isPNG());
        assertTrue(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x6a, 0x70, 0x65, 0x67});
    }

    @Test
    public void checkJPEGFromBytes() {
        MIMEMediaType uut =
                new MIMEMediaType(
                        new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x6a, 0x70, 0x65, 0x67});
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/jpeg");
        assertFalse(uut.isPNG());
        assertTrue(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x6a, 0x70, 0x65, 0x67});
    }

    @Test
    public void checkCGM() {
        MIMEMediaType uut = new MIMEMediaType("image/cgm");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/cgm");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertTrue(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(), new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x63, 0x67, 0x6d});
    }

    @Test
    public void checkCGMFromBytes() {
        MIMEMediaType uut =
                new MIMEMediaType(
                        new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x63, 0x67, 0x6d});
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/cgm");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertTrue(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(
                uut.getBytes(), new byte[] {0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x63, 0x67, 0x6d});
    }

    @Test
    public void checkCGMLegacy() {
        MIMEMediaType uut = new MIMEMediaType("cgm");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "cgm");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertTrue(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(uut.getBytes(), new byte[] {0x63, 0x67, 0x6d});
    }

    @Test
    public void checkCGMLegacyFromBytes() {
        MIMEMediaType uut = new MIMEMediaType(new byte[] {0x63, 0x67, 0x6d});
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "cgm");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertTrue(uut.isCGM());
        assertFalse(uut.isSVG());
        assertEquals(uut.getBytes(), new byte[] {0x63, 0x67, 0x6d});
    }

    @Test
    public void checkSVG() {
        MIMEMediaType uut = new MIMEMediaType("image/svg+xml");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/svg+xml");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertTrue(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x73, 0x76, 0x67, 0x2b, 0x78, 0x6d, 0x6c
                });
    }

    @Test
    public void checkSVGFromBytes() {
        MIMEMediaType uut =
                new MIMEMediaType(
                        new byte[] {
                            0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x73, 0x76, 0x67, 0x2b, 0x78, 0x6d,
                            0x6c
                        });
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "image/svg+xml");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertTrue(uut.isSVG());
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x2f, 0x73, 0x76, 0x67, 0x2b, 0x78, 0x6d, 0x6c
                });
    }

    @Test
    public void checkUnknownMIMEType() {
        MIMEMediaType uut = new MIMEMediaType("text/plain");
        assertEquals(uut.getDisplayName(), "MIME Media Type");
        assertEquals(uut.getDisplayableValue(), "text/plain");
        assertFalse(uut.isPNG());
        assertFalse(uut.isJPEG());
        assertFalse(uut.isBMP());
        assertFalse(uut.isCGM());
        assertFalse(uut.isSVG());
    }
}
