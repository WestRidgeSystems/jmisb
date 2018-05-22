package org.jmisb.api.klv.st0601;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

public class PrecisionTimeStampTest
{
    // Example from MISB ST doc
    @Test
    public void testExample()
    {
        // Convert byte[] -> value
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x04, (byte)0x59, (byte)0xf4,
                (byte)0xA6, (byte)0xaa, (byte)0x4a, (byte)0xa8};
        PrecisionTimeStamp pts = new PrecisionTimeStamp(bytes);
        LocalDateTime dateTime = pts.getLocalDateTime();

        Assert.assertEquals(dateTime.getYear(), 2008);
        Assert.assertEquals(dateTime.getMonth(), Month.OCTOBER);
        Assert.assertEquals(dateTime.getDayOfMonth(), 24);
        Assert.assertEquals(dateTime.getHour(), 0);
        Assert.assertEquals(dateTime.getMinute(), 13);
        Assert.assertEquals(dateTime.getSecond(), 29);
        Assert.assertEquals(dateTime.getNano(), 913000000);

        // Convert value -> byte[]
        long microseconds = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() * 1000;
        BigInteger val = BigInteger.valueOf(microseconds);
        PrecisionTimeStamp pts2 = new PrecisionTimeStamp(val);
        Assert.assertEquals(pts2.getBytes(), new byte[]{(byte)0x00, (byte)0x04, (byte)0x59, (byte)0xf4,
                (byte)0xA6, (byte)0xaa, (byte)0x4a, (byte)0xa8});

        BigInteger biMicroseconds = pts2.getMicroseconds();
        Assert.assertEquals(biMicroseconds, new BigInteger("1224807209913000"));
    }

    @Test
    public void testNow()
    {
        LocalDateTime now = LocalDateTime.now();
        PrecisionTimeStamp pts = new PrecisionTimeStamp(now);
        Assert.assertEquals(pts.getLocalDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getLocalDateTime().getHour(), now.getHour());
        Assert.assertEquals(pts.getLocalDateTime().getNano(), now.getNano());
    }

    @Test
    public void testMinAndMax()
    {
        PrecisionTimeStamp pts = new PrecisionTimeStamp(BigInteger.ZERO);
        Assert.assertEquals(pts.getLocalDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getLocalDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getLocalDateTime().getDayOfMonth(), 1);

        // Create max value and ensure no exception is thrown
        BigInteger maxInt = new BigInteger("18446744073709551615"); // 2^64 - 1
        new PrecisionTimeStamp(maxInt);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PrecisionTimeStamp(BigInteger.valueOf(-1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        BigInteger maxPlusOne = new BigInteger("18446744073709551616"); // 2^64
        new PrecisionTimeStamp(maxPlusOne);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PrecisionTimeStamp(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
