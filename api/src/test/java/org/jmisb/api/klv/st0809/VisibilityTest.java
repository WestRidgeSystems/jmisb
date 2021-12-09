package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Visibility. */
public class VisibilityTest {

    @Test
    public void fromValue() {
        Visibility uut = new Visibility(32.2f);
        assertEquals(uut.getVisibility(), 32.2f);
        assertEquals(uut.getDisplayName(), "Visibility");
        assertEquals(uut.getDisplayableValue(), "32.200 km");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x00, (byte) 0xcc, (byte) 0xcd});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        Visibility uut =
                new Visibility(new byte[] {(byte) 0x42, (byte) 0x00, (byte) 0xcc, (byte) 0xcd});
        assertEquals(uut.getVisibility(), 32.2f);
        assertEquals(uut.getDisplayName(), "Visibility");
        assertEquals(uut.getDisplayableValue(), "32.200 km");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x00, (byte) 0xcc, (byte) 0xcd});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.Visibility,
                        new byte[] {(byte) 0x42, (byte) 0x00, (byte) 0xcc, (byte) 0xcd});
        assertTrue(v instanceof Visibility);
        Visibility uut = (Visibility) v;
        assertEquals(uut.getVisibility(), 32.2f);
        assertEquals(uut.getDisplayName(), "Visibility");
        assertEquals(uut.getDisplayableValue(), "32.200 km");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x00, (byte) 0xcc, (byte) 0xcd});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new Visibility(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new Visibility(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new Visibility(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
