package org.jmisb.st1603.localset;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvValue;

/**
 * Reference Source part of Time Transfer Parameters.
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The Reference Source indicates if the reference clock is synchronized to an atomic source
 * (i.e. atomic clock), or not. For example, in normal operations, a GPS synchronizes to satellites,
 * and effectively, an atomic clock. However, if its antenna were broken, the GPS could no longer
 * synchronize to the atomic clock. When the reference clock is synchronized to an atomic source
 * (e.g. TAI or GPS), the Reference Source value is set to two (2); otherwise, it is set to one (1).
 * When the state of the clock is unknown, a value of zero (0) is assigned.
 *
 * </blockquote>
 *
 * <p>This enumeration maps the allowed values to a human-readable description, and supports
 * building the {@link TimeTransferParameters} value.
 */
public enum ReferenceSource implements IKlvValue {
    /**
     * Unknown status.
     *
     * <p>Status of the reference clock lock is unknown.
     *
     * <p>This is encoded as 0 in the Reference Source part of the Time Transfer Parameters.
     */
    Unknown(0, "Reference Source status is unknown"),
    /**
     * Not Synchronized.
     *
     * <p>Reference Source is not locked to an atomic clock (or equivalent, such as GPS).
     *
     * <p>This is encoded as 1 in the Reference Source part of the Time Transfer Parameters.
     */
    NotSynchronized(1, "Reference Source is not synchronized to an atomic source"),
    /**
     * Synchronized.
     *
     * <p>Reference Source is locked to an atomic clock (or equivalent, such as GPS).
     *
     * <p>This is encoded as 2 in the Reference Source part of the Time Transfer Parameters.
     */
    Synchronized(2, "Reference Source is synchronized to an atomic source"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 3 in the Reference Source part of the Time Transfer Parameters.
     */
    Reserved(3, "Reserved for future use");

    private final int value;
    private final String meaning;

    private static final Map<Integer, ReferenceSource> lookup = new HashMap<>();

    static {
        for (ReferenceSource key : values()) {
            lookup.put(key.value, key);
        }
    }

    private ReferenceSource(int tag, String meaning) {
        this.value = tag;
        this.meaning = meaning;
    }

    /**
     * Get the integer value associated with this Reference Source value.
     *
     * <p>This is probably only useful for encoding the value.
     *
     * @return value in the range [0,3].
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the human readable meaning of this Reference Source value.
     *
     * @return text representing the meaning of this reference source status.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Look up the reference source by value.
     *
     * @param value the integer value to look up
     * @return corresponding {@code ReferenceSource} enumeration value (or {@link Unknown} if it is
     *     not one of the known values)
     */
    public static ReferenceSource lookupValue(int value) {
        return lookup.getOrDefault(value, Unknown);
    }

    @Override
    public String getDisplayName() {
        return "Reference Source";
    }

    @Override
    public String getDisplayableValue() {
        return getMeaning();
    }
}
