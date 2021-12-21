package org.jmisb.api.klv.st0809;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST 0809 Meteorological Metadata Local Set. */
public class MeteorologicalMetadataLocalSetTest extends LoggerChecks {
    public MeteorologicalMetadataLocalSetTest() {
        super(MeteorologicalMetadataLocalSet.class);
    }

    @Test
    public void parseMinimumTags() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02
                };
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkTimeStamp(localSet);
        checkVersion(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.MeteorologicalMetadataLocalSetUl);
    }

    private void checkTimeStamp(MeteorologicalMetadataLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(MeteorologicalMetadataKey.PrecisionTimeStamp));
        IMeteorologicalMetadataValue v =
                localSet.getField(MeteorologicalMetadataKey.PrecisionTimeStamp);
        assertEquals(v.getDisplayName(), "Precision Time Stamp");
        assertEquals(v.getDisplayableValue(), "987654321000000");
        assertEquals(
                v.getBytes(),
                new byte[] {
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40
                });
        assertTrue(v instanceof PrecisionTimeStamp);
        PrecisionTimeStamp timestamp =
                (PrecisionTimeStamp)
                        localSet.getField(MeteorologicalMetadataKey.PrecisionTimeStamp);
        assertEquals(timestamp.getMicroseconds(), 987654321000000l);
    }

    private void checkVersion(MeteorologicalMetadataLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(MeteorologicalMetadataKey.Version));
        IMeteorologicalMetadataValue v = localSet.getField(MeteorologicalMetadataKey.Version);
        assertEquals(v.getDisplayName(), "Version");
        assertEquals(v.getDisplayableValue(), "ST 0809.2");
        assertEquals(v.getBytes(), new byte[] {0x00, 0x02});
        assertTrue(v instanceof ST0809Version);
        ST0809Version version =
                (ST0809Version) localSet.getField(MeteorologicalMetadataKey.Version);
        assertEquals(version.getVersion(), 2);
        ST0809Version versionFromIKlvKey =
                (ST0809Version) localSet.getField((IKlvKey) MeteorologicalMetadataKey.Version);
        assertEquals(versionFromIKlvKey.getVersion(), 2);
    }

    @Test
    public void parseSimpleValues() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x10,
                    (byte) 0x04,
                    (byte) 0x41,
                    (byte) 0x20,
                    (byte) 0x00,
                    (byte) 0x00
                };
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 3);
        checkTimeStamp(localSet);
        checkVersion(localSet);
        checkTemperature(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.MeteorologicalMetadataLocalSetUl);
    }

    private void checkTemperature(MeteorologicalMetadataLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(MeteorologicalMetadataKey.Temperature));
        IMeteorologicalMetadataValue v = localSet.getField(MeteorologicalMetadataKey.Temperature);
        assertEquals(v.getDisplayName(), "Temperature");
        assertEquals(v.getDisplayableValue(), "10.00°C");
        assertTrue(v instanceof Temperature);
        Temperature temperature =
                (Temperature) localSet.getField(MeteorologicalMetadataKey.Temperature);
        assertEquals(temperature.getTemperature(), 10.0f);
        Temperature versionFromIKlvKey =
                (Temperature) localSet.getField((IKlvKey) MeteorologicalMetadataKey.Temperature);
        assertEquals(versionFromIKlvKey.getTemperature(), 10.0f);
    }

    private void checkBarometricPressure(MeteorologicalMetadataLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(MeteorologicalMetadataKey.BarometricPressure));
        IMeteorologicalMetadataValue v =
                localSet.getField(MeteorologicalMetadataKey.BarometricPressure);
        assertEquals(v.getDisplayName(), "Barometric Pressure");
        assertEquals(v.getDisplayableValue(), "101320.00 Pa");
        assertTrue(v instanceof BarometricPressure);
        BarometricPressure barometricPressure =
                (BarometricPressure)
                        localSet.getField(MeteorologicalMetadataKey.BarometricPressure);
        assertEquals(barometricPressure.getPressure(), 101320.0f);
    }

    @Test
    public void buildSimpleSetFromValues() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x14,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x10,
                    (byte) 0x04,
                    (byte) 0x41,
                    (byte) 0x20,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Map<MeteorologicalMetadataKey, IMeteorologicalMetadataValue> values = new HashMap<>();
        values.put(
                MeteorologicalMetadataKey.Version,
                new ST0809Version(MeteorologicalMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                MeteorologicalMetadataKey.PrecisionTimeStamp,
                new PrecisionTimeStamp(987654321000000l));
        values.put(MeteorologicalMetadataKey.Temperature, new Temperature(10.0f));
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 3);
        checkTimeStamp(localSet);
        checkVersion(localSet);
        checkTemperature(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.MeteorologicalMetadataLocalSetUl);
    }

    @Test
    public void buildSetFromValues() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x1A,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02,
                    (byte) 0x06,
                    (byte) 0x04,
                    (byte) 0x47,
                    (byte) 0xc5,
                    (byte) 0xe4,
                    (byte) 0x00,
                    (byte) 0x10,
                    (byte) 0x04,
                    (byte) 0x41,
                    (byte) 0x20,
                    (byte) 0x00,
                    (byte) 0x00
                };
        Map<MeteorologicalMetadataKey, IMeteorologicalMetadataValue> values = new HashMap<>();
        values.put(
                MeteorologicalMetadataKey.Version,
                new ST0809Version(MeteorologicalMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                MeteorologicalMetadataKey.PrecisionTimeStamp,
                new PrecisionTimeStamp(987654321000000l));
        values.put(MeteorologicalMetadataKey.Temperature, new Temperature(10.0f));
        values.put(MeteorologicalMetadataKey.BarometricPressure, new BarometricPressure(101320.0f));
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 4);
        checkTimeStamp(localSet);
        checkVersion(localSet);
        checkTemperature(localSet);
        checkBarometricPressure(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.MeteorologicalMetadataLocalSetUl);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp =
                    "ST 0809 Meteorological Metadata Local Set cannot be nested.")
    public void checkNestedThrows() {
        Map<MeteorologicalMetadataKey, IMeteorologicalMetadataValue> values = new HashMap<>();
        values.put(
                MeteorologicalMetadataKey.Version,
                new ST0809Version(MeteorologicalMetadataConstants.ST_VERSION_NUMBER));
        values.put(
                MeteorologicalMetadataKey.PrecisionTimeStamp,
                new PrecisionTimeStamp(987654321000000l));
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(values);
        localSet.frameMessage(true);
    }

    @Test
    public void parseWithUnknownTag() throws KlvParseException {
        final byte[] bytes =
                new byte[] {
                    (byte) 0x06,
                    (byte) 0x0E,
                    (byte) 0x2B,
                    (byte) 0x34,
                    (byte) 0x02,
                    (byte) 0x2B,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x01,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x0E,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x13,
                    (byte) 0x01,
                    (byte) 0x08,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x40,
                    (byte) 0x03,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x00,
                    (byte) 0x02
                };
        MeteorologicalMetadataLocalSet localSet = new MeteorologicalMetadataLocalSet(bytes);
        this.verifySingleLoggerMessage("Unknown Meteorological Metadata tag: 64");
        assertNotNull(localSet);
        assertEquals(localSet.getIdentifiers().size(), 2);
        checkTimeStamp(localSet);
        checkVersion(localSet);
        assertEquals(localSet.displayHeader(), "ST 0809 Meteorological Metadata");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.MeteorologicalMetadataLocalSetUl);
    }

    @Test
    public void constructUnknown() throws KlvParseException {
        verifyNoLoggerMessages();
        IMeteorologicalMetadataValue unknown =
                MeteorologicalMetadataLocalSet.createValue(
                        MeteorologicalMetadataKey.Undefined, new byte[] {0x01, 0x02});
        this.verifySingleLoggerMessage("Unknown Meteorological Metadata tag: Undefined");
        assertNull(unknown);
    }
}
