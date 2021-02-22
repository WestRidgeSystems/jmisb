package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.api.klv.st0903.shared.IVTrackItemMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target Priority (ST0903 VTarget Pack Item 4 and VTrackItem Item 7).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Provides systems downstream a means to intelligently cull targets for a given frame.
 *
 * <p>For example, VMTI processors may generate thousands of hits.
 *
 * </blockquote>
 */
public class TargetPriority implements IVmtiMetadataValue, IVTrackItemMetadataValue {

    private final short priority;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 255;

    /**
     * Create from value.
     *
     * @param priority the target priority (1 highest, 255 lowest)
     */
    public TargetPriority(short priority) {
        if (priority < MIN_VALUE || priority > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [1,255]");
        }
        this.priority = priority;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetPriority(byte[] bytes) {
        if (bytes.length > 1) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " encoding is maximum one byte unsigned integer");
        }
        priority = (short) PrimitiveConverter.toUint8(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint8ToBytes(priority);
    }

    @Override
    public String getDisplayableValue() {
        return "" + priority;
    }

    @Override
    public String getDisplayName() {
        return "Target Priority";
    }

    /**
     * Get the target priority.
     *
     * @return the target priority (1 highest, 255 lowest)
     */
    public short getTargetPriority() {
        return this.priority;
    }
}
