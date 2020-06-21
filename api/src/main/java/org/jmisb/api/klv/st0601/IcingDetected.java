package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Icing Detected (ST 0601 tag 34)
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Flag for icing detected at aircraft location. This metadata item signals when the icing sensor
 * detects water forming on its vibrating probe.
 *
 * <p>Map 0..2 to enumeration value
 *
 * </blockquote>
 */
public class IcingDetected extends UasEnumeration {
    static final Map<Integer, String> DISPLAY_VALUES =
            Arrays.stream(
                            new Object[][] {
                                {0, "Detector off"},
                                {1, "No icing detected"},
                                {2, "Icing detected"}
                            })
                    .collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param icingDetected The value of the icing enumeration (0: Detector off, 1: No icing
     *     Detected, 2: Icing Detected)
     */
    public IcingDetected(byte icingDetected) {
        super(icingDetected);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public IcingDetected(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the icing detected value
     *
     * @return The value as an enumeration (0: Detector off, 1: No icing Detected, 2: Icing
     *     Detected)
     */
    public byte getIcingDetected() {
        return value;
    }

    @Override
    public Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Icing Detected";
    }
}
