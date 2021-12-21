package org.jmisb.st1010;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.INestedKlvValue;

/**
 * Standard Deviation and Correlation Coefficient Matrix.
 *
 * <p>An instance of this class represents the "full" version of the Standard Deviation and Cross
 * Correlation (SDCC) matrix. The standard deviation values (error estimates) are on the main
 * diagonal. The off-diagonal values are the estimates of correlation between the errors on
 * corresponding rows / columns. The SDCC is symmetrical about the main diagonal.
 *
 * <p>As an example, consider the case where a local set (such as ST 0601) has Sensor Latitude and
 * Sensor Longitude information, and there is an estimate of the error (at the one
 * standard-deviation level) - say based on the GPS sensor performance and satellite geometry
 * (Horizontal Dilution of Precision - HDOP). In this situation, the latitude and longitude parts of
 * the position error are probably fairly closely related (and would have cross-correlation values
 * approaching 1). SDCC provides the structure to represent this information.
 */
public class SDCC implements INestedKlvValue {
    private int stdDevLength = Float.BYTES;
    private int corrCoefLength = 3;
    private EncodingFormat stdDevFormat = EncodingFormat.IEEE;
    private EncodingFormat corrCoefFormat = EncodingFormat.ST1201;
    private double[][] values = new double[0][0];

    /**
     * Constructor.
     *
     * <p>This will produce an empty SDCC, initialised to use four-byte IEEE format for standard
     * deviations, and three-byte ST 1201 for correlation coefficients.
     */
    public SDCC() {}

    /**
     * Copy constructor.
     *
     * @param other the object to copy from.
     */
    public SDCC(SDCC other) {
        this.stdDevLength = other.getStandardDeviationLength();
        this.corrCoefLength = other.getCorrelationCoefficientLength();
        this.stdDevFormat = other.getStandardDeviationFormat();
        this.corrCoefFormat = other.getCorrelationCoefficientFormat();
        this.values = other.getValues().clone();
    }

    /**
     * Get the encoded standard deviation length.
     *
     * @return the length of each encoded value.
     */
    public int getStandardDeviationLength() {
        return stdDevLength;
    }

    /**
     * Set the length of each encoded standard deviation value.
     *
     * <p>Note that the format needs to be consistent with the standard deviation format. In
     * particular, {@code EncodingFormat.IEEE} requires that the length be 2, 4 or 8 bytes, while
     * {@code EncodingFormat.ST1201} requires that the length be consistent with the IMAP encoding.
     *
     * <p>All standard deviation values in an SDCC instance need to have the same length.
     *
     * @param length the length in bytes
     */
    public void setStandardDeviationLength(int length) {
        this.stdDevLength = length;
    }

    /**
     * Get the encoded correlation coefficient value length.
     *
     * @return the length of each encoded value.
     */
    public int getCorrelationCoefficientLength() {
        return corrCoefLength;
    }

    /**
     * Set the length of each encoded correlation coefficient value.
     *
     * <p>Note that the format needs to be consistent with the correlation coefficient format. In
     * particular, {@code EncodingFormat.IEEE} requires that the length be 2, 4 or 8 bytes, while
     * {@code EncodingFormat.ST1201} requires that the length be consistent with the IMAP encoding.
     *
     * <p>All correlation coefficients in an SDCC instance need to have the same length.
     *
     * @param length the length in bytes
     */
    public void setCorrelationCoefficientLength(int length) {
        this.corrCoefLength = length;
    }

    /**
     * Get the standard deviation encoding format.
     *
     * @return the format for encoding standard deviations.
     */
    public EncodingFormat getStandardDeviationFormat() {
        return stdDevFormat;
    }

    /**
     * Set the standard deviation encoding format.
     *
     * <p>Note that the format needs to be consistent with the standard deviation length.
     *
     * <p>Also, use of {@code EncodingFormat.ST1201} requires specifying the IMAP encoding for the
     * standard deviation value.
     *
     * @param format the format to use for encoding standard deviation values.
     */
    public void setStandardDeviationFormat(EncodingFormat format) {
        this.stdDevFormat = format;
    }

    /**
     * Get the correlation coefficient encoding format.
     *
     * @return the format for encoding correlation coefficients.
     */
    public EncodingFormat getCorrelationCoefficientFormat() {
        return corrCoefFormat;
    }

    /**
     * Set the correlation coefficient encoding format.
     *
     * <p>Note that the format needs to be consistent with the correlation coefficient length.
     *
     * @param format the format to use for encoding correlation coefficients.
     */
    public void setCorrelationCoefficientFormat(EncodingFormat format) {
        this.corrCoefFormat = format;
    }

    /**
     * Get the reconstructed matrix values.
     *
     * <p>The matrix will have the standard deviations on the main diagonal, and the correlation
     * coefficients symmetrical about the main diagonal. Any sparse entries from the encoded format
     * will be filled with zeros.
     *
     * @return the reconstructed matrix.
     */
    public double[][] getValues() {
        return values.clone();
    }

    /**
     * Set the matrix values.
     *
     * <p>The matrix should have the standard deviations on the main diagonal, and the correlation
     * coefficients symmetrical about the main diagonal.
     *
     * @param values the matrix values.
     */
    public void setValues(double[][] values) {
        this.values = values.clone();
    }

    @Override
    public Set<SDCCValueIdentifierKey> getIdentifiers() {
        SortedSet<SDCCValueIdentifierKey> identifiers = new TreeSet<>();
        for (int rowNumber = 0; rowNumber < values.length; rowNumber++) {
            for (int columnNumber = 0; columnNumber < values[rowNumber].length; columnNumber++) {
                identifiers.add(
                        new SDCCValueIdentifierKey(
                                rowNumber, columnNumber, values[rowNumber].length));
            }
        }
        return identifiers;
    }

    @Override
    public SDCCValueWrap getField(IKlvKey identifier) {
        SDCCValueIdentifierKey key = (SDCCValueIdentifierKey) identifier;
        double value = values[key.getRow()][key.getColumn()];
        return new SDCCValueWrap(key.getRow(), key.getColumn(), value);
    }
}
