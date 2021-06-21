package org.jmisb.api.klv.st1108.st1108_3.metric;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.testng.annotations.Test;

/** Unit tests for Metric Local Set. */
public class MetricLocalSetTest extends LoggerChecks {

    public MetricLocalSetTest() {
        super(MetricLocalSet.class);
    }

    @Test
    public void all() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x03,
                    (byte) 0x52,
                    (byte) 0x45,
                    (byte) 0x52,
                    0x02,
                    0x04,
                    (byte) 0x31,
                    (byte) 0x2E,
                    (byte) 0x33,
                    (byte) 0x38,
                    0x03,
                    0x0F,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4F,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1e,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6F,
                    (byte) 0x75,
                    (byte) 0x70,
                    0x04,
                    0x09,
                    (byte) 0xC3,
                    (byte) 0xBC,
                    (byte) 0x2C,
                    (byte) 0x41,
                    (byte) 0x42,
                    (byte) 0x3A,
                    (byte) 0x33,
                    (byte) 0x32,
                    (byte) 0x34,
                    0x05,
                    0x08,
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0xc5,
                    (byte) 0x8f,
                    (byte) 0x08,
                    (byte) 0x32,
                    (byte) 0x58,
                    (byte) 0x00,
                    0x06,
                    0x04,
                    (byte) 0x3e,
                    (byte) 0xae,
                    (byte) 0x14,
                    (byte) 0x7b
                };
        MetricLocalSet uut = MetricLocalSet.fromBytes(bytes);
        assertEquals(uut.getIdentifiers().size(), 6);
        assertEquals(uut.getDisplayName(), "Metric");
        assertEquals(uut.getDisplayableValue(), "[Metric]");
        checkMetricName(uut);
        checkMetricVersion(uut);
        checkMetricImplementer(uut);
        checkMetricParameters(uut);
        checkMetricTime(uut);
        checkMetricValue(uut);
        assertEquals(uut.getBytes(), bytes);
    }

    @Test
    public void allMap() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x03,
                    (byte) 0x52,
                    (byte) 0x45,
                    (byte) 0x52,
                    0x02,
                    0x04,
                    (byte) 0x31,
                    (byte) 0x2E,
                    (byte) 0x33,
                    (byte) 0x38,
                    0x03,
                    0x0F,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4F,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1e,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6F,
                    (byte) 0x75,
                    (byte) 0x70,
                    0x04,
                    0x09,
                    (byte) 0xC3,
                    (byte) 0xBC,
                    (byte) 0x2C,
                    (byte) 0x41,
                    (byte) 0x42,
                    (byte) 0x3A,
                    (byte) 0x33,
                    (byte) 0x32,
                    (byte) 0x34,
                    0x05,
                    0x08,
                    (byte) 0x00,
                    (byte) 0x05,
                    (byte) 0xc5,
                    (byte) 0x8f,
                    (byte) 0x08,
                    (byte) 0x32,
                    (byte) 0x58,
                    (byte) 0x00,
                    0x06,
                    0x04,
                    (byte) 0x3e,
                    (byte) 0xae,
                    (byte) 0x14,
                    (byte) 0x7b
                };
        Map<MetricLocalSetKey, IMetricLocalSetValue> map = new HashMap<>();
        map.put(MetricLocalSetKey.MetricName, new MetricName("RER"));
        map.put(MetricLocalSetKey.MetricVersion, new MetricVersion("1.38"));
        map.put(MetricLocalSetKey.MetricImplementer, new MetricImplementer("My Org", "My group"));
        map.put(MetricLocalSetKey.MetricParameters, new MetricParameters("ü,AB:324"));
        map.put(
                MetricLocalSetKey.MetricTime,
                new MetricTime(new ST0603TimeStamp(1624592992065536L)));
        map.put(MetricLocalSetKey.MetricValue, new MetricValue(0.34));
        MetricLocalSet uut = MetricLocalSet.fromMap(map);
        assertEquals(uut.getIdentifiers().size(), 6);
        assertEquals(uut.getDisplayName(), "Metric");
        assertEquals(uut.getDisplayableValue(), "[Metric]");
        checkMetricName(uut);
        checkMetricVersion(uut);
        checkMetricImplementer(uut);
        checkMetricParameters(uut);
        checkMetricTime(uut);
        checkMetricValue(uut);
        assertEquals(uut.getBytes(), bytes);
    }

    private void checkMetricName(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricName));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricName);
        assertTrue(value instanceof MetricName);
        MetricName metricName = (MetricName) value;
        assertEquals(metricName.getDisplayableValue(), "RER");
    }

    private void checkMetricVersion(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricVersion));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricVersion);
        assertTrue(value instanceof MetricVersion);
        MetricVersion metricVersion = (MetricVersion) value;
        assertEquals(metricVersion.getDisplayableValue(), "1.38");
    }

    private void checkMetricImplementer(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricImplementer));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricImplementer);
        assertTrue(value instanceof MetricImplementer);
        MetricImplementer metricImplementer = (MetricImplementer) value;
        assertEquals(metricImplementer.getDisplayableValue(), "My Org - My group");
    }

    private void checkMetricParameters(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricParameters));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricParameters);
        assertTrue(value instanceof MetricParameters);
        MetricParameters metricParameters = (MetricParameters) value;
        assertEquals(metricParameters.getDisplayableValue(), "ü,AB:324");
    }

    private void checkMetricTime(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricTime));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricTime);
        assertTrue(value instanceof MetricTime);
        MetricTime metricTime = (MetricTime) value;
        assertEquals(metricTime.getDisplayableValue(), "1624592992065536");
    }

    private void checkMetricValue(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricValue));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricValue);
        assertTrue(value instanceof MetricValue);
        MetricValue metricValue = (MetricValue) value;
        assertEquals(metricValue.getDisplayableValue(), "0.34");
        assertEquals(metricValue.getValue(), 0.34, 0.001);
    }

    @Test
    public void withUnknownTag() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x03,
                    (byte) 0x52,
                    (byte) 0x45,
                    (byte) 0x52,
                    0x02,
                    0x04,
                    (byte) 0x31,
                    (byte) 0x2E,
                    (byte) 0x33,
                    (byte) 0x38,
                    0x7F,
                    0x01,
                    (byte) 0x4D,
                    0x06,
                    0x04,
                    (byte) 0x3e,
                    (byte) 0xae,
                    (byte) 0x14,
                    (byte) 0x7b
                };
        this.verifyNoLoggerMessages();
        MetricLocalSet uut = MetricLocalSet.fromBytes(bytes);
        this.verifySingleLoggerMessage("Unsupported/unknown ST 1108 Metric Local Set tag: 127");
        assertEquals(uut.getIdentifiers().size(), 3);
        checkMetricName(uut);
        checkMetricVersion(uut);
        checkMetricValue(uut);
        assertEquals(
                uut.getBytes(),
                new byte[] {
                    0x01,
                    0x03,
                    (byte) 0x52,
                    (byte) 0x45,
                    (byte) 0x52,
                    0x02,
                    0x04,
                    (byte) 0x31,
                    (byte) 0x2E,
                    (byte) 0x33,
                    (byte) 0x38,
                    0x06,
                    0x04,
                    (byte) 0x3e,
                    (byte) 0xae,
                    (byte) 0x14,
                    (byte) 0x7b
                });
    }
}
