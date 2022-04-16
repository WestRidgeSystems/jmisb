package org.jmisb.st0602;

import static org.testng.Assert.*;

import org.jmisb.api.klv.UniversalLabel;
import org.testng.annotations.Test;

public class AnnotationMetadataKeyTest {
    @Test
    public void checkUnknownKey() {
        assertEquals(
                AnnotationMetadataKey.getKey(
                        new UniversalLabel(
                                new byte[] {
                                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01,
                                    0x03, 0x05, 0x05, 0x00, 0x00, 0x00
                                })),
                AnnotationMetadataKey.Undefined);
    }

    @Test
    public void checkGetter() {
        assertEquals(
                AnnotationMetadataKey.ByteOrder.getUl(),
                AnnotationByteOrderMessage.AnnotationByteOrderUl);
    }

    @Test
    public void checkIdentifierIdentity() {
        assertEquals(
                AnnotationMetadataKey.MIMEMediaType.getIdentifier(),
                AnnotationMetadataKey.MIMEMediaType.getIdentifier());
    }

    @Test
    public void checkIdentifierDifferent() {
        assertNotEquals(
                AnnotationMetadataKey.MIMEMediaType.getIdentifier(),
                AnnotationMetadataKey.AnnotationSource.getIdentifier());
    }
}
