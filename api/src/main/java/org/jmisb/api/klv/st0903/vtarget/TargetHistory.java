package org.jmisb.api.klv.st0903.vtarget;

import org.jmisb.api.klv.st0903.IVmtiMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Target History (ST0903 VTarget Pack Tag 6).
 *
 * <p>From ST0903:
 *
 * <blockquote>
 *
 * Primarily indicates detection of a new target or reuse of a previous Target ID Number. Also
 * provides the ability to indicate target persistence i.e., the number of previous times the same
 * target is detected and may provide useful context when a target reappears after no detection for
 * a significant time. There is no requirement that detections be in consecutive frames.
 *
 * </blockquote>
 */
public class TargetHistory implements IVmtiMetadataValue {

    private final int history;
    private static int MIN_VALUE = 0;
    private static int MAX_VALUE = 65535;

    /**
     * Create from value.
     *
     * @param history the target history count (0 lowest, 65535 highest)
     */
    public TargetHistory(int history) {
        if (history < MIN_VALUE || history > MAX_VALUE) {
            throw new IllegalArgumentException(
                    this.getDisplayName() + " value must be in range [0,65535]");
        }
        this.history = history;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Encoded byte array
     */
    public TargetHistory(byte[] bytes) {
        switch (bytes.length) {
            case 1:
                history = PrimitiveConverter.toUint8(bytes);
                break;
            case 2:
                history = PrimitiveConverter.toUint16(bytes);
                break;
            default:
                throw new IllegalArgumentException(
                        this.getDisplayName() + " encoding is maximum two byte unsigned integer");
        }
    }

    @Override
    public byte[] getBytes() {
        if (history < 256) {
            return PrimitiveConverter.uint8ToBytes((short) history);
        } else {
            return PrimitiveConverter.uint16ToBytes(history);
        }
    }

    @Override
    public String getDisplayableValue() {
        return "" + history;
    }

    @Override
    public final String getDisplayName() {
        return "Target History";
    }

    /**
     * Get the target history.
     *
     * @return the target history (0 lowest - new target, 65535 highest)
     */
    public int getTargetHistory() {
        return this.history;
    }
}
