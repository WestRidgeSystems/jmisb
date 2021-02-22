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
}
