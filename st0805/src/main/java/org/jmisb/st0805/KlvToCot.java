package org.jmisb.st0805;

import java.time.Clock;
import org.jmisb.st0601.FrameCenterElevation;
import org.jmisb.st0601.FrameCenterLatitude;
import org.jmisb.st0601.FrameCenterLongitude;
import org.jmisb.st0601.HorizontalFov;
import org.jmisb.st0601.PlatformHeadingAngle;
import org.jmisb.st0601.PrecisionTimeStamp;
import org.jmisb.st0601.SensorEllipsoidHeight;
import org.jmisb.st0601.SensorEllipsoidHeightExtended;
import org.jmisb.st0601.SensorLatitude;
import org.jmisb.st0601.SensorLongitude;
import org.jmisb.st0601.SensorRelativeAzimuth;
import org.jmisb.st0601.SensorTrueAltitude;
import org.jmisb.st0601.SlantRange;
import org.jmisb.st0601.TargetErrorEstimateCe90;
import org.jmisb.st0601.TargetErrorEstimateLe90;
import org.jmisb.st0601.TargetLocationElevation;
import org.jmisb.st0601.TargetLocationLatitude;
import org.jmisb.st0601.TargetLocationLongitude;
import org.jmisb.st0601.UasDatalinkMessage;
import org.jmisb.st0601.UasDatalinkString;
import org.jmisb.st0601.UasDatalinkTag;
import org.jmisb.st0601.VerticalFov;

/** Perform KLV to CoT conversion as defined by ST 0805. */
public class KlvToCot {
    private static final String PARENT_PRODUCER_RELATIONSHIP = "p-p";
    private final ConversionConfiguration configuration;

    /**
     * Constructor.
     *
     * @param conversionConfiguration custom configuration options
     */
    public KlvToCot(ConversionConfiguration conversionConfiguration) {
        configuration = conversionConfiguration;
    }

    /**
     * Constructor.
     *
     * <p>This version uses a reasonable default configuration, but customization is usually worth
     * it if data is available.
     */
    public KlvToCot() {
        this(new ConversionConfiguration());
    }

    /**
     * Convert a MISB UAS Datalink message to a CoT Sensor Point of Interest (SPI) message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @return The CoT message
     */
    public SensorPointOfInterest getSensorPointOfInterest(UasDatalinkMessage uasMessage) {
        return getSensorPointOfInterest(uasMessage, Clock.systemUTC());
    }

