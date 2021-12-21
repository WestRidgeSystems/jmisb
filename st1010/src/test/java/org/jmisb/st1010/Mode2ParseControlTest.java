package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class Mode2ParseControlTest {

    public Mode2ParseControlTest() {}

    @Test
    public void checkB308() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result = parser.parse(new byte[] {(byte) 0x00, (byte) 0xB3, (byte) 0x08});
        assertEquals(result.getStandardDeviationLength(), 8);
        assertEquals(result.getCorrelationCoefficientLength(), 3);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
    }

    @Test
    public void check8413() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        SDCC result = parser.parse(new byte[] {(byte) 0x00, (byte) 0x84, (byte) 0x13});
        assertEquals(result.getStandardDeviationLength(), 3);
        assertEquals(result.getCorrelationCoefficientLength(), 4);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.ST1201);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.IEEE);
    }
}
