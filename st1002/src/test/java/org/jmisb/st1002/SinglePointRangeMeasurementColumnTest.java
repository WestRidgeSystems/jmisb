package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SinglePointRangeMeasurementColumn. */
public class SinglePointRangeMeasurementColumnTest {

    @Test
    public void fromValue() {
        SinglePointRangeMeasurementColumn uut = new SinglePointRangeMeasurementColumn(984.2);
        assertEquals(uut.getColumn(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Column");
        assertEquals(uut.getDisplayableValue(), "984.2");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x8E,
                    (byte) 0xC1,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A
                });
    }

    @Test
    public void fromBytesFloat() throws KlvParseException {
        SinglePointRangeMeasurementColumn uut =
                new SinglePointRangeMeasurementColumn(
                        new byte[] {(byte) 0x44, (byte) 0x76, (byte) 0x0C, (byte) 0xCD});
        assertEquals(uut.getColumn(), 984.2, 0.0001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Column");
        assertEquals(uut.getDisplayableValue(), "984.2");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x8E,
                    (byte) 0xC1,
                    (byte) 0x99,
                    (byte) 0xA0,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }

    @Test
    public void fromBytesDouble() throws KlvParseException {
        SinglePointRangeMeasurementColumn uut =
                new SinglePointRangeMeasurementColumn(
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x8E,
                            (byte) 0xC1,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x9A
                        });
        assertEquals(uut.getColumn(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Column");
        assertEquals(uut.getDisplayableValue(), "984.2");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x8E,
                    (byte) 0xC1,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A
                });
    }

    @Test
    public void fromBytesFactory() throws KlvParseException {
        IRangeImageMetadataValue v =
                RangeImageLocalSet.createValue(
                        RangeImageMetadataKey.SinglePointRangeMeasurementColumnCoordinate,
                        new byte[] {
                            (byte) 0x40,
                            (byte) 0x8E,
                            (byte) 0xC1,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x99,
                            (byte) 0x9A
                        });
        assertTrue(v instanceof SinglePointRangeMeasurementColumn);
        SinglePointRangeMeasurementColumn uut = (SinglePointRangeMeasurementColumn) v;
        assertEquals(uut.getColumn(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Column");
        assertEquals(uut.getDisplayableValue(), "984.2");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x40,
                    (byte) 0x8E,
                    (byte) 0xC1,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x99,
                    (byte) 0x9A
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength3() throws KlvParseException {
        new SinglePointRangeMeasurementColumn(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength5() throws KlvParseException {
        new SinglePointRangeMeasurementColumn(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
