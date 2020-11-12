package org.jmisb.api.klv.st1206;

/**
 * Shared ST1206 implementation of a resolution in metres.
 *
 * <p>This is encoded as IMAPB(0, 1e6, 4).
 */
public abstract class AbstractResolutionDistance extends AbstractDistance {

    /**
     * Create from value.
     *
     * @param resolution resolution in metres.
     */
    public AbstractResolutionDistance(double resolution) {
        super(resolution);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public AbstractResolutionDistance(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the resolution.
     *
     * @return resolution in metres.
     */
    public final double getResolution() {
        return value;
    }
}
