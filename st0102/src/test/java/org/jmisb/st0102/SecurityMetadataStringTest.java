package org.jmisb.st0102;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for SecurityMetadataString. */
public class SecurityMetadataStringTest {
    @Test
    public void checkCaveats() {
        SecurityMetadataString s =
                new SecurityMetadataString(SecurityMetadataString.CAVEATS, "SomeCaveat");
        assertEquals(s.getDisplayName(), "Caveats");
        assertEquals(s.getDisplayableValue(), "SomeCaveat");
        assertEquals(s.getValue(), "SomeCaveat");
        assertEquals(
                s.getBytes(),
                new byte[] {
                    (byte) 0x53,
                    (byte) 0x6f,
                    (byte) 0x6d,
                    (byte) 0x65,
                    (byte) 0x43,
                    (byte) 0x61,
                    (byte) 0x76,
                    (byte) 0x65,
                    (byte) 0x61,
                    (byte) 0x74
                });
    }

    @Test
    public void checkClassificationReason() {
        SecurityMetadataString s =
                new SecurityMetadataString(
                        SecurityMetadataString.CLASSIFICATION_REASON, "The Classification Reason");
        assertEquals(s.getDisplayName(), "Classification Reason");
        assertEquals(s.getDisplayableValue(), "The Classification Reason");
        assertEquals(s.getValue(), "The Classification Reason");
        assertEquals(
                s.getBytes(),
                new byte[] {
                    (byte) 0x54,
                    (byte) 0x68,
                    (byte) 0x65,
                    (byte) 0x20,
                    (byte) 0x43,
                    (byte) 0x6c,
                    (byte) 0x61,
                    (byte) 0x73,
                    (byte) 0x73,
                    (byte) 0x69,
                    (byte) 0x66,
                    (byte) 0x69,
                    (byte) 0x63,
                    (byte) 0x61,
                    (byte) 0x74,
                    (byte) 0x69,
                    (byte) 0x6f,
                    (byte) 0x6e,
                    (byte) 0x20,
                    (byte) 0x52,
                    (byte) 0x65,
                    (byte) 0x61,
                    (byte) 0x73,
                    (byte) 0x6f,
                    (byte) 0x6e
                });
    }
}
