package org.jmisb.api.klv.st1206;

/**
 * Cross-Range Image Plane Pixel Size (ST 1206 Item 8).
 *
 * <p>Knowledge of the pixel dimensions and the number of pixels traversed enables the measurement
 * of the Euclidean distance between any two arbitrary points in a SAR image. Pixel dimensions are
 * specified in the range and cross-range directions of an image plane. The pixel size is a relative
 * quantity that reflects the amount of oversampling the image undergoes during image formation. As
 * in the case of range resolution, the range image plane pixel size in the slant plane is shorter
 * than the range image plane pixel size in the ground plane by a factor given by the inverse of the
 * cosine of the grazing angle. The cross-range image plane pixel size reflects the size of a pixel
 * in the SAR image in the cross-range dimension.
 *
 * <p>The ratio of range / cross-range image plane pixel size to range / cross-range resolution sans
 * amplitude weights is the oversampling ratio. Also, even though both measurements allow 0 as a
 * valid value, it should not be used as it is meaningless in practical applications.
 */
public class CrossRangeImagePlanePixelSize extends AbstractPixelSizeDistance {

    /**
     * Create from value.
     *
     * @param pixelSize pixel size in metres.
     */
    public CrossRangeImagePlanePixelSize(double pixelSize) {
        super(pixelSize);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public CrossRangeImagePlanePixelSize(byte[] bytes) {
        super(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Cross-Range Image Plane Pixel Size";
    }
}
