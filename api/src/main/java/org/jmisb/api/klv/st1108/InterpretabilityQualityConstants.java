package org.jmisb.api.klv.st1108;

import org.jmisb.api.klv.UniversalLabel;

/** Utility constants for ST 1108 implementation. */
public class InterpretabilityQualityConstants {
    private InterpretabilityQualityConstants() {}

    /**
     * Universal label for Interpretability and Quality Local Set.
     *
     * <p>This was called Minimum Interpretability and Quality Pack in ST 1108.2, same value.
     */
    public static final UniversalLabel InterpretabilityQualityLocalSetUl =
            new UniversalLabel(
                    new byte[] {
                        0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03,
                        0x1C, 0x00, 0x00, 0x00
                    });
}
