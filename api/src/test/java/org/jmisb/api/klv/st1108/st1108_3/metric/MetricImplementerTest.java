package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Metric Implementer (ST 1108 Metric Local Set Tag 3). */
public class MetricImplementerTest {

    @Test
    public void testConstructFromValue() {
        MetricImplementer uut = new MetricImplementer("My Org", "My group");
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4f,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1e,
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6f,
                    (byte) 0x75,
                    (byte) 0x70
                });
        assertEquals(uut.getDisplayName(), "Metric Implementer");
        assertEquals(uut.getDisplayableValue(), "My Org - My group");
        assertEquals(uut.getOrganization(), "My Org");
        assertEquals(uut.getSubgroup(), "My group");
    }

    @Test
    public void testConstructFromBytes() throws KlvParseException {
        MetricImplementer uut =
                new MetricImplementer(
                        new byte[] {
                            (byte) 0x4d,
                            (byte) 0x79,
                            (byte) 0x20,
                            (byte) 0x4f,
                            (byte) 0x72,
                            (byte) 0x67,
                            (byte) 0x1e,
                            (byte) 0x4d,
                            (byte) 0x79,
                            (byte) 0x20,
                            (byte) 0x67,
                            (byte) 0x72,
                            (byte) 0x6f,
                            (byte) 0x75,
                            (byte) 0x70
                        });
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4f,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1e,
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6f,
                    (byte) 0x75,
                    (byte) 0x70
                });
        assertEquals(uut.getDisplayName(), "Metric Implementer");
        assertEquals(uut.getDisplayableValue(), "My Org - My group");
        assertEquals(uut.getOrganization(), "My Org");
        assertEquals(uut.getSubgroup(), "My group");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void testConstructFromBytesBad() throws KlvParseException {
        new MetricImplementer(
                new byte[] {
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4f,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1d,
                    (byte) 0x4d,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6f,
                    (byte) 0x75,
                    (byte) 0x70
                });
    }
}
