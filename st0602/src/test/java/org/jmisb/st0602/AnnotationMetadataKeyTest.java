package org.jmisb.st0602;

import static org.testng.Assert.*;

import org.jmisb.api.klv.KlvConstants;
import org.testng.annotations.Test;

public class AnnotationMetadataKeyTest {
    @Test
    public void checkUnknownKey() {
        assertEquals(
                AnnotationMetadataKey.getKey(KlvConstants.GeneralizedTransformationUl),
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
