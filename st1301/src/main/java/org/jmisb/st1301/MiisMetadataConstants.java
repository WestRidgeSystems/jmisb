package org.jmisb.st1301;

import org.jmisb.api.klv.UniversalLabel;

/** Constants used in the ST 1301 MIIS Local Set implementation. */
public class MiisMetadataConstants {

    /**
     * The currently-supported revision is ST 1301.2.
     *
     * <p>This may be useful in {@link ST1301Version}.
     */
    public static final short ST_VERSION_NUMBER = 2;

    /** Universal label for MIIS Augmentation Identifiers. */
    public static final UniversalLabel MiisLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x0B, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x05,
                        0x03, 0x00, 0x00, 0x00
                    });

    private MiisMetadataConstants() {}
}
