package org.jmisb.api.klv;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for CrcCcitt. */
public class CrcCcittTest {

    @Test
    public void checkEmpty() {
        CrcCcitt crc = new CrcCcitt();
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0x1d, (byte) 0x0f});
    }

    @Test
    public void checkSingle() {
        CrcCcitt crc = new CrcCcitt();
        crc.addData(new byte[] {0x00});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xcc, (byte) 0x9c});
    }

    @Test
    public void checkSingleFF() {
        CrcCcitt crc = new CrcCcitt();
        crc.addData(new byte[] {(byte) 0xff});
        byte[] result = crc.getCrc();
        assertEquals(result, new byte[] {(byte) 0xd2, (byte) 0x6c});
    }

    @Test
    public void checkStatic() {
        byte[] result = CrcCcitt.getCRC(new byte[] {(byte) 0xff});
        assertEquals(result, new byte[] {(byte) 0xd2, (byte) 0x6c});
    }

    @Test
    public void checkVerify() {
        assertTrue(
                CrcCcitt.verify(
                        new byte[] {(byte) 0xff, (byte) 0xd2, (byte) 0x6c},
                        new byte[] {(byte) 0xd2, (byte) 0x6c}));
        assertFalse(
                CrcCcitt.verify(
                        new byte[] {(byte) 0xff, (byte) 0xd2, (byte) 0x6d},
                        new byte[] {(byte) 0xd2, (byte) 0x6d}));
        assertFalse(
                CrcCcitt.verify(
                        new byte[] {(byte) 0xff, (byte) 0xd1, (byte) 0x6c},
                        new byte[] {(byte) 0xd1, (byte) 0x6c}));
        assertFalse(
                CrcCcitt.verify(
                        new byte[] {(byte) 0xfe, (byte) 0xd2, (byte) 0x6c},
                        new byte[] {(byte) 0xd2, (byte) 0x6c}));
    }

    @Test
    public void checkVerifyBadMessageLength() {
        assertFalse(
                CrcCcitt.verify(new byte[] {(byte) 0xff}, new byte[] {(byte) 0xd2, (byte) 0x6c}));
    }

    @Test
    public void checkBadExpectedLength() {
        assertFalse(
                CrcCcitt.verify(
                        new byte[] {(byte) 0xff, (byte) 0xd2, (byte) 0x6c},
                        new byte[] {(byte) 0xd2}));
        assertFalse(
                CrcCcitt.verify(new byte[] {(byte) 0xff, (byte) 0xd2}, new byte[] {(byte) 0xd2}));
    }
}
