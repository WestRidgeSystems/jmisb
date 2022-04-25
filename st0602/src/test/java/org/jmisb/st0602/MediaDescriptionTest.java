package org.jmisb.st0602;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for ST 0602 Media Description implementation. */
public class MediaDescriptionTest {

    public MediaDescriptionTest() {}

    @Test
    public void checkConstruction() {
        MediaDescription uut = new MediaDescription("Some notes");
        assertEquals(uut.getDisplayName(), "Media Description");
        assertEquals(uut.getDisplayableValue(), "Some notes");
        assertEquals(
                uut.getBytes(),
                new byte[] {0x53, 0x6f, 0x6d, 0x65, 0x20, 0x6e, 0x6f, 0x74, 0x65, 0x73});
    }

    @Test
    public void checkConstructionFromBytes() {
        MediaDescription uut =
                new MediaDescription(
                        new byte[] {0x53, 0x6f, 0x6d, 0x65, 0x20, 0x6e, 0x6f, 0x74, 0x65, 0x73});
        assertEquals(uut.getDisplayName(), "Media Description");
        assertEquals(uut.getDisplayableValue(), "Some notes");
        assertEquals(
                uut.getBytes(),
                new byte[] {0x53, 0x6f, 0x6d, 0x65, 0x20, 0x6e, 0x6f, 0x74, 0x65, 0x73});
    }

    @Test
    public void checkConstructionFromFactory() throws KlvParseException {
        IAnnotationMetadataValue value =
                UniversalSetFactory.createValue(
                        AnnotationMetadataKey.MediaDescription,
                        new byte[] {0x53, 0x6f, 0x6d, 0x65, 0x20, 0x6e, 0x6f, 0x74, 0x65, 0x73});
        assertTrue(value instanceof MediaDescription);
        MediaDescription uut = (MediaDescription) value;
        assertEquals(uut.getDisplayName(), "Media Description");
        assertEquals(uut.getDisplayableValue(), "Some notes");
        assertEquals(
                uut.getBytes(),
                new byte[] {0x53, 0x6f, 0x6d, 0x65, 0x20, 0x6e, 0x6f, 0x74, 0x65, 0x73});
    }
}