    /**
     * Convert a MISB UAS Datalink message to a CoT Sensor Point of Interest (SPI) message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @param clock the clock to use when creating the CoT message
     * @return The CoT message
     */
    public SensorPointOfInterest getSensorPointOfInterest(
            UasDatalinkMessage uasMessage, Clock clock) {
        SensorPointOfInterest spiMessage = new SensorPointOfInterest(clock);
        // The standard suggests target position is preferred over frame center
        TargetLocationLatitude targetLat =
                (TargetLocationLatitude) uasMessage.getField(UasDatalinkTag.TargetLocationLatitude);
        TargetLocationLongitude targetLon =
                (TargetLocationLongitude)
                        uasMessage.getField(UasDatalinkTag.TargetLocationLongitude);
        TargetLocationElevation targetAlt =
                (TargetLocationElevation)
                        uasMessage.getField(UasDatalinkTag.TargetLocationElevation);
        if (targetLat != null && targetLon != null && targetAlt != null) {
            CotPoint point = new CotPoint();
            point.setLat(targetLat.getDegrees());
            point.setLon(targetLon.getDegrees());
            // TODO: convert MSL -> HAE
            point.setHae(targetAlt.getMeters());
            spiMessage.setPoint(point);
        } else {
            // Try frame center
            FrameCenterLatitude frameCenterLat =
                    (FrameCenterLatitude) uasMessage.getField(UasDatalinkTag.FrameCenterLatitude);
            FrameCenterLongitude frameCenterLon =
                    (FrameCenterLongitude) uasMessage.getField(UasDatalinkTag.FrameCenterLongitude);
            FrameCenterElevation frameCenterAlt =
                    (FrameCenterElevation) uasMessage.getField(UasDatalinkTag.FrameCenterElevation);
            if (frameCenterLat != null && frameCenterLon != null && frameCenterAlt != null) {
                CotPoint point = new CotPoint();
                point.setLat(frameCenterLat.getDegrees());
                point.setLon(frameCenterLon.getDegrees());
                // TODO: convert MSL -> HAE
                point.setHae(frameCenterAlt.getMeters());
                spiMessage.setPoint(point);
            }
        }
        if (spiMessage.getPoint() != null) {
            TargetErrorEstimateCe90 targetErrorCe =
                    (TargetErrorEstimateCe90) uasMessage.getField(UasDatalinkTag.TargetErrorCe90);
            if (targetErrorCe != null) { // Conversion from 2.146-sigma to 1-sigma necessary
                spiMessage.getPoint().setCe(targetErrorCe.getMetres() / 2.146);
            } else {
                spiMessage.getPoint().setCe(9_999_999);
            }
            TargetErrorEstimateLe90 targetErrorLe =
                    (TargetErrorEstimateLe90) uasMessage.getField(UasDatalinkTag.TargetErrorLe90);
            if (targetErrorLe != null) { // Conversion from 1.645-sigma to 1-sigma necessary
                spiMessage.getPoint().setLe(targetErrorLe.getMetres() / 1.645);
            } else {
                spiMessage.getPoint().setLe(9_999_999);
            }
        }
        spiMessage.setType("b-m-p-s-p-i");
        spiMessage.setUid(buildSensorUid(uasMessage));
        setTimes(uasMessage, spiMessage);
        spiMessage.setHow("m-p");
        spiMessage.setLink(buildLink(uasMessage));
        return spiMessage;
    }

    private Link buildLink(UasDatalinkMessage uasMessage) {
        Link link = new Link();
        link.setLinkType(configuration.getPlatformType());
        link.setLinkUid(getPlatformUid(uasMessage));
        link.setLinkRelation(PARENT_PRODUCER_RELATIONSHIP);
        return link;
    }

    private String buildSensorUid(UasDatalinkMessage uasMessage) {
        if (configuration.getSensorUidOverride() != null) {
            return configuration.getSensorUidOverride();
        }
        String platformUid = getPlatformUid(uasMessage);
        String sensorSuffix = configuration.getSensorSuffixFallback();
        UasDatalinkString imageSourceSensor =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        if (imageSourceSensor != null) {
            sensorSuffix = imageSourceSensor.getValue();
        }
        return platformUid + "_" + sensorSuffix;
    }

    /**
     * Convert a UAS Datalink message to a CoT Platform Position message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @return The CoT message
     */
    public PlatformPosition getPlatformPosition(UasDatalinkMessage uasMessage) {
        return getPlatformPosition(uasMessage, Clock.systemUTC());
    }

