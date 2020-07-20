package org.jmisb.api.klv.st0601;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.IKlvKey;

/**
 * Generic Flag Data (ST 0601 Item 47).
 *
 * <p>From ST:
 *
 * <blockquote>
 *
 * Generic metadata flags.
 *
 * <p>The Generic Data Flags are miscellaneous boolean (yes / no) aircraft and image related data
 * settings which are individual bits in a single byte value.
 *
 * </blockquote>
 *
 * Initial versions of ST0601.13, ST0601.14, ST0601.15 and ST0601.16 inadvertently reversed the
 * meaning of these bits. We use the original (intended) meaning as published in the updated
 * (ST0601.13a, ST0601.14a, ST0601.15a and ST0601.16a) versions, not the interpretation published in
 * the initial versions.
 */
public class GenericFlagData01 implements IUasDatalinkValue {

    protected static final String LASER_RANGE_STRING_KEY = "Laser Range";
    protected static final String LASER_OFF = "Laser off";
    protected static final String LASER_ON = "Laser on";

    protected static final String AUTO_TRACK_STRING_KEY = "Auto-Track";
    protected static final String AUTO_TRACK_OFF = "Auto-Track off";
    protected static final String AUTO_TRACK_ON = "Auto-Track on";

    protected static final String IR_POLARITY_STRING_KEY = "IR Polarity";
    protected static final String WHITE_HOT = "White Hot";
    protected static final String BLACK_HOT = "Black Hot";

    protected static final String ICING_STATUS_STRING_KEY = "Icing Status";
    protected static final String NO_ICING_DETECTED = "No Icing Detected";
    protected static final String ICING_DETECTED = "Icing Detected";

    protected static final String SLANT_RANGE_STRING_KEY = "Slant Range";
    protected static final String CALCULATED = "Calculated";
    protected static final String MEASURED = "Measured";

    protected static final String IMAGE_INVALID_STRING_KEY = "Image Invalid";
    protected static final String IMAGE_INVALID = "Image Invalid";
    protected static final String IMAGE_VALID = "Image Valid";

    private final SortedMap<FlagDataKey, UasDatalinkString> map = new TreeMap<>();

