package org.jmisb.api.klv.st1206;

import java.time.LocalDateTime;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/**
 * Reference Frame Precision Time Stamp (ST1206 Item 23).
 *
 * <p>SAR coherent change product is formed from two complex SAR images collected at different
 * periods of time. The process of forming a SAR coherent change product involves registering a
 * complex SAR image to a reference complex SAR image collected at nearly the same geometry.
 * Typically, the reference SAR image is collected earlier in time, but this need not be the case.
 * Without loss of generality, assume the reference SAR image is collected at an earlier point in
 * time as compared to the current SAR image.
 *
 * <p>In the optimal case where both SAR images are collected at the exact same geometry, coherence
 * naturally degrades over time due to environmental effects such as wind or rain. Therefore, the
 * elapsed time between the reference SAR image and the current SAR image is of particular interest
 * to analysts. The reference frame time stamp provides the information necessary for analysts to
 * calculate the time delta between SAR images utilized to create the SAR coherent change product.
 *
 * <p>The Reference Frame Precision Time Stamp is expressed as a Precision Time Stamp defined in
 * MISB ST 0603. The Reference Frame Precision Time Stamp is a critical metadata component for all
 * SAR coherent change products as SARMI.
 */
public class ReferenceFramePrecisionTimeStamp extends ST0603TimeStamp
        implements ISARMIMetadataValue {
    /**
     * Create from value.
     *
     * @param microseconds Microseconds since the epoch
     */
    public ReferenceFramePrecisionTimeStamp(long microseconds) {
        super(microseconds);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array, 8 bytes length
     */
    public ReferenceFramePrecisionTimeStamp(byte[] bytes) {
        super(bytes);
        if (bytes.length < 8) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is an 8-byte unsigned int");
        }
    }

    /**
     * Create from {@code LocalDateTime}.
     *
     * @param dateTime The date and time
     */
    public ReferenceFramePrecisionTimeStamp(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public final String getDisplayName() {
        return "Reference Frame Precision Time Stamp";
    }

    @Override
    public byte[] getBytes() {
        return getBytesFull();
    }
}
