package org.jmisb.st1002;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Single Point Range Measurement Uncertainty (ST 1002 Local Set Item 14).
 *
 * <p>The Single Point Range Measurement Uncertainty is the uncertainty (sigma,σ) of the Single
 * Point Range Measurement data, in metres, along the measured vector from either the perspective
 * centre or depth range measurement backplane to the scene. This value can be either a 32-bit or
 * 64-bit IEEE floating point value.
 *
 * @see SinglePointRangeMeasurement
 */
public class SinglePointRangeMeasurementUncertainty implements IRangeImageMetadataValue {

    private final double value;

    /**
     * Create from value.
     *
     * @param uncertainty the range uncertainty in metres.
     */
    public SinglePointRangeMeasurementUncertainty(double uncertainty) {
        this.value = uncertainty;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4 or 8
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public SinglePointRangeMeasurementUncertainty(byte[] bytes) throws KlvParseException {
        try {
            this.value = PrimitiveConverter.toFloat64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Single Point Range Measurement Uncertainty is of length 4 or 8 bytes");
        }
    }

    @Override
    public final String getDisplayName() {
        return "Single Point Range Measurement Uncertainty";
    }

    /**
     * Get the uncertainty.
     *
     * @return range uncertainty (one standard deviation) in metres.
     */
    public double getRange() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float64ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.3f m", this.value);
    }
}
