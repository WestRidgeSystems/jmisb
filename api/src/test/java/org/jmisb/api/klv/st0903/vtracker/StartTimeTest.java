package org.jmisb.api.klv.st0903.vtracker;

import org.jmisb.api.klv.st0903.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.common.KlvParseException;

public class StartTimeTest
{
    // Example from MISB ST doc
    @Test
    public void testExample()
    {
        // Convert byte[] -> value
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40};
        StartTime pts = new StartTime(bytes);
        Assert.assertEquals(pts.getDisplayName(), "Start Time");
        Assert.assertEquals(pts.getDisplayableValue(), "987654321000000");
        LocalDateTime dateTime = pts.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2001);
        Assert.assertEquals(dateTime.getMonth(), Month.APRIL);
        Assert.assertEquals(dateTime.getDayOfMonth(), 19);
        Assert.assertEquals(dateTime.getHour(), 4);
        Assert.assertEquals(dateTime.getMinute(), 25);
        Assert.assertEquals(dateTime.getSecond(), 21);
        Assert.assertEquals(dateTime.getNano(), 0);

        // Convert value -> byte[]
        long microseconds = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() * 1000;
        StartTime pts2 = new StartTime(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "Start Time");
        Assert.assertEquals(pts2.getBytes(), new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40});

        Assert.assertEquals(microseconds, 987654321000000L);
        Assert.assertEquals(pts2.getDisplayableValue(), "987654321000000");
    }

    @Test
    public void testFactoryExample() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40};
        IVmtiMetadataValue v = VTrackerLS.createValue(VTrackerMetadataKey.startTime, bytes);
        Assert.assertTrue(v instanceof StartTime);
        Assert.assertEquals(v.getDisplayName(), "Start Time");
        StartTime pts = (StartTime)v;
        Assert.assertEquals(pts.getDisplayableValue(), "987654321000000");
        LocalDateTime dateTime = pts.getDateTime();

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
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        StartTime pts = new StartTime(now);
        Assert.assertEquals(pts.getDisplayName(), "Start Time");
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = pts.getMicroseconds();
        long nowMicroseconds = ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant(ZoneOffset.UTC));
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax()
    {
        StartTime pts = new StartTime(0L);
        Assert.assertEquals(pts.getDisplayName(), "Start Time");
        Assert.assertEquals(pts.getDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(pts.getDisplayableValue(), "0");

        // Create max value and ensure no exception is thrown
        pts = new StartTime(Long.MAX_VALUE);
        Assert.assertEquals(pts.getDisplayName(), "Start Time");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new StartTime(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        // Oct 12, 2263 at 08:30
        new StartTime(LocalDateTime.of(2263, 10, 12, 8, 30, 0, 0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new StartTime(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
