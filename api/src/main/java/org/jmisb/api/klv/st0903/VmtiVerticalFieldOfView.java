package org.jmisb.api.klv.st0903;

/**
 * Vertical Field Of View (ST0903 VMTI LS Tag 12).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Vertical field of view of imaging sensor input to VMTI process. Required only if VMTI process
 * operates on an imaging sensor different from that described by the parent MISB ST 0601 LS ;
 * otherwise, the ST 0601 LS provides its Tag 167– VFOV value. Can use with VFOV (Tag 17) from ST
 * 0601 to scale VMTI column, row coordinates. Typically required only to account for aspect ratio
 * variation.
 *
 * <p>Valid Values: The set of real numbers from 0 to 180 inclusive.
 *
 * </blockquote>
 */
public class VmtiVerticalFieldOfView extends VmtiFieldOfView {
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
     * @param bytes Encoded byte array
     */
    public VmtiVerticalFieldOfView(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Vertical Field of View";
    }
}
