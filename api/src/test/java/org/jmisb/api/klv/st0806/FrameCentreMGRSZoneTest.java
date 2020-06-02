package org.jmisb.api.klv.st0806;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class FrameCentreMGRSZoneTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        FrameCentreMGRSZone zone = new FrameCentreMGRSZone(1);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x01});

        // Max
        zone = new FrameCentreMGRSZone(60);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x3C});

        zone = new FrameCentreMGRSZone(34);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x22});

        assertEquals(zone.getDisplayName(), "Frame Center MGRS Zone");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        FrameCentreMGRSZone zone = new FrameCentreMGRSZone(new byte[]{(byte)0x01});
        assertEquals(zone.getZone(), 1);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x01});
        assertEquals(zone.getDisplayableValue(), "1");

        // Max
        zone = new FrameCentreMGRSZone(new byte[]{(byte)0x3C});
        assertEquals(zone.getZone(), 60);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x3C});
        assertEquals(zone.getDisplayableValue(), "60");

        zone = new FrameCentreMGRSZone(new byte[]{(byte)0x22});
        assertEquals(zone.getZone(), 34);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x22});
        assertEquals(zone.getDisplayableValue(), "34");
        assertEquals(zone.getDisplayName(), "Frame Center MGRS Zone");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x23};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.MGRSZoneSecondValue, bytes);
        assertTrue(v instanceof FrameCentreMGRSZone);
        FrameCentreMGRSZone zone = (FrameCentreMGRSZone)v;
        assertEquals(zone.getZone(), 35);
        assertEquals(zone.getBytes(), new byte[]{(byte)0x23});
        assertEquals(zone.getDisplayableValue(), "35");
        assertEquals(zone.getDisplayName(), "Frame Center MGRS Zone");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new FrameCentreMGRSZone(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new FrameCentreMGRSZone(61);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new FrameCentreMGRSZone(new byte[]{0x01, 0x02});
    }
}
