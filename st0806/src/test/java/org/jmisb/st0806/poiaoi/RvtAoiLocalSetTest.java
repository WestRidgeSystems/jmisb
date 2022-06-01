package org.jmisb.st0806.poiaoi;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.st0806.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST0806 AOI LS. */
public class RvtAoiLocalSetTest extends LoggerChecks {
    public RvtAoiLocalSetTest() {
        super(RvtAoiLocalSet.class);
    }

    @Test
    public void parseTag1() throws KlvParseException {
        byte[] bytes = new byte[] {0x01, 0x02, 0x02, 0x04};
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(bytes, 0, bytes.length);
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getTags().size(), 1);
        checkPoiAoiNumberExample(aoiLocalSet);
        assertEquals(aoiLocalSet.getDisplayableValue(), "516");
    }

    @Test
    public void parseRequiredTags() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x01,
                    0x02,
                    0x02,
                    0x04,
                    0x02,
                    0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    0x03,
                    0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    0x04,
                    0x04,
                    (byte) 0x85,
                    (byte) 0x92,
                    (byte) 0x5a,
                    (byte) 0x39,
                    0x05,
                    0x04,
                    (byte) 0x53,
                    (byte) 0x37,
                    (byte) 0x3F,
                    (byte) 0xD4,
                    0x06,
                    0x01,
                    (byte) 0x03
                };
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(bytes, 0, bytes.length);
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getDisplayName(), "Area of Interest");
        assertEquals(aoiLocalSet.getDisplayableValue(), "516");
        assertEquals(aoiLocalSet.getTags().size(), 6);
        checkPoiAoiNumberExample(aoiLocalSet);
        checkPoiAoiLatitude1Example(aoiLocalSet);
        checkPoiAoiLongitude1Example(aoiLocalSet);
        checkPoiAoiLatitude3Example(aoiLocalSet);
        checkPoiAoiLongitude3Example(aoiLocalSet);
        checkPoiAoiTypeExample(aoiLocalSet);
    }

    @Test
    public void parseTagsWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    0x0B,
                    0x02,
                    (byte) 0x80,
                    (byte) 0xCA, // No such tag
                    0x01,
                    0x02,
                    0x02,
                    0x04,
                    0x02,
                    0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39,
                    0x03,
                    0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4
                };
        verifyNoLoggerMessages();
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(bytes, 0, bytes.length);
        verifySingleLoggerMessage("Unknown RVT AOI Metadata tag: 11");
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getDisplayName(), "Area of Interest");
        assertEquals(aoiLocalSet.getDisplayableValue(), "516");
        assertEquals(aoiLocalSet.getTags().size(), 3);
        checkPoiAoiNumberExample(aoiLocalSet);
        checkPoiAoiLatitude1Example(aoiLocalSet);
        checkPoiAoiLongitude1Example(aoiLocalSet);
    }

    private void checkPoiAoiNumberExample(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.PoiAoiNumber));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.PoiAoiNumber);
        assertEquals(v.getDisplayName(), "POI/AOI Number");
        assertEquals(v.getDisplayableValue(), "516");
        assertTrue(v instanceof PoiAoiNumber);
        PoiAoiNumber number = (PoiAoiNumber) v;
        assertNotNull(number);
        assertEquals(number.getNumber(), 516);
    }

    private void checkPoiAoiLatitude1Example(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.CornerLatitudePoint1));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.CornerLatitudePoint1);
        assertEquals(v.getDisplayName(), "Corner Latitude Point 1");
        assertEquals(v.getDisplayableValue(), "-86.0412\u00B0");
        assertTrue(v instanceof CornerLatitudePoint1);
        CornerLatitudePoint1 lat = (CornerLatitudePoint1) v;
        assertEquals(lat.getDegrees(), -86.0412, 0.0001);
        assertEquals(
                lat.getBytes(), new byte[] {(byte) 0x85, (byte) 0xa1, (byte) 0x5a, (byte) 0x39});
    }

    private void checkPoiAoiLongitude1Example(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.CornerLongitudePoint1));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.CornerLongitudePoint1);
        assertEquals(v.getDisplayName(), "Corner Longitude Point 1");
        assertEquals(v.getDisplayableValue(), "116.9344\u00B0");
        assertTrue(v instanceof CornerLongitudePoint1);
        CornerLongitudePoint1 lon = (CornerLongitudePoint1) v;
        assertEquals(lon.getDegrees(), 116.9344, 0.0001);
        assertEquals(
                lon.getBytes(), new byte[] {(byte) 0x53, (byte) 0x27, (byte) 0x3F, (byte) 0xD4});
    }

    private void checkPoiAoiLatitude3Example(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.CornerLatitudePoint3));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.CornerLatitudePoint3);
        assertEquals(v.getDisplayName(), "Corner Latitude Point 3");
        assertEquals(v.getDisplayableValue(), "-86.0824\u00B0");
        assertTrue(v instanceof CornerLatitudePoint3);
        CornerLatitudePoint3 lat = (CornerLatitudePoint3) v;
        assertEquals(lat.getDegrees(), -86.0824, 0.0001);
        assertEquals(
                lat.getBytes(), new byte[] {(byte) 0x85, (byte) 0x92, (byte) 0x5a, (byte) 0x39});
    }

    private void checkPoiAoiLongitude3Example(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.CornerLongitudePoint3));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.CornerLongitudePoint3);
        assertEquals(v.getDisplayName(), "Corner Longitude Point 3");
        assertEquals(v.getDisplayableValue(), "117.0222\u00B0");
        assertTrue(v instanceof CornerLongitudePoint3);
        CornerLongitudePoint3 lon = (CornerLongitudePoint3) v;
        assertEquals(lon.getDegrees(), 117.0222, 0.0001);
        assertEquals(
                lon.getBytes(), new byte[] {(byte) 0x53, (byte) 0x37, (byte) 0x3F, (byte) 0xD4});
    }

    private void checkPoiAoiTypeExample(RvtAoiLocalSet aoiLocalSet) {
        assertTrue(aoiLocalSet.getTags().contains(RvtAoiMetadataKey.PoiAoiType));
        IRvtPoiAoiMetadataValue v = aoiLocalSet.getField(RvtAoiMetadataKey.PoiAoiType);
        assertEquals(v.getDisplayName(), "POI/AOI Type");
        assertEquals(v.getDisplayableValue(), "Target");
        assertTrue(v instanceof PoiAoiType);
        PoiAoiType type = (PoiAoiType) v;
        assertNotNull(type);
        assertEquals(type.getValue(), 3);
    }

    @Test
    public void createUnknownTag() throws KlvParseException {
        final byte[] bytes = new byte[] {0x03};
        verifyNoLoggerMessages();
        IRvtPoiAoiMetadataValue value =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.Undefined, bytes);
        verifySingleLoggerMessage("Unrecognized RVT AOI tag: Undefined");
        assertNull(value);
    }

    @Test
    public void constructFromMap() throws KlvParseException {
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values = buildAoiValues();
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(values);
        assertNotNull(aoiLocalSet);
        assertEquals(aoiLocalSet.getDisplayName(), "Area of Interest");
        assertEquals(aoiLocalSet.getDisplayableValue(), "My Point (516)");
        assertEquals(aoiLocalSet.getTags().size(), 4);
        checkPoiAoiNumberExample(aoiLocalSet);
        checkPoiAoiLatitude1Example(aoiLocalSet);
        checkPoiAoiLongitude1Example(aoiLocalSet);
        byte[] expectedBytes =
                new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x04, // T:1, L:2, V: 0x0204
                    (byte) 0x02,
                    (byte) 0x04,
                    (byte) 0x85,
                    (byte) 0xa1,
                    (byte) 0x5a,
                    (byte) 0x39, // T: 2, L:4, V: -86.0412
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x53,
                    (byte) 0x27,
                    (byte) 0x3F,
                    (byte) 0xD4, // T: 3, L:4, V: 116.9344
                    (byte) 0x09,
                    (byte) 0x08,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x50,
                    (byte) 0x6F,
                    (byte) 0x69,
                    (byte) 0x6e,
                    (byte) 0x74 // T:9, L: 8, V: "My Point"
                };
        assertEquals(aoiLocalSet.getBytes(), expectedBytes);
    }

    @Test
    public void checkLabelOnly() {
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        IRvtPoiAoiMetadataValue label =
                new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, "Test Label1");
        values.put(RvtAoiMetadataKey.PoiAoiLabel, label);
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(values);
        assertEquals(aoiLocalSet.getDisplayableValue(), "Test Label1");
    }

    @Test
    public void checkSourceIdOnly() {
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        IRvtPoiAoiMetadataValue sourceId =
                new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_SOURCE_ID, "Some Source");
        values.put(RvtAoiMetadataKey.PoiAoiSourceId, sourceId);
        RvtAoiLocalSet aoiLocalSet = new RvtAoiLocalSet(values);
        IKlvValue value = aoiLocalSet.getField((IKlvKey) RvtAoiMetadataKey.PoiAoiSourceId);
        assertTrue(value instanceof RvtPoiAoiTextString);
        RvtPoiAoiTextString textString = (RvtPoiAoiTextString) value;
        assertEquals(textString.getDisplayableValue(), "Some Source");
        assertEquals(aoiLocalSet.getDisplayableValue(), "[AOI Local Set]");
    }

    /**
     * Basic test values for AOI Local Set.
     *
     * @return Map ready to drop into the value constructor.
     * @throws KlvParseException if parsing goes wrong.
     */
    public static Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> buildAoiValues()
            throws KlvParseException {
        Map<RvtAoiMetadataKey, IRvtPoiAoiMetadataValue> values = new HashMap<>();
        IRvtPoiAoiMetadataValue poiNumberBytes =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.PoiAoiNumber, new byte[] {0x02, 0x04});
        values.put(RvtAoiMetadataKey.PoiAoiNumber, poiNumberBytes);
        final byte[] latBytes = new byte[] {(byte) 0x85, (byte) 0xa1, (byte) 0x5a, (byte) 0x39};
        IRvtPoiAoiMetadataValue lat =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.CornerLatitudePoint1, latBytes);
        values.put(RvtAoiMetadataKey.CornerLatitudePoint1, lat);
        final byte[] lonBytes = new byte[] {(byte) 0x53, (byte) 0x27, (byte) 0x3F, (byte) 0xD4};
        IRvtPoiAoiMetadataValue lon =
                RvtAoiLocalSet.createValue(RvtAoiMetadataKey.CornerLongitudePoint1, lonBytes);
        values.put(RvtAoiMetadataKey.CornerLongitudePoint1, lon);
        IRvtPoiAoiMetadataValue label =
                new RvtPoiAoiTextString(RvtPoiAoiTextString.POI_AOI_LABEL, "My Point");
        values.put(RvtAoiMetadataKey.PoiAoiLabel, label);
        return values;
    }
}
