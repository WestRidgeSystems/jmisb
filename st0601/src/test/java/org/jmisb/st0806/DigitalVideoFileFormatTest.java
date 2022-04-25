package org.jmisb.st0806;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class DigitalVideoFileFormatTest {
    @Test
    public void testConstructFromValue() {
        DigitalVideoFileFormat format = new DigitalVideoFileFormat("H.264");
        assertEquals(
                format.getBytes(),
                new byte[] {(byte) 0x48, (byte) 0x2E, (byte) 0x32, (byte) 0x36, (byte) 0x34});
        assertEquals(format.getDisplayName(), "Digital Video File Format");
        assertEquals(format.getDisplayableValue(), "H.264");
    }

    @Test
    public void testConstructFromEncoded() {
        DigitalVideoFileFormat format =
                new DigitalVideoFileFormat(
                        new byte[] {
                            (byte) 0x48, (byte) 0x2E, (byte) 0x32, (byte) 0x36, (byte) 0x34
                        });
        assertEquals(format.getDisplayableValue(), "H.264");
        assertEquals(
                format.getBytes(),
                new byte[] {(byte) 0x48, (byte) 0x2E, (byte) 0x32, (byte) 0x36, (byte) 0x34});
        assertEquals(format.getDisplayName(), "Digital Video File Format");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[] {(byte) 0x48, (byte) 0x2E, (byte) 0x32, (byte) 0x36, (byte) 0x34};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.DigitalVideoFileFormat, bytes);
        assertTrue(v instanceof DigitalVideoFileFormat);
        DigitalVideoFileFormat format = (DigitalVideoFileFormat) v;
        assertEquals(
                format.getBytes(),
                new byte[] {(byte) 0x48, (byte) 0x2E, (byte) 0x32, (byte) 0x36, (byte) 0x34});
        assertEquals(format.getDisplayableValue(), "H.264");
        assertEquals(format.getDisplayName(), "Digital Video File Format");
    }
}
