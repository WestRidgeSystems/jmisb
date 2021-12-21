package org.jmisb.st1010;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for the EncodingFormat enum. */
public class EncodingFormatTest {

    public EncodingFormatTest() {}

    @Test
    public void checkST1201() {
        EncodingFormat uut = EncodingFormat.ST1201;
        assertEquals(uut.getValue(), 1);
    }

    @Test
    public void checkST1201Lookup() throws KlvParseException {
        EncodingFormat uut = EncodingFormat.getEncoding(1);
        assertEquals(uut, EncodingFormat.ST1201);
        assertEquals(uut.getValue(), 1);
    }

    @Test
    public void checkIEEE() {
        EncodingFormat uut = EncodingFormat.IEEE;
        assertEquals(uut.getValue(), 0);
    }

    @Test
    public void checkIEEELookup() throws KlvParseException {
        EncodingFormat uut = EncodingFormat.getEncoding(0);
        assertEquals(uut, EncodingFormat.IEEE);
        assertEquals(uut.getValue(), 0);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLookup2() throws KlvParseException {
        EncodingFormat.getEncoding(2);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkBadLookupNeg() throws KlvParseException {
        EncodingFormat.getEncoding(-1);
    }
}
