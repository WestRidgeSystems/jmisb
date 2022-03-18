package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Cloud Cover Percentage (ST 0809 Local Set Item 9).
 *
 * <p>The portion of the celestial dome which is obscured by cloud; described by the terms clear
 * (0/10), scattered (1/10 to 5/10), broken (6/10 to 9/10), and overcast (10/10).
 */
public class CloudCoverPercentage extends AbstractPercentage {

    /**
     * Create from value.
     *
     * @param percentage percentage of cloud cover
     */
    public CloudCoverPercentage(float percentage) {
        super(percentage);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public CloudCoverPercentage(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Cloud Cover";
    }
}
