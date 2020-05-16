package org.jmisb.api.klv.st0806.poi;

import org.jmisb.api.common.KlvParseException;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class PoiAoiTypeTest {

    @Test
    public void testConstructFromValue() {
        // Min
        PoiAoiType poiAoiType = new PoiAoiType((byte) 1);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 1});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Friendly");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Max
        poiAoiType = new PoiAoiType((byte) 4);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 4});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Unknown");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Other value...
        poiAoiType = new PoiAoiType((byte) 3);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 3});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Target");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");
    }

    @Test
    public void testStaticValues() {
        // Min
        PoiAoiType poiAoiType = PoiAoiType.FRIENDLY;
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 1});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Friendly");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Max
        poiAoiType = PoiAoiType.UNKNOWN;
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 4});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Unknown");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Other values
        poiAoiType = PoiAoiType.HOSTILE;
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 2});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Hostile");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");
        poiAoiType = PoiAoiType.TARGET;
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 3});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Target");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");
    }

    @Test
    public void testConstructFromEncoded() {
        // Min
        PoiAoiType poiAoiType = new PoiAoiType(new byte[]{(byte) 1});
        Assert.assertEquals(poiAoiType.getValue(), (byte) 1);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Friendly");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Max
        poiAoiType = new PoiAoiType(new byte[]{(byte) 4});
        Assert.assertEquals(poiAoiType.getValue(), (byte) 4);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x04});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Unknown");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");

        // Other value...
        poiAoiType = new PoiAoiType(new byte[]{(byte) 2});
        Assert.assertEquals(poiAoiType.getValue(), (byte) 2);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Hostile");
        Assert.assertEquals(poiAoiType.getDisplayName(), "POI/AOI Type");
    }

    @Test
    public void testFactory() throws KlvParseException {
        byte[] bytes = new byte[]{(byte) 0x01};
        IRvtPoiMetadataValue v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiType, bytes);
        Assert.assertEquals(v.getDisplayName(), "POI/AOI Type");
        Assert.assertTrue(v instanceof PoiAoiType);
        PoiAoiType poiAoiType = (PoiAoiType) v;
        Assert.assertEquals(poiAoiType.getValue(), (byte) 1);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x01});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Friendly");
        Assert.assertEquals(poiAoiType, PoiAoiType.FRIENDLY);

        bytes = new byte[]{(byte) 0x02};
        v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiType, bytes);
        Assert.assertEquals(v.getDisplayName(), "POI/AOI Type");
        Assert.assertTrue(v instanceof PoiAoiType);
        poiAoiType = (PoiAoiType) v;
        Assert.assertEquals(poiAoiType.getValue(), (byte) 2);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x02});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Hostile");
        Assert.assertEquals(poiAoiType, PoiAoiType.HOSTILE);

        bytes = new byte[]{(byte) 0x03};
        v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiType, bytes);
        Assert.assertTrue(v instanceof PoiAoiType);
        Assert.assertEquals(v.getDisplayName(), "POI/AOI Type");
        poiAoiType = (PoiAoiType) v;
        Assert.assertEquals(poiAoiType.getValue(), (byte) 3);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x03});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Target");
        Assert.assertEquals(poiAoiType, PoiAoiType.TARGET);

        bytes = new byte[]{(byte) 0x04};
        v = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiType, bytes);
        Assert.assertTrue(v instanceof PoiAoiType);
        Assert.assertEquals(v.getDisplayName(), "POI/AOI Type");
        poiAoiType = (PoiAoiType) v;
        Assert.assertEquals(poiAoiType.getValue(), (byte) 4);
        Assert.assertEquals(poiAoiType.getBytes(), new byte[]{(byte) 0x04});
        Assert.assertEquals(poiAoiType.getDisplayableValue(), "Unknown");
        Assert.assertEquals(poiAoiType, PoiAoiType.UNKNOWN);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PoiAoiType((byte)0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PoiAoiType((byte)5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new PoiAoiType(new byte[]{0x01, 0x02});
    }

    @Test
    public void hashTest()
    {
        PoiAoiType poiAoiType = PoiAoiType.HOSTILE;
        assertEquals(poiAoiType.hashCode(), 0x125);
        poiAoiType = PoiAoiType.UNKNOWN;
        assertEquals(poiAoiType.hashCode(), 0x127);
    }

    @Test
    public void equalsSameObject()
    {
        PoiAoiType poiAoiType = PoiAoiType.HOSTILE;
        assertTrue(poiAoiType.equals(poiAoiType));
    }

    @Test
    public void equalsSameValues()
    {
        PoiAoiType poiAoiType1 = new PoiAoiType((byte)0x02);
        PoiAoiType poiAoiType2 = new PoiAoiType((byte)0x02);
        assertTrue(poiAoiType1.equals(poiAoiType2));
        assertTrue(poiAoiType2.equals(poiAoiType1));
        assertTrue(poiAoiType1 != poiAoiType2);
    }

    @Test
    public void equalsDifferentValues()
    {
        PoiAoiType poiAoiType1 = new PoiAoiType((byte)0x01);
        PoiAoiType poiAoiType2 = new PoiAoiType((byte)0x02);
        assertFalse(poiAoiType1.equals(poiAoiType2));
        assertFalse(poiAoiType2.equals(poiAoiType1));
        assertTrue(poiAoiType1 != poiAoiType2);
    }

    @Test
    public void equalsNull()
    {
        PoiAoiType poiAoiType = new PoiAoiType((byte)0x03);
        assertFalse(poiAoiType.equals(null));
    }

    @Test
    public void equalsDifferentClass()
    {
        PoiAoiType poiAoiType = new PoiAoiType((byte)0x01);
        assertFalse(poiAoiType.equals(new String("blah")));
    }
}
