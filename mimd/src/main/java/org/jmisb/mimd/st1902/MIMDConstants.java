package org.jmisb.mimd.st1902;

import org.jmisb.api.klv.UniversalLabel;

/** Constant values for the MIMD implementation. */
public class MIMDConstants {
    private MIMDConstants() {}

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
}
