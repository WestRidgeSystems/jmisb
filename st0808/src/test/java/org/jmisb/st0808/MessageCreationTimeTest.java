package org.jmisb.st0808;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageCreationTimeTest {
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
        MessageCreationTime timestamp = new MessageCreationTime(bytes);
        Assert.assertEquals(timestamp.getDisplayName(), "Message Creation Time");
        Assert.assertEquals(timestamp.getDisplayableValue(), "2008-10-24T00:13:29.913");
        LocalDateTime dateTime = timestamp.getDateTime();

        Assert.assertEquals(dateTime.getYear(), 2008);
        Assert.assertEquals(dateTime.getMonth(), Month.OCTOBER);
        Assert.assertEquals(dateTime.getDayOfMonth(), 24);
        Assert.assertEquals(dateTime.getHour(), 0);
        Assert.assertEquals(dateTime.getMinute(), 13);
        Assert.assertEquals(dateTime.getSecond(), 29);
        Assert.assertEquals(dateTime.getNano(), 913000000);

        // Convert value -> byte[]
        long microseconds = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli() * 1000;
        MessageCreationTime pts2 = new MessageCreationTime(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "Message Creation Time");
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
        Assert.assertEquals(pts2.getDisplayableValue(), "2008-10-24T00:13:29.913");
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
        IAncillaryTextMetadataValue v =
                AncillaryTextLocalSet.createValue(
                        AncillaryTextMetadataKey.MessageCreationTime, bytes);
        Assert.assertTrue(v instanceof MessageCreationTime);
        Assert.assertEquals(v.getDisplayName(), "Message Creation Time");
        MessageCreationTime timestamp = (MessageCreationTime) v;
        Assert.assertEquals(timestamp.getDisplayableValue(), "2008-10-24T00:13:29.913");
        LocalDateTime dateTime = timestamp.getDateTime();

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
        MessageCreationTime timestamp = new MessageCreationTime(now);
        Assert.assertEquals(timestamp.getDisplayName(), "Message Creation Time");
        Assert.assertEquals(timestamp.getDateTime().getDayOfMonth(), now.getDayOfMonth());
        Assert.assertEquals(timestamp.getDateTime().getHour(), now.getHour());
        long ptsMicroseconds = timestamp.getMicroseconds();
        long nowMicroseconds =
                ChronoUnit.MICROS.between(Instant.EPOCH, now.toInstant(ZoneOffset.UTC));
        Assert.assertEquals(ptsMicroseconds, nowMicroseconds);
    }

    @Test
    public void testMinAndMax() {
        MessageCreationTime timestamp = new MessageCreationTime(0L);
        Assert.assertEquals(timestamp.getDisplayName(), "Message Creation Time");
        Assert.assertEquals(timestamp.getDateTime().getYear(), 1970);
        Assert.assertEquals(timestamp.getDateTime().getMonth(), Month.JANUARY);
        Assert.assertEquals(timestamp.getDateTime().getDayOfMonth(), 1);
        Assert.assertEquals(timestamp.getDisplayableValue(), "1970-01-01T00:00:00");

        // Create max value and ensure no exception is thrown
        timestamp = new MessageCreationTime(Long.MAX_VALUE);
        Assert.assertEquals(timestamp.getDisplayName(), "Message Creation Time");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new MessageCreationTime(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        // Oct 12, 2263 at 08:30
        new MessageCreationTime(LocalDateTime.of(2263, 10, 12, 8, 30));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new MessageCreationTime(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
