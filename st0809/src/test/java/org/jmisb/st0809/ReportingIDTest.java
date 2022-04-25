package org.jmisb.st0809;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for ReportingID. */
public class ReportingIDTest {

    @Test
    public void fromValue() {
        ReportingID uut = new ReportingID("Noarlunga");
        assertEquals(uut.getValue(), "Noarlunga");
        assertEquals(uut.getDisplayName(), "Reporting ID");
        assertEquals(uut.getDisplayableValue(), "Noarlunga");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x4e,
                    (byte) 0x6f,
                    (byte) 0x61,
                    (byte) 0x72,
                    (byte) 0x6c,
                    (byte) 0x75,
                    (byte) 0x6e,
                    (byte) 0x67,
                    (byte) 0x61
                });
    }

    @Test
    public void fromBytes() {
        ReportingID uut =
                new ReportingID(
                        new byte[] {
                            (byte) 0x4e,
                            (byte) 0x6f,
                            (byte) 0x61,
                            (byte) 0x72,
                            (byte) 0x6c,
                            (byte) 0x75,
                            (byte) 0x6e,
                            (byte) 0x67,
                            (byte) 0x61
                        });
        assertEquals(uut.getValue(), "Noarlunga");
        assertEquals(uut.getDisplayName(), "Reporting ID");
        assertEquals(uut.getDisplayableValue(), "Noarlunga");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x4e,
                    (byte) 0x6f,
                    (byte) 0x61,
                    (byte) 0x72,
                    (byte) 0x6c,
                    (byte) 0x75,
                    (byte) 0x6e,
                    (byte) 0x67,
                    (byte) 0x61
                });
    }

    @Test
    public void fromFactory() throws KlvParseException {
        IMeteorologicalMetadataValue v =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.ReportingID,
                        new byte[] {
                            (byte) 0x4e,
                            (byte) 0x6f,
                            (byte) 0x61,
                            (byte) 0x72,
                            (byte) 0x6c,
                            (byte) 0x75,
                            (byte) 0x6e,
                            (byte) 0x67,
                            (byte) 0x61
                        });
        assertTrue(v instanceof ReportingID);
        ReportingID uut = (ReportingID) v;
        assertEquals(uut.getValue(), "Noarlunga");
        assertEquals(uut.getDisplayName(), "Reporting ID");
        assertEquals(uut.getDisplayableValue(), "Noarlunga");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x4e,
                    (byte) 0x6f,
                    (byte) 0x61,
                    (byte) 0x72,
                    (byte) 0x6c,
                    (byte) 0x75,
                    (byte) 0x6e,
                    (byte) 0x67,
                    (byte) 0x61
                });
    }
}
