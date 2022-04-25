package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CloudSkyCoverType. */
public class CloudSkyCoverTypeTest {

    @Test
    public void fromValue() {
        CloudSkyCoverType uut = new CloudSkyCoverType("cirrus");
        assertEquals(uut.getValue(), "cirrus");
        assertEquals(uut.getDisplayName(), "Cloud Sky Cover Type");
        assertEquals(uut.getDisplayableValue(), "cirrus");
        assertEquals(uut.getBytes(), new byte[] {0x63, 0x69, 0x72, 0x72, 0x75, 0x73});
    }

    @Test
    public void fromBytes() {
        CloudSkyCoverType uut =
                new CloudSkyCoverType(new byte[] {0x63, 0x69, 0x72, 0x72, 0x75, 0x73});
        assertEquals(uut.getValue(), "cirrus");
        assertEquals(uut.getDisplayName(), "Cloud Sky Cover Type");
        assertEquals(uut.getDisplayableValue(), "cirrus");
        assertEquals(uut.getBytes(), new byte[] {0x63, 0x69, 0x72, 0x72, 0x75, 0x73});
    }

    @Test
    public void fromFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CloudSkyCoverType,
                        new byte[] {0x63, 0x69, 0x72, 0x72, 0x75, 0x73});
        assertTrue(v instanceof CloudSkyCoverType);
        CloudSkyCoverType uut = (CloudSkyCoverType) v;
        assertEquals(uut.getValue(), "cirrus");
        assertEquals(uut.getDisplayName(), "Cloud Sky Cover Type");
        assertEquals(uut.getDisplayableValue(), "cirrus");
        assertEquals(uut.getBytes(), new byte[] {0x63, 0x69, 0x72, 0x72, 0x75, 0x73});
    }
}