    /**
     * Convert a UAS Datalink message to a CoT Platform Position message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @param clock the clock to use when creating the CoT message
     * @return The CoT message
     */
    public PlatformPosition getPlatformPosition(UasDatalinkMessage uasMessage, Clock clock) {
        PlatformPosition platformMessage = new PlatformPosition(clock);

        SensorLatitude pointLat =
                (SensorLatitude) uasMessage.getField(UasDatalinkTag.SensorLatitude);
        SensorLongitude pointLon =
                (SensorLongitude) uasMessage.getField(UasDatalinkTag.SensorLongitude);
        if ((pointLat != null) && (pointLon != null)) {
            SensorTrueAltitude sensorTrueAltitude =
                    (SensorTrueAltitude) uasMessage.getField(UasDatalinkTag.SensorTrueAltitude);
            SensorEllipsoidHeight sensorEllipsoidHeight =
                    (SensorEllipsoidHeight)
                            uasMessage.getField(UasDatalinkTag.SensorEllipsoidHeight);
            SensorEllipsoidHeightExtended sensorEllipsoidHeightExtended =
                    (SensorEllipsoidHeightExtended)
                            uasMessage.getField(UasDatalinkTag.SensorEllipsoidHeightExtended);
            if ((sensorEllipsoidHeight != null)
                    || (sensorEllipsoidHeightExtended != null)
                    || (sensorTrueAltitude != null)) {
                CotPoint point = new CotPoint();
                point.setLat(pointLat.getDegrees());
                point.setLon(pointLon.getDegrees());
                if (sensorEllipsoidHeightExtended != null) {
                    point.setHae(sensorEllipsoidHeightExtended.getMeters());
                } else if (sensorEllipsoidHeight != null) {
                    point.setHae(sensorEllipsoidHeight.getMeters());
                } else {
                    // TODO: convert MSL -> HAE
                    point.setHae(sensorTrueAltitude.getMeters());
                }
                // Represents "no value given" - ST 0601 does not contain platform uncertainty
                point.setCe(9_999_999);
                point.setLe(9_999_999);
                platformMessage.setPoint(point);
            }
        }

        platformMessage.setType(configuration.getPlatformType());

        platformMessage.setUid(getPlatformUid(uasMessage));

        setTimes(uasMessage, platformMessage);

        platformMessage.setHow("m-p");

        platformMessage.setSensor(buildSensor(uasMessage));

        return platformMessage;
    }

    private CotSensor buildSensor(UasDatalinkMessage uasMessage) {
        // Sensor absolute azimuth obtained by adding platform heading and sensor relative azimuth
        PlatformHeadingAngle platformHeading =
                (PlatformHeadingAngle) uasMessage.getField(UasDatalinkTag.PlatformHeadingAngle);
        SensorRelativeAzimuth sensorRelativeAzimuth =
                (SensorRelativeAzimuth)
                        uasMessage.getField(UasDatalinkTag.SensorRelativeAzimuthAngle);
        CotSensor sensor = new CotSensor();
        if (platformHeading != null && sensorRelativeAzimuth != null) {
            sensor.setAzimuth(
                    (platformHeading.getDegrees() + sensorRelativeAzimuth.getDegrees()) % 360);
        }
        HorizontalFov fov = (HorizontalFov) uasMessage.getField(UasDatalinkTag.SensorHorizontalFov);
        if (fov != null) {
            sensor.setFov(fov.getDegrees());
        }
        VerticalFov vfov = (VerticalFov) uasMessage.getField(UasDatalinkTag.SensorVerticalFov);
        if (vfov != null) {
            sensor.setVerticalFov(vfov.getDegrees());
        }
        UasDatalinkString imageSourceSensor =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        if (imageSourceSensor != null) {
            sensor.setModel(imageSourceSensor.getValue());
        }
        SlantRange slantRange = (SlantRange) uasMessage.getField(UasDatalinkTag.SlantRange);
        if (slantRange != null) {
            sensor.setRange(slantRange.getMeters());
        }
        return sensor;
    }

    private String getPlatformUid(UasDatalinkMessage uasMessage) {
        if (configuration.getPlatformUidOverride() != null) {
            return configuration.getPlatformUidOverride();
        }
        UasDatalinkString platformDesignation =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.PlatformDesignation);
        UasDatalinkString missionId =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.MissionId);
        if (platformDesignation != null && missionId != null) {
            return platformDesignation.getValue() + "_" + missionId.getValue();
        } else {
            return configuration.getPlatformUidFallback();
        }
    }

    private void setTimes(UasDatalinkMessage uasMessage, CotMessage cotMessage) {
        PrecisionTimeStamp unixTimeStamp =
                (PrecisionTimeStamp) uasMessage.getField(UasDatalinkTag.PrecisionTimeStamp);
        if (unixTimeStamp != null) {
            cotMessage.setTime(unixTimeStamp.getMicroseconds());
            cotMessage.setStart(unixTimeStamp.getMicroseconds());
            cotMessage.setStale(unixTimeStamp.getMicroseconds() + configuration.getStalePeriod());
        }
    }
}
