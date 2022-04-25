package org.jmisb.st0809;

import org.jmisb.api.common.KlvParseException;

/**
 * Solar Illumination Diffuse (ST 0809 Local Set Item 26).
 *
 * <p>In Lux (lx).
 */
public class SolarIlluminationDiffuse extends AbstractIllumination {

    /**
     * Create from value.
     *
     * @param illumination illumination in lux
     */
    public SolarIlluminationDiffuse(float illumination) {
        super(illumination);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public SolarIlluminationDiffuse(byte[] bytes) throws KlvParseException {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Solar Illumination Diffuse";
    }
}
