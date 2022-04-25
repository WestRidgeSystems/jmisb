package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CloudThickness. */
public class CloudThicknessTest {

    @Test
    public void fromValue() {
        CloudThickness uut = new CloudThickness(60.0f);
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        CloudThickness uut =
                new CloudThickness(new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CloudThickness,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof CloudThickness);
        CloudThickness uut = (CloudThickness) v;
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new CloudThickness(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new CloudThickness(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new CloudThickness(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
