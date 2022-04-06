package org.jmisb.st0903;

import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.IVTrackItemMetadataValue;

/**
 * Horizontal Field Of View (ST0903 VMTI Local Set Item 11 and VTrackItem Pack Item 22).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Horizontal field of view of imaging sensor input to the VMTI process. Required only if VMTI
 * process operates on an imaging sensor different than that described by the parent MISB ST 0601
 * LS; otherwise, the ST 0601 LS provides its Tag 16 – HFOV value. Can use HFOV (Tag 16) from ST
 * 0601 to scale VMTI column, row coordinates.
 *
 * <p>Valid Values: The set of real numbers from 0 to 180 inclusive.
 *
 * </blockquote>
 */
public class VmtiHorizontalFieldOfView extends VmtiFieldOfView
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {

    /**
     * Create from value.
     *
     * @param fov horizontal field of view in degrees.
     */
    public VmtiHorizontalFieldOfView(double fov) {
        super(fov);
    }

    /**
     * Create from encoded bytes.
     *
     * <p>ST0903 changed the encoding to 2-byte IMAPB in ST0903.4. Earlier versions used a two-byte
     * unsigned integer structure in the range [0, 2^16-1] that was then mapped into the range [0,
     * 180.0] degrees. Which formatting applies can only be determined from the ST0903 version in
     * this {@link org.jmisb.st0903.VmtiLocalSet}. The {@code encodingMode} parameter determines
     * whether to parse using the legacy encoding or current encoding.
     *
     * <p>Note that this only affects parsing. Output encoding is always IMAPB (ST0903.4 or later).
     *
     * @param bytes Encoded byte array
     * @param encodingMode which encoding mode the {@code bytes} parameter uses.
     */
    public VmtiHorizontalFieldOfView(byte[] bytes, EncodingMode encodingMode) {
        super(bytes, encodingMode);
    }

    @Override
    public final String getDisplayName() {
        return "Horizontal Field of View";
    }
}
