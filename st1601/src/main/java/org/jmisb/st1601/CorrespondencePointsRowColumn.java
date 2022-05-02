package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1303.NaturalFormatEncoder;

/**
 * Correspondence Points – Row/Column (ST 1601 Tag 4).
 *
 * <p>A geo-registration algorithm uses the Correspondence Points – Row/Column MDARRAY item to
 * define an extensible list of tie points represented in pixel space for two Images, or a single
 * Image and a set of geographic points defined in Tag 5 (Latitude/Longitude) and Tag 8 (Height
 * above Elevation).
 *
 * <p>For the case where this represents N correspondence points between two images, this is four
 * rows by N columns. The rows (in order) are image 1 row ("Y" direction), image 1 column ("X"
 * direction), image 2 row, image 2 column.
 *
 * <p>For the case where this represents N correspondence points to geographic points, this is two
 * rows by N columns.The rows (in order) are image row ("Y" direction), image column ("X"
 * direction),
 *
 * <p>See ST 1601.1 Section 6.3.4 for more information.
 */
public class CorrespondencePointsRowColumn implements IGeoRegistrationValue {

    private final long[][] values;
    /**
     * Create from value.
     *
     * @param tiePoints the array tie points.
     */
    public CorrespondencePointsRowColumn(long[][] tiePoints) {
        values = tiePoints.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     * @throws KlvParseException if parsing fails
     */
    public CorrespondencePointsRowColumn(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.values = decoder.decodeUInt2D(bytes, 0);
    }

    @Override
    public byte[] getBytes() {
        try {
            NaturalFormatEncoder encoder = new NaturalFormatEncoder();
            return encoder.encodeUnsigned(values);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public final String getDisplayableValue() {
        return "[Correspondence Points – Row/Column]";
    }

    @Override
    public final String getDisplayName() {
        return "Correspondence Points – Row/Column";
    }

    /**
     * Get the Correspondence Points.
     *
     * @return correspondence points
     */
    public long[][] getCorrespondencePoints() {
        return values.clone();
    }
}
