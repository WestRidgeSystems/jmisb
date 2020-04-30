package org.jmisb.api.klv.st0603;

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

public class ST0603TimeStampTest
{
    // Example from MISB ST doc
    @Test
    public void testExample()
    {
        // Convert byte[] -> value
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40};
        ST0603TimeStamp pts = new ST0603TimeStamp(bytes);
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
        ST0603TimeStamp pts2 = new ST0603TimeStamp(microseconds);
        Assert.assertEquals(pts2.getBytes(), new byte[]{(byte)0x00, (byte)0x03, (byte)0x82, (byte)0x44, (byte)0x30, (byte)0xF6, (byte)0xCE, (byte)0x40});

        Assert.assertEquals(microseconds, 987654321000000L);
        Assert.assertEquals(pts2.getDisplayableValue(), "987654321000000");
    }

    @Test
    public void testNow()
    {
        LocalDateTime now = LocalDateTime.now();
        ST0603TimeStamp pts = new ST0603TimeStamp(now);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = pts.getMicroseconds();
        long nowMicroseconds = ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant(ZoneOffset.UTC));
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax()
    {
        ST0603TimeStamp pts = new ST0603TimeStamp(0L);
        Assert.assertEquals(pts.getDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(pts.getDisplayableValue(), "0");

        // Create max value and ensure no exception is thrown
        pts = new ST0603TimeStamp(Long.MAX_VALUE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new ST0603TimeStamp(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        // Oct 12, 2263 at 08:30
        new ST0603TimeStamp(LocalDateTime.of(2263, 10, 12, 8, 30, 0, 0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new ST0603TimeStamp(new byte[]{0x00, 0x00, 0x00, 0x00});
    }
}
