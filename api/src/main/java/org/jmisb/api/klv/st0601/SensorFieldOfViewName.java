package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sensor Field of View Name (ST 0601 tag 63)
 * <p>
 * From ST:
 * <blockquote>
 * The Sensor Field of View Name indicates the Motion Imagery sensor’s current lens type.
 * <p>
 * Map 0..7 to enumeration value
 * </blockquote>
 */
public class SensorFieldOfViewName extends UasEnumeration
{
    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Ultranarrow"},
        {1, "Narrow"},
        {2, "Medium"},
        {3, "Wide"},
        {4, "Ultrawide"},
        {5, "Narrow Medium"},
        {6, "2x Ultranarrow"},
        {7, "4x Ultranarrow"}
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param mode The value of the sensor field of view name enumeration.
     */
    public SensorFieldOfViewName(byte mode)
    {
        super(mode);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public SensorFieldOfViewName(byte[] bytes)
    {
        super(bytes);
    }

    /**
     * Get the sensor field of view mode value
     *
     * @return The value as an enumeration
     */
    public byte getSensorFieldOfViewName()
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
        return "Sensor Field of View Name";
    }
}
