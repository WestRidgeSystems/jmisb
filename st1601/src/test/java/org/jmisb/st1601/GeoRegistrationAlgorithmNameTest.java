package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for ST 1601 Algorithm Name. */
public class GeoRegistrationAlgorithmNameTest {
    @Test
    public void testConstructFromValue() {
        GeoRegistrationAlgorithmName uut = new GeoRegistrationAlgorithmName("good tools");
        assertEquals(
                uut.getBytes(),
                new byte[] {0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c, 0x73});
        assertEquals(uut.getDisplayName(), "Algorithm Name");
        assertEquals(uut.getDisplayableValue(), "good tools");
        assertEquals(uut.getValue(), "good tools");
    }

    @Test
    public void testConstructFromBytes() {
        GeoRegistrationAlgorithmName uut =
                new GeoRegistrationAlgorithmName(
                        new byte[] {0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c, 0x73});
        assertEquals(
                uut.getBytes(),
                new byte[] {0x67, 0x6f, 0x6f, 0x64, 0x20, 0x74, 0x6f, 0x6f, 0x6c, 0x73});
        assertEquals(uut.getDisplayName(), "Algorithm Name");
        assertEquals(uut.getDisplayableValue(), "good tools");
        assertEquals(uut.getValue(), "good tools");
    }
}
