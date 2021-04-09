package org.jmisb.api.klv.st1206;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.ElementProcessedEncoder;
import org.jmisb.api.klv.st1303.MDAPDecoder;

/**
 * Radar Cross Section Scale Factor Polynomial (ST 1206 Item 22).
 *
 * <p>To determine the Radar Cross Section (RCS) for a pixel in a SARMI frame, the RCS scale factor
 * is defined as a quantity that relates pixel power for an ideal point scatterer to the RCS in
 * square meters.
 *
 * <p>See ST 1206.1 Section 6.2.19 for more information.
 */
public class RadarCrossSectionScaleFactorPolynomial implements ISARMIMetadataValue {

    private double[][] values;
    /**
     * Create from value.
     *
     * @param polynomial the RCS scale factor polynomial values
     */
    public RadarCrossSectionScaleFactorPolynomial(double[][] polynomial) {
        values = polynomial.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     * @throws KlvParseException if parsing fails
     */
    public RadarCrossSectionScaleFactorPolynomial(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.values = decoder.decodeFloatingPoint2D(bytes, 0);
    }

    @Override
    public byte[] getBytes() {
        try {
            ElementProcessedEncoder encoder = new ElementProcessedEncoder(0, 1e6, 3);
            return encoder.encode(values);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public final String getDisplayableValue() {
        return "[RCS Polynomial]";
    }

    @Override
    public final String getDisplayName() {
        return "Radar Cross Section Scale Factor Polynomial";
    }

    /**
     * Get the RCS polynomial.
     *
     * @return polynomial values in m^2, m, 1, 1/m, etc.
     */
    public double[][] getPolynomialValues() {
        return values.clone();
    }
}
