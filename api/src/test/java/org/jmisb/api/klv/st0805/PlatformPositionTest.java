package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PlatformHeadingAngle;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeight;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SensorRelativeAzimuth;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.testng.annotations.Test;

/** PlatformPosition unit tests. */
public class PlatformPositionTest {

    public PlatformPositionTest() {}

    @Test
    public void serialiseEmpty() {
        Clock clock = Clock.fixed(Instant.parse("2021-07-13T10:22:26.935488Z"), ZoneOffset.UTC);
        PlatformPosition uut = new PlatformPosition(clock);
        assertEquals(
                uut.toXml(),
                "<?xml version='1.0' standalone='yes'?><event version='2.0' type='a-f' uid='jmisb' how='m'><detail><_flow-tags_ ST0601CoT='2021-07-13T10:22:26.935488Z'/></detail></event>");
    }

    @Test
    public void serialise() {
        Clock clock = Clock.fixed(Instant.parse("2021-07-13T10:22:26.935488Z"), ZoneOffset.UTC);
        PlatformPosition uut = new PlatformPosition(clock);
        CotSensor sensor = new CotSensor();
        sensor.setModel("EOW");
        uut.setSensor(sensor);
        uut.setStart(1625389203000000L);
        uut.setTime(1625389203000000L);
        uut.setStale(1625389213000000L);
        uut.setUid("PaperAeroplane.3");
        uut.setHow("m-p");
        assertEquals(
                uut.toXml(),
                "<?xml version='1.0' standalone='yes'?><event version='2.0' type='a-f' uid='PaperAeroplane.3' time='2021-07-04T09:00:03.000Z' start='2021-07-04T09:00:03.000Z' stale='2021-07-04T09:00:13.000Z' how='m-p'><detail><_flow-tags_ ST0601CoT='2021-07-13T10:22:26.935488Z'/><sensor model='EOW'/></detail></event>");
    }

    @Test
    public void checkPlatformPositionNoLatitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoLatitudeOrLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(145));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoAlt() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkPlatformPositionSensorAzimuthIncomplete() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
        map.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(32.42));
        KlvToCot converter = new KlvToCot();
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
        assertNull(platformPosition.getSensor().getAzimuth());
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkPlatformPositionSensorAzimuthNoHeading() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(20.0));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getSensor().getAzimuth());
    }

    @Test
    public void checkPlatformPositionUidNoPlatformDesignation() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "Mission2"));
        KlvToCot converter = new KlvToCot();
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkPlatformPositionUidNoMissionId() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "SomePlat"));
        KlvToCot converter = new KlvToCot();
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkTimes() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        KlvToCot converter = new KlvToCot();
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNotNull(platformPosition.getTime());
        assertNotNull(platformPosition.getStart());
        assertNotNull(platformPosition.getStale());
    }

    @Test
    public void checkTimesMissing() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        KlvToCot converter = new KlvToCot();
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getTime());
        assertNull(platformPosition.getStart());
        assertNull(platformPosition.getStale());
    }
}
