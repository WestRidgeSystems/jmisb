package org.jmisb.st0601;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class EventStartTimeUtcTest {
    // Example from MISB ST doc
    @Test
    public void testExample() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0xD5,
                    (byte) 0xCF,
                    (byte) 0x4D,
                    (byte) 0xDC,
                    (byte) 0x9A,
                    (byte) 0x35
                };
        EventStartTimeUtc timestamp = new EventStartTimeUtc(bytes);
        checkExampleValue(timestamp);
    }

    @Test
    public void testContructFromValue() {
        EventStartTimeUtc timestamp = new EventStartTimeUtc(798036294670901L);
        checkExampleValue(timestamp);
    }

    @Test
    public void testContructFromDateTime() {
        LocalDateTime ldt = LocalDateTime.of(1995, Month.APRIL, 16, 12, 44, 54, 670901000);
        EventStartTimeUtc timestamp = new EventStartTimeUtc(ldt);
        checkExampleValue(timestamp);
    }

    protected void checkExampleValue(EventStartTimeUtc timestamp) {
        assertEquals(timestamp.getDisplayName(), "Event Start Time UTC");
        // April 16, 1995. 13:44:54
        assertEquals(timestamp.getDisplayableValue(), "1995-04-16T12:44:54.670901");
        LocalDateTime dateTime = timestamp.getDateTime();
        assertEquals(dateTime.getYear(), 1995);
        assertEquals(dateTime.getMonth(), Month.APRIL);
        assertEquals(dateTime.getDayOfMonth(), 16);
        assertEquals(dateTime.getHour(), 12);
        assertEquals(dateTime.getMinute(), 44);
        assertEquals(dateTime.getSecond(), 54);
        assertEquals(dateTime.getNano(), 670901000);
        assertEquals(timestamp.getMicroseconds(), 798036294670901L);
        assertEquals(
                timestamp.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0xD5,
                    (byte) 0xCF,
                    (byte) 0x4D,
                    (byte) 0xDC,
                    (byte) 0x9A,
                    (byte) 0x35
                });
    }

    @Test
    public void testFactoryExample() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0xD5,
                    (byte) 0xCF,
                    (byte) 0x4D,
                    (byte) 0xDC,
                    (byte) 0x9A,
                    (byte) 0x35
                };
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.EventStartTimeUtc, bytes);
        assertTrue(v instanceof EventStartTimeUtc);
        assertEquals(v.getDisplayName(), "Event Start Time UTC");
        EventStartTimeUtc timestamp = (EventStartTimeUtc) v;
        checkExampleValue(timestamp);
    }

    @Test
    public void testMinAndMax() {
        EventStartTimeUtc pts = new EventStartTimeUtc(0L);
        assertEquals(pts.getDisplayName(), "Event Start Time UTC");
        assertEquals(pts.getDateTime().getYear(), 1970);
        assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        assertEquals(pts.getDisplayableValue(), "1970-01-01T00:00:00");

        // Create max value and ensure no exception is thrown
        pts = new EventStartTimeUtc(Long.MAX_VALUE);
        assertEquals(pts.getDisplayName(), "Event Start Time UTC");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new EventStartTimeUtc(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        // Oct 12, 2263 at 08:30
        new EventStartTimeUtc(LocalDateTime.of(2263, 10, 12, 8, 30));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new EventStartTimeUtc(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
