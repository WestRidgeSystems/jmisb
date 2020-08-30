package org.jmisb.api.klv.st0601;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.testng.Assert;
import org.testng.annotations.Test;

/** Tests for Country Codes */
public class CountryCodesTest {
    static final byte[] ST_EXAMPLE_BYTES =
            new byte[] {0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E, 0x00, 0x03, 0x46, 0x52, 0x41};

    @Test
    public void testFromValues() {
        CountryCodes countryCodes =
                new CountryCodes(CountryCodingMethod.GENC_THREE_LETTER, "CAN", "", "FRA");
        checkExampleValues(countryCodes);
    }

    @Test
    public void testFromBytes() throws KlvParseException {
        CountryCodes countryCodes = new CountryCodes(ST_EXAMPLE_BYTES);
        checkExampleValues(countryCodes);
    }

    @Test
    public void testFactory() throws KlvParseException {
        IUasDatalinkValue v =
                UasDatalinkFactory.createValue(UasDatalinkTag.CountryCodes, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof CountryCodes);
        CountryCodes countryCodes = (CountryCodes) v;
        checkExampleValues(countryCodes);
    }

    private void checkExampleValues(CountryCodes countryCodes) {
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.GENC_THREE_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "CAN");
        assertEquals(countryCodes.getOperatorCountry(), "");
        assertEquals(countryCodes.getCountryOfManufacture(), "FRA");
        assertEquals(countryCodes.getBytes(), ST_EXAMPLE_BYTES);
    }

    @Test
    public void testTruncatedBytesLast() throws KlvParseException {
        CountryCodes countryCodes =
                new CountryCodes(new byte[] {0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x55, 0x53});
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.ISO3166_TWO_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "AU");
        assertEquals(countryCodes.getOperatorCountry(), "US");
        assertEquals(countryCodes.getCountryOfManufacture(), "");
        assertEquals(
                countryCodes.getBytes(),
                new byte[] {0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x55, 0x53});
    }

    @Test
    public void testTruncatedBytesBoth() throws KlvParseException {
        CountryCodes countryCodes =
                new CountryCodes(new byte[] {0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E});
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.GENC_THREE_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "CAN");
        assertEquals(countryCodes.getOperatorCountry(), "");
        assertEquals(countryCodes.getCountryOfManufacture(), "");
        assertEquals(countryCodes.getBytes(), new byte[] {0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz1() throws KlvParseException {
        new CountryCodes(
                new byte[] {
                    0x06, (byte) 0xb5, 0x01, 0x48, (byte) 0x8a, (byte) 0xa5, (byte) 0xc4, 0x70
                });
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fuzz2() throws KlvParseException {
        new CountryCodes(new byte[] {0x1b});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void truncatedBad1() throws KlvParseException {
        new CountryCodes(new byte[] {0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x55, 0x53, 0x02});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void truncatedBad2() throws KlvParseException {
        new CountryCodes(new byte[] {0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x30});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void truncatedBad3() throws KlvParseException {
        new CountryCodes(new byte[] {0x01, 0x01, 0x02, 0x41});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void truncatedBad4() throws KlvParseException {
        new CountryCodes(new byte[] {0x01, 0x03, 0x30, 0x30, 0x30, 0x02});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void truncatedBad5() throws KlvParseException {
        new CountryCodes(new byte[] {0x01});
    }
}
