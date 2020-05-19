package org.jmisb.api.klv.st0806.poiaoi;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for the ST0806 POI LS.
 */
public class RvtPoiLocalSetTest extends LoggerChecks
{
    public RvtPoiLocalSetTest()
    {
        super(RvtPoiLocalSet.class);
    }

    @Test
    public void parseTag1() throws KlvParseException
    {
        byte[] bytes = new byte[]{0x01, 0x02, 0x02, 0x04};
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes, 0, bytes.length);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 1);
        checkPoiAoiNumberExample(poiLocalSet);
    }

    @Test
    public void parseRequiredTags() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x01, 0x02, 0x02, 0x04,
            0x02, 0x04, (byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39,
            0x03, 0x04, (byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4};
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes, 0, bytes.length);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 3);
        checkPoiAoiNumberExample(poiLocalSet);
        checkPoiAoiLatitudeExample(poiLocalSet);
        checkPoiAoiLongitudeExample(poiLocalSet);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{
            0x0B, 0x02, (byte) 0x80, (byte) 0xCA, // No such tag
            0x01, 0x02, 0x02, 0x04,
            0x02, 0x04, (byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39,
            0x03, 0x04, (byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4};
        verifyNoLoggerMessages();
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(bytes, 0, bytes.length);
        verifySingleLoggerMessage("Unknown RVT POI Metadata tag: 11");
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 3);
        checkPoiAoiNumberExample(poiLocalSet);
        checkPoiAoiLatitudeExample(poiLocalSet);
        checkPoiAoiLongitudeExample(poiLocalSet);
    }

    public static void checkPoiAoiNumberExample(RvtPoiLocalSet poiLocalSet)
    {
        assertTrue(poiLocalSet.getTags().contains(RvtPoiMetadataKey.PoiAoiNumber));
        IRvtPoiAoiMetadataValue v = poiLocalSet.getField(RvtPoiMetadataKey.PoiAoiNumber);
        assertEquals(v.getDisplayName(), "POI/AOI Number");
        assertEquals(v.getDisplayableValue(), "516");
        assertTrue(v instanceof PoiAoiNumber);
        PoiAoiNumber number = (PoiAoiNumber)v;
        assertNotNull(number);
        assertEquals(number.getNumber(), 516);
    }

    public static void checkPoiAoiLatitudeExample(RvtPoiLocalSet poiLocalSet)
    {
        assertTrue(poiLocalSet.getTags().contains(RvtPoiMetadataKey.PoiLatitude));
        IRvtPoiAoiMetadataValue v = poiLocalSet.getField(RvtPoiMetadataKey.PoiLatitude);
        assertEquals(v.getDisplayName(), "POI Latitude");
        assertEquals(v.getDisplayableValue(), "-86.0412\u00B0");
        assertTrue(v instanceof PoiLatitude);
        PoiLatitude lat = (PoiLatitude)v;
        assertEquals(lat.getDegrees(), -86.0412, 0.0001);
        assertEquals(lat.getBytes(), new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39});
    }

    public static void checkPoiAoiLongitudeExample(RvtPoiLocalSet poiLocalSet)
    {
        assertTrue(poiLocalSet.getTags().contains(RvtPoiMetadataKey.PoiLongitude));
        IRvtPoiAoiMetadataValue v = poiLocalSet.getField(RvtPoiMetadataKey.PoiLongitude);
        assertEquals(v.getDisplayName(), "POI Longitude");
        assertEquals(v.getDisplayableValue(), "116.9344\u00B0");
        assertTrue(v instanceof PoiLongitude);
        PoiLongitude lon = (PoiLongitude)v;
        assertEquals(lon.getDegrees(), 116.9344, 0.0001);
        assertEquals(lon.getBytes(), new byte[]{(byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4});
    }

    @Test
    public void createUnknownTag() throws KlvParseException
    {
        final byte[] bytes = new byte[]{0x03};
        verifyNoLoggerMessages();
        IRvtPoiAoiMetadataValue value = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized RVT POI tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException
    {
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> values = buildPoiValues();
        RvtPoiLocalSet poiLocalSet = new RvtPoiLocalSet(values);
        assertNotNull(poiLocalSet);
        assertEquals(poiLocalSet.getTags().size(), 4);
        checkPoiAoiNumberExample(poiLocalSet);
        checkPoiAoiLatitudeExample(poiLocalSet);
        checkPoiAoiLongitudeExample(poiLocalSet);
        byte[] expectedBytes = new byte[] {
            (byte)0x01, (byte)0x02, (byte)0x02, (byte)0x04, // T:1, L:2, V: 0x0204
            (byte)0x02, (byte)0x04, (byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39, // T: 2, L:4, V: -86.0412
            (byte)0x03, (byte)0x04, (byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4, // T: 3, L:4, V: 116.9344
            (byte)0x09, (byte)0x08, (byte)0x4D, (byte)0x79, (byte)0x20, (byte)0x50, (byte)0x6F, (byte)0x69, (byte)0x6e, (byte)0x74 // T:9, L: 8, V: "My Point"
        };
        assertEquals(poiLocalSet.getBytes(), expectedBytes);
    }

    /**
     * Basic test values for POI Local Set.
     * @return Map ready to drop into the value constructor.
     * @throws KlvParseException if parsing goes wrong.
     */
    public static Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> buildPoiValues() throws KlvParseException {
        Map<RvtPoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        IRvtPoiAoiMetadataValue poiNumberBytes = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiAoiNumber, new byte[]{0x02, 0x04});
        values.put(RvtPoiMetadataKey.PoiAoiNumber, poiNumberBytes);
        final byte[] latBytes = new byte[]{(byte)0x85, (byte)0xa1, (byte)0x5a, (byte)0x39};
        IRvtPoiAoiMetadataValue lat = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiLatitude, latBytes);
        values.put(RvtPoiMetadataKey.PoiLatitude, lat);
        final byte[] lonBytes = new byte[]{(byte)0x53, (byte)0x27, (byte)0x3F, (byte)0xD4};
        IRvtPoiAoiMetadataValue lon = RvtPoiLocalSet.createValue(RvtPoiMetadataKey.PoiLongitude, lonBytes);
        values.put(RvtPoiMetadataKey.PoiLongitude, lon);
        IRvtPoiAoiMetadataValue label = new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, "My Point");
        values.put(RvtPoiMetadataKey.PoiAoiLabel, label);
        return values;
    }
}
