package org.jmisb.api.klv.st0102;

import org.jmisb.api.klv.IKlvValue;

/**
 * Country coding methods.
 *
 * <p>Country codes are used in security markings for identifying which country classified the
 * motion imagery, which countries the motion imagery is releasable to, and which countries are the
 * object of the motion imagery (in the frame). There are various ways in which country codes are to
 * be interpreted, as shown in this enumeration.
 */
public enum CountryCodingMethod implements IKlvValue {
    /**
     * ISO-3166 Two Letter codes.
     *
     * <p>For example, Australia is "AU".
     */
    ISO3166_TWO_LETTER,
    /**
     * ISO-3166 Three Letter codes.
     *
     * <p>For example, Australia is "AUS".
     */
    ISO3166_THREE_LETTER,
    ISO3166_MIXED,
    /**
     * ISO-3166 Numeric codes.
     *
     * <p>For example, Australia is "036".
     */
    ISO3166_NUMERIC,
    /**
     * FIPS 10-4 codes.
     *
     * <p>For example, Australia is "AS".
     *
     * <p>This is a legacy coding method, only used in the US.
     */
    FIPS10_4_TWO_LETTER,
    FIPS10_4_FOUR_LETTER,
    FIPS10_4_MIXED,
    /**
     * STANAG 1059 Two Letter codes.
     *
     * <p>Similar to an old version of ISO-3166 Two Letter codes, but with some changes for NATO
     * policy.
     */
    C1059_TWO_LETTER,
    /**
     * STANAG 1059 Three Letter codes.
     *
     * <p>Similar to an old version of ISO-3166 Three Letter codes, but with some changes for NATO
     * policy.
     */
    C1059_THREE_LETTER,
    /**
     * Used for values that do not have corresponding meaning.
     *
     * <p>This is not valid, and should not be created.
     */
    OMITTED_VALUE,
    STANAG_1059_MIXED,
    /**
     * GENC Two Letter codes.
     *
     * <p>Similar to ISO-3166 Two Letter codes, but with some changes for US policy. Only used in
     * the US.
     */
    GENC_TWO_LETTER,
    /**
     * GENC Three Letter codes.
     *
     * <p>Similar to ISO-3166 Three Letter codes, but with some changes for US policy. Only used in
     * the USA.
     */
    GENC_THREE_LETTER,
    /**
     * GENC Numeric codes.
     *
     * <p>Similar to ISO-3166 Numeric codes, but with some changes for US policy. Only used in the
     * US.
     */
    GENC_NUMERIC,
    GENC_MIXED,
    GENC_ADMINSUB;

    @Override
    public String getDisplayName() {
        return "Country Coding Method";
    }

    @Override
    public String getDisplayableValue() {
        return this.toString();
    }
}
