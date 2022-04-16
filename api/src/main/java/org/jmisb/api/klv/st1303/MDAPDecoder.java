package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;

/** Decoder for MISB ST1303 Multi-Dimensional Array Pack (MDAP) encoded byte arrays. */
public class MDAPDecoder {

    /**
     * Decode a one-dimensional floating point array from a byte array.
     *
     * <p>This supports Natural Format, ST 1201 and Run Length Encoding. Boolean Array and Unsigned
     * Integer array processing are not applicable to this kind of data.
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
                            "Invalid APA algorithm for floating point 1D decode: BooleanArray");
                case UnsignedInteger:
                    throw new KlvParseException(
                            "Invalid APA algorithm for floating point 1D decode: UnsignedInteger");
                case RunLengthEncoding:
                    double apas = getFloatingPointValue(bytes, i, ebytes.getValue());
                    i += ebytes.getValue();
                    return decodeFloatingPoint1D_RunLengthEncoding(
                            bytes, i, ebytes.getValue(), dim1.getValue(), apas);
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
            result[i] = getFloatingPointValue(bytes, index, eBytes);
            index += eBytes;
        }
        return result;
    }

    private double getFloatingPointValue(byte[] bytes, int index, final int eBytes)
            throws KlvParseException {
        double d;
        switch (eBytes) {
            case Double.BYTES:
                d = PrimitiveConverter.toFloat64(bytes, index);
                break;
            case Float.BYTES:
                d = PrimitiveConverter.toFloat32(bytes, index);
                break;
            default:
                throw new KlvParseException(String.format("Invalid number of bytes: %d", eBytes));
        }
        return d;
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
                    encoder = new FpEncoder(min, max, eBytes, OutOfRangeBehaviour.Default);
                }
                break;
            case 2 * Float.BYTES:
                {
                    double min = PrimitiveConverter.toFloat32(bytes, offset);
                    double max = PrimitiveConverter.toFloat32(bytes, offset + Float.BYTES);
                    encoder = new FpEncoder(min, max, eBytes, OutOfRangeBehaviour.Default);
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

    private double[] decodeFloatingPoint1D_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int eBytes,
            final int numElements,
            final double apas)
            throws KlvParseException {
        int index = offset;
        double[] result = new double[numElements];
        // Fill with APAS value
        for (int i = 0; i < numElements; ++i) {
            result[i] = apas;
        }
        while (index < bytes.length) {
            index = processNextRunDouble(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextRunDouble(byte[] bytes, int eBytes, int i, double[] result)
            throws KlvParseException {
        double value = getFloatingPointValue(bytes, i, eBytes);
        i += eBytes;
        BerField dim = BerDecoder.decode(bytes, i, true);
        i += dim.getLength();
        int startPosition = dim.getValue();
        BerField runLength = BerDecoder.decode(bytes, i, true);
        i += runLength.getLength();
        int entriesInRun = runLength.getValue();
        for (int r = startPosition; r < startPosition + entriesInRun; ++r) {
            result[r] = value;
        }
        return i;
    }

    /**
     * Decode a two-dimensional floating point array from a byte array.
     *
     * <p>This supports Natural Format, ST 1201 and Run Length Encoding. Boolean Array and Unsigned
     * Integer array processing are not applicable to this kind of data.
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
                            "Invalid APA algorithm for floating point 2D decode: BooleanArray");
                case UnsignedInteger:
                    throw new KlvParseException(
                            "Invalid APA algorithm for floating point 2D decode: UnsignedInteger");
                case RunLengthEncoding:
                    double apas = getFloatingPointValue(bytes, i, ebytes.getValue());
                    i += ebytes.getValue();
                    return decodeFloatingPoint2D_RunLengthEncoding(
                            bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue(), apas);
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
                result[r][c] = getFloatingPointValue(bytes, index, eBytes);
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
                    encoder = new FpEncoder(min, max, eBytes, OutOfRangeBehaviour.Default);
                }
                break;
            case 2 * Float.BYTES:
                {
                    double min = PrimitiveConverter.toFloat32(bytes, offset);
                    double max = PrimitiveConverter.toFloat32(bytes, offset + Float.BYTES);
                    encoder = new FpEncoder(min, max, eBytes, OutOfRangeBehaviour.Default);
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

    private double[][] decodeFloatingPoint2D_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes,
            final double apas)
            throws KlvParseException {
        int index = offset;
        double[][] result = new double[numRows][numColumns];
        // Fill with APAS value
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = apas;
            }
        }
        while (index < bytes.length) {
            index = processNextPatchDouble(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextPatchDouble(byte[] bytes, int eBytes, int i, double[][] result)
            throws KlvParseException {
        double value = getFloatingPointValue(bytes, i, eBytes);
        i += eBytes;
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
     * Decode a one-dimensional boolean array from a byte array.
     *
     * <p>This supports Natural Format, Boolean Array and Run Length Encoding. ST 1201 and Unsigned
     * Integer array processing are not applicable to this kind of data.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return boolean 1D array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public boolean[] decodeBoolean1D(byte[] bytes, final int offset) throws KlvParseException {
        int i = offset;
        BerField ndim = BerDecoder.decode(bytes, i, true);
        if (ndim.getValue() != 1) {
            throw new KlvParseException("Wrong dimensions for this call");
        }
        i += ndim.getLength();
        BerField dim = BerDecoder.decode(bytes, i, true);
        i += dim.getLength();
        int numValues = dim.getValue();
        BerField ebytes = BerDecoder.decode(bytes, i, true);
        i += ebytes.getLength();
        if (ebytes.getValue() != Byte.BYTES) {
            throw new KlvParseException("Expected 1 byte encoding for boolean MDAP");
        }
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeBoolean1D_NaturalFormat(numValues, bytes, i);
            case ST1201:
                throw new KlvParseException("Invalid APA algorithm for boolean 1D decode: ST1201");
            case BooleanArray:
                return decodeBoolean1D_BooleanArray(numValues, bytes, i);
            case UnsignedInteger:
                throw new KlvParseException(
                        "Invalid APA algorithm for boolean 1D decode: Unsigned Integer");
            case RunLengthEncoding:
                int apas = bytes[i];
                i += Byte.BYTES;
                return decodeBoolean1D_RunLengthEncoding(numValues, bytes, i, apas);
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for boolean 2D decode: %d", apa.getValue()));
        }
    }

    private boolean[] decodeBoolean1D_NaturalFormat(int numValues, byte[] bytes, int offset) {
        boolean[] result = new boolean[numValues];
        for (int i = 0; i < numValues; ++i) {
            // The only legal values are 0x00 or 0x01
            result[i] = bytes[offset] != 0x00;
            offset++;
        }
        return result;
    }

    private boolean[] decodeBoolean1D_BooleanArray(int numValues, byte[] bytes, int offset) {
        boolean[] result = new boolean[numValues];
        int bitOffset = Byte.SIZE - 1;
        int currentByte = bytes[offset];
        offset++;
        for (int r = 0; r < numValues; ++r) {
            int mask = 0x01 << bitOffset;
            result[r] = (currentByte & mask) == mask;
            bitOffset -= 1;
            if (bitOffset < 0) {
                bitOffset = Byte.SIZE - 1;
                currentByte = bytes[offset];
                offset++;
            }
        }
        return result;
    }

    private boolean[] decodeBoolean1D_RunLengthEncoding(
            int numValues, byte[] bytes, int i, int apas) {
        boolean[] result = new boolean[numValues];
        // Fill "background" with APAS value
        for (int r = 0; r < numValues; ++r) {
            // The only legal values are 0x00 or 0x01
            result[r] = apas != 0x00;
        }
        while (i < bytes.length) {
            i = processNextRun(bytes, i, result);
        }
        return result;
    }

    private int processNextRun(byte[] bytes, int i, boolean[] result) {
        boolean value = bytes[i] != 0x00;
        i++;
        BerField dim = BerDecoder.decode(bytes, i, true);
        i += dim.getLength();
        int startPosition = dim.getValue();
        BerField runLength = BerDecoder.decode(bytes, i, true);
        i += runLength.getLength();
        int entriesInRun = runLength.getValue();
        for (int r = startPosition; r < startPosition + entriesInRun; ++r) {
            result[r] = value;
        }
        return i;
    }

    /**
     * Decode a two-dimensional boolean array from a byte array.
     *
     * <p>This supports Natural Format, Boolean Array and Run Length Encoding. ST 1201 and Unsigned
     * Integer array processing are not applicable to this kind of data.
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
            throw new KlvParseException("Expected 1 byte encoding for boolean MDAP");
        }
        BerField apa = BerDecoder.decode(bytes, i, true);
        i += apa.getLength();
        switch (ArrayProcessingAlgorithm.getValue(apa.getValue())) {
            case NaturalFormat:
                return decodeBoolean2D_NaturalFormat(numRows, numColumns, bytes, i);
            case ST1201:
                throw new KlvParseException("Invalid APA algorithm for boolean 2D decode: ST1201");
            case BooleanArray:
                return decodeBoolean2D_BooleanArray(numRows, numColumns, bytes, i);
            case UnsignedInteger:
                throw new KlvParseException(
                        "Invalid APA algorithm for boolean 2D decode: Unsigned Integer");
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
     * Decode a one-dimensional signed integer array from a byte array.
     *
     * <p>This supports Natural Format and Run Length Encoding. ST 1201, Boolean Array, and Unsigned
     * Integer array processing are not applicable to this kind of data.
     *
     * @param bytes the byte array to decode from
     * @param offset the offset to start the decoding from
     * @return (signed) 1D integer array containing the data decoded from the byte array.
     * @throws KlvParseException if the parsing fails.
     */
    public long[] decodeInt1D(byte[] bytes, final int offset) throws KlvParseException {
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
                return decodeInt1D_NaturalFormat(bytes, i, dim1.getValue(), ebytes.getValue());
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
                long apas = PrimitiveConverter.variableBytesToInt64(bytes, i, ebytes.getValue());
                i += ebytes.getValue();
                return decodeInt1D_RunLengthEncoding(
                        bytes, i, dim1.getValue(), ebytes.getValue(), apas);
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for  signed integer 1D decode: %d",
                                apa.getValue()));
        }
    }

    private long[] decodeInt1D_NaturalFormat(
            byte[] bytes, final int offset, final int numElements, final int eBytes) {
        int index = offset;
        long[] result = new long[numElements];
        for (int i = 0; i < numElements; ++i) {
            result[i] = PrimitiveConverter.variableBytesToInt64(bytes, index, eBytes);
            index += eBytes;
        }
        return result;
    }

    private long[] decodeInt1D_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int numElements,
            final int eBytes,
            final long apas) {
        int index = offset;
        long[] result = new long[numElements];
        // Fill with APAS value
        for (int i = 0; i < numElements; ++i) {
            result[i] = apas;
        }
        while (index < bytes.length) {
            index = processNextRunSignedInt(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextRunSignedInt(byte[] bytes, int eBytes, int i, long[] result) {
        long value = PrimitiveConverter.variableBytesToInt64(bytes, i, eBytes);
        i += eBytes;
        BerField dim = BerDecoder.decode(bytes, i, true);
        i += dim.getLength();
        int startPosition = dim.getValue();
        BerField runLength = BerDecoder.decode(bytes, i, true);
        i += runLength.getLength();
        int entriesInRun = runLength.getValue();
        for (int r = startPosition; r < startPosition + entriesInRun; ++r) {
            result[r] = value;
        }
        return i;
    }

    /**
     * Decode a two-dimensional signed integer array from a byte array.
     *
     * <p>This supports Natural Format and Run Length Encoding. ST 1201, Boolean Array, and Unsigned
     * Integer array processing are not applicable to this kind of data.
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
                        "Invalid APA algorithm for signed integer 2D decode: ST1201");
            case BooleanArray:
                throw new KlvParseException(
                        "Invalid APA algorithm for signed integer 2D decode: BooleanArray");
            case UnsignedInteger:
                throw new KlvParseException(
                        "Invalid APA algorithm for signed integer 2D decode: UnsignedInteger");
            case RunLengthEncoding:
                long apas = PrimitiveConverter.variableBytesToInt64(bytes, i, ebytes.getValue());
                i += ebytes.getValue();
                return decodeInt2D_RunLengthEncoding(
                        bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue(), apas);
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for signed integer 2D decode: %d",
                                apa.getValue()));
        }
    }

    private long[][] decodeInt2D_NaturalFormat(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes) {
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

    private long[][] decodeInt2D_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes,
            final long apas) {
        int index = offset;
        long[][] result = new long[numRows][numColumns];
        // Fill with APAS value
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = apas;
            }
        }
        while (index < bytes.length) {
            index = processNextPatchSignedInt(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextPatchSignedInt(byte[] bytes, int eBytes, int i, long[][] result) {
        long value = PrimitiveConverter.variableBytesToInt64(bytes, i, eBytes);
        i += eBytes;
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
     * Decode a one-dimensional unsigned integer array from a byte array.
     *
     * <p>This supports Natural Format, Unsigned Integer and Run Length Encoding. ST 1201 and
     * Boolean Array array processing are not applicable to this kind of data.
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
                long apas = PrimitiveConverter.variableBytesToUint64(bytes, i, ebytes.getValue());
                i += ebytes.getValue();
                return decodeUInt_RunLengthEncoding(
                        bytes, i, dim1.getValue(), ebytes.getValue(), apas);
            default:
                throw new KlvParseException(
                        String.format(
                                "Unknown APA algorithm for  unsigned integer 1D decode: %d",
                                apa.getValue()));
        }
    }

    private long[] decodeUInt_NaturalFormat(
            byte[] bytes, final int offset, final int numElements, final int eBytes) {
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

    private long[] decodeUInt_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int numElements,
            final int eBytes,
            final long apas) {
        int index = offset;
        long[] result = new long[numElements];
        // Fill with APAS value
        for (int i = 0; i < numElements; ++i) {
            result[i] = apas;
        }
        while (index < bytes.length) {
            index = processNextRunUnsignedInt(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextRunUnsignedInt(byte[] bytes, int eBytes, int i, long[] result) {
        long value = PrimitiveConverter.variableBytesToUint64(bytes, i, eBytes);
        i += eBytes;
        BerField dim = BerDecoder.decode(bytes, i, true);
        i += dim.getLength();
        int startPosition = dim.getValue();
        BerField runLength = BerDecoder.decode(bytes, i, true);
        i += runLength.getLength();
        int entriesInRun = runLength.getValue();
        for (int r = startPosition; r < startPosition + entriesInRun; ++r) {
            result[r] = value;
        }
        return i;
    }

    /**
     * Decode a two-dimensional unsigned integer array from a byte array.
     *
     * <p>This supports Natural Format, Unsigned Integer and Run Length Encoding. ST 1201 and
     * Boolean Array array processing are not applicable to this kind of data.
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
                long apas = PrimitiveConverter.variableBytesToUint64(bytes, i, ebytes.getValue());
                i += ebytes.getValue();
                return decodeUInt2D_RunLengthEncoding(
                        bytes, i, dim1.getValue(), dim2.getValue(), ebytes.getValue(), apas);
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
            final int eBytes) {
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

    private long[][] decodeUInt2D_RunLengthEncoding(
            byte[] bytes,
            final int offset,
            final int numRows,
            final int numColumns,
            final int eBytes,
            final long apas) {
        int index = offset;
        long[][] result = new long[numRows][numColumns];
        // Fill with APAS value
        for (int r = 0; r < numRows; ++r) {
            for (int c = 0; c < numColumns; ++c) {
                result[r][c] = apas;
            }
        }
        while (index < bytes.length) {
            index = processNextPatchUnsignedInt(bytes, eBytes, index, result);
        }
        return result;
    }

    private int processNextPatchUnsignedInt(byte[] bytes, int eBytes, int i, long[][] result) {
        long value = PrimitiveConverter.variableBytesToUint64(bytes, i, eBytes);
        i += eBytes;
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
}
