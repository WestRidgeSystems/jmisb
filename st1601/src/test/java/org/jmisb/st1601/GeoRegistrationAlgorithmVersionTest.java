package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for ST 1601 Algorithm Name. */
public class GeoRegistrationAlgorithmVersionTest {
    @Test
    public void testConstructFromValue() {
        GeoRegistrationAlgorithmVersion uut = new GeoRegistrationAlgorithmVersion("6.76a");
        assertEquals(uut.getBytes(), new byte[] {0x36, 0x2e, 0x37, 0x36, 0x61});
        assertEquals(uut.getDisplayName(), "Algorithm Version");
        assertEquals(uut.getDisplayableValue(), "6.76a");
        assertEquals(uut.getValue(), "6.76a");
    }

    @Test
    public void testConstructFromBytes() {
        GeoRegistrationAlgorithmVersion uut =
                new GeoRegistrationAlgorithmVersion(new byte[] {0x36, 0x2e, 0x37, 0x36, 0x61});
        assertEquals(uut.getBytes(), new byte[] {0x36, 0x2e, 0x37, 0x36, 0x61});
        assertEquals(uut.getDisplayName(), "Algorithm Version");
        assertEquals(uut.getDisplayableValue(), "6.76a");
        assertEquals(uut.getValue(), "6.76a");
    }
}
