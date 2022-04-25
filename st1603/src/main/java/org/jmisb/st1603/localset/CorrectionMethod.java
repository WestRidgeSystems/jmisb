package org.jmisb.st1603.localset;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvValue;

/**
 * Correction Method part of Time Transfer Parameters.
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The Correction Method indicates Jam Correction, Slew Correction or Unknown (see Section 8 for
 * details on Correction Method application). Table 3 lists the valid values for the Correction
 * Method. Unknown indicates either no correction, or a method other than Jam/Slew Correction is
 * applied.
 *
 * </blockquote>
 *
 * <p>This enumeration maps the allowed values to a human-readable description, and supports
 * building the {@link TimeTransferParameters} value.
 */
public enum CorrectionMethod implements IKlvValue {
    /**
     * Unknown status.
     *
     * <p>The correction method is unknown, or is a method other than Jam Correction or Slew
     * Correction is being applied, or no correction is being applied.
     *
     * <p>This is encoded as 0 in the Correction Method part of the Time Transfer Parameters.
     */
    Unknown(0, "Unknown or No Correction"),
    /**
     * Jam Correction.
     *
     * <p>Jam correction is being applied.
     *
     * <p>Jam Correction forces an abrupt change to a receptor’s clock report time to the latest
     * reported time of the reference clock. If the receptor clock is free-wheeling faster than the
     * reference clock, the reported time forces a negative jump (i.e. backward) in time creating a
     * negative timing discontinuity. Such discontinuities change the system to Level 0 and
     * complicates downstream processing.
     *
     * <p>This is encoded as 1 in the Correction Method part of the Time Transfer Parameters.
     */
    JamCorrection(1, "Jam Correction"),
    /**
     * Unknown status.
     *
     * <p>Slew correction is being applied.
     *
     * <p>Slew Correction slows down the receptor clock allowing the reference clock to “catch up”.
     * During this period of slewing, the difference between the reported time (receptor clock) and
     * the reference time (reference clock) is included as metadata; this metadata enables an
     * application to correct time instantaneously, if needed. Proper initialization in Slew
     * Correction ensures monotonicity. When used, Slew Correction maintains a minimum of a Level 1
     * Timing System, insuring Total Ordering.
     *
     * <p>This is encoded as 2 in the Correction Method part of the Time Transfer Parameters.
     */
    SlewCorrection(2, "Slew Correction"),
    /**
     * Unknown status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 3 in the Correction Method part of the Time Transfer Parameters.
     */
    Reserved(3, "Reserved for future use");

    private final int value;
    private final String meaning;

    private static final Map<Integer, CorrectionMethod> lookup = new HashMap<>();

    static {
        for (CorrectionMethod key : values()) {
            lookup.put(key.value, key);
        }
    }

    private CorrectionMethod(int tag, String meaning) {
        this.value = tag;
        this.meaning = meaning;
    }

    /**
     * Get the integer value associated with this Correction Method value.
     *
     * <p>This is probably only useful for encoding the value. Note that a bit shift is required to
     * place it into the {@link TimeTransferParameters} encoded value.
     *
     * @return value in the range [0,3].
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the human readable meaning of this Correction Method value.
     *
     * @return text representing the meaning of this correction method status.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Look up the Correction Method by value.
     *
     * @param value the integer value to look up
     * @return corresponding {@code CorrectionMethod} enumeration value (or {@link Unknown} if it is
     *     not one of the known values)
     */
    public static CorrectionMethod lookupValue(int value) {
        return lookup.getOrDefault(value, Unknown);
    }

    @Override
    public String getDisplayName() {
        return "Correction Method";
    }

    @Override
    public String getDisplayableValue() {
        return getMeaning();
    }
}
