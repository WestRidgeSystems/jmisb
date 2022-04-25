package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for RytovParameter. */
public class RytovParameterTest {

    @Test
    public void fromValue() {
        RytovParameter uut = new RytovParameter(0.14f);
        assertEquals(uut.getValue(), 0.14f);
        assertEquals(uut.getDisplayName(), "Rytov Parameter");
        assertEquals(uut.getDisplayableValue(), "0.140");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x0f, (byte) 0x5c, (byte) 0x29});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        RytovParameter uut =
                new RytovParameter(new byte[] {(byte) 0x3e, (byte) 0x0f, (byte) 0x5c, (byte) 0x29});
        assertEquals(uut.getValue(), 0.14f);
        assertEquals(uut.getDisplayName(), "Rytov Parameter");
        assertEquals(uut.getDisplayableValue(), "0.140");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x0f, (byte) 0x5c, (byte) 0x29});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.RytovParameter,
                        new byte[] {(byte) 0x3e, (byte) 0x0f, (byte) 0x5c, (byte) 0x29});
        assertTrue(v instanceof RytovParameter);
        RytovParameter uut = (RytovParameter) v;
        assertEquals(uut.getValue(), 0.14f);
        assertEquals(uut.getDisplayName(), "Rytov Parameter");
        assertEquals(uut.getDisplayableValue(), "0.140");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x3e, (byte) 0x0f, (byte) 0x5c, (byte) 0x29});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new RytovParameter(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new RytovParameter(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new RytovParameter(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
