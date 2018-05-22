package org.jmisb.api.klv.st0805;

import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;

/**
 * Perform KLV to CoT conversion as defined by ST 0805
 */
public class KlvToCot
{
    private static String platformType = "a-f-A";

    private KlvToCot() {}

    /**
     * Convert a MISB UAS Datalink message to a CoT Sensor Point of Interest (SPI) message
     *
     * @param uasMessage The UAS Datalink message to convert
     *
     * @return The CoT message
     */
    public static SensorPointOfInterest getSensorPointOfInterest(UasDatalinkMessage uasMessage)
    {
        SensorPointOfInterest spiMessage = new SensorPointOfInterest();

        // The standard is unclear, but seems to indicate target position is preferred over frame center
        Double targetLat = (Double)uasMessage.getField(UasDatalinkTag.TargetLocationLatitude);
        Double targetLon = (Double)uasMessage.getField(UasDatalinkTag.TargetLocationLongitude);
        Double targetAlt = (Double)uasMessage.getField(UasDatalinkTag.TargetLocationElevation);

        if (targetLat != null && targetLon != null && targetAlt != null)
        {
            spiMessage.setPointLat(targetLat);
            spiMessage.setPointLon(targetLon);
            // TODO: convert MSL -> HAE
            spiMessage.setPointHae(targetAlt);
        }
        else
        {
            // Try frame center
            Double frameCenterLat = (Double)uasMessage.getField(UasDatalinkTag.FrameCenterLatitude);
            Double frameCenterLon = (Double)uasMessage.getField(UasDatalinkTag.FrameCenterLongitude);
            Double frameCenterAlt = (Double)uasMessage.getField(UasDatalinkTag.FrameCenterElevation);

            if (frameCenterLat != null && frameCenterLon != null && frameCenterAlt != null)
            {
                spiMessage.setPointLat(frameCenterLat);
                spiMessage.setPointLon(frameCenterLon);
                // TODO: convert MSL -> HAE
                spiMessage.setPointHae(frameCenterAlt);
            }
        }

        Double targetErrorCe = (Double)uasMessage.getField(UasDatalinkTag.TargetErrorCe90);
        if (targetErrorCe != null)
        {
            // Conversion from 2.146-sigma to 1-sigma necessary
            spiMessage.setPointCe(targetErrorCe / 2.146);
        }
        else
        {
            spiMessage.setPointCe(9_999_999);
        }

        Double targetErrorLe = (Double)uasMessage.getField(UasDatalinkTag.TargetErrorLe90);
        if (targetErrorLe != null)
        {
            // Conversion from 1.645-sigma to 1-sigma necessary
            spiMessage.setPointLe(targetErrorLe / 1.645);
        }
        else
        {
            spiMessage.setPointLe(9_999_999);
        }

        spiMessage.setType("b-m-p-s-p-i");

        // TODO: allow client to specify UID & handle missing tags from KLV
        String platformDesignation = (String)uasMessage.getField(UasDatalinkTag.PlatformDesignation);
        String missionId = (String)uasMessage.getField(UasDatalinkTag.MissionId);
        String imageSourceSensor = (String)uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        if (platformDesignation != null || missionId != null || imageSourceSensor != null)
        {
            spiMessage.setUid(platformDesignation + "_" + missionId + "_" + imageSourceSensor);
        }

        setTimes(uasMessage, spiMessage);

        spiMessage.setHow("m-p");

        // TODO: allow client to specify platform type
        spiMessage.setLinkType(platformType);

        // TODO: allow client to specify platform UID
        spiMessage.setLinkUid(getPlatformUid(uasMessage));

        return spiMessage;
    }

    /**
     * Convert a UAS Datalink message to a CoT Platform Position message
     *
     * @param uasMessage The UAS Datalink message to convert
     *
     * @return The CoT message
     */
    public static PlatformPosition getPlatformPosition(UasDatalinkMessage uasMessage)
    {
        PlatformPosition platformMessage = new PlatformPosition();

        double pointLat = (double)uasMessage.getField(UasDatalinkTag.SensorLatitude);
        platformMessage.setPointLat(pointLat);

        double pointLon = (double)uasMessage.getField(UasDatalinkTag.SensorLongitude);
        platformMessage.setPointLon(pointLon);

        // TODO: convert MSL -> HAE
        double pointHae = (double)uasMessage.getField(UasDatalinkTag.SensorTrueAltitude);
        platformMessage.setPointHae(pointHae);

        // Represents "no value given" - ST 0601 does not contain platform uncertainty
        platformMessage.setPointCe(9_999_999);
        platformMessage.setPointLe(9_999_999);

        // TODO: allow client to specify platform type
        platformMessage.setType(platformType);

        // TODO: allow client to specify UID & handle missing tags from KLV
        platformMessage.setUid(getPlatformUid(uasMessage));

        setTimes(uasMessage, platformMessage);

        platformMessage.setHow("m-p");

        // Sensor absolute azimuth obtained by adding platform heading and sensor relative azimuth
        Double platformHeading = (Double)uasMessage.getField(UasDatalinkTag.PlatformHeadingAngle);
        Double sensorRelativeAzimuth = (Double)uasMessage.getField(UasDatalinkTag.SensorRelativeAzimuthAngle);
        if (platformHeading != null && sensorRelativeAzimuth != null)
        {
            platformMessage.setSensorAzimuth((platformHeading + sensorRelativeAzimuth) % 360);
        }

        Double fov = (Double)uasMessage.getField(UasDatalinkTag.SensorHorizontalFov);
        if (fov != null)
        {
            platformMessage.setSensorFov(fov);
        }

        Double vfov = (Double)uasMessage.getField(UasDatalinkTag.SensorVerticalFov);
        if (vfov != null)
        {
            platformMessage.setSensorVfov(vfov);
        }

        String sensor = (String)uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        platformMessage.setSensorModel(sensor);

        Double slantRange = (Double)uasMessage.getField(UasDatalinkTag.SlantRange);
        if (slantRange != null)
        {
            platformMessage.setSensorRange(slantRange);
        }

        return platformMessage;
    }

    private static String getPlatformUid(UasDatalinkMessage uasMessage)
    {
        // TODO: allow client to specify UID & handle missing tags from KLV
        String platformDesignation = (String)uasMessage.getField(UasDatalinkTag.PlatformDesignation);
        String missionId = (String)uasMessage.getField(UasDatalinkTag.MissionId);
        if (platformDesignation != null || missionId != null)
        {
            return platformDesignation + "_" + missionId;
        }
        else
        {
            return "jmisb";
        }
    }

    private static void setTimes(UasDatalinkMessage uasMessage, CotMessage cotMessage)
    {
        long unixTimeStamp = (long)uasMessage.getField(UasDatalinkTag.PrecisionTimeStamp);
        cotMessage.setTime(unixTimeStamp);
        cotMessage.setStart(unixTimeStamp);

        // TODO: allow client to specify stale time
        cotMessage.setStale(unixTimeStamp + 5_000_000);
    }
}

