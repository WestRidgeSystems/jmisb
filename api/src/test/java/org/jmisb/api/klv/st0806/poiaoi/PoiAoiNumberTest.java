package org.jmisb.api.klv.st0806.poiaoi;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class PoiAoiNumberTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        PoiAoiNumber number = new PoiAoiNumber(0);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x00});

        // Max
        number = new PoiAoiNumber(65535);
        assertEquals(number.getBytes(), new byte[]{(byte)0xff, (byte)0xff});

        number = new PoiAoiNumber(159);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});

        assertEquals(number.getDisplayName(), "POI/AOI Number");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        PoiAoiNumber number = new PoiAoiNumber(new byte[]{(byte)0x00, (byte)0x00});
        assertEquals(number.getNumber(), 0);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        assertEquals(number.getDisplayableValue(), "0");

        // Max
        number = new PoiAoiNumber(new byte[]{(byte)0xff, (byte)0xff});
        assertEquals(number.getNumber(), 65535);
        assertEquals(number.getBytes(), new byte[]{(byte)0xff, (byte)0xff});
        assertEquals(number.getDisplayableValue(), "65535");

        number = new PoiAoiNumber(new byte[]{(byte)0x00, (byte)0x9f});
        assertEquals(number.getNumber(), 159);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});
        assertEquals(number.getDisplayableValue(), "159");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x00, (byte)0x00};
        IRvtPoiAoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiNumber, bytes);
        assertTrue(v instanceof PoiAoiNumber);
        PoiAoiNumber number = (PoiAoiNumber)v;
        assertEquals(number.getNumber(), 0);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x00});
        assertEquals(number.getDisplayableValue(), "0");

        bytes = new byte[]{(byte)0x00, (byte)0x9f};
        v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiNumber, bytes);
        assertTrue(v instanceof PoiAoiNumber);
        number = (PoiAoiNumber)v;
        assertEquals(number.getNumber(), 159);
        assertEquals(number.getBytes(), new byte[]{(byte)0x00, (byte)0x9f});
        assertEquals(number.getDisplayableValue(), "159");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new PoiAoiNumber(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new PoiAoiNumber(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new PoiAoiNumber(new byte[]{0x00});
    }
}
