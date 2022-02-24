package org.jmisb.api.klv.st0603;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ST0603 Time Status byte. */
public class TimeStatusTest {
    @Test
    public void testDefaultConstructor() {
        TimeStatus timeStatus = new TimeStatus();
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        assertEquals(timeStatus.toString(), "Not locked | No discontinuity");
    }

    @Test
    public void testFromEncodedValueConstructor() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b10011111);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        assertEquals(timeStatus.toString(), "Not locked | No discontinuity");
    }

    @Test
    public void testFromEncodedValueConstructorLocked() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b00011111);
        assertTrue(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b00011111);
        assertEquals(timeStatus.toString(), "Locked | No discontinuity");
    }

    @Test
    public void testFromEncodedValueConstructorForwardDiscontinuity() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b11011111);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11011111);
        assertEquals(timeStatus.toString(), "Not locked | Discontinuity - Forward");
    }

    @Test
    public void testFromEncodedValueConstructorReverseDiscontinuity() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b11111111);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertTrue(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11111111);
        assertEquals(timeStatus.toString(), "Not locked | Discontinuity - Reverse");
    }

    @Test
    public void testLockModifier() {
        TimeStatus timeStatus = new TimeStatus();
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        timeStatus.setLocked(true);
        assertTrue(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b00011111);
        timeStatus.setLocked(false);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
    }

    @Test
    public void testDiscontinuityModifier() {
        TimeStatus timeStatus = new TimeStatus();
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        timeStatus.setDiscontinuity(true, false);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11011111);
        timeStatus.setDiscontinuity(false, false);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        timeStatus.setDiscontinuity(false, true);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
        timeStatus.setDiscontinuity(true, true);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertTrue(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11111111);
        timeStatus.setDiscontinuity(false, true);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
    }

    @Test
    public void testCopy() {
        TimeStatus timeStatus = new TimeStatus();
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        TimeStatus copy = timeStatus.deepCopy();
        timeStatus.setLocked(true);
        timeStatus.setDiscontinuity(true, true);
        assertFalse(copy.isLocked());
        assertFalse(copy.isDiscontinuity());
        assertFalse(copy.isReverse());
        assertTrue(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertTrue(timeStatus.isReverse());
        copy = timeStatus.deepCopy();
        assertTrue(copy.isLocked());
        assertTrue(copy.isDiscontinuity());
        assertTrue(timeStatus.isReverse());
    }
}
