package org.jmisb.api.klv.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CloudCoverPercentage. */
public class CloudCoverPercentageTest {

    @Test
    public void fromValue() {
        CloudCoverPercentage uut = new CloudCoverPercentage(30.0f);
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Cloud Cover");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        CloudCoverPercentage uut =
                new CloudCoverPercentage(
                        new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Cloud Cover");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.CloudCoverPercentage,
                        new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof CloudCoverPercentage);
        CloudCoverPercentage uut = (CloudCoverPercentage) v;
        assertEquals(uut.getValue(), 30.0f);
        assertEquals(uut.getDisplayName(), "Cloud Cover");
        assertEquals(uut.getDisplayableValue(), "30.0 %");
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xf0, (byte) 0x00, (byte) 0x00});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() throws KlvParseException {
        new CloudCoverPercentage(-0.1f);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooLarge() throws KlvParseException {
        new CloudCoverPercentage(100.1f);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooShort() throws KlvParseException {
        new CloudCoverPercentage(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testTooLong() throws KlvParseException {
        new CloudCoverPercentage(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testNotDouble() throws KlvParseException {
        new CloudCoverPercentage(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08});
    }
}
