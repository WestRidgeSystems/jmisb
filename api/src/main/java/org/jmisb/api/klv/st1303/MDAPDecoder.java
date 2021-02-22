package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.core.klv.PrimitiveConverter;

/** Decoder for MISB ST1303 Multi-Dimensional Array Pack (MDAP) encoded byte arrays. */
public class MDAPDecoder {
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
                throw new KlvParseException("TODO: wrong dimensions for this call");
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
}
