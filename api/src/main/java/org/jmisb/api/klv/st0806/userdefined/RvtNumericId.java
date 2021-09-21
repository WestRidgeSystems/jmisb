package org.jmisb.api.klv.st0806.userdefined;

import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Numeric ID (ST 0806 User Defined Local Set Tag 1).
 *
 * <p>Required when using User Defined Local Set.
 */
public class RvtNumericId implements IRvtUserDefinedMetadataValue {
    protected short number;
    private static final int REQUIRED_BYTE_LENGTH = 1;

    /**
     * Create from value.
     *
     * @param dataType the datatype (0 for string, 1 for integer, 2 for unsigned integer, 3 for
     *     experimental)
     * @param id the numeric id for the user defined data in the range [0,63].
     */
    public RvtNumericId(int dataType, int id) {
        if (dataType > 3 || dataType < 0) {
            throw new IllegalArgumentException(
                    getDisplayName() + " data type must be in range [0, 3]");
        }
        if (id > 63 || id < 0) {
            throw new IllegalArgumentException(getDisplayName() + " id must be in range [0, 64]");
        }
        number = (short) ((dataType << 6) + id);
    }

    /**
     * Construct from encoded bytes.
     *
     * @param bytes one byte representing 8-bit unsigned integer value.
     */
    public RvtNumericId(byte[] bytes) {
        if (bytes.length != REQUIRED_BYTE_LENGTH) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is a one byte unsigned integer");
        }
        this.number = (short) PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes(number);
    }

    @Override
    public String getDisplayableValue() {
        int dataType = getDataType();
        String dataTypeRepresentation;
        switch (dataType) {
            case 0:
                dataTypeRepresentation = "String: ";
                break;
            case 1:
                dataTypeRepresentation = "Signed Integer: ";
                break;
            case 2:
                dataTypeRepresentation = "Unsigned Integer: ";
                break;
            case 3:
                dataTypeRepresentation = "Experimental: ";
                break;
            default:
                dataTypeRepresentation = "Unknown: ";
                break;
        }
        int idPart = getId();
        return dataTypeRepresentation + idPart;
    }

    /**
     * Get the id part of this numeric identifier.
     *
     * @return index between 0 and 63 (inclusive).
     */
    public int getId() {
        return this.number & 0x3F;
    }

    /**
     * Get the data type part of this numeric identifier.
     *
     * @return the datatype (0 for string, 1 for integer, 2 for unsigned integer, 3 for
     *     experimental)
     */
    public int getDataType() {
        return this.number >> 6;
    }

    @Override
    public final String getDisplayName() {
        return "Numeric ID";
    }

    /**
     * Get the composite value for this numeric ID.
     *
     * @return numeric ID as an integer.
     */
    public int getValue() {
        return number;
    }
}
