package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Fog Cover Percentage (ST 0809 Local Set Item 31).
 *
 * <p>Percentage of scene covered with fog.
 */
public class FogCoverPercentage extends AbstractPercentage {

    /**
     * Create from value.
     *
     * @param percentage fog coverage as a percentage
     */
    public FogCoverPercentage(float percentage) {
        super(percentage);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public FogCoverPercentage(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Fog Coverage";
    }
}
