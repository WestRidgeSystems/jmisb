package org.jmisb.api.klv.st0102.localset;

import static org.testng.Assert.assertEquals;

import org.jmisb.api.klv.st0102.*;
import org.testng.annotations.Test;

public class ClassificationLocalTest {
    @Test
    public void testBasicBuild() {
        ClassificationLocal classificationLocal =
                new ClassificationLocal(Classification.UNCLASSIFIED);
        assertEquals(classificationLocal.getClassification(), Classification.UNCLASSIFIED);
        assertEquals(classificationLocal.getDisplayName(), "Classification");
        assertEquals(classificationLocal.getDisplayableValue(), "UNCLASSIFIED");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIllegalByteArrayLength() {
        new ClassificationLocal(new byte[] {0x01, 0x02});
    }
}
