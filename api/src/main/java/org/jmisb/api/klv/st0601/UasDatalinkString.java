package org.jmisb.api.klv.st0601;

import java.nio.charset.StandardCharsets;

/** Represents a string value in ST 0601 */
public class UasDatalinkString implements IUasDatalinkValue {
    public static final String ALTERNATE_PLATFORM_NAME = "Alternate Platform Name";
    public static final String BROADCAST_SOURCE = "Broadcast Source";
    public static final String COMMUNICATIONS_METHOD = "Communications Method";
    public static final String IMAGE_COORDINATE_SYSTEM = "Image Coordinate System";
    public static final String IMAGE_SOURCE_SENSOR = "Image Source Sensor";
    public static final String MISSION_ID = "Mission ID";
    public static final String OPERATIONAL_BASE = "Operational Base";
    public static final String PLATFORM_CALL_SIGN = "Platform Call Sign";
    public static final String PLATFORM_DESIGNATION = "Platform Designation";
    public static final String PLATFORM_TAIL_NUMBER = "Platform Tail Number";
    public static final String STREAM_DESIGNATOR = "Stream Designator";
    public static final String TARGET_ID = "Target ID";

    private final String displayName;
    private String stringValue;

    /**
     * Create from value
     *
     * @param name The display name for the datalink string
     * @param value The string value
     */
    public UasDatalinkString(String name, String value) {
        this.displayName = name;
        this.stringValue = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param name The display name for the datalink string
     * @param bytes Encoded byte array
     */
    public UasDatalinkString(String name, byte[] bytes) {
        this.displayName = name;
        this.stringValue = new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Get the value
     *
     * @return The string value
     */
    public String getValue() {
        return stringValue;
    }

    @Override
    public byte[] getBytes() {
        return stringValue.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getDisplayableValue() {
        return stringValue;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
