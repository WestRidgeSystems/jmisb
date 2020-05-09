package org.jmisb.api.klv.st0601;

import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.Month;
import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TakeOffTimeTest
{
    // Example from MISB ST doc
    @Test
    public void testExample()
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x05, (byte)0x6F, (byte)0x27, (byte)0x1B, (byte)0x5E, (byte)0x41, (byte)0xB7};
        TakeOffTime timestamp = new TakeOffTime(bytes);
        checkExampleValue(timestamp);
    }

    @Test
    public void testContructFromValue()
    {
        TakeOffTime timestamp = new TakeOffTime(1529588637122999L);
        checkExampleValue(timestamp);
    }

    @Test
    public void testContructFromDateTime()
    {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JUNE, 21, 13, 43, 57, 122999000);
        TakeOffTime timestamp = new TakeOffTime(ldt);
        checkExampleValue(timestamp);
    }

    protected void checkExampleValue(TakeOffTime timestamp)
    {
        assertEquals(timestamp.getDisplayName(), "Take Off Time");
        assertEquals(timestamp.getDisplayableValue(), "2018-06-21T13:43:57.122999");
        LocalDateTime dateTime = timestamp.getDateTime();
        assertEquals(dateTime.getYear(), 2018);
        assertEquals(dateTime.getMonth(), Month.JUNE);
        assertEquals(dateTime.getDayOfMonth(), 21);
        assertEquals(dateTime.getHour(), 13);
        assertEquals(dateTime.getMinute(), 43);
        assertEquals(dateTime.getSecond(), 57);
        assertEquals(dateTime.getNano(), 122999000);
        assertEquals(timestamp.getMicroseconds(), 1529588637122999L);
        assertEquals(timestamp.getBytes(), new byte[]{(byte)0x05, (byte)0x6F, (byte)0x27, (byte)0x1B, (byte)0x5E, (byte)0x41, (byte)0xB7});
    }

    @Test
    public void testFactoryExample() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x05, (byte)0x6F, (byte)0x27, (byte)0x1B, (byte)0x5E, (byte)0x41, (byte)0xB7};
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.TakeOffTime, bytes);
        assertTrue(v instanceof TakeOffTime);
        assertEquals(v.getDisplayName(), "Take Off Time");
        TakeOffTime timestamp = (TakeOffTime)v;
        checkExampleValue(timestamp);
    }

    @Test
    public void testExampleWithShortArray()
    {
        // Convert byte[] -> value
        byte[] bytes = new byte[]{(byte)0x05, (byte)0x6F, (byte)0x27, (byte)0x1B, (byte)0x5E, (byte)0x41, (byte)0xB7};
        TakeOffTime timestamp = new TakeOffTime(bytes);
        checkExampleValue(timestamp);
    }

    @Test
    public void testMinAndMax()
    {
        TakeOffTime pts = new TakeOffTime(0L);
        assertEquals(pts.getDisplayName(), "Take Off Time");
        assertEquals(pts.getDateTime().getYear(), 1970);
        assertEquals(pts.getDateTime().getMonth(), Month.JANUARY);
        assertEquals(pts.getDateTime().getDayOfMonth(), 1);
        assertEquals(pts.getDisplayableValue(), "1970-01-01T00:00:00");

        // Create max value and ensure no exception is thrown
        pts = new TakeOffTime(Long.MAX_VALUE);
        assertEquals(pts.getDisplayName(), "Take Off Time");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new TakeOffTime(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        // Oct 12, 2263 at 08:30
        new TakeOffTime(LocalDateTime.of(2263, 10, 12, 8, 30));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new TakeOffTime(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }
}
