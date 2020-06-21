package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Telemetry Accuracy Indicator (ST 0806 RVT LS Tag 5).
 *
 * <p>From ST0806:
 *
 * <blockquote>
 *
 * Reserved for future use.
 *
 * </blockquote>
 */
public class TelemetryAccuracyIndicator implements IRvtMetadataValue {
    private int value;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;
    private static final int REQUIRED_BYTES = 1;

    /**
     * Create from value
     *
     * @param value the value
     */
    public TelemetryAccuracyIndicator(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,255]");
        }
        this.value = value;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes Byte array of length 1
     */
    public TelemetryAccuracyIndicator(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTES) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is one byte unsigned integer");
        }
        value = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the value
     *
     * @return The value
     */
    public int getValue() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes((short) value);
    }

    @Override
    public String getDisplayableValue() {
        return "" + value;
    }

    @Override
    public final String getDisplayName() {
        return "Telemetry Accuracy Indicator";
    }
}
