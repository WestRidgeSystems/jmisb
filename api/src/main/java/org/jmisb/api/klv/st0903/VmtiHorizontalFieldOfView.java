package org.jmisb.api.klv.st0903;

/**
 * Horizontal Field Of View (ST0903 VMTI LS Tag 11).
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
public class VmtiHorizontalFieldOfView extends VmtiFieldOfView {

    /**
     * Create from value
     *
     * @param fov horizontal field of view in degrees.
     */
    public VmtiHorizontalFieldOfView(double fov) {
        super(fov);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public VmtiHorizontalFieldOfView(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Horizontal Field of View";
    }
}