    /**
     * Create from value.
     *
     * <p>Each of the keys that is set in the parameter list will be set (1). The meaning of set is:
     *
     * <ul>
     *   <li>LaserRange: Laser is on.
     *   <li>AutoTrack: Auto-track is on.
     *   <li>IR_Polarity: Black hot.
     *   <li>IcingStatus: Icing detected.
     *   <li>SlantRange: Measured.
     * </ul>
     *
     * @param values the values to set on this flagged data instance.
     */
    public GenericFlagData01(List<FlagDataKey> values) {
        if (values.contains(FlagDataKey.LaserRange)) {
            map.put(
                    FlagDataKey.LaserRange,
                    new UasDatalinkString(LASER_RANGE_STRING_KEY, LASER_ON));
        } else {
            map.put(
                    FlagDataKey.LaserRange,
                    new UasDatalinkString(LASER_RANGE_STRING_KEY, LASER_OFF));
        }

        if (values.contains(FlagDataKey.AutoTrack)) {
            map.put(
                    FlagDataKey.AutoTrack,
                    new UasDatalinkString(AUTO_TRACK_STRING_KEY, AUTO_TRACK_ON));
        } else {
            map.put(
                    FlagDataKey.AutoTrack,
                    new UasDatalinkString(AUTO_TRACK_STRING_KEY, AUTO_TRACK_OFF));
        }

        if (values.contains(FlagDataKey.IR_Polarity)) {
            map.put(
                    FlagDataKey.IR_Polarity,
                    new UasDatalinkString(IR_POLARITY_STRING_KEY, BLACK_HOT));
        } else {
            map.put(
                    FlagDataKey.IR_Polarity,
                    new UasDatalinkString(IR_POLARITY_STRING_KEY, WHITE_HOT));
        }

        if (values.contains(FlagDataKey.IcingStatus)) {
            map.put(
                    FlagDataKey.IcingStatus,
                    new UasDatalinkString(ICING_STATUS_STRING_KEY, ICING_DETECTED));
        } else {
            map.put(
                    FlagDataKey.IcingStatus,
                    new UasDatalinkString(ICING_STATUS_STRING_KEY, NO_ICING_DETECTED));
        }

        if (values.contains(FlagDataKey.SlantRange)) {
            map.put(
                    FlagDataKey.SlantRange,
                    new UasDatalinkString(SLANT_RANGE_STRING_KEY, MEASURED));
        } else {
            map.put(
                    FlagDataKey.SlantRange,
                    new UasDatalinkString(SLANT_RANGE_STRING_KEY, CALCULATED));
        }

        if (values.contains(FlagDataKey.ImageInvalid)) {
            map.put(
                    FlagDataKey.ImageInvalid,
                    new UasDatalinkString(IMAGE_INVALID_STRING_KEY, IMAGE_INVALID));
        } else {
            map.put(
                    FlagDataKey.ImageInvalid,
                    new UasDatalinkString(IMAGE_INVALID_STRING_KEY, IMAGE_VALID));
        }
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes array of single byte containing flag values.
     */
    public GenericFlagData01(byte[] bytes) {
        byte flags = bytes[bytes.length - 1];
        if ((flags & 0x01) == 0x01) {
            map.put(
                    FlagDataKey.LaserRange,
                    new UasDatalinkString(LASER_RANGE_STRING_KEY, LASER_ON));
        } else {
            map.put(
                    FlagDataKey.LaserRange,
                    new UasDatalinkString(LASER_RANGE_STRING_KEY, LASER_OFF));
        }

        if ((flags & 0x02) == 0x02) {
            map.put(
                    FlagDataKey.AutoTrack,
                    new UasDatalinkString(AUTO_TRACK_STRING_KEY, AUTO_TRACK_ON));
        } else {
            map.put(
                    FlagDataKey.AutoTrack,
                    new UasDatalinkString(AUTO_TRACK_STRING_KEY, AUTO_TRACK_OFF));
        }

        if ((flags & 0x04) == 0x04) {
            map.put(
                    FlagDataKey.IR_Polarity,
                    new UasDatalinkString(IR_POLARITY_STRING_KEY, BLACK_HOT));
        } else {
            map.put(
                    FlagDataKey.IR_Polarity,
                    new UasDatalinkString(IR_POLARITY_STRING_KEY, WHITE_HOT));
        }

        if ((flags & 0x08) == 0x08) {
            map.put(
                    FlagDataKey.IcingStatus,
                    new UasDatalinkString(ICING_STATUS_STRING_KEY, ICING_DETECTED));
        } else {
            map.put(
                    FlagDataKey.IcingStatus,
                    new UasDatalinkString(ICING_STATUS_STRING_KEY, NO_ICING_DETECTED));
        }

        if ((flags & 0x10) == 0x10) {
            map.put(
                    FlagDataKey.SlantRange,
                    new UasDatalinkString(SLANT_RANGE_STRING_KEY, MEASURED));
        } else {
            map.put(
                    FlagDataKey.SlantRange,
                    new UasDatalinkString(SLANT_RANGE_STRING_KEY, CALCULATED));
        }

        if ((flags & 0x20) == 0x20) {
            map.put(
                    FlagDataKey.ImageInvalid,
                    new UasDatalinkString(IMAGE_INVALID_STRING_KEY, IMAGE_INVALID));
        } else {
            map.put(
                    FlagDataKey.ImageInvalid,
                    new UasDatalinkString(IMAGE_INVALID_STRING_KEY, IMAGE_VALID));
        }
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[] {0x00};
        if (map.get(FlagDataKey.LaserRange).getDisplayableValue().equals(LASER_ON)) {
            bytes[0] += (byte) 0x01;
        }
        if (map.get(FlagDataKey.AutoTrack).getDisplayableValue().equals(AUTO_TRACK_ON)) {
            bytes[0] += (byte) 0x02;
        }
        if (map.get(FlagDataKey.IR_Polarity).getDisplayableValue().equals(BLACK_HOT)) {
            bytes[0] += (byte) 0x04;
        }
        if (map.get(FlagDataKey.IcingStatus).getDisplayableValue().equals(ICING_DETECTED)) {
            bytes[0] += (byte) 0x08;
        }
        if (map.get(FlagDataKey.SlantRange).getDisplayableValue().equals(MEASURED)) {
            bytes[0] += (byte) 0x10;
        }
        if (map.get(FlagDataKey.ImageInvalid).getDisplayableValue().equals(IMAGE_INVALID)) {
            bytes[0] += (byte) 0x20;
        }
        return bytes;
    }

    @Override
    public String getDisplayName() {
        return "Generic Flag Data 01";
    }

    @Override
    public String getDisplayableValue() {
        return "[Flag data]";
    }

    public UasDatalinkString getField(IKlvKey tag) {
        if (tag instanceof FlagDataKey) {
            return map.get((FlagDataKey) tag);
        }
        return null;
    }

    public Set<? extends IKlvKey> getTags() {
        return map.keySet();
    }
}
