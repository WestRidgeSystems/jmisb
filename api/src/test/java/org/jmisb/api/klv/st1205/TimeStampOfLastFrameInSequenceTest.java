package org.jmisb.api.klv.st1205;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TimeStampOfLastFrameInSequenceTest {
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
        TimeStampOfLastFrameInSequence pts = new TimeStampOfLastFrameInSequence(bytes);
        Assert.assertEquals(pts.getDisplayName(), "Time Stamp of Last Frame in Sequence");
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
        TimeStampOfLastFrameInSequence pts2 = new TimeStampOfLastFrameInSequence(microseconds);
        Assert.assertEquals(pts2.getDisplayName(), "Time Stamp of Last Frame in Sequence");
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
        ICalibrationMetadataValue v =
                CalibrationLocalSet.createValue(
                        CalibrationPackMetadataKey.TimeStampOfLastFrameInSequence, bytes);
        Assert.assertTrue(v instanceof TimeStampOfLastFrameInSequence);
        Assert.assertEquals(v.getDisplayName(), "Time Stamp of Last Frame in Sequence");
        TimeStampOfLastFrameInSequence pts = (TimeStampOfLastFrameInSequence) v;
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TimeStampOfLastFrameInSequence(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        // Oct 12, 2263 at 08:30
        new TimeStampOfLastFrameInSequence(LocalDateTime.of(2263, 10, 12, 8, 30));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TimeStampOfLastFrameInSequence(new byte[] {0x00, 0x00, 0x00, 0x00});
    }
}
