package org.jmisb.api.klv.st0602;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Source method for the annotation.
 *
 * <p>The Annotation Source key describes the source method of the annotation. This 4-byte bit
 * masked field is only required during a “NEW” and “STATUS” event.
 *
 * <p>Valid values are combinations of:
 *
 * <ul>
 *   <li>{@code 0x00000000} - manually annotated
 *   <li>{@code 0x00000001} - automated annotation from BE/RWAC
 *   <li>{@code 0x00000002} - automated annotation from user defined latitude / longitude center
 *       point
 *   <li>{@code 0x00000004} - automated annotation from user defined Area of Interest (AOI)
 *   <li>{@code 0x00000008} - automated annotation from pixel intelligence
 * </ul>
 *
 * <p>Note that while this is a bit mask, the manual annotation cannot reasonably be combined with
 * other annotations.
 */
public class AnnotationSource implements IAnnotationMetadataValue {

    /** Manually annotated annotation. */
    public static final int MANUALLY_ANNOTATED = 0x00000000;
    /** Automated from BE/RWAC. */
    public static final int AUTOMATED_FROM_BE_RWAC = 0x00000001;
    /** Automated from user-defined latitude / longitude center point. */
    public static final int AUTOMATED_FROM_USER_LAT_LON = 0x00000002;
    /** Automated from user-defined Area of Interest (AOI). */
    public static final int AUTOMATED_FROM_USER_AOI = 0x00000004;
    /**
     * Automated from pixel intelligence.
     *
     * <p>This value is also known to be used for other automated annotations (not just pixel
     * based).
     */
    public static final int AUTOMATED_FROM_PIXEL_INTELLIGENCE = 0x00000008;

    private final long flags;

    /**
     * Create from value.
     *
     * @param flags bitmask of flag values per ST 0602.
     */
    public AnnotationSource(long flags) {
        this.flags = flags;
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes Byte array of length 4
     * @throws KlvParseException if {@code bytes} is not of the expected length.
     */
    public AnnotationSource(byte[] bytes) throws KlvParseException {
        if (bytes.length != 4) {
            throw new KlvParseException("Annotation Source encoding is a four byte bit mask");
        }
        flags = PrimitiveConverter.toUint32(bytes);
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uint32ToBytes(flags);
    }

    @Override
    public String getDisplayName() {
        return "Annotation Source";
    }

    @Override
    public String getDisplayableValue() {
        if (flags == MANUALLY_ANNOTATED) {
            return "Manually Annotated";
        }
        List<String> labels = new ArrayList<>();
        if ((flags & AUTOMATED_FROM_BE_RWAC) == AUTOMATED_FROM_BE_RWAC) {
            labels.add("Automated from BE/RWAC");
        }
        if ((flags & AUTOMATED_FROM_USER_LAT_LON) == AUTOMATED_FROM_USER_LAT_LON) {
            labels.add("Automated from user defined latitude/longitude");
        }
        if ((flags & AUTOMATED_FROM_USER_AOI) == AUTOMATED_FROM_USER_AOI) {
            labels.add("Automated from user defined AOI");
        }
        if ((flags & AUTOMATED_FROM_PIXEL_INTELLIGENCE) == AUTOMATED_FROM_PIXEL_INTELLIGENCE) {
            labels.add("Automated from pixel intelligence");
        }
        return String.join("|", labels);
    }
}
