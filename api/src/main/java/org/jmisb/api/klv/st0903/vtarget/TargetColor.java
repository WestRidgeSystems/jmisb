package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;

/**
 * Target Color (ST0903 VTarget Pack Tag 8).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Dominant color of the target expressed using RGB color values. General mapping of any
 * multispectral dataset to an RGB value. Primary use when transmitting metadata in the absence of
 * the underlying Motion Imagery. VFeature LS provides more comprehensive color information.
 * Represents the RGB color value with: First byte = Red, Second byte = Green, Third byte = Blue.
 *
 * </blockquote>
 */
public class TargetColor implements IVmtiMetadataValue {

    private short red;
    private short green;
    private short blue;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 255;

    /**
     * Create from values.
     *
     * @param red the red level (0 lowest, 255 highest)
     * @param green the green level (0 lowest, 255 highest)
     * @param blue the blue level (0 lowest, 255 highest)
     */
    public TargetColor(short red, short green, short blue) {
        if (red < MIN_VALUE || red > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " red value must be in range [0,255]");
        }
        if (green < MIN_VALUE || green > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " green value must be in range [0,255]");
        }
        if (blue < MIN_VALUE || blue > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " blue value must be in range [0,255]");
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetColor(byte[] bytes) {
        if (bytes.length != 3) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is three byte array [R,G,B]");
        }
        this.red = (short) (bytes[0] & 0xFF);
        this.green = (short) (bytes[1] & 0xFF);
        this.blue = (short) (bytes[2] & 0xFF);
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[] {(byte) red, (byte) green, (byte) blue};
        return bytes;
    }

    @Override
    public String getDisplayableValue() {
        return "[" + red + ", " + green + ", " + blue + "]";
    }

    @Override
    public final String getDisplayName() {
        return "Target Color";
    }

    /**
     * Get the red value.
     *
     * @return the red value (0 lowest, 255 highest)
     */
    public short getRed() {
        return this.red;
    }

    /**
     * Get the green value.
     *
     * @return the green value (0 lowest, 255 highest)
     */
    public short getGreen() {
        return this.green;
    }

    /**
     * Get the blue value.
     *
     * @return the blue value (0 lowest, 255 highest)
     */
    public short getBlue() {
        return this.blue;
    }
}
