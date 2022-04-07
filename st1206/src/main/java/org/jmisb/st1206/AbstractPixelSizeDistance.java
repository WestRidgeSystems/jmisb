package org.jmisb.st1206;

/**
 * Shared ST1206 implementation of a pixel size in meters.
 *
 * <p>This is encoded as IMAPB(0, 1e6, 4).
 */
public abstract class AbstractPixelSizeDistance extends AbstractDistance {

    /**
     * Create from value.
     *
     * @param pixelSize pixel size in meters.
     */
    public AbstractPixelSizeDistance(double pixelSize) {
        super(pixelSize);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public AbstractPixelSizeDistance(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the pixel size.
     *
     * @return pixel size in meters.
     */
    public final double getPixelSize() {
        return value;
    }
}
