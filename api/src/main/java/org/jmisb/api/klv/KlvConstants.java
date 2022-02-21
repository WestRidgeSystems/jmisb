package org.jmisb.api.klv;

/** Various constants such as Universal Labels. */
public class KlvConstants {
    private KlvConstants() {}

    /** Universal label for Generalized Transformation Set (ST 1202). */
    public static final UniversalLabel GeneralizedTransformationUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x05, 0x00, 0x00, 0x00
                    });
}
