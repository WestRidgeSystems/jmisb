package org.jmisb.api.klv.st0102.universalset;

import org.jmisb.api.klv.st0102.Classification;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.SecurityMetadataString;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ItemDesignatorId;
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

    @Test
    public void testCreateItemDesignatorId() {
        byte[] itemDesignatorId = new byte[]{(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
        ISecurityMetadataValue value = UniversalSetFactory.createValue(SecurityMetadataKey.ItemDesignatorId, itemDesignatorId);
        Assert.assertTrue(value instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) value;
        Assert.assertEquals(id.getItemDesignatorId(), itemDesignatorId);
    }
}
