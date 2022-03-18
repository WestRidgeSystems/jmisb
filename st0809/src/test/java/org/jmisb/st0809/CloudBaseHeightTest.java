package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CloudBaseHeight. */
public class CloudBaseHeightTest {

    @Test
    public void fromValue() {
        CloudBaseHeight uut = new CloudBaseHeight(8.0f);
        assertEquals(uut.getHeight(), 8.0f);
        assertEquals(uut.getDisplayName(), "Cloud Base Height");
        assertEquals(uut.getDisplayableValue(), "8.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        CloudBaseHeight uut =
                new CloudBaseHeight(
                        new byte[] {(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getHeight(), 8.0f);
        assertEquals(uut.getDisplayName(), "Cloud Base Height");
        assertEquals(uut.getDisplayableValue(), "8.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CloudBaseHeight,
                        new byte[] {(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof CloudBaseHeight);
        CloudBaseHeight uut = (CloudBaseHeight) v;
        assertEquals(uut.getHeight(), 8.0f);
        assertEquals(uut.getDisplayName(), "Cloud Base Height");
        assertEquals(uut.getDisplayableValue(), "8.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new CloudBaseHeight(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new CloudBaseHeight(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new CloudBaseHeight(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
