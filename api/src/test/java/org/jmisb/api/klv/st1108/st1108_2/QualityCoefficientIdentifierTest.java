package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Quality Coefficient Identifier (ST 1108.2 Tag 6). */
public class QualityCoefficientIdentifierTest {

    @Test
    public void testConstructFromValue() {
        QualityCoefficientIdentifier uut = new QualityCoefficientIdentifier(3);
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        QualityCoefficientIdentifier uut =
                new QualityCoefficientIdentifier(new byte[] {(byte) 0x03});
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "3");
        assertEquals(uut.getIdentifier(), 3);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        QualityCoefficientIdentifier uut =
                new QualityCoefficientIdentifier(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "0");
        assertEquals(uut.getIdentifier(), 0);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        QualityCoefficientIdentifier uut =
                new QualityCoefficientIdentifier(new byte[] {(byte) 0xFF});
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "255");
        assertEquals(uut.getIdentifier(), 255);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new QualityCoefficientIdentifier(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new QualityCoefficientIdentifier(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new QualityCoefficientIdentifier(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new QualityCoefficientIdentifier(new byte[] {0x01, 0x02});
    }
}
