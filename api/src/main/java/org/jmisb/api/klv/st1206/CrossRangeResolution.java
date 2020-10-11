package org.jmisb.api.klv.st1206;

/**
 * Range Resolution (ST 1206 Item 6).
 *
 * <p>Cross-range resolution is a measurement of spatial resolution where two targets in close
 * proximity in cross-range may be resolved from one another. The cross-range resolution is range
 * dependent.
 *
 * <p>Both range and cross-range resolution measurements include amplitude weighting. Also, even
 * though both measurements allow 0 as a valid value, it should not be used as it is meaningless in
 * practical applications.
 */
public class CrossRangeResolution extends AbstractResolutionDistance {

    /**
     * Create from value.
     *
     * @param resolution resolution in metres.
     */
    public CrossRangeResolution(double resolution) {
        super(resolution);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public CrossRangeResolution(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Cross Range Resolution";
    }
}
