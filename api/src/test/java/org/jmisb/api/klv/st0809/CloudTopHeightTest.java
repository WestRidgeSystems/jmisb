package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CloudTopHeight. */
public class CloudTopHeightTest {

    @Test
    public void fromValue() {
        CloudTopHeight uut = new CloudTopHeight(60.0f);
        assertEquals(uut.getHeight(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Top Height");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        CloudTopHeight uut =
                new CloudTopHeight(new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getHeight(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Top Height");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CloudTopHeight,
                        new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof CloudTopHeight);
        CloudTopHeight uut = (CloudTopHeight) v;
        assertEquals(uut.getHeight(), 60.0f);
        assertEquals(uut.getDisplayName(), "Cloud Top Height");
        assertEquals(uut.getDisplayableValue(), "60.0 m");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x42, (byte) 0x70, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new CloudTopHeight(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new CloudTopHeight(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new CloudTopHeight(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
