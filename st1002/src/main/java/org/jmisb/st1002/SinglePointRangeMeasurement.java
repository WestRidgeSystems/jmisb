package org.jmisb.st1002;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Single Point Range Measurement (ST 1002 Local Set Item 13).
 *
 * <p>The Single Point Range Measurement (SPRM) is the measure of distance (in metres) from either
 * the principle point, or backplane of a Collaborative Sensor through the image plane to a point in
 * the scene.
 *
 * <p>These measurement concepts are shown in the following images, which are from MISB ST 1002.2.
 * Note that the red line closer to the terrain (green area) represents the focal plane, and that
 * the images are unlikely to be to scale in most scenarios.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/perspectiverangeimage.png" alt="Perspective
 * Range Image Example">
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/depthrangeimage.png" alt="Depth Range Image
 * Example">
 *
 * <p>This value can be either a 32-bit or 64-bit IEEE floating point value.
 *
 * @see SinglePointRangeMeasurementUncertainty
 */
public class SinglePointRangeMeasurement implements IRangeImageMetadataValue {

    private final double value;

    /**
     * Create from value.
     *
     * @param range the range in metres.
     */
    public SinglePointRangeMeasurement(double range) {
        this.value = range;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4 or 8
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public SinglePointRangeMeasurement(byte[] bytes) throws KlvParseException {
        try {
            this.value = PrimitiveConverter.toFloat64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException("Single Point Range Measurement is of length 4 or 8 bytes");
        }
    }

    @Override
    public final String getDisplayName() {
        return "Single Point Range Measurement";
    }

    /**
     * Get the range.
     *
     * @return range in metres.
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
