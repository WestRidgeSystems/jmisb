package org.jmisb.api.klv.st0601;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransmissionFrequencyTest
{
    @Test
    public void testConstructFromValue()
    {
        // From ST:
        TransmissionFrequency freq = new TransmissionFrequency(2400.0);
        Assert.assertEquals(freq.getBytes(), new byte[]{(byte)0x02, (byte)0x57, (byte)0xc0});
        Assert.assertEquals(freq.getDisplayableValue(), "2400.00MHz");
        Assert.assertEquals(freq.getDisplayName(), "Transmission Frequency");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // From ST:
        TransmissionFrequency freq = new TransmissionFrequency(new byte[]{(byte)0x02, (byte)0x57, (byte)0xc0});
        Assert.assertEquals(freq.getFrequency(), 2400.0, 0.01);
        Assert.assertEquals(freq.getBytes(), new byte[]{(byte)0x02, (byte)0x57, (byte)0xc0});
        Assert.assertEquals(freq.getDisplayableValue(), "2400.00MHz");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x02, (byte)0x57, (byte)0xc0};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TransmissionFrequency, bytes);
        Assert.assertTrue(v instanceof TransmissionFrequency);
        TransmissionFrequency freq = (TransmissionFrequency)v;
        Assert.assertEquals(freq.getFrequency(), 2400.0, 0.01);
        Assert.assertEquals(freq.getBytes(), new byte[]{(byte)0x02, (byte)0x57, (byte)0xC0});
        Assert.assertEquals(freq.getDisplayableValue(), "2400.00MHz");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TransmissionFrequency(0.999);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new TransmissionFrequency(100000.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLong()
    {
        new TransmissionFrequency(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
