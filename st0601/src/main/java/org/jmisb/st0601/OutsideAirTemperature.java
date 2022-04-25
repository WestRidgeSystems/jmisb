package org.jmisb.st0601;

/**
 * Outside Air Temperature (ST 0601 Item 39).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Temperature outside of aircraft.
 *
 * <p>The measured temperature outside of the platform.
 *
 * <p>Map -128..127 to -128..127 degrees Celcius.
 *
 * <p>Resolution: 1 degree Celcius
 *
 * </blockquote>
 */
public class OutsideAirTemperature implements IUasDatalinkValue {
    private final byte temperature;
    private static final double MIN_VAL = -128;
    private static final double MAX_VAL = 127;

    /**
     * Create from value.
     *
     * @param temp temperature in degrees Celcius
     */
    public OutsideAirTemperature(short temp) {
        if (temp < MIN_VAL || temp > MAX_VAL) {
            throw new IllegalArgumentException("Temperature must be in range [-128,127]");
        }
        this.temperature = (byte) temp;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 1
     */
    public OutsideAirTemperature(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Temperature encoding is a 1-byte signed int");
        }

        this.temperature = bytes[0];
    }

    /**
     * Get the value as temperature.
     *
     * @return Temperature in degrees Celcius
     */
    public short getTemperature() {
        return this.temperature;
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[1];
        bytes[0] = this.temperature;
        return bytes;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d\u00B0C", this.temperature);
    }

    @Override
    public String getDisplayName() {
        return "Outside Air Temperature";
    }
}
