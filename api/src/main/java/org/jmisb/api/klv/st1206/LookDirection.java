package org.jmisb.api.klv.st1206;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Look Direction (ST 1206 Item 3).
 *
 * <p>The look direction indicates the side of the imaging platform from which the imagery is
 * collected. As such, the sensor look direction with respect to the velocity vector is defined as 0
 * for Left Look Direction and 1 for Right Look Direction.
 *
 * <p>The Look Direction metadata element and the Ground Plane Squint Angle metadata element shall
 * be present at all times.
 */
public class LookDirection extends SARMIEnumeration {
    static final Map<Integer, String> DISPLAY_VALUES =
            Arrays.stream(
                            new Object[][] {
                                {0, "Left"},
                                {1, "Right"}
                            })
                    .collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value.
     *
     * @param status The value of the look direction enumeration.
     */
    public LookDirection(byte status) {
        super(status);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public LookDirection(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the look direction.
     *
     * @return The value as an enumeration value (0 for left, 1 for right).
     */
    public byte getLookDirection() {
        return value;
    }

    @Override
    Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Look Direction";
    }
}
