package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Quality Method (ST 1108.2 Tag 4). */
public class QualityMethodTest {

    @Test
    public void testConstructFromValue() {
        QualityMethod uut = new QualityMethod(3);
        assertEquals(uut.getDisplayName(), "Interpretability Quality Method");
        assertEquals(uut.getDisplayableValue(), "3 (RP 1203.3)");
        assertEquals(uut.getMethodId(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        QualityMethod uut = new QualityMethod(new byte[] {(byte) 0x01});
        assertEquals(uut.getDisplayName(), "Interpretability Quality Method");
        assertEquals(uut.getDisplayableValue(), "1 (RP 1203.1)");
        assertEquals(uut.getMethodId(), 1);
    }

    @Test
    public void testConstructFromEncodedBytesMin() {
        QualityMethod uut = new QualityMethod(new byte[] {(byte) 0x00});
        assertEquals(uut.getDisplayName(), "Interpretability Quality Method");
        assertEquals(uut.getDisplayableValue(), "0 (Manual)");
        assertEquals(uut.getMethodId(), 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new QualityMethod(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new QualityMethod(new byte[] {});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new QualityMethod(new byte[] {0x01, 0x02});
    }
}
