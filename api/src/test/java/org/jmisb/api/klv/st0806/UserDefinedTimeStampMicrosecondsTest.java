package org.jmisb.api.klv.st0806;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserDefinedTimeStampMicrosecondsTest {
    // Example from MISB ST 0601 doc
    @Test
    public void testExample() {
        // Convert byte[] -> value
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xf4,
                    (byte) 0xA6,
                    (byte) 0xaa,
                    (byte) 0x4a,
                    (byte) 0xa8
                };
        UserDefinedTimeStampMicroseconds pts = new UserDefinedTimeStampMicroseconds(bytes);
        Assert.assertEquals(pts.getDisplayName(), "User Defined Time Stamp");
        Assert.assertEquals(pts.getDisplayableValue(), "1224807209913000");
        LocalDateTime dateTime = pts.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2008);
        Assert.assertEquals(dateTime.getMonth(), Month.OCTOBER);
        Assert.assertEquals(dateTime.getDayOfMonth(), 24);
        Assert.assertEquals(dateTime.getHour(), 0);
        Assert.assertEquals(dateTime.getMinute(), 13);
        Assert.assertEquals(dateTime.getSecond(), 29);
        Assert.assertEquals(dateTime.getNano(), 913000000);

        // Convert value -> byte[]
        long microseconds = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() * 1000;
        UserDefinedTimeStampMicroseconds pts2 = new UserDefinedTimeStampMicroseconds(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "User Defined Time Stamp");
        Assert.assertEquals(
                pts2.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xf4,
                    (byte) 0xA6,
                    (byte) 0xaa,
                    (byte) 0x4a,
                    (byte) 0xa8
                });

        Assert.assertEquals(microseconds, 1224807209913000L);
        Assert.assertEquals(pts2.getDisplayableValue(), "1224807209913000");
    }

    @Test
    public void testFactoryExample() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x59,
                    (byte) 0xf4,
                    (byte) 0xA6,
                    (byte) 0xaa,
                    (byte) 0x4a,
                    (byte) 0xa8
                };
        IRvtMetadataValue v =
                RvtLocalSet.createValue(RvtMetadataKey.UserDefinedTimeStampMicroseconds, bytes);
        Assert.assertTrue(v instanceof UserDefinedTimeStampMicroseconds);
        Assert.assertEquals(v.getDisplayName(), "User Defined Time Stamp");
        UserDefinedTimeStampMicroseconds pts = (UserDefinedTimeStampMicroseconds) v;
        Assert.assertEquals(pts.getDisplayableValue(), "1224807209913000");
        LocalDateTime dateTime = pts.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2008);
        Assert.assertEquals(dateTime.getMonth(), Month.OCTOBER);
        Assert.assertEquals(dateTime.getDayOfMonth(), 24);
        Assert.assertEquals(dateTime.getHour(), 0);
        Assert.assertEquals(dateTime.getMinute(), 13);
        Assert.assertEquals(dateTime.getSecond(), 29);
        Assert.assertEquals(dateTime.getNano(), 913000000);
    }

    @Test
    public void testNow() {
        LocalDateTime now = LocalDateTime.now();
        UserDefinedTimeStampMicroseconds pts = new UserDefinedTimeStampMicroseconds(now);
        Assert.assertEquals(pts.getDisplayName(), "User Defined Time Stamp");
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(pts.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = pts.getMicroseconds();
        long nowMicroseconds =
                ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant(ZoneOffset.UTC));
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax() {
        UserDefinedTimeStampMicroseconds pts = new UserDefinedTimeStampMicroseconds(0L);
        Assert.assertEquals(pts.getDisplayName(), "User Defined Time Stamp");
        Assert.assertEquals(pts.getDateTime().getYear(), 1970);
        Assert.assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(pts.getDisplayableValue(), "0");

        // Create max value and ensure no exception is thrown
        pts = new UserDefinedTimeStampMicroseconds(Long.MAX_VALUE);
        Assert.assertEquals(pts.getDisplayName(), "User Defined Time Stamp");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new UserDefinedTimeStampMicroseconds(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        // Oct 12, 2263 at 08:30
        new UserDefinedTimeStampMicroseconds(LocalDateTime.of(2263, 10, 12, 8, 30));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new UserDefinedTimeStampMicroseconds(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
