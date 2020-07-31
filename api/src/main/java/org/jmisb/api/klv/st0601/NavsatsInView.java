package org.jmisb.api.klv.st0601;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Number of NAVSATs in View (ST 0601 Item 123).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Count of navigation satellites in view of platform.
 *
 * <p>Number of satellites used to determine position.
 *
 * <p>Used with Positioning Method Source (Item 124) for NAVSAT Types
 *
 * <p>Map 0..(2^8-1) to 0..255.
 *
 * <p>Resolution: 1.
 *
 * </blockquote>
 */
public class NavsatsInView implements IUasDatalinkValue {
    private int navsatsInView;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * @param navsats The count of navigation satellites. Legal values are in [0, 255].
     */
    public NavsatsInView(int navsats) {
        if (navsats > MAX_VALUE || navsats < MIN_VALUE) {
            throw new IllegalArgumentException(this.getDisplayName() + " must be in range [0,255]");
        }
        navsatsInView = navsats;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public NavsatsInView(byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a 1-byte unsigned int");
        }
        this.navsatsInView = PrimitiveConverter.toUint8(bytes);
    }

    /**
     * Get the number of satellites in view.
     *
     * @return The number of satellites (as an integer count).
     */
    public int getNavsatsInView() {
        return this.navsatsInView;
    }

    @Override
    public byte[] getBytes() {
        short intVal = (short) this.navsatsInView;
        return PrimitiveConverter.uint8ToBytes(intVal);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", this.navsatsInView);
    }

    @Override
    public String getDisplayName() {
        return "NAVSATs In View";
    }
}
