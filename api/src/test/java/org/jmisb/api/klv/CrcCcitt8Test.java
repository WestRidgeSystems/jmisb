package org.jmisb.api.klv;

import static org.testng.Assert.*;

import java.nio.charset.StandardCharsets;
import org.testng.annotations.Test;

/** Unit tests for CrcCcitt. */
public class CrcCcitt8Test {

    @Test
    public void checkEmpty() {
        CrcCcitt8 crc = new CrcCcitt8();
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x00});
    }

    @Test
    public void checkSingle() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {0x00});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x00});
    }

    @Test
    public void checkSingleFF() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {(byte) 0xff});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xf3});
    }

    @Test
    public void checkStatic() {
        byte[] result = CrcCcitt8.getCRC(new byte[] {(byte) 0xff});
        assertEquals(result, new byte[] {(byte) 0xf3});
    }

    @Test
    public void checkMISBCase1() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData("A".repeat(256).getBytes(StandardCharsets.US_ASCII));
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x8e});
    }

    @Test
    public void checkMISBCase2() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {(byte) 0x03, (byte) 0x05, (byte) 0x0B});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xcd});
    }

    @Test
    public void checkMISBCase3() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {65});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xc0});
    }

    @Test
    public void checkMISBCase4() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {3, 5});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x24});
    }

    @Test
    public void checkMISBCase5() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {5, 3});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x48});
    }

    @Test
    public void checkMISBCase6() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData("123456789".getBytes(StandardCharsets.US_ASCII));
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xF4});
    }

    @Test
    public void checkMISBCase7() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x05,
                    0x04,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    (byte) 0x82,
                    0x01,
                    0x7B
                });
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xea});
    }

    @Test
    public void checkSpecifiedLen() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {(byte) 0x03, (byte) 0x05, (byte) 0x0B}, 0, 2);
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x24});
    }

    @Test
    public void checkSpecifiedStartAndLen() {
        CrcCcitt8 crc = new CrcCcitt8();
        crc.addData(new byte[] {(byte) 0xFF, (byte) 0x03, (byte) 0x05, (byte) 0x0B}, 1, 2);
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x24});
    }
}
