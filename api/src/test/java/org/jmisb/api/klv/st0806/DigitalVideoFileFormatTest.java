package org.jmisb.api.klv.st0806;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class DigitalVideoFileFormatTest
{
    @Test
    public void testConstructFromValue()
    {
        DigitalVideoFileFormat rate = new DigitalVideoFileFormat("H.264");
        assertEquals(rate.getBytes(), new byte[]{(byte)0x48, (byte)0x2E, (byte)0x32, (byte)0x36, (byte)0x34});
        assertEquals(rate.getDisplayName(), "Digital Video File Format");
        assertEquals(rate.getDisplayableValue(), "H.264");
    }

    @Test
    public void testConstructFromEncoded()
    {
        DigitalVideoFileFormat rate = new DigitalVideoFileFormat(new byte[]{(byte)0x48, (byte)0x2E, (byte)0x32, (byte)0x36, (byte)0x34});
        assertEquals(rate.getDisplayableValue(), "H.264");
        assertEquals(rate.getBytes(), new byte[]{(byte)0x48, (byte)0x2E, (byte)0x32, (byte)0x36, (byte)0x34});
        assertEquals(rate.getDisplayName(), "Digital Video File Format");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x48, (byte)0x2E, (byte)0x32, (byte)0x36, (byte)0x34};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.DigitalVideoFileFormat, bytes);
        assertTrue(v instanceof DigitalVideoFileFormat);
        DigitalVideoFileFormat rate = (DigitalVideoFileFormat)v;
        assertEquals(rate.getBytes(), new byte[]{(byte)0x48, (byte)0x2E, (byte)0x32, (byte)0x36, (byte)0x34});
        assertEquals(rate.getDisplayableValue(), "H.264");
        assertEquals(rate.getDisplayName(), "Digital Video File Format");
    }
}
