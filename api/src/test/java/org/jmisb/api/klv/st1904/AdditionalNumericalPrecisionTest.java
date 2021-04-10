package org.jmisb.api.klv.st1904;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvValue;
import org.testng.annotations.Test;

/** Additional unit tests for NumericalPrecision. */
public class AdditionalNumericalPrecisionTest {

    public AdditionalNumericalPrecisionTest() {}

    @Test
    public void checkFromBytes() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    (byte) 0x22,
                    (byte) 0x0a,
                    (byte) 0x21,
                    (byte) 0x08,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x40,
                    (byte) 0x49,
                    (byte) 0x16,
                    (byte) 0x87
                };
        NumericalPrecision uut = new NumericalPrecision(bytes, 0, 12);
        assertEquals(uut.getIdentifiers().size(), 1);
        assertTrue(uut.getIdentifiers().contains(NumericalPrecisionMetadataKey.sdcc));
        IKlvValue sdccValue = uut.getField(NumericalPrecisionMetadataKey.sdcc);
        assertTrue(sdccValue instanceof SDCC);
        SDCC sdcc = (SDCC) sdccValue;
        assertNotNull(sdcc);
        assertEquals(sdcc.getIdentifiers().size(), 1);
        assertTrue(sdcc.getIdentifiers().contains(SDCCMetadataKey.sdVals));
        IKlvValue sdValsValue = sdcc.getField(SDCCMetadataKey.sdVals);
        assertTrue(sdValsValue instanceof SDCC_SdVals);
        SDCC_SdVals sdVals = (SDCC_SdVals) sdValsValue;
        assertEquals(sdVals.getValue().length, 1);
        assertEquals(sdVals.getValue()[0], 3.142, 0.001);
        byte[] generatedBytes = uut.getBytes();
        assertEquals(
                generatedBytes,
                new byte[] {
                    (byte) 0x22,
                    (byte) 0x0e,
                    (byte) 0x21,
                    (byte) 0x0c,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x01,
                    (byte) 0x40,
                    (byte) 0x09,
                    (byte) 0x22,
                    (byte) 0xd0,
                    (byte) 0xe0,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
                });
    }
}
