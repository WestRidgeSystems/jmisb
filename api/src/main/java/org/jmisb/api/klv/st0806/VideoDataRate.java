package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Video Data Rate (ST 0806 Tag 9).
 *
 * <p>Video data rate (Digital only), or Analog FM.
 *
 * <p>Units are bps or Hz.
 */
public class VideoDataRate implements IRvtMetadataValue {
    private final long dataRate;

    private static long MIN_VALUE = 0;
    private static long MAX_VALUE = 4294967295L;
    private static final int REQUIRED_BYTE_LENGTH = 4;

    /**
     * Create from value
     *
     * @param rate Data Rate (bps or Hz).
     */
    public VideoDataRate(long rate) {
        if (rate > MAX_VALUE || rate < MIN_VALUE) {
            throw new IllegalArgumentException(
                    getDisplayName() + " must be in range [0, 4294967295]");
        }
        this.dataRate = rate;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes four bytes representing unsigned integer value.
     */
    public VideoDataRate(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTE_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a four byte unsigned integer");
        }
        dataRate = PrimitiveConverter.toUint32(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(dataRate);
    }

    /**
     * Get the rate.
     *
     * @return The data rate (in bps or Hz).
     */
    public long getRate() {
        return dataRate;
    }

    @Override
    public String getDisplayableValue() {
        return String.format("" + dataRate);
    }

    @Override
    public final String getDisplayName() {
        return "Video Data Rate";
    }
}
