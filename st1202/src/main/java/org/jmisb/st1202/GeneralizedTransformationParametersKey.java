package org.jmisb.st1202;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1202 tags - description and numbers.
 *
 * <p>This enumeration maps the Generalized Transformation Local Set tag values to a name, and names
 * to tag values. It conceptually corresponds to the Tag ID and Name columns in ST 1202.2 Table 2.
 */
public enum GeneralizedTransformationParametersKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It does not correspond to any known ST 1202 tag / key.
     */
    Undefined(0),

    /**
     * x Equation Numerator - x factor.
     *
     * <p>ST 1202 Local Set Tag 1.
     *
     * <p>This is {code A} in ST 1202.2 Equation 1.
     */
    X_Numerator_x(1),

    /**
     * x Equation Numerator - y factor.
     *
     * <p>ST 1202 Local Set Tag 2.
     *
     * <p>This is {code B} in ST 1202.2 Equation 1.
     */
    X_Numerator_y(2),

    /**
     * x Equation Numerator - constant factor.
     *
     * <p>ST 1202 Local Set Tag 3.
     *
     * <p>This is {code C} in ST 1202.2 Equation 1.
     */
    X_Numerator_Constant(3),

    /**
     * y Equation Numerator - x factor.
     *
     * <p>ST 1202 Local Set Tag 4.
     *
     * <p>This is {code D} in ST 1202.2 Equation 2.
     */
    Y_Numerator_x(4),

    /**
     * y Equation Numerator - y factor.
     *
     * <p>ST 1202 Local Set Tag 5.
     *
     * <p>This is {code E} in ST 1202.2 Equation 2.
     */
    Y_Numerator_y(5),

    /**
     * y Equation Numerator - constant factor.
     *
     * <p>ST 1202 Local Set Tag 6.
     *
     * <p>This is {code F} in ST 1202.2 Equation 2.
     */
    Y_Numerator_Constant(6),

    /**
     * Denominator - x factor.
     *
     * <p>ST 1202 Local Set Tag 7.
     *
     * <p>This is {code G} in ST 1202.2 Equations 1 and 2.
     */
    Denominator_x(7),

    /**
     * Denominator - y factor.
     *
     * <p>ST 1202 Local Set Tag 8.
     *
     * <p>This is {code H} in ST 1202.2 Equations 1 and 2.
     */
    Denominator_y(8),

    /**
     * Standard Deviation and Correlation Coefficients (SDCC).
     *
     * <p>ST 1202 Local Set Tag 9.
     */
    SDCC(9),

    /**
     * Document Version.
     *
     * <p>ST 1202 Local Set Tag 10.
     */
    DocumentVersion(10),

    /**
     * Transformation Enumeration.
     *
     * <p>ST 1202 Local Set Tag 11.
     */
    TransformationEnumeration(11);

    private final int tag;

    private static final Map<Integer, GeneralizedTransformationParametersKey> tagTable =
            new HashMap<>();

    static {
        for (GeneralizedTransformationParametersKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private GeneralizedTransformationParametersKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the local set identifier
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding local set key
     */
    public static GeneralizedTransformationParametersKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
