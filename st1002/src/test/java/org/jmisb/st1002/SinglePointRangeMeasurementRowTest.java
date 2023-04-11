package org.jmisb.st1002;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for SinglePointRangeMeasurementRow. */
public class SinglePointRangeMeasurementRowTest {

    @Test
    public void fromValue() {
        SinglePointRangeMeasurementRow uut = new SinglePointRangeMeasurementRow(984.2);
        assertEquals(uut.getRow(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Row");
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
        SinglePointRangeMeasurementRow uut =
                new SinglePointRangeMeasurementRow(
                        new byte[] {(byte) 0x44, (byte) 0x76, (byte) 0x0C, (byte) 0xCD});
        assertEquals(uut.getRow(), 984.2, 0.0001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Row");
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
        SinglePointRangeMeasurementRow uut =
                new SinglePointRangeMeasurementRow(
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
        assertEquals(uut.getRow(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Row");
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
                        RangeImageMetadataKey.SinglePointRangeMeasurementRowCoordinate,
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
        assertTrue(v instanceof SinglePointRangeMeasurementRow);
        SinglePointRangeMeasurementRow uut = (SinglePointRangeMeasurementRow) v;
        assertEquals(uut.getRow(), 984.2, 0.000000001);
        assertEquals(uut.getDisplayName(), "Single Point Range Measurement Row");
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
        new SinglePointRangeMeasurementRow(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testBadLength5() throws KlvParseException {
        new SinglePointRangeMeasurementRow(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }
}
