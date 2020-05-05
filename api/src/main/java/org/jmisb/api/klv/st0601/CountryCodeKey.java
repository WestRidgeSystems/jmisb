package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the keys for CountryCodes.
 */
public enum CountryCodeKey implements IKlvKey
{
    CountryCodingMethod(0),
    OverflightCountry(1),
    OperatorCountry(2),
    CountryOfManufacture(3);

    CountryCodeKey(int key)
    {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getTagCode()
    {
        return tag;
    }
}
