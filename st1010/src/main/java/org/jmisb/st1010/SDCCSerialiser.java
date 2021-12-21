package org.jmisb.st1010;

import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.Ber;
import org.jmisb.api.klv.BerEncoder;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Serialization support for {@link SDCC} instances.
 *
 * <p>ST 1010 has two encoding formats, referred to as "Parse Modes". Mode 1 is more compact but
 * Mode 2 parse control is more flexible, and is required by some standards, and therefore is the
 * default. Use {@link setPreferMode1} to select which mode is preferred. There are modes and bit
 * lengths that cannot be supported in Mode 1 control. In particular, specifying lengths &gt; 7, or
 * {@code EncodingFormat.IEEE} for correlation coefficients will result in Mode 2 being used
 * (irrespective of the preferred mode).
 *
 * <p>ST 1010 also supports not encoding correlation coefficients that are 0.0 (i.e. no
 * correlation), using "sparse mode". This requires adding a bit mask which is not always required.
 * By default, sparse mode will be used if it is more efficient (i.e. would save bytes overall). It
 * is also possible to disable use of sparse mode using {@link setSparseEnabled}. The result is that
 * sparse mode is only used if both it would be more efficient and it is not disabled.
 */
public class SDCCSerialiser {
    private boolean preferMode1 = false;
    private boolean sparseEnabled = true;
    private static final byte NOT_FINAL_BYTE = (byte) (1 << 7);
    private static final byte FINAL_BYTE = 0;
    private static final byte MODE1_SLEN_SHIFT = 4;
    private static final byte MODE1_SPARSE_SHIFT = 3;
    private static final byte MODE2_SPARSE_SHIFT = 5;
    private static final byte CORRELATION_COEFFICIENT_FORMAT_SHIFT = 4;
    private static final byte STANDARD_DEVIATION_FORMAT_SHIFT = 4;
    private static final int MAX_MODE1_CORRELATION_COEFFICIENT_LEN = 7;
    private static final int MAX_MODE1_STANDARD_DEVIATION_LEN = 7;

    /**
     * Get whether encoding should prefer Mode 1 parse control.
     *
     * @return true if encoding will prefer Mode 1 parse control.
     */
    public boolean isPreferMode1() {
        return preferMode1;
    }

    /**
     * Get whether sparse representation is allowed.
     *
     * <p>SDCC can reduce space by omitting the encoding of uncorrelated variable covariances (i.e.
     * 0 values). See MISB ST 1010.3 for more information.
     *
     * @return true if sparse encoding is enabled, otherwise false.
     */
    public boolean isSparseEnabled() {
        return sparseEnabled;
    }

    /**
     * Set whether sparse representation is allowed.
     *
     * <p>SDCC can reduce space by omitting the encoding of uncorrelated variable covariances (i.e.
     * 0 values). See MISB ST 1010.3 for more information.
     *
     * <p>When serializing, sparse representation will be used if it is enabled and would be more
     * efficient than serializing the 0 values (i.e. save bytes). If it is not enabled (set to
     * false), full values will be used. If it is not more efficient, full values will be used.
     *
     * <p>Sparse representation is enabled by default.
     *
     * @param sparseEnabled true to enable sparse representation, false to disable sparse
     *     representation.
     */
    public void setSparseEnabled(boolean sparseEnabled) {
        this.sparseEnabled = sparseEnabled;
    }

    /**
     * Set whether encoding should prefer Mode 1 parse control.
     *
     * <p>In general, Mode 2 parse control is more flexible, and is required by some standards, and
     * therefore is the default. In addition, there are modes and bit lengths that cannot be
     * supported in Mode 1 control. In particular, specifying lengths &gt; 7, or {@code
     * EncodingFormat.IEEE} for correlation coefficients will result in Mode 2 being used,
     * irrespective of this setting.
     *
     * <p>If this option is enabled, and the required encoding can work with Mode 1, it will be
     * used.
     *
     * @param preferMode1 true to enable Mode 1 parse control as an option, false to disable Mode 1
     *     parse control.
     */
    public void setPreferMode1(boolean preferMode1) {
        this.preferMode1 = preferMode1;
    }

