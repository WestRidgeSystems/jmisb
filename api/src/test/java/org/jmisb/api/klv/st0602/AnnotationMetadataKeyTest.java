package org.jmisb.api.klv.st0602;

import static org.testng.Assert.*;

import org.jmisb.api.klv.KlvConstants;
import org.testng.annotations.Test;

public class AnnotationMetadataKeyTest {
    @Test
    public void checkUnknownKey() {
        assertEquals(
                AnnotationMetadataKey.getKey(KlvConstants.VmtiLocalSetUl),
                AnnotationMetadataKey.Undefined);
    }

    @Test
    public void checkGetter() {
        assertEquals(
                AnnotationMetadataKey.ByteOrder.getUl(), AnnotationMetadataConstants.byteOrderUl);
    }
}
