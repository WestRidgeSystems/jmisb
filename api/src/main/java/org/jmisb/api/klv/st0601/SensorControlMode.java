package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sensor Control Mode (ST 0601 tag 126)
 * <p>
 * From ST:
 * <blockquote>
 * Enumerated value for the current sensor control operational status
 * <p>
 * Map 0..6 to enumeration value
 * </blockquote>
 */
public class SensorControlMode extends UasEnumeration
{
    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Off"},
        {1, "Home Position"},
        {2, "Uncontrolled"},
        {3, "Manual Control"},
        {4, "Calibrating"},
        {5, "Auto - Holding Position"},
        {6, "Auto - Tracking"},
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param status The value of the sensor control operational status enumeration.
     */
    public SensorControlMode(byte status)
    {
        super(status);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public SensorControlMode(byte[] bytes)
    {
        super(bytes);
    }

    /**
     * Get the sensor control operational status
     *
     * @return The value as an enumeration
     */
    public byte getSensorControlMode()
    {
        return value;
    }

    @Override
    public Map<Integer, String> getDisplayValues()
    {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName()
    {
        return "Sensor Control Mode";
    }
}
