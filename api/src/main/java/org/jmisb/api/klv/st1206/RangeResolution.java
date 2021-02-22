package org.jmisb.api.klv.st1206;

/**
 * Range Resolution (ST 1206 Item 5).
 *
 * <p>Range resolution is a measurement of spatial resolution, where two targets in close proximity
 * in range may be resolved from one another. Theoretical resolution is important as it is an
 * indicator of the maximum image quality.
 *
 * <p>Most literature sources define the range resolution in the slant plane. Range resolution in
 * the slant plane is defined by the RF bandwidth of the waveform, B, and is independent of sensor
 * range.
 *
 * <p>Both range and cross-range resolution measurements include amplitude weighting. Also, even
 * though both measurements allow 0 as a valid value, it should not be used as it is meaningless in
 * practical applications.
 */
public class RangeResolution extends AbstractResolutionDistance {

    /**
     * Create from value.
     *
     * @param resolution resolution in metres.
     */
    public RangeResolution(double resolution) {
        super(resolution);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public RangeResolution(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Range Resolution";
    }
}
