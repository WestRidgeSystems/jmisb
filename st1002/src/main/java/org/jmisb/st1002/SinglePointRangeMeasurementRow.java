package org.jmisb.st1002;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Single Point Range Measurement Row (ST 1002 Local Set Item 15).
 *
 * <p>The Single Point Range Measurement is not necessarily measured directly through the centre of
 * the Collaborative Image; therefore, the location within the image needs to be indicated. The
 * Single Point Range Measurement Row and Column Coordinates are the coordinates within the
 * Collaborative Sensor’s Image where the measurement was taken.
 *
 * <p><img src="../../../../org/jmisb/st1002/doc-files/singlepointrangemeasurement.png" alt="Single
 * Point Range Measurement">
 *
 * <p>These values are either 32-bit or 64-bit IEEE floating point values. If the Row and Column
 * values are omitted from the Range Image Local Set, then the default values are set to the centre
 * of the Collaborative Sensor's Image.
 *
 * @see SinglePointRangeMeasurement
 * @see SinglePointRangeMeasurementColumn
 */
public class SinglePointRangeMeasurementRow implements IRangeImageMetadataValue {

    private final double value;

    /**
     * Create from value.
     *
     * @param row the row coordinate in pixels.
     */
    public SinglePointRangeMeasurementRow(double row) {
        this.value = row;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array of length 4 or 8
     * @throws KlvParseException if the byte array is not of the correct length
     */
    public SinglePointRangeMeasurementRow(byte[] bytes) throws KlvParseException {
        try {
            this.value = PrimitiveConverter.toFloat64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(
                    "Single Point Range Measurement Row Coordinate is of length 4 or 8 bytes");
        }
    }

    @Override
    public final String getDisplayName() {
        return "Single Point Range Measurement Row";
    }

    /**
     * Get the row coordinate.
     *
     * @return row coordinate in pixels.
     */
    public double getRow() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.float64ToBytes(value);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%.1f", this.value);
    }
}
