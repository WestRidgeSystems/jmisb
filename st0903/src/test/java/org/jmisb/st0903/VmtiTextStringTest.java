package org.jmisb.st0903;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.st0903.shared.EncodingMode;
import org.jmisb.st0903.shared.VmtiTextString;
import org.testng.annotations.Test;

/** Tests for text strings (ST0903 Tag 3 - System Name and Tag 10 - Source Sensor). */
public class VmtiTextStringTest {
    @Test
    public void testConstructFromValue() {
        VmtiTextString systemName =
                new VmtiTextString(VmtiTextString.SYSTEM_NAME, "DSTO_ADSS_VMTI");
        assertEquals(
                systemName.getBytes(),
                new byte[] {
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x54,
                    (byte) 0x4F,
                    (byte) 0x5F,
                    (byte) 0x41,
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x53,
                    (byte) 0x5F,
                    (byte) 0x56,
                    (byte) 0x4D,
                    (byte) 0x54,
                    (byte) 0x49
                });
        assertEquals(systemName.getDisplayName(), "System Name/Description");
        assertEquals(systemName.getDisplayableValue(), "DSTO_ADSS_VMTI");
        assertEquals(systemName.getValue(), "DSTO_ADSS_VMTI");
    }

    @Test
    public void testConstructFromEncodedBytes() {
        VmtiTextString systemName =
                new VmtiTextString(
                        VmtiTextString.SYSTEM_NAME,
                        new byte[] {
                            (byte) 0x44,
                            (byte) 0x53,
                            (byte) 0x54,
                            (byte) 0x4F,
                            (byte) 0x5F,
                            (byte) 0x41,
                            (byte) 0x44,
                            (byte) 0x53,
                            (byte) 0x53,
                            (byte) 0x5F,
                            (byte) 0x56,
                            (byte) 0x4D,
                            (byte) 0x54,
                            (byte) 0x49
                        });
        assertEquals(
                systemName.getBytes(),
                new byte[] {
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x54,
                    (byte) 0x4F,
                    (byte) 0x5F,
                    (byte) 0x41,
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x53,
                    (byte) 0x5F,
                    (byte) 0x56,
                    (byte) 0x4D,
                    (byte) 0x54,
                    (byte) 0x49
                });
        assertEquals(systemName.getDisplayName(), "System Name/Description");
        assertEquals(systemName.getDisplayableValue(), "DSTO_ADSS_VMTI");
        assertEquals(systemName.getValue(), "DSTO_ADSS_VMTI");
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.SystemName,
                        new byte[] {
                            (byte) 0x44,
                            (byte) 0x53,
                            (byte) 0x54,
                            (byte) 0x4F,
                            (byte) 0x5F,
                            (byte) 0x41,
                            (byte) 0x44,
                            (byte) 0x53,
                            (byte) 0x53,
                            (byte) 0x5F,
                            (byte) 0x56,
                            (byte) 0x4D,
                            (byte) 0x54,
                            (byte) 0x49
                        },
                        EncodingMode.IMAPB);
        assertTrue(value instanceof VmtiTextString);
        VmtiTextString systemName = (VmtiTextString) value;
        assertEquals(
                systemName.getBytes(),
                new byte[] {
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x54,
                    (byte) 0x4F,
                    (byte) 0x5F,
                    (byte) 0x41,
                    (byte) 0x44,
                    (byte) 0x53,
                    (byte) 0x53,
                    (byte) 0x5F,
                    (byte) 0x56,
                    (byte) 0x4D,
                    (byte) 0x54,
                    (byte) 0x49
                });
        assertEquals(systemName.getDisplayName(), "System Name/Description");
        assertEquals(systemName.getDisplayableValue(), "DSTO_ADSS_VMTI");
        assertEquals(systemName.getValue(), "DSTO_ADSS_VMTI");
    }

    @Test
    public void testConstructFromValueSourceSensor() {
        VmtiTextString sourceSensor = new VmtiTextString(VmtiTextString.SOURCE_SENSOR, "EO Nose");
        assertEquals(
                sourceSensor.getBytes(),
                new byte[] {
                    (byte) 0x45,
                    (byte) 0x4F,
                    (byte) 0x20,
                    (byte) 0x4E,
                    (byte) 0x6F,
                    (byte) 0x73,
                    (byte) 0x65
                });
        assertEquals(sourceSensor.getDisplayName(), "Source Sensor");
        assertEquals(sourceSensor.getDisplayableValue(), "EO Nose");
        assertEquals(sourceSensor.getValue(), "EO Nose");
    }

    @Test
    public void testConstructFromEncodedBytesSourceSensor() {
        VmtiTextString sourceSensor =
                new VmtiTextString(
                        VmtiTextString.SOURCE_SENSOR,
                        new byte[] {
                            (byte) 0x45,
                            (byte) 0x4F,
                            (byte) 0x20,
                            (byte) 0x4E,
                            (byte) 0x6F,
                            (byte) 0x73,
                            (byte) 0x65
                        });
        assertEquals(
                sourceSensor.getBytes(),
                new byte[] {
                    (byte) 0x45,
                    (byte) 0x4F,
                    (byte) 0x20,
                    (byte) 0x4E,
                    (byte) 0x6F,
                    (byte) 0x73,
                    (byte) 0x65
                });
        assertEquals(sourceSensor.getDisplayName(), "Source Sensor");
        assertEquals(sourceSensor.getDisplayableValue(), "EO Nose");
        assertEquals(sourceSensor.getValue(), "EO Nose");
    }

    @Test
    public void testFactoryEncodedBytesSourceSensor() throws KlvParseException {
        IVmtiMetadataValue value =
                VmtiLocalSet.createValue(
                        VmtiMetadataKey.SourceSensor,
                        new byte[] {
                            (byte) 0x45,
                            (byte) 0x4F,
                            (byte) 0x20,
                            (byte) 0x4E,
                            (byte) 0x6F,
                            (byte) 0x73,
                            (byte) 0x65
                        },
                        EncodingMode.IMAPB);
        assertTrue(value instanceof VmtiTextString);
        VmtiTextString sourceSensor = (VmtiTextString) value;
        assertEquals(
                sourceSensor.getBytes(),
                new byte[] {
                    (byte) 0x45,
                    (byte) 0x4F,
                    (byte) 0x20,
                    (byte) 0x4E,
                    (byte) 0x6F,
                    (byte) 0x73,
                    (byte) 0x65
                });
        assertEquals(sourceSensor.getDisplayName(), "Source Sensor");
        assertEquals(sourceSensor.getDisplayableValue(), "EO Nose");
        assertEquals(sourceSensor.getValue(), "EO Nose");
    }
}
