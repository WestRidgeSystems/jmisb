package org.jmisb.api.klv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ChecksumTest {
    @Test
    public void testFromSt() {
        // Input array: 060E 2B34 0200 81BB
        byte[] input =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0e,
                    (byte) 0x2b,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0xbb,
                    (byte) 0x00,
                    (byte) 0x00
                }; // 2 bytes padding

        // Checksum: B4FD
        byte[] expected = new byte[] {(byte) 0xb4, (byte) 0xfd};

        byte[] actual = Checksum.compute(input, false);
        Assert.assertEquals(actual, expected);
    }
}
