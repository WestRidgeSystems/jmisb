package org.jmisb.api.klv.st0601;

import java.util.HashSet;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st0102.CountryCodingMethod;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Tests for Country Codes
 */
public class CountryCodesTest
{
    static final byte[] ST_EXAMPLE_BYTES = new byte[]{0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E, 0x00, 0x03, 0x46, 0x52, 0x41};

    @Test
    public void testFromValues()
    {
        CountryCodes countryCodes = new CountryCodes(CountryCodingMethod.GENC_THREE_LETTER, "CAN", "", "FRA");
        checkExampleValues(countryCodes);
    }

    @Test
    public void testFromBytes()
    {
        CountryCodes countryCodes = new CountryCodes(ST_EXAMPLE_BYTES);
        checkExampleValues(countryCodes);
    }

    @Test
    public void testFactory() throws KlvParseException
    {
        IUasDatalinkValue v = UasDatalinkFactory.createValue(UasDatalinkTag.CountryCodes, ST_EXAMPLE_BYTES);
        Assert.assertTrue(v instanceof CountryCodes);
        CountryCodes countryCodes = (CountryCodes)v;
        checkExampleValues(countryCodes);
    }

    private void checkExampleValues(CountryCodes countryCodes)
    {
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.GENC_THREE_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "CAN");
        assertEquals(countryCodes.getOperatorCountry(), "");
        assertEquals(countryCodes.getCountryOfManufacture(), "FRA");
        assertEquals(countryCodes.getBytes(), ST_EXAMPLE_BYTES);
        Set<CountryCodeKey> expectedTags = new HashSet<>();
        expectedTags.add(CountryCodeKey.CountryCodingMethod);
        expectedTags.add(CountryCodeKey.OverflightCountry);
        expectedTags.add(CountryCodeKey.CountryOfManufacture);
        assertEquals(countryCodes.getTags(), expectedTags);
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayName(), "Country Coding Method");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayableValue(), "GENC_THREE_LETTER");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayName(), "Overflight Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayableValue(), "CAN");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayName(), "Operator Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayableValue(), "");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayName(), "Country of Manufacture");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayableValue(), "FRA");
        assertNull(countryCodes.getField(() -> 616));
    }

    @Test
    public void testTruncatedBytesLast()
    {
        CountryCodes countryCodes = new CountryCodes(new byte[]{0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x55, 0x53});
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.ISO3166_TWO_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "AU");
        assertEquals(countryCodes.getOperatorCountry(), "US");
        assertEquals(countryCodes.getCountryOfManufacture(), "");
        assertEquals(countryCodes.getBytes(), new byte[]{0x01, 0x01, 0x02, 0x41, 0x55, 0x02, 0x55, 0x53});
        Set<CountryCodeKey> expectedTags = new HashSet<>();
        expectedTags.add(CountryCodeKey.CountryCodingMethod);
        expectedTags.add(CountryCodeKey.OverflightCountry);
        expectedTags.add(CountryCodeKey.OperatorCountry);
        assertEquals(countryCodes.getTags(), expectedTags);
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayName(), "Country Coding Method");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayableValue(), "ISO3166_TWO_LETTER");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayName(), "Overflight Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayableValue(), "AU");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayName(), "Operator Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayableValue(), "US");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayName(), "Country of Manufacture");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayableValue(), "");
    }

    @Test
    public void testTruncatedBytesBoth()
    {
        CountryCodes countryCodes = new CountryCodes(new byte[]{0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E});
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.GENC_THREE_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "CAN");
        assertEquals(countryCodes.getOperatorCountry(), "");
        assertEquals(countryCodes.getCountryOfManufacture(), "");
        assertEquals(countryCodes.getBytes(), new byte[]{0x01, 0x0E, 0x03, 0x43, 0x41, 0x4E});
        Set<CountryCodeKey> expectedTags = new HashSet<>();
        expectedTags.add(CountryCodeKey.CountryCodingMethod);
        expectedTags.add(CountryCodeKey.OverflightCountry);
        assertEquals(countryCodes.getTags(), expectedTags);
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayName(), "Country Coding Method");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayableValue(), "GENC_THREE_LETTER");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayName(), "Overflight Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayableValue(), "CAN");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayName(), "Operator Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayableValue(), "");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayName(), "Country of Manufacture");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayableValue(), "");
    }

    @Test
    public void testFromValuesNoOverflight()
    {
        CountryCodes countryCodes = new CountryCodes(CountryCodingMethod.ISO3166_THREE_LETTER, "", "", "CAN");
        assertEquals(countryCodes.getDisplayName(), "Country Codes");
        assertEquals(countryCodes.getDisplayableValue(), "[Country Codes]");
        assertEquals(countryCodes.getCodingMethod(), CountryCodingMethod.ISO3166_THREE_LETTER);
        assertEquals(countryCodes.getOverflightCountry(), "");
        assertEquals(countryCodes.getOperatorCountry(), "");
        assertEquals(countryCodes.getCountryOfManufacture(), "CAN");
        assertEquals(countryCodes.getBytes(), new byte[]{0x01, 0x02, 0x0, 0x0, 0x03, 0x43, 0x41, 0x4E});
        Set<CountryCodeKey> expectedTags = new HashSet<>();
        expectedTags.add(CountryCodeKey.CountryCodingMethod);
        expectedTags.add(CountryCodeKey.CountryOfManufacture);
        assertEquals(countryCodes.getTags(), expectedTags);
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayName(), "Country Coding Method");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryCodingMethod).getDisplayableValue(), "ISO3166_THREE_LETTER");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayName(), "Overflight Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OverflightCountry).getDisplayableValue(), "");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayName(), "Operator Country");
        assertEquals(countryCodes.getField(CountryCodeKey.OperatorCountry).getDisplayableValue(), "");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayName(), "Country of Manufacture");
        assertEquals(countryCodes.getField(CountryCodeKey.CountryOfManufacture).getDisplayableValue(), "CAN");
    }

    @Test
    public void testCountryCodeKeyValue()
    {
        assertEquals(CountryCodeKey.CountryCodingMethod.getTagCode(), 0);
        assertEquals(CountryCodeKey.CountryOfManufacture.getTagCode(), 3);
    }
}
