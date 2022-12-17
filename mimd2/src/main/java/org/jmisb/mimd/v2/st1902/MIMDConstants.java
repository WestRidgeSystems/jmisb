package org.jmisb.mimd.v2.st1902;

import org.jmisb.api.klv.UniversalLabel;

/** Constant values for the MIMD implementation. */
public class MIMDConstants {
    private MIMDConstants() {}

    /**
     * Universal label for MIMD Metadata KLV Encoding (ST1902.2).
     *
     * <p>This is defined in ST 1902.2 Section 6.3.1 "MIMD KLV Key".
     *
     * <p>Note that the key in ST 1902.1 is different.
     */
    public static final UniversalLabel MIMDLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x04,
                        0x00, 0x00, 0x00, 0x00
                    });
}
