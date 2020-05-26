package org.jmisb.api.klv.st0903.vtarget;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ParseOptions;
import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.vfeature.VFeatureLS;
import org.jmisb.api.klv.st0903.vfeature.VFeatureMetadataKey;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for VFeature (ST0903 VTarget Tag 103).
 */
public class VFeatureTest
{
    private final byte[] featureBytes = new byte[]
    {   0x01, 45,
        0x75, 0x72, 0x6e, 0x3a, 0x75, 0x75, 0x69, 0x64,
        0x3a, 0x66, 0x38, 0x31, 0x64, 0x34, 0x66, 0x61,
        0x65, 0x2d, 0x37, 0x64, 0x65, 0x63, 0x2d, 0x31,
        0x31, 0x64, 0x30, 0x2d, 0x61, 0x37, 0x36, 0x35,
        0x2d, 0x30, 0x30, 0x61, 0x30, 0x63, 0x39, 0x31,
        0x65, 0x36, 0x62, 0x66, 0x36
    };

    @Test
    public void testConstructFromEncodedBytes() throws KlvParseException
    {
        VFeature vfeature = new VFeature(featureBytes, EnumSet.noneOf(ParseOptions.class));
        assertEquals(vfeature.getBytes(), featureBytes);
        assertEquals(vfeature.getDisplayName(), "Target Feature");
        assertEquals(vfeature.getDisplayableValue(), "[VFeature]");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException
    {
        IVmtiMetadataValue value = VTargetPack.createValue(VTargetMetadataKey.VFeature, featureBytes);
        assertTrue(value instanceof VFeature);
        VFeature vFeature = (VFeature)value;
        assertEquals(vFeature.getBytes(), featureBytes);
        assertEquals(vFeature.getDisplayName(), "Target Feature");
        assertEquals(vFeature.getDisplayableValue(), "[VFeature]");
        assertEquals(vFeature.getFeature().getTags().size(), 1);
    }

    @Test
    public void constructFromValue() throws KlvParseException, URISyntaxException
    {
        Map<VFeatureMetadataKey, IVmtiMetadataValue> values = new HashMap<>();
        IVmtiMetadataValue schema = VFeatureLS.createValue(VFeatureMetadataKey.schema, featureBytes);
        values.put(VFeatureMetadataKey.schema, schema);
        final byte[] urlBytes = new byte[]{
            0x75, 0x72, 0x6e, 0x3a, 0x75, 0x75, 0x69, 0x64,
            0x3a, 0x66, 0x38, 0x31, 0x64, 0x34, 0x66, 0x61,
            0x65, 0x2d, 0x37, 0x64, 0x65, 0x63, 0x2d, 0x31,
            0x31, 0x64, 0x30, 0x2d, 0x61, 0x37, 0x36, 0x35,
            0x2d, 0x30, 0x30, 0x61, 0x30, 0x63, 0x39, 0x31,
            0x65, 0x36, 0x62, 0x66, 0x36};
        IVmtiMetadataValue imageUri = VFeatureLS.createValue(VFeatureMetadataKey.schema, urlBytes);
        values.put(VFeatureMetadataKey.schema, imageUri);
        VFeatureLS vFeatureLS = new VFeatureLS(values);
        VFeature vFeature = new VFeature(vFeatureLS);
        assertNotNull(vFeature);
        assertEquals(vFeature.getFeature().getTags().size(), 1);
    }
}
