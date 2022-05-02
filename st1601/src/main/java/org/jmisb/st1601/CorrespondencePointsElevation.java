package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.ElementProcessedEncoder;
import org.jmisb.api.klv.st1303.MDAPDecoder;

/**
 * Correspondence Points – Elevation (ST 1601 Tag 8).
 *
 * <p>A geo-registration algorithm uses the Correspondence Points – Elevation MDARRAY item to define
 * an extensible list of elevation values for geographic space tie points for two Images.
 *
 * <p>For N correspondence geographic points, this is a one dimensional, N element array (or
 * equivalently, one row by N columns).
 *
 * <p>See ST 1601.1 Section 6.3.8 for more information.
 */
public class CorrespondencePointsElevation implements IGeoRegistrationValue {

    private final double[] values;
    /**
     * Create from value.
     *
     * @param elevations the array of elevation values for the tie points.
     */
    public CorrespondencePointsElevation(double[] elevations) {
        values = elevations.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     * @throws KlvParseException if parsing fails
     */
    public CorrespondencePointsElevation(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.values = decoder.decodeFloatingPoint1D(bytes, 0);
    }

    @Override
    public byte[] getBytes() {
        try {
            // From ST 0807.25, the valid range is -900.0 to 40000 metres, default size 2 bytes.
            // However that isn't much resolution
            ElementProcessedEncoder encoder = new ElementProcessedEncoder(-900.0, 40000.0, 3);
            return encoder.encode(values);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public final String getDisplayableValue() {
        return "[Correspondence Points – Elevation]";
    }

    @Override
    public final String getDisplayName() {
        return "Correspondence Points – Elevation";
    }

    /**
     * Get the Correspondence Point Elevations.
     *
     * @return correspondence point elevations
     */
    public double[] getElevations() {
        return values.clone();
    }
}
