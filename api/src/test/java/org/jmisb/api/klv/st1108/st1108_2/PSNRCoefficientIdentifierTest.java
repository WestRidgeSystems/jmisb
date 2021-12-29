package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for PSNR Coefficient Identifier (ST 1108.2 Tag 5). */
public class PSNRCoefficientIdentifierTest {

    @Test
    public void testConstructFromValue() {
        PSNRCoefficientIdentifier uut = new PSNRCoefficientIdentifier(3);
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        PSNRCoefficientIdentifier uut = new PSNRCoefficientIdentifier(new byte[] {(byte) 0x03});
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        PSNRCoefficientIdentifier uut = new PSNRCoefficientIdentifier(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getIdentifier(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        PSNRCoefficientIdentifier uut = new PSNRCoefficientIdentifier(new byte[] {(byte) 0xFF});
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "255");
        assertEquals(uut.getIdentifier(), 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new PSNRCoefficientIdentifier(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new PSNRCoefficientIdentifier(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new PSNRCoefficientIdentifier(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new PSNRCoefficientIdentifier(new byte[] {0x01, 0x02});
    }
}
