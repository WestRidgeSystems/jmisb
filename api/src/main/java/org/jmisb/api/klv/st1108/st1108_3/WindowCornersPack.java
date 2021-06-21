package org.jmisb.api.klv.st1108.st1108_3;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Window Corners Pack.
 *
 * <p>From ST 1108.3:
 *
 * <blockquote>
 *
 * <p>The Window Corners Pack, which is a defined length pack (DLP), describes a sub-image region
 * within an image Frame for the metric calculation bounded by Frame Line[start] to Frame Line [end]
 * and Frame Row[start] to Frame Row[end].
 *
 * <p>This enables defining a local region within an image to calculate image metrics rather than
 * over the entire image.
 *
 * <p>The Window Corners Pack is optional. If not provided, the metrics are assumed as taken over
 * the entire image.
 *
 * </blockquote>
 */
public class WindowCornersPack implements IInterpretabilityQualityMetadataValue {

    private final int startingRow;
    private final int endingRow;
    private final int startingColumn;
    private final int endingColumn;

    /**
     * Create from values.
     *
     * <p>The values essentially provide a bounding box defined by {@code [c1row, c1column]} as the
     * upper left hand corner, and {@code [c2row, c2column]} as the lower right hand corner.
     *
     * <p>Values are zero based, in units of pixels.
     *
     * @param c1row starting row (top row for the region of interest)
     * @param c1column starting column (left hand column for the region of interest)
     * @param c2row ending row (bottom row for the region of interest)
     * @param c2column ending column (right hand column for the region of interest)
     */
    public WindowCornersPack(
            final int c1row, final int c1column, final int c2row, final int c2column) {
        startingRow = c1row;
        startingColumn = c1column;
        endingRow = c2row;
        endingColumn = c2column;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing 4 BER-OID encoded values.
     * @throws KlvParseException if the {@code bytes} array could not be fully decoded
     */
    public WindowCornersPack(byte[] bytes) throws KlvParseException {
        int offset = 0;

        try {
            BerField c1rowField = BerDecoder.decode(bytes, offset, true);
            startingRow = c1rowField.getValue();
            offset += c1rowField.getLength();

            BerField c1columnField = BerDecoder.decode(bytes, offset, true);
            startingColumn = c1columnField.getValue();
            offset += c1columnField.getLength();

            BerField c2rowField = BerDecoder.decode(bytes, offset, true);
            endingRow = c2rowField.getValue();
            offset += c2rowField.getLength();

            BerField c2columnField = BerDecoder.decode(bytes, offset, true);
            endingColumn = c2columnField.getValue();
            offset += c2columnField.getLength();
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }

        if (offset != bytes.length) {
            throw new KlvParseException("WindowCornersPack not correctly encoded");
        }
    }

    /**
     * Get the value as a byte array.
     *
     * <p>This is the V part of a TLV encoding.
     *
     * @return the value of this pack, as a byte array.
     */
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        arrayBuilder.appendAsOID(startingRow);
        arrayBuilder.appendAsOID(startingColumn);
        arrayBuilder.appendAsOID(endingRow);
        arrayBuilder.appendAsOID(endingColumn);
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return String.format(
                "[%d, %d], [%d, %d]", startingRow, startingColumn, endingRow, endingColumn);
    }

    @Override
    public final String getDisplayName() {
        return "Window Corners";
    }

    /**
     * Get the starting row.
     *
     * <p>The row number ({@code c1row}) for the top left corner coordinate, in pixel space where
     * the first row is zero.
     *
     * @return starting row in pixels.
     */
    public int getStartingRow() {
        return startingRow;
    }

    /**
     * Get the ending row.
     *
     * <p>The row number ({@code c2row}) for the bottom right corner coordinate, in pixel space
     * where the first row is zero.
     *
     * @return ending row in pixels.
     */
    public int getEndingRow() {
        return endingRow;
    }

    /**
     * Get the starting column.
     *
     * <p>The column number ({@code c1column}) for the top left corner coordinate, in pixel space
     * where the first column is zero.
     *
     * @return starting column in pixels.
     */
    public int getStartingColumn() {
        return startingColumn;
    }

    /**
     * Get the ending column.
     *
     * <p>The column number ({@code c2column}) for the bottom right corner coordinate, in pixel
     * space where the first column is zero.
     *
     * @return ending column in pixels.
     */
    public int getEndingColumn() {
        return endingColumn;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.WindowCornersPack.getIdentifier());
        byte[] valueBytes = getBytes();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
