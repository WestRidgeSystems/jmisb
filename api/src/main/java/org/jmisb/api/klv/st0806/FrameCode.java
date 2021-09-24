package org.jmisb.api.klv.st0806;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Frame Code (ST 0806 Tag 7).
 *
 * <p>Frame counter index. Counter runs at 60Hz.
 */
public class FrameCode implements IRvtMetadataValue {
    private final long frameCode;
    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = 4294967295L;
    private static final int REQUIRED_BYTE_LENGTH = 4;

    /**
     * Create from value.
     *
     * @param frameCode frameCode.
     */
    public FrameCode(long frameCode) {
        if (frameCode > MAX_VALUE || frameCode < MIN_VALUE) {
            throw new IllegalArgumentException(
                    getDisplayName() + " must be in range [0, 4294967295]");
        }
        this.frameCode = frameCode;
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes four bytes representing unsigned integer value.
     */
    public FrameCode(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTE_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a four byte unsigned integer");
        }
        frameCode = PrimitiveConverter.toUint32(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(frameCode);
    }

    /**
     * Get the frameCode.
     *
     * @return The frame code number
     */
    public long getFrameCode() {
        return frameCode;
    }

    @Override
    public String getDisplayableValue() {
        return "" + frameCode;
    }

    @Override
    public final String getDisplayName() {
        return "Frame Code";
    }
}
