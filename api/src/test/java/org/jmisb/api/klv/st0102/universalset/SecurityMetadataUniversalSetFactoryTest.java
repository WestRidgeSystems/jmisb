package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0102.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SecurityMetadataUniversalSetFactoryTest {
    @Test
    public void parseBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x02, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x02, (byte) 0x08, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x6b, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x03, (byte) 0x02, (byte) 0x08, (byte) 0x02, (byte) 0x01, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x0e, (byte) 0x55, (byte) 0x4e, (byte) 0x43, (byte) 0x4c,
                            (byte) 0x41, (byte) 0x53,
                    (byte) 0x53, (byte) 0x49, (byte) 0x46, (byte) 0x49, (byte) 0x45, (byte) 0x44,
                            (byte) 0x2f, (byte) 0x2f,
                    (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01, (byte) 0x01,
                            (byte) 0x01, (byte) 0x03,
                    (byte) 0x07, (byte) 0x01, (byte) 0x20, (byte) 0x01, (byte) 0x02, (byte) 0x07,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x13, (byte) 0x49, (byte) 0x53, (byte) 0x4f, (byte) 0x2d, (byte) 0x33,
                            (byte) 0x31, (byte) 0x36,
                    (byte) 0x36, (byte) 0x20, (byte) 0x54, (byte) 0x77, (byte) 0x6f, (byte) 0x20,
                            (byte) 0x4c, (byte) 0x65,
                    (byte) 0x74, (byte) 0x74, (byte) 0x65, (byte) 0x72, (byte) 0x06, (byte) 0x0e,
                            (byte) 0x2b, (byte) 0x34,
                    (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x03, (byte) 0x07, (byte) 0x01,
                            (byte) 0x20, (byte) 0x01,
                    (byte) 0x02, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x2f,
                            (byte) 0x2f, (byte) 0x55,
                    (byte) 0x53, (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34, (byte) 0x01,
                            (byte) 0x01, (byte) 0x01,
                    (byte) 0x01, (byte) 0x0e, (byte) 0x01, (byte) 0x02, (byte) 0x05, (byte) 0x04,
                            (byte) 0x00, (byte) 0x00,
                    (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x0C
                };
        SecurityMetadataUniversalSetFactory factory = new SecurityMetadataUniversalSetFactory();
        SecurityMetadataUniversalSet universalSet = factory.create(bytes);
        Assert.assertEquals(universalSet.displayHeader(), "ST 0102 (universal)");
        Assert.assertEquals(
                universalSet.getUniversalLabel(),
                SecurityMetadataUniversalSet.SecurityMetadataUniversalSetUl);
        Assert.assertEquals(universalSet.getKeys().size(), 4);
        Assert.assertTrue(
                universalSet.getKeys().contains(SecurityMetadataKey.SecurityClassification));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.SecurityClassification).getDisplayName(),
                "Classification");
        Assert.assertEquals(
                universalSet
                        .getField(SecurityMetadataKey.SecurityClassification)
                        .getDisplayableValue(),
                "UNCLASSIFIED//");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.CcCodingMethod));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.CcCodingMethod).getDisplayName(),
                "Country Coding Method");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.CcCodingMethod).getDisplayableValue(),
                "ISO-3166 Two Letter");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.ClassifyingCountry));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.ClassifyingCountry).getDisplayName(),
                "Classifying Country");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.ClassifyingCountry).getDisplayableValue(),
                "//US");
        Assert.assertTrue(universalSet.getKeys().contains(SecurityMetadataKey.Version));
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.Version).getDisplayName(),
                "ST0102 Version");
        Assert.assertEquals(
                universalSet.getField(SecurityMetadataKey.Version).getDisplayableValue(), "12");
    }
}
