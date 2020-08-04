package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.shared.EncodingMode;

/**
 * Boundary Top Left Latitude Offset (ST0903 VTarget Pack Tag 13).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Latitude offset for top left corner of target bounding box from Frame Center Latitude (MISB ST
 * 0601), based on WGS84 ellipsoid. Use with Frame Center Latitude. Added to the Frame Center
 * Latitude from the parent ST 0601 packet to determine the Latitude of the top left corner of the
 * target bounding box. Convert both data items to decimal representation prior to addition to
 * determine the actual measured or calculated Motion Imagery bounding box corner location. Bounding
 * box corners that lie above the horizon do not correspond to points on the earth. Bounding box
 * corners may lie outside of the mapped range. Both cases should either not be reported or be
 * reported as an “error”. Conversion: IMAPB (-19.2, 19.2, 3).
 *
 * <p>Valid Values: The set of real numbers from -19.2 to 19.2 inclusive.
 *
 * </blockquote>
 */
public class BoundaryTopLeftLatOffset extends AbstractTargetLocationOffset {

    /**
     * Create from value.
     *
     * @param offset latitude offset in degrees. Valid range is [-19.2, 19.2]
     */
    public BoundaryTopLeftLatOffset(double offset) {
        super(offset);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note: this constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @deprecated use {@link #BoundaryTopLeftLatOffset(byte[], EncodingMode)} to indicate the
     *     encoding.
     */
    @Deprecated
    public BoundaryTopLeftLatOffset(byte[] bytes) {
        super(bytes);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 3-byte IMAPB in ST0903.4. Earlier versions used a variable
     * length (1-3 bytes) byte array to represent an integer in the range [-2^23-1, 2^23-1]that was
     * then mapped into the range [-19.2,19.2]. The three byte case could potentially represent
     * either kind of formatting, and which formatting applies can only be determined from the
     * version in the parent {@link org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code
     * compatibilityMode} parameter determines whether to parse using the legacy encoding or current
     * encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public BoundaryTopLeftLatOffset(byte[] bytes, EncodingMode encodingMode) {
        super(bytes, encodingMode);
    }

    @Override
    public final String getDisplayName() {
        return "Boundary Top Left Lat Offset";
    }
}
