package org.jmisb.st1010;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Parsing support for {@link SDCC} instances.
 *
 * <p>This turns an ST 1010 encoded byte array into an SDCC structure.
 *
 * <p>Note that parsing of an SDCC instance that uses Mode 1 parse control requires out-of-band
 * information (defined in the invoking standards document) on the encoding of standard deviation
 * values (i.e. either ST 1201 IMAP, or IEEE-754 float).
 */
public class SDCCParser {

    private EncodingFormat standardDeviationFormat = EncodingFormat.IEEE;

    /**
     * Constructor.
     *
     * <p>Creates a new SDCC Parser, initialized to use IEEE format for standard deviation encoding
     * (applicable to Mode 1). Use {@link setStandardDeviationFormat} prior to calling {@link parse}
     * if required.
     */
    public SDCCParser() {}

    /**
     * Get the standard deviation encoding format.
     *
     * @return the encoding format as an enumerated value.
     */
    public EncodingFormat getStandardDeviationFormat() {
        return standardDeviationFormat;
    }

    /**
     * Set the standard deviation encoding format.
     *
     * <p>This is only required for "Mode 1" parsing.
     *
     * @param format the encoding format to use
     */
    public void setStandardDeviationFormat(EncodingFormat format) {
        this.standardDeviationFormat = format;
    }

    /**
     * Parse a byte array into an SDCC structure.
     *
     * <p>This will automatically detect and handle both Mode 1 and Mode 2 parse control modes.
     *
     * @param bytes the ST 1010 encoded data
     * @return SDCC equivalent to the {@code bytes} data.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public SDCC parse(byte[] bytes) throws KlvParseException {
        int offset = 0;
        BerField berMatrix = BerDecoder.decode(bytes, offset, true);
        int matrixSize = berMatrix.getValue();
        offset += berMatrix.getLength();
        SDCCParserContext parserContext;
        int modeByte1 = bytes[offset];
        offset += 1;
        if (isLastByte(modeByte1)) {
            parserContext = initialiseMode1(modeByte1);
        } else {
            int modeByte2 = bytes[offset];
            offset += 1;
            parserContext = initialiseMode2(modeByte1, modeByte2);
        }
        byte[] bitVector = new byte[0];
        if ((matrixSize > 0) && parserContext.isSparseMode()) {
            bitVector = parseBitVector(bytes, offset, matrixSize);
            offset += bitVector.length;
        }
        double[][] values = new double[matrixSize][matrixSize];
        for (int r = 0; r < values.length; r++) {
            if (parserContext.getSdcc().getStandardDeviationFormat().equals(EncodingFormat.IEEE)) {
                values[r][r] =
                        PrimitiveConverter.toFloat64(
                                bytes,
                                offset,
                                parserContext.getSdcc().getStandardDeviationLength());
            } else {
                throw new UnsupportedOperationException(
                        "Parsing of ST 1201 encoded SDCC Standard Deviation values needs to be implemented");
            }
            offset += parserContext.getSdcc().getStandardDeviationLength();
        }

        if (parserContext.getSdcc().getCorrelationCoefficientLength() > 0) {
            FpEncoder correlationCoefficientDecoder =
                    new FpEncoder(
                            -1.0,
                            1.0,
                            parserContext.getSdcc().getCorrelationCoefficientLength(),
                            OutOfRangeBehaviour.Default);
            int bitVectorOffset = 7;
            int bitVectorValidByte = 0;
            for (int r = 0; r < matrixSize; r++) {
                for (int c = r + 1; c < matrixSize; c++) {
                    if (parserContext.isSparseMode()
                            && ((bitVector[bitVectorValidByte] & (1 << bitVectorOffset)) == 0)) {
                        values[r][c] = 0.0;
                    } else {
                        if (parserContext
                                .getSdcc()
                                .getCorrelationCoefficientFormat()
                                .equals(EncodingFormat.IEEE)) {
                            values[r][c] =
                                    PrimitiveConverter.toFloat64(
                                            bytes,
                                            offset,
                                            parserContext
                                                    .getSdcc()
                                                    .getCorrelationCoefficientLength());
                        } else {
                            values[r][c] = correlationCoefficientDecoder.decode(bytes, offset);
                        }
                        offset += parserContext.getSdcc().getCorrelationCoefficientLength();
                    }
                    bitVectorOffset -= 1;
                    if (bitVectorOffset < 0) {
                        bitVectorOffset = 7;
                        bitVectorValidByte += 1;
                    }
                    values[c][r] = values[r][c];
                }
            }
        }
        parserContext.getSdcc().setValues(values);
        return parserContext.getSdcc();
    }

    private byte[] parseBitVector(byte[] bytes, int offset, int matrixSize) {
        int numCoefficients = matrixSize * (matrixSize - 1);
        int numBits = numCoefficients / 2;
        int numBytes = (numBits + 7) / Byte.SIZE;
        byte[] bitVector = new byte[numBytes];
        for (int i = 0; i < bitVector.length; i++) {
            bitVector[i] = bytes[i + offset];
        }
        return bitVector;
    }

    private SDCCParserContext initialiseMode2(int modeByte1, int modeByte2)
            throws KlvParseException {
        // Mode 2 parse control
        SDCCParserContext parserContext = new SDCCParserContext();
        if (!isLastByte(modeByte2)) {
            throw new KlvParseException(
                    String.format(
                            "ST 1010 parsing only supports 1 and 2 byte mode selection, got 0x%02x, 0x%02x",
                            modeByte1 & 0xFF, modeByte2 & 0xFF));
        }
        parserContext.setSparseMode((modeByte1 & 0x20) == 0x20);
        int cf = (modeByte1 & 0x10) >> 4;
        SDCC sdcc = new SDCC();
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.getEncoding(cf));
        sdcc.setCorrelationCoefficientLength(modeByte1 & 0x0F);
        int sf = (modeByte2 & 0x10) >> 4;
        sdcc.setStandardDeviationFormat(EncodingFormat.getEncoding(sf));
        sdcc.setStandardDeviationLength(modeByte2 & 0x0F);
        parserContext.setSdcc(sdcc);
        return parserContext;
    }

    private SDCCParserContext initialiseMode1(int modeByte1) {
        // Mode 1 parse control
        SDCCParserContext parserContext = new SDCCParserContext();
        SDCC sdcc = new SDCC();
        sdcc.setStandardDeviationLength((modeByte1 & 0x70) >> 4);
        parserContext.setSparseMode((modeByte1 & 0x08) == 0x08);
        sdcc.setCorrelationCoefficientLength(modeByte1 & 0x07);
        sdcc.setCorrelationCoefficientFormat(EncodingFormat.ST1201);
        sdcc.setStandardDeviationFormat(getStandardDeviationFormat());
        parserContext.setSdcc(sdcc);
        return parserContext;
    }

    private static boolean isLastByte(int modeByte1) {
        return (modeByte1 & 0x80) == 0x00;
    }
}
