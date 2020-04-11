package org.jmisb.api.klv.st0601;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Platform Status (ST 0601 tag 125)
 * <p>
 * From ST:
 * <blockquote>
 * Enumeration of operational modes of the platform (e.g., in-route, RTB)
 * <p>
 * Map 0..12 to enumeration value
 * </blockquote>
 */
public class PlatformStatus extends UasEnumeration
{
    static final Map<Integer, String> DISPLAY_VALUES = Arrays.stream(new Object[][]{
        {0, "Active"},
        {1, "Pre-flight"},
        {2, "Pre-flight-taxiing"},
        {3, "Run-up"},
        {4, "Take-off"},
        {5, "Ingress"},
        {6, "Manual operation"},
        {7, "Automated-orbit"},
        {8, "Transitioning"},
        {9, "Egress"},
        {10, "Landing"},
        {11, "Landed-taxiing"},
        {12, "Landed-Parked"}
    }).collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value
     *
     * @param status The value of the platform status enumeration.
     */
    public PlatformStatus(byte status)
    {
        super(status);
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 1
     */
    public PlatformStatus(byte[] bytes)
    {
        super(bytes);
    }

    /**
     * Get the platform status
     *
     * @return The value as an enumeration
     */
    public byte getPlatformStatus()
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
        return "Platform Status";
    }
}
