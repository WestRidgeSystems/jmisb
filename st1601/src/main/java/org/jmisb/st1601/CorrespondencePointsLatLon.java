package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.ElementProcessedEncoder;
import org.jmisb.api.klv.st1303.MDAPDecoder;

/**
 * Correspondence Points – Latitude/Longitude (ST 1601 Tag 5).
 *
 * <p>A geo-registration algorithm uses the Correspondence Points – Latitude/Longitude MDARRAY item
 * to define an extensible list of tie points represented in geographic space for two Images.
 *
 * <p>For N correspondence geographic points, this is two rows by N columns.The rows are latitude,
 * then longitude,
 *
 * <p>See ST 1601.1 Section 6.3.5 for more information.
 */
public class CorrespondencePointsLatLon implements IGeoRegistrationValue {

    private final double[][] values;
    /**
     * Create from value.
     *
     * @param tiePoints the array tie points.
     */
    public CorrespondencePointsLatLon(double[][] tiePoints) {
        values = tiePoints.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     * @throws KlvParseException if parsing fails
     */
    public CorrespondencePointsLatLon(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.values = decoder.decodeFloatingPoint2D(bytes, 0);
    }

    @Override
    public byte[] getBytes() {
        try {
            ElementProcessedEncoder encoder = new ElementProcessedEncoder(-180.0, 180.0, 4);
            return encoder.encode(values);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public final String getDisplayableValue() {
        return "[Correspondence Points – Latitude/Longitude]";
    }

    @Override
    public final String getDisplayName() {
        return "Correspondence Points – Latitude/Longitude";
    }

    /**
     * Get the Correspondence Points.
     *
     * @return correspondence points
     */
    public double[][] getCorrespondencePoints() {
        return values.clone();
    }
}
