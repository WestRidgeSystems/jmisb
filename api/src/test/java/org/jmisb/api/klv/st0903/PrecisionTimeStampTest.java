package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0601.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.common.KlvParseException;

public class PrecisionTimeStampTest
{
    // Example from MISB ST doc
    @Test
    public void testExample()
    {
        // Convert byte[] -> value
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40};
        PrecisionTimeStamp pts = new PrecisionTimeStamp(bytes);
        Assert.assertEquals(pts.getDisplayName(), "Precision Time Stamp");
        Assert.assertEquals(pts.getDisplayableValue(), "987654321000000");
        ZonedDateTime dateTime = pts.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2001);
        Assert.assertEquals(dateTime.getMonth(), Month.APRIL);
        Assert.assertEquals(dateTime.getDayOfMonth(), 19);
        Assert.assertEquals(dateTime.getHour(), 4);
        Assert.assertEquals(dateTime.getMinute(), 25);
        Assert.assertEquals(dateTime.getSecond(), 21);
        Assert.assertEquals(dateTime.getNano(), 0);

        // Convert value -> byte[]
        long microseconds = dateTime.toInstant().toEpochMilli() * 1000;
        PrecisionTimeStamp pts2 = new PrecisionTimeStamp(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "Precision Time Stamp");
        Assert.assertEquals(pts2.getBytes(), new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40});

        Assert.assertEquals(microseconds, 987654321000000L);
        Assert.assertEquals(pts2.getDisplayableValue(), "987654321000000");
    }

    @Test
    public void testFactoryExample() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40};
        IVmtiMetadataValue v = VmtiMetadataValueFactory.createValue(VmtiMetadataKey.PrecisionTimeStamp, bytes);
        Assert.assertTrue(v instanceof PrecisionTimeStamp);
        Assert.assertEquals(v.getDisplayName(), "Precision Time Stamp");
        PrecisionTimeStamp pts = (PrecisionTimeStamp)v;
        Assert.assertEquals(pts.getDisplayableValue(), "987654321000000");
        ZonedDateTime dateTime = pts.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2001);
        Assert.assertEquals(dateTime.getMonth(), Month.APRIL);
        Assert.assertEquals(dateTime.getDayOfMonth(), 19);
        Assert.assertEquals(dateTime.getHour(), 4);
        Assert.assertEquals(dateTime.getMinute(), 25);
        Assert.assertEquals(dateTime.getSecond(), 21);
        Assert.assertEquals(dateTime.getNano(), 0);
    }

    @Test
    public void testNow()
    {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        PrecisionTimeStamp pts = new PrecisionTimeStamp(now);
        Assert.assertEquals(pts.getDisplayName(), "Precision Time Stamp");
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = pts.getMicroseconds();
        long nowMicroseconds = ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant());
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax()
    {
        PrecisionTimeStamp pts = new PrecisionTimeStamp(0L);
        Assert.assertEquals(pts.getDisplayName(), "Precision Time Stamp");
        Assert.assertEquals(pts.getDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(pts.getDisplayableValue(), "0");

        // Create max value and ensure no exception is thrown
        pts = new PrecisionTimeStamp(Long.MAX_VALUE);
        Assert.assertEquals(pts.getDisplayName(), "Precision Time Stamp");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PrecisionTimeStamp(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        // Oct 12, 2263 at 08:30
        new PrecisionTimeStamp(ZonedDateTime.of(2263, 10, 12, 8, 30, 0, 0, ZoneId.of("UTC")));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PrecisionTimeStamp(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
