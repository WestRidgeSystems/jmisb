package org.jmisb.st0806;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for TelemetryAccuracyIndicator (ST0806 Tag 5). */
public class TelemetryAccuracyIndicatorTest {
    @Test
    public void testConstructFromValue() {
        TelemetryAccuracyIndicator version = new TelemetryAccuracyIndicator(4);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "4");
        assertEquals(version.getValue(), 4);
    }

    @Test
    public void testConstructFromValue128() {
        TelemetryAccuracyIndicator version = new TelemetryAccuracyIndicator(128);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x80});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "128");
        assertEquals(version.getValue(), 128);
    }

    @Test
    public void testConstructFromValue255() {
        TelemetryAccuracyIndicator version = new TelemetryAccuracyIndicator(255);
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "255");
        assertEquals(version.getValue(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        TelemetryAccuracyIndicator version =
                new TelemetryAccuracyIndicator(new byte[] {(byte) 0x04});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "4");
        assertEquals(version.getValue(), 4);
    }

    @Test
    public void testConstructFromEncodedBytes0() {
        TelemetryAccuracyIndicator version =
                new TelemetryAccuracyIndicator(new byte[] {(byte) 0x00});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x00});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "0");
        assertEquals(version.getValue(), 0);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        TelemetryAccuracyIndicator version =
                new TelemetryAccuracyIndicator(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "255");
        assertEquals(version.getValue(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IRvtMetadataValue value =
                RvtLocalSet.createValue(
                        RvtMetadataKey.TelemetryAccuracyIndicator, new byte[] {(byte) 0x04});
        assertTrue(value instanceof TelemetryAccuracyIndicator);
        TelemetryAccuracyIndicator version = (TelemetryAccuracyIndicator) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Telemetry Accuracy Indicator");
        assertEquals(version.getDisplayableValue(), "4");
        assertEquals(version.getValue(), 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new TelemetryAccuracyIndicator(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new TelemetryAccuracyIndicator(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new TelemetryAccuracyIndicator(new byte[] {0x01, 0x02});
    }
}
