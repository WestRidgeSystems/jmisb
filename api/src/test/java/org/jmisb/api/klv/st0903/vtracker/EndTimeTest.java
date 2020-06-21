package org.jmisb.api.klv.st0903.vtracker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0903.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndTimeTest {
    // Example from MISB ST0903 start time. No distinct example for end time.
    @Test
    public void testExample() {
        // Convert byte[] -> value
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40
                };
        EndTime pts = new EndTime(bytes);
        Assert.assertEquals(pts.getDisplayName(), "End Time");
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
        EndTime pts2 = new EndTime(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "End Time");
        Assert.assertEquals(
                pts2.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40
                });

        Assert.assertEquals(microseconds, 987654321000000L);
        Assert.assertEquals(pts2.getDisplayableValue(), "987654321000000");
    }

    @Test
    public void testFactoryExample() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40
                };
        IVmtiMetadataValue v = VTrackerLS.createValue(VTrackerMetadataKey.endTime, bytes);
        Assert.assertTrue(v instanceof EndTime);
        Assert.assertEquals(v.getDisplayName(), "End Time");
        EndTime pts = (EndTime) v;
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
    public void testNow() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        EndTime pts = new EndTime(now);
        Assert.assertEquals(pts.getDisplayName(), "End Time");
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = pts.getMicroseconds();
        long nowMicroseconds =
                ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant(ZoneOffset.UTC));
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax() {
        EndTime pts = new EndTime(0L);
        Assert.assertEquals(pts.getDisplayName(), "End Time");
        Assert.assertEquals(pts.getDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(pts.getDisplayableValue(), "0");

        // Create max value and ensure no exception is thrown
        pts = new EndTime(Long.MAX_VALUE);
        Assert.assertEquals(pts.getDisplayName(), "End Time");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new EndTime(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        // Oct 12, 2263 at 08:30
        new EndTime(LocalDateTime.of(2263, 10, 12, 8, 30, 0, 0));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new EndTime(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