    /**
     * Serialize a Standard Deviation and Cross Correlation instance to bytes.
     *
     * @param sdcc the matrix of values to encode
     * @return the corresponding byte array representation
     */
    public byte[] encode(SDCC sdcc) {
        if (sdcc.getValues().length != sdcc.getValues()[0].length) {
            throw new IllegalArgumentException("SDCC requires a square input array");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        byte[] matrixBytes = BerEncoder.encode(sdcc.getValues().length, Ber.OID);
        arrayBuilder.append(matrixBytes);
        boolean usingSparse = isSparseEnabled() && sparseWouldSaveSpace(sdcc);
        arrayBuilder.append(getModeBytes(sdcc, usingSparse));
        byte[] bitVector = new byte[0];
        if (usingSparse) {
            bitVector = new byte[getBitVectorLength(sdcc)];
            // The values will get filled in as we work through the coefficients
            arrayBuilder.append(bitVector);
        }
        for (int r = 0; r < sdcc.getValues().length; r++) {
            // TODO: ST1201
            double standardDeviation = sdcc.getValues()[r][r];
            switch (sdcc.getStandardDeviationLength()) {
                case 4:
                    arrayBuilder.append(
                            PrimitiveConverter.float32ToBytes((float) standardDeviation));
                    break;
                case 8:
                    arrayBuilder.append(PrimitiveConverter.float64ToBytes(standardDeviation));
                    break;
                default:
                    throw new IllegalArgumentException(
                            "SDCC IEEE format length 4 and 8 is currently supported. More work required for 2 byte. Other lengths not sensible.");
            }
        }
        FpEncoder correlationCoefficientEncoder =
                new FpEncoder(
                        -1.0,
                        1.0,
                        sdcc.getCorrelationCoefficientLength(),
                        OutOfRangeBehaviour.Default);
        int bitVectorOffset = 7;
        int bitVectorValidByte = 0;
        for (int r = 0; r < sdcc.getValues().length; r++) {
            for (int c = r + 1; c < sdcc.getValues().length; c++) {

                double coef = sdcc.getValues()[r][c];
                if (usingSparse) {
                    if (coef != 0.0) {
                        bitVector[bitVectorValidByte] |= 1 << bitVectorOffset;
                    }
                    bitVectorOffset -= 1;
                    if (bitVectorOffset < 0) {
                        bitVectorValidByte += 1;
                        bitVectorOffset = 7;
                    }
                    if (coef == 0.0) {
                        continue;
                    }
                }
                if (sdcc.getCorrelationCoefficientFormat().equals(EncodingFormat.IEEE)) {
                    switch (sdcc.getCorrelationCoefficientLength()) {
                        case 4:
                            arrayBuilder.append(PrimitiveConverter.float32ToBytes((float) coef));
                            break;
                        case 8:
                            arrayBuilder.append(PrimitiveConverter.float64ToBytes(coef));
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "SDCC IEEE format length 4 and 8 is currently supported. More work required for 2 byte. Other lengths not sensible.");
                    }
                } else {
                    arrayBuilder.append(correlationCoefficientEncoder.encode(coef));
                }
            }
        }
        return arrayBuilder.toBytes();
    }

    private byte[] getModeBytes(SDCC sdcc, boolean usingSparse) {
        if (isPreferMode1() && isMode1Compatible(sdcc)) {
            return getModeBytesMode1(sdcc, usingSparse);
        }
        return getModeBytesMode2(sdcc, usingSparse);
    }

    private byte[] getModeBytesMode1(SDCC sdcc, boolean usingSparse) {
        byte[] parseControl = new byte[1];
        parseControl[0] = FINAL_BYTE;
        byte slen = (byte) (sdcc.getStandardDeviationLength() & 0x07);
        parseControl[0] |= (slen << MODE1_SLEN_SHIFT);
        if (usingSparse) {
            parseControl[0] |= 1 << MODE1_SPARSE_SHIFT;
        }
        byte clen = (byte) (sdcc.getCorrelationCoefficientLength() & 0x07);
        parseControl[0] |= clen;
        return parseControl;
    }

    private byte[] getModeBytesMode2(SDCC sdcc, boolean usingSparse) {
        byte[] parseControl = new byte[2];
        parseControl[0] = NOT_FINAL_BYTE;
        parseControl[1] = FINAL_BYTE;
        int numCoefficients = getNumCoefficients(sdcc);
        if (numCoefficients != 0) {
            if (usingSparse) {
                parseControl[0] |= 1 << MODE2_SPARSE_SHIFT;
            }
            parseControl[0] |=
                    (sdcc.getCorrelationCoefficientFormat().getValue()
                            << CORRELATION_COEFFICIENT_FORMAT_SHIFT);
            byte clen = (byte) (sdcc.getCorrelationCoefficientLength() & 0x0F);
            parseControl[0] |= clen;
        }
        parseControl[1] |=
                (sdcc.getStandardDeviationFormat().getValue() << STANDARD_DEVIATION_FORMAT_SHIFT);
        byte slen = (byte) (sdcc.getStandardDeviationLength() & 0x0F);
        parseControl[1] |= slen;
        return parseControl;
    }

    private int getNumCoefficients(SDCC sdcc) {
        return (sdcc.getValues().length * (sdcc.getValues().length - 1));
    }

    private boolean sparseWouldSaveSpace(SDCC sdcc) {
        int numZeros = 0;
        for (int r = 0; r < sdcc.getValues().length; r++) {
            for (int c = r + 1; c < sdcc.getValues().length; c++) {
                if (sdcc.getValues()[r][c] == 0.0) {
                    numZeros += 1;
                }
            }
        }
        return (sdcc.getCorrelationCoefficientLength() * numZeros > getBitVectorLength(sdcc));
    }

    /**
     * Get the length of the BitVector.
     *
     * @param sdcc the SDCC to calculate for
     * @return the length in bytes
     */
    private int getBitVectorLength(SDCC sdcc) {
        int numBits = getNumCoefficients(sdcc) / 2;
        int numBytes = (numBits + 7) / Byte.SIZE;
        return numBytes;
    }

    private boolean isMode1Compatible(SDCC sdcc) {
        if (sdcc.getCorrelationCoefficientFormat().equals(EncodingFormat.IEEE)) {
            return false;
        }
        if (sdcc.getStandardDeviationFormat().equals(EncodingFormat.ST1201)) {
            return false;
        }
        if (sdcc.getCorrelationCoefficientLength() > MAX_MODE1_CORRELATION_COEFFICIENT_LEN) {
            return false;
        }
        if (sdcc.getStandardDeviationLength() > MAX_MODE1_STANDARD_DEVIATION_LEN) {
            return false;
        }
        return true;
    }
}
