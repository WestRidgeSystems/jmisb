package org.jmisb.api.klv;

/** Various constants such as Universal Labels. */
public class KlvConstants {
    private KlvConstants() {}

    /** Universal label for UAS Datalink Local Metadata Set (ST 0601). */
    public static final UniversalLabel UasDatalinkLocalUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0b, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x01,
                        0x01, 0x00, 0x00, 0x00
                    });

    /** Universal label for Security Metadata Set Local Set (ST 0102). */
    public static final UniversalLabel SecurityMetadataLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Universal label for Security Metadata Set Universal Set (ST 0102). */
    public static final UniversalLabel SecurityMetadataUniversalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x01, 0x01, 0x01, 0x02, 0x08, 0x02, 0x00,
                        0x00, 0x00, 0x00, 0x00
                    });

    /** Universal label for Generalized Transformation Set (ST 1202). */
    public static final UniversalLabel GeneralizedTransformationUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x05, 0x00, 0x00, 0x00
                    });

    /** Universal label for MIIS Augmentation Identifiers (ST 1301). */
    public static final UniversalLabel MiisLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x03, 0x00, 0x00, 0x00
                    });

    /** Universal label for Remote Video Terminal (RVT) Metadata Local Set (ST0806). */
    public static final UniversalLabel RvtLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Universal label for Ancillary Text Metadata Sets Local Set (ST0808). */
    public static final UniversalLabel AncillaryTextLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Universal label for VMTI Local Set (ST 0903). */
    public static final UniversalLabel VmtiLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0e, 0x01, 0x03, 0x03,
                        0x06, 0x00, 0x00, 0x00
                    });

    /** Universal Label for VTrack Local Set (ST0903). */
    public static final UniversalLabel VTrackLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x1E, 0x00, 0x00, 0x00
                    });

    /** Universal label for obsolete Predator UAV Basic Universal Metadata Set (EG0104.5). */
    public static final UniversalLabel PredatorMetadataLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x02, 0x01, 0x01, 0x01, 0x0e, 0x01, 0x01, 0x02,
                        0x01, 0x01, 0x00, 0x00
                    });

    /** Universal label for Synthetic Aperture Radar Motion Imagery (ST1206). */
    public static final UniversalLabel SARMILocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x0D, 0x00, 0x00, 0x00
                    });

    /**
     * Universal label for MIMD Metadata KLV Encoding (ST1902.1).
     *
     * <p>There is a different label ({@code 06.0E.2B.34.02.05.01.01.0E.01.05.01.00.00.00.00}) in
     * the original ST1902, but that may not have been implemented "in the wild".
     */
    public static final UniversalLabel MIMDLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00
                    });

    /**
     * Universal label for Interpretability and Quality Local Set (ST 1108).
     *
     * <p>This was called Minimum Interpretability and Quality Pack in ST 1108.2, same value.
     */
    public static final UniversalLabel InterpretabilityQualityLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x1C, 0x00, 0x00, 0x00
                    });

    /**
     * Universal label for Byte Order preface item for Annotation Universal Metadata Set (ST 0602).
     */
    public static final UniversalLabel AnnotationByteOrderUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x03, 0x01, 0x02, 0x01,
                        0x02, 0x00, 0x00, 0x00
                    });

    /**
     * Universal label for Active Lines per Frame preface item for Annotation Universal Metadata Set
     * (ST 0602).
     */
    public static final UniversalLabel AnnotationActiveLinesPerFrameUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x03, 0x02,
                        0x02, 0x00, 0x00, 0x00
                    });
    /**
     * Universal label for Active Samples per Line preface item for Annotation Universal Metadata
     * Set (ST 0602).
     */
    public static final UniversalLabel AnnotationActiveSamplesPerLineUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01, 0x04, 0x01, 0x05, 0x01,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Universal label for Annotation Universal Metadata Set (ST 0602). */
    public static final UniversalLabel AnnotationUniversalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x01, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x01, 0x00, 0x00, 0x00
                    });

    /** Universal label for Meteorological Metadata Local Set (ST 0809). */
    public static final UniversalLabel MeteorologicalMetadataLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x2B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x01,
                        0x0E, 0x00, 0x00, 0x00
                    });

    /** Universal label for Time Transfer Local Set (ST 1603). */
    public static final UniversalLabel TimeTransferLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02,
                        0x02, 0x00, 0x00, 0x00
                    });

    /** Universal label for Nano Time Transfer Pack (ST 1603). */
    public static final UniversalLabel NanoTimeTransferPackUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x02,
                        0x09, 0x00, 0x00, 0x00
                    });
}
