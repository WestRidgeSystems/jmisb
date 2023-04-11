package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SinglePointRangeMeasurement. */
public class SinglePointRangeMeasurementTest {

    @Test
    public void fromValue() {
        SinglePointRangeMeasurement uut = new SinglePointRangeMeasurement(12.0f);
        assertEquals(uut.getRange(), 12.0f);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement");
        assertEquals(uut.getDisplayableValue(), "12.000 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x28,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void fromBytesFloat() throws KlvParseException {
        SinglePointRangeMeasurement uut =
                new SinglePointRangeMeasurement(
                        new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
        assertEquals(uut.getRange(), 12.0f);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement");
        assertEquals(uut.getDisplayableValue(), "12.000 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x28,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void fromBytesDouble() throws KlvParseException {
        SinglePointRangeMeasurement uut =
                new SinglePointRangeMeasurement(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x28,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00,
                            (byte) 0x00
                        });
        assertEquals(uut.getRange(), 12.0f);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement");
        assertEquals(uut.getDisplayableValue(), "12.000 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x28,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IRangeImageMetadataValue v =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.SinglePointRangeMeasurement,
                        new byte[] {(byte) 0x41, (byte) 0x40, (byte) 0x00, (byte) 0x00});
        assertTrue(v instanceof SinglePointRangeMeasurement);
        SinglePointRangeMeasurement uut = (SinglePointRangeMeasurement) v;
        assertEquals(uut.getRange(), 12.0f);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement");
        assertEquals(uut.getDisplayableValue(), "12.000 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x28,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength3() throws KlvParseException {
        new SinglePointRangeMeasurement(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength5() throws KlvParseException {
        new SinglePointRangeMeasurement(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
