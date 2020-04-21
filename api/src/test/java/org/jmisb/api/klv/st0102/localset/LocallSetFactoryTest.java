package org.jmisb.api.klv.st0102.localset;

import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.ISecurityMetadataValue;
import org.jmisb.api.klv.st0102.ItemDesignatorId;
import org.jmisb.api.klv.st0102.ObjectCountryCodeString;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LocallSetFactoryTest {

    @Test
    public void testItemDesignatorIdCreate()
    {
        byte[] itemDesignatorId = new byte[]{(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B, (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F};
        ISecurityMetadataValue value = LocalSetFactory.createValue(SecurityMetadataKey.ItemDesignatorId, itemDesignatorId);
        Assert.assertTrue(value instanceof ItemDesignatorId);
        ItemDesignatorId id = (ItemDesignatorId) value;
        Assert.assertEquals(id.getBytes(), itemDesignatorId);
        Assert.assertEquals(id.getItemDesignatorId(), itemDesignatorId);
    }

    @Test
    public void testObjectCountryCodeCreate()
    {
        byte[] bytes = new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41};
        ISecurityMetadataValue value = LocalSetFactory.createValue(SecurityMetadataKey.ObjectCountryCodes, bytes);
        Assert.assertTrue(value instanceof ObjectCountryCodeString);
        ObjectCountryCodeString objectCountryCode = (ObjectCountryCodeString) value;
        Assert.assertEquals(objectCountryCode.getBytes(), bytes);
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
    }
}
