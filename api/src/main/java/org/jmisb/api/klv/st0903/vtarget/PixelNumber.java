package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/** Shared implementation of variable length pixel number. */
public abstract class PixelNumber implements IVmtiMetadataValue, IVTrackMetadataValue {

    private long pixelNumber;
    private static long MIN_VALUE = 1;
    private static long MAX_VALUE = 281_474_976_710_655L;

    /**
     * Create from value.
     *
     * @param num the pixel number
     */
    public PixelNumber(long num) {
        if (num < MIN_VALUE || num > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [1,281474976710655]");
        }

        pixelNumber = num;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public PixelNumber(byte[] bytes) {
        if (bytes.length > 6) {
            throw new IllegalArgumentException("Pixel number encoding is up to 6 bytes");
        }
        pixelNumber = 0;
        for (int i = 0; i < bytes.length; ++i) {
            pixelNumber = pixelNumber << 8;
            pixelNumber += ((int) bytes[i] & 0xFF);
        }
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytesV6(pixelNumber);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", pixelNumber);
    }

    /**
     * The pixel number value.
     *
     * @return the pixel number as a long value.
     */
    public long getPixelNumber() {
        return pixelNumber;
    }
}
