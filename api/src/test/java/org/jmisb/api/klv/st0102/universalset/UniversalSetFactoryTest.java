package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UniversalSetFactoryTest
{
    @Test
    public void testCreate()
    {
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.SecurityClassification,
                "UNCLASSIFIED//".getBytes());
        Assert.assertTrue(value instanceof ClassificationUniversal);
        ClassificationUniversal classification = (ClassificationUniversal) value;
        Assert.assertEquals(classification.getClassification(), Classification.UNCLASSIFIED);

        String ccMethod = "GENC Two Letter";
        value = UniversalSetFactory.createValue(SecurityMetadataKey.CcCodingMethod,
                ccMethod.getBytes());
        Assert.assertTrue(value instanceof SecurityMetadataString);
        SecurityMetadataString string = (SecurityMetadataString) value;
        Assert.assertEquals(string.getValue(), ccMethod);
    }
}