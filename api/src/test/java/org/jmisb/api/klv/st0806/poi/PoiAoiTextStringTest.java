package org.jmisb.api.klv.st0806.poi;

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class PoiAoiTextStringTest
{
    @Test
    public void testConstructFromValue()
    {
        RvtPoiTextString textString = new RvtPoiTextString(RvtPoiTextString.POI_AOI_TEXT, "QFJ");
        assertEquals(textString.getBytes(), new byte[]{(byte)0x51, (byte)0x46, (byte)0x4A});
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
        assertEquals(textString.getDisplayableValue(), "QFJ");
    }

    @Test
    public void testConstructFromEncoded()
    {
        RvtPoiTextString textString = new RvtPoiTextString(RvtPoiTextString.POI_AOI_TEXT, new byte[]{(byte)0x51, (byte)0x46, (byte)0x4A});
        assertEquals(textString.getDisplayableValue(), "QFJ");
        assertEquals(textString.getBytes(), new byte[]{(byte)0x51, (byte)0x46, (byte)0x4A});
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x51, (byte)0x46, (byte)0x4A};
        IRvtPoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiText, bytes);
        assertTrue(v instanceof RvtPoiTextString);
        RvtPoiTextString textString = (RvtPoiTextString)v;
        assertEquals(textString.getBytes(), new byte[]{(byte)0x51, (byte)0x46, (byte)0x4A});
        assertEquals(textString.getDisplayableValue(), "QFJ");
        assertEquals(textString.getDisplayName(), "POI/AOI Text");
    }

    @Test
    public void testFactorySourceIcon() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x54, (byte)0x32, (byte)0x35, (byte)0x32, (byte)0x35, (byte)0x42, (byte)0x59};
        IRvtPoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiSourceIcon, bytes);
        assertTrue(v instanceof RvtPoiTextString);
        RvtPoiTextString textString = (RvtPoiTextString)v;
        assertEquals(textString.getBytes(), new byte[]{(byte)0x54, (byte)0x32, (byte)0x35, (byte)0x32, (byte)0x35, (byte)0x42, (byte)0x59});
        assertEquals(textString.getDisplayableValue(), "T2525BY");
        assertEquals(textString.getDisplayName(), "POI Source Icon");
    }

    @Test
    public void testFactorySourceID() throws KlvParseException
    {
        byte[] bytes = new byte[]{(byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x20, (byte)0x41, (byte)0x42};
        IRvtPoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiSourceId, bytes);
        assertTrue(v instanceof RvtPoiTextString);
        RvtPoiTextString textString = (RvtPoiTextString)v;
        assertEquals(textString.getBytes(), new byte[]{(byte)0x31, (byte)0x32, (byte)0x33, (byte)0x34, (byte)0x20, (byte)0x41, (byte)0x42});
        assertEquals(textString.getDisplayableValue(), "1234 AB");
        assertEquals(textString.getDisplayName(), "POI/AOI Source ID");
    }
}
