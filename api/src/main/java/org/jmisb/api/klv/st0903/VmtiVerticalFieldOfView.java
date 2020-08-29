package org.jmisb.api.klv.st0903;

import org.jmisb.api.klv.st0903.shared.EncodingMode;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;

/**
 * Vertical Field Of View (ST0903 VMTI Local Set Item 12 and VTrackItem Pack Item 23).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Vertical field of view of imaging sensor input to VMTI process. Required only if VMTI process
 * operates on an imaging sensor different from that described by the parent MISB ST 0601 LS ;
 * otherwise, the ST 0601 LS provides its Tag 17 – VFOV value. Can use with VFOV (Tag 17) from ST
 * 0601 to scale VMTI column, row coordinates. Typically required only to account for aspect ratio
 * variation.
 *
 * <p>Valid Values: The set of real numbers from 0 to 180 inclusive.
 *
 * </blockquote>
 */
public class VmtiVerticalFieldOfView extends VmtiFieldOfView
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    /**
     * Create from value.
     *
     * @param fov horizontal field of view in degrees.
     */
    public VmtiVerticalFieldOfView(double fov) {
        super(fov);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>Note this constructor only supports ST0903.4 and later.
     *
     * @param bytes Encoded byte array
     * @deprecated use {@link #VmtiVerticalFieldOfView(byte[], EncodingMode)} instead to specify the
     *     encoding mode
     */
    @Deprecated
    public VmtiVerticalFieldOfView(byte[] bytes) {
        this(bytes, EncodingMode.IMAPB);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 2-byte IMAPB in ST0903.4. Earlier versions used a two-byte
     * unsigned integer structure in the range [0, 2^16-1]that was then mapped into the range [0,
     * 180.0] degrees. Which formatting applies can only be determined from the ST0903 version in
     * this {@link org.jmisb.api.klv.st0903.VmtiLocalSet}. The {@code compatibilityMode} parameter
     * determines whether to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public VmtiVerticalFieldOfView(byte[] bytes, EncodingMode encodingMode) {
        super(bytes, encodingMode);
    }

    @Override
    public final String getDisplayName() {
        return "Vertical Field of View";
    }
}
