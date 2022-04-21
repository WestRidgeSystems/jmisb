package org.jmisb.st1601;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.st1201.FpEncoder;
import org.jmisb.api.klv.st1201.OutOfRangeBehaviour;
import org.jmisb.api.klv.st1303.ArrayProcessingAlgorithm;

/**
 * Correspondence Points – Latitude / Longitude / Elevation Standard Deviation and Correlation
 * Coefficients (ST 1601 Tag 10).
 *
 * <p>A geo-registration algorithm uses the Correspondence Points – Latitude / Longitude / Elevation
 * Standard Deviation and Correlation Coefficients MDARRAY item to define an extensible list of
 * standard deviations and correlations coefficients for tie points represented in geographic space.
 *
 * <p>The Value of Tag 10 is a two-dimensional Array with dimensions of up to six (6) by the number
 * of tie points (tp) defined in Tag 5’s value, i.e. one sigma value for each value in Tag 5’s array
 * and optionally Tag 8’s array, along with one rho value for each latitude/longitude pair in Tag
 * 5’s array and rho values for the correlations between latitude/elevation and longitude/elevation.
 *
 * <p>The first row is the standard deviation (sigma) value in metres for the latitude value of each
 * tie point. Note that the location is in degrees, but the standard deviation is in metres.
 *
 * <p>The second row is the standard deviation (sigma) value in metres for the longitude value of
 * each tie point. Note that the location is in degrees, but the standard deviation is in metres.
 *
 * <p>The third row is the cross correlation coefficient (rho) value for the latitude and longitude
 * for each tie point. values.
 *
 * <p>The fourth row (if present) is the standard deviation (sigma) value in metres for the
 * elevation value for each tie point.
 *
 * <p>The fifth row (if present) is the cross correlation coefficient (rho) value for the latitude
 * and elevation for each tie point.
 *
 * <p>The fifth row (if present) is the cross correlation coefficient (rho) value for the longitude
 * and elevation for each tie point.
 *
 * <p>See ST 1601.1 Section 6.3.10 for more information.
 *
 * <p>Although the item has SDCC in the name, it is not encoded as an ST 1010 SDCC pack.
 */
public class CorrespondencePointsLatLonElevSDCC implements IGeoRegistrationValue {

    private final double[][] values;
    private static final int NUM_DIMS = 2;
    private static final int NUM_BYTES_PER_ITEM = 3;
    private static final double LAT_LON_SIGMA_MIN = 0.0;
    private static final double LAT_LON_SIGMA_MAX = 650.0;
    private static final double ELEV_SIGMA_MIN = 0.0;
    private static final double ELEV_SIGMA_MAX = 1000.0;
    private static final double RHO_MIN = -1.0;
    private static final double RHO_MAX = 1.0;

