package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Relative Humidity (ST 0809 Local Set Item 19).
 *
 * <p>The ratio of the partial pressure of water vapor in the atmosphere to the partial pressure of
 * water vapor for a saturated atmosphere at a given temperature.
 */
public class RelativeHumidity extends AbstractPercentage {

    /**
     * Create from value.
     *
     * @param percentage relative humidity as a percentage
     */
    public RelativeHumidity(float percentage) {
        super(percentage);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public RelativeHumidity(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Relative Humidity";
    }
}
