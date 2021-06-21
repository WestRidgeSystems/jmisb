package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Tests for Metric Name (ST 1108 Metric Local Set Tag 1). */
public class MetricNameTest {

    @Test
    public void testConstructFromValue() {
        MetricName uut = new MetricName("GSD");
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x47, (byte) 0x53, (byte) 0x44});
        assertEquals(uut.getDisplayName(), "Metric Name");
        assertEquals(uut.getDisplayableValue(), "GSD");
        assertEquals(uut.getName(), "GSD");
    }

    @Test
    public void testConstructFromBytes() {
        MetricName uut = new MetricName(new byte[] {(byte) 0x52, (byte) 0x45, (byte) 0x52});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x52, (byte) 0x45, (byte) 0x52});
        assertEquals(uut.getDisplayName(), "Metric Name");
        assertEquals(uut.getDisplayableValue(), "RER");
        assertEquals(uut.getName(), "RER");
    }

    @Test
    public void testConstructFromValueVNIIRS() {
        MetricName uut = new MetricName("VNIIRS");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x56, (byte) 0x4E, (byte) 0x49, (byte) 0x49, (byte) 0x52, (byte) 0x053
                });
        assertEquals(uut.getDisplayName(), "Metric Name");
        assertEquals(uut.getDisplayableValue(), "VNIIRS");
        assertEquals(uut.getName(), "VNIIRS");
    }
}
