package org.jmisb.api.klv.st0809;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Solar Illumination Direct (ST 0809 Local Set Item 27).
 *
 * <p>In Lux (lx).
 */
public class SolarIlluminationDirect extends AbstractIllumination {

    /**
     * Create from value.
     *
     * @param illumination illumination in lux
     */
    public SolarIlluminationDirect(float illumination) {
        this.value = illumination;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public SolarIlluminationDirect(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException(this.getDisplayName() + " encoding is a 4-byte float");
        }
        this.value = PrimitiveConverter.toFloat32(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Solar Illumination Direct";
    }
}
