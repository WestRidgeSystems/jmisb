package org.jmisb.api.klv.st1108.st1108_2;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Image Chip Location, Size and Bit Depth.
 *
 * <p>From ST 1108.2:
 *
 * <blockquote>
 *
 * <p>The chip location is indicated by the column and row index of the top-left corner of the chip.
 *
 * <p>The first location value is the column index.
 *
 * <p>The second location value is the row index.
 *
 * <p>The third value is the length of the edge of the chip. Chips are square.
 *
 * <p>The fourth value is the bit-depth of the imagery. Bit-depths up to 16 are supported.
 *
 * </blockquote>
 */
public class ChipLocationSizeBitDepth implements IInterpretabilityQualityMetadataValue {

    private static final int COLUMN_INDEX_OFFSET = 0;
    private static final int COLUMN_INDEX_BYTES = 2;
    private static final int ROW_INDEX_OFFSET = COLUMN_INDEX_OFFSET + COLUMN_INDEX_BYTES;
    private static final int ROW_INDEX_BYTES = 2;
    private static final int CHIP_LENGTH_OFFSET = ROW_INDEX_OFFSET + ROW_INDEX_BYTES;
    private static final int CHIP_LENGTH_BYTES = 2;
    private static final int BIT_DEPTH_OFFSET = CHIP_LENGTH_OFFSET + CHIP_LENGTH_BYTES;
    private static final int BIT_DEPTH_BYTES = 2;
    private static final int REQUIRED_NUM_BYTES =
            COLUMN_INDEX_BYTES + ROW_INDEX_BYTES + CHIP_LENGTH_BYTES + BIT_DEPTH_BYTES;
    private final int columnIndex;
    private final int rowIndex;
    private final int chipLength;
    private final int bitDepth;

    /**
     * Create from values.
     *
     * @param columnIndex column index of the top left corner of the chip
     * @param rowIndex row index of the top left corner of the chip
     * @param chipLength the length of the edge of the chip, which is square (32, 64 or 128)
     * @param bitDepth the bit depth of the imagery
     */
    public ChipLocationSizeBitDepth(int columnIndex, int rowIndex, int chipLength, int bitDepth) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.chipLength = chipLength;
        this.bitDepth = bitDepth;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array containing 4 16-bit unsigned integer values
     * @throws KlvParseException if the {@code bytes} array could not be fully decoded
     */
    public ChipLocationSizeBitDepth(byte[] bytes) throws KlvParseException {
        if (bytes.length != REQUIRED_NUM_BYTES) {
            throw new KlvParseException(
                    this.getDisplayName() + " encoding is 4x16-bit unsigned int");
        }
        this.columnIndex = PrimitiveConverter.toUint16(bytes, COLUMN_INDEX_OFFSET);
        this.rowIndex = PrimitiveConverter.toUint16(bytes, ROW_INDEX_OFFSET);
        this.chipLength = PrimitiveConverter.toUint16(bytes, CHIP_LENGTH_OFFSET);
        this.bitDepth = PrimitiveConverter.toUint16(bytes, BIT_DEPTH_OFFSET);
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
        arrayBuilder.append(PrimitiveConverter.uint16ToBytes(columnIndex));
        arrayBuilder.append(PrimitiveConverter.uint16ToBytes(rowIndex));
        arrayBuilder.append(PrimitiveConverter.uint16ToBytes(chipLength));
        arrayBuilder.append(PrimitiveConverter.uint16ToBytes(bitDepth));
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return String.format(
                "[%d, %d], %dx%d, %d", columnIndex, rowIndex, chipLength, chipLength, bitDepth);
    }

    @Override
    public final String getDisplayName() {
        return "Chip Location, Size & Bit Depth";
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(LegacyIQMetadataKey.ChipLocationSizeBitDepth.getIdentifier());
        byte[] valueBytes = getBytes();
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }

    /**
     * Get the column index for the top left corner of the chip.
     *
     * @return column index as an integer number of pixels
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * Get the row index for the top left corner of the chip.
     *
     * @return row index as an integer number of pixels
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * The size of the image chip.
     *
     * @return length of the (square) image chip edge.
     */
    public int getChipLength() {
        return chipLength;
    }

    /**
     * The bit-depth of the imagery.
     *
     * @return the bit-depth.
     */
    public int getBitDepth() {
        return bitDepth;
    }
}
