package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OperationalMode (ST 0601 Item 77).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Indicates the mode of operations of the event portrayed in Motion Imagery.
 *
 * <p>Map 0..5 to enumeration value
 *
 * </blockquote>
 */
public class OperationalMode extends UasEnumeration {
    static final Map<Integer, String> DISPLAY_VALUES =
            Arrays.stream(
                            new Object[][] {
                                {0, "Other"},
                                {1, "Operational"},
                                {2, "Training"},
                                {3, "Exercise"},
                                {4, "Maintenance"},
                                {5, "Test"}
                            })
                    .collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param mode The value of the operational mode enumeration.
     */
    public OperationalMode(byte mode) {
        super(mode);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public OperationalMode(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the operational mode
     *
     * @return The value as an enumeration
     */
    public byte getOperationalMode() {
        return value;
    }

    @Override
    public Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Operational Mode";
    }
}
