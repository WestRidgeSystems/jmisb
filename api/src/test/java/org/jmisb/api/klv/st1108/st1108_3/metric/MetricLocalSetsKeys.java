package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** @author bradh */
public class MetricLocalSetsKeys {

    public MetricLocalSetsKeys() {}

    @Test
    public void checkToString() {
        MetricLocalSetsKey key = new MetricLocalSetsKey(2);
        assertEquals(key.toString(), "Metric 2");
    }

    @Test
    public void checkHash() {
        MetricLocalSetsKey key = new MetricLocalSetsKey(2);
        assertEquals(key.hashCode(), 337);
    }

    @Test
    public void checkCompareTo() {
        MetricLocalSetsKey key = new MetricLocalSetsKey(2);
        MetricLocalSetsKey keyHigher = new MetricLocalSetsKey(4);
        assertTrue(key.compareTo(keyHigher) < 0);
    }

    @Test
    public void checkEquals() {
        MetricLocalSetsKey key = new MetricLocalSetsKey(2);
        assertFalse(key.equals(null));
        assertFalse(key.equals(new MetricLocalSetsKey(3)));
        assertFalse(key.equals(2));
        assertTrue(key.equals(key));
    }
}
