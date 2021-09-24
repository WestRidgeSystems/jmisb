package org.jmisb.core.klv;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.testng.annotations.Test;

/** Tests for the ArrayUtils. */
public class ArrayUtilsTest {
    @Test
    public void checkBasicHexString() {
        String s = ArrayUtils.toHexString(new byte[] {(byte) 0x01, (byte) 0x09, (byte) 0xff});
        assertEquals(s, "01 09 ff ");
    }

    @Test
    public void checkColumnsHexString() {
        String s =
                ArrayUtils.toHexString(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x05,
                            (byte) 0x06,
                            (byte) 0x07,
                            (byte) 0x08,
                            (byte) 0x09,
                            (byte) 0x0a,
                            (byte) 0x0b,
                            (byte) 0x0c,
                            (byte) 0x0d,
                            (byte) 0x0e,
                            (byte) 0x0f,
                            (byte) 0x10,
                            (byte) 0xff
                        });
        assertEquals(
                s,
                "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f "
                        + System.lineSeparator()
                        + "10 ff ");
    }

    @Test
    public void checkColumns16HexString() {
        String s =
                ArrayUtils.toHexString(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x05,
                            (byte) 0x06,
                            (byte) 0x07,
                            (byte) 0x08,
                            (byte) 0x09,
                            (byte) 0x0a,
                            (byte) 0x0b,
                            (byte) 0x0c,
                            (byte) 0x0d,
                            (byte) 0x0e,
                            (byte) 0x0f,
                            (byte) 0x10,
                            (byte) 0xff
                        },
                        16);
        assertEquals(
                s,
                "00 01 02 03 04 05 06 07 08 09 0a 0b 0c 0d 0e 0f "
                        + System.lineSeparator()
                        + "10 ff ");
    }

    @Test
    public void checkColumns8HexString() {
        String s =
                ArrayUtils.toHexString(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x05,
                            (byte) 0x06,
                            (byte) 0x07,
                            (byte) 0x08,
                            (byte) 0x09,
                            (byte) 0x0a,
                            (byte) 0x0b,
                            (byte) 0x0c,
                            (byte) 0x0d,
                            (byte) 0x0e,
                            (byte) 0x0f,
                            (byte) 0x10,
                            (byte) 0xff
                        },
                        8);
        assertEquals(
                s,
                "00 01 02 03 04 05 06 07 "
                        + System.lineSeparator()
                        + "08 09 0a 0b 0c 0d 0e 0f "
                        + System.lineSeparator()
                        + "10 ff ");
    }

    @Test
    public void checkColumns8DecoratedHexString() {
        String s =
                ArrayUtils.toHexString(
                        new byte[] {
                            (byte) 0x00,
                            (byte) 0x01,
                            (byte) 0x02,
                            (byte) 0x03,
                            (byte) 0x04,
                            (byte) 0x05,
                            (byte) 0x06,
                            (byte) 0x07,
                            (byte) 0x08,
                            (byte) 0x09,
                            (byte) 0x0a,
                            (byte) 0x0b,
                            (byte) 0x0c,
                            (byte) 0x0d,
                            (byte) 0x0e,
                            (byte) 0x0f,
                            (byte) 0x10,
                            (byte) 0xff
                        },
                        8,
                        true);
        assertEquals(
                s,
                "0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, "
                        + System.lineSeparator()
                        + "0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, "
                        + System.lineSeparator()
                        + "0x10, 0xff, ");
    }

    @Test
    public void checkBytesToArray() {
        Collection<byte[]> chunks = new ArrayList<>();
        byte[] chunk0 = new byte[] {0x0a, 0x0b};
        int len = chunk0.length;
        chunks.add(chunk0);
        byte[] chunk1 = new byte[] {0x01, 0x02};
        len += chunk1.length;
        chunks.add(chunk1);
        byte[] chunk2 = new byte[] {0x09, 0x08, 0x07};
        len += chunk2.length;
        chunks.add(chunk2);
        byte[] a = ArrayUtils.arrayFromChunks(chunks, len);
        assertEquals(a, new byte[] {0x0a, 0x0b, 0x01, 0x02, 0x09, 0x08, 0x07});
    }
}
