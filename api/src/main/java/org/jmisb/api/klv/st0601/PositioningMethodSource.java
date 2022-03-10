package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Positioning Method Source (ST 0601 Item 124).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Source of the navigation positioning information. (e.g., NAVSAT-GPS, NAVSAT-Galileo, INS).
 *
 * <p>A set of flags specifying the source(s) of positioning information
 *
 * <p>A value with all bits set to zero does not have meaning and is not allowed
 *
 * <p>Map 1..(2^8-1) to 1..255.
 *
 * <p>Resolution: N/A.
 *
 * </blockquote>
 *
 * The Positioning Method Source is an integer interpreted as a set of bit flags. Bit zero is the
 * Least Significant Bit (LSB).
 *
 * <p>Bit 0. INS (On-board Inertial Navigation System)
 *
 * <p>Bit 1. GPS
 *
 * <p>Bit 2. Galileo
 *
 * <p>Bit 3. QZSS
 *
 * <p>Bit 4. NAVIC
 *
 * <p>Bit 5. GLONASS
 *
 * <p>Bit 6. BeiDou-1
 *
 * <p>Bit 7. BeiDou-2
 */
public class PositioningMethodSource implements IUasDatalinkValue {
    private final int source;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 255;

    /** Inertial Navigation System (INS). */
    public static final int INS = 1;
    /** US Global Positioning System (GPS). */
    public static final int GPS = 2;
    /** Galileo. */
    public static final int GALILEO = 4;
    /** QZSS. */
    public static final int QZSS = 8;
    /** NAVIC. */
    public static final int NAVIC = 16;
    /** GLONASS. */
    public static final int GLONASS = 32;
    /** BeiDou Generation 1. */
    public static final int BEIDOU1 = 64;
    /** BeiDou Generation 2. */
    public static final int BEIDOU2 = 128;

    /**
     * Create from value.
     *
     * @param flags The positioning source flags. Legal values are in [0, 255].
     */
    public PositioningMethodSource(int flags) {
        if (flags > MAX_VALUE || flags < MIN_VALUE) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [1,255]");
        }
        this.source = flags;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public PositioningMethodSource(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 1-byte unsigned int");
        }
        this.source = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the positioning source or sources.
     *
     * @return The positioning source flags, as an integer
     */
    public int getPositioningSource() {
        return this.source;
    }

    @Override
    public byte[] getBytes() {
        short intVal = (short) this.source;
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        int numberOfNavsatTypes = 0;
        for (int i = 2; i < 8; i++) {
            int bitflag = 2 << i;
            if ((this.source & bitflag) == bitflag) {
                numberOfNavsatTypes++;
            }
        }
        boolean hasMultiNavsat = numberOfNavsatTypes > 1;
        boolean hasGPS = ((this.source & GPS) == GPS);
        boolean hasOtherNavsat = numberOfNavsatTypes > 0;
        boolean hasINS = ((this.source & INS) == INS);
        StringBuilder sb = new StringBuilder();
        if (hasMultiNavsat) {
            sb.append("Mixed");
        } else if (hasGPS) {
            sb.append("GPS");
        } else if (hasOtherNavsat) {
            sb.append("Satellite");
        }
        if (hasINS) {
            if (sb.length() > 0) {
                sb.append("/");
            }
            sb.append("INS");
        }
        String displayValue = sb.toString();
        return String.format("%s [%d]", displayValue, this.source);
    }

    @Override
    public final String getDisplayName() {
        return "Positioning Method Source";
    }
}