    /**
     * Create from values.
     *
     * @param sdcc the 2D array of values for standard deviations and correlations coefficients
     */
    public CorrespondencePointsLatLonElevSDCC(double[][] sdcc) {
        values = sdcc.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     * @throws KlvParseException if parsing fails
     */
    public CorrespondencePointsLatLonElevSDCC(byte[] bytes) throws KlvParseException {
        int i = 0;
        try {
            BerField ndim = BerDecoder.decode(bytes, i, true);
            if (ndim.getValue() != NUM_DIMS) {
                throw new KlvParseException(
                        "Wrong dimensions for CorrespondencePointsLatLonElevSDCC decode");
            }
            i += ndim.getLength();
            BerField dim1 = BerDecoder.decode(bytes, i, true);
            int numRows = dim1.getValue();
            i += dim1.getLength();
            BerField dim2 = BerDecoder.decode(bytes, i, true);
            int numColumns = dim2.getValue();
            i += dim2.getLength();
            BerField ebytes = BerDecoder.decode(bytes, i, true);
            i += ebytes.getLength();
            int numBytesPerElement = ebytes.getValue();
            BerField apa = BerDecoder.decode(bytes, i, true);
            i += apa.getLength();
            if (apa.getValue() != ArrayProcessingAlgorithm.NaturalFormat.getCode()) {
                throw new KlvParseException(
                        "Wrong APA for CorrespondencePointsLatLonElevSDCC decode");
            }
            values = new double[numRows][numColumns];
            for (int r = 0; r < numRows; r++) {
                FpEncoder decoder;
                if ((r == 0) || (r == 1)) {
                    decoder =
                            new FpEncoder(
                                    LAT_LON_SIGMA_MIN,
                                    LAT_LON_SIGMA_MAX,
                                    numBytesPerElement,
                                    OutOfRangeBehaviour.Throw);
                } else if ((r == 3)) {
                    decoder =
                            new FpEncoder(
                                    ELEV_SIGMA_MIN,
                                    ELEV_SIGMA_MAX,
                                    numBytesPerElement,
                                    OutOfRangeBehaviour.Throw);
                } else {
                    decoder =
                            new FpEncoder(
                                    RHO_MIN,
                                    RHO_MAX,
                                    numBytesPerElement,
                                    OutOfRangeBehaviour.Throw);
                }
                for (int c = 0; c < numColumns; c++) {
                    values[r][c] = decoder.decode(bytes, i);
                    i += numBytesPerElement;
                }
            }
        } catch (java.lang.IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    @Override
    public byte[] getBytes() {
        if ((values.length < 3) || (values[0].length < 1)) {
            return new byte[0];
        }
        ArrayBuilder builder = new ArrayBuilder();
        FpEncoder latLonSigmaEncoder =
                new FpEncoder(
                        LAT_LON_SIGMA_MIN,
                        LAT_LON_SIGMA_MAX,
                        NUM_BYTES_PER_ITEM,
                        OutOfRangeBehaviour.Throw);
        FpEncoder elevSigmaEncoder =
                new FpEncoder(
                        ELEV_SIGMA_MIN,
                        ELEV_SIGMA_MAX,
                        NUM_BYTES_PER_ITEM,
                        OutOfRangeBehaviour.Throw);
        FpEncoder rhoEncoder =
                new FpEncoder(RHO_MIN, RHO_MAX, NUM_BYTES_PER_ITEM, OutOfRangeBehaviour.Throw);
        builder.appendAsOID(NUM_DIMS);
        builder.appendAsOID(values.length);
        builder.appendAsOID(values[0].length);
        builder.appendAsOID(NUM_BYTES_PER_ITEM);
        builder.appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (int tp = 0; tp < values[0].length; tp++) {
            builder.append(latLonSigmaEncoder.encode(values[0][tp]));
        }
        for (int tp = 0; tp < values[1].length; tp++) {
            builder.append(latLonSigmaEncoder.encode(values[1][tp]));
        }
        for (int tp = 0; tp < values[2].length; tp++) {
            builder.append(rhoEncoder.encode(values[2][tp]));
        }
        if (values.length == 6) {
            for (int tp = 0; tp < values[3].length; tp++) {
                builder.append(elevSigmaEncoder.encode(values[3][tp]));
            }
            for (int tp = 0; tp < values[4].length; tp++) {
                builder.append(rhoEncoder.encode(values[4][tp]));
            }
            for (int tp = 0; tp < values[5].length; tp++) {
                builder.append(rhoEncoder.encode(values[5][tp]));
            }
        }
        return builder.toBytes();
    }

    @Override
    public final String getDisplayableValue() {
        return "[Coefficients]";
    }

    @Override
    public final String getDisplayName() {
        return "Correspondence Points – Latitude / Longitude / Elevation Standard Deviation & Correlation Coefficients";
    }

    /**
     * Get the Correspondence Point Standard Deviation and Correlation Coefficients.
     *
     * @return correspondence point standard deviation and correlation coefficients.
     */
    public double[][] getCoefficients() {
        return values.clone();
    }
}
