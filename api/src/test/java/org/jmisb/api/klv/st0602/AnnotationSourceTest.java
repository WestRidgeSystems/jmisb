package org.jmisb.api.klv.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Annotation Source implementation. */
public class AnnotationSourceTest {

    public AnnotationSourceTest() {}

    @Test
    public void checkConstruction() throws KlvParseException {
        AnnotationSource uut = new AnnotationSource(3);
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(
                uut.getDisplayableValue(),
                "Automated from BE/RWAC|Automated from user defined latitude/longitude");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test
    public void checkConstructionFromBytes() throws KlvParseException {
        AnnotationSource uut = new AnnotationSource(new byte[] {0x00, 0x00, 0x00, 0x03});
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(
                uut.getDisplayableValue(),
                "Automated from BE/RWAC|Automated from user defined latitude/longitude");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.AnnotationSource,
                        new byte[] {0x00, 0x00, 0x00, 0x03});
        assertTrue(value instanceof AnnotationSource);
        AnnotationSource uut = (AnnotationSource) value;
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(
                uut.getDisplayableValue(),
                "Automated from BE/RWAC|Automated from user defined latitude/longitude");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesShort() throws KlvParseException {
        new AnnotationSource(new byte[] {0x00, 0x00, 0x02});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkConstructionFromBytesLong() throws KlvParseException {
        new AnnotationSource(new byte[] {0x00, 0x00, 0x00, 0x00, 0x04});
    }

    @Test
    public void checkConstructionManual() throws KlvParseException {
        AnnotationSource uut = new AnnotationSource(0);
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(uut.getDisplayableValue(), "Manually Annotated");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x00});
    }

    @Test
    public void checkConstructionUserAOI() throws KlvParseException {
        AnnotationSource uut = new AnnotationSource(0x00000004);
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(uut.getDisplayableValue(), "Automated from user defined AOI");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x04});
    }

    @Test
    public void checkConstructionPixelIntel() throws KlvParseException {
        AnnotationSource uut = new AnnotationSource(0x00000008);
        assertEquals(uut.getDisplayName(), "Annotation Source");
        assertEquals(uut.getDisplayableValue(), "Automated from pixel intelligence");
        assertEquals(uut.getBytes(), new byte[] {0x00, 0x00, 0x00, 0x08});
    }
}
