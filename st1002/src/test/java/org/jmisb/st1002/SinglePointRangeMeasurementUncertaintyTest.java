package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SinglePointRangeMeasurementUncertainty. */
public class SinglePointRangeMeasurementUncertaintyTest {

    @Test
    public void fromValue() {
        SinglePointRangeMeasurementUncertainty uut =
                new SinglePointRangeMeasurementUncertainty(0.15);
        assertEquals(uut.getRange(), 0.15);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Uncertainty");
        assertEquals(uut.getDisplayableValue(), "0.150 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x3F,
                    (byte) 0xC3,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33
                });
    }

    @Test
    public void fromBytesFloat() throws KlvParseException {
        SinglePointRangeMeasurementUncertainty uut =
                new SinglePointRangeMeasurementUncertainty(
                        new byte[] {(byte) 0x3E, (byte) 0x19, (byte) 0x99, (byte) 0x9A});
        assertEquals(uut.getRange(), 0.15, 0.00000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Uncertainty");
        assertEquals(uut.getDisplayableValue(), "0.150 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x3F,
                    (byte) 0xC3,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void fromBytesDouble() throws KlvParseException {
        SinglePointRangeMeasurementUncertainty uut =
                new SinglePointRangeMeasurementUncertainty(
                        new byte[] {
                            (byte) 0x3F,
                            (byte) 0xC3,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x33,
                            (byte) 0x33
                        });
        assertEquals(uut.getRange(), 0.15);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Uncertainty");
        assertEquals(uut.getDisplayableValue(), "0.150 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x3F,
                    (byte) 0xC3,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x33
                });
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IRangeImageMetadataValue v =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.SinglePointRangeMeasurementUncertainty,
                        new byte[] {(byte) 0x3E, (byte) 0x19, (byte) 0x99, (byte) 0x9A});
        assertTrue(v instanceof SinglePointRangeMeasurementUncertainty);
        SinglePointRangeMeasurementUncertainty uut = (SinglePointRangeMeasurementUncertainty) v;
        assertEquals(uut.getRange(), 0.15, 0.00000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Uncertainty");
        assertEquals(uut.getDisplayableValue(), "0.150 m");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x3F,
                    (byte) 0xC3,
                    (byte) 0x33,
                    (byte) 0x33,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength3() throws KlvParseException {
        new SinglePointRangeMeasurementUncertainty(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength5() throws KlvParseException {
        new SinglePointRangeMeasurementUncertainty(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
