package org.jmisb.st1603.localset;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/**
 * ST 1603 tags - description and numbers.
 *
 * <p>This enumeration maps the Time Transfer Local Set tag values to a name, and names to tag
 * values. It conceptually corresponds to the first two columns in ST 1603.2 Table 1.
 */
public enum TimeTransferKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It does not correspond to any known ST 1603 tag / key.
     */
    Undefined(0),
    /**
     * Document Version.
     *
     * <p>ST 1603 Local Set Tag 1.
     */
    DocumentVersion(1),
    /**
     * UTC Leap Second Offset
     *
     * <p>ST 1603 Local Set Tag 2.
     */
    UTCLeapSecondOffset(2),
    /**
     * Time Transfer Parameters.
     *
     * <p>ST 1603 Local Set Tag 3.
     */
    TimeTransferParameters(3),
    /**
     * Synchronization Pulse Frequency.
     *
     * <p>ST 1603 Local Set Tag 4.
     */
    SynchronizationPulseFrequency(4),
    /**
     * Unlock Time.
     *
     * <p>ST 1603 Local Set Tag 5.
     */
    UnlockTime(5),
    /**
     * Last Synchronization Difference.
     *
     * <p>ST 1603 Local Set Tag 6.
     */
    LastSynchronizationDifference(6),
    /**
     * Drift Rate.
     *
     * <p>ST 1603 Local Set Tag 7.
     */
    DriftRate(7),
    /**
     * Signal Source Delay.
     *
     * <p>ST 1603 Local Set Tag 8.
     */
    SignalSourceDelay(8),
    /**
     * Receptor Clock Uncertainty.
     *
     * <p>ST 1603 Local Set Tag 9.
     */
    ReceptorClockUncertainty(9);

    private final int tag;

    private static final Map<Integer, TimeTransferKey> tagTable = new HashMap<>();

    static {
        for (TimeTransferKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private TimeTransferKey(int tag) {
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
    public static TimeTransferKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}
