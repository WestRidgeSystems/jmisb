package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for FogThickness. */
public class FogThicknessTest {

    @Test
    public void fromValue() {
        FogThickness uut = new FogThickness(60.0f);
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Fog Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        FogThickness uut =
                new FogThickness(new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Fog Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.FogThickness,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof FogThickness);
        FogThickness uut = (FogThickness) v;
        assertEquals(uut.getThickness(), 60.0f);
        assertEquals(uut.getDisplayName(), "Fog Thickness");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new FogThickness(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new FogThickness(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new FogThickness(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
