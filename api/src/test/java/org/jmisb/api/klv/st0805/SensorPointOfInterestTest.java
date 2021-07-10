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
import org.jmisb.api.klv.st0601.FrameCenterElevation;
import org.jmisb.api.klv.st0601.FrameCenterLatitude;
import org.jmisb.api.klv.st0601.FrameCenterLongitude;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.TargetLocationElevation;
import org.jmisb.api.klv.st0601.TargetLocationLatitude;
import org.jmisb.api.klv.st0601.TargetLocationLongitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.testng.annotations.Test;

/** SensorPointOfInterest unit tests. */
public class SensorPointOfInterestTest {

    public SensorPointOfInterestTest() {}

    @Test
    public void serialiseEmpty() {
        Clock clock = Clock.fixed(Instant.parse("2021-07-13T10:22:26.935488Z"), ZoneOffset.UTC);
        SensorPointOfInterest uut = new SensorPointOfInterest(clock);
        assertEquals(
                uut.toXml(),
                "<?xml version='1.0' standalone='yes'?><event version='2.0' type='b-m-p-s-p-i' uid='jmisb' how='m'><detail><_flow-tags_ ST0601CoT='2021-07-13T10:22:26.935488Z'/></detail></event>");
    }

    @Test
    public void checkSPINoLatitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(135.2));
        map.put(UasDatalinkTag.TargetLocationElevation, new TargetLocationElevation(200.5));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkSPINoLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-40.2));
        map.put(UasDatalinkTag.TargetLocationElevation, new TargetLocationElevation(200.5));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkSPINoAltitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-40.2));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(135.2));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkSPINoLatitudeFrameCentre() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(135.2));
        map.put(UasDatalinkTag.FrameCenterHae, new FrameCenterElevation(200.5));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkSPINoLongitudeFrameCentre() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-40.2));
        map.put(UasDatalinkTag.FrameCenterHae, new FrameCenterElevation(200.5));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkSPINoAltitudeFrameCentre() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-40.2));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(135.2));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertNull(spi.getPoint());
    }

    @Test
    public void checkUid() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "SomePlat"));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "MissionFoo"));
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "SensorTest"));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(spi.getUid(), "SomePlat_MissionFoo_SensorTest");
        assertEquals(spi.getLink().getLinkUid(), "SomePlat_MissionFoo");
    }

    @Test
    public void checkUidNoMissionId() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "SomePlat"));
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "SensorTest"));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(spi.getUid(), "jmisb_SensorTest");
        assertEquals(spi.getLink().getLinkUid(), "jmisb");
    }

    @Test
    public void checkUidNoPlatform() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "MissionFoo"));
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "SensorTest"));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(spi.getUid(), "jmisb_SensorTest");
        assertEquals(spi.getLink().getLinkUid(), "jmisb");
    }

    @Test
    public void checkUidNoSensor() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "SomePlat"));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "MissionFoo"));
        KlvToCot converter = new KlvToCot();
        SensorPointOfInterest spi = converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(spi.getUid(), "SomePlat_MissionFoo_UNKNOWN");
        assertEquals(spi.getLink().getLinkUid(), "SomePlat_MissionFoo");
    }
}
