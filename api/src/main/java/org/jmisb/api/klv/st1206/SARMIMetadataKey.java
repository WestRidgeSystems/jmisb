package org.jmisb.api.klv.st1206;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 1206 tags - description and numbers. */
public enum SARMIMetadataKey implements IKlvKey {
    /** Unknown key. This should not be created. */
    Undefined(0),
    /** Grazing Angle. */
    GrazingAngle(1),
    /** Ground Plane Squint Angle. */
    GroundPlaneSquintAngle(2),
    /** Look Direction. */
    LookDirection(3),
    /** Image Plane. */
    ImagePlane(4),
    /** Range Resolution. */
    RangeResolution(5),
    /** Cross-Range Resolution. */
    CrossRangeResolution(6),
    /** Range Image Plane Pixel Size. */
    RangeImagePlanePixelSize(7),
    /** Cross-Range Image Plane Pixel Size. */
    CrossRangeImagePlanePixelSize(8),
    /** Image Rows. */
    ImageRows(9),
    /** Image Columns. */
    ImageColumns(10),
    /** Range Direction Angle Relative to True North. */
    RangeDirectionAngleRelativeToTrueNorth(11),
    /** True North Direction Relative to Top Image Edge. */
    TrueNorthDirectionRelativeToTopImageEdge(12),
    /** Range Layover Angle Relative to True North. */
    RangeLayoverAngleRelativeToTrueNorth(13),
    /** Ground Aperture Angular Extent. */
    GroundApertureAngularExtent(14),
    /** Aperture Duration. */
    ApertureDuration(15),
    /** Ground Track Angle. */
    GroundTrackAngle(16),
    /** Minimum Detectable Velocity. */
    MinimumDetectableVelocity(17),
    /** True Pulse Repetition Frequency. */
    TruePulseRepetitionFrequency(18),
    /** Pulse Repetition Frequency Scale Factor. */
    PulseRepetitionFrequencyScaleFactor(19),
    /** Transmit RF Center Frequency. */
    TransmitRFCenterFrequency(20),
    /** Transmit RF Bandwidth. */
    TransmitRFBandwidth(21),
    /** Radar Cross Section Scale Factor Polynomial. */
    RadarCrossSectionScaleFactorPolynomial(22),
    /** Reference Frame Precision Time Stamp. */
    ReferenceFramePrecisionTimeStamp(23),
    /** Reference Frame Grazing Angle. */
    ReferenceFrameGrazingAngle(24),
    /** Reference Frame Ground Plane Squint Angle. */
    ReferenceFrameGroundPlaneSquintAngle(25),
    /** Reference Frame Range Direction Angle Relative to True North. */
    ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(26),
    /** Reference Frame Range Layover Angle Relative to True North. */
    ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(27),
    /** Document Version. */
    DocumentVersion(28);

    private int tag;

    private static final Map<Integer, SARMIMetadataKey> tagTable = new HashMap<>();

    static {
        for (SARMIMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    private SARMIMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key
     */
    public static SARMIMetadataKey getKey(int tag) {
        return tagTable.containsKey(tag) ? tagTable.get(tag) : Undefined;
    }
}
