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
    }

    @Test
    public void testFromEncodedValueConstructor() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b10011111);
        assertFalse(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b10011111);
    }

    @Test
    public void testFromEncodedValueConstructorLocked() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b00011111);
        assertTrue(timeStatus.isLocked());
        assertFalse(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b00011111);
    }

    @Test
    public void testFromEncodedValueConstructorForwardDiscontinuity() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b11011111);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertFalse(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11011111);
    }

    @Test
    public void testFromEncodedValueConstructorReverseDiscontinuity() {
        TimeStatus timeStatus = new TimeStatus((byte) 0b11111111);
        assertFalse(timeStatus.isLocked());
        assertTrue(timeStatus.isDiscontinuity());
        assertTrue(timeStatus.isReverse());
        assertEquals(timeStatus.getEncodedValue(), (byte) 0b11111111);
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
}
