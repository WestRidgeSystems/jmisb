package org.jmisb.st1601;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for ST 1601 Second Image Name. */
public class SecondImageNameTest {
    @Test
    public void testConstructFromValue() {
        SecondImageName uut = new SecondImageName("image name xyz");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x20, 0x6e, 0x61, 0x6d, 0x65, 0x20, 0x78, 0x79,
                    0x7a
                });
        assertEquals(uut.getDisplayName(), "Second Image Name");
        assertEquals(uut.getDisplayableValue(), "image name xyz");
        assertEquals(uut.getValue(), "image name xyz");
    }

    @Test
    public void testConstructFromBytes() {
        SecondImageName uut =
                new SecondImageName(
                        new byte[] {
                            0x69, 0x6d, 0x61, 0x67, 0x65, 0x20, 0x6e, 0x61, 0x6d, 0x65, 0x20, 0x78,
                            0x79, 0x7a
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x69, 0x6d, 0x61, 0x67, 0x65, 0x20, 0x6e, 0x61, 0x6d, 0x65, 0x20, 0x78, 0x79,
                    0x7a
                });
        assertEquals(uut.getDisplayName(), "Second Image Name");
        assertEquals(uut.getDisplayableValue(), "image name xyz");
        assertEquals(uut.getValue(), "image name xyz");
    }
}
