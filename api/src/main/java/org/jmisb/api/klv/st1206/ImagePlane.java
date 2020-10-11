package org.jmisb.api.klv.st1206;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Image Plane (ST 1206 Item 4).
 *
 * <p>SAR images are typically formed either on the slant plane - the plane containing the ground-
 * track vector and the line-of-sight vector to the SRP, or onto the ground plane - the plane
 * tangential to the ellipsoid at the SRP.
 *
 * <p>The selection of the imaging plane may impact the accuracy of distance measurements. SAR
 * images formed in the slant plane experience a foreshortening in the range dimension, whereby
 * objects projected onto the slant plane will appear shorter than when projected onto the ground
 * plane. As such, the plane in which the SARMI data is formed is defined as 0 for Slant Plane, 1
 * for Ground Plane, and 2 for Other (self-defined plane).
 *
 * <p>See ST 1206 6.2.4 for more information.
 */
public class ImagePlane extends SARMIEnumeration {
    static final Map<Integer, String> DISPLAY_VALUES =
            Arrays.stream(
                            new Object[][] {
                                {0, "Slant Plane"},
                                {1, "Ground Plane"},
                                {2, "Other (self defined) Plane"}
                            })
                    .collect(Collectors.toMap(kv -> (Integer) kv[0], kv -> (String) kv[1]));

    /**
     * Create from value.
     *
     * @param status The value of the image plane enumeration.
     */
    public ImagePlane(byte status) {
        super(status);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes The byte array of length 1
     */
    public ImagePlane(byte[] bytes) {
        super(bytes);
    }

    /**
     * Get the image plane.
     *
     * @return The value as an enumeration value (0 for slant, 1 for ground, 2 for other).
     */
    public byte getImagePlane() {
        return value;
    }

    @Override
    Map<Integer, String> getDisplayValues() {
        return DISPLAY_VALUES;
    }

    @Override
    public String getDisplayName() {
        return "Image Plane";
    }
}
