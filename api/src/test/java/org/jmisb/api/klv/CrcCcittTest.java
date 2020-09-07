package org.jmisb.api.klv;

import static org.testng.Assert.*;

import java.nio.charset.StandardCharsets;
import org.testng.annotations.Test;

/**
 * Unit tests for CrcCcitt.
 *
 * <p>These are from Appendix D of the Motion Imagery Handbook (October 2019), Appendix D.
 *
 * <p>No copyright is claimed over the code in this source file.
 */
public class CrcCcittTest {

    public CrcCcittTest() {}

    @Test
    public void A256() {
        String s = "";
        for (int x = 0; x < 256; x++) {
            s += "A";
        }
        assertEquals(CrcCcitt.getCRC(s.getBytes()), new byte[] {(byte) 0xE9, (byte) 0x38});
    }

    @Test
    public void test2() {
        assertEquals(CrcCcitt.getCRC(new byte[] {3, 5, 11}), new byte[] {(byte) 0x06, (byte) 0xC2});
    }

    @Test
    public void test3() {
        assertEquals(CrcCcitt.getCRC(new byte[] {65}), new byte[] {(byte) 0x94, (byte) 0x79});
    }

    @Test
    public void test4() {
        assertEquals(
                CrcCcitt.getCRC("123456789".getBytes(StandardCharsets.US_ASCII)),
                new byte[] {(byte) 0xE5, (byte) 0xCC});
    }

    @Test
    public void A256Object() {
        String s = "";
        for (int x = 0; x < 256; x++) {
            s += "A";
        }
        CrcCcitt crcCalculator = new CrcCcitt();
        crcCalculator.addData(s.getBytes());
        assertEquals(crcCalculator.getCrc(), new byte[] {(byte) 0xE9, (byte) 0x38});
    }

    @Test
    public void test2Object() {
        CrcCcitt crcCalculator = new CrcCcitt();
        crcCalculator.addData(new byte[] {3, 5, 11});
        assertEquals(crcCalculator.getCrc(), new byte[] {(byte) 0x06, (byte) 0xC2});
    }

    @Test
    public void test3Object() {
        CrcCcitt crcCalculator = new CrcCcitt();
        crcCalculator.addData(new byte[] {65});
        assertEquals(crcCalculator.getCrc(), new byte[] {(byte) 0x94, (byte) 0x79});
    }

    @Test
    public void test4Object() {
        CrcCcitt crcCalculator = new CrcCcitt();
        crcCalculator.addData("123456789".getBytes(StandardCharsets.US_ASCII));
        assertEquals(crcCalculator.getCrc(), new byte[] {(byte) 0xE5, (byte) 0xCC});
    }

    @Test
    public void paul1() {
        byte[] data =
                new byte[] {
                    0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0e, 0x01, 0x05, 0x03, 0x00,
                    0x00, 0x00, 0x00, 0x21, 0x01, 0x31
                };
        assertEquals(CrcCcitt.getCRC(data), new byte[] {(byte) 0xE7, (byte) 0x92});
    }

    @Test
    public void paul2() {
        byte[] data =
                new byte[] {
                    0x06,
                    0x0e,
                    0x2b,
                    0x34,
                    0x02,
                    0x05,
                    0x01,
                    0x01,
                    0x0e,
                    0x01,
                    0x05,
                    0x03,
                    0x00,
                    0x00,
                    0x00,
                    0x00,
                    0x21,
                    0x01,
                    0x31,
                    0x26,
                    0x0E,
                    0x0D,
                    0x21,
                    0x08,
                    0x16,
                    0x2C,
                    (byte) 0x86,
                    0x31,
                    0x7D,
                    0x78,
                    0x15,
                    (byte) 0xF9,
                    0x22,
                    0x01,
                    0x24
                };
        assertEquals(CrcCcitt.getCRC(data), new byte[] {(byte) 0xD8, (byte) 0x62});
    }
}
