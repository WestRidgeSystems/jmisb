package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

public class Mode1ParseControlTest {

    public Mode1ParseControlTest() {}

    @Test
    public void check4B() throws KlvParseException {
        SDCCParser parser = new SDCCParser();
        parser.setStandardDeviationFormat(EncodingFormat.IEEE);
        SDCC result = parser.parse(new byte[] {0x00, 0x4B});
        assertEquals(result.getStandardDeviationLength(), 4);
        assertEquals(result.getCorrelationCoefficientLength(), 3);
        assertEquals(result.getStandardDeviationFormat(), EncodingFormat.IEEE);
        assertEquals(result.getCorrelationCoefficientFormat(), EncodingFormat.ST1201);
    }
}
