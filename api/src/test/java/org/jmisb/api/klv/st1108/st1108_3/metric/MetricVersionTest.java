package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Metric Version (ST 1108 Metric Local Set Tag 2). */
public class MetricVersionTest {

    @Test
    public void testConstructFromValue() {
        MetricVersion uut = new MetricVersion("13.2a");
        assertEquals(
                uut.getBytes(),
                new byte[] {(byte) 0x31, (byte) 0x33, (byte) 0x2E, (byte) 0x32, (byte) 0x61});
        assertEquals(uut.getDisplayName(), "Metric Version");
        assertEquals(uut.getDisplayableValue(), "13.2a");
        assertEquals(uut.getVersion(), "13.2a");
    }

    @Test
    public void testConstructFromBytes() {
        MetricVersion uut =
                new MetricVersion(new byte[] {(byte) 0x31, (byte) 0x2E, (byte) 0x33, (byte) 0x38});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x31, (byte) 0x2E, (byte) 0x33, (byte) 0x38});
        assertEquals(uut.getDisplayName(), "Metric Version");
        assertEquals(uut.getDisplayableValue(), "1.38");
        assertEquals(uut.getVersion(), "1.38");
    }
}
