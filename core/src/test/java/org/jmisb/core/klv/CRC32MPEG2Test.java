package org.jmisb.core.klv;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/**
 * Tests for the CRC32 MPEG2 implementation.
 *
 * <p>Test vectors were generated with Jacksum https://jacksum.loefflmann.net/en/index.html), except
 * where noted.
 */
public class CRC32MPEG2Test {
    @Test
    public void checkConstructor() {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        assertNotNull(crc32);
    }

    @Test
    public void check00() {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[] {(byte) 0x00}, 1);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0x4e, (byte) 0x08, (byte) 0xbf, (byte) 0xb4});
    }

    @Test
    public void check00FF() {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[] {(byte) 0x00, (byte) 0xFF}, 2);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0xb1, (byte) 0x40, (byte) 0x24, (byte) 0xc9});
    }

    @Test
    public void checkFF() {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[] {(byte) 0xFF}, 1);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00});
    }

    @Test
    public void checkFFFFFFFF() {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, 4);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void checkTextNumbers() {
        // this appears to be a standard test case:
        // http://reveng.sourceforge.net/crc-catalogue/all.htm#crc.cat-bits.32
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(
                new byte[] {
                    (byte) 0x31,
                    (byte) 0x32,
                    (byte) 0x33,
                    (byte) 0x34,
                    (byte) 0x35,
                    (byte) 0x36,
                    (byte) 0x37,
                    (byte) 0x38,
                    (byte) 0x39
                },
                9);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0x03, (byte) 0x76, (byte) 0xe6, (byte) 0xe7});
    }

    @Test
    public void checkRandomGuidText() {
        byte[] inputBytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33
                };
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(inputBytes, inputBytes.length);
        assertEquals(
                crc32.getCRC32(), new byte[] {(byte) 0xb5, (byte) 0x7b, (byte) 0xfb, (byte) 0xb7});
    }

    @Test
    public void computeRandomGuidText() {
        byte[] inputBytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0x00, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00
                };
        byte[] res = CRC32MPEG2.compute(inputBytes);
        assertEquals(res, new byte[] {(byte) 0xb5, (byte) 0x7b, (byte) 0xfb, (byte) 0xb7});
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb5, (byte) 0x7b,
                            (byte) 0xfb, (byte) 0xb7
                };
        assertEquals(inputBytes, expectedBytes);
    }

    @Test
    public void verifyRandomGuidText() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb5, (byte) 0x7b,
                            (byte) 0xfb, (byte) 0xb7
                };
        assertTrue(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xb5, (byte) 0x7b, (byte) 0xfb, (byte) 0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte0() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb4, (byte) 0x7b,
                            (byte) 0xfb, (byte) 0xb7
                };
        assertFalse(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xb4, (byte) 0x7b, (byte) 0xfb, (byte) 0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte1() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb5, (byte) 0x8b,
                            (byte) 0xfb, (byte) 0xb7
                };
        assertFalse(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xb5, (byte) 0x8b, (byte) 0xfb, (byte) 0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte2() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb5, (byte) 0x7b,
                            (byte) 0xeb, (byte) 0xb7
                };
        assertFalse(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xb5, (byte) 0x7b, (byte) 0xeb, (byte) 0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte3() {
        byte[] bytes =
                new byte[] {
                    (byte) 0x66, (byte) 0x30, (byte) 0x65, (byte) 0x61, (byte) 0x61, (byte) 0x36,
                            (byte) 0x32, (byte) 0x65,
                    (byte) 0x2d, (byte) 0x62, (byte) 0x65, (byte) 0x64, (byte) 0x61, (byte) 0x2d,
                            (byte) 0x34, (byte) 0x37,
                    (byte) 0x37, (byte) 0x64, (byte) 0x2d, (byte) 0x62, (byte) 0x35, (byte) 0x66,
                            (byte) 0x30, (byte) 0x2d,
                    (byte) 0x62, (byte) 0x38, (byte) 0x37, (byte) 0x37, (byte) 0x65, (byte) 0x31,
                            (byte) 0x35, (byte) 0x36,
                    (byte) 0x36, (byte) 0x35, (byte) 0x30, (byte) 0x33, (byte) 0xb5, (byte) 0x7b,
                            (byte) 0xfb, (byte) 0xb0
                };
        assertFalse(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xb5, (byte) 0x7b, (byte) 0xfb, (byte) 0xb0}));
    }

    @Test
    public void verifyValuesFromST0903FLI() {
        byte[] bytes =
                new byte[] {
                    // T
                    73,
                    // L
                    (byte) 0x82,
                    0x01,
                    0x53,
                    // V
                    0x02,
                    0x08,
                    0x00,
                    0x03,
                    (byte) 0xf3,
                    (byte) 0xb3,
                    0x5e,
                    (byte) 0xde,
                    0x0a,
                    0x7c,
                    0x03,
                    0x02,
                    0x00,
                    (byte) 0xc8,
                    0x04,
                    0x02,
                    0x00,
                    (byte) 0xc8,
                    0x05,
                    0x01,
                    0x00,
                    0x06,
                    0x02,
                    0x00,
                    0x00,
                    0x07,
                    0x04,
                    0x00,
                    0x00,
                    0x00,
                    0x1e,
                    0x08,
                    0x01,
                    0x03,
                    0x09,
                    0x04,
                    0x00,
                    0x00,
                    0x00,
                    0x1e,
                    0x0a,
                    0x05,
                    0x4d,
                    0x50,
                    0x45,
                    0x47,
                    0x32,
                    0x0b,
                    0x13,
                    0x01,
                    0x01,
                    0x00,
                    0x02,
                    0x0e,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x55,
                    0x53,
                    0x45,
                    0x52,
                    0x20,
                    0x44,
                    0x41,
                    0x54,
                    0x41,
                    0x0c,
                    0x72,
                    0x01,
                    0x02,
                    0x00,
                    0x64,
                    0x02,
                    0x04,
                    0x0e,
                    0x38,
                    (byte) 0xe3,
                    (byte) 0x8e,
                    0x03,
                    0x04,
                    0x07,
                    0x1c,
                    0x71,
                    (byte) 0xc7,
                    0x04,
                    0x02,
                    0x0c,
                    (byte) 0xdd,
                    0x05,
                    0x01,
                    0x01,
                    0x06,
                    0x11,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x50,
                    0x4f,
                    0x49,
                    0x2f,
                    0x41,
                    0x4f,
                    0x49,
                    0x20,
                    0x54,
                    0x45,
                    0x58,
                    0x54,
                    0x07,
                    0x09,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x49,
                    0x43,
                    0x4f,
                    0x4e,
                    0x08,
                    0x16,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x50,
                    0x4f,
                    0x49,
                    0x2f,
                    0x41,
                    0x4f,
                    0x49,
                    0x20,
                    0x53,
                    0x4f,
                    0x55,
                    0x52,
                    0x43,
                    0x45,
                    0x20,
                    0x49,
                    0x44,
                    0x09,
                    0x10,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x4c,
                    0x41,
                    0x42,
                    0x45,
                    0x4c,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x4c,
                    0x41,
                    0x42,
                    0x0a,
                    0x11,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x4f,
                    0x50,
                    0x45,
                    0x52,
                    0x41,
                    0x54,
                    0x49,
                    0x4f,
                    0x4e,
                    0x20,
                    0x49,
                    0x44,
                    0x0d,
                    0x6f,
                    0x01,
                    0x02,
                    0x00,
                    0x64,
                    0x02,
                    0x04,
                    0x0e,
                    0x38,
                    (byte) 0xe3,
                    (byte) 0x8e,
                    0x03,
                    0x04,
                    0x07,
                    0x1c,
                    0x71,
                    (byte) 0xc7,
                    0x04,
                    0x04,
                    0x0e,
                    0x38,
                    (byte) 0xe3,
                    (byte) 0x8e,
                    0x05,
                    0x04,
                    0x07,
                    0x1c,
                    0x71,
                    (byte) 0xc7,
                    0x06,
                    0x01,
                    0x01,
                    0x07,
                    0x11,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x50,
                    0x4f,
                    0x49,
                    0x2f,
                    0x41,
                    0x4f,
                    0x49,
                    0x20,
                    0x54,
                    0x45,
                    0x58,
                    0x54,
                    0x08,
                    0x16,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x50,
                    0x4f,
                    0x49,
                    0x2f,
                    0x41,
                    0x4f,
                    0x49,
                    0x20,
                    0x53,
                    0x4f,
                    0x55,
                    0x52,
                    0x43,
                    0x45,
                    0x20,
                    0x49,
                    0x44,
                    0x09,
                    0x10,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x4c,
                    0x41,
                    0x42,
                    0x45,
                    0x4c,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x4c,
                    0x41,
                    0x42,
                    0x0a,
                    0x11,
                    0x54,
                    0x45,
                    0x53,
                    0x54,
                    0x20,
                    0x4f,
                    0x50,
                    0x45,
                    0x52,
                    0x41,
                    0x54,
                    0x49,
                    0x4f,
                    0x4e,
                    0x20,
                    0x49,
                    0x44,
                    0x0e,
                    0x01,
                    0x13,
                    0x0f,
                    0x03,
                    0x54,
                    0x44,
                    0x4a,
                    0x10,
                    0x03,
                    0x00,
                    (byte) 0x96,
                    (byte) 0xbc,
                    0x11,
                    0x03,
                    0x01,
                    0x7c,
                    0x56,
                    0x12,
                    0x01,
                    0x13,
                    0x13,
                    0x03,
                    0x54,
                    0x44,
                    0x4a,
                    0x14,
                    0x03,
                    0x00,
                    (byte) 0x96,
                    (byte) 0xbc,
                    0x15,
                    0x03,
                    0x01,
                    0x7c,
                    0x56,
                    0x01,
                    0x04,
                    (byte) 0xcd,
                    (byte) 0xbc,
                    0x71,
                    0x45
                };
        assertTrue(
                CRC32MPEG2.verify(
                        bytes, new byte[] {(byte) 0xcd, (byte) 0xbc, (byte) 0x71, (byte) 0x45}));
    }
}
