package org.jmisb.api.klv.st1108.st1108_3;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Video Compression Profile.
 *
 * <p>The Compression Profile mandatory item is a valid MISP-approved level for MPEG-2, H.264 and
 * H.265 specified using an unsigned 8-bit integer enumeration value. For no compression (i.e.,
 * uncompressed) specify a value of zero (0).
 */
public enum CompressionProfile implements IInterpretabilityQualityMetadataValue {

    /**
     * Undefined or Reserved.
     *
     * <p>This is not a valid compression profile value, and should not be created.
     */
    Undefined(-1),
    /**
     * Uncompressed.
     *
     * <p>Enumeration value 0.
     */
    Uncompressed(0),
    /**
     * Main (H.264).
     *
     * <p>Enumeration value 1.
     */
    MainH264(1),
    /* Main 10 (H.265).
     *
     *<p>Enumeration value 2.
     */
    Main10H265(2),
    /**
     * Constrained Baseline (H.264).
     *
     * <p>Enumeration value 3.
     */
    ConstrainedBaselineH264(3),
    /**
     * High (H.264).
     *
     * <p>Enumeration value 4.
     */
    HighH264(4),
    /**
     * Main 4:2:2 12 (H.265).
     *
     * <p>Enumeration value 5.
     */
    Main_4_2_2_12(5),
    /**
     * Main 4:4:4 12 (H.265).
     *
     * <p>Enumeration value 6.
     */
    Main_4_4_4_12(6),
    /**
     * High 4:2:2 (H.264).
     *
     * <p>Enumeration value 7.
     */
    High_4_2_2(7),
    /**
     * High 4:4:4 Predictive (H.264).
     *
     * <p>Enumeration value 8.
     */
    High_4_4_4_Predictive_H264(8);

    static CompressionProfile fromBytes(byte[] bytes) throws KlvParseException {
        if (bytes.length != 1) {
            throw new KlvParseException("Compression Profile encoding is a one-byte unsigned int");
        }
        return lookup.getOrDefault(bytes[0], Undefined);
    }

    private static final Map<Byte, CompressionProfile> lookup = new HashMap<>();

    static {
        for (CompressionProfile key : values()) {
            lookup.put(key.value, key);
        }
    }

    private byte value;

    @Override
    public String getDisplayName() {
        return "Compression Profile";
    }

    @Override
    public String getDisplayableValue() {
        switch (this) {
            case Uncompressed:
                return "N/A (uncompressed)";
            case MainH264:
                return "Main (H.264)";
            case Main10H265:
                return "Main 10 (H.265)";
            case ConstrainedBaselineH264:
                return "Constrained Baseline (H.264)";
            case HighH264:
                return "High (H.264)";
            case Main_4_2_2_12:
                return "Main 4:2:2 12 (H.265)";
            case Main_4_4_4_12:
                return "Main 4:4:4 12 (H.265)";
            case High_4_2_2:
                return "High 4:2:2 (H.264)";
            case High_4_4_4_Predictive_H264:
                return "High 4:4:4 Predictive (H.264)";
            default:
                return "Unknown or Reserved";
        }
    }

    private CompressionProfile(int value) {
        this.value = (byte) value;
    }

    @Override
    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
        arrayBuilder.appendAsOID(IQMetadataKey.CompressionProfile.getIdentifier());
        byte[] valueBytes = new byte[] {(byte) value};
        arrayBuilder.appendAsBerLength(valueBytes.length);
        arrayBuilder.append(valueBytes);
    }
}
