package org.jmisb.core.klv;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the CRC32 MPEG2 implementation.
 *
 * Test vectors were generated with Jacksum 
 * https://jacksum.loefflmann.net/en/index.html), except where noted.
 */
public class CRC32MPEG2Test
{
    @Test
    public void checkConstructor()
    {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        assertNotNull(crc32);
    }

    @Test
    public void check00()
    {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[]{(byte)0x00}, 1);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0x4e, (byte)0x08, (byte)0xbf, (byte)0xb4});
    }

    @Test
    public void check00FF()
    {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[]{(byte)0x00, (byte)0xFF}, 2);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0xb1, (byte)0x40, (byte)0x24, (byte)0xc9});
    }

    @Test
    public void checkFF()
    {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[]{(byte)0xFF}, 1);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0x00});
    }

    @Test
    public void checkFFFFFFFF()
    {
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}, 4);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00});
    }

    @Test
    public void checkTextNumbers()
    {
        // this appears to be a standard test case: http://reveng.sourceforge.net/crc-catalogue/all.htm#crc.cat-bits.32
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(new byte[]{
            (byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x35,
            (byte)0x36, (byte)0x37, (byte)0x38, (byte)0x39}, 9);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0x03, (byte)0x76, (byte)0xe6, (byte)0xe7});
    }
    
    @Test
    public void checkRandomGuidText()
    {
        byte[] inputBytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33};
        CRC32MPEG2 crc32 = new CRC32MPEG2();
        crc32.update(inputBytes, inputBytes.length);
        assertEquals(crc32.getCRC32(), new byte[]{(byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb7});
    }

    @Test
    public void computeRandomGuidText()
    {
        byte[] inputBytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
        byte[] res = CRC32MPEG2.compute(inputBytes);
        assertEquals(res, new byte[]{(byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb7});
        byte[] expectedBytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb7};
        assertEquals(inputBytes, expectedBytes);
    }

    @Test
    public void verifyRandomGuidText()
    {
        byte[] bytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb7};
        assertTrue(CRC32MPEG2.verify(bytes, new byte[]{(byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte0()
    {
        byte[] bytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb4, (byte)0x7b, (byte)0xfb, (byte)0xb7};
        assertFalse(CRC32MPEG2.verify(bytes, new byte[]{(byte)0xb4, (byte)0x7b, (byte)0xfb, (byte)0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte1()
    {
        byte[] bytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb5, (byte)0x8b, (byte)0xfb, (byte)0xb7};
        assertFalse(CRC32MPEG2.verify(bytes, new byte[]{(byte)0xb5, (byte)0x8b, (byte)0xfb, (byte)0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte2()
    {
        byte[] bytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb5, (byte)0x7b, (byte)0xeb, (byte)0xb7};
        assertFalse(CRC32MPEG2.verify(bytes, new byte[]{(byte)0xb5, (byte)0x7b, (byte)0xeb, (byte)0xb7}));
    }

    @Test
    public void verifyRandomGuidTextBadByte3()
    {
        byte[] bytes = new byte[]{
            (byte)0x66, (byte)0x30, (byte)0x65, (byte)0x61, (byte)0x61, (byte)0x36, (byte)0x32, (byte)0x65,
            (byte)0x2d, (byte)0x62, (byte)0x65, (byte)0x64, (byte)0x61, (byte)0x2d, (byte)0x34, (byte)0x37,
            (byte)0x37, (byte)0x64, (byte)0x2d, (byte)0x62, (byte)0x35, (byte)0x66, (byte)0x30, (byte)0x2d,
            (byte)0x62, (byte)0x38, (byte)0x37, (byte)0x37, (byte)0x65, (byte)0x31, (byte)0x35, (byte)0x36,
            (byte)0x36, (byte)0x35, (byte)0x30, (byte)0x33, (byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb0};
        assertFalse(CRC32MPEG2.verify(bytes, new byte[]{(byte)0xb5, (byte)0x7b, (byte)0xfb, (byte)0xb0}));
    }
}