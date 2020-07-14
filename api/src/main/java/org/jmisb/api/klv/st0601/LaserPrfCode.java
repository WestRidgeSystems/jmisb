package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Laser PRF Code (ST0601 Item 62).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * A laser's Pulse Repetition Frequency (PRF) code used to mark a target
 *
 * <p>The Laser PRF code is a three or four digit number consisting of the values 1..8
 *
 * <p>Only the values 111..8888 can be used without 0's or 9's
 *
 * <p>When enabled, laser designators can generate a pulsed signal according to a Pulse Repetition
 * Frequency (PRF) code which distinguishes one laser beam from another.
 *
 * </blockquote>
 *
 * <p>Direct mapping: 0...65535 to 0..65535 (noting limits above).
 *
 * <p>Resolution: N.A. Exact - integer code.
 */
public class LaserPrfCode implements IUasDatalinkValue {
    private int code;
    private static double MIN_VALUE = 111;
    private static double MAX_VALUE = 8888;

    /**
     * Create from value
     *
     * @param prfCode The PRF code. Legal values are in [111, 8888]. No use of 0's or 9s in the
     *     code.
     */
    public LaserPrfCode(int prfCode) {
        if (prfCode > MAX_VALUE || prfCode < MIN_VALUE) {
            throw new IllegalArgumentException(
                    getDisplayName() + " must be in the range [111, 8888]");
        }
        String prfCodeAsString = "" + prfCode;
        if (prfCodeAsString.contains("0") || prfCodeAsString.contains("9")) {
            throw new IllegalArgumentException(
                    getDisplayName() + " must only contain 1 through 8, not 0 or 9");
        }
        this.code = prfCode;
    }

    /**
     * Create from encoded bytes
     *
     * @param bytes The byte array of length 2
     */
    public LaserPrfCode(byte[] bytes) {
        if (bytes.length != 2) {
            throw new IllegalArgumentException(
                    getDisplayName() + " encoding is a 2-byte unsigned int");
        }

        code = PrimitiveConverter.toUint16(bytes);
    }

    /**
     * Get the code
     *
     * @return The PRF code
     */
    public int getCode() {
        return code;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint16ToBytes(code);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", code);
    }

    @Override
    public final String getDisplayName() {
        return "Laser PRF Code";
    }
}
