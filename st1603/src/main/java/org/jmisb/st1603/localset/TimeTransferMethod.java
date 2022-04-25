package org.jmisb.st1603.localset;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvValue;

/**
 * Time Transfer Method part of Time Transfer Parameters.
 *
 * <p>From ST 1603:
 *
 * <blockquote>
 *
 * <p>The Time Transfer Method indicates the system used to transfer time from a reference clock to
 * a receptor clock. Many known time reference systems are represented; others will be added in the
 * future if applicable. A value of zero (0) alerts users the reported time has uncertainty in how
 * it was created.
 *
 * </blockquote>
 *
 * <p>This enumeration maps the allowed values to a human-readable description, and supports
 * building the {@link TimeTransferParameters} value.
 */
public enum TimeTransferMethod implements IKlvValue {
    /**
     * Unknown status.
     *
     * <p>An unknown time transfer method.
     *
     * <p>This is encoded as 0 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Unknown(0, "Unknown Time Transfer Method"),
    /**
     * Global Positioning System (GPS) PPS.
     *
     * <p>Global Positioning System (GPS) Pulse-per-second transfer method.
     *
     * <p>This is encoded as 1 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    GlobalPositioningSystem(1, "Global Positioning System (GPS) PPS"),
    /**
     * Precision Time Protocol (PTP) Version 1.
     *
     * <p>Precision Time Protocol (PTP) Version 1 transfer method, as described in IEEE Standard
     * 1588-2002 "IEEE Standard for a Precision Clock Synchronization Protocol for Networked
     * Measurement and Control Systems", 2002.
     *
     * <p>This is encoded as 2 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    PrecisionTimeProtocolV1(2, "Precision Time Protocol (PTP) Version 1"),
    /**
     * Precision Time Protocol (PTP) Version 2.
     *
     * <p>Precision Time Protocol (PTP) Version 2 transfer method, as described in IEEE Standard
     * 1588-2008 "IEEE Standard for a Precision Clock Synchronization Protocol for Networked
     * Measurement and Control Systems, 2008.
     *
     * <p>This is encoded as 3 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    PrecisionTimeProtocolV2(3, "Precision Time Protocol (PTP) Version 2"),
    /**
     * Network Time Protocol (NTP) Version 3.
     *
     * <p>Network Time Protocol (NTP) Version 3 transfer method, as described in IETF RFC 1305
     * "Network Time Protocol (Version 3) Specification, Implementation and Analysis", 1992
     *
     * <p>This is encoded as 4 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    NetworkTimeProtocolV3(4, "Network Time Protocol (NTP) Version 3"),
    /**
     * Network Time Protocol (NTP) Version 4.
     *
     * <p>Network Time Protocol (NTP) Version 4 transfer method, as described in IETF RFC 5905
     * "Network Time Protocol Version 4: Protocol and Algorithms Specification", 2010
     *
     * <p>This is encoded as 5 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    NetworkTimeProtocolV4(5, "Network Time Protocol (NTP) Version 4"),
    /**
     * Inter-range Instrumentation Group Time Code A (IRIG-A).
     *
     * <p>Inter-range Instrumentation Group (IRIG) Time Code A transfer method, as described in IRIG
     * Standard 200-04 "IRIG SERIAL TIME CODE FORMATS" 2004.
     *
     * <p>This is encoded as 6 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    IRIG_A(6, "Inter-range Instrumentation Group (IRIG-A)"),
    /**
     * Inter-range Instrumentation Group Time Code B (IRIG-B).
     *
     * <p>Inter-range Instrumentation Group (IRIG) Time Code B transfer method, as described in IRIG
     * Standard 200-04 "IRIG SERIAL TIME CODE FORMATS" 2004.
     *
     * <p>This is encoded as 7 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    IRIG_B(7, "Inter-range Instrumentation Group (IRIG-B)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 8 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved8(8, "Reserved for future use (8)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 9 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved9(9, "Reserved for future use (9)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 10 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved10(10, "Reserved for future use (10)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 11 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved11(11, "Reserved for future use (11)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 12 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved12(12, "Reserved for future use (12)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 8 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved13(13, "Reserved for future use (13)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 14 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved14(14, "Reserved for future use (14)"),
    /**
     * Reserved Status.
     *
     * <p>This value is reserved for future use, and is not valid as of ST 1603.2.
     *
     * <p>This is encoded as 15 in the Time Transfer Method part of the Time Transfer Parameters.
     */
    Reserved15(15, "Reserved for future use (15)");

    private final int value;
    private final String meaning;

    private static final Map<Integer, TimeTransferMethod> lookup = new HashMap<>();

    static {
        for (TimeTransferMethod key : values()) {
            lookup.put(key.value, key);
        }
    }

    private TimeTransferMethod(int tag, String meaning) {
        this.value = tag;
        this.meaning = meaning;
    }

    /**
     * Get the integer value associated with this Time Transfer Method value.
     *
     * <p>This is probably only useful for encoding the value.
     *
     * @return value in the range [0,3].
     */
    public int getValue() {
        return value;
    }

    /**
     * Get the human readable meaning of this Time Transfer Method value.
     *
     * @return text representing the meaning of this time transfer method.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Look up the time transfer method by value.
     *
     * @param value the integer value to look up
     * @return corresponding {@code TimeTransferMethod} enumeration value (or {@link Unknown} if it
     *     is not one of the known values)
     */
    public static TimeTransferMethod lookupValue(int value) {
        return lookup.getOrDefault(value, Unknown);
    }

    @Override
    public String getDisplayName() {
        return "Time Transfer Method";
    }

    @Override
    public String getDisplayableValue() {
        return getMeaning();
    }
}
