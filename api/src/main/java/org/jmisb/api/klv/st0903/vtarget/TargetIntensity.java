package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.api.klv.st0903.shared.VmtiV3Value;

/**
 * Target Intensity (ST0903 VTarget Pack Item 9 and VTrackItem Pack Item 12).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Dominant intensity of the target with dynamic range up to 24 bits. For use when transmitting
 * metadata in the absence of the underlying Motion Imagery. Primarily designed for Infrared systems
 * that may detect targets with greater than 8-bits per pixel dynamic range and transmit the signal
 * at a lower dynamic range. VFeature LS offers more comprehensive spectral information. Value 0 is
 * black.
 *
 * </blockquote>
 */
public class TargetIntensity extends VmtiV3Value
        implements IVmtiMetadataValue, IVTrackItemMetadataValue {
    /**
     * Create from value.
     *
     * @param intensity the target intensity.
     */
    public TargetIntensity(int intensity) {
        super(intensity);
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes intensity, encoded as a variable length unsigned int (max 3 bytes)
     */
    public TargetIntensity(byte[] bytes) {
        super(bytes);
    }

    @Override
    public final String getDisplayName() {
        return "Target Intensity";
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", value);
    }
}
