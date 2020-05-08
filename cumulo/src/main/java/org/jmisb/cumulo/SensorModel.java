package org.jmisb.cumulo;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.RotationOrder;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.jmisb.api.klv.st0601.HorizontalFov;
import org.jmisb.api.klv.st0601.PlatformHeadingAngle;
import org.jmisb.api.klv.st0601.PlatformPitchAngle;
import org.jmisb.api.klv.st0601.PlatformRollAngle;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SensorRelativeAzimuth;
import org.jmisb.api.klv.st0601.SensorRelativeElevation;
import org.jmisb.api.klv.st0601.SensorRelativeRoll;
import org.jmisb.api.klv.st0601.SensorTrueAltitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st0601.VerticalFov;

import java.io.IOException;

public class SensorModel {

    private final long precisionTimestamp;
    private final Vector3D ecefPosition;
    private final Rotation platformRotation;
    private final Rotation gimbalRotation;
    private final Camera camera;

    public SensorModel(long precisionTimestamp, Vector3D ecefPosition, Rotation platformRotation,
                       Rotation gimbalRotation, Camera camera)
    {
        this.precisionTimestamp = precisionTimestamp;
        this.ecefPosition = ecefPosition;
        this.platformRotation = platformRotation;
        this.gimbalRotation = gimbalRotation;
        this.camera = camera;
    }

    public SensorModel(UasDatalinkMessage msg) throws IOException {

        // pts
        PrecisionTimeStamp pts = (PrecisionTimeStamp)msg.getField(UasDatalinkTag.PrecisionTimeStamp);
        precisionTimestamp = pts.getMicroseconds();

        // sensor position
        SensorLatitude lat = (SensorLatitude)msg.getField(UasDatalinkTag.SensorLatitude);
        SensorLongitude lon = (SensorLongitude)msg.getField(UasDatalinkTag.SensorLongitude);
        SensorTrueAltitude alt = (SensorTrueAltitude) msg.getField(UasDatalinkTag.SensorTrueAltitude);
        double offset = Geoid.getInstance().getOffset(lat.getDegrees(), lon.getDegrees());
        double[] sensorPosition = {lat.getRadians(), lon.getRadians(), alt.getMeters()+offset};
        ecefPosition = new Vector3D(LocationConverter.geoToEcef(sensorPosition));

        // platform rotation (todo: check against the "full" versions)
        PlatformHeadingAngle heading = (PlatformHeadingAngle)msg.getField(UasDatalinkTag.PlatformHeadingAngle);
        PlatformPitchAngle pitch = (PlatformPitchAngle)msg.getField(UasDatalinkTag.PlatformPitchAngle);
        PlatformRollAngle platformRoll = (PlatformRollAngle)msg.getField(UasDatalinkTag.PlatformRollAngle);
        platformRotation = new Rotation(RotationOrder.XYX, RotationConvention.FRAME_TRANSFORM,
            heading.getRadians(), pitch.getRadians(), platformRoll.getRadians());

        // gimbal rotation
        SensorRelativeAzimuth azimuth = (SensorRelativeAzimuth)msg.getField(UasDatalinkTag.SensorRelativeAzimuthAngle);
        SensorRelativeElevation elevation = (SensorRelativeElevation)msg.getField(UasDatalinkTag.SensorRelativeElevationAngle);
        SensorRelativeRoll sensorRoll = (SensorRelativeRoll)msg.getField(UasDatalinkTag.SensorRelativeRollAngle);
        gimbalRotation = new Rotation(RotationOrder.XYZ, RotationConvention.FRAME_TRANSFORM,
            azimuth.getRadians(), elevation.getRadians(), sensorRoll.getRadians());

        // camera
        HorizontalFov hfov = (HorizontalFov)msg.getField(UasDatalinkTag.SensorHorizontalFov);
        VerticalFov vfov = (VerticalFov)msg.getField(UasDatalinkTag.SensorVerticalFov);
        // todo image dims needed?
        camera = new Camera(hfov.getRadians(), vfov.getRadians(), 640, 480);
    }

    public long getPrecisionTimestamp() {
        return precisionTimestamp;
    }

    public Vector3D getEcefPosition() {
        return ecefPosition;
    }

    public Rotation getPlatformRotation() {
        return platformRotation;
    }

    public Rotation getGimbalRotation() {
        return gimbalRotation;
    }

    public Camera getCamera() {
        return camera;
    }
}
