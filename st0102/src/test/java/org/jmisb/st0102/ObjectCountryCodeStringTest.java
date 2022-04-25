package org.jmisb.st0102;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ObjectCountryCodeStringTest extends LoggerChecks {
    public ObjectCountryCodeStringTest() {
        super(ObjectCountryCodeString.class);
    }

    @Test
    public void testConstructFromValue() {
        verifyNoLoggerMessages();
        ObjectCountryCodeString objectCountryCode = new ObjectCountryCodeString("US;CA");
        verifyNoLoggerMessages();
        Assert.assertEquals(objectCountryCode.getBytes().length, 10);
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(
                objectCountryCode.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x55,
                    (byte) 0x00,
                    (byte) 0x53,
                    (byte) 0x00,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x43,
                    (byte) 0x00,
                    (byte) 0x41
                });
    }

    @Test
    public void testConstructFromEncoded() {
        verifyNoLoggerMessages();
        ObjectCountryCodeString objectCountryCode =
                new ObjectCountryCodeString(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x55,
                            (byte) 0x00,
                            (byte) 0x53,
                            (byte) 0x00,
                            (byte) 0x3b,
                            (byte) 0x00,
                            (byte) 0x43,
                            (byte) 0x00,
                            (byte) 0x41
                        });
        verifyNoLoggerMessages();
        Assert.assertEquals(objectCountryCode.getValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "US;CA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(
                objectCountryCode.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x55,
                    (byte) 0x00,
                    (byte) 0x53,
                    (byte) 0x00,
                    (byte) 0x3b,
                    (byte) 0x00,
                    (byte) 0x43,
                    (byte) 0x00,
                    (byte) 0x41
                });
    }

    @Test
    public void testConstructFromEncodedUTF8() {
        verifyNoLoggerMessages();
        ObjectCountryCodeString objectCountryCode =
                new ObjectCountryCodeString(new byte[] {(byte) 0x55, (byte) 0x53, (byte) 0x41});
        verifySingleLoggerMessage(
                "Object Country Codes has too few bytes for required UTF-16 encoding. Trying UTF-8 workaround.");
        Assert.assertEquals(objectCountryCode.getValue(), "USA");
        Assert.assertEquals(objectCountryCode.getDisplayableValue(), "USA");
        Assert.assertEquals(objectCountryCode.getDisplayName(), "Object Country Codes");
        Assert.assertEquals(
                objectCountryCode.getBytes(),
                new byte[] {
                    (byte) 0x00, (byte) 0x55, (byte) 0x00, (byte) 0x53, (byte) 0x00, (byte) 0x41
                });
    }
}
