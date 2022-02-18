package org.jmisb.api.klv.st0601;

import org.jmisb.api.klv.IKlvKey;

/**
 * Enumeration of the various fields used in {@link CountryCodes}.
 *
 * <p>Each of these corresponds to part of the country code information. Note that not all of them
 * have to be filled in.
 */
public enum CountryCodeKey implements IKlvKey {
    /** Coding Method. */
    CodingMethod(0),
    /** Overflight Country. */
    OverflightCountry(1),
    /** Operator Country. */
    OperatorCountry(2),
    /** Country of Manufacture. */
    CountryOfManufacture(3);

    private CountryCodeKey(int key) {
        this.tag = key;
    }

    private final int tag;

    @Override
    public int getIdentifier() {
        return tag;
    }
}
