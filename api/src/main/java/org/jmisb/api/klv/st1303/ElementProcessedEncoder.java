package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * MISB ST 1201 Element Processing Encoder.
 *
 * <p>In formatting floating point Elements into KLV, MISB ST 1201 reduces the number of bytes per
 * Element by mapping floating point values to integers. There are two methods specified in ST 1201
 * for mapping the values: IMAPA and IMAPB. IMAPA parameters include minimum, maximum and a
 * precision values to compute the length of a mapped integer value; IMAPB parameters include
 * minimum, maximum and a pre-computed length value.
 *
 * <p>The value of EBytes along with the Minimum and Maximum values form the IMAPB mapping
 * parameters for each value in the Array: IMAPB(Minimum, Maximum, EBytes). Because the MDAP already
 * specifies the length of an Element in the Array, ST 1303 uses the IMAPB method of MISB ST 1201.
 */
public class ElementProcessedEncoder {

    private final double min;
    private final double max;
    private final int bytesPerEncodedValue;
    private final FpEncoder st1201encoder;

    /**
     * Constructor.
     *
     * <p>The encoder is set up with the standard IMAPB parameters, which cannot be changed. Create
     * a new instance if the parameters need to change.
     *
     * @param min the minimum value for the encoding (same as {@code a} parameter in IMAPB).
     * @param max the maximum value for the encoding (same as {@code b} parameter in IMAPB).
     * @param ebytes the number of bytes to encode to (same as {@code L} parameter in IMAPB).
     */
    public ElementProcessedEncoder(double min, double max, int ebytes) {
        this.min = min;
        this.max = max;
        this.bytesPerEncodedValue = ebytes;
        st1201encoder = new FpEncoder(min, max, ebytes);
    }

    /**
     * Constructor.
     *
     * <p>The encoder is set up with the standard IMAPA parameters, which cannot be changed. Create
     * a new instance if the parameters need to change.
     *
     * @param min the minimum value for the encoding (same as {@code a} parameter in IMAPA).
     * @param max the maximum value for the encoding (same as {@code b} parameter in IMAPA).
     * @param resolution the resolution to encode to (same as {@code g} parameter in IMAPA).
     */
    public ElementProcessedEncoder(double min, double max, double resolution) {
        this.min = min;
        this.max = max;
        st1201encoder = new FpEncoder(min, max, resolution);
        this.bytesPerEncodedValue = st1201encoder.getFieldLength();
    }

    /**
     * Constructor.
     *
     * <p>The encoder is set up with the standard IMAPB parameters, which cannot be changed. Create
     * a new instance if the parameters need to change.
     *
     * @param min the minimum value for the encoding (same as {@code a} parameter in IMAPB).
     * @param max the maximum value for the encoding (same as {@code b} parameter in IMAPB).
     * @param ebytes the number of bytes to encode to (same as {@code L} parameter in IMAPB).
     */
    public ElementProcessedEncoder(float min, float max, int ebytes) {
        this.min = min;
        this.max = max;
        this.bytesPerEncodedValue = ebytes;
        st1201encoder = new FpEncoder(min, max, ebytes);
    }

    /**
     * Encode a one dimensional double array to a Multi-Dimensional Array Pack using ST1201 Element
     * Processed Encoding.
     *
     * <p>This encodes each value into a ST1201 IMAPB encoded element, and serialises the 1D {@code
     * data} matrix to a byte array.
     *
     * @param data the array of floating point ({@code double}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(double[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // num bytes for each element (E_bytes)
                        .appendAsOID(this.bytesPerEncodedValue)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.ST1201.getCode())
                        // array processing algorithm support (APAS) values - min and max
                        .appendAsFloat64Primitive(this.min)
                        .appendAsFloat64Primitive(this.max);
        // Array Of Elements
        for (int r = 0; r < data.length; ++r) {
            builder.append(st1201encoder.encode(data[r]));
        }
        return builder.toBytes();
    }

    /**
     * Encode a one dimensional float array to a Multi-Dimensional Array Pack using ST1201 Element
     * Processed Encoding.
     *
     * <p>This encodes each value into a ST1201 IMAPB encoded element, and serialises the 1D {@code
     * data} matrix to a byte array.
     *
     * @param data the array of floating point ({@code float}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(float[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // num bytes for each element (E_bytes)
                        .appendAsOID(this.bytesPerEncodedValue)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.ST1201.getCode())
                        // array processing algorithm support (APAS) values - min and max
                        .appendAsFloat32Primitive((float) this.min)
                        .appendAsFloat32Primitive((float) this.max);
        // Array Of Elements
        for (int r = 0; r < data.length; ++r) {
            builder.append(st1201encoder.encode(data[r]));
        }
        return builder.toBytes();
    }

    /**
     * Encode a two dimensional double array to a Multi-Dimensional Array Pack using ST1201 Element
     * Processed Encoding.
     *
     * <p>This encodes each value into a ST1201 IMAPB encoded element, and serialises the 2D {@code
     * data} matrix to a byte array.
     *
     * @param data the array of arrays of floating point ({@code double}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(double[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(2)
                        // dim_1
                        .appendAsOID(data.length)
                        // dim_2
                        .appendAsOID(data[0].length)
                        // num bytes for each element (E_bytes)
                        .appendAsOID(this.bytesPerEncodedValue)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.ST1201.getCode())
                        // array processing algorithm support (APAS) values - min and max
                        .appendAsFloat64Primitive(this.min)
                        .appendAsFloat64Primitive(this.max);
        // Array Of Elements
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                builder.append(st1201encoder.encode(data[r][c]));
            }
        }
        return builder.toBytes();
    }

    /**
     * Encode a two dimensional float array to a Multi-Dimensional Array Pack using ST1201 Element
     * Processed Encoding.
     *
     * <p>This encodes each value into a ST1201 IMAPB encoded element, and serialises the 2D {@code
     * data} matrix to a byte array.
     *
     * @param data the array of arrays of floating point ({@code float}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(float[][] data) throws KlvParseException {
        if ((data.length < 1) || (data[0].length < 1)) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(2)
                        // dim_1
                        .appendAsOID(data.length)
                        // dim_2
                        .appendAsOID(data[0].length)
                        // num bytes for each element (E_bytes)
                        .appendAsOID(this.bytesPerEncodedValue)
                        // array processing algorithm (APA)
                        .appendAsOID(ArrayProcessingAlgorithm.ST1201.getCode())
                        // array processing algorithm support (APAS) values - min and max
                        .appendAsFloat32Primitive((float) this.min)
                        .appendAsFloat32Primitive((float) this.max);
        for (int r = 0; r < data.length; ++r) {
            for (int c = 0; c < data[r].length; ++c) {
                builder.append(st1201encoder.encode(data[r][c]));
            }
        }
        return builder.toBytes();
    }
}
