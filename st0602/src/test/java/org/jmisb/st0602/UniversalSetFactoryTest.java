package org.jmisb.st0602;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Unit tests for UniversalSetFactory. */
public class UniversalSetFactoryTest {

    public UniversalSetFactoryTest() {}

    @Test
    public void checkUnknownEntryParse() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0e,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x27,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0F,
                    (byte) 0x0F,
                    (byte) 0x04,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x31
                };
        AnnotationMetadataUniversalSet universalSet = new AnnotationMetadataUniversalSet(bytes);
        Assert.assertEquals(universalSet.displayHeader(), "ST 0602, ID: Unknown");
        Assert.assertEquals(
                universalSet.getUniversalLabel(),
                AnnotationMetadataUniversalSet.AnnotationUniversalSetUl);
        // Verify we still got the part we can handle
        Assert.assertEquals(universalSet.getIdentifiers().size(), 1);
        Assert.assertTrue(
                universalSet.getIdentifiers().contains(AnnotationMetadataKey.EventIndication));
        Assert.assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayName(),
                "Event Indication");
        Assert.assertEquals(
                universalSet.getField(AnnotationMetadataKey.EventIndication).getDisplayableValue(),
                "NEW");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void checkBadValue() throws KlvParseException {
        UniversalSetFactory.createValue(AnnotationMetadataKey.Undefined, new byte[] {0x01, 0x02});
    }
}
