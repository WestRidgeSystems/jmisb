package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.PrimitiveConverter;

/** Decoder for MISB ST1303 Multi-Dimensional Array Pack (MDAP) encoded byte arrays. */
public class MDAPDecoder {

    /**
     * Decode a one-dimensional floating point array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return floating point (double) array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public double[] decodeFloatingPoint1D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        try {
            BerField ndim = BerDecoder.decode(bytes, i, true);
            if (ndim.getValue() != 1) {
                throw new KlvParseException("Wrong dimensions for this call");
            }
            i += ndim.getLength();
            BerField dim1 = BerDecoder.decode(bytes, i, true);
            i += dim1.getLength();
            BerField ebytes = BerDecoder.decode(bytes, i, true);
            i += ebytes.getLength();
            BerField apa = BerDecoder.decode(bytes, i, true);
            i += apa.getLength();
            switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
                case NaturalFormat:
                    return decodeFloatingPoint1D_NaturalFormat(
                            bytes, i, dim1.getValue(), ebytes.getValue());
                case ST1201:
                    return decodeFloatingPoint1D_ST1201(
                            bytes, i, dim1.getValue(), ebytes.getValue());
                case BooleanArray:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 1D decode: BooleanArray");
                case UnsignedInteger:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 1D decode: UnsignedInteger");
                case RunLengthEncoding:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 1D decode: RunLengthEncoding");
                default:
                    throw new KlvParseException(
                            String.format(
                                    "Unknown APA algorithm for floating point 1D decode: %d",
                                    apa.getValue()));
            }
        } catch (java.lang.IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    private double[] decodeFloatingPoint1D_NaturalFormat(
            byte[] bytes, final int offset, final int numElements, final int eBytes)
            throws KlvParseException {
        int index = offset;
        double[] result = new double[numElements];
        for (int i = 0; i < numElements; ++i) {
            switch (eBytes) {
                case Double.BYTES:
                    result[i] = PrimitiveConverter.toFloat64(bytes, index);
                    break;
                case Float.BYTES:
                    result[i] = PrimitiveConverter.toFloat32(bytes, index);
                    break;
                default:
                    throw new KlvParseException(
                            String.format("Invalid number of bytes: %d", eBytes));
            }
            index += eBytes;
        }
        return result;
    }

    private double[] decodeFloatingPoint1D_ST1201(
            byte[] bytes, final int offset, final int numElements, final int eBytes)
            throws KlvParseException {
        int bytesRemaining = bytes.length - offset;
        int lengthOfArrayOfElements = numElements * eBytes;
        int lengthOfArrayProcessingAlgorithmSupportValues =
                bytesRemaining - lengthOfArrayOfElements;
        FpEncoder encoder;
        switch (lengthOfArrayProcessingAlgorithmSupportValues) {
            case 2 * Double.BYTES:
                {
                    double min = PrimitiveConverter.toFloat64(bytes, offset);
                    double max = PrimitiveConverter.toFloat64(bytes, offset + Double.BYTES);
                    encoder = new FpEncoder(min, max, eBytes);
                }
                break;
            case 2 * Float.BYTES:
                {
                    double min = PrimitiveConverter.toFloat32(bytes, offset);
                    double max = PrimitiveConverter.toFloat32(bytes, offset + Float.BYTES);
                    encoder = new FpEncoder(min, max, eBytes);
                }
                break;
            default:
                throw new KlvParseException(
                        String.format(
                                "Invalid length of APAS: %d",
                                lengthOfArrayProcessingAlgorithmSupportValues));
        }
        int index = offset + lengthOfArrayProcessingAlgorithmSupportValues;
        double[] result = new double[numElements];
        for (int i = 0; i < numElements; ++i) {
            result[i] = encoder.decode(bytes, index);
            index += eBytes;
        }
        return result;
    }

    /**
     * Decode a two-dimensional floating point array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return floating point (double) array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public double[][] decodeFloatingPoint2D(byte[] bytes, final int offset)
            throws KlvParseException {
        int i = offset;
        try {
            BerField ndim = BerDecoder.decode(bytes, i, true);
            if (ndim.getValue() != 2) {
                throw new KlvParseException("Wrong dimensions for this call");
            }
            i += ndim.getLength();
            BerField dim1 = BerDecoder.decode(bytes, i, true);
            i += dim1.getLength();
            BerField dim2 = BerDecoder.decode(bytes, i, true);
            i += dim2.getLength();
            BerField ebytes = BerDecoder.decode(bytes, i, true);
            i += ebytes.getLength();
            BerField apa = BerDecoder.decode(bytes, i, true);
            i += apa.getLength();
            switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
                case NaturalFormat:
                    return decodeFloatingPoint2D_NaturalFormat(
                            bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue());
                case ST1201:
                    return decodeFloatingPoint2D_ST1201(
                            bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue());
                case BooleanArray:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 2D decode: BooleanArray");
                case UnsignedInteger:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 2D decode: UnsignedInteger");
                case RunLengthEncoding:
                    throw new KlvParseException(
                            "Unsupported APA algorithm for floating point 2D decode: RunLengthEncoding");
                default:
                    throw new KlvParseException(
                            String.format(
                                    "Unknown APA algorithm for floating point 2D decode: %d",
                                    apa.getValue()));
            }
        } catch (java.lang.IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    private double[][] decodeFloatingPoint2D_NaturalFormat(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes)
            throws KlvParseException {
        int index = offset;
        double[][] result = new double[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                switch (eBytes) {
                    case Double.BYTES:
                        result[r][c] = PrimitiveConverter.toFloat64(bytes, index);
                        break;
                    case Float.BYTES:
                        result[r][c] = PrimitiveConverter.toFloat32(bytes, index);
                        break;
                    default:
                        throw new KlvParseException(
                                String.format("Invalid number of bytes: %d", eBytes));
                }
                index += eBytes;
            }
        }
        return result;
    }

    private double[][] decodeFloatingPoint2D_ST1201(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes)
            throws KlvParseException {
        int bytesRemaining = bytes.length - offset;
        int lengthOfArrayOfElements = numRows * numColumns * eBytes;
        int lengthOfArrayProcessingAlgorithmSupportValues =
                bytesRemaining - lengthOfArrayOfElements;
        FpEncoder encoder;
        switch (lengthOfArrayProcessingAlgorithmSupportValues) {
            case 2 * Double.BYTES:
                {
                    double min = PrimitiveConverter.toFloat64(bytes, offset);
                    double max = PrimitiveConverter.toFloat64(bytes, offset + Double.BYTES);
                    encoder = new FpEncoder(min, max, eBytes);
                }
                break;
            case 2 * Float.BYTES:
                {
                    double min = PrimitiveConverter.toFloat32(bytes, offset);
                    double max = PrimitiveConverter.toFloat32(bytes, offset + Float.BYTES);
                    encoder = new FpEncoder(min, max, eBytes);
                }
                break;
            default:
                throw new KlvParseException(
                        String.format(
                                "Invalid length of APAS: %d",
                                lengthOfArrayProcessingAlgorithmSupportValues));
        }
        int index = offset + lengthOfArrayProcessingAlgorithmSupportValues;
        double[][] result = new double[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = encoder.decode(bytes, index);
                index += eBytes;
            }
        }
        return result;
    }

    /**
     * Decode a two-dimensional boolean array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return boolean 2D array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public boolean[][] decodeBoolean2D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        BerField ndim = BerDecoder.decode(bytes, i, true);
        if (ndim.getValue() != 2) {
            throw new KlvParseException("Wrong dimensions for this call");
        }
        i += ndim.getLength();
        BerField dim1 = BerDecoder.decode(bytes, i, true);
        i += dim1.getLength();
        int numRows = dim1.getValue();
        BerField dim2 = BerDecoder.decode(bytes, i, true);
        i += dim2.getLength();
        int numColumns = dim2.getValue();
        BerField ebytes = BerDecoder.decode(bytes, i, true);
        i += ebytes.getLength();
        if (ebytes.getValue() != Byte.BYTES) {
            throw new KlvParseException("Expected 1 byte encoding for boolean RLE");
        }
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeBoolean2D_NaturalFormat(numRows, numColumns, bytes, i);
            case ST1201:
                throw new KlvParseException(
                        "Unsupported APA algorithm for boolean 2D decode: ST1201");
            case BooleanArray:
                return decodeBoolean2D_BooleanArray(numRows, numColumns, bytes, i);
            case UnsignedInteger:
                throw new KlvParseException(
                        "Unsupported APA algorithm for boolean 2D decode: Unsigned Integer");
            case RunLengthEncoding:
                int apas = bytes[i];
                i += Byte.BYTES;
                return decodeBoolean2D_RunLengthEncoding(numRows, numColumns, bytes, i, apas);
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for boolean 2D decode: %d", apa.getValue()));
        }
    }

    private boolean[][] decodeBoolean2D_NaturalFormat(
            int numRows, int numColumns, byte[] bytes, int i) {
        boolean[][] result = new boolean[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                // The only legal values are 0x00 or 0x01
                result[r][c] = bytes[i] != 0x00;
                i++;
            }
        }
        return result;
    }

    private boolean[][] decodeBoolean2D_BooleanArray(
            int numRows, int numColumns, byte[] bytes, int i) {
        boolean[][] result = new boolean[numRows][numColumns];
        int bitOffset = Byte.SIZE - 1;
        int currentByte = bytes[i];
        i++;
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                int mask = 0x01 << bitOffset;
                result[r][c] = (currentByte & mask) == mask;
                bitOffset -= 1;
                if (bitOffset < 0) {
                    bitOffset = Byte.SIZE - 1;
                    currentByte = bytes[i];
                    i++;
                }
            }
        }
        return result;
    }

    private boolean[][] decodeBoolean2D_RunLengthEncoding(
            int numRows, int numColumns, byte[] bytes, int i, int apas) {
        boolean[][] result = new boolean[numRows][numColumns];
        // Fill "background" with APAS value
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                // The only legal values are 0x00 or 0x01
                result[r][c] = apas != 0x00;
            }
        }
        while (i < bytes.length) {
            i = processNextPatch(bytes, i, result);
        }
        return result;
    }

    private int processNextPatch(byte[] bytes, int i, boolean[][] result) {
        boolean value = bytes[i] != 0x00;
        i++;
        BerField dim1 = BerDecoder.decode(bytes, i, true);
        i += dim1.getLength();
        int startRow = dim1.getValue();
        BerField dim2 = BerDecoder.decode(bytes, i, true);
        i += dim2.getLength();
        int startColumn = dim2.getValue();
        BerField runLength1 = BerDecoder.decode(bytes, i, true);
        i += runLength1.getLength();
        int numRowsForRun = runLength1.getValue();
        BerField runLength2 = BerDecoder.decode(bytes, i, true);
        i += runLength2.getLength();
        int numColumnsForRun = runLength2.getValue();
        for (int r = startRow; r < startRow + numRowsForRun; ++r) {
            for (int c = startColumn; c < startColumn + numColumnsForRun; ++c) {
                result[r][c] = value;
            }
        }
        return i;
    }

    /**
     * Decode a two-dimensional signed integer array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return (signed) 2D integer array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public long[][] decodeInt2D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        BerField ndim = BerDecoder.decode(bytes, i, true);
        if (ndim.getValue() != 2) {
            throw new KlvParseException("Wrong dimensions for this call");
        }
        i += ndim.getLength();
        BerField dim1 = BerDecoder.decode(bytes, i, true);
        i += dim1.getLength();
        BerField dim2 = BerDecoder.decode(bytes, i, true);
        i += dim2.getLength();
        BerField ebytes = BerDecoder.decode(bytes, i, true);
        i += ebytes.getLength();
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeInt2D_NaturalFormat(
                        bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue());
            case ST1201:
                throw new KlvParseException(
                        "Invalid APA algorithm for signed integer 1D decode: ST1201");
            case BooleanArray:
                throw new KlvParseException(
                        "Invalid APA algorithm for signed integer 1D decode: BooleanArray");
            case UnsignedInteger:
                throw new KlvParseException(
                        "Invalid APA algorithm for signed integer 1D decode: UnsignedInteger");
            case RunLengthEncoding:
                throw new KlvParseException(
                        "Unsupported APA algorithm for signed integer 1D decode: RunLengthEncoding");
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for  signed integer 1D decode: %d",
                                apa.getValue()));
        }
    }

    private long[][] decodeInt2D_NaturalFormat(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes)
            throws KlvParseException {
        int index = offset;
        long[][] result = new long[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = PrimitiveConverter.variableBytesToInt64(bytes, index, eBytes);
                index += eBytes;
            }
        }
        return result;
    }

    /**
     * Decode a one-dimensional unsigned integer array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return (unsigned) integer array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public long[] decodeUInt1D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        BerField ndim = BerDecoder.decode(bytes, i, true);
        if (ndim.getValue() != 1) {
            throw new KlvParseException("Wrong dimensions for this call");
        }
        i += ndim.getLength();
        BerField dim1 = BerDecoder.decode(bytes, i, true);
        i += dim1.getLength();
        BerField ebytes = BerDecoder.decode(bytes, i, true);
        i += ebytes.getLength();
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeUInt_NaturalFormat(bytes, i, dim1.getValue(), ebytes.getValue());
            case ST1201:
                throw new KlvParseException(
                        "Invalid APA algorithm for unsigned integer 1D decode: ST1201");
            case BooleanArray:
                throw new KlvParseException(
                        "Invalid APA algorithm for unsigned integer 1D decode: BooleanArray");
            case UnsignedInteger:
                BerField biasField = BerDecoder.decode(bytes, i, true);
                i += biasField.getLength();
                return decodeUInt_UnsignedIntegerEncoded(
                        bytes, i, dim1.getValue(), biasField.getValue());

            case RunLengthEncoding:
                throw new KlvParseException(
                        "Unsupported APA algorithm for unsigned integer 1D decode: RunLengthEncoding");
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for  unsigned integer 1D decode: %d",
                                apa.getValue()));
        }
    }

    private long[] decodeUInt_NaturalFormat(
            byte[] bytes, final int offset, final int numElements, final int eBytes)
            throws KlvParseException {
        int index = offset;
        long[] result = new long[numElements];
        for (int i = 0; i < numElements; ++i) {
            result[i] = PrimitiveConverter.variableBytesToUint64(bytes, index, eBytes);
            index += eBytes;
        }
        return result;
    }

    private long[] decodeUInt_UnsignedIntegerEncoded(
            byte[] bytes, final int offset, final int numElements, final int bias) {
        int index = offset;
        long[] result = new long[numElements];
        for (int i = 0; i < numElements; ++i) {
            BerField el = BerDecoder.decode(bytes, index, true);
            result[i] = el.getValue() + bias;
            index += el.getLength();
        }
        return result;
    }

    /**
     * Decode a two-dimensional unsigned integer array from a byte array.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return (unsigned) 2D integer array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public long[][] decodeUInt2D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        BerField ndim = BerDecoder.decode(bytes, i, true);
        if (ndim.getValue() != 2) {
            throw new KlvParseException("Wrong dimensions for this call");
        }
        i += ndim.getLength();
        BerField dim1 = BerDecoder.decode(bytes, i, true);
        i += dim1.getLength();
        BerField dim2 = BerDecoder.decode(bytes, i, true);
        i += dim2.getLength();
        BerField ebytes = BerDecoder.decode(bytes, i, true);
        i += ebytes.getLength();
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeUInt2D_NaturalFormat(
                        bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue());
            case ST1201:
                throw new KlvParseException(
                        "Invalid APA algorithm for unsigned integer 1D decode: ST1201");
            case BooleanArray:
                throw new KlvParseException(
                        "Invalid APA algorithm for unsigned integer 1D decode: BooleanArray");
            case UnsignedInteger:
                BerField biasField = BerDecoder.decode(bytes, i, true);
                i += biasField.getLength();
                return decodeUInt2D_UnsignedIntegerEncoded(
                        bytes, i, dim1.getValue(), dim2.getValue(), biasField.getValue());

            case RunLengthEncoding:
                throw new KlvParseException(
                        "Unsupported APA algorithm for unsigned integer 1D decode: RunLengthEncoding");
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for  unsigned integer 1D decode: %d",
                                apa.getValue()));
        }
    }

    private long[][] decodeUInt2D_NaturalFormat(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes)
            throws KlvParseException {
        int index = offset;
        long[][] result = new long[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = PrimitiveConverter.variableBytesToUint64(bytes, index, eBytes);
                index += eBytes;
            }
        }
        return result;
    }

    private long[][] decodeUInt2D_UnsignedIntegerEncoded(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int bias) {
        int index = offset;
        long[][] result = new long[numRows][numColumns];
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                BerField el = BerDecoder.decode(bytes, index, true);
                result[r][c] = el.getValue() + bias;
                index += el.getLength();
            }
        }
        return result;
    }
}
