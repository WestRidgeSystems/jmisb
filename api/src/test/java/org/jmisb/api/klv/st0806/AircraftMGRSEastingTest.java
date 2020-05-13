package org.jmisb.api.klv.st0806;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class AircraftMGRSEastingTest
{
    @Test
    public void testConstructFromValue()
    {
        // Min
        AircraftMGRSEasting easting = new AircraftMGRSEasting(0);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00});

        // Max
        easting = new AircraftMGRSEasting(99999);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x01, (byte)0x86, (byte)0x9F});

        easting = new AircraftMGRSEasting(74565);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x01, (byte)0x23, (byte)0x45});

        assertEquals(easting.getDisplayName(), "Aircraft MGRS Easting");
    }

    @Test
    public void testConstructFromEncoded()
    {
        // Min
        AircraftMGRSEasting easting = new AircraftMGRSEasting(new byte[]{(byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(easting.getEasting(), 0);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x00, (byte)0x00, (byte)0x00});
        assertEquals(easting.getDisplayableValue(), "0");

        // Max
        easting = new AircraftMGRSEasting(new byte[]{(byte)0x01, (byte)0x86, (byte)0x9F});
        assertEquals(easting.getEasting(), 99999);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x01, (byte)0x86, (byte)0x9F});
        assertEquals(easting.getDisplayableValue(), "99999");

        easting = new AircraftMGRSEasting(new byte[]{(byte)0x01, (byte)0x23, (byte)0x45});
        assertEquals(easting.getEasting(), 74565);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x01, (byte)0x23, (byte)0x45});
        assertEquals(easting.getDisplayableValue(), "74565");
        assertEquals(easting.getDisplayName(), "Aircraft MGRS Easting");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x01, (byte)0x23, (byte)0x45};
        IRvtMetadataValue v = RvtLocalSet.createValue(RvtMetadataKey.MGRSEasting, bytes);
        assertTrue(v instanceof AircraftMGRSEasting);
        AircraftMGRSEasting easting = (AircraftMGRSEasting)v;
        assertEquals(easting.getEasting(), 74565);
        assertEquals(easting.getBytes(), new byte[]{(byte)0x01, (byte)0x23, (byte)0x45});
        assertEquals(easting.getDisplayableValue(), "74565");
        assertEquals(easting.getDisplayName(), "Aircraft MGRS Easting");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall()
    {
        new AircraftMGRSEasting(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig()
    {
        new AircraftMGRSEasting(100000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength()
    {
        new AircraftMGRSEasting(new byte[]{0x01, 0x02});
    }
}
