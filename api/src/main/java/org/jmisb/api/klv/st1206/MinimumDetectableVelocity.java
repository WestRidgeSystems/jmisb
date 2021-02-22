package org.jmisb.api.klv.st1206;

import org.jmisb.api.klv.st1201.FpEncoder;

/**
 * Minimum Detectable Velocity (ST 1206 Item 17).
 *
 * <p>SARMI is essentially a form of Ground Moving Target Indicators (GMTI) displayed on full-
 * resolution Endo-clutter SAR frames rather than the classic 'blips' on a range-Doppler image. The
 * GMTI radial velocity is the projection of the mover's velocity vector upon the radar's line-of-
 * sight vector to the target. Hence, the radial velocity is not only a function of target velocity,
 * but also scene geometry.
 *
 * <p>The Minimum Detectable Velocity (MDV) is defined as the radial velocity when a target located
 * at the antenna beam’s cross-range center line transcends from the Endo- clutter (visible SAR
 * image) boundary into Exo-clutter (not visible). Technically, the MDV could change within a SAR
 * image. However, for simplicity, assume the MDV is constant over the SAR coherent processing
 * interval. Estimation of the MDV using aforementioned SARMI metadata requires additional a priori
 * knowledge of sensor parameters. Hence, it is desirable to specify the MDV explicitly.
 */
public class MinimumDetectableVelocity implements ISARMIMetadataValue {

    protected static final double MIN_VAL = 0.0;
    protected static final double MAX_VAL = 100.0;
    protected static final int NUM_BYTES = 2;
    protected double value;

    /**
     * Create from value.
     *
     * <p>The valid range is 0 to 100 metres/second.
     *
     * @param mdv minimum detectable velocity in metres per second.
     */
    public MinimumDetectableVelocity(double mdv) {
        if (mdv < MIN_VAL || mdv > MAX_VAL) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s must be in range [%f, %f]",
                            this.getDisplayName(), MIN_VAL, MAX_VAL));
        }
        this.value = mdv;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from (2 bytes).
     */
    public MinimumDetectableVelocity(byte[] bytes) {
        if (bytes.length != NUM_BYTES) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s encoding is %d byte IMAPB", this.getDisplayName(), NUM_BYTES));
        }
        FpEncoder decoder = new FpEncoder(MIN_VAL, MAX_VAL, bytes.length);
        this.value = decoder.decode(bytes);
    }

    @Override
    public byte[] getBytes() {
        FpEncoder encoder = new FpEncoder(MIN_VAL, MAX_VAL, NUM_BYTES);
        return encoder.encode(this.value);
    }

    @Override
    public final String getDisplayableValue() {
        return String.format("%.3fm/s", value);
    }

    @Override
    public final String getDisplayName() {
        return "Minimum Detectable Velocity";
    }

    /**
     * Get the Transmit RF Bandwidth.
     *
     * @return bandwidth in Hertz
     */
    public double getBandwidth() {
        return value;
    }
}
