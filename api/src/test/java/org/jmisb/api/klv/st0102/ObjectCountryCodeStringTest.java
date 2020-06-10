package org.jmisb.api.klv.st0102;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ObjectCountryCodeStringTest
{
    @Test
    public void testConstructFromValue()
    {
        ObjectCountryCodeString objectCountryCode = new ObjectCountryCodeString("US;CA");
        Assert.assertEquals(objectCountryCode.getBytes().length, 10);
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(objectCountryCode.getBytes(), new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41});
    }

    @Test
    public void testConstructFromEncoded()
    {
        ObjectCountryCodeString objectCountryCode = new ObjectCountryCodeString(new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41});
        Assert.assertEquals(objectCountryCode.getValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(objectCountryCode.getBytes(), new byte[]{(byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x3b, (byte) 0x00, (byte) 0x43, (byte) 0x00, (byte) 0x41});
    }
}
