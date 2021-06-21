package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Compression Level (ST 1108 Tag 7). */
public class CompressionLevelTest {

    @Test
    public void testConstructFromValue() {
        CompressionLevel uut = new CompressionLevel("4.2");
        assertEquals(uut.getDisplayName(), "Compression Level");
        assertEquals(uut.getDisplayableValue(), "4.2");
        assertEquals(uut.getCompressionLevel(), "4.2");
    }

    @Test
    public void testConstructFromBytes() {
        CompressionLevel uut = new CompressionLevel(new byte[] {(byte) 0x35});
        assertEquals(uut.getDisplayName(), "Compression Level");
        assertEquals(uut.getDisplayableValue(), "5");
        assertEquals(uut.getCompressionLevel(), "5");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new CompressionLevel("blah");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new CompressionLevel(new byte[] {0x31, 0x32, 0x33, 0x34});
    }
}
